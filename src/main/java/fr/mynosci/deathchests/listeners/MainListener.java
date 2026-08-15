package fr.mynosci.deathchests.listeners;

import fr.mynosci.deathchests.DeathChests;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Chest;
import org.bukkit.entity.Display;
import org.bukkit.entity.Player;
import org.bukkit.entity.TextDisplay;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

public class MainListener implements Listener {

    public static HashMap<Player, Location> deathChests = new HashMap<>();
    public static HashMap<Player, TextDisplay> deathChestDisplays = new HashMap<>();

    public static HashMap<Player, Location> getDeathChests() {
        return deathChests;
    }

    public static HashMap<Player, TextDisplay> getDCDisplays() {
        return deathChestDisplays;
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent e) {
        DeathChests.getInstance().getLogger().info("Player " + e.getEntity().getName() + " was died :(");

        if (e.getEntity() instanceof Player p) {
            Location c = p.getLocation().subtract(0, 1, 0);

            p.getWorld().getBlockAt(c).setType(Material.CHEST);
            deathChests.put(p, c);

            Chest chest = (Chest) p.getWorld().getBlockAt(c).getState(); // sur que c'est un chest = override setType
            chest.getInventory().addItem(e.getDrops().toArray(new ItemStack[0]));

            TextDisplay oldDisplay = deathChestDisplays.remove(p);

            if (oldDisplay != null) {
                oldDisplay.remove();
            }

            TextDisplay display = p.getWorld().spawn(
                    c.clone().add(0.5, 1.7, 0.5),
                    TextDisplay.class
            );

            display.text(
                    Component.empty()
                            .append(Component.text("Restes de ").color(NamedTextColor.GRAY))
                            .append(Component.text(p.getName()).color(NamedTextColor.WHITE))
            );

            display.setBillboard(Display.Billboard.CENTER);
            display.setShadowed(true);
            display.setSeeThrough(false);
            display.setVisibleByDefault(false);

            deathChestDisplays.put(p, display);

            p.sendMessage(
                    Component.empty()
                            .append(Component.text("[").color(NamedTextColor.WHITE))
                            .append(Component.text("DeathChests").color(NamedTextColor.RED))
                            .append(Component.text("]").color(NamedTextColor.WHITE))
                            .append(
                                    Component.text(
                                            " Vous êtes mort. Le coffre de votre mort est disponible en ${coords}"
                                    ).color(NamedTextColor.RED)
                            )
                            .replaceText(builder ->
                                    builder.matchLiteral("${coords}")
                                            .replacement(
                                                    Component.text(
                                                            c.getBlockX() + " " +
                                                                    c.getBlockY() + " " +
                                                                    c.getBlockZ()
                                                    ).color(NamedTextColor.WHITE)
                                            )
                            )
            );

            e.getDrops().clear();
        }
    }

    @EventHandler
    public void onPlayerClickChest(PlayerInteractEvent e) {
        if (e.getAction().isRightClick()) {
            if (e.getClickedBlock() != null && e.getClickedBlock().getType() == Material.CHEST) {
                Location c = e.getClickedBlock().getLocation();
                // Todo Later
            }
        }
    }
}