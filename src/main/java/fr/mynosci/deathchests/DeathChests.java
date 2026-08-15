package fr.mynosci.deathchests;

import fr.mynosci.deathchests.listeners.DeathEvent;
import org.bukkit.plugin.java.JavaPlugin;

public final class DeathChests extends JavaPlugin {

    public static DeathChests instance;

    public static DeathChests getInstance() {
        return instance;
    }


    @Override
    public void onEnable() {
        instance = this;
        getServer().getPluginManager().registerEvents(new DeathEvent(), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
