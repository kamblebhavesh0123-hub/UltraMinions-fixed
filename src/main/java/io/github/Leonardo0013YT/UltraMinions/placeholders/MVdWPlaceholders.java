package io.github.Leonardo0013YT.UltraMinions.placeholders;

import be.maximvdw.placeholderapi.PlaceholderAPI;
import io.github.Leonardo0013YT.UltraMinions.Main;
import io.github.Leonardo0013YT.UltraMinions.database.PlayerData;
import io.github.Leonardo0013YT.UltraMinions.minions.Minion;
import io.github.Leonardo0013YT.UltraMinions.minions.levels.MinionLevel;
import io.github.Leonardo0013YT.UltraMinions.tiers.Tier;
import org.bukkit.entity.Player;

public class MVdWPlaceholders {
   private Main plugin;

   public MVdWPlaceholders(Main plugin) {
      this.plugin = plugin;
   }

   public void register() {
      PlaceholderAPI.registerPlaceholder(this.plugin, "um_minions_size", (e) -> {
         Player p = e.getPlayer();
         PlayerData pd = PlayerData.getPlayerData(p);
         Tier tier = this.plugin.getTm().getTier(pd);
         return "" + pd.getMinionSize();
      });
      PlaceholderAPI.registerPlaceholder(this.plugin, "um_max_size", (e) -> {
         Player p = e.getPlayer();
         PlayerData pd = PlayerData.getPlayerData(p);
         Tier tier = this.plugin.getTm().getTier(pd);
         return tier == null ? "0" : "" + tier.getMax();
      });
      PlaceholderAPI.registerPlaceholder(this.plugin, "um_next_required", (e) -> {
         Player p = e.getPlayer();
         PlayerData pd = PlayerData.getPlayerData(p);
         Tier tier = this.plugin.getTm().getTier(pd);
         if (this.plugin.getTm().getTier(pd) == null) {
            return "0";
         } else {
            Tier tnext = this.plugin.getTm().getNextTier(tier);
            return "" + tnext.getRequired();
         }
      });
      PlaceholderAPI.registerPlaceholder(this.plugin, "um_next_unlocked", (e) -> {
         Player p = e.getPlayer();
         PlayerData pd = PlayerData.getPlayerData(p);
         return "" + pd.getUnlocked();
      });
      PlaceholderAPI.registerPlaceholder(this.plugin, "um_next_restant", (e) -> {
         Player p = e.getPlayer();
         PlayerData pd = PlayerData.getPlayerData(p);
         Tier tier = this.plugin.getTm().getTier(pd);
         if (this.plugin.getTm().getTier(pd) == null) {
            return "0";
         } else {
            Tier tnext = this.plugin.getTm().getNextTier(tier);
            return "" + (tnext.getRequired() - pd.getUnlocked());
         }
      });

      for(String key : this.plugin.getMm().getMinions().keySet()) {
         Minion m = this.plugin.getMm().getMinion(key);

         for(MinionLevel ml : m.getLevels().values()) {
            PlaceholderAPI.registerPlaceholder(this.plugin, "um_unlocked_" + key + "_level_" + ml.getLevel(), (e) -> {
               Player p = e.getPlayer();
               PlayerData pd = PlayerData.getPlayerData(p);
               int level = ml.getLevel();
               return pd.isUnlocked(key, level) ? this.plugin.getLang().get("unlocked") : this.plugin.getLang().get("locked");
            });
         }
      }

   }
}
