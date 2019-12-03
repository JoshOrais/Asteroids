package game.asteroids;

import org.joml.Vector2f;
import org.joml.Vector3f;

import game.AsteroidsGame;

// Actually a hit circle
public class HitBox {
	public Vector2f position;
	public float radius;

	public HitBox(Vector2f center, float radius) {
		this.position = center;
		this.radius = radius;
	}

	public HitBox(Vector3f center, float radius){
		this(new Vector2f(center.x, center.y), radius);
	}

	public boolean intersects(HitBox b) {
		/// hahaahahahahhahahahaha
		return MathHelper.toroidalDistance(position, b.getPosition(), AsteroidsGame.GAME_BOUNDS_WIDTH, AsteroidsGame.GAME_BOUNDS_HEIGHT) < (radius + b.getRadius());
	}

	public float getCenterX(){
		return position.x;
	}

	public float getCenterY(){
		return position.y;
	}

	public void setCenter(Vector2f v){
		position.x = v.x;
		position.y = v.y;
	}

	public void setCenter(float x, float y){
		position.x = x;
		position.y = y;
	}

	public Vector2f getPosition() {
		return position;
	}

	public float getRadius() {
		return radius;
	}

	public void setRadius(float radius) {
		this.radius = radius;
	}
}
