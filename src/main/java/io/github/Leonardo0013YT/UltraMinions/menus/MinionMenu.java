package io.github.Leonardo0013YT.UltraMinions.menus;

import io.github.Leonardo0013YT.UltraMinions.Main;
import io.github.Leonardo0013YT.UltraMinions.craft.Craft;
import io.github.Leonardo0013YT.UltraMinions.database.PlayerMinion;
import io.github.Leonardo0013YT.UltraMinions.database.minion.PlayerMinionStat;
import io.github.Leonardo0013YT.UltraMinions.enums.MinionType;
import io.github.Leonardo0013YT.UltraMinions.minions.Minion;
import io.github.Leonardo0013YT.UltraMinions.minions.levels.MinionLevel;
import io.github.Leonardo0013YT.UltraMinions.minions.skins.MinionSkin;
import io.github.Leonardo0013YT.UltraMinions.shop.ShopItem;
import io.github.Leonardo0013YT.UltraMinions.upgrades.UpgradeFuel;
import io.github.Leonardo0013YT.UltraMinions.utils.ItemBuilder;
import io.github.Leonardo0013YT.UltraMinions.utils.NBTEditor;
import io.github.Leonardo0013YT.UltraMinions.utils.Utils;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

public class MinionMenu {
   private final ArrayList<Integer> inv = new ArrayList(Arrays.asList(21, 22, 23, 24, 25, 30, 31, 32, 33, 34, 39, 40, 41, 42, 43));
   private final ArrayList<Integer> fill = new ArrayList(Arrays.asList(0, 1, 2, 6, 7, 8, 9, 18, 27, 36, 45, 17, 26, 35, 44, 53, 46, 47, 48, 49, 50, 51, 52, 11, 12, 13, 14, 15, 16, 20, 29, 38));
   private final Collection<Integer> workbench = new ArrayList(Arrays.asList(12, 13, 14, 21, 22, 23, 30, 31, 32));
   private final HashMap<MinionType, ArrayList<int[]>> layouts = new HashMap();
   private final int[] m1m1m1 = new int[3];
   private final int[] m1m1 = new int[2];
   private final int[] m1 = new int[1];
   private final Main plugin;
   private HashMap<Player, Integer> pages = new HashMap();

   public MinionMenu(Main plugin) {
      this.plugin = plugin;
      String s3 = plugin.getConfig().getString("slots.upgrade.1m1m1m");
      String[] s3m = s3.split(";");
      this.m1m1m1[0] = Integer.parseInt(s3m[0]);
      this.m1m1m1[1] = Integer.parseInt(s3m[1]);
      this.m1m1m1[2] = Integer.parseInt(s3m[2]);
      String s2 = plugin.getConfig().getString("slots.upgrade.1m1m");
      String[] s2m = s2.split(";");
      this.m1m1[0] = Integer.parseInt(s2m[0]);
      this.m1m1[1] = Integer.parseInt(s2m[1]);
      String s1 = plugin.getConfig().getString("slots.upgrade.1m");
      String[] s1m = s1.split(";");
      this.m1[0] = Integer.parseInt(s1m[0]);
      this.layouts.put(MinionType.MINER, new ArrayList());
      ((ArrayList)this.layouts.get(MinionType.MINER)).add(new int[]{2, 3, 4, 5, 6, 11, 12, 13, 14, 15, 20, 21, 23, 24, 29, 30, 31, 32, 33, 38, 39, 40, 41, 42});
      ((ArrayList)this.layouts.get(MinionType.MINER)).add(new int[]{0, 1, 9, 10, 18, 19, 27, 28, 36, 37, 45, 46, 47, 50, 51, 7, 8, 16, 17, 25, 26, 34, 35, 43, 44, 52, 53});
      this.layouts.put(MinionType.FISHER, new ArrayList());
      ((ArrayList)this.layouts.get(MinionType.FISHER)).add(new int[]{2, 3, 4, 5, 6, 11, 12, 13, 14, 15, 20, 21, 23, 24, 29, 30, 31, 32, 33, 38, 39, 40, 41, 42});
      ((ArrayList)this.layouts.get(MinionType.FISHER)).add(new int[]{0, 1, 9, 10, 18, 19, 27, 28, 36, 37, 45, 46, 47, 50, 51, 7, 8, 16, 17, 25, 26, 34, 35, 43, 44, 52, 53});
      this.layouts.put(MinionType.LUMBERJACK, new ArrayList());
      ((ArrayList)this.layouts.get(MinionType.LUMBERJACK)).add(new int[]{2, 3, 4, 5, 6, 11, 12, 13, 14, 15, 20, 21, 23, 24, 29, 30, 31, 32, 33, 38, 39, 40, 41, 42});
      ((ArrayList)this.layouts.get(MinionType.LUMBERJACK)).add(new int[]{0, 1, 9, 10, 18, 19, 27, 28, 36, 37, 45, 46, 47, 50, 51, 7, 8, 16, 17, 25, 26, 34, 35, 43, 44, 52, 53});
      this.layouts.put(MinionType.FARMER, new ArrayList());
      ((ArrayList)this.layouts.get(MinionType.FARMER)).add(new int[]{2, 3, 4, 5, 6, 11, 12, 13, 14, 15, 20, 21, 23, 24, 29, 30, 31, 32, 33, 38, 39, 40, 41, 42});
      ((ArrayList)this.layouts.get(MinionType.FARMER)).add(new int[]{0, 1, 9, 10, 18, 19, 27, 28, 36, 37, 45, 46, 47, 50, 51, 7, 8, 16, 17, 25, 26, 34, 35, 43, 44, 52, 53});
      this.layouts.put(MinionType.PEASANT, new ArrayList());
      ((ArrayList)this.layouts.get(MinionType.PEASANT)).add(new int[]{2, 3, 4, 5, 6, 11, 12, 13, 14, 15, 20, 21, 23, 24, 29, 30, 31, 32, 33, 38, 39, 40, 41, 42});
      ((ArrayList)this.layouts.get(MinionType.PEASANT)).add(new int[]{0, 1, 9, 10, 18, 19, 27, 28, 36, 37, 45, 46, 47, 50, 51, 7, 8, 16, 17, 25, 26, 34, 35, 43, 44, 52, 53});
      this.layouts.put(MinionType.HUNTER, new ArrayList());
      ((ArrayList)this.layouts.get(MinionType.HUNTER)).add(new int[]{2, 3, 4, 5, 6, 11, 12, 13, 14, 15, 20, 21, 23, 24, 29, 30, 31, 32, 33, 38, 39, 40, 41, 42});
      ((ArrayList)this.layouts.get(MinionType.HUNTER)).add(new int[]{0, 9, 18, 27, 36, 45, 8, 17, 26, 35, 44, 53});
      ((ArrayList)this.layouts.get(MinionType.HUNTER)).add(new int[]{1, 10, 19, 28, 37, 46, 47, 50, 51, 52, 43, 34, 25, 16, 7});
      this.layouts.put(MinionType.RANCHER, new ArrayList());
      ((ArrayList)this.layouts.get(MinionType.RANCHER)).add(new int[]{2, 3, 4, 5, 6, 11, 12, 13, 14, 15, 20, 21, 23, 24, 29, 30, 31, 32, 33, 38, 39, 40, 41, 42});
      ((ArrayList)this.layouts.get(MinionType.RANCHER)).add(new int[]{0, 9, 18, 27, 36, 45, 8, 17, 26, 35, 44, 53});
      ((ArrayList)this.layouts.get(MinionType.RANCHER)).add(new int[]{1, 10, 19, 28, 37, 46, 47, 50, 51, 52, 43, 34, 25, 16, 7});
      this.layouts.put(MinionType.CACTUSCANE, new ArrayList());
      ((ArrayList)this.layouts.get(MinionType.CACTUSCANE)).add(new int[]{2, 4, 6, 12, 13, 14, 20, 21, 23, 24, 30, 31, 32, 38, 40, 42});
      ((ArrayList)this.layouts.get(MinionType.CACTUSCANE)).add(new int[]{0, 1, 9, 10, 18, 19, 27, 28, 36, 37, 45, 46, 47, 50, 51, 7, 8, 16, 17, 25, 26, 34, 35, 43, 44, 52, 53});
      ((ArrayList)this.layouts.get(MinionType.CACTUSCANE)).add(new int[]{3, 5, 11, 15, 29, 33, 39, 41});
      this.layouts.put(MinionType.COLLECTOR, new ArrayList());
      ((ArrayList)this.layouts.get(MinionType.COLLECTOR)).add(new int[]{2, 3, 4, 5, 6, 11, 12, 13, 14, 15, 20, 21, 23, 24, 29, 30, 31, 32, 33, 38, 39, 40, 41, 42});
      ((ArrayList)this.layouts.get(MinionType.COLLECTOR)).add(new int[]{0, 1, 9, 10, 18, 19, 27, 28, 36, 37, 45, 46, 47, 50, 51, 7, 8, 16, 17, 25, 26, 34, 35, 43, 44, 52, 53});
   }

   public void createShopMenu(Player p) {
      int page = (Integer)this.pages.get(p);
      Inventory inv = Bukkit.createInventory((InventoryHolder)null, 54, this.plugin.getLang().get("menus.shop.title").length() > 32 ? this.plugin.getLang().get("menus.shop.title").substring(0, 32) : this.plugin.getLang().get("menus.shop.title"));
      ItemStack close = ItemBuilder.item(Material.BARRIER, this.plugin.getLang().get("menus.close.nameItem"), this.plugin.getLang().get("menus.close.loreItem"));
      ItemStack next = ItemBuilder.item(Material.ARROW, this.plugin.getLang().get("menus.next.nameItem"), this.plugin.getLang().get("menus.next.loreItem"));
      ItemStack last = ItemBuilder.item(Material.ARROW, this.plugin.getLang().get("menus.last.nameItem"), this.plugin.getLang().get("menus.last.loreItem"));

      for(ShopItem si : this.plugin.getShm().getShop().values()) {
         if (page == si.getPage()) {
            inv.setItem(si.getSlot(), si.toIcon(p));
         }
      }

      if (page > 1) {
         inv.setItem(45, last);
      }

      if (page < this.plugin.getShm().getLastPage()) {
         inv.setItem(53, next);
      }

      inv.setItem(49, close);
      p.openInventory(inv);
   }

   public void createIdealLayout(Player p, PlayerMinion pm) {
      MinionType type = pm.getType();
      Inventory inv = Bukkit.createInventory((InventoryHolder)null, 54, this.plugin.getLang().get("menus.layout.title").length() > 32 ? this.plugin.getLang().get("menus.layout.title").substring(0, 32) : this.plugin.getLang().get("menus.layout.title"));
      ItemStack back = ItemBuilder.item(Material.ARROW, this.plugin.getLang().get("menus.back.nameItem"), this.plugin.getLang().get("menus.back.loreItem").replaceAll("<destiny>", pm.getMinionLevel().getLevelTitle()));
      ItemStack close = ItemBuilder.item(Material.BARRIER, this.plugin.getLang().get("menus.close.nameItem"), this.plugin.getLang().get("menus.close.loreItem"));
      ItemStack black = ItemBuilder.item(Material.BLACK_STAINED_GLASS_PANE, "§7", "§7");
      ItemStack fence = ItemBuilder.item(Material.OAK_FENCE, this.plugin.getLang().get("menus.layout.fence.nameItem"), this.plugin.getLang().get("menus.layout.fence.loreItem"));
      if (type.equals(MinionType.COLLECTOR) || type.equals(MinionType.SELLER)) {
         ItemStack nothing = ItemBuilder.item(Material.WHITE_STAINED_GLASS_PANE, this.plugin.getLang().get("menus.layout.nothing.nameItem"), this.plugin.getLang().get("menus.layout.nothing.loreItem"));

         for(int i : (int[])((ArrayList)this.layouts.get(MinionType.COLLECTOR)).get(0)) {
            inv.setItem(i, nothing);
         }

         for(int i : (int[])((ArrayList)this.layouts.get(MinionType.COLLECTOR)).get(1)) {
            inv.setItem(i, black);
         }
      }

      if (type.equals(MinionType.FISHER)) {
         ItemStack water = ItemBuilder.item(Material.WATER_BUCKET, this.plugin.getLang().get("menus.layout.water.nameItem"), this.plugin.getLang().get("menus.layout.water.loreItem"));

         for(int i : (int[])((ArrayList)this.layouts.get(MinionType.FISHER)).get(0)) {
            inv.setItem(i, water);
         }

         for(int i : (int[])((ArrayList)this.layouts.get(MinionType.FISHER)).get(1)) {
            inv.setItem(i, black);
         }
      }

      if (type.equals(MinionType.MINER)) {
         ItemStack air = ItemBuilder.item(Material.WHITE_STAINED_GLASS_PANE, this.plugin.getLang().get("menus.layout.air.nameItem"), this.plugin.getLang().get("menus.layout.air.loreItem"));

         for(int i : (int[])((ArrayList)this.layouts.get(MinionType.MINER)).get(0)) {
            inv.setItem(i, air);
         }

         for(int i : (int[])((ArrayList)this.layouts.get(MinionType.MINER)).get(1)) {
            inv.setItem(i, black);
         }
      }

      if (type.equals(MinionType.CACTUSCANE)) {
         ItemStack grass = ItemBuilder.item(Material.GRASS_BLOCK, this.plugin.getLang().get("menus.layout.grass.nameItem"), this.plugin.getLang().get("menus.layout.grass.loreItem"));
         ItemStack sand = ItemBuilder.item(Material.SAND, this.plugin.getLang().get("menus.layout.sand.nameItem"), this.plugin.getLang().get("menus.layout.sand.loreItem"));
         ItemStack water = ItemBuilder.item(Material.WATER_BUCKET, this.plugin.getLang().get("menus.layout.water.nameItem"), this.plugin.getLang().get("menus.layout.water.loreItem"));
         if (pm.getMinion().getPlace().getType().equals(Material.CACTUS)) {
            for(int i : (int[])((ArrayList)this.layouts.get(MinionType.CACTUSCANE)).get(0)) {
               inv.setItem(i, sand);
            }

            for(int i : (int[])((ArrayList)this.layouts.get(MinionType.CACTUSCANE)).get(2)) {
               inv.setItem(i, sand);
            }
         } else {
            for(int i : (int[])((ArrayList)this.layouts.get(MinionType.CACTUSCANE)).get(0)) {
               inv.setItem(i, grass);
            }

            for(int i : (int[])((ArrayList)this.layouts.get(MinionType.CACTUSCANE)).get(2)) {
               inv.setItem(i, water);
            }
         }

         for(int i : (int[])((ArrayList)this.layouts.get(MinionType.CACTUSCANE)).get(1)) {
            inv.setItem(i, black);
         }
      }

      if (type.equals(MinionType.LUMBERJACK)) {
         ItemStack grass = ItemBuilder.item(Material.GRASS_BLOCK, this.plugin.getLang().get("menus.layout.grass.nameItem"), this.plugin.getLang().get("menus.layout.grass.loreItem"));

         for(int i : (int[])((ArrayList)this.layouts.get(MinionType.LUMBERJACK)).get(0)) {
            inv.setItem(i, grass);
         }

         for(int i : (int[])((ArrayList)this.layouts.get(MinionType.LUMBERJACK)).get(1)) {
            inv.setItem(i, black);
         }
      }

      if (type.equals(MinionType.FARMER)) {
         ItemStack grass = ItemBuilder.item(Material.GRASS_BLOCK, this.plugin.getLang().get("menus.layout.grass.nameItem"), this.plugin.getLang().get("menus.layout.grass.loreItem"));
         ItemStack soulsand = ItemBuilder.item(Material.SOUL_SAND, this.plugin.getLang().get("menus.layout.soulsand.nameItem"), this.plugin.getLang().get("menus.layout.soulsand.loreItem"));
         if (pm.getMinion().getPlace().getType().equals(Material.NETHER_WART)) {
            for(int i : (int[])((ArrayList)this.layouts.get(MinionType.FARMER)).get(0)) {
               inv.setItem(i, soulsand);
            }
         } else {
            for(int i : (int[])((ArrayList)this.layouts.get(MinionType.FARMER)).get(0)) {
               inv.setItem(i, grass);
            }
         }

         for(int i : (int[])((ArrayList)this.layouts.get(MinionType.FARMER)).get(1)) {
            inv.setItem(i, black);
         }
      }

      if (type.equals(MinionType.PEASANT)) {
         ItemStack grass = ItemBuilder.item(Material.GRASS_BLOCK, this.plugin.getLang().get("menus.layout.grass.nameItem"), this.plugin.getLang().get("menus.layout.grass.loreItem"));

         for(int i : (int[])((ArrayList)this.layouts.get(MinionType.PEASANT)).get(0)) {
            inv.setItem(i, grass);
         }

         for(int i : (int[])((ArrayList)this.layouts.get(MinionType.PEASANT)).get(1)) {
            inv.setItem(i, black);
         }
      }

      if (type.equals(MinionType.HUNTER)) {
         ItemStack grass = ItemBuilder.item(Material.GRASS_BLOCK, this.plugin.getLang().get("menus.layout.air.nameItem"), this.plugin.getLang().get("menus.layout.air.loreItem"));

         for(int i : (int[])((ArrayList)this.layouts.get(MinionType.HUNTER)).get(0)) {
            inv.setItem(i, grass);
         }

         for(int i : (int[])((ArrayList)this.layouts.get(MinionType.HUNTER)).get(1)) {
            inv.setItem(i, black);
         }

         for(int i : (int[])((ArrayList)this.layouts.get(MinionType.HUNTER)).get(2)) {
            inv.setItem(i, fence);
         }
      }

      if (type.equals(MinionType.RANCHER)) {
         ItemStack grass = ItemBuilder.item(Material.GRASS_BLOCK, this.plugin.getLang().get("menus.layout.air.nameItem"), this.plugin.getLang().get("menus.layout.air.loreItem"));

         for(int i : (int[])((ArrayList)this.layouts.get(MinionType.RANCHER)).get(0)) {
            inv.setItem(i, grass);
         }

         for(int i : (int[])((ArrayList)this.layouts.get(MinionType.RANCHER)).get(1)) {
            inv.setItem(i, black);
         }

         for(int i : (int[])((ArrayList)this.layouts.get(MinionType.RANCHER)).get(2)) {
            inv.setItem(i, fence);
         }
      }

      inv.setItem(22, pm.getMinionLevel().getMinionHead());
      inv.setItem(48, back);
      inv.setItem(49, close);
      p.openInventory(inv);
   }

   public void createAdminMinionMenu(Player p, PlayerMinion pm) {
      Minion m = pm.getMinion();
      MinionLevel ml = pm.getMinionLevel();
      MinionLevel mn = ml;
      if (m.getMinionLevelByLevel(pm.getStat().getLevel() + 1) != null) {
         mn = m.getMinionLevelByLevel(pm.getStat().getLevel() + 1);
      }

      int slot = Utils.getMaxSlots(pm);
      Inventory inv = Bukkit.createInventory((InventoryHolder)null, 54, this.plugin.getLang().get("menus.minion.adminTitle"));
      this.fillInventoryMinion(p, pm, m, ml, mn, slot, inv);
   }

   public void createMinionMenu(Player p, PlayerMinion pm) {
      Minion m = pm.getMinion();
      MinionLevel ml = pm.getMinionLevel();
      MinionLevel mn = ml;
      if (m.getMinionLevelByLevel(pm.getStat().getLevel() + 1) != null) {
         mn = m.getMinionLevelByLevel(pm.getStat().getLevel() + 1);
      }

      int slot = Utils.getMaxSlots(pm);
      String level = pm.getMinionLevel().getLevelTitle();
      Inventory inv = Bukkit.createInventory((InventoryHolder)null, 54, level.length() > 32 ? level.substring(0, 32) : level);
      this.fillInventoryMinion(p, pm, m, ml, mn, slot, inv);
   }

   private void fillInventoryMinion(Player p, PlayerMinion pm, Minion m, MinionLevel ml, MinionLevel mn, int slot, Inventory inv) {
      ItemStack black = ItemBuilder.item(Material.BLACK_STAINED_GLASS_PANE, 1, (short)15, "§7", "§7");
      ItemStack white = ItemBuilder.item(Material.WHITE_STAINED_GLASS_PANE, 1, (short)0, this.plugin.getLang().get("menus.minion.blockedSlot.nameItem"), this.plugin.getLang().get("menus.minion.blockedSlot.loreItem"));
      ItemStack chest = ItemBuilder.item(Material.WHITE_STAINED_GLASS_PANE, 1, (short)0, this.plugin.getLang().get("menus.minion.chestLinked.nameItem"), this.plugin.getLang().get("menus.minion.chestLinked.loreItem"));
      ItemStack layout = ItemBuilder.item(Material.REDSTONE_TORCH, 1, (short)0, this.plugin.getLang().get("menus.minion.layout.nameItem"), this.plugin.getLang().get("menus.minion.layout.loreItem"));
      ItemStack info = ItemBuilder.createSkull(this.plugin.getLang().get("menus.minion.info.nameItem").replaceAll("<type>", pm.getMinionLevel().getLevelTitle()), this.getInfoLore(pm), ml.getUrl());
      ItemStack next = ItemBuilder.item(Material.GOLD_INGOT, 1, (short)0, this.plugin.getLang().get("menus.minion.next.nameItem"), this.plugin.getLang().get("menus.minion.next.loreItem").replaceAll("<slotsNow>", String.valueOf(ml.getMax())).replaceAll("<timeNow>", String.valueOf(ml.getDelay())).replaceAll("<slotsNext>", mn.equals(ml) ? "§cMax" : String.valueOf(mn.getMax())).replaceAll("<timeNext>", mn.equals(ml) ? "§cMax" : String.valueOf(mn.getDelay())).replaceAll("<level>", mn.equals(ml) ? "§cMax" : Utils.IntegerToRomanNumeral(mn.getLevel())));
      ItemStack skin = ItemBuilder.item(Material.GREEN_STAINED_GLASS_PANE, 1, (short)5, this.plugin.getLang().get("menus.minion.skin.nameItem"), this.plugin.getLang().get("menus.minion.skin.loreItem"));
      ItemStack fuel = ItemBuilder.item(Material.ORANGE_STAINED_GLASS_PANE, 1, (short)1, this.plugin.getLang().get("menus.minion.fuel.nameItem"), this.plugin.getLang().get("menus.minion.fuel.loreItem"));
      ItemStack shipping = ItemBuilder.item(Material.BLUE_STAINED_GLASS_PANE, 1, (short)11, this.plugin.getLang().get("menus.minion.shipping.nameItem"), this.plugin.getLang().get("menus.minion.shipping.loreItem"));
      ItemStack update = ItemBuilder.item(Material.YELLOW_STAINED_GLASS_PANE, 1, (short)4, this.plugin.getLang().get("menus.minion.update.nameItem"), this.plugin.getLang().get("menus.minion.update.loreItem"));
      ItemStack collect = ItemBuilder.item(Material.CHEST, 1, (short)0, this.plugin.getLang().get("menus.minion.collect.nameItem"), this.plugin.getLang().get("menus.minion.collect.loreItem"));
      ItemStack pickup = ItemBuilder.item(Material.BEDROCK, 1, (short)0, this.plugin.getLang().get("menus.minion.pickup.nameItem"), this.plugin.getLang().get("menus.minion.pickup.loreItem"));
      this.fill.forEach((f) -> inv.setItem(f, black));
      if (!ml.equals(mn)) {
         boolean has = false;
         Craft craft = mn.getCraft();
         if (craft != null) {
            for(ItemStack i : craft.getAtLeast().keySet()) {
               if (i != null && !i.getType().equals(Material.AIR)) {
                  int amo = (Integer)craft.getAtLeast().get(i);
                  if (!p.getInventory().containsAtLeast(i, amo)) {
                     has = false;
                     break;
                  }

                  has = true;
               }
            }

            ItemStack quick = ItemBuilder.item(Material.DIAMOND, 1, (short)0, this.plugin.getLang().get("menus.minion.quick.nameItem"), this.plugin.getLang().get("menus.minion.quick.loreItem").replaceAll("<status>", has ? this.plugin.getLang().get("menus.minion.quick.has") : this.plugin.getLang().get("menus.minion.quick.noHas")).replaceAll("<slotsNow>", String.valueOf(mn.getMax())).replaceAll("<timeNow>", String.valueOf(mn.getDelay())).replaceAll("<slotsNext>", mn.equals(ml) ? "§cMax" : String.valueOf(ml.getMax())).replaceAll("<timeNext>", mn.equals(ml) ? "§cMax" : String.valueOf(ml.getDelay())));
            inv.setItem(50, quick);
         }
      }

      boolean special = pm.getType().equals(MinionType.COLLECTOR) || pm.getType().equals(MinionType.SELLER);
      if (!special) {
         for(int i = 0; i < this.inv.size(); ++i) {
            if (i >= slot) {
               inv.setItem((Integer)this.inv.get(i), white);
            }
         }
      } else {
         for(int integer : this.inv) {
            inv.setItem(integer, chest);
         }
      }

      if (!pm.getSkin().equals("none") && this.plugin.getSkm().getSkins().get(pm.getSkin()) != null) {
         inv.setItem(10, ((MinionSkin)this.plugin.getSkm().getSkins().get(pm.getSkin())).getHead());
      } else {
         inv.setItem(10, skin);
      }

      if (!m.isFuelEnabled()) {
         inv.setItem(19, black);
      } else if (pm.getUpgrade().getFuel() != null) {
         boolean ended = this.ended(pm.getStat(), pm.getUpgrade().getFuel());
         if (ended) {
            inv.setItem(19, fuel);
         } else {
            ItemStack i = (ItemStack)NBTEditor.set(pm.getUpgrade().getFuel().getResult(true, this.getTime(pm.getStat(), pm.getUpgrade().getFuel())), pm.getUpgrade().getFuel().getName(), "MINION", "FUEL", "TIME");
            i.setAmount(pm.getStat().getAmountFuel(pm.getUpgrade().getFuel()));
            inv.setItem(19, i);
         }
      } else {
         inv.setItem(19, fuel);
      }

      if (!m.isAutoSellEnabled()) {
         inv.setItem(28, black);
      } else if (pm.getUpgrade().getAutoSell() != null) {
         inv.setItem(28, pm.getUpgrade().getAutoSell().getResult());
      } else {
         inv.setItem(28, shipping);
      }

      if (!m.isCompressorEnabled()) {
         inv.setItem(37, black);
      } else if (pm.getUpgrade().getCompressor() != null) {
         inv.setItem(37, pm.getUpgrade().getCompressor().getResult());
      } else {
         inv.setItem(37, update);
      }

      if (!m.isSmeltEnabled()) {
         inv.setItem(46, black);
      } else if (pm.getUpgrade().getAutoSmelt() != null) {
         inv.setItem(46, pm.getUpgrade().getAutoSmelt().getResult());
      } else {
         inv.setItem(46, update);
      }

      inv.setItem(3, layout);
      inv.setItem(4, info);
      inv.setItem(5, next);
      inv.setItem(48, collect);
      inv.setItem(53, pickup);
      if (!special) {
         for(Map.Entry<ItemStack, Integer> entry : pm.getItems().entrySet()) {
            if (entry.getKey() != null && !((ItemStack)entry.getKey()).getType().equals(Material.AIR)) {
               int stack = (Integer)entry.getValue() / 64;
               int rest = (Integer)entry.getValue() % 64;

               for(int i = 0; i < stack; ++i) {
                  ItemStack item = ((ItemStack)entry.getKey()).clone();
                  item.setAmount(64);
                  inv.addItem(new ItemStack[]{item});
               }

               if (rest > 0) {
                  ItemStack add = ((ItemStack)entry.getKey()).clone();
                  add.setAmount(rest);
                  inv.addItem(new ItemStack[]{add});
               }
            }
         }
      }

      p.openInventory(inv);
   }

   public void createMinionLevelMenu(Player p, PlayerMinion pm, MinionLevel next) {
      Inventory inv = Bukkit.createInventory((InventoryHolder)null, 27, this.plugin.getLang().get("menus.upgrade.title").replaceAll("<title>", pm.getMinion().getTitle()));
      ItemStack level = ItemBuilder.item(Material.EXPERIENCE_BOTTLE, this.plugin.getLang().get("menus.upgrade.level.nameItem"), this.plugin.getLang().get("menus.upgrade.level.loreItem").replaceAll("<amount>", String.valueOf(next.getUpgradeLevels())).replaceAll("<level>", Utils.IntegerToRomanNumeral(next.getLevel())));
      ItemStack coins = ItemBuilder.item(Material.SUNFLOWER, this.plugin.getLang().get("menus.upgrade.coins.nameItem"), this.plugin.getLang().get("menus.upgrade.coins.loreItem").replaceAll("<amount>", String.valueOf(next.getUpgradeCoins())).replaceAll("<level>", Utils.IntegerToRomanNumeral(next.getLevel())));
      ItemStack craft = ItemBuilder.item(Material.CRAFTING_TABLE, this.plugin.getLang().get("menus.upgrade.craft.nameItem"), this.plugin.getLang().get("menus.upgrade.craft.loreItem"));
      int u = next.getUpgrades();
      if (u == 3) {
         inv.setItem(this.m1m1m1[0], level);
         inv.setItem(this.m1m1m1[1], coins);
         inv.setItem(this.m1m1m1[2], craft);
      } else if (u == 2) {
         if (next.isCoins() && next.isLevel()) {
            inv.setItem(this.m1m1[0], coins);
            inv.setItem(this.m1m1[1], level);
         } else if (next.isCoins() && next.isCraft()) {
            inv.setItem(this.m1m1[0], coins);
            inv.setItem(this.m1m1[1], craft);
         } else {
            inv.setItem(this.m1m1[0], level);
            inv.setItem(this.m1m1[1], craft);
         }
      } else if (next.isCoins()) {
         inv.setItem(this.m1[0], coins);
      } else if (next.isLevel()) {
         inv.setItem(this.m1[0], level);
      } else {
         inv.setItem(this.m1[0], craft);
      }

      p.openInventory(inv);
   }

   public void createPreviewCraftMenu(Player p, PlayerMinion pm, MinionLevel l) {
      Inventory inv = Bukkit.createInventory((InventoryHolder)null, 54, this.plugin.getLang().get("menus.craft.title").replaceAll("<title>", pm.getMinion().getTitle()));
      ItemStack orange = ItemBuilder.item(Material.ORANGE_STAINED_GLASS_PANE, 1, (short)15, "§7", "§7");
      ItemStack upgrade = ItemBuilder.item(Material.SUNFLOWER, 1, (short)0, this.plugin.getLang().get("menus.craft.upgrade.nameItem"), this.plugin.getLang().get("menus.craft.upgrade.loreItem"));
      Craft craft = l.getCraft();

      for(int i = 0; i < 54; ++i) {
         if (!this.workbench.contains(i)) {
            inv.setItem(i, orange);
         }
      }

      ItemStack[] m = craft.getMatrix();
      inv.setItem(12, m[0]);
      inv.setItem(13, m[1]);
      inv.setItem(14, m[2]);
      inv.setItem(21, m[3]);
      inv.setItem(22, m[4]);
      inv.setItem(23, m[5]);
      inv.setItem(30, m[6]);
      inv.setItem(31, m[7]);
      inv.setItem(32, m[8]);
      inv.setItem(49, upgrade);
      p.openInventory(inv);
   }

   public void createMinionStat(Player p, PlayerMinion pm) {
      Inventory inv = Bukkit.createInventory((InventoryHolder)null, 27, this.plugin.getLang().get("menus.stat.title"));
      ItemStack chest;
      if (pm.getChest() != null) {
         chest = ItemBuilder.item(Material.CHEST, this.plugin.getLang().get("menus.stat.chest.nameItem"), this.plugin.getLang().get("menus.stat.chest.loreItem").replaceAll("<chest>", Utils.getFormatedLocation(pm.getChest().getLoc())));
      } else {
         chest = ItemBuilder.item(Material.CHEST, this.plugin.getLang().get("menus.stat.chest.nameItem"), this.plugin.getLang().get("menus.stat.chest.loreItem").replaceAll("<chest>", "§cNot set!"));
      }

      if (this.plugin.getCfm().isFood()) {
         inv.setItem(11, ItemBuilder.item(Material.BREAD, this.plugin.getLang().get("menus.stat.food.nameItem"), this.plugin.getLang().get("menus.stat.food.loreItem").replaceAll("<max>", String.valueOf(pm.getMinionLevel().getFood())).replaceAll("<food>", String.valueOf(pm.getStat().getFood()))));
      }

      if (this.plugin.getCfm().isHealth()) {
         inv.setItem(15, ItemBuilder.item(Material.APPLE, this.plugin.getLang().get("menus.stat.health.nameItem"), this.plugin.getLang().get("menus.stat.health.loreItem").replaceAll("<max>", String.valueOf(pm.getMinionLevel().getHealth())).replaceAll("<health>", String.valueOf(pm.getStat().getHealth()))));
      }

      if (this.plugin.getCfm().isChestLink()) {
         inv.setItem(13, chest);
      }

      p.openInventory(inv);
   }

   public boolean isInv(int slot) {
      return this.inv.contains(slot);
   }

   public String getTime(PlayerMinionStat stat, UpgradeFuel uf) {
      long passed = System.currentTimeMillis() - stat.getFuel();
      long restant = uf.getDuration() * (long)stat.getAmountFuel(uf) - passed;
      int seconds = (int)restant / 1000;
      return Utils.convertTime(seconds);
   }

   public boolean ended(PlayerMinionStat stat, UpgradeFuel uf) {
      long passed = System.currentTimeMillis() - stat.getFuel();
      long restant = uf.getDuration() * (long)stat.getAmountFuel(uf) - passed;
      return restant < 0L;
   }

   private List<String> getInfoLore(PlayerMinion pm) {
      Minion m = pm.getMinion();
      MinionLevel ml = pm.getMinionLevel();
      List<String> lore = new ArrayList();

      for(String s : this.plugin.getLang().get("menus.minion.info.loreItem").split("\\n")) {
         if (s.equalsIgnoreCase("<lore>")) {
            m.getLore().forEach((l) -> lore.add(l.replaceAll("<type>", ml.getLevelTitle()).replaceAll("<generated>", String.valueOf(pm.getStat().getGenerated())).replaceAll("<max>", String.valueOf(ml.getMax())).replaceAll("<time>", String.valueOf(pm.getDelay()))));
         } else {
            lore.add(s.replaceAll("<type>", ml.getLevelTitle()).replaceAll("<generated>", String.valueOf(pm.getStat().getGenerated())).replaceAll("<max>", String.valueOf(ml.getMax())).replaceAll("<time>", String.valueOf(pm.getDelay())));
         }
      }

      return lore;
   }

   public HashMap<Player, Integer> getPages() {
      return this.pages;
   }

   public void addPage(Player p) {
      this.pages.put(p, (Integer)this.pages.get(p) + 1);
   }

   public void removePage(Player p) {
      this.pages.put(p, (Integer)this.pages.get(p) - 1);
   }
}
