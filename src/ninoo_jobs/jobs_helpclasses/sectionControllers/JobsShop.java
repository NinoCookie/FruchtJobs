package ninoo_jobs.jobs_helpclasses.sectionControllers;

import org.bukkit.configuration.ConfigurationSection;

import javax.print.attribute.standard.JobName;
import java.util.Map;

public class JobsShop {
    public String Entity;
    public String quest;
    public double price;
    public String Jobname;

    public JobsShop(ConfigurationSection section){
        Entity = section.getString("entity");
        Jobname =section.getString("jobname");
        price   =section.getDouble("price");
        quest   =section.getString("quest");
    }
}
