package fr.mynosci.deathchests;

import fr.mynosci.deathchests.listeners.MainListener;
import fr.mynosci.deathchests.schedulers.ParticleScheduler;
import org.bukkit.plugin.java.JavaPlugin;

public final class DeathChests extends JavaPlugin {

    public static DeathChests instance;

    public static DeathChests getInstance() {
        return instance;
    }


    @Override
    public void onEnable() {
        instance = this;
        getServer().getPluginManager().registerEvents(new MainListener(), this);
        new ParticleScheduler().runTaskTimer(this, 0L, 7L);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
