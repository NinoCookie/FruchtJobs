package ninoo_jobs.jobs_helpclasses.sectionControllers;

import org.bukkit.configuration.ConfigurationSection;

import java.util.ArrayList;
import java.util.List;

public class JobsJob
{
    public String Name;
    public String Color;
    public int MaxLevel;
    public List<String> NeededTool;
    public List<String> GUIText;
    public String GUITitle;
    public String GUIIcon;
    public List<JobsTrigger> Triggers;
    public String Permission;

    public JobsJob(ConfigurationSection section)
    {
        Name        = section.getString("name");
        Color       = section.getString("color");
        MaxLevel    = section.getInt("maxlevel");
        NeededTool  = section.getStringList("neededtool");
        GUIText     = section.getStringList("guilore");
        GUITitle    = section.getString("guititle");
        GUIIcon     = section.getString("guiicon");
        Triggers    = JobsTrigger.FromSection(section.getConfigurationSection("trigger"));
        Permission  = section.getString("permission");
    }

    public static List<JobsJob> FromSection(ConfigurationSection section)
    {
        List<JobsJob> jobsJobs = new ArrayList<>();

        for(String key : section.getKeys(false))
        {
            jobsJobs.add(new JobsJob(section.getConfigurationSection(key)));
        }

        return jobsJobs;
    }
}
