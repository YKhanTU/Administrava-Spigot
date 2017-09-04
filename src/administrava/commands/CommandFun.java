package administrava.commands;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import administrava.Administrava;
import administrava.util.ChatHelper;
import net.md_5.bungee.api.ChatColor;

public class CommandFun extends Command {

	public CommandFun() {
		super("Fun", "fun", 0, "/fun");
	}

	@Override
	public boolean onExecute(Player player, String[] args) {
		
		ChatHelper.sendMessage(player, ChatColor.GRAY + "Fun is only obtained by not having a higher figure controlling you.");
		
//		Location loc = player.getLocation();
		Location boxLocation = player.getEyeLocation().add(0D, .05D, 0D);
		boxLocation.setYaw(180);
		
		ArmorStand as = (ArmorStand) player.getWorld().spawnEntity(boxLocation, EntityType.ARMOR_STAND);
		
		
		as.setCustomName(ChatColor.LIGHT_PURPLE + "Mystery Box");
		as.setCustomNameVisible(true);
		as.setHelmet(new ItemStack(Material.ENDER_CHEST));
		as.setGravity(true);
		as.setVisible(false);
		as.setCollidable(false);
		as.setInvulnerable(true);
		as.setCanPickupItems(false);
		
		BukkitRunnable armorStandRunnable = new BukkitRunnable() {
			
			final long startTime = System.currentTimeMillis();
			
			@Override
			public void run() {
				
				long elapsed = System.currentTimeMillis() - startTime;
				
				float pitch = 0.025f;
				
				if(elapsed >= (5000)) {
					
					as.setCustomName(ChatColor.LIGHT_PURPLE + "Mystery Box: " + ChatColor.AQUA + "Opening Prize!");
					
					if(elapsed >= 10000) {
						as.getWorld().spawnParticle(Particle.SMOKE_LARGE, as.getLocation().getX(), as.getLocation().getY() + 1.5D, as.getLocation().getZ(), 30, 0, 0, 0);
						
						player.playSound(player.getLocation(), Sound.ENTITY_FIREWORK_LAUNCH, 1f, 1f);
						player.sendMessage("You have received X item from the Mystery Box!");
						
						as.remove();
						this.cancel();
					}else{
						as.setVelocity(new Vector(0, 0, 0));
						
						as.getWorld().spawnParticle(Particle.FLAME, as.getLocation().getX(), as.getLocation().getY() + 1.5D, as.getLocation().getZ(), 1, 0, 0, 0);
						
						pitch += .025f;
						
						player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1f, pitch);
					}
				}else{
					as.setCustomName(ChatColor.LIGHT_PURPLE + "Mystery Box: " + ChatColor.YELLOW + ((double) Math.round(((double) elapsed / 1000.0D) * 10) / 10) + ChatColor.RED + " seconds.");
					
					as.setVelocity(new Vector(0, .05D, 0));
				}
			}
		};
		
		armorStandRunnable.runTaskTimer(Administrava.getInstance(), 0, 1L);
		
		return true;
	}

}
