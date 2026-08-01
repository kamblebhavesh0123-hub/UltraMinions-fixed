package io.github.Leonardo0013YT.UltraMinions.interfaces;

import io.github.Leonardo0013YT.UltraMinions.database.PlayerMinion;
import java.util.List;
import org.bukkit.Location;

public interface HologramAddon {
   void createHologram(PlayerMinion var1, Location var2, List<String> var3);

   void deleteHologram(PlayerMinion var1);

   boolean hasHologram(PlayerMinion var1);

   void delete();
}
