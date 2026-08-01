package io.github.Leonardo0013YT.UltraMinions.managers;

import io.github.Leonardo0013YT.UltraMinions.Main;
import io.github.Leonardo0013YT.UltraMinions.minions.skins.MinionSkin;
import java.util.HashMap;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;

public class SkinManager {
   private Main plugin;
   private HashMap<String, MinionSkin> skins = new HashMap();

   public SkinManager(Main plugin) {
      this.plugin = plugin;
      this.loadSkins();
   }

   public void loadSkins() {
      this.skins.clear();
      ConfigurationSection conf = this.plugin.getSkins().getConfig().getConfigurationSection("skins");

      for(String s : conf.getKeys(false)) {
         this.skins.put(s.toLowerCase(), new MinionSkin(this.plugin, s));
      }

   }

   public HashMap<String, MinionSkin> getSkins() {
      return this.skins;
   }

   public MinionSkin getMinionSkinByName(ItemStack item) {
      if (item.hasItemMeta() && item.getItemMeta().hasDisplayName()) {
         for(MinionSkin s : this.skins.values()) {
            if (s.getCustomname().equals(item.getItemMeta().getDisplayName().replaceFirst(this.plugin.getLang().get("items.minionSkin.nameItem") + " ", ""))) {
               return s;
            }
         }

         return null;
      } else {
         return null;
      }
   }
}
