package io.github.Leonardo0013YT.UltraMinions.managers;

import io.github.Leonardo0013YT.UltraMinions.Main;
import io.github.Leonardo0013YT.UltraMinions.database.PlayerData;
import io.github.Leonardo0013YT.UltraMinions.tiers.Tier;
import java.util.HashMap;
import org.bukkit.configuration.ConfigurationSection;

public class TiersManager {
   private Main plugin;
   private HashMap<Integer, Tier> tiers = new HashMap();
   private int last;

   public TiersManager(Main plugin) {
      this.plugin = plugin;
      this.loadTiers();
   }

   public void loadTiers() {
      this.tiers.clear();
      if (this.plugin.getTiers().isSet("tiers")) {
         ConfigurationSection conf = this.plugin.getTiers().getConfig().getConfigurationSection("tiers");

         for(String s : conf.getKeys(false)) {
            int required = this.plugin.getTiers().getInt("tiers." + s + ".typesOrLevelsRequired");
            int max = this.plugin.getTiers().getInt("tiers." + s + ".max");
            String msg = this.plugin.getTiers().get("tiers." + s + ".newTier");
            int l = Integer.parseInt(s);
            this.tiers.put(l, new Tier(this.tiers.size(), required, max, msg));
            if (this.last < l) {
               this.last = l;
            }
         }
      }

   }

   public Tier getTier(PlayerData pd) {
      Tier t = this.getTierByUnlocked(pd.getUnlocked());
      return t == null ? (Tier)this.tiers.get(this.last) : t;
   }

   public Tier getTierByUnlocked(int unlocked) {
      for(int i = 0; i < this.tiers.size(); ++i) {
         Tier next;
         if (this.tiers.containsKey(i + 1)) {
            next = (Tier)this.tiers.get(i + 1);
         } else {
            next = (Tier)this.tiers.get(i);
         }

         Tier now = (Tier)this.tiers.get(i);
         if (unlocked >= now.getRequired() && unlocked < next.getRequired()) {
            return now;
         }
      }

      return null;
   }

   public Tier getNextTier(Tier tier) {
      return this.tiers.containsKey(tier.getOrder() + 1) ? (Tier)this.tiers.get(tier.getOrder() + 1) : (Tier)this.tiers.get(this.last);
   }

   public HashMap<Integer, Tier> getTiers() {
      return this.tiers;
   }
}
