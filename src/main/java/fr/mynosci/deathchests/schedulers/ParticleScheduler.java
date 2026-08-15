package fr.mynosci.deathchests.schedulers;

import fr.mynosci.deathchests.DeathChests;
import fr.mynosci.deathchests.listeners.MainListener;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.entity.TextDisplay;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Map;

public class ParticleScheduler extends BukkitRunnable {

    private static final Particle.DustOptions OWNER_PARTICLE =
            new Particle.DustOptions(Color.LIME, 1.2f);

    private static final Particle.DustOptions STEAL_PARTICLE =
            new Particle.DustOptions(Color.RED, 1.2f);

    @Override
    public void run() {
        for (Player p : Bukkit.getOnlinePlayers()) {
            for (Map.Entry<Player, Location> entry : MainListener.getDeathChests().entrySet()) {
                Player owner = entry.getKey();
                Location c = entry.getValue();
                TextDisplay display = MainListener.getDCDisplays().get(owner);

                if (!p.getWorld().equals(c.getWorld())) {
                    if (display != null && display.isValid()) {
                        p.hideEntity(DeathChests.getInstance(), display);
                    }
                    continue;
                }

                if (p.getLocation().distanceSquared(c) <= 100) {
                    boolean isOwner = p.getUniqueId().equals(owner.getUniqueId());

                    Particle.DustOptions particle =
                            isOwner ? OWNER_PARTICLE : STEAL_PARTICLE;

                    p.spawnParticle(
                            Particle.DUST,
                            c.clone().add(0.5, 1, 0.5),
                            10,
                            0.5,
                            0.5,
                            0.5,
                            0,
                            particle
                    );

                    if (display != null && display.isValid()) {
                        p.showEntity(DeathChests.getInstance(), display);
                    }
                } else {
                    if (display != null && display.isValid()) {
                        p.hideEntity(DeathChests.getInstance(), display);
                    }
                }
            }
        }
    }
}