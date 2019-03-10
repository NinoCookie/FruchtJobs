package ninoo_jobs.jobs_helpclasses.sectionControllers;

import org.bukkit.configuration.ConfigurationSection;

import java.util.ArrayList;
import java.util.List;

public class JobsTreasureItems {
    public String Rarity;
    public double Chance;
    public List<JobsTreasureItemsItems> JobsTreasureItemsItemsList;

    public JobsTreasureItems(ConfigurationSection section){
        Rarity = section.getString("rarity");
        Chance = section.getDouble("chance");
        JobsTreasureItemsItemsList = JobsTreasureItemsItems.FromSection(section.getConfigurationSection("items"));

    }

    public static List<JobsTreasureItems> FromSection(ConfigurationSection section){
        List<JobsTreasureItems> jobsTreasureItems = new ArrayList<>();

        for (String key : section.getKeys(false)){
            jobsTreasureItems.add(new JobsTreasureItems(section.getConfigurationSection(key)));
        }

        return jobsTreasureItems;
    }
}
