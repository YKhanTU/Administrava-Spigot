package administrava.commands;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.UUID;
import java.util.logging.Level;

import administrava.Administrava;
import administrava.util.ChatHelper;
import net.md_5.bungee.api.ChatColor;

public class CommandRocket extends Command {
	
	private ArrayList<UUID> rocketPlayers = new ArrayList<UUID>();

	public CommandRocket() {
		super("Rocket", "rocket", 1, "/rocket [player]");
	}
	
	public ArrayList<UUID> getRocketPlayers() {
		return rocketPlayers;
	}

	@Override
	public boolean onExecute(Player player, String[] args) {
		
		if(args.length == 1) {
			
			String targetArgs = args[0];
			
			final Player target = Administrava.getInstance().getServer().getPlayerExact(targetArgs);

			if(target == null) {
				
				ChatHelper.sendMessage(player, ChatColor.RED + "Error. Invalid target.");
				
				return false;
			}else{
				if(target.isOnline()) {
					
					double initY = target.getLocation().getY();

					rocketPlayers.add(target.getUniqueId());
					
					BukkitRunnable rocketRunnable = new BukkitRunnable() {

						@Override
						public void run() {
							
							BukkitRunnable particleRunnable = new BukkitRunnable() {
								
								@Override
								public void run() {
									Location l = target.getLocation();
									
									target.getWorld().spawnParticle(Particle.FLAME, l.getX(), l.getY(), l.getZ(), 3, 0, 0, 0);
									
									if(!rocketPlayers.contains(target.getUniqueId())) {
										this.cancel();
									}
								}
							};
							
							BukkitTask particleTask = particleRunnable.runTaskTimer(Administrava.getInstance(), 0, 2);
							
							for(int i = 0; i < 5; i++) {
								target.playSound(target.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1f, 2f);
								
								try {
									Thread.sleep(1000);
								} catch (InterruptedException e) {
									Bukkit.getLogger().log(Level.WARNING, "Administrava: Error has occurred with CommandRocket!", e);
								}
							}
							
							target.playSound(target.getLocation(), Sound.ENTITY_TNT_PRIMED, 5f, 1f);
							
							target.setVelocity(new Vector(0, 5, 0));
							
							BukkitRunnable checkRunnable = new BukkitRunnable() {
								
								long startTime = System.currentTimeMillis();
								
								@Override
								public void run() {
									long elapsed = System.currentTimeMillis() - startTime;
									
									double currY = target.getLocation().getY();
									
									if((Math.abs(currY - initY) >= 30D) || target.getVelocity().getY() == 0 || elapsed >= 5000) {
										target.getWorld().createExplosion(target.getLocation(), 5F);
										rocketPlayers.remove(target.getUniqueId());
										this.cancel();
									}
								}
								
							};
							
							BukkitTask task = checkRunnable.runTaskTimer(Administrava.getInstance(), 20, 20);
						}
						
					};
					
					BukkitTask t = rocketRunnable.runTask(Administrava.getInstance());
					
					ChatHelper.sendMessage(player, ChatColor.GREEN + "You have rocketed " + target.getDisplayName());
					ChatHelper.sendMessage(target, ChatColor.GREEN + "You have been rocketed by " + player.getDisplayName());
					
					return true;
				}else{
					
					ChatHelper.sendMessage(player, ChatColor.RED + "Error. Target is not online.");
					
					return false;
				}
			}
			
		}else{
			
			ChatHelper.sendMessage(player, ChatColor.RED + "Error. Invalid arguments.");
			
			return false;
		}
	}
	
	
}
