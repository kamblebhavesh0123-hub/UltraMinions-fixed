package io.github.Leonardo0013YT.UltraMinions.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.SkullMeta;
import com.destroystokyo.paper.profile.PlayerProfile;
import com.destroystokyo.paper.profile.ProfileProperty;

public class ItemBuilder {
   public static ItemStack item(Material material, String displayName, String s) {
      ItemStack itemStack = new ItemStack(material);
      ItemMeta itemMeta = itemStack.getItemMeta();
      itemMeta.setDisplayName(displayName);
      itemMeta.setLore((List)(s.isEmpty() ? new ArrayList() : Arrays.asList(s.split("\\n"))));
      addItemFlags(itemMeta);
      itemStack.setItemMeta(itemMeta);
      return itemStack;
   }

   public static ItemStack item(ItemStack item, String displayName, List<String> s) {
      ItemStack itemStack = item.clone();
      ItemMeta itemMeta = itemStack.getItemMeta();
      if (itemMeta.hasLore()) {
         itemMeta.getLore().clear();
      }

      itemMeta.setDisplayName(displayName);
      itemMeta.setLore(s);
      addItemFlags(itemMeta);
      itemStack.setItemMeta(itemMeta);
      return itemStack;
   }

   public static ItemStack item(Material material, int n, short n2, String displayName, String s) {
      ItemStack itemStack = new ItemStack(material, n);
      ItemMeta itemMeta = itemStack.getItemMeta();
      itemMeta.setDisplayName(displayName);
      itemMeta.setLore((List)(s.isEmpty() ? new ArrayList() : Arrays.asList(s.split("\\n"))));
      addItemFlags(itemMeta);
      itemStack.setItemMeta(itemMeta);
      return itemStack;
   }

   public static ItemStack item(Material material, int n, short n2, String displayName, List<String> s) {
      ItemStack itemStack = new ItemStack(material, n);
      ItemMeta itemMeta = itemStack.getItemMeta();
      itemMeta.setDisplayName(displayName);
      itemMeta.setLore(s);
      addItemFlags(itemMeta);
      itemStack.setItemMeta(itemMeta);
      return itemStack;
   }

   public static ItemStack skull(Material material, int n, short n2, String displayName, String s, String owner) {
      ItemStack itemStack = new ItemStack(material, n);
      SkullMeta skullMeta = (SkullMeta)itemStack.getItemMeta();
      skullMeta.setOwner(owner);
      skullMeta.setDisplayName(displayName);
      skullMeta.setLore((List)(s.isEmpty() ? new ArrayList() : Arrays.asList(s.split("\\n"))));
      addItemFlags(skullMeta);
      itemStack.setItemMeta(skullMeta);
      return itemStack;
   }

   public static ItemStack getArmor(String displayName, String s, int red, int green, int blue) {
      Color color = Color.fromRGB(red, green, blue);
      ItemStack c = new ItemStack(Material.LEATHER_CHESTPLATE);
      LeatherArmorMeta cm = (LeatherArmorMeta)c.getItemMeta();
      cm.setDisplayName(displayName);
      cm.setLore((List)(s.isEmpty() ? new ArrayList() : Arrays.asList(s.split("\\n"))));
      cm.setColor(color);
      addItemFlags(cm);
      c.setItemMeta(cm);
      return c;
   }

   private static SkullMeta applyTexture(SkullMeta headMeta, String url) {
      PlayerProfile profile = (PlayerProfile) Bukkit.createProfile(UUID.randomUUID(), "MinionHead");
      profile.setProperty(new ProfileProperty("textures", url));
      headMeta.setPlayerProfile(profile);
      return headMeta;
   }

   public static ItemStack createSkull(String displayName, String lore, String url) {
      ItemStack head = new ItemStack(Material.PLAYER_HEAD, 1);
      if (url.isEmpty()) {
         return head;
      } else {
         SkullMeta headMeta = (SkullMeta)head.getItemMeta();
         headMeta.setDisplayName(displayName);
         headMeta.setLore((List)(lore.isEmpty() ? new ArrayList() : Arrays.asList(lore.split("\\n"))));
         headMeta = applyTexture(headMeta, url);
         head.setItemMeta(headMeta);
         return head;
      }
   }

   public static ItemStack createSkull(String displayName, List<String> lore, String url) {
      ItemStack head = new ItemStack(Material.PLAYER_HEAD, 1);
      if (url.isEmpty()) {
         return head;
      } else {
         SkullMeta headMeta = (SkullMeta)head.getItemMeta();
         headMeta.setDisplayName(displayName);
         headMeta.setLore(lore);
         headMeta = applyTexture(headMeta, url);
         head.setItemMeta(headMeta);
         return head;
      }
   }

   public static ItemStack[] getArmorMinion(int red, int green, int blue) {
      Color color = Color.fromRGB(red, green, blue);
      ItemStack c = new ItemStack(Material.LEATHER_CHESTPLATE);
      LeatherArmorMeta cm = (LeatherArmorMeta)c.getItemMeta();
      cm.setColor(color);
      c.setItemMeta(cm);
      ItemStack l = new ItemStack(Material.LEATHER_LEGGINGS);
      LeatherArmorMeta lm = (LeatherArmorMeta)c.getItemMeta();
      lm.setColor(color);
      l.setItemMeta(lm);
      ItemStack b = new ItemStack(Material.LEATHER_BOOTS);
      LeatherArmorMeta bm = (LeatherArmorMeta)c.getItemMeta();
      bm.setColor(color);
      b.setItemMeta(bm);
      return new ItemStack[]{c, l, b};
   }

   public static void addItemFlags(ItemMeta itemMeta) {
      itemMeta.addItemFlags(new ItemFlag[]{ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_DESTROYS, ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_PLACED_ON, ItemFlag.HIDE_POTION_EFFECTS, ItemFlag.HIDE_UNBREAKABLE});
   }
}
