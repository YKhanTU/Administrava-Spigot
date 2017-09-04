package administrava.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import net.md_5.bungee.api.ChatColor;

public class ConnectionListener implements Listener {

	@EventHandler
	public void onJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		
		if(player.hasPlayedBefore()) {
			event.setJoinMessage(player.getDisplayName() + ChatColor.GREEN + " has connected to the server.");
		}else{
			event.setJoinMessage(ChatColor.LIGHT_PURPLE + "Welcome " 
							+ ChatColor.YELLOW + player.getDisplayName() + ChatColor.LIGHT_PURPLE + " to the server!"
									+ " It is their first time connecting.");
		}
	}
	
	@EventHandler
	public void onQuit(PlayerQuitEvent event) {
		Player player = event.getPlayer();
		
		event.setQuitMessage(player.getDisplayName() + ChatColor.RED + " has disconnected to the server.");
	}
}
