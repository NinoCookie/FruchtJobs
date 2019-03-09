package ninoo_jobs.jobs_helpclasses;

import org.bukkit.configuration.ConfigurationSection;

import java.util.ArrayList;
import java.util.List;

public class JobsTreasureItemsItems {
    public String Name;
    public String Displayname;
    public List<String> Lore;
    public List<String> Enchants;
    public int Amount;
    public double Chance;
    public boolean Glow;

    public JobsTreasureItemsItems(ConfigurationSection section){
        Name = section.getString("name");
        Displayname = section.getString("displayname");
        Lore = section.getStringList("lore");
        Amount = section.getInt("amount");
        Chance = section.getDouble("chance");
        Enchants = section.getStringList("enchants");
        Glow    = section.getBoolean("itemglow");
    }

    public static List<JobsTreasureItemsItems> FromSection(ConfigurationSection section){
        List<JobsTreasureItemsItems> jobsTreasureItemsItems =new ArrayList<>();
        for (String key : section.getKeys(false)){
            jobsTreasureItemsItems.add(new JobsTreasureItemsItems(section.getConfigurationSection(key)));
        }
        return jobsTreasureItemsItems;
    }

}
