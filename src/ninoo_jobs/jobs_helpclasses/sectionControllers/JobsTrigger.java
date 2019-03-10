package ninoo_jobs.jobs_helpclasses.sectionControllers;

import org.bukkit.configuration.ConfigurationSection;

import java.util.ArrayList;
import java.util.List;

public class JobsTrigger
{
    public String Name;
    public double XP;
    public String Entity;
    public List<Integer> Bounties;

    public JobsTrigger(ConfigurationSection section)
    {
        Name    = section.getString("name");
        XP      = section.getDouble("xp");
        Entity  = section.getString("entity");
        Bounties = section.getIntegerList("bounties");

    }

    public static List<JobsTrigger> FromSection(ConfigurationSection section)
    {
        List<JobsTrigger> triggers = new ArrayList<>();

        for(String key : section.getKeys(false))
        {
            triggers.add(new JobsTrigger(section.getConfigurationSection(key)));
        }

        return triggers;
    }
}
