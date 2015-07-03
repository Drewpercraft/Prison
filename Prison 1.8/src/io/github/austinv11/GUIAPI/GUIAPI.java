package io.github.austinv11.GUIAPI;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
/**
 * <b>
 * GUIAPI Object:
 * </b>
 * Main object for this API's actual plugin features.
 */

public class GUIAPI extends JavaPlugin implements Listener{
	private static boolean debug = false;
	@Override
    public void onEnable() {
		getServer().getPluginManager().registerEvents(this, this);
        getLogger().info("GUIAPI v"+Menu.getAPIVersion()+" has been enabled. Remember, it does nothing on its own.");
    }
 
    @Override
    public void onDisable() {
        // TODO Insert logic to be performed when the plugin is disabled
    }
    public static boolean getDebugStatus(){
    	return debug;
    }
    public static void setDebugStatus(boolean isDebug){
    	debug = isDebug;
    }
    /**
     * Used to broadcast general debug messages.
     * @param object Any object.
     * @param message Information you want to display.
     */
    public static void debugMessage(Object object, String message){
    	if (debug){
	    	Bukkit.getServer().broadcastMessage("["+ChatColor.RED+"DEBUG"+ChatColor.RESET+"("+ChatColor.GOLD+Thread.currentThread().getStackTrace()[2].getClassName()+"."+Thread.currentThread().getStackTrace()[2].getMethodName()+":"+Thread.currentThread().getStackTrace()[2].getLineNumber()+ChatColor.RESET+")] "+object.getClass().getSimpleName()+": "+message);
    	}
    }
    /**
     * Used to broadcast general debug messages.
     * @param message Information you want to display.
     */
    public static void debugMessage(String message){
    	if (debug){
	    	Bukkit.getServer().broadcastMessage("["+ChatColor.RED+"DEBUG"+ChatColor.RESET+"("+ChatColor.GOLD+Thread.currentThread().getStackTrace()[2].getClassName()+"."+Thread.currentThread().getStackTrace()[2].getMethodName()+":"+Thread.currentThread().getStackTrace()[2].getLineNumber()+ChatColor.RESET+")] "+" "+message);
    	}
    }
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onMenuInteract(MenuInteractEvent event){
    	if (debug){
    		debugMessage("Menu"+event.getMenuName());
    		debugMessage("Is Menu Open: "+event.getMenu().isMenuOpen());
    		debugMessage("Button: "+event.getButtonName());
    		debugMessage("Button Item: "+event.getButtonItem().getType().toString());
    		debugMessage("Did Button Toggle: "+event.getMenu().isButtonToggled(event.getSlot()));
    		debugMessage("Slot: "+(event.getSlot()+1));
    		debugMessage("ClickType: "+event.getClickType().toString());
    		debugMessage("Player: "+event.getPlayer().getDisplayName());
    		debugMessage("Is Cancelled: "+event.isCancelled());
    	}
    }
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
    	if (cmd.getName().equalsIgnoreCase("toggleTestMode")){
    		Menu.setDebugStatus(!debug);
    		if (debug == false){
    			sender.sendMessage("[GUIAPI] Debug mode has been set to "+ChatColor.RED+debug);
    		}else{
    			sender.sendMessage("[GUIAPI] Debug mode has been set to "+ChatColor.GREEN+debug);
    		}
    		return true;
    	}
    	return false; 
    }
}
