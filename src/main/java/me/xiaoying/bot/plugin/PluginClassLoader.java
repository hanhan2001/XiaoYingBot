package me.xiaoying.bot.plugin;

import me.xiaoying.bot.utils.Preconditions;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.jar.JarFile;
import java.util.jar.Manifest;

public class PluginClassLoader extends URLClassLoader {
    JavaPluginLoader loader;
    ClassLoader parent;
    PluginDescriptionFile description;
    File dataFolder;
    File file;
    JarFile jar;
    Manifest manifest;
    URL url;
    ClassLoader libraryLoader;
    final JavaPlugin plugin;
    private JavaPlugin pluginInit;
    private IllegalStateException pluginState;
    private final Map<String, Class<?>> classes = new ConcurrentHashMap<>();

    public PluginClassLoader(JavaPluginLoader loader, ClassLoader parent, PluginDescriptionFile description, File dataFolder, File file, ClassLoader libraryLoader) throws IOException, InvalidPluginException, MalformedURLException {
        super(new URL[]{file.toURI().toURL()}, parent);
        Preconditions.checkArgument(loader != null, "Loader cannot be null");
        this.loader = loader;
        this.description = description;
        this.dataFolder = dataFolder;
        this.file = file;
        this.jar = new JarFile(file);
        this.manifest = this.jar.getManifest();
        this.url = file.toURI().toURL();
        this.libraryLoader = libraryLoader;

        try {
            Class<?> jarClass;
            try {
                jarClass = Class.forName(description.getMain(), true, this);
            } catch (ClassNotFoundException var11) {
                throw new InvalidPluginException("Cannot find main class `" + description.getMain() + "'", var11);
            }

            Class<?> pluginClass;
            try {
                pluginClass = jarClass.asSubclass(JavaPlugin.class);
            } catch (ClassCastException var10) {
                throw new InvalidPluginException("main class `" + description.getMain() + "' does not extend JavaPlugin", var10);
            }

            this.plugin = (JavaPlugin)pluginClass.newInstance();
        } catch (IllegalAccessException var12) {
            throw new InvalidPluginException("No public constructor", var12);
        } catch (InstantiationException var13) {
            throw new InvalidPluginException("Abnormal plugin type", var13);
        }
    }

    Set<String> getClasses() {
        return this.classes.keySet();
    }

    protected Class<?> findClass(String name) throws ClassNotFoundException {
        return findClass(name, true);
    }

    Class<?> findClass(String name, boolean checkGlobal) throws ClassNotFoundException {
        if (name.startsWith("org.bukkit.") || name.startsWith("net.minecraft.")) {
            throw new ClassNotFoundException(name);
        }
        Class<?> result = this.classes.get(name);

        if (result == null) {
            if (checkGlobal)
                result = this.loader.getClassByName(name);

            if (result == null) {
                result = super.findClass(name);
                if (result != null)
                    this.loader.setClass(name, result);
            }
            this.classes.put(name, result);
        }
        return result;
    }

    synchronized void initialize(JavaPlugin javaPlugin) {
        Preconditions.checkArgument(javaPlugin != null, "Initializing plugin cannot be null");
        Preconditions.checkArgument(javaPlugin.getClass().getClassLoader() == this, "Cannot initialize plugin outside of this class loader");
        if (this.plugin != null || this.pluginInit != null)
            throw new IllegalArgumentException("Plugin already initialized!", this.pluginState);

        this.pluginState = new IllegalStateException("Initial initialization");
        this.pluginInit = javaPlugin;

        javaPlugin.init(this.loader, this.description, this.dataFolder, this.file, this);
    }
}