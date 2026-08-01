package io.github.Leonardo0013YT.UltraMinions.addons;

import io.github.Leonardo0013YT.UltraMinions.Main;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;

public class VaultAddon {
   private Economy econ;

   public VaultAddon(Main plugin) {
      RegisteredServiceProvider<Economy> rsp = Bukkit.getServer().getServicesManager().getRegistration(Economy.class);
      if (rsp != null) {
         this.econ = (Economy)rsp.getProvider();
      }

   }

   public void addCoins(Player p, double amount) {
      if (this.econ != null) {
         this.econ.depositPlayer(p, amount);
      }

   }

   public void removeCoins(Player p, double amount) {
      if (this.econ != null) {
         this.econ.withdrawPlayer(p, amount);
      }

   }

   public double getCoins(Player p) {
      return this.econ.getBalance(p);
   }
}
