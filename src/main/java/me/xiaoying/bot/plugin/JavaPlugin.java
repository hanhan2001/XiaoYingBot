package me.xiaoying.bot.plugin;

import me.xiaoying.bot.command.PluginCommand;
import me.xiaoying.bot.enums.InfoType;
import me.xiaoying.bot.server.Server;
import me.xiaoying.bot.utils.InfoUtil;
import me.xiaoying.bot.utils.Preconditions;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;

public class JavaPlugin extends PluginBase {
    private boolean isEnabled = false;
    private boolean naggable = true;
    private Server server = null;
    private JavaPluginLoader loader;
    private PluginDescriptionFile description;
    private PluginCommand pluginCommand = new PluginCommand();
    private File configFile = null;
    private File file;
    private File dataFolder;
    private ClassLoader classLoader;

    public JavaPlugin() {
        ClassLoader classLoader = this.getClass().getClassLoader();
        if (!(classLoader instanceof PluginClassLoader)) {
            throw new IllegalStateException("JavaPlugin requires " + PluginClassLoader.class.getName());
        } else {
            ((PluginClassLoader)classLoader).initialize(this);
        }
    }

    protected JavaPlugin(JavaPluginLoader loader, PluginDescriptionFile description, File dataFolder, File file) {
        ClassLoader classLoader = this.getClass().getClassLoader();
        if (classLoader instanceof PluginClassLoader) {
            throw new IllegalStateException("Cannot use initialization constructor at runtime");
        }
        this.init(loader, description, dataFolder, file, classLoader);
    }

    final void init(JavaPluginLoader loader, PluginDescriptionFile description, File dataFolder, File file, ClassLoader classLoader) {
        this.loader = loader;
        this.file = file;
        this.server = loader.server;
        this.description = description;
        this.dataFolder = dataFolder;
        this.classLoader = classLoader;
    }

    protected final void setEnabled(boolean enabled) {
        if (this.isEnabled == enabled)
            return;

        this.isEnabled = enabled;
        if (this.isEnabled)
            this.onEnable();
        else
            this.onDisable();
    }

    protected final ClassLoader getClassLoader() {
        return this.classLoader;
    }

    protected File getFile() {
        return this.file;
    }

    @Override
    public final File getDataFolder() {
        return this.dataFolder;
    }

    @Override
    public PluginDescriptionFile getDescription() {
        return this.description;
    }

    @Override
    public void saveConfig() {
    }

    @Override
    public void saveDefaultConfig() {
        if (!this.configFile.exists()) {
            saveResource("config.yml", false);
        }
    }

    @Override
    public void saveResource(String resourcePath, boolean replace) {
        if (resourcePath == null || resourcePath.equals("")) {
            throw new IllegalArgumentException("ResourcePath cannot be null or empty");
        }

        resourcePath = resourcePath.replace('\\', '/');
        InputStream in = getResource(resourcePath);
        if (in == null) {
            throw new IllegalArgumentException("The embedded resource '" + resourcePath + "' cannot be found in " + this.file);
        }

        File outFile = new File(this.dataFolder, resourcePath);
        int lastIndex = resourcePath.lastIndexOf('/');
        File outDir = new File(this.dataFolder, resourcePath.substring(0, Math.max(lastIndex, 0)));

        if (!outDir.exists()) {
            outDir.mkdirs();
        }

        try {
            if (!outFile.exists() || replace) {
                OutputStream out = Files.newOutputStream(outFile.toPath());
                byte[] buf = new byte[in.available()];
                int len;
                while ((len = in.read(buf)) > 0) {
                    out.write(buf, 0, len);
                }
                out.close();
                in.close();
            } else {
                InfoUtil.sendMessage(InfoType.WARING, "Could not save " + outFile.getName() + " to " + outFile + " because " + outFile.getName() + " already exists.");
            }
        } catch (IOException ex) {
            InfoUtil.sendMessage("Could not save " + outFile.getName() + " to " + outFile);
            ex.printStackTrace();
        }
    }

    @Override
    public Server getServer() {
        return this.server;
    }

    @Override
    public void reloadConfig() {

    }

    @Override
    public PluginLoader getPluginLoader() {
        return this.loader;
    }

    @Override
    public boolean isEnabled() {
        return this.isEnabled;
    }

    @Override
    public void onDisable() {

    }

    @Override
    public void onLoad() {

    }

    @Override
    public PluginCommand getPluginCommand() {
        return this.pluginCommand;
    }

    @Override
    public void onEnable() {

    }

    @Override
    public boolean isNaggable() {
        return this.naggable;
    }

    @Override
    public void setNaggable(boolean canNag) {
        this.naggable = canNag;
    }

    public String toString() {
        return this.description.getName();
    }

    /**
     * 获取包内文件
     *
     * @param filename 文件名称
     * @return InputStream
     */
    public InputStream getResource(String filename) {
        if (filename == null) {
            throw new IllegalArgumentException("Filename cannot be null");
        }

        try {
            URL url = getClassLoader().getResource(filename);

            if (url == null) {
                return null;
            }

            URLConnection connection = url.openConnection();
            connection.setUseCaches(false);
            return connection.getInputStream();
        } catch (IOException iOException) {
            return null;
        }
    }

    public static <T extends JavaPlugin> T getPlugin(Class<T> clazz) {
        Preconditions.checkNotNull(clazz, "Null class cannot have a plugin");
        if (!JavaPlugin.class.isAssignableFrom(clazz)) {
            throw new IllegalArgumentException(clazz + " does not extend " + JavaPlugin.class);
        }
        ClassLoader cl = clazz.getClassLoader();
        if (!(cl instanceof PluginClassLoader)) {
            throw new IllegalArgumentException(clazz + " is not initialized by " + PluginClassLoader.class);
        }
        JavaPlugin plugin = ((PluginClassLoader) cl).plugin;
        if (plugin == null) {
            throw new IllegalStateException("Cannot get plugin for " + clazz + " from a static initializer");
        }
        return clazz.cast(plugin);
    }

    public static JavaPlugin getProvidingPlugin(Class<?> clazz) {
        Preconditions.checkNotNull(clazz, "Null class cannot have a plugin");
        ClassLoader cl = clazz.getClassLoader();
        if (!(cl instanceof PluginClassLoader)) {
            throw new IllegalArgumentException(clazz + " is not provided by " + PluginClassLoader.class);
        }
        JavaPlugin plugin = ((PluginClassLoader) cl).plugin;
        if (plugin == null) {
            throw new IllegalStateException("Cannot get plugin for " + clazz + " from a static initializer");
        }
        return plugin;
    }
}