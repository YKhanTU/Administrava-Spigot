package administrava.listeners;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import administrava.Administrava;
import administrava.commands.CommandRocket;

public class MovementListener implements Listener {

	@EventHandler
	public void onPlayerMove(PlayerMoveEvent event) {
//		Player p = event.getPlayer();
//		
//		CommandRocket cmdRocket = (CommandRocket) Administrava.getInstance().getCommandHandler().getCommand(CommandRocket.class);
//		
//		if(cmdRocket.getRocketPlayers().contains(p.getUniqueId())) {
//			
//			Location from = event.getFrom();
//			Location to = event.getTo();
//			
//			if((from.getBlockX() > to.getBlockX() || from.getBlockX() < to.getBlockX()) || (from.getBlockZ() > to.getBlockZ() || from.getBlockZ() < to.getBlockZ())) {
//				event.setCancelled(true);
//			}
//		}
	}
}
