package ninoo_jobs.jobs_helpclasses.sectionControllers;

import org.bukkit.configuration.ConfigurationSection;

import java.util.ArrayList;
import java.util.List;

public class JobsBounty {
    public int Number;
    public String Permission;
    public int Level;
    public double Chance;
    public List<String> Commands;
    public String Rarity;
    public String Name;
    public boolean Stats;
    public boolean Treasure;
    public double Cashchance;
    public double Cash;
    public String Guiicon;

    public JobsBounty(ConfigurationSection section)
    {
        Number      = section.getInt("number");
        Permission  = section.getString("permission");
        Level       = section.getInt("level");
        Chance      = section.getDouble("chance");
        Commands     = section.getStringList("commands");
        Rarity      = section.getString("rarity");
        Name        = section.getString("name");
        Stats       = section.getBoolean("stats");
        Treasure = section.getBoolean("treasure");
        Cash    = section.getDouble("cash");
        Cashchance  = section.getDouble("cashchance");
        Guiicon =   section.getString("guiicon");

    }

    public static List<JobsBounty> FromSection(ConfigurationSection section)
    {
        List<JobsBounty> bounties = new ArrayList<>();

        for(String key : section.getKeys(false))
        {
            bounties.add(new JobsBounty(section.getConfigurationSection(key)));
        }

        return bounties;
    }
}
