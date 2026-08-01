package io.github.Leonardo0013YT.UltraMinions.listeners;

import io.github.Leonardo0013YT.UltraMinions.Main;
import io.github.Leonardo0013YT.UltraMinions.database.PlayerMinion;
import io.github.Leonardo0013YT.UltraMinions.database.minion.PlayerMinionChest;
import io.github.Leonardo0013YT.UltraMinions.enums.MinionType;
import io.github.Leonardo0013YT.UltraMinions.food.Food;
import io.github.Leonardo0013YT.UltraMinions.minions.Minion;
import io.github.Leonardo0013YT.UltraMinions.setup.SetupAutoSell;
import io.github.Leonardo0013YT.UltraMinions.setup.SetupAutoSmelt;
import io.github.Leonardo0013YT.UltraMinions.setup.SetupCompressor;
import io.github.Leonardo0013YT.UltraMinions.setup.SetupCraft;
import io.github.Leonardo0013YT.UltraMinions.setup.SetupFood;
import io.github.Leonardo0013YT.UltraMinions.setup.SetupFuel;
import io.github.Leonardo0013YT.UltraMinions.setup.SetupMinion;
import io.github.Leonardo0013YT.UltraMinions.setup.SetupMinionLevel;
import io.github.Leonardo0013YT.UltraMinions.utils.NBTEditor;
import io.github.Leonardo0013YT.UltraMinions.utils.Utils;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.inventory.InventoryType.SlotType;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class SetupListener implements Listener {
   private Main plugin;

   public SetupListener(Main plugin) {
      this.plugin = plugin;
   }

   @EventHandler
   public void onInteract(PlayerInteractEvent e) {
      Player p = e.getPlayer();
      if (this.plugin.getSm().isSetupChest(p) && e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
         Block b = e.getClickedBlock();
         if (b.getType().equals(Material.CHEST)) {
            e.setCancelled(true);
            PlayerMinion pm = this.plugin.getSm().getSetupChest(p);
            pm.setChest(true);
            pm.setChest(new PlayerMinionChest(b.getLocation()));
            p.sendMessage(this.plugin.getLang().get("messages.linked"));
            this.plugin.getSm().removeSetupChest(p);
         }
      }

   }

   @EventHandler
   public void onChat(AsyncPlayerChatEvent e) {
      Player p = e.getPlayer();
      if (this.plugin.getSm().isSetupName(p)) {
         String type = this.plugin.getSm().getSetupName(p);
         SetupMinion sm = this.plugin.getSm().getSetupMinion(p);
         if (type.equals("minionPriceCompressor")) {
            double price;
            try {
               price = Double.parseDouble(e.getMessage());
            } catch (NumberFormatException var30) {
               p.sendMessage(this.plugin.getLang().get("setup.writeNumber"));
               return;
            }

            sm.setPriceCompressedSell(price);
            p.sendMessage(this.plugin.getLang().get("setup.setCompressorPrice"));
            this.plugin.getServer().getScheduler().scheduleSyncDelayedTask(this.plugin, () -> this.plugin.getSem().createSetupMinionMenu(p, sm));
            e.setCancelled(true);
            this.plugin.getSm().removeSetupName(p);
         }

         if (type.equals("minionPriceSmelt")) {
            double price;
            try {
               price = Double.parseDouble(e.getMessage());
            } catch (NumberFormatException var29) {
               p.sendMessage(this.plugin.getLang().get("setup.writeNumber"));
               return;
            }

            sm.setPriceSmeltedSell(price);
            p.sendMessage(this.plugin.getLang().get("setup.setSmeltPrice"));
            this.plugin.getServer().getScheduler().scheduleSyncDelayedTask(this.plugin, () -> this.plugin.getSem().createSetupMinionMenu(p, sm));
            e.setCancelled(true);
            this.plugin.getSm().removeSetupName(p);
         }

         if (type.equals("minionPriceNormal")) {
            double price;
            try {
               price = Double.parseDouble(e.getMessage());
            } catch (NumberFormatException var28) {
               p.sendMessage(this.plugin.getLang().get("setup.writeNumber"));
               return;
            }

            sm.setPriceNormalSell(price);
            p.sendMessage(this.plugin.getLang().get("setup.setNormalPrice"));
            this.plugin.getServer().getScheduler().scheduleSyncDelayedTask(this.plugin, () -> this.plugin.getSem().createSetupMinionMenu(p, sm));
            e.setCancelled(true);
            this.plugin.getSm().removeSetupName(p);
         }

         if (type.equals("minionDelay")) {
            SetupMinionLevel sml = sm.getActual();

            int delay;
            try {
               delay = Integer.parseInt(e.getMessage());
            } catch (NumberFormatException var27) {
               p.sendMessage(this.plugin.getLang().get("setup.writeNumber"));
               return;
            }

            sml.setDelay(delay);
            p.sendMessage(this.plugin.getLang().get("setup.setDelay"));
            this.plugin.getServer().getScheduler().scheduleSyncDelayedTask(this.plugin, () -> this.plugin.getSem().createSetupMinionLevelMenu(p, sml));
            e.setCancelled(true);
            this.plugin.getSm().removeSetupName(p);
         }

         if (type.equals("minionMax")) {
            SetupMinionLevel sml = sm.getActual();

            int max;
            try {
               max = Integer.parseInt(e.getMessage());
            } catch (NumberFormatException var26) {
               p.sendMessage(this.plugin.getLang().get("setup.writeNumber"));
               return;
            }

            sml.setMax(max);
            p.sendMessage(this.plugin.getLang().get("setup.setMax"));
            this.plugin.getServer().getScheduler().scheduleSyncDelayedTask(this.plugin, () -> this.plugin.getSem().createSetupMinionLevelMenu(p, sml));
            e.setCancelled(true);
            this.plugin.getSm().removeSetupName(p);
         }

         if (type.equals("minionLevel")) {
            SetupMinionLevel sml = sm.getActual();

            int level;
            try {
               level = Integer.parseInt(e.getMessage());
            } catch (NumberFormatException var25) {
               p.sendMessage(this.plugin.getLang().get("setup.writeNumber"));
               return;
            }

            sml.setUpgradeLevel(level);
            p.sendMessage(this.plugin.getLang().get("setup.setLevel"));
            this.plugin.getServer().getScheduler().scheduleSyncDelayedTask(this.plugin, () -> this.plugin.getSem().createSetupMinionLevelMenu(p, sml));
            e.setCancelled(true);
            this.plugin.getSm().removeSetupName(p);
         }

         if (type.equals("minionCoins")) {
            SetupMinionLevel sml = sm.getActual();

            int coins;
            try {
               coins = Integer.parseInt(e.getMessage());
            } catch (NumberFormatException var24) {
               p.sendMessage(this.plugin.getLang().get("setup.writeNumber"));
               return;
            }

            sml.setUpgradeCoins(coins);
            p.sendMessage(this.plugin.getLang().get("setup.setCoins"));
            this.plugin.getServer().getScheduler().scheduleSyncDelayedTask(this.plugin, () -> this.plugin.getSem().createSetupMinionLevelMenu(p, sml));
            e.setCancelled(true);
            this.plugin.getSm().removeSetupName(p);
         }

         if (type.equals("craftPermission")) {
            SetupMinionLevel sml = sm.getActual();
            SetupCraft sc = sml.getCraft();
            sc.setPermission(e.getMessage());
            p.sendMessage(this.plugin.getLang().get("setup.setPermission"));
            this.plugin.getServer().getScheduler().scheduleSyncDelayedTask(this.plugin, () -> this.plugin.getSem().createSetupCraftMenu(p));
            e.setCancelled(true);
            this.plugin.getSm().removeSetupName(p);
         }

         if (type.equals("minionHealth")) {
            SetupMinionLevel sml = sm.getActual();

            int health;
            try {
               health = Integer.parseInt(e.getMessage());
            } catch (NumberFormatException var23) {
               p.sendMessage(this.plugin.getLang().get("setup.writeNumber"));
               return;
            }

            sml.setHealth(health);
            p.sendMessage(this.plugin.getLang().get("setup.setHealth"));
            this.plugin.getServer().getScheduler().scheduleSyncDelayedTask(this.plugin, () -> this.plugin.getSem().createSetupMinionLevelMenu(p, sml));
            e.setCancelled(true);
            this.plugin.getSm().removeSetupName(p);
         }

         if (type.equals("minionFood")) {
            SetupMinionLevel sml = sm.getActual();

            int food;
            try {
               food = Integer.parseInt(e.getMessage());
            } catch (NumberFormatException var22) {
               p.sendMessage(this.plugin.getLang().get("setup.writeNumber"));
               return;
            }

            sml.setFood(food);
            p.sendMessage(this.plugin.getLang().get("setup.setFood"));
            this.plugin.getServer().getScheduler().scheduleSyncDelayedTask(this.plugin, () -> this.plugin.getSem().createSetupMinionLevelMenu(p, sml));
            e.setCancelled(true);
            this.plugin.getSm().removeSetupName(p);
         }

         if (type.equals("minionWorkTime")) {
            SetupMinionLevel sml = sm.getActual();

            int workTime;
            try {
               workTime = Integer.parseInt(e.getMessage());
            } catch (NumberFormatException var21) {
               p.sendMessage(this.plugin.getLang().get("setup.writeNumber"));
               return;
            }

            sml.setWorkTime(workTime);
            p.sendMessage(this.plugin.getLang().get("setup.setWorkTime"));
            this.plugin.getServer().getScheduler().scheduleSyncDelayedTask(this.plugin, () -> this.plugin.getSem().createSetupMinionLevelMenu(p, sml));
            e.setCancelled(true);
            this.plugin.getSm().removeSetupName(p);
         }

         if (type.equals("minionSleep")) {
            SetupMinionLevel sml = sm.getActual();

            int sleep;
            try {
               sleep = Integer.parseInt(e.getMessage());
            } catch (NumberFormatException var20) {
               p.sendMessage(this.plugin.getLang().get("setup.writeNumber"));
               return;
            }

            sml.setSleep(sleep);
            p.sendMessage(this.plugin.getLang().get("setup.setSleep"));
            this.plugin.getServer().getScheduler().scheduleSyncDelayedTask(this.plugin, () -> this.plugin.getSem().createSetupMinionLevelMenu(p, sml));
            e.setCancelled(true);
            this.plugin.getSm().removeSetupName(p);
         }

         if (type.equals("minionTitle")) {
            sm.setTitle(e.getMessage().replaceAll("&", "§"));
            p.sendMessage(this.plugin.getLang().get("setup.setTitle"));
            this.plugin.getServer().getScheduler().scheduleSyncDelayedTask(this.plugin, () -> this.plugin.getSem().createSetupMinionMenu(p, sm));
            e.setCancelled(true);
            this.plugin.getSm().removeSetupName(p);
         }

         if (type.equals("minionRed")) {
            int red;
            try {
               red = Integer.parseInt(e.getMessage());
            } catch (NumberFormatException var19) {
               p.sendMessage(this.plugin.getLang().get("setup.writeNumber"));
               return;
            }

            sm.setRed(red);
            p.sendMessage(this.plugin.getLang().get("setup.setRed"));
            this.plugin.getServer().getScheduler().scheduleSyncDelayedTask(this.plugin, () -> this.plugin.getSem().createSetupMinionMenu(p, sm));
            e.setCancelled(true);
            this.plugin.getSm().removeSetupName(p);
         }

         if (type.equals("minionBlue")) {
            int red;
            try {
               red = Integer.parseInt(e.getMessage());
            } catch (NumberFormatException var18) {
               p.sendMessage(this.plugin.getLang().get("setup.writeNumber"));
               return;
            }

            sm.setBlue(red);
            p.sendMessage(this.plugin.getLang().get("setup.setBlue"));
            this.plugin.getServer().getScheduler().scheduleSyncDelayedTask(this.plugin, () -> this.plugin.getSem().createSetupMinionMenu(p, sm));
            e.setCancelled(true);
            this.plugin.getSm().removeSetupName(p);
         }

         if (type.equals("minionGreen")) {
            int green;
            try {
               green = Integer.parseInt(e.getMessage());
            } catch (NumberFormatException var17) {
               p.sendMessage(this.plugin.getLang().get("setup.writeNumber"));
               return;
            }

            sm.setGreen(green);
            p.sendMessage(this.plugin.getLang().get("setup.setGreen"));
            this.plugin.getServer().getScheduler().scheduleSyncDelayedTask(this.plugin, () -> this.plugin.getSem().createSetupMinionMenu(p, sm));
            e.setCancelled(true);
            this.plugin.getSm().removeSetupName(p);
         }

         if (type.equals("autoSellPercent")) {
            SetupAutoSell sas = this.plugin.getSm().getSetupAutoSell(p);

            double percent;
            try {
               percent = Double.parseDouble(e.getMessage());
            } catch (NumberFormatException var16) {
               p.sendMessage(this.plugin.getLang().get("setup.writeNumber"));
               return;
            }

            sas.setSell(percent);
            p.sendMessage(this.plugin.getLang().get("setup.setPercent"));
            this.plugin.getServer().getScheduler().scheduleSyncDelayedTask(this.plugin, () -> this.plugin.getSem().createSetupAutoSellMenu(p, sas));
            e.setCancelled(true);
            this.plugin.getSm().removeSetupName(p);
         }

         if (type.equals("autoSellCraftPermission")) {
            SetupAutoSell sas = this.plugin.getSm().getSetupAutoSell(p);
            SetupCraft sc = sas.getCraft();
            sc.setPermission(e.getMessage());
            p.sendMessage(this.plugin.getLang().get("setup.setPermission"));
            this.plugin.getServer().getScheduler().scheduleSyncDelayedTask(this.plugin, () -> this.plugin.getSem().createSetupAutoSellCraftMenu(p, sas));
            e.setCancelled(true);
            this.plugin.getSm().removeSetupName(p);
         }

         if (type.equals("autoSmeltCraftPermission")) {
            SetupAutoSmelt sas = this.plugin.getSm().getSetupAutoSmelt(p);
            SetupCraft sc = sas.getCraft();
            sc.setPermission(e.getMessage());
            p.sendMessage(this.plugin.getLang().get("setup.setPermission"));
            this.plugin.getServer().getScheduler().scheduleSyncDelayedTask(this.plugin, () -> this.plugin.getSem().createSetupAutoSmeltCraftMenu(p, sas));
            e.setCancelled(true);
            this.plugin.getSm().removeSetupName(p);
         }

         if (type.equals("compressorCraftPermission")) {
            SetupCompressor sas = this.plugin.getSm().getSetupCompressor(p);
            SetupCraft sc = sas.getCraft();
            sc.setPermission(e.getMessage());
            p.sendMessage(this.plugin.getLang().get("setup.setPermission"));
            this.plugin.getServer().getScheduler().scheduleSyncDelayedTask(this.plugin, () -> this.plugin.getSem().createSetupCompressorCraftMenu(p, sas));
            e.setCancelled(true);
            this.plugin.getSm().removeSetupName(p);
         }

         if (type.equals("autoSellCraftPermission")) {
            SetupFuel sas = this.plugin.getSm().getSetupFuel(p);
            SetupCraft sc = sas.getCraft();
            sc.setPermission(e.getMessage());
            p.sendMessage(this.plugin.getLang().get("setup.setPermission"));
            this.plugin.getServer().getScheduler().scheduleSyncDelayedTask(this.plugin, () -> this.plugin.getSem().createSetupFuelCraftMenu(p, sas));
            e.setCancelled(true);
            this.plugin.getSm().removeSetupName(p);
         }

         if (type.equals("minionKey")) {
            sm.setKey(e.getMessage());
            p.sendMessage(this.plugin.getLang().get("setup.setPermission"));
            this.plugin.getServer().getScheduler().scheduleSyncDelayedTask(this.plugin, () -> this.plugin.getSem().createSetupMinionMenu(p, sm));
            e.setCancelled(true);
            this.plugin.getSm().removeSetupName(p);
         }

         if (type.equals("minionLore")) {
            sm.getLore().add(e.getMessage());
            p.sendMessage(this.plugin.getLang().get("setup.addLore"));
            this.plugin.getServer().getScheduler().scheduleSyncDelayedTask(this.plugin, () -> this.plugin.getSem().createSetupMinionMenu(p, sm));
            e.setCancelled(true);
            this.plugin.getSm().removeSetupName(p);
         }

         if (type.equals("autoSmeltPercent")) {
            SetupAutoSmelt sas = this.plugin.getSm().getSetupAutoSmelt(p);

            double percent;
            try {
               percent = Double.parseDouble(e.getMessage());
            } catch (NumberFormatException var15) {
               p.sendMessage(this.plugin.getLang().get("setup.writeNumber"));
               return;
            }

            sas.setPercent(percent);
            p.sendMessage(this.plugin.getLang().get("setup.autosmelt.setPercent"));
            this.plugin.getServer().getScheduler().scheduleSyncDelayedTask(this.plugin, () -> this.plugin.getSem().createSetupAutoSmeltMenu(p, sas));
            e.setCancelled(true);
            this.plugin.getSm().removeSetupName(p);
         }

         if (type.equals("autoSellPercent")) {
            SetupAutoSell sas = this.plugin.getSm().getSetupAutoSell(p);

            double percent;
            try {
               percent = Double.parseDouble(e.getMessage());
            } catch (NumberFormatException var14) {
               p.sendMessage(this.plugin.getLang().get("setup.writeNumber"));
               return;
            }

            sas.setSell(percent);
            p.sendMessage(this.plugin.getLang().get("setup.autosell.setPercent"));
            this.plugin.getServer().getScheduler().scheduleSyncDelayedTask(this.plugin, () -> this.plugin.getSem().createSetupAutoSellMenu(p, sas));
            e.setCancelled(true);
            this.plugin.getSm().removeSetupName(p);
         }

         if (type.equals("compressorAmount")) {
            SetupCompressor sas = this.plugin.getSm().getSetupCompressor(p);

            int amount;
            try {
               amount = Integer.parseInt(e.getMessage());
            } catch (NumberFormatException var13) {
               p.sendMessage(this.plugin.getLang().get("setup.writeNumber"));
               return;
            }

            sas.setAmount(amount);
            p.sendMessage(this.plugin.getLang().get("setup.compressor.setAmount"));
            this.plugin.getServer().getScheduler().scheduleSyncDelayedTask(this.plugin, () -> this.plugin.getSem().createSetupCompressorMenu(p, sas));
            e.setCancelled(true);
            this.plugin.getSm().removeSetupName(p);
         }

         if (type.equals("fuelPercent")) {
            SetupFuel sas = this.plugin.getSm().getSetupFuel(p);

            double percent;
            try {
               percent = Double.parseDouble(e.getMessage());
            } catch (NumberFormatException var12) {
               p.sendMessage(this.plugin.getLang().get("setup.writeNumber"));
               return;
            }

            sas.setPercent(percent);
            p.sendMessage(this.plugin.getLang().get("setup.fuel.setPercent"));
            this.plugin.getServer().getScheduler().scheduleSyncDelayedTask(this.plugin, () -> this.plugin.getSem().createSetupFuelMenu(p, sas));
            e.setCancelled(true);
            this.plugin.getSm().removeSetupName(p);
         }

         if (type.equals("fuelDuration")) {
            SetupFuel sas = this.plugin.getSm().getSetupFuel(p);

            int seconds;
            try {
               seconds = Integer.parseInt(e.getMessage());
            } catch (NumberFormatException var11) {
               p.sendMessage(this.plugin.getLang().get("setup.writeNumber"));
               return;
            }

            sas.setDuration(seconds);
            p.sendMessage(this.plugin.getLang().get("setup.fuel.setPercent"));
            this.plugin.getServer().getScheduler().scheduleSyncDelayedTask(this.plugin, () -> this.plugin.getSem().createSetupFuelMenu(p, sas));
            e.setCancelled(true);
            this.plugin.getSm().removeSetupName(p);
         }

         if (type.equals("minionType")) {
            List<String> types = new ArrayList();

            for(MinionType value : MinionType.values()) {
               types.add(value.name());
            }

            if (!types.contains(e.getMessage().toUpperCase())) {
               p.sendMessage(this.plugin.getLang().get("setup.thisNotWork"));
               return;
            }

            sm.setType(MinionType.valueOf(e.getMessage().toUpperCase()));
            p.sendMessage(this.plugin.getLang().get("setup.setType"));
            this.plugin.getServer().getScheduler().scheduleSyncDelayedTask(this.plugin, () -> this.plugin.getSem().createSetupMinionMenu(p, sm));
            e.setCancelled(true);
            this.plugin.getSm().removeSetupName(p);
         }

         if (type.equals("minionSpawn")) {
            List<String> types = new ArrayList();

            for(EntityType value : EntityType.values()) {
               types.add(value.name());
            }

            if (!types.contains(e.getMessage().toUpperCase())) {
               p.sendMessage(this.plugin.getLang().get("setup.thisNotType"));
               return;
            }

            sm.setSpawn(EntityType.valueOf(e.getMessage().toUpperCase()));
            p.sendMessage(this.plugin.getLang().get("setup.setEntity"));
            this.plugin.getServer().getScheduler().scheduleSyncDelayedTask(this.plugin, () -> this.plugin.getSem().createSetupMinionMenu(p, sm));
            e.setCancelled(true);
            this.plugin.getSm().removeSetupName(p);
         }

         if (type.equals("foodAmount")) {
            SetupFood sf = this.plugin.getSm().getSetupFood(p);

            int amount;
            try {
               amount = Integer.parseInt(e.getMessage());
            } catch (NumberFormatException var10) {
               p.sendMessage(this.plugin.getLang().get("setup.writeNumber"));
               return;
            }

            sf.setAmount(amount);
            p.sendMessage(this.plugin.getLang().get("setup.setupFood.setAmount"));
            this.plugin.getServer().getScheduler().scheduleSyncDelayedTask(this.plugin, () -> this.plugin.getSem().createSetupFoodMenu(p, sf));
            e.setCancelled(true);
            this.plugin.getSm().removeSetupName(p);
         }
      }

   }

   @EventHandler
   public void onMenu(InventoryClickEvent e) {
      Player p = (Player)e.getWhoClicked();
      if (e.getView().getTitle().equals(this.plugin.getLang().get("menus.setupFood.title"))) {
         if (e.getCurrentItem() == null || e.getCurrentItem().getType().equals(Material.AIR) || e.getSlotType().equals(SlotType.OUTSIDE)) {
            return;
         }

         ItemStack item = e.getCurrentItem();
         if (!item.hasItemMeta() || !item.getItemMeta().hasDisplayName()) {
            return;
         }

         SetupFood sf = this.plugin.getSm().getSetupFood(p);
         String display = item.getItemMeta().getDisplayName();
         if (display.equals(this.plugin.getLang().get("menus.setupFood.food.nameItem"))) {
            e.setCancelled(true);
            ItemStack cursor = p.getItemInHand();
            if (cursor == null || cursor.getType().equals(Material.AIR)) {
               p.sendMessage(this.plugin.getLang().get("setup.onHand"));
               return;
            }

            sf.setFood(cursor);
            p.sendMessage(this.plugin.getLang().get("setup.setFoodItem"));
         }

         if (display.equals(this.plugin.getLang().get("menus.setupFood.amount.nameItem"))) {
            e.setCancelled(true);
            this.plugin.getSm().setSetupName(p, "foodAmount");
            p.closeInventory();
            p.sendMessage(this.plugin.getLang().get("setup.setupFood.amount"));
         }

         if (display.equals(this.plugin.getLang().get("menus.setupFood.save.nameItem"))) {
            e.setCancelled(true);
            sf.save(p);
            this.plugin.getFm().loadFoods();
            this.plugin.getSm().removeSetupFood(p);
            p.sendMessage(this.plugin.getLang().get("setup.setupFood.save"));
            p.closeInventory();
         }
      }

      if (e.getView().getTitle().equals(this.plugin.getLang().get("menus.setupMainFood.title"))) {
         e.setCancelled(true);
         if (e.getCurrentItem() == null || e.getCurrentItem().getType().equals(Material.AIR) || e.getSlotType().equals(SlotType.OUTSIDE)) {
            return;
         }

         ItemStack item = e.getCurrentItem();
         if (!item.hasItemMeta() || !item.getItemMeta().hasDisplayName()) {
            return;
         }

         String display = item.getItemMeta().getDisplayName();
         if (display.equals(this.plugin.getLang().get("menus.next.nameItem"))) {
            this.plugin.getSem().addPage(p);
            this.plugin.getSem().createSetupMainFoodMenu(p);
            return;
         }

         if (display.equals(this.plugin.getLang().get("menus.last.nameItem"))) {
            this.plugin.getSem().removePage(p);
            this.plugin.getSem().createSetupMainFoodMenu(p);
            return;
         }

         if (display.equals(this.plugin.getLang().get("menus.setupMainFood.add.nameItem"))) {
            if (!this.plugin.getSm().isSetupFood(p)) {
               this.plugin.getSm().setSetupFood(p, new SetupFood(this.plugin));
            }

            this.plugin.getSem().createSetupFoodMenu(p, this.plugin.getSm().getSetupFood(p));
            return;
         }

         String id = NBTEditor.getString(item, "FOOD", "KEY");
         Food food = this.plugin.getFm().getFoodByKey(id);
         if (food != null) {
            if (!this.plugin.getSm().isSetupFood(p)) {
               this.plugin.getSm().setSetupFood(p, new SetupFood(this.plugin, food));
            }

            this.plugin.getSem().createSetupFoodMenu(p, this.plugin.getSm().getSetupFood(p));
         }
      }

      if (e.getView().getTitle().equals(this.plugin.getLang().get("menus.setup.setupFuel.title"))) {
         e.setCancelled(true);
         if (e.getCurrentItem() == null || e.getCurrentItem().getType().equals(Material.AIR) || e.getSlotType().equals(SlotType.OUTSIDE)) {
            return;
         }

         ItemStack item = e.getCurrentItem();
         if (!item.hasItemMeta() || !item.getItemMeta().hasDisplayName()) {
            return;
         }

         SetupFuel sas = this.plugin.getSm().getSetupFuel(p);
         String display = item.getItemMeta().getDisplayName();
         if (display.equals(this.plugin.getLang().get("menus.setup.setupFuel.result.nameItem"))) {
            ItemStack cursor = p.getItemInHand();
            if (cursor == null || cursor.getType().equals(Material.AIR)) {
               p.sendMessage(this.plugin.getLang().get("setup.onHand"));
               return;
            }

            sas.setResult(cursor);
            p.sendMessage(this.plugin.getLang().get("setup.setFuelItem"));
         }

         if (display.equals(this.plugin.getLang().get("menus.setup.setupFuel.percent.nameItem"))) {
            this.plugin.getSm().setSetupName(p, "fuelPercent");
            p.closeInventory();
            p.sendMessage(this.plugin.getLang().get("setup.fuel.percent"));
         }

         if (display.equals(this.plugin.getLang().get("menus.setup.setupFuel.amount.nameItem"))) {
            this.plugin.getSm().setSetupName(p, "fuelDuration");
            p.closeInventory();
            p.sendMessage(this.plugin.getLang().get("setup.fuel.duration"));
         }

         if (display.equals(this.plugin.getLang().get("menus.setup.setupFuel.setCraft.nameItem"))) {
            this.plugin.getSem().createSetupFuelCraftMenu(p, sas);
         }

         if (display.equals(this.plugin.getLang().get("menus.setup.setupFuel.isCraft.nameItem"))) {
            sas.setCraft(!sas.isCraft());
            p.sendMessage(this.plugin.getLang().get("setup.setFuelCraft").replaceAll("<state>", Utils.parseBoolean(sas.isCraft())));
            this.plugin.getSem().updateSetupFuelMenu(sas, e.getClickedInventory());
         }

         if (display.equals(this.plugin.getLang().get("menus.setup.setupFuel.save.nameItem"))) {
            sas.save((b) -> {
               if (b) {
                  p.sendMessage(this.plugin.getLang().get("setup.saveFuel"));
                  p.closeInventory();
                  this.plugin.getSm().removeSetupFuel(p);
                  this.plugin.getUm().reload();
               } else {
                  p.sendMessage(this.plugin.getLang().get("setup.fuel.setResult"));
                  p.closeInventory();
               }

            });
         }
      }

      if (e.getView().getTitle().equals(this.plugin.getLang().get("menus.setup.setupCompressor.title"))) {
         e.setCancelled(true);
         if (e.getCurrentItem() == null || e.getCurrentItem().getType().equals(Material.AIR) || e.getSlotType().equals(SlotType.OUTSIDE)) {
            return;
         }

         ItemStack item = e.getCurrentItem();
         if (!item.hasItemMeta() || !item.getItemMeta().hasDisplayName()) {
            return;
         }

         SetupCompressor sas = this.plugin.getSm().getSetupCompressor(p);
         String display = item.getItemMeta().getDisplayName();
         if (display.equals(this.plugin.getLang().get("menus.setup.setupCompressor.result.nameItem"))) {
            ItemStack cursor = p.getItemInHand();
            if (cursor == null || cursor.getType().equals(Material.AIR)) {
               p.sendMessage(this.plugin.getLang().get("setup.onHand"));
               return;
            }

            sas.setResult(cursor);
            p.sendMessage(this.plugin.getLang().get("setup.setCompressorItem"));
         }

         if (display.equals(this.plugin.getLang().get("menus.setup.setupCompressor.amount.nameItem"))) {
            this.plugin.getSm().setSetupName(p, "compressorAmount");
            p.closeInventory();
            p.sendMessage(this.plugin.getLang().get("setup.compressor.amount"));
         }

         if (display.equals(this.plugin.getLang().get("menus.setup.setupCompressor.setCraft.nameItem"))) {
            this.plugin.getSem().createSetupCompressorCraftMenu(p, sas);
         }

         if (display.equals(this.plugin.getLang().get("menus.setup.setupCompressor.isCraft.nameItem"))) {
            sas.setCraft(!sas.isCraft());
            p.sendMessage(this.plugin.getLang().get("setup.setCompressorCraft").replaceAll("<state>", Utils.parseBoolean(sas.isCraft())));
            this.plugin.getSem().updateSetupCompressorMenu(sas, e.getClickedInventory());
         }

         if (display.equals(this.plugin.getLang().get("menus.setup.setupCompressor.save.nameItem"))) {
            sas.save((b) -> {
               if (b) {
                  p.sendMessage(this.plugin.getLang().get("setup.saveCompressor"));
                  p.closeInventory();
                  this.plugin.getSm().removeSetupCompressor(p);
                  this.plugin.getUm().reload();
               } else {
                  p.sendMessage(this.plugin.getLang().get("setup.compressor.setResult"));
                  p.closeInventory();
               }

            });
         }
      }

      if (e.getView().getTitle().equals(this.plugin.getLang().get("menus.setup.setupAutoSmelt.title"))) {
         e.setCancelled(true);
         if (e.getCurrentItem() == null || e.getCurrentItem().getType().equals(Material.AIR) || e.getSlotType().equals(SlotType.OUTSIDE)) {
            return;
         }

         ItemStack item = e.getCurrentItem();
         if (!item.hasItemMeta() || !item.getItemMeta().hasDisplayName()) {
            return;
         }

         SetupAutoSmelt sas = this.plugin.getSm().getSetupAutoSmelt(p);
         String display = item.getItemMeta().getDisplayName();
         if (display.equals(this.plugin.getLang().get("menus.setup.setupAutoSmelt.result.nameItem"))) {
            ItemStack cursor = p.getItemInHand();
            if (cursor == null || cursor.getType().equals(Material.AIR)) {
               p.sendMessage(this.plugin.getLang().get("setup.onHand"));
               return;
            }

            sas.setResult(cursor);
            p.sendMessage(this.plugin.getLang().get("setup.setAutoSmeltItem"));
         }

         if (display.equals(this.plugin.getLang().get("menus.setup.setupAutoSmelt.amount.nameItem"))) {
            this.plugin.getSm().setSetupName(p, "autoSmeltPercent");
            p.closeInventory();
            p.sendMessage(this.plugin.getLang().get("setup.autosmelt.percent"));
         }

         if (display.equals(this.plugin.getLang().get("menus.setup.setupAutoSmelt.setCraft.nameItem"))) {
            this.plugin.getSem().createSetupAutoSmeltCraftMenu(p, sas);
         }

         if (display.equals(this.plugin.getLang().get("menus.setup.setupAutoSmelt.isCraft.nameItem"))) {
            sas.setCraft(!sas.isCraft());
            p.sendMessage(this.plugin.getLang().get("setup.setAutoSmeltCraft").replaceAll("<state>", Utils.parseBoolean(sas.isCraft())));
            this.plugin.getSem().updateSetupAutoSmeltMenu(sas, e.getClickedInventory());
         }

         if (display.equals(this.plugin.getLang().get("menus.setup.setupAutoSmelt.save.nameItem"))) {
            sas.save((b) -> {
               if (b) {
                  p.sendMessage(this.plugin.getLang().get("setup.saveAutoSmelt"));
                  p.closeInventory();
                  this.plugin.getSm().removeSetupAutoSmelt(p);
                  this.plugin.getUm().reload();
               } else {
                  p.sendMessage(this.plugin.getLang().get("setup.autosmelt.setResult"));
                  p.closeInventory();
               }

            });
         }
      }

      if (e.getView().getTitle().equals(this.plugin.getLang().get("menus.setup.fuelCraft.title"))) {
         if (e.getCurrentItem() == null || e.getCurrentItem().getType().equals(Material.AIR) || e.getSlotType().equals(SlotType.OUTSIDE)) {
            return;
         }

         ItemStack item = e.getCurrentItem();
         if (!item.hasItemMeta() || !item.getItemMeta().hasDisplayName()) {
            return;
         }

         SetupFuel sas = this.plugin.getSm().getSetupFuel(p);
         String display = item.getItemMeta().getDisplayName();
         if (display.equals("§7")) {
            e.setCancelled(true);
         }

         if (display.equals(this.plugin.getLang().get("menus.setup.fuelCraft.permission.nameItem"))) {
            e.setCancelled(true);
            this.plugin.getSm().setSetupName(p, "fuelCraftPermission");
            p.closeInventory();
            p.sendMessage(this.plugin.getLang().get("setup.permission"));
         }

         if (display.equals(this.plugin.getLang().get("menus.setup.fuelCraft.save.nameItem"))) {
            e.setCancelled(true);
            if (sas.getResult() == null && (e.getView().getItem(25) == null || e.getView().getItem(25).getType().equals(Material.AIR))) {
               p.sendMessage(this.plugin.getLang().get("setup.noResult"));
               return;
            }

            SetupCraft sc = sas.getCraft();
            ItemStack[] matrix = new ItemStack[]{e.getView().getItem(12) == null ? new ItemStack(Material.AIR) : e.getView().getItem(12), e.getView().getItem(13) == null ? new ItemStack(Material.AIR) : e.getView().getItem(13), e.getView().getItem(14) == null ? new ItemStack(Material.AIR) : e.getView().getItem(14), e.getView().getItem(21) == null ? new ItemStack(Material.AIR) : e.getView().getItem(21), e.getView().getItem(22) == null ? new ItemStack(Material.AIR) : e.getView().getItem(22), e.getView().getItem(23) == null ? new ItemStack(Material.AIR) : e.getView().getItem(23), e.getView().getItem(30) == null ? new ItemStack(Material.AIR) : e.getView().getItem(30), e.getView().getItem(31) == null ? new ItemStack(Material.AIR) : e.getView().getItem(31), e.getView().getItem(32) == null ? new ItemStack(Material.AIR) : e.getView().getItem(32)};
            sc.setMatrix(matrix);
            if (e.getView().getItem(25) != null && !e.getView().getItem(25).getType().equals(Material.AIR)) {
               sc.setResult(e.getView().getItem(25));
               sas.setResult(e.getView().getItem(25));
            } else {
               sc.setResult(sas.getResult());
            }

            p.closeInventory();
         }
      }

      if (e.getView().getTitle().equals(this.plugin.getLang().get("menus.setup.compressorCraft.title"))) {
         if (e.getCurrentItem() == null || e.getCurrentItem().getType().equals(Material.AIR) || e.getSlotType().equals(SlotType.OUTSIDE)) {
            return;
         }

         ItemStack item = e.getCurrentItem();
         if (!item.hasItemMeta() || !item.getItemMeta().hasDisplayName()) {
            return;
         }

         SetupCompressor sas = this.plugin.getSm().getSetupCompressor(p);
         String display = item.getItemMeta().getDisplayName();
         if (display.equals("§7")) {
            e.setCancelled(true);
         }

         if (display.equals(this.plugin.getLang().get("menus.setup.compressorCraft.permission.nameItem"))) {
            e.setCancelled(true);
            this.plugin.getSm().setSetupName(p, "compressorCraftPermission");
            p.closeInventory();
            p.sendMessage(this.plugin.getLang().get("setup.permission"));
         }

         if (display.equals(this.plugin.getLang().get("menus.setup.compressorCraft.save.nameItem"))) {
            e.setCancelled(true);
            if (sas.getResult() == null && (e.getView().getItem(25) == null || e.getView().getItem(25).getType().equals(Material.AIR))) {
               p.sendMessage(this.plugin.getLang().get("setup.noResult"));
               return;
            }

            SetupCraft sc = sas.getCraft();
            ItemStack[] matrix = new ItemStack[9];
            matrix[0] = e.getView().getItem(12) == null ? new ItemStack(Material.AIR) : e.getView().getItem(12);
            matrix[1] = e.getView().getItem(13) == null ? new ItemStack(Material.AIR) : e.getView().getItem(13);
            matrix[2] = e.getView().getItem(14) == null ? new ItemStack(Material.AIR) : e.getView().getItem(14);
            matrix[3] = e.getView().getItem(21) == null ? new ItemStack(Material.AIR) : e.getView().getItem(21);
            matrix[4] = e.getView().getItem(22) == null ? new ItemStack(Material.AIR) : e.getView().getItem(22);
            matrix[5] = e.getView().getItem(23) == null ? new ItemStack(Material.AIR) : e.getView().getItem(23);
            matrix[6] = e.getView().getItem(30) == null ? new ItemStack(Material.AIR) : e.getView().getItem(30);
            matrix[7] = e.getView().getItem(31) == null ? new ItemStack(Material.AIR) : e.getView().getItem(31);
            matrix[8] = e.getView().getItem(32) == null ? new ItemStack(Material.AIR) : e.getView().getItem(32);
            sc.setMatrix(matrix);
            if (e.getView().getItem(25) != null && !e.getView().getItem(25).getType().equals(Material.AIR)) {
               sc.setResult(e.getView().getItem(25));
               sas.setResult(e.getView().getItem(25));
            } else {
               sc.setResult(sas.getResult());
            }

            p.closeInventory();
         }
      }

      if (e.getView().getTitle().equals(this.plugin.getLang().get("menus.setup.autoSmeltCraft.title"))) {
         if (e.getCurrentItem() == null || e.getCurrentItem().getType().equals(Material.AIR) || e.getSlotType().equals(SlotType.OUTSIDE)) {
            return;
         }

         ItemStack item = e.getCurrentItem();
         if (!item.hasItemMeta() || !item.getItemMeta().hasDisplayName()) {
            return;
         }

         SetupAutoSmelt sas = this.plugin.getSm().getSetupAutoSmelt(p);
         String display = item.getItemMeta().getDisplayName();
         if (display.equals("§7")) {
            e.setCancelled(true);
         }

         if (display.equals(this.plugin.getLang().get("menus.setup.autoSmeltCraft.permission.nameItem"))) {
            e.setCancelled(true);
            this.plugin.getSm().setSetupName(p, "autoSmeltCraftPermission");
            p.closeInventory();
            p.sendMessage(this.plugin.getLang().get("setup.permission"));
         }

         if (display.equals(this.plugin.getLang().get("menus.setup.autoSmeltCraft.save.nameItem"))) {
            e.setCancelled(true);
            if (sas.getResult() == null && (e.getView().getItem(25) == null || e.getView().getItem(25).getType().equals(Material.AIR))) {
               p.sendMessage(this.plugin.getLang().get("setup.noResult"));
               return;
            }

            SetupCraft sc = sas.getCraft();
            ItemStack[] matrix = new ItemStack[9];
            matrix[0] = e.getView().getItem(12) == null ? new ItemStack(Material.AIR) : e.getView().getItem(12);
            matrix[1] = e.getView().getItem(13) == null ? new ItemStack(Material.AIR) : e.getView().getItem(13);
            matrix[2] = e.getView().getItem(14) == null ? new ItemStack(Material.AIR) : e.getView().getItem(14);
            matrix[3] = e.getView().getItem(21) == null ? new ItemStack(Material.AIR) : e.getView().getItem(21);
            matrix[4] = e.getView().getItem(22) == null ? new ItemStack(Material.AIR) : e.getView().getItem(22);
            matrix[5] = e.getView().getItem(23) == null ? new ItemStack(Material.AIR) : e.getView().getItem(23);
            matrix[6] = e.getView().getItem(30) == null ? new ItemStack(Material.AIR) : e.getView().getItem(30);
            matrix[7] = e.getView().getItem(31) == null ? new ItemStack(Material.AIR) : e.getView().getItem(31);
            matrix[8] = e.getView().getItem(32) == null ? new ItemStack(Material.AIR) : e.getView().getItem(32);
            sc.setMatrix(matrix);
            if (e.getView().getItem(25) != null && !e.getView().getItem(25).getType().equals(Material.AIR)) {
               sc.setResult(e.getView().getItem(25));
               sas.setResult(e.getView().getItem(25));
            } else {
               sc.setResult(sas.getResult());
            }

            p.closeInventory();
         }
      }

      if (e.getView().getTitle().equals(this.plugin.getLang().get("menus.setup.autoSellCraft.title"))) {
         if (e.getCurrentItem() == null || e.getCurrentItem().getType().equals(Material.AIR) || e.getSlotType().equals(SlotType.OUTSIDE)) {
            return;
         }

         ItemStack item = e.getCurrentItem();
         if (!item.hasItemMeta() || !item.getItemMeta().hasDisplayName()) {
            return;
         }

         SetupAutoSell sas = this.plugin.getSm().getSetupAutoSell(p);
         String display = item.getItemMeta().getDisplayName();
         if (display.equals("§7")) {
            e.setCancelled(true);
         }

         if (display.equals(this.plugin.getLang().get("menus.setup.autoSellCraft.permission.nameItem"))) {
            e.setCancelled(true);
            this.plugin.getSm().setSetupName(p, "autoSellCraftPermission");
            p.closeInventory();
            p.sendMessage(this.plugin.getLang().get("setup.permission"));
         }

         if (display.equals(this.plugin.getLang().get("menus.setup.autoSellCraft.save.nameItem"))) {
            e.setCancelled(true);
            if (sas.getResult() == null && (e.getView().getItem(25) == null || e.getView().getItem(25).getType().equals(Material.AIR))) {
               p.sendMessage(this.plugin.getLang().get("setup.noResult"));
               return;
            }

            SetupCraft sc = sas.getCraft();
            ItemStack[] matrix = new ItemStack[9];
            matrix[0] = e.getView().getItem(12) == null ? new ItemStack(Material.AIR) : e.getView().getItem(12);
            matrix[1] = e.getView().getItem(13) == null ? new ItemStack(Material.AIR) : e.getView().getItem(13);
            matrix[2] = e.getView().getItem(14) == null ? new ItemStack(Material.AIR) : e.getView().getItem(14);
            matrix[3] = e.getView().getItem(21) == null ? new ItemStack(Material.AIR) : e.getView().getItem(21);
            matrix[4] = e.getView().getItem(22) == null ? new ItemStack(Material.AIR) : e.getView().getItem(22);
            matrix[5] = e.getView().getItem(23) == null ? new ItemStack(Material.AIR) : e.getView().getItem(23);
            matrix[6] = e.getView().getItem(30) == null ? new ItemStack(Material.AIR) : e.getView().getItem(30);
            matrix[7] = e.getView().getItem(31) == null ? new ItemStack(Material.AIR) : e.getView().getItem(31);
            matrix[8] = e.getView().getItem(32) == null ? new ItemStack(Material.AIR) : e.getView().getItem(32);
            sc.setMatrix(matrix);
            if (e.getView().getItem(25) != null && !e.getView().getItem(25).getType().equals(Material.AIR)) {
               sc.setResult(e.getView().getItem(25));
               sas.setResult(e.getView().getItem(25));
            } else {
               sc.setResult(sas.getResult());
            }

            p.closeInventory();
         }
      }

      if (e.getView().getTitle().equals(this.plugin.getLang().get("menus.setup.setupAutoSell.title"))) {
         e.setCancelled(true);
         if (e.getCurrentItem() == null || e.getCurrentItem().getType().equals(Material.AIR) || e.getSlotType().equals(SlotType.OUTSIDE)) {
            return;
         }

         ItemStack item = e.getCurrentItem();
         if (!item.hasItemMeta() || !item.getItemMeta().hasDisplayName()) {
            return;
         }

         SetupAutoSell sas = this.plugin.getSm().getSetupAutoSell(p);
         String display = item.getItemMeta().getDisplayName();
         if (display.equals(this.plugin.getLang().get("menus.setup.setupAutoSell.result.nameItem"))) {
            ItemStack cursor = p.getItemInHand();
            if (cursor == null || cursor.getType().equals(Material.AIR)) {
               p.sendMessage(this.plugin.getLang().get("setup.onHand"));
               return;
            }

            sas.setResult(cursor);
            p.sendMessage(this.plugin.getLang().get("setup.setAutoSellItem"));
         }

         if (display.equals(this.plugin.getLang().get("menus.setup.setupAutoSell.amount.nameItem"))) {
            this.plugin.getSm().setSetupName(p, "autoSellPercent");
            p.closeInventory();
            p.sendMessage(this.plugin.getLang().get("setup.autosell.percent"));
         }

         if (display.equals(this.plugin.getLang().get("menus.setup.setupAutoSell.setCraft.nameItem"))) {
            this.plugin.getSem().createSetupAutoSellCraftMenu(p, sas);
         }

         if (display.equals(this.plugin.getLang().get("menus.setup.setupAutoSell.isCraft.nameItem"))) {
            sas.setCraft(!sas.isCraft());
            p.sendMessage(this.plugin.getLang().get("setup.setAutoSellCraft").replaceAll("<state>", Utils.parseBoolean(sas.isCraft())));
            this.plugin.getSem().updateSetupAutoSellMenu(sas, e.getClickedInventory());
         }

         if (display.equals(this.plugin.getLang().get("menus.setup.setupAutoSell.save.nameItem"))) {
            sas.save((b) -> {
               if (b) {
                  p.sendMessage(this.plugin.getLang().get("setup.saveAutoSell"));
                  p.closeInventory();
                  this.plugin.getSm().removeSetupAutoSell(p);
                  this.plugin.getUm().reload();
               } else {
                  p.sendMessage(this.plugin.getLang().get("setup.autosell.setResult"));
                  p.closeInventory();
               }

            });
         }
      }

      if (e.getView().getTitle().equals(this.plugin.getLang().get("menus.setup.craft.title"))) {
         if (e.getCurrentItem() == null || e.getCurrentItem().getType().equals(Material.AIR) || e.getSlotType().equals(SlotType.OUTSIDE)) {
            return;
         }

         ItemStack item = e.getCurrentItem();
         if (!item.hasItemMeta() || !item.getItemMeta().hasDisplayName()) {
            return;
         }

         SetupMinion sm = this.plugin.getSm().getSetupMinion(p);
         SetupMinionLevel sml = sm.getActual();
         String display = item.getItemMeta().getDisplayName();
         if (display.equals("§7")) {
            e.setCancelled(true);
         }

         if (display.equals(this.plugin.getLang().get("menus.setup.craft.permission.nameItem"))) {
            e.setCancelled(true);
            this.plugin.getSm().setSetupName(p, "craftPermission");
            p.closeInventory();
            p.sendMessage(this.plugin.getLang().get("setup.permission"));
         }

         if (display.equals(this.plugin.getLang().get("menus.setup.craft.save.nameItem"))) {
            e.setCancelled(true);
            if (e.getView().getItem(25) == null) {
               if (!e.getClick().equals(ClickType.DOUBLE_CLICK)) {
                  p.sendMessage(this.plugin.getLang().get("setup.noCraft"));
                  return;
               }

               p.sendMessage(this.plugin.getLang().get("setup.changeURL"));
            }

            SetupCraft sc = sml.getCraft();
            ItemStack[] matrix = new ItemStack[]{e.getView().getItem(12) == null ? new ItemStack(Material.AIR) : e.getView().getItem(12), e.getView().getItem(13) == null ? new ItemStack(Material.AIR) : e.getView().getItem(13), e.getView().getItem(14) == null ? new ItemStack(Material.AIR) : e.getView().getItem(14), e.getView().getItem(21) == null ? new ItemStack(Material.AIR) : e.getView().getItem(21), e.getView().getItem(22) == null ? new ItemStack(Material.AIR) : e.getView().getItem(22), e.getView().getItem(23) == null ? new ItemStack(Material.AIR) : e.getView().getItem(23), e.getView().getItem(30) == null ? new ItemStack(Material.AIR) : e.getView().getItem(30), e.getView().getItem(31) == null ? new ItemStack(Material.AIR) : e.getView().getItem(31), e.getView().getItem(32) == null ? new ItemStack(Material.AIR) : e.getView().getItem(32)};
            sc.setMatrix(matrix);
            if (e.getView().getItem(25) != null && !e.getView().getItem(25).getType().equals(Material.AIR)) {
               sc.setResult(e.getView().getItem(25));
            } else {
               sc.setResult((ItemStack)null);
            }

            p.closeInventory();
            this.plugin.getSem().createSetupMinionLevelMenu(p, sml);
         }
      }

      if (e.getView().getTitle().equals(this.plugin.getLang().get("menus.setup.minionLevel.title"))) {
         e.setCancelled(true);
         if (e.getCurrentItem() == null || e.getCurrentItem().getType().equals(Material.AIR) || e.getSlotType().equals(SlotType.OUTSIDE)) {
            return;
         }

         ItemStack item = e.getCurrentItem();
         if (!item.hasItemMeta() || !item.getItemMeta().hasDisplayName()) {
            return;
         }

         SetupMinion sm = this.plugin.getSm().getSetupMinion(p);
         SetupMinionLevel sml = sm.getActual();
         String display = item.getItemMeta().getDisplayName();
         if (display.equals(this.plugin.getLang().get("menus.setup.minionLevel.delay.nameItem"))) {
            this.plugin.getSm().setSetupName(p, "minionDelay");
            p.closeInventory();
            p.sendMessage(this.plugin.getLang().get("setup.delay"));
         }

         if (display.equals(this.plugin.getLang().get("menus.setup.minionLevel.max.nameItem"))) {
            this.plugin.getSm().setSetupName(p, "minionMax");
            p.closeInventory();
            p.sendMessage(this.plugin.getLang().get("setup.max"));
         }

         if (display.equals(this.plugin.getLang().get("menus.setup.minionLevel.upgradeLevel.nameItem"))) {
            this.plugin.getSm().setSetupName(p, "minionLevel");
            p.closeInventory();
            p.sendMessage(this.plugin.getLang().get("setup.level"));
         }

         if (display.equals(this.plugin.getLang().get("menus.setup.minionLevel.upgradeCoins.nameItem"))) {
            this.plugin.getSm().setSetupName(p, "minionCoins");
            p.closeInventory();
            p.sendMessage(this.plugin.getLang().get("setup.coins"));
         }

         if (display.equals(this.plugin.getLang().get("menus.setup.minionLevel.health.nameItem"))) {
            this.plugin.getSm().setSetupName(p, "minionHealth");
            p.closeInventory();
            p.sendMessage(this.plugin.getLang().get("setup.health"));
         }

         if (display.equals(this.plugin.getLang().get("menus.setup.minionLevel.food.nameItem"))) {
            this.plugin.getSm().setSetupName(p, "minionFood");
            p.closeInventory();
            p.sendMessage(this.plugin.getLang().get("setup.food"));
         }

         if (display.equals(this.plugin.getLang().get("menus.setup.minionLevel.workTime.nameItem"))) {
            this.plugin.getSm().setSetupName(p, "minionWorkTime");
            p.closeInventory();
            p.sendMessage(this.plugin.getLang().get("setup.workTime"));
         }

         if (display.equals(this.plugin.getLang().get("menus.setup.minionLevel.sleep.nameItem"))) {
            this.plugin.getSm().setSetupName(p, "minionSleep");
            p.closeInventory();
            p.sendMessage(this.plugin.getLang().get("setup.sleep"));
         }

         if (display.equals(this.plugin.getLang().get("menus.setup.minionLevel.isCoins.nameItem"))) {
            sml.setCoins(!sml.isCoins());
            p.sendMessage(this.plugin.getLang().get("setup.setIsCoins").replaceAll("<state>", Utils.parseBoolean(sml.isCoins())));
            this.plugin.getSem().updateSetupMinionLevelMenu(sml, e.getClickedInventory());
         }

         if (display.equals(this.plugin.getLang().get("menus.setup.minionLevel.isLevel.nameItem"))) {
            sml.setLevel(!sml.isLevel());
            p.sendMessage(this.plugin.getLang().get("setup.setIsLevel").replaceAll("<state>", Utils.parseBoolean(sml.isLevel())));
            this.plugin.getSem().updateSetupMinionLevelMenu(sml, e.getClickedInventory());
         }

         if (display.equals(this.plugin.getLang().get("menus.setup.minionLevel.isCraft.nameItem"))) {
            sml.setCraft(!sml.isCraft());
            p.sendMessage(this.plugin.getLang().get("setup.setIsCraft").replaceAll("<state>", Utils.parseBoolean(sml.isCraft())));
            this.plugin.getSem().updateSetupMinionLevelMenu(sml, e.getClickedInventory());
         }

         if (display.equals(this.plugin.getLang().get("menus.setup.minionLevel.setCraft.nameItem"))) {
            if (!sml.isCraft()) {
               p.sendMessage(this.plugin.getLang().get("setup.craftNotEnabled"));
               return;
            }

            if (sml.getCraft() == null) {
               sml.setCraft(new SetupCraft());
            }

            this.plugin.getSem().createSetupCraftMenu(p);
         }

         if (display.equals(this.plugin.getLang().get("menus.setup.minionLevel.save.nameItem"))) {
            p.closeInventory();
            sm.saveLevel(p);
            this.plugin.getSem().createSetupMinionMenu(p, sm);
         }
      }

      if (e.getView().getTitle().equals(this.plugin.getLang().get("menus.setup.title"))) {
         if (e.getCurrentItem() == null || e.getCurrentItem().getType().equals(Material.AIR) || e.getSlotType().equals(SlotType.OUTSIDE)) {
            return;
         }

         ItemStack item = e.getCurrentItem();
         if (!item.hasItemMeta() || !item.getItemMeta().hasDisplayName()) {
            return;
         }

         String display = item.getItemMeta().getDisplayName();
         if (display.equals(this.plugin.getLang().get("menus.next.nameItem"))) {
            e.setCancelled(true);
            this.plugin.getSem().addPage(p);
            this.plugin.getSem().createSetupMainMenu(p);
            return;
         }

         if (display.equals(this.plugin.getLang().get("menus.last.nameItem"))) {
            e.setCancelled(true);
            this.plugin.getSem().removePage(p);
            this.plugin.getSem().createSetupMainMenu(p);
            return;
         }

         if (display.equals(this.plugin.getLang().get("menus.setup.main.add.nameItem"))) {
            e.setCancelled(true);
            if (!this.plugin.getSm().isSetupMinion(p)) {
               this.plugin.getSm().setSetupMinion(p, new SetupMinion(this.plugin));
            }

            SetupMinion sm = this.plugin.getSm().getSetupMinion(p);
            this.plugin.getSem().createSetupMinionMenu(p, sm);
            return;
         }

         String key = Utils.ObjectOrDefaultString(NBTEditor.getString(item, "KEY"), "NONE");
         if (key.equals("NONE")) {
            return;
         }

         e.setCancelled(true);
         Minion m = this.plugin.getMm().getMinion(key);
         SetupMinion sm = new SetupMinion(this.plugin, m);
         this.plugin.getSm().setSetupMinion(p, sm);
         this.plugin.getSem().createSetupMinionMenu(p, sm);
      }

      if (e.getView().getTitle().equals(this.plugin.getLang().get("menus.setup.minion.title"))) {
         if (e.getSlotType().equals(SlotType.OUTSIDE)) {
            return;
         }

         if (e.getClickedInventory().getType().equals(InventoryType.PLAYER)) {
            return;
         }

         e.setCancelled(true);
         if (e.getCurrentItem() == null || e.getCurrentItem().getType().equals(Material.AIR)) {
            return;
         }

         ItemStack item = e.getCurrentItem();
         if (!item.hasItemMeta() || !item.getItemMeta().hasDisplayName()) {
            return;
         }

         SetupMinion sm = this.plugin.getSm().getSetupMinion(p);
         String display = item.getItemMeta().getDisplayName();
         if (display.equals(this.plugin.getLang().get("menus.setup.minion.entity.nameItem"))) {
            this.plugin.getSm().setSetupName(p, "minionSpawn");
            p.closeInventory();
            p.sendMessage(this.plugin.getLang().get("setup.entity"));
         }

         if (display.equals(this.plugin.getLang().get("menus.setup.minion.type.nameItem"))) {
            this.plugin.getSm().setSetupName(p, "minionType");
            p.closeInventory();
            p.sendMessage(this.plugin.getLang().get("setup.type"));
         }

         if (display.equals(this.plugin.getLang().get("menus.setup.minion.levels.nameItem"))) {
            if (sm.getActual() == null) {
               sm.setActual(new SetupMinionLevel(sm, sm.getLevels().size() + 1));
            }

            SetupMinionLevel sml = sm.getActual();
            this.plugin.getSem().createSetupMinionLevelMenu(p, sml);
         }

         if (display.equals(this.plugin.getLang().get("menus.setup.minion.lore.nameItem"))) {
            if (e.getClick().equals(ClickType.LEFT)) {
               this.plugin.getSm().setSetupName(p, "minionLore");
               p.closeInventory();
               p.sendMessage(this.plugin.getLang().get("setup.lore"));
            } else {
               if (sm.getLore().isEmpty()) {
                  p.sendMessage(this.plugin.getLang().get("setup.noLine"));
                  return;
               }

               sm.getLore().remove(sm.getLore().size() - 1);
               this.plugin.getSem().createSetupMinionMenu(p, sm);
               p.sendMessage(this.plugin.getLang().get("setup.removeLast"));
            }
         }

         if (display.equals(this.plugin.getLang().get("menus.setup.minion.key.nameItem"))) {
            this.plugin.getSm().setSetupName(p, "minionKey");
            p.closeInventory();
            p.sendMessage(this.plugin.getLang().get("setup.key"));
         }

         if (display.equals(this.plugin.getLang().get("menus.setup.minion.red.nameItem"))) {
            this.plugin.getSm().setSetupName(p, "minionRed");
            p.closeInventory();
            p.sendMessage(this.plugin.getLang().get("setup.red"));
         }

         if (display.equals(this.plugin.getLang().get("menus.setup.minion.blue.nameItem"))) {
            this.plugin.getSm().setSetupName(p, "minionBlue");
            p.closeInventory();
            p.sendMessage(this.plugin.getLang().get("setup.blue"));
         }

         if (display.equals(this.plugin.getLang().get("menus.setup.minion.green.nameItem"))) {
            this.plugin.getSm().setSetupName(p, "minionGreen");
            p.closeInventory();
            p.sendMessage(this.plugin.getLang().get("setup.green"));
         }

         if (display.equals(this.plugin.getLang().get("menus.setup.minion.minionTitle.nameItem"))) {
            this.plugin.getSm().setSetupName(p, "minionTitle");
            p.closeInventory();
            p.sendMessage(this.plugin.getLang().get("setup.title"));
         }

         if (display.equals(this.plugin.getLang().get("menus.setup.minion.priceCompressor.nameItem"))) {
            this.plugin.getSm().setSetupName(p, "minionPriceCompressor");
            p.closeInventory();
            p.sendMessage(this.plugin.getLang().get("setup.priceCompressor"));
         }

         if (display.equals(this.plugin.getLang().get("menus.setup.minion.priceSmelt.nameItem"))) {
            this.plugin.getSm().setSetupName(p, "minionPriceSmelt");
            p.closeInventory();
            p.sendMessage(this.plugin.getLang().get("setup.priceSmelt"));
         }

         if (display.equals(this.plugin.getLang().get("menus.setup.minion.priceNormal.nameItem"))) {
            this.plugin.getSm().setSetupName(p, "minionPriceNormal");
            p.closeInventory();
            p.sendMessage(this.plugin.getLang().get("setup.priceNormal"));
         }

         if (display.equals(this.plugin.getLang().get("menus.setup.minion.fuelEnabled.nameItem"))) {
            sm.setFuelEnabled(!sm.isFuelEnabled());
            p.sendMessage(this.plugin.getLang().get("setup.fuelUpgrade").replaceAll("<state>", Utils.parseBoolean(sm.isFuelEnabled())));
            this.plugin.getSem().updateSetupMinionMenu(sm, e.getClickedInventory());
         }

         if (display.equals(this.plugin.getLang().get("menus.setup.minion.autoSellEnabled.nameItem"))) {
            sm.setAutoSellEnabled(!sm.isAutoSellEnabled());
            p.sendMessage(this.plugin.getLang().get("setup.autoSellUpgrade").replaceAll("<state>", Utils.parseBoolean(sm.isAutoSellEnabled())));
            this.plugin.getSem().updateSetupMinionMenu(sm, e.getClickedInventory());
         }

         if (display.equals(this.plugin.getLang().get("menus.setup.minion.smeltEnabled.nameItem"))) {
            sm.setSmeltEnabled(!sm.isSmeltEnabled());
            p.sendMessage(this.plugin.getLang().get("setup.smeltUpgrade").replaceAll("<state>", Utils.parseBoolean(sm.isSmeltEnabled())));
            this.plugin.getSem().updateSetupMinionMenu(sm, e.getClickedInventory());
         }

         if (display.equals(this.plugin.getLang().get("menus.setup.minion.compressorEnabled.nameItem"))) {
            sm.setCompressorEnabled(!sm.isCompressorEnabled());
            p.sendMessage(this.plugin.getLang().get("setup.compressorUpgrade").replaceAll("<state>", Utils.parseBoolean(sm.isCompressorEnabled())));
            this.plugin.getSem().updateSetupMinionMenu(sm, e.getClickedInventory());
         }

         if (display.equals(this.plugin.getLang().get("menus.setup.minion.place.nameItem"))) {
            ItemStack cursor = p.getItemInHand();
            if (cursor == null || cursor.getType().equals(Material.AIR)) {
               p.sendMessage(this.plugin.getLang().get("setup.onHand"));
               return;
            }

            if (sm.getType().equals(MinionType.LUMBERJACK) && !MinionType.LUMBERJACK.check(cursor.getType())) {
               p.sendMessage(this.plugin.getLang().get("setup.onlyAccept").replaceAll("<works>", MinionType.LUMBERJACK.toString()));
               return;
            }

            if (sm.getType().equals(MinionType.PEASANT) && !MinionType.PEASANT.check(cursor.getType())) {
               p.sendMessage(this.plugin.getLang().get("setup.onlyAccept").replaceAll("<works>", MinionType.PEASANT.toString()));
               return;
            }

            sm.setPlace(cursor);
            p.sendMessage(this.plugin.getLang().get("setup.setPlace"));
         }

         if (display.equals(this.plugin.getLang().get("menus.setup.minion.give.nameItem"))) {
            this.plugin.getSem().createAddGiveInInvItems(p, sm);
         }

         if (display.equals(this.plugin.getLang().get("menus.setup.minion.compressor.nameItem"))) {
            this.plugin.getSem().createAddCompressorItems(p, sm);
         }

         if (display.equals(this.plugin.getLang().get("menus.setup.minion.smelt.nameItem"))) {
            this.plugin.getSem().createAddSmeltItems(p, sm);
         }

         if (display.equals(this.plugin.getLang().get("menus.setup.minion.save.nameItem"))) {
            if (sm.getLevels().size() < 1) {
               p.sendMessage(this.plugin.getLang().get("setup.oneLevel"));
               return;
            }

            sm.save(p);
            this.plugin.getSm().removeSetupMinion(p);
            p.closeInventory();
            Bukkit.getScheduler().scheduleSyncDelayedTask(this.plugin, () -> this.plugin.reload(), 2L);
         }
      }

      if (e.getView().getTitle().equals(this.plugin.getLang().get("menus.setup.giveInInv.title"))) {
         if (e.getCurrentItem() == null || e.getCurrentItem().getType().equals(Material.AIR) || e.getSlotType().equals(SlotType.OUTSIDE)) {
            return;
         }

         ItemStack item = e.getCurrentItem();
         if (!item.hasItemMeta() || !item.getItemMeta().hasDisplayName()) {
            return;
         }

         SetupMinion sm = this.plugin.getSm().getSetupMinion(p);
         String display = item.getItemMeta().getDisplayName();
         if (display.equals(this.plugin.getLang().get("menus.setup.giveInInv.save.nameItem"))) {
            e.setCancelled(true);
            sm.getGiveInInv().clear();

            for(int i = 0; i < 27; ++i) {
               ItemStack it = e.getView().getItem(i);
               if (it != null && !it.getType().equals(Material.AIR)) {
                  sm.getGiveInInv().add(it);
               }
            }

            p.sendMessage(this.plugin.getLang().get("setup.giveInInvSave"));
            this.plugin.getSem().createSetupMinionMenu(p, sm);
         }
      }

      if (e.getView().getTitle().equals(this.plugin.getLang().get("menus.setup.smelt.title"))) {
         if (e.getCurrentItem() == null || e.getCurrentItem().getType().equals(Material.AIR) || e.getSlotType().equals(SlotType.OUTSIDE)) {
            return;
         }

         ItemStack item = e.getCurrentItem();
         if (!item.hasItemMeta() || !item.getItemMeta().hasDisplayName()) {
            return;
         }

         SetupMinion sm = this.plugin.getSm().getSetupMinion(p);
         String display = item.getItemMeta().getDisplayName();
         if (display.equals(this.plugin.getLang().get("menus.setup.smelt.save.nameItem"))) {
            e.setCancelled(true);
            sm.getAutoSmelt().clear();

            for(int i = 0; i < 27; ++i) {
               ItemStack it = e.getView().getItem(i);
               if (it != null && !it.getType().equals(Material.AIR)) {
                  sm.getAutoSmelt().add(it);
               }
            }

            p.sendMessage(this.plugin.getLang().get("setup.smeltSave"));
            this.plugin.getSem().createSetupMinionMenu(p, sm);
         }
      }

      if (e.getView().getTitle().equals(this.plugin.getLang().get("menus.setup.compressor.title"))) {
         if (e.getCurrentItem() == null || e.getCurrentItem().getType().equals(Material.AIR) || e.getSlotType().equals(SlotType.OUTSIDE)) {
            return;
         }

         ItemStack item = e.getCurrentItem();
         if (!item.hasItemMeta() || !item.getItemMeta().hasDisplayName()) {
            return;
         }

         SetupMinion sm = this.plugin.getSm().getSetupMinion(p);
         String display = item.getItemMeta().getDisplayName();
         if (display.equals(this.plugin.getLang().get("menus.setup.compressor.save.nameItem"))) {
            e.setCancelled(true);
            sm.getCompressor().clear();

            for(int i = 0; i < 27; ++i) {
               ItemStack it = e.getView().getItem(i);
               if (it != null && !it.getType().equals(Material.AIR)) {
                  sm.getCompressor().add(it);
               }
            }

            p.sendMessage(this.plugin.getLang().get("setup.smeltSave"));
            this.plugin.getSem().createSetupMinionMenu(p, sm);
         }
      }

   }
}
