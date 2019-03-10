package ninoo_jobs.jobs_Main.fileHandler;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class JobsCFG {

    private final Plugin p;
    private final String filename;
    private FileConfiguration file;
    private File cfile;

    public JobsCFG(final Plugin p, final String string) {
        this.p = p;
        this.filename = string + ".yml";
    }

    /**
     * Attempts to load the file.
     *
     * @param saveResource
     *            -> Saves the raw contents embedded with the plugin's jar
     *
     * @see #filename
     * @see #cfile
     * @see #reloadFile()
     * @see JavaPlugin#saveResource(String, boolean)
     */
    public void setup(final boolean saveResource) {
        if (!this.p.getDataFolder().exists()) {
            this.p.getDataFolder().mkdir();
        }

        this.cfile = new File(this.p.getDataFolder(), this.filename);

        if (!this.cfile.exists()) {
            try {
                this.cfile.createNewFile();
            } catch (final IOException e) {
                e.printStackTrace();
            }
            if (saveResource) {
                this.p.saveResource(this.filename, true);
            }
        }

        this.reloadFile();
    }

    /**
     * Saves the file configuration.
     *
     * @see FileConfiguration#save(File)
     * @see #getFile()
     * @see #cfile
     */
    public void saveFile() {
        try {
            this.getFile().save(this.cfile);
        } catch (final IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Reloads the file.
     *
     * @see YamlConfiguration#loadConfiguration(File)
     * @see #file
     * @see #cfile
     */
    public void reloadFile() {
        this.file = YamlConfiguration.loadConfiguration(this.cfile);
    }

    /**
     * @return the file -> The fileconfiguration.
     */
    public FileConfiguration getFile() {
        return this.file;
    }
}