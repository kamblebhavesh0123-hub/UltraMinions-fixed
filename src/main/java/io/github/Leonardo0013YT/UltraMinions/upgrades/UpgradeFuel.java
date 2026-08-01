package io.github.Leonardo0013YT.UltraMinions.upgrades;

import io.github.Leonardo0013YT.UltraMinions.Main;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class UpgradeFuel {
   private double percent;
   private long duration;
   private String name;
   private ItemStack result;
   private boolean isCraft;
   private boolean unlimited = false;
   private Main plugin;

   public UpgradeFuel(Main plugin, String path) {
      this.plugin = plugin;
      this.name = plugin.getUpgrades().get(path + ".name");
      this.duration = (long)(plugin.getUpgrades().getInt(path + ".duration") * 1000);
      if (this.duration < 0L) {
         this.unlimited = true;
      }

      this.percent = plugin.getUpgrades().getConfig().getDouble(path + ".percent", (double)5.0F);
      this.isCraft = plugin.getUpgrades().getBoolean(path + ".isCraft");
      this.result = plugin.getUpgrades().getConfig().getItemStack(path + ".result");
      if (this.isCraft) {
         plugin.getCm().loadCustomCraft(path, this.name);
      }

   }

   public double getPercent() {
      return this.percent;
   }

   public long getDuration() {
      return this.unlimited ? Long.MAX_VALUE : this.duration;
   }

   public boolean isCraft() {
      return this.isCraft;
   }

   public String getName() {
      return this.name;
   }

   public ItemStack getResult(boolean minion) {
      if (this.result == null) {
         return null;
      } else {
         ItemStack now = this.result.clone();
         if (now.getItemMeta() == null) {
            return now;
         } else if (now.getItemMeta().getLore() == null) {
            return now;
         } else {
            ItemMeta nowM = now.getItemMeta();
            List<String> lore = new ArrayList();

            for(String s : nowM.getLore()) {
               lore.add(s.replaceAll("<status>", minion ? (this.unlimited ? this.plugin.getLang().get("menus.upgrades.fuel.status.unlimited") : this.plugin.getLang().get("menus.upgrades.fuel.status.minion")) : this.plugin.getLang().get("menus.upgrades.fuel.status.item")).replaceAll("<time>", this.plugin.getLang().get("menus.upgrades.fuel.time")));
            }

            nowM.setLore(lore);
            now.setItemMeta(nowM);
            return now;
         }
      }
   }

   public ItemStack getResult(boolean minion, String time) {
      if (this.result == null) {
         return null;
      } else {
         ItemStack now = this.result.clone();
         if (now.getItemMeta() == null) {
            return now;
         } else if (now.getItemMeta().getLore() == null) {
            return now;
         } else {
            ItemMeta nowM = now.getItemMeta();
            List<String> lore = new ArrayList();

            for(String s : nowM.getLore()) {
               lore.add(s.replaceAll("<status>", minion ? (this.unlimited ? this.plugin.getLang().get("menus.upgrades.fuel.status.unlimited") : this.plugin.getLang().get("menus.upgrades.fuel.status.minion")) : this.plugin.getLang().get("menus.upgrades.fuel.status.item")).replaceAll("<time>", time));
            }

            nowM.setLore(lore);
            now.setItemMeta(nowM);
            return now;
         }
      }
   }

   public ItemStack getResult() {
      return this.result;
   }

   public boolean isUnlimited() {
      return this.unlimited;
   }

   public Main getPlugin() {
      return this.plugin;
   }
}
