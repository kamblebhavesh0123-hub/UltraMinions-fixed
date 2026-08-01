package io.github.Leonardo0013YT.UltraMinions.tiers;

public class Tier {
   private int order;
   private int required;
   private int max;
   private String msg;

   public Tier(int order, int required, int max, String msg) {
      this.order = order;
      this.required = required;
      this.max = max;
      this.msg = msg;
   }

   public int getOrder() {
      return this.order;
   }

   public String getMsg() {
      return this.msg;
   }

   public int getRequired() {
      return this.required;
   }

   public int getMax() {
      return this.max;
   }
}
