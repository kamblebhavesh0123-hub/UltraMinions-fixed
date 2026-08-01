package io.github.Leonardo0013YT.UltraMinions.database.data;

import io.github.Leonardo0013YT.UltraMinions.interfaces.DataSave;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class PlayerDataSave implements DataSave {
   private UUID uuid;
   private int unlocked;
   private long lastLogin;
   private HashMap<String, Integer> levels = new HashMap();
   private ArrayList<String> data = new ArrayList();

   public ArrayList<String> getData() {
      return this.data;
   }

   public void setData(ArrayList<String> data) {
      this.data = data;
   }

   public HashMap<String, Integer> getLevels() {
      return this.levels;
   }

   public void setLevels(HashMap<String, Integer> levels) {
      this.levels = levels;
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

   public UUID getUuid() {
      return this.uuid;
   }

   public void setUuid(UUID uuid) {
      this.uuid = uuid;
   }
}
