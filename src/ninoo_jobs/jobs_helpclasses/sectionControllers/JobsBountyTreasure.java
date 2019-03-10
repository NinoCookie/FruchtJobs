package ninoo_jobs.jobs_helpclasses.sectionControllers;

import ninoo_jobs.jobs_Main.JobsMain;
import ninoo_jobs.jobs_helpclasses.helpfulObjects.JobsRItem;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class JobsBountyTreasure {

    public List<JobsRItem> ItemList;
    public String Rarity;

    public JobsBountyTreasure(String rarity) {
        Rarity = rarity;
    }

    public void fillBountyTreasure(){
        ItemList=new ArrayList<>();
        for (int i = 0; i < JobsMain.treasureItemList.size(); i++) {
            if(chance(JobsMain.treasureItemList.get(i).Chance)){
                for (int j = 0; j < JobsMain.treasureItemList.get(i).JobsTreasureItemsItemsList.size(); j++) {
                    if(JobsMain.treasureItemList.get(i).Rarity.equalsIgnoreCase(Rarity)){
                        if(chance(JobsMain.treasureItemList.get(i).JobsTreasureItemsItemsList.get(j).Chance)){
                            ItemList.add(new JobsRItem(
                                    JobsMain.treasureItemList.get(i).JobsTreasureItemsItemsList.get(j).Displayname,
                                    JobsMain.treasureItemList.get(i).JobsTreasureItemsItemsList.get(j).Name,
                                    JobsMain.treasureItemList.get(i).JobsTreasureItemsItemsList.get(j).Lore,
                                    JobsMain.treasureItemList.get(i).Rarity,
                                    JobsMain.treasureItemList.get(i).JobsTreasureItemsItemsList.get(j).Enchants,
                                    JobsMain.treasureItemList.get(i).JobsTreasureItemsItemsList.get(j).Amount));
                        }
                    }
                }
            }
        }
    }
    public boolean chance(double chance){
        Random r = new Random();
        double result = r.nextDouble();
        if (result <= chance / 100) {
            return true;
        }
        return false;
    }

    public List<JobsRItem> getItemList() {
        return ItemList;
    }

}
