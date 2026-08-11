package io.github.Leonardo0013YT.UltraMinions.database;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import io.github.Leonardo0013YT.UltraMinions.Main;
import io.github.Leonardo0013YT.UltraMinions.database.data.PlayerDataSave;
import io.github.Leonardo0013YT.UltraMinions.database.data.PlayerMinionSave;
import io.github.Leonardo0013YT.UltraMinions.database.minion.PlayerMinionChest;
import io.github.Leonardo0013YT.UltraMinions.database.minion.PlayerMinionStat;
import io.github.Leonardo0013YT.UltraMinions.database.minion.PlayerMinionUpgrade;
import io.github.Leonardo0013YT.UltraMinions.interfaces.DataSave;
import io.github.Leonardo0013YT.UltraMinions.interfaces.MinionSave;
import io.github.Leonardo0013YT.UltraMinions.upgrades.UpgradeAutoSell;
import io.github.Leonardo0013YT.UltraMinions.upgrades.UpgradeAutoSmelt;
import io.github.Leonardo0013YT.UltraMinions.upgrades.UpgradeCompressor;
import io.github.Leonardo0013YT.UltraMinions.upgrades.UpgradeFuel;
import io.github.Leonardo0013YT.UltraMinions.utils.Utils;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class Database {
   private static boolean enabled;
   private static String SAVE = "UPDATE UltraMinions SET Data=? WHERE UUID=?";
   private Main plugin;
   private HikariDataSource hikari;
   private Connection connection;

   public Database(Main plugin) {
      this.plugin = plugin;
      enabled = plugin.getConfig().getBoolean("mysql.enabled");
      if (enabled) {
         int port = plugin.getConfig().getInt("mysql.port");
         String ip = plugin.getConfig().getString("mysql.host");
         String database = plugin.getConfig().getString("mysql.database");
         String username = plugin.getConfig().getString("mysql.username");
         String password = plugin.getConfig().getString("mysql.password");
         String connectionString = "jdbc:mysql://" + ip + ":" + port + "/" + database + "?autoReconnect=true";
         HikariConfig config = new HikariConfig();
         config.setJdbcUrl(connectionString);
         config.setUsername(username);
         config.setPassword(password);
         config.addDataSourceProperty("databaseName", database);
         config.setDriverClassName("com.mysql.jdbc.Driver");
         config.addDataSourceProperty("cachePrepStmts", true);
         config.addDataSourceProperty("prepStmtCacheSize", 250);
         config.addDataSourceProperty("prepStmtCacheSqlLimit", 2048);
         config.addDataSourceProperty("useServerPrepStmts", true);
         config.addDataSourceProperty("useLocalSessionState", true);
         config.addDataSourceProperty("rewriteBatchedStatements", true);
         config.addDataSourceProperty("cacheResultSetMetadata", true);
         config.addDataSourceProperty("cacheServerConfiguration", true);
         config.addDataSourceProperty("elideSetAutoCommits", true);
         config.addDataSourceProperty("maintainTimeStats", false);
         config.addDataSourceProperty("characterEncoding", "utf8");
         config.addDataSourceProperty("encoding", "UTF-8");
         config.addDataSourceProperty("useUnicode", "true");
         config.addDataSourceProperty("useSSL", false);
         config.addDataSourceProperty("tcpKeepAlive", true);
         config.setPoolName("UltraMinions " + UUID.randomUUID().toString());
         config.setMaxLifetime(Long.MAX_VALUE);
         config.setMinimumIdle(0);
         config.setIdleTimeout(30000L);
         config.setConnectionTimeout(10000L);
         config.setMaximumPoolSize(30);
         this.hikari = new HikariDataSource(config);
         this.createTable();
         plugin.sendLogMessage("§eMySQL connected correctly.");
      } else {
         File DataFile = new File(plugin.getDataFolder(), "/UltraMinions.db");
         if (!DataFile.exists()) {
            try {
               DataFile.createNewFile();
            } catch (IOException ex) {
               ex.printStackTrace();
               Bukkit.getPluginManager().disablePlugin(plugin);
            }
         }

         try {
            Class.forName("org.sqlite.JDBC");

            try {
               this.connection = DriverManager.getConnection("jdbc:sqlite:" + DataFile);
               plugin.sendLogMessage("§eSQLLite connected correctly.");
               this.createTable();
            } catch (SQLException ex2) {
               ex2.printStackTrace();
               Bukkit.getPluginManager().disablePlugin(plugin);
            }
         } catch (ClassNotFoundException ex3) {
            ex3.printStackTrace();
            Bukkit.getPluginManager().disablePlugin(plugin);
         }
      }

   }

   public void close() {
      if (enabled) {
         if (this.hikari != null) {
            this.hikari.close();
         }
      } else if (this.connection != null) {
         try {
            this.connection.close();
         } catch (SQLException e) {
            e.printStackTrace();
         }
      }

   }

   private Connection getConnection() {
      return this.connection;
   }

   private HikariDataSource getHikari() {
      return this.hikari;
   }

   private void createTable() {
      if (enabled) {
         try {
            Connection connection = this.hikari.getConnection();
            Statement statement = connection.createStatement();
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS UltraMinions(UUID varchar(36) primary key, Name varchar(20), Data LONGTEXT);");
            statement.executeUpdate("ALTER TABLE UltraMinions MODIFY Data LONGTEXT;");
         } catch (SQLException e) {
            e.printStackTrace();
         }
      } else {
         try {
            Statement statement = this.connection.createStatement();
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS UltraMinions(UUID varchar(36) primary key, Name varchar(20), Data LONGTEXT);");
         } catch (SQLException e) {
            e.printStackTrace();
         }
      }

   }

   public void loadPlayer(final Player p) {
      (new BukkitRunnable() {
         public void run() {
            if (!p.isOnline()) {
               this.cancel();
            } else {
               String SELECT = "SELECT * FROM UltraMinions WHERE UUID=?";
               if (Database.enabled) {
                  try {
                     Connection connection = Database.this.hikari.getConnection();
                     String INSERT = "INSERT INTO UltraMinions VALUES(?,?,?) ON DUPLICATE KEY UPDATE Name=?";
                     PreparedStatement insert = connection.prepareStatement(INSERT);
                     PreparedStatement select = connection.prepareStatement(SELECT);
                     insert.setString(1, p.getUniqueId().toString());
                     insert.setString(2, p.getName());
                     insert.setString(3, Main.toDataString(new PlayerDataSave()));
                     insert.setString(4, p.getName());
                     insert.execute();
                     select.setString(1, p.getUniqueId().toString());
                     ResultSet result = select.executeQuery();
                     if (result.next()) {
                        Database.this.loadData(p, Main.fromDataString(result.getString("Data")));
                     }

                     Database.this.close(connection, insert, result);
                     Database.this.close((Connection)null, select, (ResultSet)null);
                  } catch (SQLException var9) {
                  }
               } else {
                  try {
                     String INSERT2 = "INSERT INTO `UltraMinions` (`UUID`, `Name`, `Data`) VALUES (?, ?, ?);";
                     PreparedStatement insert = Database.this.connection.prepareStatement(INSERT2);
                     PreparedStatement select = Database.this.connection.prepareStatement(SELECT);
                     select.setString(1, p.getUniqueId().toString());
                     ResultSet result = select.executeQuery();
                     if (result.next()) {
                        Database.this.loadData(p, Main.fromDataString(result.getString("Data")));
                     } else {
                        insert.setString(1, p.getUniqueId().toString());
                        insert.setString(2, p.getName());
                        insert.setString(3, Main.toDataString(new PlayerDataSave()));
                        insert.executeUpdate();
                        PreparedStatement select2 = Database.this.connection.prepareStatement(SELECT);
                        select2.setString(1, p.getUniqueId().toString());
                        ResultSet result2 = select2.executeQuery();
                        if (result2.next()) {
                           Database.this.loadData(p, Main.fromDataString(result2.getString("Data")));
                        }

                        Database.this.close(Database.this.connection, select2, result2);
                     }

                     Database.this.close(Database.this.connection, insert, result);
                     Database.this.close(Database.this.connection, select, (ResultSet)null);
                  } catch (SQLException var8) {
                  }
               }

            }
         }
      }).runTaskAsynchronously(this.plugin);
   }

   public void savePlayer(final Player p) {
      PlayerData pd = PlayerData.getPlayerData(p);
      if (pd != null && pd.isLoaded()) {
         final DataSave ps = this.playerDataToDataSave(pd, false, false);
         if (ps != null) {
            (new BukkitRunnable() {
               public void run() {
                  if (Database.enabled) {
                     try {
                        Connection connection = Database.this.hikari.getConnection();
                        PreparedStatement statement = connection.prepareStatement(Database.SAVE);
                        statement.setString(1, Main.toDataString(ps));
                        statement.setString(2, p.getUniqueId().toString());
                        statement.execute();
                        Database.this.close(connection, statement, (ResultSet)null);
                        PlayerData.remove(p);
                     } catch (SQLException e) {
                        e.printStackTrace();
                     }
                  } else {
                     try {
                        Connection connection = Database.this.getConnection();
                        PreparedStatement statement = connection.prepareStatement(Database.SAVE);
                        statement.setString(1, Main.toDataString(ps));
                        statement.setString(2, p.getUniqueId().toString());
                        statement.execute();
                        Database.this.close(connection, statement, (ResultSet)null);
                        PlayerData.remove(p);
                     } catch (SQLException e) {
                        e.printStackTrace();
                     }
                  }

               }
            }).runTaskAsynchronously(this.plugin);
         }
      }
   }

   public void savePlayerSync(UUID p) {
      PlayerData pd = PlayerData.getPlayerUUID(p);
      if (pd != null) {
         DataSave ps = this.playerDataToDataSave(pd, true, false);
         if (ps != null) {
            if (enabled) {
               try {
                  Connection connection = this.hikari.getConnection();
                  PreparedStatement statement = connection.prepareStatement(SAVE);
                  statement.setString(1, Main.toDataString(ps));
                  statement.setString(2, p.toString());
                  statement.execute();
                  this.close(connection, statement, (ResultSet)null);
                  PlayerData.remove(p);
               } catch (SQLException e) {
                  e.printStackTrace();
               }
            } else {
               try {
                  Connection connection = this.getConnection();
                  PreparedStatement statement = connection.prepareStatement(SAVE);
                  statement.setString(1, Main.toDataString(ps));
                  statement.setString(2, p.toString());
                  statement.execute();
                  this.close(connection, statement, (ResultSet)null);
                  PlayerData.remove(p);
               } catch (SQLException e) {
                  e.printStackTrace();
               }
            }

         }
      }
   }

   /**
    * Saves a player's current minion data to the database synchronously,
    * without removing them from the in-memory tracking map. Used by
    * /minions save, where minions must keep working for online players
    * after the save completes (unlike savePlayerSync, which is only
    * used during shutdown/logout when tracking should stop anyway).
    */
   public void savePlayerSyncKeepAlive(UUID p) {
      PlayerData pd = PlayerData.getPlayerUUID(p);
      if (pd != null && pd.isLoaded()) {
         // IMPORTANT:
         // /minions save must save the data WITHOUT destroying
         // the currently spawned minions.
         DataSave ps = this.playerDataToDataSave(pd, true, true);
         if (ps != null) {
            if (enabled) {
               try {
                  Connection connection = this.hikari.getConnection();
                  PreparedStatement statement = connection.prepareStatement(SAVE);
                  statement.setString(1, Main.toDataString(ps));
                  statement.setString(2, p.toString());
                  statement.execute();
                  this.close(connection, statement, (ResultSet)null);
               } catch (SQLException e) {
                  e.printStackTrace();
               }
            } else {
               try {
                  Connection connection = this.getConnection();
                  PreparedStatement statement = connection.prepareStatement(SAVE);
                  statement.setString(1, Main.toDataString(ps));
                  statement.setString(2, p.toString());
                  statement.execute();
                  this.close(connection, statement, (ResultSet)null);
               } catch (SQLException e) {
                  e.printStackTrace();
               }
            }
         }
      }
   }

   /**
    * Reads every player's saved minion data directly from the database
    * (online or offline) and hands back a flat list of lightweight
    * MinionRecord summaries (owner, type, world, coordinates, level).
    * Runs asynchronously; the callback is invoked back on the main
    * thread once the scan completes.
    */
   public void getAllMinionRecords(java.util.function.Consumer<List<MinionRecord>> callback) {
      (new BukkitRunnable() {
         public void run() {
            List<MinionRecord> records = new ArrayList<>();
            try {
               Connection connection = Database.enabled ? Database.this.hikari.getConnection() : Database.this.getConnection();
               PreparedStatement select = connection.prepareStatement("SELECT UUID, Name, Data FROM UltraMinions");
               ResultSet result = select.executeQuery();

               while (result.next()) {
                  String name = result.getString("Name");
                  String dataStr = result.getString("Data");
                  if (dataStr == null) {
                     continue;
                  }

                  try {
                     DataSave ds = Main.fromDataString(dataStr);
                     if (ds == null || ds.getData() == null) {
                        continue;
                     }

                     for (String minionStr : ds.getData()) {
                        try {
                           MinionSave ms = Main.fromMinionString(minionStr);
                           String[] loc = ms.getLoc().split(";");
                           records.add(new MinionRecord(name, ms.getKey(), loc[0],
                              Double.parseDouble(loc[1]), Double.parseDouble(loc[2]), Double.parseDouble(loc[3]),
                              ms.getLevel()));
                        } catch (Exception ignored) {
                        }
                     }
                  } catch (Exception ignored) {
                  }
               }

               Database.this.close(connection, select, result);
            } catch (SQLException e) {
               e.printStackTrace();
            }

            Bukkit.getScheduler().runTask(Database.this.plugin, () -> callback.accept(records));
         }
      }).runTaskAsynchronously(this.plugin);
   }

   public void autoSave() {
      (new BukkitRunnable() {
         public void run() {
            for(Player p : Bukkit.getOnlinePlayers()) {
               PlayerData pd = PlayerData.getPlayerData(p);
               if (pd == null || !pd.isLoaded()) {
                  continue;
               }

               DataSave ps = Database.this.playerDataToDataSave(pd, true, true);
               if (ps == null) {
                  continue;
               }

               if (Database.enabled) {
                  try {
                     Connection connection = Database.this.hikari.getConnection();
                     PreparedStatement statement = connection.prepareStatement(Database.SAVE);
                     statement.setString(1, Main.toDataString(ps));
                     statement.setString(2, p.getUniqueId().toString());
                     statement.execute();
                     Database.this.close(connection, statement, (ResultSet)null);
                  } catch (SQLException e) {
                     e.printStackTrace();
                  }
               } else {
                  try {
                     Connection connection = Database.this.getConnection();
                     PreparedStatement statement = connection.prepareStatement(Database.SAVE);
                     statement.setString(1, Main.toDataString(ps));
                     statement.setString(2, p.getUniqueId().toString());
                     statement.execute();
                     Database.this.close(connection, statement, (ResultSet)null);
                  } catch (SQLException e) {
                     e.printStackTrace();
                  }
               }
            }

         }
      }).runTaskAsynchronously(this.plugin);
      this.plugin.sendLogMessage("Minions have been saved automatically.");
   }

   private void close(Connection connection, PreparedStatement statement, ResultSet result) {
      if (enabled && connection != null) {
         try {
            connection.close();
         } catch (SQLException e) {
            e.printStackTrace();
         }
      }

      if (statement != null) {
         try {
            statement.close();
         } catch (SQLException e) {
            e.printStackTrace();
         }
      }

      if (result != null) {
         try {
            result.close();
         } catch (SQLException e) {
            e.printStackTrace();
         }
      }

   }

   public void loadData(Player p, DataSave loadPlayer) {
      Bukkit.getScheduler().scheduleSyncDelayedTask(this.plugin, () -> {
         if (p != null && p.isOnline()) {
            PlayerData pd = new PlayerData(p.getUniqueId());
            pd.setLastLogin(loadPlayer.getLastLogin());
            pd.setUnlocked(loadPlayer.getUnlocked());
            pd.setLevels(loadPlayer.getLevels());

            for(String minion : new ArrayList<String>(loadPlayer.getData())) {
               MinionSave ms = Main.fromMinionString(minion);
               if (!this.plugin.getMm().getMinions().containsKey(ms.getKey())) {
                  loadPlayer.getData().remove(minion);
                  continue;
               }

               PlayerMinion pm = new PlayerMinion(Utils.getStringLocation(ms.getLoc()), ms.getKey(), p);
               pm.setStat(new PlayerMinionStat(pm, ms.getLevel(), ms.getGenerated(), ms.getWork(), ms.getSleep(), ms.getFood(), ms.getHealth(), ms.getFuelTime()));
               PlayerMinionUpgrade upgrade = new PlayerMinionUpgrade(pm);
               UpgradeFuel f = this.plugin.getUm().getFuel(ms.getFuel());
               if (f != null) {
                  upgrade.setFuel(f);
                  pm.getStat().setAmountFuel(ms.getFuelAmount());
                  pm.getStat().setTotalFuel(ms.getTotalFuel());
               }

               UpgradeCompressor c = this.plugin.getUm().getCompressor(ms.getCompressor());
               if (c != null) {
                  upgrade.setCompressor(c);
               }

               UpgradeAutoSmelt as = this.plugin.getUm().getAutoSmelt(ms.getAutoSmelt());
               if (as != null) {
                  upgrade.setAutoSmelt(as);
               }

               UpgradeAutoSell al = this.plugin.getUm().getAutoSell(ms.getAutoSell());
               if (al != null) {
                  upgrade.setAutoSell(al);
               }

               pm.setUpgrade(upgrade);
               if (pm.getSpawn().getChunk().isLoaded()) {
                  pm.firstSpawn();
                  pd.getTypes().put(pm.getKey(), (Integer)pd.getTypes().getOrDefault(pm.getKey(), 0) + 1);
               }

               pm.setSkin(ms.getSkin());
               if (ms.isChest()) {
                  Block b = Utils.getStringLocation(ms.getChest()).getBlock();
                  if (b.getType().equals(Material.CHEST)) {
                     pm.setChest(new PlayerMinionChest(Utils.getStringLocation(ms.getChest())));
                  } else {
                     ms.setChest(false);
                  }
               }

               pm.setChest(ms.isChest());
               int a;
               if (this.plugin.getCfm().isOfflineWorking()) {
                  a = this.getActions(pm, pd.getLastLogin(), pm.getDelay(), pm.getStat().getFood(), pm.getStat().getHealth());
               } else {
                  a = 0;
               }

               pm.setActions(ms.getActions() + a);
               pd.getMinions().put(pm.getId(), pm);
               if (!pm.getSpawn().getChunk().isLoaded()) {
                  this.plugin.getMm().getToSpawn().add(pm);
               }
            }

            pd.setLoaded(true);
         }
      });
   }

   public PlayerDataSave playerDataToDataSave(PlayerData pd, boolean sync, boolean autoSave) {
      PlayerDataSave pds = new PlayerDataSave();
      ArrayList<String> minionSaves = new ArrayList();
      pds.setUuid(pd.getUuid());
      pds.setLastLogin(System.currentTimeMillis());
      pds.setUnlocked(pd.getUnlocked());
      pds.setLevels(pd.getLevels());

      for(PlayerMinion pm : pd.getMinions().values()) {
         PlayerMinionSave pms = new PlayerMinionSave();
         pms.setActions(pm.getActions());
         pms.setKey(pm.getKey());
         pms.setType(pm.getType().name());
         pms.setSkin(pm.getSkin());
         pms.setLoc(Utils.getLocationString(pm.getSpawn()));
         pms.setLevel(pm.getStat().getLevel());
         pms.setGenerated(pm.getStat().getGenerated());
         pms.setWork(pm.getStat().getWork());
         pms.setSleep(pm.getStat().getSleep());
         pms.setFood(pm.getStat().getFood());
         pms.setHealth(pm.getStat().getHealth());
         pms.setFuelTime(pm.getStat().getFuel());
         if (pm.getUpgrade().getFuel() != null) {
            pms.setFuel(pm.getUpgrade().getFuel().getName());
            pms.setFuelAmount(pm.getStat().getAmountFuel());
            pms.setTotalFuel(pm.getStat().getTotalFuel());
         }

         if (pm.getUpgrade().getCompressor() != null) {
            pms.setCompressor(pm.getUpgrade().getCompressor().getName());
         }

         if (pm.getUpgrade().getAutoSmelt() != null) {
            pms.setAutoSmelt(pm.getUpgrade().getAutoSmelt().getName());
         }

         if (pm.getUpgrade().getAutoSell() != null) {
            pms.setAutoSell(pm.getUpgrade().getAutoSell().getName());
         }

         pms.setChest(pm.isChest());
         if (pm.isChest()) {
            pms.setChest(Utils.getLocationString(pm.getChest().getLoc()));
         }

         minionSaves.add(Main.toMinionString(pms));
      }

      // Entity cleanup happens in its own pass, after every minion's data
      // has already been safely captured above. This way, if destroying
      // one minion's entity ever fails or throws, it cannot prevent the
      // other minions' data from being saved - each entry above is already
      // safe in minionSaves by this point.
      if (!autoSave) {
         for (PlayerMinion pm : pd.getMinions().values()) {
            try {
               this.removeWhenOffline(pm, sync);
            } catch (Exception e) {
               this.plugin.sendLogMessage("Failed to clean up minion entity for " + pd.getUuid() + " (data was still saved): " + e.getMessage());
            }
         }
      }

      pds.setData(minionSaves);
      this.plugin.getMm().getToSpawn().removeAll(pd.getMinions().values());
      return pds;
   }

   public void removeWhenOffline(PlayerMinion pm, boolean sync) {
      if (!sync && pm.getArmor() != null) {
         this.plugin.getMm().getActiveMinions().remove(pm.getArmor().getUniqueId());
         if (this.plugin.getAdm().hasHologramPlugin() && this.plugin.getAdm().hasHologram(pm)) {
            this.plugin.getAdm().deleteHologram(pm);
         }

         pm.destroy();
      }
   }

   public int getActions(PlayerMinion pm, long lastLogin, int delay, int food, int health) {
      int passed = (int)((System.currentTimeMillis() - lastLogin) / 1000L / (long)delay);
      if (!this.plugin.getCfm().isHealth() && !this.plugin.getCfm().isFood()) {
         return passed / pm.getMinion().getType().getActions();
      } else {
         int posActions = 0;
         if (this.plugin.getCfm().isHealth()) {
            posActions += health;
         }

         if (this.plugin.getCfm().isFood()) {
            posActions += food;
         }

         if (posActions == 0) {
            return 0;
         } else if (posActions > 0 && (this.plugin.getCfm().isHealth() || this.plugin.getCfm().isFood()) && passed > posActions) {
            pm.getStat().setFood(0);
            pm.getStat().setHealth(0);
            return posActions / pm.getMinion().getType().getActions();
         } else {
            if (this.plugin.getCfm().isHealth() || this.plugin.getCfm().isFood()) {
               int consumed;
               int nFood;
               if (passed >= food) {
                  consumed = passed - food;
                  nFood = 0;
               } else {
                  nFood = food - passed;
                  consumed = 0;
               }

               int nHealth;
               if (consumed >= health) {
                  nHealth = 0;
               } else {
                  nHealth = health - consumed;
               }

               pm.getStat().setFood(nFood);
               pm.getStat().setHealth(nHealth);
            }

            return passed / pm.getMinion().getType().getActions();
         }
      }
   }

   public boolean checkOtherMinion(Player p, Location loc) {
      if (!p.getWorld().getName().equals(loc.getWorld().getName())) {
         return false;
      } else {
         ArrayList<Entity> entities = new ArrayList(loc.getWorld().getNearbyEntities(loc, (double)0.5F, (double)0.5F, (double)0.5F));
         entities.removeIf((entity) -> !entity.getType().equals(EntityType.ARMOR_STAND));

         for(Entity as : entities) {
            UUID uuid = as.getUniqueId();
            if (Utils.isMinionUUID(uuid)) {
               PlayerMinion pm = (PlayerMinion)this.plugin.getMm().getActiveMinions().get(uuid);
               return pm != null;
            }
         }

         return false;
      }
   }
}
