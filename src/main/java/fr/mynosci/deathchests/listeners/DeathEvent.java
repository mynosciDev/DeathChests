package fr.mynosci.deathchests.listeners;

import fr.mynosci.deathchests.DeathChests;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class DeathEvent implements Listener {

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent e) {
        DeathChests.getInstance().getLogger().info("Player " + e.getEntity().getName() + " was died :(");
        if(e.getEntity() instanceof Player p) {
            Location c = p.getLocation().subtract(0, 1, 0);
            p.getWorld().getBlockAt(c).setType(Material.CHEST);
            Chest chest = (Chest) p.getWorld().getBlockAt(c).getState(); // sur que c'est un chest = override setType
            chest.getInventory().addItem(e.getDrops().toArray(new ItemStack[0]));
            p.sendMessage(Component.empty()
                    .append(Component.text("[").color(NamedTextColor.WHITE))
                    .append(Component.text("DeathChests").color(NamedTextColor.RED))
                    .append(Component.text("]").color(NamedTextColor.WHITE))
                    .append(Component.text(" Vous êtes mort. Le coffre de votre mort est disponible en ${coords}").color(NamedTextColor.RED)).replaceText(builder -> builder.matchLiteral("${coords}").replacement(Component.text(c.getBlockX() + " " + c.getBlockY() + " " + c.getBlockZ()).color(NamedTextColor.WHITE))));
            e.getDrops().clear();
        }

    }

}
