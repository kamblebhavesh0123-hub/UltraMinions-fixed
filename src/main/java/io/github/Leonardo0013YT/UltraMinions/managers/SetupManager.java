package io.github.Leonardo0013YT.UltraMinions.managers;

import io.github.Leonardo0013YT.UltraMinions.database.PlayerMinion;
import io.github.Leonardo0013YT.UltraMinions.setup.SetupAutoSell;
import io.github.Leonardo0013YT.UltraMinions.setup.SetupAutoSmelt;
import io.github.Leonardo0013YT.UltraMinions.setup.SetupCompressor;
import io.github.Leonardo0013YT.UltraMinions.setup.SetupFood;
import io.github.Leonardo0013YT.UltraMinions.setup.SetupFuel;
import io.github.Leonardo0013YT.UltraMinions.setup.SetupMinion;
import java.util.HashMap;
import org.bukkit.entity.Player;

public class SetupManager {
   private HashMap<Player, PlayerMinion> setupChest = new HashMap();
   private HashMap<Player, SetupMinion> setupMinion = new HashMap();
   private HashMap<Player, SetupAutoSell> setupAutoSell = new HashMap();
   private HashMap<Player, SetupAutoSmelt> setupAutoSmelt = new HashMap();
   private HashMap<Player, SetupCompressor> setupCompressor = new HashMap();
   private HashMap<Player, SetupFuel> setupFuel = new HashMap();
   private HashMap<Player, String> setupName = new HashMap();
   private HashMap<Player, SetupFood> setupFood = new HashMap();

   public void setSetupFood(Player p, SetupFood sm) {
      this.setupFood.put(p, sm);
   }

   public SetupFood getSetupFood(Player p) {
      return (SetupFood)this.setupFood.get(p);
   }

   public boolean isSetupFood(Player p) {
      return this.setupFood.containsKey(p);
   }

   public void removeSetupFood(Player p) {
      this.setupFood.remove(p);
   }

   public void setSetupChest(Player p, PlayerMinion sm) {
      this.setupChest.put(p, sm);
   }

   public PlayerMinion getSetupChest(Player p) {
      return (PlayerMinion)this.setupChest.get(p);
   }

   public boolean isSetupChest(Player p) {
      return this.setupChest.containsKey(p);
   }

   public void removeSetupChest(Player p) {
      this.setupChest.remove(p);
   }

   public void setSetupName(Player p, String sm) {
      this.setupName.put(p, sm);
   }

   public String getSetupName(Player p) {
      return (String)this.setupName.get(p);
   }

   public boolean isSetupName(Player p) {
      return this.setupName.containsKey(p);
   }

   public void removeSetupName(Player p) {
      this.setupName.remove(p);
   }

   public void setSetupMinion(Player p, SetupMinion sm) {
      this.setupMinion.put(p, sm);
   }

   public SetupMinion getSetupMinion(Player p) {
      return (SetupMinion)this.setupMinion.get(p);
   }

   public boolean isSetupMinion(Player p) {
      return this.setupMinion.containsKey(p);
   }

   public void removeSetupMinion(Player p) {
      this.setupMinion.remove(p);
   }

   public void setSetupAutoSell(Player p, SetupAutoSell sm) {
      this.setupAutoSell.put(p, sm);
   }

   public SetupAutoSell getSetupAutoSell(Player p) {
      return (SetupAutoSell)this.setupAutoSell.get(p);
   }

   public boolean isSetupAutoSell(Player p) {
      return this.setupAutoSell.containsKey(p);
   }

   public void removeSetupAutoSell(Player p) {
      this.setupAutoSell.remove(p);
   }

   public void setSetupAutoSmelt(Player p, SetupAutoSmelt sm) {
      this.setupAutoSmelt.put(p, sm);
   }

   public SetupAutoSmelt getSetupAutoSmelt(Player p) {
      return (SetupAutoSmelt)this.setupAutoSmelt.get(p);
   }

   public boolean isSetupAutoSmelt(Player p) {
      return this.setupAutoSmelt.containsKey(p);
   }

   public void removeSetupAutoSmelt(Player p) {
      this.setupAutoSmelt.remove(p);
   }

   public void setSetupCompressor(Player p, SetupCompressor sm) {
      this.setupCompressor.put(p, sm);
   }

   public SetupCompressor getSetupCompressor(Player p) {
      return (SetupCompressor)this.setupCompressor.get(p);
   }

   public boolean isSetupCompressor(Player p) {
      return this.setupCompressor.containsKey(p);
   }

   public void removeSetupCompressor(Player p) {
      this.setupCompressor.remove(p);
   }

   public void setSetupFuel(Player p, SetupFuel sm) {
      this.setupFuel.put(p, sm);
   }

   public SetupFuel getSetupFuel(Player p) {
      return (SetupFuel)this.setupFuel.get(p);
   }

   public boolean isSetupFuel(Player p) {
      return this.setupFuel.containsKey(p);
   }

   public void removeSetupFuel(Player p) {
      this.setupFuel.remove(p);
   }
}
