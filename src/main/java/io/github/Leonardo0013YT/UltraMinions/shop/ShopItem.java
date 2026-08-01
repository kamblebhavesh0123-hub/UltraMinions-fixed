package io.github.Leonardo0013YT.UltraMinions.shop;

import io.github.Leonardo0013YT.UltraMinions.Main;
import io.github.Leonardo0013YT.UltraMinions.minions.Minion;
import io.github.Leonardo0013YT.UltraMinions.minions.levels.MinionLevel;
import io.github.Leonardo0013YT.UltraMinions.utils.NBTEditor;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ShopItem {
   private String key;
   private String name;
   private int price;
   private int slot;
   private int page;
   private List<String> lore;
   private Main plugin;
   private Minion minion;

   public ShopItem(Main plugin, String path) {
      this.plugin = plugin;
      this.key = plugin.getShop().get(path + ".key");
      this.name = plugin.getShop().get(path + ".name");
      this.price = plugin.getShop().getInt(path + ".price");
      this.slot = plugin.getShop().getInt(path + ".slot");
      this.page = plugin.getShop().getInt(path + ".page");
      this.lore = plugin.getShop().getList(path + ".lore");
      this.minion = plugin.getMm().getMinion(this.key);
   }

   public int getPage() {
      return this.page;
   }

   public int getSlot() {
      return this.slot;
   }

   public int getPrice() {
      return this.price;
   }

   public String getKey() {
      return this.key;
   }

   public Minion getMinion() {
      return this.minion;
   }

   public ItemStack toIcon(Player p) {
      if (this.minion == null) {
         return new ItemStack(Material.AIR);
      } else {
         MinionLevel ml = this.minion.getMinionLevelByLevel(1);
         List<String> lore = new ArrayList();

         for(String l : this.lore) {
            lore.add(l.replaceAll("&", "§").replaceAll("<status>", this.plugin.getAdm().getCoins(p) >= (double)this.price ? this.plugin.getShop().get("hasMoney") : this.plugin.getShop().get("noMoney")).replaceAll("<title>", ml.getLevelTitle()).replaceAll("<price>", "" + this.getPrice()));
         }

         ItemStack head = NBTEditor.getHead(ml.getUrl());
         ItemMeta headM = head.getItemMeta();
         headM.setDisplayName(this.name.replaceAll("<title>", ml.getLevelTitle()));
         headM.setLore(lore);
         head.setItemMeta(headM);
         head = (ItemStack)NBTEditor.set(head, this.key, "UltraMinions", "MINION", "SHOP", "KEY");
         return head;
      }
   }
}
