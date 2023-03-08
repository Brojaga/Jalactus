package io.github.Brojaga;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import java.util.logging.Logger;
import java.util.Date;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Jalactus extends JavaPlugin implements Listener{
  private Logger log;
  private Enum killer;
  final TextComponent jalactusKickMsg = Component.text("JALACTUS IS DISAPPOINTED, TRY AGAIN MORTAL!")
  .color(NamedTextColor.DARK_RED).decoration(TextDecoration.BOLD, true);
  
  
  @Override
  public void onEnable() {
    Bukkit.getPluginManager().registerEvents(this, this);
    log = getLogger();
  }
  
  @EventHandler
  public void onEntityDamageByEntity(EntityDamageByEntityEvent event){
    if (event.getEntity().getType() == EntityType.PLAYER) {
      LivingEntity p = (LivingEntity)event.getEntity();
      if (event.getFinalDamage() > p.getHealth()) {
        killer = event.getDamager().getType();
      }
    }
  }

  @EventHandler
  public void onPlayerJoin(PlayerJoinEvent event) {
    final TextComponent jalactusJoinMsg = Component.text("WELCOME " + event.getPlayer().getName() + "... TO YOUR DOOM")
    .color(NamedTextColor.DARK_RED).decoration(TextDecoration.BOLD, true);
    event.getPlayer().sendMessage(jalactusJoinMsg);
  }

  @EventHandler
  public void onPlayerDeath(PlayerDeathEvent event) {
    int daysPassed = (int)(Bukkit.getWorld("world").getFullTime() / 24000);
    Date currDate = new Date();
    Enum deathCause = killer == null ? event.getEntity().getLastDamageCause().getCause() : killer;
    final TextComponent jalactusDeathMsg = Component.text("JALACTUS SEES ALL! ").color(NamedTextColor.DARK_RED).decoration(TextDecoration.BOLD, true)
    .append(Component.text("AFTER " + daysPassed + " DAYS ").color(NamedTextColor.DARK_RED))
    .append(Component.text(event.getPlayer().getName()).color(NamedTextColor.GOLD).decoration(TextDecoration.ITALIC, true))
    .append(Component.text(" HAS FAILED BY " + deathCause + "...   MORE BLOOD FOR JALACTUS").color(NamedTextColor.DARK_RED).decoration(TextDecoration.BOLD, true));
    
    Bukkit.broadcast(jalactusDeathMsg);
    
    try {
      Thread.sleep(5000);
    }
    catch(Exception e)
    {
      System.out.println(e);
    }
    
    for (Player target : Bukkit.getServer().getOnlinePlayers()) {
          target.kick(jalactusKickMsg);
        }
    
    File logDeath = new File(Bukkit.getServer().getWorldContainer().getAbsolutePath() + "/logs/deathtracker.txt");
    try {
      if (!logDeath.exists()){
        logDeath.createNewFile();
      }
      FileWriter writer = new FileWriter(logDeath, true);
      writer.append("FOOLISH MORTAL, YOUR SIN IS MEMORIALIZED! " + event.getPlayer().getName() + " HAS ENDED THE WORLD OF "
       + currDate + " AFTER " + daysPassed + " DAYS" + " BY " + deathCause + "\n");
      writer.close();
    }
    catch(IOException e) {
    }
    Bukkit.shutdown();
  }
}
