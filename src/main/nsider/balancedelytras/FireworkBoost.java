package balancedelytra;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.WeatherType;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerRiptideEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;


public class FireworkBoost implements Listener {
    
    private final BalancedElytra plugin;

    public FireworkBoost(BalancedElytra instance) {
        plugin = instance;
    }
    
    private final HashMap<String, Long> cooldowns = new HashMap<>();
    private final HashMap<String, Long> cooldowns1 = new HashMap<>();
    private final ArrayList<UUID> animationwindow = new ArrayList<>();
    
    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        
        if(!event.getAction().equals(Action.RIGHT_CLICK_AIR) && !event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) { return; }
        ItemStack item1 = event.getItem();
        
        if(item1 == null || !player.isGliding() || !item1.getType().equals(Material.FIREWORK_ROCKET) || player.hasPermission("bel.admin") || player.hasPermission("bel.vip")) {
            return;
        }

        if(!player.hasPermission("bel.override.firework")) {
            
            if(this.isInAffectedWorldFireworks(player)) {
                if (plugin.getConfig().getBoolean("disableFireworkBoostCompletely")) {
                    if (plugin.getConfig().getBoolean("FireworkDisabled_EnableMessage")) {
                        Utils.sendMessage(player, plugin.getConfig().getString("FireworkDisabled_Message"));
                    }
                    event.setCancelled(true);
                    return;
                }

                if (plugin.getConfig().getBoolean("FireworkCooldown_enable")) {
                    if (this.cooldowns.containsKey(player.getName())) {
                        int cooldownTime1 = plugin.getConfig().getInt("FireworkBoost_Cooldown");
                        long time = System.currentTimeMillis() - this.cooldowns.get(player.getName());
                        //5000L = 5 segundos
                        if (time <= (cooldownTime1 * 1000L)) {
                            long timeLeft = cooldownTime1 * 1000 - time;
                            if (plugin.getConfig().getBoolean("FireworkCooldown_EnableMessage")) {
                                Utils.sendMessage(player, plugin.getConfig().getString("FireworkCooldown_Message")
                                        .replaceAll("%time_left%", "" + timeLeft / 1000));
                            }
                            if (plugin.getConfig().getBoolean("enableFireworkCooldownAnimation") && !this.animationwindow.contains(player.getUniqueId())) {
                                long timeLeft1 = cooldownTime1 * 1000 - time;
                                player.setCooldown(Material.FIREWORK_ROCKET, (int) ((timeLeft1 / 1000) * 20));
                                this.animationwindow.add(player.getUniqueId());
                            }
                            event.setCancelled(true);
                            return;
                        }
                        this.cooldowns.remove(player.getName());
                        if(this.animationwindow.contains(player.getUniqueId())) {
                            this.animationwindow.remove(player.getUniqueId());
                        }
                    }
                }
            }
        }
        if(plugin.getConfig().getBoolean("FireworkCooldown_enable")) {
            this.cooldowns.put(player.getName(), System.currentTimeMillis());
        }
    }
    
    @EventHandler
    public void onPlayerInteractElytra(PlayerInteractEvent e) {
        Player player = e.getPlayer();
        
        if(!e.getAction().equals(Action.RIGHT_CLICK_AIR) && !e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) { return; }
        ItemStack item1 = e.getItem();
        
        if(item1 == null || !item1.getType().equals(Material.ELYTRA) ||
                player.hasPermission("bel.admin") ||
                player.hasPermission("bel.override") ||
                player.hasPermission("bel.vip")) {
            return;
        }
        
        List<String> activeWorlds = plugin.getConfig().getStringList("noelytra-worlds");
        for(String activeWorld : activeWorlds) {
            if(player.getWorld().getName().equalsIgnoreCase(activeWorld)) {
                if (plugin.getConfig().getBoolean("message-enable")) {
                    Utils.sendMessage(player, plugin.getConfig().getString("disabledElytra-message")); 
                }
                
                e.setCancelled(true);
                return;
            }
        }
    }
    
    @SuppressWarnings("deprecation")
    @EventHandler
    public void onPlayerInteractTrident(PlayerRiptideEvent event) {
        Player player = event.getPlayer();
        ItemStack item = player.getInventory().getItemInMainHand();
        ItemStack item1 = player.getInventory().getItemInOffHand();
        
        if(!player.hasPermission("bel.admin") || !player.hasPermission("bel.vip") || !player.hasPermission("bel.override.riptide")) {
            if(this.isInAffectedWorldRiptide(player)) {
                if(item.containsEnchantment(Enchantment.RIPTIDE) || item1.containsEnchantment(Enchantment.RIPTIDE)) {
                    if(plugin.getConfig().getBoolean("disableTridentRiptideUse")) {
                        if(plugin.getConfig().getBoolean("riptideDisabled_EnableMessage")) {
                            Utils.sendMessage(player, plugin.getConfig().getString("riptideDisabled-message")); 
                        }
                        this.cancelRiptideJump(player);
                        return;
                    }
                }

                if (plugin.getConfig().getBoolean("RiptideCooldown_enable")) {
                    if(this.isInAffectedBiome(player)) {
                        //COOLDOWN OPTION
                        if (this.cooldowns1.containsKey(player.getName())) {
                            int cooldownTime1 = plugin.getConfig().getInt("RiptideBoost_Cooldown");
                            long time = System.currentTimeMillis() - this.cooldowns1.get(player.getName());
                            //5000L = 5 segundos
                            if (time <= (cooldownTime1 * 1000L)) {
                                long timeLeft = cooldownTime1 * 1000 - time;
                                if (plugin.getConfig().getBoolean("RiptideCooldown_EnableMessage")) {
                                    Utils.sendMessage(player, plugin.getConfig().getString("RiptideCooldown_Message")
                                            .replaceAll("%time_left%", "" + timeLeft / 1000));
                                }
                                if (plugin.getConfig().getBoolean("enableRiptideCooldownAnimation") 
                                        && !this.animationwindow.contains(player.getUniqueId())) {
                                    long timeLeft1 = cooldownTime1 * 1000 - time;
                                    player.setCooldown(Material.TRIDENT, (int) ((timeLeft1 / 1000) * 20));
                                    this.animationwindow.add(player.getUniqueId());
                                }
                                this.cancelRiptideJump(player);
                                return;
                            }
                            this.cooldowns1.remove(player.getName());
                            if(this.animationwindow.contains(player.getUniqueId())) {
                                this.animationwindow.remove(player.getUniqueId());
                            }
                        }
                        if(plugin.getConfig().getBoolean("RiptideCooldown_enable")) {
                            this.cooldowns1.put(player.getName(), System.currentTimeMillis());
                        }
                    }
                }
                }
        }
    }
    
    public void cancelRiptideJump(Player p) {
        final Location oldLoc = p.getLocation();
        (new BukkitRunnable() {
            public void run() {
              p.teleport(oldLoc);
            }
          }).runTaskLater(plugin, 1L);
    }
    
    public boolean isInAffectedBiome(Player player) {
        if(player.getPlayerWeather() != null) {
            if(player.getPlayerWeather().equals(WeatherType.DOWNFALL)) {
                return true;
            }
        }
        if(player.getWorld().hasStorm() || player.getWorld().isThundering()) {
            return true;
        }
        return false;
    }
    
    public boolean isInAffectedWorldFireworks(Player player) {
        if(plugin.getConfig().getBoolean("ignoreWorldsFunction_Fireworks")) {
            return true;
        }
        List<String> activeWorlds = plugin.getConfig().getStringList("nofireworkboost-worlds");
        for (String activeWorld : activeWorlds) {
            if (player.getWorld().getName().equalsIgnoreCase(activeWorld)) {
                return true;
            }
        }
        return false;
    }
    
    public boolean isInAffectedWorldRiptide(Player player) {
        if(plugin.getConfig().getBoolean("ignoreWorldsFunction_Riptide")) {
            return true;
        }
        List<String> activeWorlds = plugin.getConfig().getStringList("noriptideboost-worlds");
        for (String activeWorld : activeWorlds) {
            if (player.getWorld().getName().equalsIgnoreCase(activeWorld)) {
                return true;
            }
        }
        return false;
    }
}
