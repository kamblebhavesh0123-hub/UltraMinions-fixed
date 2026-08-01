package io.github.Leonardo0013YT.UltraMinions;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.github.Leonardo0013YT.UltraMinions.adapters.InterfaceDataSave;
import io.github.Leonardo0013YT.UltraMinions.adapters.InterfaceMinionSave;
import io.github.Leonardo0013YT.UltraMinions.cmds.SetupCMD;
import io.github.Leonardo0013YT.UltraMinions.database.Database;
import io.github.Leonardo0013YT.UltraMinions.database.PlayerData;
import io.github.Leonardo0013YT.UltraMinions.database.PlayerMinion;
import io.github.Leonardo0013YT.UltraMinions.interfaces.DataSave;
import io.github.Leonardo0013YT.UltraMinions.interfaces.MinionSave;
import io.github.Leonardo0013YT.UltraMinions.listeners.CraftListener;
import io.github.Leonardo0013YT.UltraMinions.listeners.MenuListener;
import io.github.Leonardo0013YT.UltraMinions.listeners.PlayerListener;
import io.github.Leonardo0013YT.UltraMinions.listeners.SetupListener;
import io.github.Leonardo0013YT.UltraMinions.managers.AddonManager;
import io.github.Leonardo0013YT.UltraMinions.managers.AnimationManager;
import io.github.Leonardo0013YT.UltraMinions.managers.ConfigManager;
import io.github.Leonardo0013YT.UltraMinions.managers.CraftManager;
import io.github.Leonardo0013YT.UltraMinions.managers.FoodManager;
import io.github.Leonardo0013YT.UltraMinions.managers.HologramManager;
import io.github.Leonardo0013YT.UltraMinions.managers.MinionManager;
import io.github.Leonardo0013YT.UltraMinions.managers.SetupManager;
import io.github.Leonardo0013YT.UltraMinions.managers.ShopManager;
import io.github.Leonardo0013YT.UltraMinions.managers.SkinManager;
import io.github.Leonardo0013YT.UltraMinions.managers.TiersManager;
import io.github.Leonardo0013YT.UltraMinions.managers.UpgradeManager;
import io.github.Leonardo0013YT.UltraMinions.menus.MinionMenu;
import io.github.Leonardo0013YT.UltraMinions.menus.SetupMenu;
import io.github.Leonardo0013YT.UltraMinions.placeholders.Placeholders;
import io.github.Leonardo0013YT.UltraMinions.utils.MetricsLite;
import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

public class Main extends JavaPlugin {
   private static Main instance;
   private static Gson dataSave;
   private static Gson dataMinion;
   private boolean stop = false;
   private Settings lang;
   private Settings upgrades;
   private Settings skins;
   private Settings foods;
   private Settings tiers;
   private Settings shop;
   private Settings temp;
   private MinionManager mm;
   private SetupManager sm;
   private UpgradeManager um;
   private ConfigManager cfm;
   private AddonManager adm;
   private CraftManager cm;
   private AnimationManager am;
   private HologramManager hm;
   private FoodManager fm;
   private SkinManager skm;
   private TiersManager tm;
   private SetupMenu sem;
   private MinionMenu mem;
   private ShopManager shm;
   private BukkitTask task;
   private Database db;

   public static Main get() {
      return instance;
   }

   public static String toDataString(DataSave ds) {
      return dataSave.toJson(ds, DataSave.class);
   }

   public static DataSave fromDataString(String data) {
      return (DataSave)dataSave.fromJson(data, DataSave.class);
   }

   public static String toMinionString(MinionSave ms) {
      return dataMinion.toJson(ms, MinionSave.class);
   }

   public static MinionSave fromMinionString(String data) {
      return (MinionSave)dataMinion.fromJson(data, MinionSave.class);
   }

   public void onEnable() {
      instance = this;
      dataSave = (new GsonBuilder()).registerTypeAdapter(DataSave.class, new InterfaceDataSave()).create();
      dataMinion = (new GsonBuilder()).registerTypeAdapter(MinionSave.class, new InterfaceMinionSave()).create();
      this.setupSounds();
      this.getConfig().options().copyDefaults(true);
      this.saveConfig();
      File m = new File(this.getDataFolder(), "minions");
      this.saveAnimations();
      if (!m.exists()) {
         m.mkdirs();
         this.saveMinions();
      }

      this.hm = new HologramManager(this);
      this.temp = new Settings(this, "temp", true, false);
      this.lang = new Settings(this, "lang", true, true);
      this.foods = new Settings(this, "foods", false, false);
      this.upgrades = new Settings(this, "upgrades", false, false);
      this.skins = new Settings(this, "skins", false, false);
      this.tiers = new Settings(this, "tiers", false, false);
      this.shop = new Settings(this, "shop", false, false);
      this.hm.reload();
      this.db = new Database(this);
      this.cfm = new ConfigManager(this);
      this.cm = new CraftManager(this);
      this.mm = new MinionManager(this);
      this.sm = new SetupManager();
      this.um = new UpgradeManager(this);
      this.adm = new AddonManager(this);
      this.am = new AnimationManager(this);
      this.skm = new SkinManager(this);
      this.fm = new FoodManager(this);
      this.sem = new SetupMenu(this);
      this.mem = new MinionMenu(this);
      this.tm = new TiersManager(this);
      this.shm = new ShopManager(this);
      this.shm.loadShop();
      if (this.getServer().getPluginManager().isPluginEnabled("PlaceholderAPI")) {
         (new Placeholders(this)).register();
      }

      this.getCommand("msetup").setExecutor(new SetupCMD(this));
      this.getServer().getPluginManager().registerEvents(new SetupListener(this), this);
      this.getServer().getPluginManager().registerEvents(new PlayerListener(this), this);
      this.getServer().getPluginManager().registerEvents(new MenuListener(this), this);
      this.getServer().getPluginManager().registerEvents(new CraftListener(this), this);
      this.task = (new BukkitRunnable() {
         public void run() {
            (new HashSet<PlayerData>(PlayerData.getPlayers().values())).forEach((pd) -> pd.getMinions().values().forEach(PlayerMinion::update));
         }
      }).runTaskTimer(this, 20L, 20L);
      if (!this.getCfm().isAutoSaveEnabled()) {
         (new BukkitRunnable() {
            public void run() {
               Main.this.getDb().autoSave();
            }
         }).runTaskTimer(this, 1200L * (long)this.getCfm().getMinutesAutoSave(), 1200L * (long)this.getCfm().getMinutesAutoSave());
      }

      new MetricsLite(this, 9622);
   }

   public void onDisable() {
      if (this.task != null) {
         this.task.cancel();
      }

      if (!this.getCfm().isSecureStop()) {
         for(PlayerData pd : new ArrayList<PlayerData>(PlayerData.getPlayers().values())) {
            this.getDb().savePlayerSync(pd.getUuid());
         }
      }

      this.getTemp().set("minionsData", (Object)null);
      this.getTemp().save();
      this.db.close();
   }

   public boolean isStop() {
      return this.stop;
   }

   public void setStop(boolean stop) {
      this.stop = stop;
   }

   private void setupSounds() {
      this.getConfig().addDefault("sounds.upgrade", "ENTITY_PLAYER_LEVELUP");
      this.getConfig().addDefault("sounds.noUpgrade", "ENTITY_ENDERMAN_TELEPORT");
   }

   private void saveAnimations() {
      if (this.checkFile("picar.animc")) {
         this.saveResource("picar.animc", false);
      }

      if (this.checkFile("lumberjack.animc")) {
         this.saveResource("lumberjack.animc", false);
      }

   }

   private void saveMinions() {
      if (this.checkFile("minions/cactus.yml")) {
         this.saveResource("minions/cactus.yml", false);
      }

      if (this.checkFile("minions/cobblestone.yml")) {
         this.saveResource("minions/cobblestone.yml", false);
      }

      if (this.checkFile("minions/fisher_minion.yml")) {
         this.saveResource("minions/fisher_minion.yml", false);
      }

      if (this.checkFile("minions/iron_ore.yml")) {
         this.saveResource("minions/iron_ore.yml", false);
      }

      if (this.checkFile("minions/wheat_minion.yml")) {
         this.saveResource("minions/wheat_minion.yml", false);
      }

      if (this.checkFile("minions/sugarcane.yml")) {
         this.saveResource("minions/sugarcane.yml", false);
      }

      if (this.checkFile("minions/nether_wart.yml")) {
         this.saveResource("minions/nether_wart.yml", false);
      }

      if (this.checkFile("minions/collector.yml")) {
         this.saveResource("minions/collector.yml", false);
      }

      if (this.checkFile("minions/seller.yml")) {
         this.saveResource("minions/seller.yml", false);
      }

      if (this.checkFile("minions/pig_minion.yml")) {
         this.saveResource("minions/pig_minion.yml", false);
      }

      if (this.checkFile("minions/skeleton_minion.yml")) {
         this.saveResource("minions/skeleton_minion.yml", false);
      }

      if (this.checkFile("minions/coal.yml")) {
         this.saveResource("minions/coal.yml", false);
      }

      if (this.checkFile("minions/cow.yml")) {
         this.saveResource("minions/cow.yml", false);
      }

      if (this.checkFile("minions/diamond.yml")) {
         this.saveResource("minions/diamond.yml", false);
      }

      if (this.checkFile("minions/emerald.yml")) {
         this.saveResource("minions/emerald.yml", false);
      }

      if (this.checkFile("minions/enderman.yml")) {
         this.saveResource("minions/enderman.yml", false);
      }

      if (this.checkFile("minions/gold.yml")) {
         this.saveResource("minions/gold.yml", false);
      }

      if (this.checkFile("minions/lapis.yml")) {
         this.saveResource("minions/lapis.yml", false);
      }

      if (this.checkFile("minions/redstone.yml")) {
         this.saveResource("minions/redstone.yml", false);
      }

      if (this.checkFile("minions/sheep.yml")) {
         this.saveResource("minions/sheep.yml", false);
      }

      if (this.checkFile("minions/zombie.yml")) {
         this.saveResource("minions/zombie.yml", false);
      }

   }

   private boolean checkFile(String path) {
      return !(new File(this.getDataFolder(), path)).exists();
   }

   public void reload() {
      this.reloadConfig();
      this.foods.reload();
      this.lang.reload();
      this.upgrades.reload();
      this.tiers.reload();
      this.shop.reload();
      this.cm.reload();
      this.mm.loadMinions();
      this.cfm.reload();
      this.adm.delete();
      this.adm.reload();
      this.hm.reload();
      this.tm.loadTiers();
      this.um.reload();
      this.am.reload();
      this.skm.loadSkins();
      this.shm.loadShop();
   }

   public void sendLogMessage(String msg) {
      Bukkit.getConsoleSender().sendMessage("§a[§cUltraMinions§a] §e" + msg);
   }

   public void sendDebugMessage(String msg) {
      if (this.getCfm().isDebugMode()) {
         Bukkit.getConsoleSender().sendMessage("§a[§bUltraMinions Debug§a] §e" + msg);
      }

   }

   public Settings getTemp() {
      return this.temp;
   }

   public Database getDb() {
      return this.db;
   }

   public ShopManager getShm() {
      return this.shm;
   }

   public Settings getTiers() {
      return this.tiers;
   }

   public TiersManager getTm() {
      return this.tm;
   }

   public Settings getFoods() {
      return this.foods;
   }

   public FoodManager getFm() {
      return this.fm;
   }

   public HologramManager getHm() {
      return this.hm;
   }

   public MinionMenu getMem() {
      return this.mem;
   }

   public SkinManager getSkm() {
      return this.skm;
   }

   public AnimationManager getAm() {
      return this.am;
   }

   public CraftManager getCm() {
      return this.cm;
   }

   public AddonManager getAdm() {
      return this.adm;
   }

   public Settings getUpgrades() {
      return this.upgrades;
   }

   public Settings getLang() {
      return this.lang;
   }

   public Settings getShop() {
      return this.shop;
   }

   public ConfigManager getCfm() {
      return this.cfm;
   }

   public UpgradeManager getUm() {
      return this.um;
   }

   public SetupMenu getSem() {
      return this.sem;
   }

   public MinionManager getMm() {
      return this.mm;
   }

   public SetupManager getSm() {
      return this.sm;
   }

   public Settings getSkins() {
      return this.skins;
   }
}
