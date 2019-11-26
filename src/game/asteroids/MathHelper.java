package game.asteroids;

import org.joml.Vector2f;

public class MathHelper {
	public static float toroidalDistance(Vector2f a, Vector2f b, float width, float height) {
		float dx = Math.abs(a.x - b.x);
		float dy = Math.abs(a.y - b.y);
		
		if (dx > width / 2.0f )
			dx = width - dx;
		if (dy > height / 2.0f )
			dy = height - dy;
		
		return (float)Math.sqrt(dx * dx + dy * dy);
	}
}
