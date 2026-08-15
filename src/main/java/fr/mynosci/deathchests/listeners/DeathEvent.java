package fr.mynosci.deathchests.listeners;

import fr.mynosci.deathchests.DeathChests;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.jetbrains.annotations.NotNull;

public class DeathEvent implements Listener {

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent e) {
        DeathChests.getInstance().getLogger().info("Player " + e.getEntity().getName() + " was died :(");
        if(e.getEntity() instanceof Player p) {
            Location c = p.getLocation().subtract(0, 1, 0);
            p.getWorld().getBlockAt(c).setType(Material.CHEST);
        }
    }

}
