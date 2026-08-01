package io.github.Leonardo0013YT.UltraMinions.setup;

import io.github.Leonardo0013YT.UltraMinions.Main;
import io.github.Leonardo0013YT.UltraMinions.food.Food;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class SetupFood {
   private ItemStack food;
   private int amount;
   private Main plugin;

   public SetupFood(Main plugin) {
      this.plugin = plugin;
      this.food = new ItemStack(Material.CARROT);
      this.amount = 5;
   }

   public SetupFood(Main plugin, Food food) {
      this.plugin = plugin;
      this.food = food.getFood();
      this.amount = food.getAmount();
   }

   public void setFood(ItemStack food) {
      this.food = food;
   }

   public int getAmount() {
      return this.amount;
   }

   public void setAmount(int amount) {
      this.amount = amount;
   }

   public void save(Player p) {
      int amount = 0;
      if (this.plugin.getFoods().isSet("foods")) {
         amount = this.plugin.getFoods().getConfig().getConfigurationSection("foods").getKeys(false).size();
      }

      this.plugin.getFoods().set("foods." + amount + ".item", this.food);
      this.plugin.getFoods().set("foods." + amount + ".amount", this.amount);
      this.plugin.getFoods().save();
   }
}
