package ninoo_jobs.jobs_helpclasses;

import org.bukkit.configuration.ConfigurationSection;

import javax.print.attribute.standard.JobName;
import java.util.Map;

public class JobsShop {
    public String Entity;
    public double Chance;
    public String Jobname;

    public JobsShop(ConfigurationSection section){
        Entity = section.getString("entity");
        Chance  = section.getDouble("chance");
        Jobname =section.getString("jobname");
    }
}
