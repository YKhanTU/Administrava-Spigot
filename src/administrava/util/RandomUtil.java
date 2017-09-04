package administrava.util;

import java.util.Random;

public final class RandomUtil {

	private static Random random = new Random();
	
	public static Random getRandom() {
		return random;
	}
	
	public static float getFloatDir() {
		return (random.nextBoolean()) ? 1f : -1f;
	}
	
	public static double getDoubleDir() {
		return (random.nextBoolean()) ? 1d : -1d;
	}
}
