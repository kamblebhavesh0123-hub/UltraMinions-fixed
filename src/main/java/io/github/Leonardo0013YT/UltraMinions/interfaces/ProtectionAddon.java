package io.github.Leonardo0013YT.UltraMinions.interfaces;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

public interface ProtectionAddon {
   boolean canBuild(Player var1, Location var2);

   boolean canBuild(Player var1, Block var2);
}
