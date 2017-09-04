package administrava.commands;

import java.util.Random;

import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import administrava.Administrava;
import administrava.util.ChatHelper;
import administrava.util.RandomUtil;

public class CommandSlap extends Command {

	private Random random = new Random();
	
	public CommandSlap() {
		super("Slap", "slap", 2, "/slap [target] (damage, default none)");
	}

	@Override
	public boolean onExecute(Player player, String[] args) {
		
		if(args.length <= getArgLength() && args.length > 0) {
			String targetArg = args[0];
			
			int damage;
			
			if(args.length == 1) {
				// By default it is 0 if no 2nd argument is provided.
				damage = 0;
			}else{
				String damageArg = args[1];
				
				try{
					damage = Integer.parseInt(damageArg);
				}catch(Exception e) {
					// They inputed a non-numerical (or non-integer), so we will set it to 0.
					damage = 0;
				}
			}
			
			Player target = null;
			
			target = Administrava.getInstance().getServer().getPlayerExact(targetArg);
			
			if(target == null) {
				ChatHelper.sendMessage(player, ChatColor.RED + "Error: Target is invalid.");
				// Invalid player
				return false;
			}else{
				if(target.isOnline()) {
					// Okay, he is online
					slap(target, damage);
					
					// Notify both players
					ChatHelper.sendMessage(player, ChatColor.GREEN + "You have slapped " + target.getDisplayName() + " for " + ChatColor.RED + damage + " damage.");
					ChatHelper.sendMessage(target, ChatColor.GREEN + "You have been slapped by " + player.getDisplayName() + " for " + ChatColor.RED + damage + " damage.");
					return true;
				}else{
					ChatHelper.sendMessage(player, ChatColor.RED + "Error: Target is not online.");
					// Target is not online!
					return false;
				}
			}
			
		}else{
			ChatHelper.sendMessage(player, ChatColor.RED + "Error: Arguments are invalid.");
			// Invalid arguments
			
			return false;
		}
	}
	
	@SuppressWarnings("deprecation")
	private void slap(Player player, int damage) {
		Vector v = new Vector(Math.abs(random.nextDouble() - .5D) * RandomUtil.getDoubleDir(), .5D, Math.abs(random.nextDouble() - .5D) * RandomUtil.getDoubleDir());
		player.setVelocity(v);
		player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_HURT, 3f, .9f);
		
		if(damage > 0) {
			player.damage((double) damage);
		}
		
		for(int i = 0; i < 5; i++) {
			// Clone for memory security
			Location loc = player.getLocation().clone();
			// Set at head level
			loc.setY(loc.getY() + .8f);
			
			// Set at random directions in X and Z
			loc.setX(loc.getX() + (random.nextFloat() * RandomUtil.getFloatDir()));
			loc.setZ(loc.getZ() + (random.nextFloat() * RandomUtil.getFloatDir()));
			
			// Play the effect.
			player.getWorld().playEffect(loc, Effect.HEART, 5);
		}
	}

}
