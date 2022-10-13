package balancedelytra;

import java.io.File;
import java.util.List;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityToggleGlideEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class BalancedElytra extends JavaPlugin implements Listener {

    public static BalancedElytra instance;
    
    @Override
    public void onEnable() {
        instance = this;
        this.getLogger().info("=================================");
        if(getConfig().getString("lang").equalsIgnoreCase("en")) {
            this.getLogger().info("BalancedElytras is now active");
        } else if (getConfig().getString("lang").equalsIgnoreCase("es")) {
            this.getLogger().info("BalancedElytras ha sido activado");
        }
        this.getLogger().info("=================================");
        this.getCommand("bel").setExecutor(new Comando(this));
        getServer().getPluginManager().registerEvents(this, this);
        getServer().getPluginManager().registerEvents(new FireworkBoost(this), this);
        File config = new File(getDataFolder()+File.separator+"config.yml");
        if (!config.exists()) {
            saveDefaultConfig();
        }
    }
    
    @Override
    public void onDisable() {
        this.getLogger().info("=====================================");
        if(getConfig().getString("lang").equalsIgnoreCase("en")) {
            this.getLogger().info("BalancedElytras has been disabled");
        }  else if (getConfig().getString("lang").equalsIgnoreCase("es")) {
            this.getLogger().info("BalancedElytras ha sido desactivado");
        }
        this.getLogger().info("=====================================");
    }
    
    
    @EventHandler
    public void disableElytras(EntityToggleGlideEvent event) {
        Player player = (Player) event.getEntity();
        
        if (!player.isOp() || !player.hasPermission("bel.admin")) {
            if(!player.hasPermission("bel.override")) {
                if(this.isInAffectedWorldElytra(player)) {

                    event.setCancelled(true);
                    if (getConfig().getBoolean("message-enable")) {
                        Utils.sendMessage(player, getConfig().getString("disabledElytra-message")); 
                    }

                    if(getConfig().getBoolean("dequipElytra-enable")) {
                        dequipElytra(player);
                        if(getConfig().getBoolean("dequipElytra_Message-enable")) {
                            Utils.sendMessage(player, getConfig().getString("dequipElytra_Message"));
                        }
                    }
                }
            } 
        } 
   }
    
    public boolean isInAffectedWorldElytra(Player player) {
        if(getConfig().getBoolean("ignoreWorldsFunction_Elytra")) {
            return true;
        }
        List<String> activeWorlds = getConfig().getStringList("noelytra-worlds");
        for (String activeWorld : activeWorlds) {
            if (player.getWorld().getName().equalsIgnoreCase(activeWorld)) {
                return true;
            }
        }
        return false;
    }
    
    @EventHandler
    public void avoidWallCrash(EntityDamageEvent e) {
        if(!(e.getEntity() instanceof Player)) { return; }
        
        if(getConfig().getBoolean("AvoidElytraCrashDamage_enable")) {
            if(((Player) e.getEntity()).hasPermission("bel.vip") || ((Player) e.getEntity()).hasPermission("bel.nocrashdmg")) {
                if(e.getCause().equals(EntityDamageEvent.DamageCause.FLY_INTO_WALL)) {
                    e.setCancelled(true);
                }
            }
        }
    }
    
    private void dequipElytra(Player p) {
        PlayerInventory inv = p.getInventory();
        
        if(!inv.getChestplate().getType().equals(Material.ELYTRA) || inv.getChestplate() == null) {
            return;
        }
        
        ItemStack elytraChest = inv.getChestplate();
        inv.setChestplate(null);
        
        if(inv.firstEmpty() != -1) { //-if next doesent work, prohibit slots 0 through 8-
            inv.addItem(new ItemStack[] { elytraChest });
        } else {
            Location dropLoc = inv.getLocation();
            dropLoc.getWorld().dropItemNaturally(dropLoc, elytraChest);
            p.updateInventory();
        }
    }
}
