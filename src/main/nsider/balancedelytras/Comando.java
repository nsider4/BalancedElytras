//THIS CLASS IS TO BE MODIFIED SOON TO REDUCE REPETITIONS OF CODE THAT COULD BE AVOIDED(created it a long time ago)//

package balancedelytra;

import java.util.List;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;


public class Comando implements CommandExecutor {
    
    private final BalancedElytra plugin;

    public Comando(BalancedElytra instance) {
        plugin = instance;
    }
    
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        
        if(cmd.getName().equalsIgnoreCase("bel")) {
            if(sender.isOp() || sender.hasPermission("bel.admin")) {
                if(args.length == 0) {
                    sender.sendMessage(color("&7&m-------------------------------------------------"));
                    sender.sendMessage(color("&3&l-BalancedElytras-"));
                    sender.sendMessage("");
                    sender.sendMessage(color("&b&lCOMMANDS:"));
                    sender.sendMessage("");
                    if(plugin.getConfig().getString("lang").equalsIgnoreCase("en")) {
                        sender.sendMessage(color("&9/bel add [world] &f- &3Remove the use of elytras in the specified world"));
                        sender.sendMessage(color("&9/bel remove [world] &f- &3Resume the use of elytras in the specified world"));
                        sender.sendMessage(color("&9/bel addfirework [world] &f- &3Remove the use of firework boost in the specified world"));
                        sender.sendMessage(color("&9/bel removefirework [world] &f- &3Resume the use of firework boost in the specified world"));
                        sender.sendMessage(color("&9/bel addriptide [world] &f- &3Remove the use of riptide boost in the specified world"));
                        sender.sendMessage(color("&9/bel removeriptide [world] &f- &3Resume the use of riptide boost in the specified world"));
                        sender.sendMessage(color("&9/bel setcooldownfireworks [seconds] &f- &3Changes the firework cooldown for the specified number"));
                        sender.sendMessage(color("&9/bel setcooldownriptide [seconds] &f- &3Changes the riptide cooldown for the specified number"));
                        sender.sendMessage(color("&9/bel reload &f- &3Reload the configuration file"));
                        sender.sendMessage(color("&9/bel author &f- &3Shows the author of the plugin"));
                    } else if (plugin.getConfig().getString("lang").equalsIgnoreCase("es")) {
                        sender.sendMessage(color("&9/bel add [world] &f- &3Remueve el uso de elytras en el mundo especificado"));
                        sender.sendMessage(color("&9/bel remove [world] &f- &3Vuelve a activar el uso de elytras en el mundo especificado"));
                        sender.sendMessage(color("&9/bel addfirework [world] &f- &3Remueve el uso de boost de fuegos artificiales en el mundo especificado"));
                        sender.sendMessage(color("&9/bel removefirework [world] &f- &3Vuelve a activar el uso de boost de fuegos artificiales en el mundo especificado"));
                        sender.sendMessage(color("&9/bel addriptide [world] &f- &3Remueve el uso de propulsión acuática con tridente en el mundo especificado"));
                        sender.sendMessage(color("&9/bel removeriptide [world] &f- &3Vuelve a activar el uso de propulsión acuática con tridente en el mundo especificado"));
                        sender.sendMessage(color("&9/bel setcooldownfireworks [seconds] &f- &3Cambia el tiempo de espera para usar los fuegos artificiales"));
                        sender.sendMessage(color("&9/bel setcooldownriptide [seconds] &f- &3Cambia el tiempo de espera para usar la propulsión acuática con tridente"));
                        sender.sendMessage(color("&9/bel reload &f- &3Recarga la configuracion"));
                        sender.sendMessage(color("&9/bel author &f- &3Muestra el autor del plugin"));
                    }
                    sender.sendMessage(color("&7&m-------------------------------------------------"));
                    return true;
                }
                
                if(args.length == 1) {
                    if(args[0].equalsIgnoreCase("reload")) {
                        plugin.reloadConfig();
                        if(plugin.getConfig().getString("lang").equalsIgnoreCase("en")) {
                            sender.sendMessage(color("&7[&3&lBalancedElytras&7] &aThe config.yml has been reloaded."));
                        } else if (plugin.getConfig().getString("lang").equalsIgnoreCase("es")) {
                            sender.sendMessage(color("&7[&3&lBalancedElytras&7] &aLa config.yml ha sido recargada."));
                        }
                        return true;
                    }
                    
                    if(args[0].equalsIgnoreCase("author")) {
                        sender.sendMessage(color("&6Author: &eNsider"));
                        return true;
                    }
                }
            
                if(args.length == 2) {
                    if(args[0].equalsIgnoreCase("add")) {
                        List<String> activeWorlds = plugin.getConfig().getStringList("noelytra-worlds");
                        if(!activeWorlds.contains(args[1])) {
                            activeWorlds.add(args[1]);
                            plugin.getConfig().set("noelytra-worlds", activeWorlds);
                            if(plugin.getConfig().getString("lang").equalsIgnoreCase("en")) {
                                sender.sendMessage(color("&7[&3&lBalancedElytras&7] &f"+args[1]+" &ahas been added to the no-elytra-worlds list."));
                            } else if (plugin.getConfig().getString("lang").equalsIgnoreCase("es")) {
                                sender.sendMessage(color("&7[&3&lBalancedElytras&7] &f"+args[1]+" &aha sido añadido a la lista de no-elytra-worlds."));
                            }
                            plugin.saveConfig();
                        }
                        return true;
                    }
                
                    if(args[0].equalsIgnoreCase("remove")) {
                        List<String> activeWorlds = plugin.getConfig().getStringList("noelytra-worlds");
                        if(activeWorlds.contains(args[1])) {
                            activeWorlds.remove(args[1]);
                            plugin.getConfig().set("noelytra-worlds", activeWorlds);
                            if(plugin.getConfig().getString("lang").equalsIgnoreCase("en")) {
                                sender.sendMessage(color("&7[&3&lBalancedElytras&7] &f"+args[1]+" &ahas been removed from the no-elytra-worlds list."));
                            } else if (plugin.getConfig().getString("lang").equalsIgnoreCase("es")) {
                                sender.sendMessage(color("&7[&3&lBalancedElytras&7] &f"+args[1]+" &aha sido removido de la lista de no-elytra-worlds."));
                            }
                            plugin.saveConfig();
                        }
                        return true;
                    }
                    
                    if(args[0].equalsIgnoreCase("addfirework")) {
                        List<String> activeWorlds = plugin.getConfig().getStringList("nofireworkboost-worlds");
                        if(!activeWorlds.contains(args[1])) {
                            activeWorlds.add(args[1]);
                            plugin.getConfig().set("nofireworkboost-worlds", activeWorlds);
                            if(plugin.getConfig().getString("lang").equalsIgnoreCase("en")) {
                                sender.sendMessage(color("&7[&3&lBalancedElytras&7] &f"+args[1]+" &ahas been added to the nofireworkboost-worlds list."));
                            } else if (plugin.getConfig().getString("lang").equalsIgnoreCase("es")) {
                                sender.sendMessage(color("&7[&3&lBalancedElytras&7] &f"+args[1]+" &aha sido añadido a la lista de nofireworkboost-worlds."));
                            }
                            plugin.saveConfig();
                        }
                        return true;
                    }
                
                    if(args[0].equalsIgnoreCase("removefirework")) {
                        List<String> activeWorlds = plugin.getConfig().getStringList("nofireworkboost-worlds");
                        if(activeWorlds.contains(args[1])) {
                            activeWorlds.remove(args[1]);
                            plugin.getConfig().set("nofireworkboost-worlds", activeWorlds);
                            if(plugin.getConfig().getString("lang").equalsIgnoreCase("en")) {
                                sender.sendMessage(color("&7[&3&lBalancedElytras&7] &f"+args[1]+" &ahas been removed from the nofireworkboost-worlds list."));
                            } else if (plugin.getConfig().getString("lang").equalsIgnoreCase("es")) {
                                sender.sendMessage(color("&7[&3&lBalancedElytras&7] &f"+args[1]+" &aha sido removido de la lista de nofireworkboost-worlds."));
                            }
                            plugin.saveConfig();
                        }
                        return true;
                    }
                    
                    if(args[0].equalsIgnoreCase("addriptide")) {
                        List<String> activeWorlds = plugin.getConfig().getStringList("noriptideboost-worlds");
                        if(!activeWorlds.contains(args[1])) {
                            activeWorlds.add(args[1]);
                            plugin.getConfig().set("noriptideboost-worlds", activeWorlds);
                            if(plugin.getConfig().getString("lang").equalsIgnoreCase("en")) {
                                sender.sendMessage(color("&7[&3&lBalancedElytras&7] &f"+args[1]+" &ahas been added to the noriptideboost-worlds list."));
                            } else if (plugin.getConfig().getString("lang").equalsIgnoreCase("es")) {
                                sender.sendMessage(color("&7[&3&lBalancedElytras&7] &f"+args[1]+" &aha sido añadido a la lista de noriptideboost-worlds."));
                            }
                            plugin.saveConfig();
                        }
                        return true;
                    }
                
                    if(args[0].equalsIgnoreCase("removeriptide")) {
                        List<String> activeWorlds = plugin.getConfig().getStringList("noriptideboost-worlds");
                        if(activeWorlds.contains(args[1])) {
                            activeWorlds.remove(args[1]);
                            plugin.getConfig().set("noriptideboost-worlds", activeWorlds);
                            if(plugin.getConfig().getString("lang").equalsIgnoreCase("en")) {
                                sender.sendMessage(color("&7[&3&lBalancedElytras&7] &f"+args[1]+" &ahas been removed from the noriptideboost-worlds list."));
                            } else if (plugin.getConfig().getString("lang").equalsIgnoreCase("es")) {
                                sender.sendMessage(color("&7[&3&lBalancedElytras&7] &f"+args[1]+" &aha sido removido de la lista de noriptideboost-worlds."));
                            }
                            plugin.saveConfig();
                        }
                        return true;
                    }
                    
                    if(args[0].equalsIgnoreCase("setcooldownfireworks")) {
                        try {
                            int cooldown = Integer.parseInt(args[1]);
            
                            plugin.getConfig().set("FireworkBoost_Cooldown", cooldown);
                            plugin.saveConfig();
                            if(plugin.getConfig().getString("lang").equalsIgnoreCase("en")) {
                                sender.sendMessage(color("&7[&3&lBalancedElytras&7] &fThe firework cooldown has been set to: &a"+cooldown));
                            } else if (plugin.getConfig().getString("lang").equalsIgnoreCase("es")) {
                                sender.sendMessage(color("&7[&3&lBalancedElytras&7] &fEl tiempo de espera para usar los fuegos artificiales ha sido establecido a: &a"+cooldown));
                            }
                            return true;
                            
                        } catch (NumberFormatException ev) {
                            if(plugin.getConfig().getString("lang").equalsIgnoreCase("en")) {
                                sender.sendMessage(color("&7[&3&lBalancedElytras&7] &cYou must enter a number!"));
                            } else if (plugin.getConfig().getString("lang").equalsIgnoreCase("es")) {
                                sender.sendMessage(color("&7[&3&lBalancedElytras&7] &cDebes usar un numero!"));
                            }
                            return true;
                        }
                    }
                    
                    if(args[0].equalsIgnoreCase("setcooldownriptide")) {
                        try {
                            int cooldown = Integer.parseInt(args[1]);
            
                            plugin.getConfig().set("RiptideBoost_Cooldown", cooldown);
                            plugin.saveConfig();
                            if(plugin.getConfig().getString("lang").equalsIgnoreCase("en")) {
                                sender.sendMessage(color("&7[&3&lBalancedElytras&7] &fThe riptide cooldown has been set to: &a"+cooldown));
                            } else if (plugin.getConfig().getString("lang").equalsIgnoreCase("es")) {
                                sender.sendMessage(color("&7[&3&lBalancedElytras&7] &fEl tiempo de espera para usar propulsión acuática con tridente ha sido establecido a: &a"+cooldown));
                            }
                            return true;
                            
                        } catch (NumberFormatException ev) {
                            if(plugin.getConfig().getString("lang").equalsIgnoreCase("en")) {
                                sender.sendMessage(color("&7[&3&lBalancedElytras&7] &cYou must enter a number!"));
                            } else if (plugin.getConfig().getString("lang").equalsIgnoreCase("es")) {
                                sender.sendMessage(color("&7[&3&lBalancedElytras&7] &cDebes usar un numero!"));
                            }
                            return true;
                        }
                    }
                }
            } else { 
                if(plugin.getConfig().getString("lang").equalsIgnoreCase("en")) {
                    sender.sendMessage(color("&7[&3&lBalancedElytras&7] &cYou don't have enough permissions to do this!"));
                } else if (plugin.getConfig().getString("lang").equalsIgnoreCase("es")) {
                    sender.sendMessage(color("&7[&3&lBalancedElytras&7] &cNo tienes suficientes permisos para hacer esto!"));
                }
                return true;
            }
        }
        if(plugin.getConfig().getString("lang").equalsIgnoreCase("en")) {
            sender.sendMessage(color("&7[&3&lBalancedElytras&7] &cThis command doesen't exist! Use /bel for commands"));
        } else if (plugin.getConfig().getString("lang").equalsIgnoreCase("es")) {
            sender.sendMessage(color("&7[&3&lBalancedElytras&7] &cEste comando no existe! Usa /bel para ver comandos"));
        }
        return true;
    }
    
    public static String color(String message) {
        return message.replace("&", "§");
    }
}
