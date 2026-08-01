package io.github.Leonardo0013YT.UltraMinions.upgrades;

import io.github.Leonardo0013YT.UltraMinions.Main;
import java.util.HashMap;
import org.bukkit.inventory.ItemStack;

public class UpgradeCompressor {
   private String name;
   private ItemStack result;
   private int amount;
   private boolean isCraft;
   private HashMap<String, Integer> specialAmounts = new HashMap();

   public UpgradeCompressor(Main plugin, String path) {
      this.name = plugin.getUpgrades().get(path + ".name");
      this.isCraft = plugin.getUpgrades().getBoolean(path + ".isCraft");
      this.amount = plugin.getUpgrades().getInt(path + ".amount");
      if (plugin.getUpgrades().isSet(path + ".special_amounts")) {
         for(String special : plugin.getUpgrades().getConfig().getConfigurationSection(path + ".special_amounts").getKeys(false)) {
            this.specialAmounts.put(special, plugin.getUpgrades().getInt(path + ".special_amounts." + special));
         }
      }

      this.result = plugin.getUpgrades().getConfig().getItemStack(path + ".result");
      if (this.isCraft) {
         plugin.getCm().loadCustomCraft(path, this.name);
      }

   }

   public boolean isCraft() {
      return this.isCraft;
   }

   public int getAmount(String key) {
      return this.specialAmounts.containsKey(key) ? (Integer)this.specialAmounts.get(key) : this.amount;
   }

   public String getName() {
      return this.name;
   }

   public ItemStack getResult() {
      return this.result;
   }
}
