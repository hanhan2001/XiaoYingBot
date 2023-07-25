package me.xiaoying.bot.plugin;

import me.xiaoying.bot.cache.Caches;
import me.xiaoying.bot.configuration.YamlConfiguration;
import me.xiaoying.bot.configuration.serialization.ConfigurationSerializable;
import me.xiaoying.bot.configuration.serialization.ConfigurationSerialization;
import me.xiaoying.bot.enums.InfoType;
import me.xiaoying.bot.event.*;
import me.xiaoying.bot.server.Server;
import me.xiaoying.bot.utils.InfoUtil;
import me.xiaoying.bot.utils.Preconditions;
import me.xiaoying.bot.utils.ZipUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Pattern;

public class JavaPluginLoader implements PluginLoader {
    final Server server;
    private final List<RegisteredListener> registeredListeners = new ArrayList<>();
    private final Map<String, PluginClassLoader> loaders = new LinkedHashMap<>();
    private final Pattern[] fileFilters = new Pattern[]{Pattern.compile("\\.jar$")};
    private final Map<String, Class<?>> classes = new ConcurrentHashMap<>();

    public JavaPluginLoader(Server instance) {
        Preconditions.checkArgument(instance != null, "Server cannot be null");
        this.server = instance;
    }

    @Override
    public Plugin loadPlugin(File file) throws InvalidPluginException {
        PluginDescriptionFile description;
        PluginClassLoader loader;
        Preconditions.checkArgument(file != null, "File cannot be null");

        if (!file.exists())
            throw new InvalidPluginException(new FileNotFoundException(file.getPath() + " does not exist"));

        try {
            description = getPluginDescription(file);
        } catch (InvalidDescriptionException e) {
            throw new RuntimeException(e);
        }

        File parentFile = file.getParentFile();
        File dataFolder = new File(parentFile, description.getName());
        if (Caches.plugins.containsKey(description.getName()))
            throw new InvalidPluginException("Already load " + description.getName());

        try {
            loader = new PluginClassLoader(this, getClass().getClassLoader(), description, dataFolder, file, this.getClass().getClassLoader());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return loader.plugin;
    }

    @Override
    public PluginDescriptionFile getPluginDescription(File file) throws InvalidDescriptionException {
        YamlConfiguration pluginConfig = YamlConfiguration.loadConfiguration(ZipUtil.getFile(file.getPath(), "plugin.yml"));
        String name = pluginConfig.getString("name");
        Preconditions.checkNotNull(name, "PluginName cannot be null");
        String main = pluginConfig.getString("main");
        Preconditions.checkNotNull(main, "Main class cannot be null");
        String version = pluginConfig.getString("version");
        Preconditions.checkNotNull(version, "Plugin version cannot be null");
        List<String> authors = new ArrayList<>();
        if (pluginConfig.getString("author") != null)
            authors.add(pluginConfig.getString("author"));

        if (pluginConfig.getStringList("authors") != null)
            authors.addAll(pluginConfig.getStringList("authors"));
        return new PluginDescriptionFile(name, version, authors, main);
    }

    @Override
    public Pattern[] getPluginFileFilters() {
        return (Pattern[]) this.fileFilters.clone();
    }

    @Override
    public Map<Class<? extends Event>, Set<RegisteredListener>> createRegisteredListeners(Listener listener, Plugin plugin) {

        Set<Method> methods;
        Preconditions.checkNotNull(plugin, "Plugin can not be null");
        Preconditions.checkNotNull(listener, "Listener can not be null");

        Map<Class<? extends Event>, Set<RegisteredListener>> ret = new HashMap<>();

        try {
            Method[] publicMethods = listener.getClass().getMethods();
            Method[] privateMethods = listener.getClass().getDeclaredMethods();
            methods = new HashSet<Method>(publicMethods.length + privateMethods.length, 1.0F);
            Method[] arrayOfMethod1;
            int i;
            byte b;
            for (i = (arrayOfMethod1 = publicMethods).length, b = 0; b < i; ) {
                final Method method = arrayOfMethod1[b];
                methods.add(method);
                b++;
            }

            for (i = (arrayOfMethod1 = privateMethods).length, b = 0; b < i; ) {
                final Method method = arrayOfMethod1[b];
                methods.add(method);
                b++;
            }

        } catch (NoClassDefFoundError e) {
            InfoUtil.sendMessage(InfoType.WARING, "Plugin " + plugin.getDescription().getName() + " has failed to register events for " + listener.getClass() + " because " + e.getMessage() + " does not exist.");
            return ret;
        }

        for (Method method : methods) {
            EventHandler eh = method.getAnnotation(EventHandler.class);
            if (eh == null) {
                continue;
            }
            if (method.isBridge() || method.isSynthetic()) {
                continue;
            }
            Class<?> checkClass;
            if ((method.getParameterTypes()).length != 1 || !Event.class.isAssignableFrom(checkClass = method.getParameterTypes()[0])) {
                InfoUtil.sendMessage(plugin.getDescription().getName() + " attempted to register an invalid EventHandler method signature \"" + method.toGenericString() + "\" in " + listener.getClass());
                continue;
            }
            final Class<? extends Event> eventClass = checkClass.asSubclass(Event.class);
            method.setAccessible(true);
            Set<RegisteredListener> eventSet = ret.get(eventClass);
            if (eventSet == null) {
                eventSet = new HashSet<>();
                ret.put(eventClass, eventSet);
            }

            EventExecutor executor = new EventExecutor() {
                public void execute(Listener listener, Event event) throws EventException {
                    try {
                        if (!eventClass.isAssignableFrom(event.getClass())) {
                            return;
                        }
                        method.invoke(listener, new Object[]{event});
                    } catch (InvocationTargetException ex) {
                        throw new EventException(ex.getCause());
                    } catch (Throwable t) {
                        throw new EventException(t);
                    }
                }
            };


            eventSet.add(new RegisteredListener(listener, executor, eh.priority(), plugin));
        }

        return ret;
    }

    @Override
    public void delRegisteredListener(Listener listener) {
        for (int i = 0; i < registeredListeners.size(); i++) {
            if (registeredListeners.get(i).getListener() == listener)
                registeredListeners.remove(i);
        }
    }

    @Override
    public void delRegisteredListener(Plugin plugin) {
        for (int i = 0; i < registeredListeners.size(); i++) {
            if (registeredListeners.get(i).getPlugin() == plugin)
                registeredListeners.remove(i);
        }
    }

    @Override
    public void delRegisteredListeners() {
        registeredListeners.clear();
    }

    @Override
    public List<RegisteredListener> getRegisteredListener() {
        return this.registeredListeners;
    }

    @Override
    public void enablePlugin(Plugin plugin) {
        Preconditions.isTrue(plugin instanceof JavaPlugin, "Plugin is not associated with this PluginLoader");

        if (plugin.isEnabled())
            return;

        String message = String.format("Enabling %s %s by %s", plugin.getDescription().getName(), plugin.getDescription().getVersion(), plugin.getDescription().getAuthors());
        InfoUtil.sendMessage(message);

        JavaPlugin jPlugin = (JavaPlugin) plugin;

        String pluginName = jPlugin.getDescription().getName();

        if (!this.loaders.containsKey(pluginName)) {
            this.loaders.put(pluginName, (PluginClassLoader) jPlugin.getClassLoader());
        }

        try {
            jPlugin.setEnabled(true);
        } catch (Throwable ex) {
            InfoUtil.sendMessage(InfoType.WARING, "Error occurred while enabling " + plugin.getDescription().getName() + " (Is it up to date?)\n" + ex.getMessage());
            ex.printStackTrace();
        }


//            this.server.getPluginManager().callEvent((Event) new PluginEnableEvent(plugin));
    }

    Class<?> getClassByName(String name) {
        Class<?> cachedClass = this.classes.get(name);

        if (cachedClass != null)
            return cachedClass;

        for (String current : this.loaders.keySet()) {
            PluginClassLoader loader = this.loaders.get(current);

            try {
                cachedClass = loader.findClass(name, false);
            } catch (ClassNotFoundException classNotFoundException) {
            }
            if (cachedClass != null)
                return cachedClass;
        }

        return null;
    }

    void setClass(String name, Class<?> clazz) {
        if (this.classes.containsKey(name))
            return;

        this.classes.put(name, clazz);

        if (ConfigurationSerializable.class.isAssignableFrom(clazz)) {
            Class<? extends ConfigurationSerializable> serializable = clazz.asSubclass(ConfigurationSerializable.class);
            ConfigurationSerialization.registerClass(serializable);
        }
    }

    private void removeClass(String name) {
        Class<?> clazz = this.classes.remove(name);

        try {
            if (clazz != null && ConfigurationSerializable.class.isAssignableFrom(clazz)) {
                Class<? extends ConfigurationSerializable> serializable = clazz.asSubclass(ConfigurationSerializable.class);
                ConfigurationSerialization.unregisterClass(serializable);
            }
        } catch (NullPointerException nullPointerException) {
        }
    }

    @Override
    public void disablePlugin(Plugin plugin) {
        Preconditions.isTrue(plugin instanceof JavaPlugin, "Plugin is not associated with this PluginLoader");

        if (!plugin.isEnabled())
            return;


        String message = String.format("Disabling %s", plugin.getDescription().getName());
        InfoUtil.sendMessage(message);

//            this.server.getPluginManager().callEvent(new PluginDisableEvent(plugin));

        JavaPlugin jPlugin = (JavaPlugin) plugin;
        ClassLoader cloader = jPlugin.getClassLoader();

        try {
            jPlugin.setEnabled(false);
        } catch (Throwable ex) {
            InfoUtil.sendMessage(InfoType.WARING, "Error occurred while disabling " + plugin.getDescription().getName() + " (Is it up to date?)\n" + ex.getMessage());
        }

        this.loaders.remove(jPlugin.getDescription().getName());

        if (cloader instanceof PluginClassLoader) {
            PluginClassLoader loader = (PluginClassLoader) cloader;
            Set<String> names = loader.getClasses();

            for (String name : names)
                removeClass(name);
        }
    }
}