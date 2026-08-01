package io.github.Leonardo0013YT.UltraMinions.database;

import java.util.HashMap;
import java.util.UUID;
import org.bukkit.entity.Player;

public class PlayerData {
   private static final HashMap<UUID, PlayerData> players = new HashMap();
   private final UUID uuid;
   private final HashMap<String, PlayerMinion> minions = new HashMap();
   private final HashMap<String, Integer> types = new HashMap();
   private int unlocked;
   private int maxMinion = 3;
   private long lastLogin;
   private HashMap<String, Integer> levels = new HashMap();

   public PlayerData(UUID uuid) {
      this.uuid = uuid;
      players.put(uuid, this);
   }

   public static PlayerData getPlayerData(Player p) {
      return (PlayerData)players.get(p.getUniqueId());
   }

   public static PlayerData getPlayerUUID(UUID uuid) {
      return (PlayerData)players.get(uuid);
   }

   public static void remove(Player p) {
      players.remove(p.getUniqueId());
   }

   public static void remove(UUID p) {
      players.remove(p);
   }

   public static HashMap<UUID, PlayerData> getPlayers() {
      return players;
   }

   public boolean isNewUnlocked(String key, int level) {
      if (!this.levels.containsKey(key)) {
         return true;
      } else {
         int l = (Integer)this.levels.get(key);
         if (l >= level) {
            return false;
         } else {
            return l + 1 == level;
         }
      }
   }

   public boolean isUnlocked(String key, int level) {
      if (!this.levels.containsKey(key)) {
         return false;
      } else {
         int l = (Integer)this.levels.get(key);
         return l >= level;
      }
   }

   public int getMaxMinion() {
      return this.maxMinion;
   }

   public void setMaxMinion(int maxMinion) {
      this.maxMinion = maxMinion;
   }

   public void setLevel(String key, int level) {
      this.levels.put(key, level);
   }

   public HashMap<String, Integer> getLevels() {
      return this.levels;
   }

   public void setLevels(HashMap<String, Integer> levels) {
      this.levels = levels;
   }

   public HashMap<String, Integer> getTypes() {
      return this.types;
   }

   public int getUnlocked() {
      return this.unlocked;
   }

   public void setUnlocked(int unlocked) {
      this.unlocked = unlocked;
   }

   public long getLastLogin() {
      return this.lastLogin;
   }

   public void setLastLogin(long lastLogin) {
      this.lastLogin = lastLogin;
   }

   public void addPlayerMinion(PlayerMinion pm) {
      this.minions.put(pm.getId(), pm);
   }

   public void removePlayerMinion(PlayerMinion pm) {
      this.minions.remove(pm.getId());
   }

   public HashMap<String, PlayerMinion> getMinions() {
      return this.minions;
   }

   public int getMinionSize() {
      return this.minions.size();
   }

   public UUID getUuid() {
      return this.uuid;
   }
}
