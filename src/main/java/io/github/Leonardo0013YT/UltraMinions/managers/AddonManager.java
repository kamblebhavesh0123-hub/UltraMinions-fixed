package io.github.Leonardo0013YT.UltraMinions.managers;

import io.github.Leonardo0013YT.UltraMinions.Main;
import io.github.Leonardo0013YT.UltraMinions.addons.EssentialsAddon;
import io.github.Leonardo0013YT.UltraMinions.addons.LuckPermsAddon;
import io.github.Leonardo0013YT.UltraMinions.addons.PlaceholderAPIAddon;
import io.github.Leonardo0013YT.UltraMinions.addons.ShopGUIAddon;
import io.github.Leonardo0013YT.UltraMinions.addons.VaultAddon;
import io.github.Leonardo0013YT.UltraMinions.database.PlayerData;
import io.github.Leonardo0013YT.UltraMinions.database.PlayerMinion;
import io.github.Leonardo0013YT.UltraMinions.interfaces.HologramAddon;
import io.github.Leonardo0013YT.UltraMinions.interfaces.PlaceholderAddon;
import io.github.Leonardo0013YT.UltraMinions.interfaces.ProtectionAddon;
import io.github.Leonardo0013YT.UltraMinions.interfaces.SellAddon;
import io.github.Leonardo0013YT.UltraMinions.tiers.Tier;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.permissions.PermissionAttachmentInfo;

public class AddonManager {
   private Main plugin;
   private List<ProtectionAddon> protectionAddons = new ArrayList();
   private List<SellAddon> sellAddons = new ArrayList();
   private LuckPermsAddon lpa;
   private VaultAddon vault;
   private HologramAddon ha;
   private PlaceholderAddon placeholder;
   private boolean addon = false;

   public AddonManager(Main plugin) {
      this.plugin = plugin;
      this.reload();
   }

   public void reload() {
      if (this.plugin.getCfm().isPlaceholdersAPI()) {
         if (Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {
            this.placeholder = new PlaceholderAPIAddon();
            this.plugin.sendLogMessage("Hooked into §aPlaceholderAPI§e!");
         } else {
            this.plugin.getConfig().set("addons.PlaceholderAPI", false);
            this.plugin.saveConfig();
            this.plugin.getCm().reload();
         }
      }

      if (this.plugin.getCfm().isShopguiplus()) {
         if (Bukkit.getPluginManager().isPluginEnabled("ShopGUIPlus")) {
            this.sellAddons.add(new ShopGUIAddon());
         } else {
            this.plugin.getConfig().set("addons.shopguiplus", false);
            this.plugin.saveConfig();
            this.plugin.getCfm().reload();
         }
      }

      if (this.plugin.getCfm().isEssentials()) {
         if (Bukkit.getPluginManager().isPluginEnabled("Essentials")) {
            this.sellAddons.add(new EssentialsAddon());
         } else {
            this.plugin.getConfig().set("addons.essentials", false);
            this.plugin.saveConfig();
            this.plugin.getCfm().reload();
         }
      }

      if (this.plugin.getCfm().isVault()) {
         if (Bukkit.getPluginManager().isPluginEnabled("Vault")) {
            this.vault = new VaultAddon(this.plugin);
         } else {
            this.plugin.getConfig().set("addons.vault", false);
            this.plugin.saveConfig();
            this.plugin.getCfm().reload();
         }
      }

      if (this.plugin.getCfm().isLuckperms()) {
         if (Bukkit.getPluginManager().isPluginEnabled("LuckPerms")) {
            this.lpa = new LuckPermsAddon(this.plugin);
         } else {
            this.plugin.getConfig().set("addons.luckperms", false);
            this.plugin.saveConfig();
            this.plugin.getCfm().reload();
         }
      }
   }

   public List<String> parsePlaceholders(Player p, List<String> text) {
      return this.placeholder != null ? this.placeholder.parsePlaceholders(p, text) : text;
   }

   public int getMaxPerType(Player p, String key) {
      int max = 0;

      for(PermissionAttachmentInfo attachmentInfo : p.getEffectivePermissions()) {
         String perm = attachmentInfo.getPermission();
         if (perm.startsWith("ultraminions.maxplace." + key)) {
            try {
               int d = Integer.parseInt(perm.replaceFirst("ultraminions.maxplace." + key, ""));
               if (d > max) {
                  max = d;
               }
            } catch (NumberFormatException var8) {
            }
         }
      }

      return max;
   }

   public boolean isProtect(Player p, Location loc) {
      boolean canBuild = false;

      for(ProtectionAddon pa : this.protectionAddons) {
         if (!pa.canBuild(p, loc)) {
            canBuild = true;
         }
      }

      return canBuild;
   }

   public boolean isPricePlugin() {
      return !this.sellAddons.isEmpty();
   }

   public double getPrice(Player p, ItemStack item) {
      double price = (double)0.0F;
      Iterator var5 = this.sellAddons.iterator();
      if (var5.hasNext()) {
         SellAddon sa = (SellAddon)var5.next();
         price = (double)sa.getPrice(p, item);
      }

      return price;
   }

   public boolean isAddon() {
      return this.addon;
   }

   public boolean hasPermission(Player p, String perm) {
      if (p.isOp()) {
         return true;
      } else {
         return this.lpa == null ? p.hasPermission(perm) : this.lpa.hasPermission(p, perm.toLowerCase());
      }
   }

   public Tier getTier(Player p) {
      PlayerData pd = PlayerData.getPlayerUUID(p.getUniqueId());
      return pd != null ? this.plugin.getTm().getTier(pd) : null;
   }

   public int getMaxMinion(Player p) {
      int maxSelected = 0;
      if (this.plugin.getCfm().isUnlockingTiers()) {
         PlayerData pd = PlayerData.getPlayerUUID(p.getUniqueId());
         if (pd != null) {
            maxSelected = this.plugin.getTm().getTier(pd).getMax();
         }
      }

      if (this.plugin.getCfm().isMaxMinionInData()) {
         PlayerData pd = PlayerData.getPlayerUUID(p.getUniqueId());
         if (pd != null && maxSelected < pd.getMaxMinion()) {
            maxSelected = pd.getMaxMinion();
         }
      }

      if (!p.isOp() && !p.hasPermission("minions.max.*")) {
         if (maxSelected < this.plugin.getCfm().getDefaultMaxMinion()) {
            maxSelected = this.plugin.getCfm().getDefaultMaxMinion();
         }

         for(PermissionAttachmentInfo attachmentInfo : p.getEffectivePermissions()) {
            String perm = attachmentInfo.getPermission();
            if (perm.startsWith("minions.max.")) {
               try {
                  int d = Integer.parseInt(perm.replaceFirst("minions.max.", ""));
                  if (d > maxSelected) {
                     maxSelected = d;
                  }
               } catch (NumberFormatException var7) {
                  return maxSelected;
               }
            }
         }

         return maxSelected;
      } else {
         return 999;
      }
   }

   public boolean isStackable(Location b) {
      return false;
   }

   public boolean checkMember(Player p) {
      return false;
   }

   public void addCoins(Player p, double amount) {
      if (this.plugin.getCfm().isVault()) {
         this.vault.addCoins(p, amount);
      }

   }

   public void removeCoins(Player p, double amount) {
      if (this.plugin.getCfm().isVault()) {
         this.vault.removeCoins(p, amount);
      }

   }

   public double getCoins(Player p) {
      if (this.plugin.getCfm().isVault()) {
         return this.vault.getCoins(p);
      } else {
         return (double)0.0F;
      }
   }

   public void createHologram(PlayerMinion pm, Location spawn, List<String> lines) {
      if (this.hasHologramPlugin()) {
         if (this.ha != null) {
            this.ha.createHologram(pm, spawn, lines);
         }
      }
   }

   public void deleteHologram(PlayerMinion pm) {
      if (this.hasHologramPlugin()) {
         if (this.ha != null) {
            this.ha.deleteHologram(pm);
         }
      }
   }

   public boolean hasHologram(PlayerMinion pm) {
      if (!this.hasHologramPlugin()) {
         return false;
      } else {
         return this.ha != null && this.ha.hasHologram(pm);
      }
   }

   public void delete() {
      if (this.hasHologramPlugin()) {
         if (this.ha != null) {
            this.ha.delete();
         }
      }
   }

   public boolean hasEconomyPlugin() {
      return this.vault != null;
   }

   public boolean hasHologramPlugin() {
      return this.ha != null;
   }
}
