package game.asteroids;

import org.joml.Vector2f;

import game.AsteroidsGame;

// Actually a hit circle
public class HitBox {
	public Vector2f position;
	public float radius;
	
	public HitBox(Vector2f center, float radius) {
		
	}
	
	public boolean intersects(HitBox b) {
		/// hahaahahahahhahahahaha
		return MathHelper.toroidalDistance(position, b.getPosition(), AsteroidsGame.GAME_WIDTH, AsteroidsGame.GAME_HEIGHT) < (radius + b.getRadius());
	}

	public Vector2f getPosition() {
		return position;
	}

	public void setPosition(Vector2f position) {
		this.position = position;
	}

	public float getRadius() {
		return radius;
	}

	public void setRadius(float radius) {
		this.radius = radius;
	}
}
