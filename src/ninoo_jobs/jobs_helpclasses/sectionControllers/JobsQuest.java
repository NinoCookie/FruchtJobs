package ninoo_jobs.jobs_helpclasses.sectionControllers;

import ninoo_jobs.jobs_helpclasses.helpfulObjects.JobsRItem;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class JobsQuest {
    public String questname;
    public String jobname;
    public List<String> materialList;
    public String type;
    public List<String> beforeQuests;
    public List<ItemStack> f_materialList;
    public String guiicon;

     public JobsQuest(ConfigurationSection section) {
        this.questname = section.getString("questname");
        this.jobname = section.getString("jobname");
        this.materialList = section.getStringList("materiallist");
        this.type = section.getString("type");
        this.beforeQuests = section.getStringList("beforequests");
        constructMaterialList(materialList);
        this.guiicon=section.getString("guiicon");
    }

    private void constructMaterialList(List<String> materialList){
         f_materialList=new ArrayList<>();
        try {
            for (String s : materialList) {
                String[] str = s.split(":");
                JobsRItem item = new JobsRItem("", str[0], null, "", null, Integer.parseInt(str[1]));
                f_materialList.add(item.contruct());
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}

