package io.github.Leonardo0013YT.UltraMinions.api;

import io.github.Leonardo0013YT.UltraMinions.Main;
import io.github.Leonardo0013YT.UltraMinions.craft.Craft;
import io.github.Leonardo0013YT.UltraMinions.database.PlayerData;
import io.github.Leonardo0013YT.UltraMinions.database.PlayerMinion;
import java.util.ArrayList;
import java.util.Collection;
import org.bukkit.entity.Player;

public class UltraMinionsAPI {
   public static ArrayList<Craft> getCrafts() {
      return Main.get().getCm().getCrafts();
   }

   public static int getAmountMinions(Player p) {
      return PlayerData.getPlayerData(p) != null ? PlayerData.getPlayerData(p).getMinionSize() : 0;
   }

   public static PlayerData getPlayerData(Player p) {
      return PlayerData.getPlayerData(p) != null ? PlayerData.getPlayerData(p) : null;
   }

   public static Collection<PlayerMinion> getPlayerMinions(Player p) {
      return (Collection<PlayerMinion>)(PlayerData.getPlayerData(p) != null ? PlayerData.getPlayerData(p).getMinions().values() : new ArrayList());
   }
}
