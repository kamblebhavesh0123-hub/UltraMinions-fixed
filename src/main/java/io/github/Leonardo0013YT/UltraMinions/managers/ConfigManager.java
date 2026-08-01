package io.github.Leonardo0013YT.UltraMinions.managers;

import io.github.Leonardo0013YT.UltraMinions.Main;
import org.bukkit.Sound;

public class ConfigManager {
   private final Main plugin;
   private Sound upgrade;
   private Sound noUpgrade;
   private boolean secureStop;
   private boolean UltimateClaims;
   private boolean socialHolograms;
   private boolean optimizeOnUnloadChunk;
   private boolean maxMinionInData;
   private boolean MVdWPlaceholderAPI;
   private boolean placeholdersAPI;
   private boolean trHologram;
   private boolean adminBypass;
   private boolean hologramsSystem;
   private boolean stopOnlyFoodLow;
   private boolean goliatapi;
   private boolean autoSaveEnabled;
   private boolean chestLink;
   private boolean offlineWorking;
   private boolean plotsquaredv5;
   private boolean nbtTagsCrafting;
   private boolean cmiholograms;
   private boolean autoMinionEnabled;
   private boolean shopEnabled;
   private boolean debugMode;
   private boolean unlockingTiers;
   private boolean lands;
   private boolean massivefaction;
   private boolean factionsUUID;
   private boolean food;
   private boolean health;
   private boolean permissionToPlace;
   private boolean residence;
   private boolean essentials;
   private boolean shopguiplus;
   private boolean cmi;
   private boolean worldguard;
   private boolean preciousstones;
   private boolean redprotect;
   private boolean griefprevention;
   private boolean protectionstones;
   private boolean towny;
   private boolean removeMinion;
   private boolean openInventory;
   private boolean destroyToRemove;
   private boolean levelPermission;
   private boolean fabledskyblock;
   private boolean holograms;
   private boolean holographicdisplays;
   private boolean superiorskyblock;
   private boolean acidisland;
   private boolean askyblock;
   private boolean bentobox;
   private boolean iridiumskyblock;
   private boolean vault;
   private boolean playerpoints;
   private boolean luckperms;
   private boolean plotsquared;
   private int minutesAutoSave;
   private int defaultMaxMinion;
   private int minionLevel;
   private String minionKey;
   private double x;
   private double y;
   private double z;
   private double addX;
   private double addY;
   private double addZ;

   public ConfigManager(Main plugin) {
      this.plugin = plugin;
      this.reload();
   }

   public void reload() {
      this.secureStop = this.plugin.getConfig().getBoolean("secureStop");
      this.UltimateClaims = this.plugin.getConfig().getBoolean("addons.UltimateClaims");
      this.socialHolograms = this.plugin.getConfig().getBoolean("socialHolograms");
      this.optimizeOnUnloadChunk = this.plugin.getConfig().getBoolean("optimizeOnUnloadChunk");
      this.MVdWPlaceholderAPI = this.plugin.getConfig().getBoolean("addons.MVdWPlaceholderAPI");
      this.placeholdersAPI = this.plugin.getConfig().getBoolean("addons.PlaceholderAPI");
      this.adminBypass = this.plugin.getConfig().getBoolean("adminBypass");
      this.hologramsSystem = this.plugin.getConfig().getBoolean("hologramsSystem");
      this.autoSaveEnabled = this.plugin.getConfig().getBoolean("autoSave.enabled");
      this.minutesAutoSave = this.plugin.getConfig().getInt("autoSave.minutes");
      this.offlineWorking = this.plugin.getConfig().getBoolean("offlineWorking");
      this.nbtTagsCrafting = this.plugin.getConfig().getBoolean("nbtTagsCrafting");
      this.minionLevel = this.plugin.getConfig().getInt("autoSpawnMinion.minionLevel");
      this.addX = this.plugin.getConfig().getDouble("autoSpawnMinion.coordinates.x");
      this.addY = this.plugin.getConfig().getDouble("autoSpawnMinion.coordinates.y");
      this.addZ = this.plugin.getConfig().getDouble("autoSpawnMinion.coordinates.z");
      this.autoMinionEnabled = this.plugin.getConfig().getBoolean("autoSpawnMinion.enabled");
      this.minionKey = this.plugin.getConfig().getString("autoSpawnMinion.minionType");
      this.shopEnabled = this.plugin.getConfig().getBoolean("shopEnabled");
      this.debugMode = this.plugin.getConfig().getBoolean("debugMode");
      this.unlockingTiers = this.plugin.getConfig().getBoolean("unlockingTiers");
      this.lands = this.plugin.getConfig().getBoolean("addons.lands");
      this.massivefaction = this.plugin.getConfig().getBoolean("addons.massivefaction");
      this.factionsUUID = this.plugin.getConfig().getBoolean("addons.factionsUUID");
      this.food = this.plugin.getConfig().getBoolean("stats.food");
      this.health = this.plugin.getConfig().getBoolean("stats.health");
      this.chestLink = this.plugin.getConfig().getBoolean("stats.chestLink");
      this.stopOnlyFoodLow = this.plugin.getConfig().getBoolean("stats.stopOnlyFoodLow");
      this.maxMinionInData = this.plugin.getConfig().getBoolean("stats.maxMinionInData");
      this.x = this.plugin.getConfig().getDouble("rangeCheck.x");
      this.y = this.plugin.getConfig().getDouble("rangeCheck.y");
      this.z = this.plugin.getConfig().getDouble("rangeCheck.z");
      this.upgrade = Sound.valueOf(this.plugin.getConfig().getString("sounds.upgrade"));
      this.noUpgrade = Sound.valueOf(this.plugin.getConfig().getString("sounds.noUpgrade"));
      this.defaultMaxMinion = this.plugin.getConfig().getInt("settings.defaultMaxMinion");
      this.residence = this.plugin.getConfig().getBoolean("addons.residence");
      this.goliatapi = this.plugin.getConfig().getBoolean("addons.goliatapi");
      this.essentials = this.plugin.getConfig().getBoolean("addons.essentials");
      this.shopguiplus = this.plugin.getConfig().getBoolean("addons.shopguiplus");
      this.cmi = this.plugin.getConfig().getBoolean("addons.cmi");
      this.trHologram = this.plugin.getConfig().getBoolean("addons.trHologram");
      this.worldguard = this.plugin.getConfig().getBoolean("addons.worldguard");
      this.preciousstones = this.plugin.getConfig().getBoolean("addons.preciousstones");
      this.redprotect = this.plugin.getConfig().getBoolean("addons.redprotect");
      this.griefprevention = this.plugin.getConfig().getBoolean("addons.griefprevention");
      this.protectionstones = this.plugin.getConfig().getBoolean("addons.protectionstones");
      this.towny = this.plugin.getConfig().getBoolean("addons.towny");
      this.vault = this.plugin.getConfig().getBoolean("addons.vault");
      this.cmiholograms = this.plugin.getConfig().getBoolean("addons.cmiholograms");
      this.playerpoints = this.plugin.getConfig().getBoolean("addons.playerpoints");
      this.fabledskyblock = this.plugin.getConfig().getBoolean("addons.fabledskyblock");
      this.acidisland = this.plugin.getConfig().getBoolean("addons.acidisland");
      this.luckperms = this.plugin.getConfig().getBoolean("addons.luckperms");
      this.askyblock = this.plugin.getConfig().getBoolean("addons.askyblock");
      this.bentobox = this.plugin.getConfig().getBoolean("addons.bentobox");
      this.plotsquared = this.plugin.getConfig().getBoolean("addons.plotsquared");
      this.plotsquaredv5 = this.plugin.getConfig().getBoolean("addons.plotsquaredv5");
      this.levelPermission = this.plugin.getConfig().getBoolean("settings.levelPermission");
      this.iridiumskyblock = this.plugin.getConfig().getBoolean("addons.iridiumskyblock");
      this.superiorskyblock = this.plugin.getConfig().getBoolean("addons.superiorskyblock");
      this.holograms = this.plugin.getConfig().getBoolean("addons.holograms");
      this.holographicdisplays = this.plugin.getConfig().getBoolean("addons.holographicdisplays");
      this.permissionToPlace = this.plugin.getConfig().getBoolean("settings.permissionToPlace");
      this.destroyToRemove = this.plugin.getConfig().getBoolean("settings.destroyToRemove");
      this.openInventory = this.plugin.getConfig().getBoolean("settings.memberIsland.openInventory");
      this.removeMinion = this.plugin.getConfig().getBoolean("settings.memberIsland.removeMinion");
   }

   public boolean isAnyStat() {
      return !this.chestLink && !this.health && !this.food;
   }

   public Main getPlugin() {
      return this.plugin;
   }

   public Sound getUpgrade() {
      return this.upgrade;
   }

   public Sound getNoUpgrade() {
      return this.noUpgrade;
   }

   public boolean isSecureStop() {
      return this.secureStop;
   }

   public boolean isUltimateClaims() {
      return this.UltimateClaims;
   }

   public boolean isSocialHolograms() {
      return this.socialHolograms;
   }

   public boolean isOptimizeOnUnloadChunk() {
      return this.optimizeOnUnloadChunk;
   }

   public boolean isMaxMinionInData() {
      return this.maxMinionInData;
   }

   public boolean isMVdWPlaceholderAPI() {
      return this.MVdWPlaceholderAPI;
   }

   public boolean isPlaceholdersAPI() {
      return this.placeholdersAPI;
   }

   public boolean isTrHologram() {
      return this.trHologram;
   }

   public boolean isAdminBypass() {
      return this.adminBypass;
   }

   public boolean isHologramsSystem() {
      return this.hologramsSystem;
   }

   public boolean isStopOnlyFoodLow() {
      return this.stopOnlyFoodLow;
   }

   public boolean isGoliatapi() {
      return this.goliatapi;
   }

   public boolean isAutoSaveEnabled() {
      return this.autoSaveEnabled;
   }

   public boolean isChestLink() {
      return this.chestLink;
   }

   public boolean isOfflineWorking() {
      return this.offlineWorking;
   }

   public boolean isPlotsquaredv5() {
      return this.plotsquaredv5;
   }

   public boolean isNbtTagsCrafting() {
      return this.nbtTagsCrafting;
   }

   public boolean isCmiholograms() {
      return this.cmiholograms;
   }

   public boolean isAutoMinionEnabled() {
      return this.autoMinionEnabled;
   }

   public boolean isShopEnabled() {
      return this.shopEnabled;
   }

   public boolean isDebugMode() {
      return this.debugMode;
   }

   public boolean isUnlockingTiers() {
      return this.unlockingTiers;
   }

   public boolean isLands() {
      return this.lands;
   }

   public boolean isMassivefaction() {
      return this.massivefaction;
   }

   public boolean isFactionsUUID() {
      return this.factionsUUID;
   }

   public boolean isFood() {
      return this.food;
   }

   public boolean isHealth() {
      return this.health;
   }

   public boolean isPermissionToPlace() {
      return this.permissionToPlace;
   }

   public boolean isResidence() {
      return this.residence;
   }

   public boolean isEssentials() {
      return this.essentials;
   }

   public boolean isShopguiplus() {
      return this.shopguiplus;
   }

   public boolean isCmi() {
      return this.cmi;
   }

   public boolean isWorldguard() {
      return this.worldguard;
   }

   public boolean isPreciousstones() {
      return this.preciousstones;
   }

   public boolean isRedprotect() {
      return this.redprotect;
   }

   public boolean isGriefprevention() {
      return this.griefprevention;
   }

   public boolean isProtectionstones() {
      return this.protectionstones;
   }

   public boolean isTowny() {
      return this.towny;
   }

   public boolean isRemoveMinion() {
      return this.removeMinion;
   }

   public boolean isOpenInventory() {
      return this.openInventory;
   }

   public boolean isDestroyToRemove() {
      return this.destroyToRemove;
   }

   public boolean isLevelPermission() {
      return this.levelPermission;
   }

   public boolean isFabledskyblock() {
      return this.fabledskyblock;
   }

   public boolean isHolograms() {
      return this.holograms;
   }

   public boolean isHolographicdisplays() {
      return this.holographicdisplays;
   }

   public boolean isSuperiorskyblock() {
      return this.superiorskyblock;
   }

   public boolean isAcidisland() {
      return this.acidisland;
   }

   public boolean isAskyblock() {
      return this.askyblock;
   }

   public boolean isBentobox() {
      return this.bentobox;
   }

   public boolean isIridiumskyblock() {
      return this.iridiumskyblock;
   }

   public boolean isVault() {
      return this.vault;
   }

   public boolean isPlayerpoints() {
      return this.playerpoints;
   }

   public boolean isLuckperms() {
      return this.luckperms;
   }

   public boolean isPlotsquared() {
      return this.plotsquared;
   }

   public int getMinutesAutoSave() {
      return this.minutesAutoSave;
   }

   public int getDefaultMaxMinion() {
      return this.defaultMaxMinion;
   }

   public int getMinionLevel() {
      return this.minionLevel;
   }

   public String getMinionKey() {
      return this.minionKey;
   }

   public double getX() {
      return this.x;
   }

   public double getY() {
      return this.y;
   }

   public double getZ() {
      return this.z;
   }

   public double getAddX() {
      return this.addX;
   }

   public double getAddY() {
      return this.addY;
   }

   public double getAddZ() {
      return this.addZ;
   }
}
