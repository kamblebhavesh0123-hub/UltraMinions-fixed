package io.github.Leonardo0013YT.UltraMinions.managers;

import io.github.Leonardo0013YT.UltraMinions.Main;
import io.github.Leonardo0013YT.UltraMinions.upgrades.UpgradeAutoSell;
import io.github.Leonardo0013YT.UltraMinions.upgrades.UpgradeAutoSmelt;
import io.github.Leonardo0013YT.UltraMinions.upgrades.UpgradeCompressor;
import io.github.Leonardo0013YT.UltraMinions.upgrades.UpgradeFuel;
import io.github.Leonardo0013YT.UltraMinions.utils.Utils;
import java.util.HashMap;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;

public class UpgradeManager {
   private Main plugin;
   private HashMap<String, UpgradeAutoSell> autoSell = new HashMap();
   private HashMap<String, UpgradeAutoSmelt> autoSmelt = new HashMap();
   private HashMap<String, UpgradeCompressor> compressor = new HashMap();
   private HashMap<String, UpgradeFuel> fuel = new HashMap();

   public UpgradeManager(Main plugin) {
      this.plugin = plugin;
      this.reload();
   }

   public void reload() {
      this.autoSmelt.clear();
      this.autoSell.clear();
      this.compressor.clear();
      this.fuel.clear();
      if (this.plugin.getUpgrades().isSet("upgrades")) {
         if (this.plugin.getUpgrades().isSet("upgrades.autoSell")) {
            ConfigurationSection conf = this.plugin.getUpgrades().getConfig().getConfigurationSection("upgrades.autoSell");

            for(String c : conf.getKeys(false)) {
               this.autoSell.put(c, new UpgradeAutoSell(this.plugin, "upgrades.autoSell." + c));
               Bukkit.getConsoleSender().sendMessage("§b[UltraMinions] §5Upgrade AutoSell §a" + c + "§5 has been loaded.");
            }
         }

         if (this.plugin.getUpgrades().isSet("upgrades.autoSmelt")) {
            ConfigurationSection conf = this.plugin.getUpgrades().getConfig().getConfigurationSection("upgrades.autoSmelt");

            for(String c : conf.getKeys(false)) {
               this.autoSmelt.put(c, new UpgradeAutoSmelt(this.plugin, "upgrades.autoSmelt." + c));
               Bukkit.getConsoleSender().sendMessage("§b[UltraMinions] §5Upgrade AutoSmelt §a" + c + "§5 has been loaded.");
            }
         }

         if (this.plugin.getUpgrades().isSet("upgrades.compressor")) {
            ConfigurationSection conf = this.plugin.getUpgrades().getConfig().getConfigurationSection("upgrades.compressor");

            for(String c : conf.getKeys(false)) {
               this.compressor.put(c, new UpgradeCompressor(this.plugin, "upgrades.compressor." + c));
               Bukkit.getConsoleSender().sendMessage("§b[UltraMinions] §5Upgrade Compressor §a" + c + "§5 has been loaded.");
            }
         }

         if (this.plugin.getUpgrades().isSet("upgrades.fuel")) {
            ConfigurationSection conf = this.plugin.getUpgrades().getConfig().getConfigurationSection("upgrades.fuel");

            for(String c : conf.getKeys(false)) {
               this.fuel.put(c, new UpgradeFuel(this.plugin, "upgrades.fuel." + c));
               Bukkit.getConsoleSender().sendMessage("§b[UltraMinions] §5Upgrade Fuel §a" + c + "§5 has been loaded.");
            }
         }
      }

   }

   public UpgradeAutoSell getAutoSell(ItemStack item) {
      if (item == null) {
         return null;
      } else {
         for(UpgradeAutoSell uf : this.autoSell.values()) {
            if (uf.getResult() != null && Utils.isSimilar(uf.getResult(), item)) {
               return uf;
            }
         }

         return null;
      }
   }

   public UpgradeAutoSmelt getAutoSmelt(ItemStack item) {
      if (item == null) {
         return null;
      } else {
         for(UpgradeAutoSmelt uf : this.autoSmelt.values()) {
            if (uf.getResult() != null && Utils.isSimilar(uf.getResult(), item)) {
               return uf;
            }
         }

         return null;
      }
   }

   public UpgradeCompressor getCompressor(ItemStack item) {
      if (item == null) {
         return null;
      } else {
         for(UpgradeCompressor uf : this.compressor.values()) {
            if (uf.getResult() != null && Utils.isSimilar(uf.getResult(), item)) {
               return uf;
            }
         }

         return null;
      }
   }

   public UpgradeFuel getFuel(boolean minion, ItemStack item) {
      if (item == null) {
         return null;
      } else {
         for(UpgradeFuel uf : this.fuel.values()) {
            ItemStack r = uf.getResult(minion);
            if (r != null) {
               ItemStack i = item.clone();
               i.setAmount(r.getAmount());
               if (Utils.isSimilar(r, i)) {
                  return uf;
               }
            }
         }

         return null;
      }
   }

   public UpgradeAutoSell getAutoSell(String key) {
      return (UpgradeAutoSell)this.autoSell.get(key);
   }

   public UpgradeAutoSmelt getAutoSmelt(String key) {
      return (UpgradeAutoSmelt)this.autoSmelt.get(key);
   }

   public UpgradeCompressor getCompressor(String key) {
      return (UpgradeCompressor)this.compressor.get(key);
   }

   public UpgradeFuel getFuel(String key) {
      return (UpgradeFuel)this.fuel.get(key);
   }

   public HashMap<String, UpgradeFuel> getFuel() {
      return this.fuel;
   }

   public HashMap<String, UpgradeAutoSell> getAutoSell() {
      return this.autoSell;
   }

   public HashMap<String, UpgradeCompressor> getCompressor() {
      return this.compressor;
   }

   public HashMap<String, UpgradeAutoSmelt> getAutoSmelt() {
      return this.autoSmelt;
   }
}
