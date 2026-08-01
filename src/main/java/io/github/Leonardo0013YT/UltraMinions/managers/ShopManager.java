package io.github.Leonardo0013YT.UltraMinions.managers;

import io.github.Leonardo0013YT.UltraMinions.Main;
import io.github.Leonardo0013YT.UltraMinions.shop.ShopItem;
import java.util.HashMap;

public class ShopManager {
   private Main plugin;
   private int lastPage = 0;
   private HashMap<String, ShopItem> shop = new HashMap();

   public ShopManager(Main plugin) {
      this.plugin = plugin;
   }

   public void loadShop() {
      this.shop.clear();
      if (this.plugin.getShop().isSet("shop")) {
         for(String s : this.plugin.getShop().getConfig().getConfigurationSection("shop").getKeys(false)) {
            ShopItem si = new ShopItem(this.plugin, "shop." + s);
            this.shop.put(si.getKey(), si);
            if (this.lastPage < si.getPage()) {
               this.lastPage = si.getPage();
            }
         }

      }
   }

   public HashMap<String, ShopItem> getShop() {
      return this.shop;
   }

   public int getLastPage() {
      return this.lastPage;
   }
}
