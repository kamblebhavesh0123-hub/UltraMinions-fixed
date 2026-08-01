package io.github.Leonardo0013YT.UltraMinions.minions.skins;

import io.github.Leonardo0013YT.UltraMinions.Main;
import io.github.Leonardo0013YT.UltraMinions.utils.ItemBuilder;
import org.bukkit.inventory.ItemStack;

public class MinionSkin {
   private String customname;
   private String name;
   private ItemStack head;
   private int red;
   private int green;
   private int blue;

   public MinionSkin(Main plugin, String name) {
      this.name = name;
      this.customname = plugin.getSkins().get("skins." + name + ".name");
      this.head = ItemBuilder.createSkull(plugin.getLang().get("items.minionSkin.nameItem") + " " + this.customname, plugin.getLang().get("items.minionSkin.loreItem"), plugin.getSkins().get("skins." + name + ".uri"));
      this.red = plugin.getSkins().getInt("skins." + name + ".armor.red");
      this.green = plugin.getSkins().getInt("skins." + name + ".armor.green");
      this.blue = plugin.getSkins().getInt("skins." + name + ".armor.blue");
   }

   public String getCustomname() {
      return this.customname;
   }

   public String getName() {
      return this.name;
   }

   public ItemStack getHead() {
      return this.head;
   }

   public int getRed() {
      return this.red;
   }

   public int getGreen() {
      return this.green;
   }

   public int getBlue() {
      return this.blue;
   }
}
