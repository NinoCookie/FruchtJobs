package ninoo_jobs.jobs_helpclasses.helpfulObjects;

import io.netty.handler.traffic.ChannelTrafficShapingHandler;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class JobsRItem {
    public String Name;
    public String MaterialN;
    public List<String> Lore;
    public String Rarity;
    public List<String> Enchants;
    public int Amount;

    public JobsRItem(String name, String materialN, List<String> lore, String rarity, List<String> enchants, int amount) {
        Name = name;
        MaterialN = materialN;
        Lore = lore;
        Rarity = rarity;
        Enchants = enchants;
        Amount=amount;
    }

    public ItemStack contruct(){
        try {
            Material m=Material.matchMaterial(MaterialN);
            if(m!=null){
                ItemStack item=new ItemStack(m, Amount);
                if(!(Name.equalsIgnoreCase("")||Lore!=null||Enchants!=null)){
                    ItemMeta md=item.getItemMeta();
                    md.setDisplayName(Name);
                    md.setLore(Lore);
                    item.setItemMeta(md);
                    for (int i = 0; i < Enchants.size(); i++) {
                        String[] splitted=Enchants.get(i).split("-");
                        Enchantment en=Enchantment.getByName(splitted[0]);
                        if(en!=null && item.getType()!=null){
                            if(en.canEnchantItem(item)){
                                item.addEnchantment(en, Integer.parseInt(splitted[1]));
                            }
                        }
                    }
                }
                return item;
            }
        }
        catch (Exception e){
            Bukkit.getConsoleSender().sendMessage("Jobs - Error - Check Items in config!");
        }
        return null;
    }
}
