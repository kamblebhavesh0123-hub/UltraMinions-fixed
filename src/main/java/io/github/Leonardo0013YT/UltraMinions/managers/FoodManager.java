package io.github.Leonardo0013YT.UltraMinions.managers;

import io.github.Leonardo0013YT.UltraMinions.Main;
import io.github.Leonardo0013YT.UltraMinions.food.Food;
import io.github.Leonardo0013YT.UltraMinions.utils.Utils;
import java.util.HashMap;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;

public class FoodManager {
   private Main plugin;
   private HashMap<String, Food> foods = new HashMap();

   public FoodManager(Main plugin) {
      this.plugin = plugin;
      this.loadFoods();
   }

   public void loadFoods() {
      this.foods.clear();
      if (this.plugin.getFoods().isSet("foods")) {
         ConfigurationSection food = this.plugin.getFoods().getConfig().getConfigurationSection("foods");

         for(String s : food.getKeys(false)) {
            Food f = new Food(this.plugin.getFoods().getConfig().getItemStack("foods." + s + ".item"), this.plugin.getFoods().getInt("foods." + s + ".amount"), s);
            this.foods.put(f.getId(), f);
         }
      }

   }

   public Food getFoodByItem(ItemStack item) {
      if (item != null && !item.getType().equals(Material.AIR)) {
         for(Food f : this.foods.values()) {
            item.setAmount(f.getFood().getAmount());
            if (f.getFood() != null && !f.getFood().getType().equals(Material.AIR) && Utils.isSimilar(f.getFood(), item)) {
               return f;
            }
         }

         return null;
      } else {
         return null;
      }
   }

   public Food getFoodByKey(String id) {
      return (Food)this.foods.get(id);
   }

   public HashMap<String, Food> getFoods() {
      return this.foods;
   }
}
