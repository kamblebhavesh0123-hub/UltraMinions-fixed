package io.github.Leonardo0013YT.UltraMinions.interfaces;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public interface DataSave {
   ArrayList<String> getData();

   void setData(ArrayList<String> var1);

   HashMap<String, Integer> getLevels();

   void setLevels(HashMap<String, Integer> var1);

   int getUnlocked();

   void setUnlocked(int var1);

   long getLastLogin();

   void setLastLogin(long var1);

   UUID getUuid();

   void setUuid(UUID var1);
}
