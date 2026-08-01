package io.github.Leonardo0013YT.UltraMinions.fanciful;

import java.util.ArrayList;
import java.util.Collection;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class FancyMessage {
   private Collection<TextComponent> text = new ArrayList();
   private TextComponent now;

   public FancyMessage(String msg) {
      this.now = new TextComponent(msg);
   }

   public FancyMessage addMsg(String msg) {
      this.text.add(this.now);
      this.now = new TextComponent(msg);
      return this;
   }

   public FancyMessage bold(boolean b) {
      this.now.setBold(b);
      return this;
   }

   public FancyMessage color(ChatColor color) {
      this.now.setColor(color);
      return this;
   }

   public FancyMessage setClick(ClickEvent.Action ca, String cmd) {
      this.now.setClickEvent(new ClickEvent(ca, cmd));
      return this;
   }

   public FancyMessage setHover(HoverEvent.Action ha, String msg) {
      this.now.setHoverEvent(new HoverEvent(ha, (new ComponentBuilder(msg)).create()));
      return this;
   }

   public void send(Player p) {
      this.text.add(this.now);
      TextComponent[] tc = new TextComponent[this.text.size()];
      int i = 0;

      for(TextComponent s : this.text) {
         tc[i] = s;
         ++i;
      }

      p.spigot().sendMessage(tc);
   }

   public void send(CommandSender p) {
      TextComponent[] tc = (TextComponent[])this.text.toArray();
      p.sendMessage((new TextComponent(tc)).toString());
   }
}
