package io.github.Brojaga;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.Plugin;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.entity.Player;
import java.util.logging.Logger;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import org.apache.commons.io.FileUtils;    

public class Jalactus extends JavaPlugin implements Listener{
  private Logger log;
  
  @Override
  public void onEnable() {
    Bukkit.getPluginManager().registerEvents(this, this);
    log = getLogger();
  }

  @EventHandler
  public void onPlayerDeath(PlayerDeathEvent event) {
    Bukkit.broadcastMessage(ChatColor.RED + "JALACTUS SEES ALL! " + event.getPlayer().getName() + " HAS FAILED... MORE BLOOD FOR JALACTUS!!!");
    try {
      Thread.sleep(2000);
    }
    catch(Exception e)
    {
      System.out.println(e);
    }
    for (Player target : Bukkit.getServer().getOnlinePlayers()) {
          target.kickPlayer(ChatColor.RED + "JALACTUS IS DISAPPOINTED, TRY AGAIN MORTAL!");
        }
    
    File logDeath = new File(Bukkit.getServer().getWorldContainer().getAbsolutePath() + "/logs/deathtracker.txt");
    try {
      if (!logDeath.exists()){
        logDeath.createNewFile();
      }
      FileWriter writer = new FileWriter(logDeath);
      writer.append("FOOLISH MORTAL, YOUR SIN IS MEMORIALIZED! " + event.getPlayer().getName() + " HAS ENDED THE WORLD OF " + LocalDateTime.now() + " By " + event.deathMessage());
      writer.append("\n");
      writer.close();
    }
    catch(IOException e) {
    }
    Bukkit.shutdown();
  }
}
