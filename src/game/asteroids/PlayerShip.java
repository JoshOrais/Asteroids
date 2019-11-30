package game.asteroids;

import org.joml.Vector2f;

import engine.ResourceLoader;
import engine.graphics.Shader;
import game.asteroids.graphics.StaticSprite;

public class PlayerShip extends AsteroidsGameObject{
	private float angle;
	public static final float MAX_VELOCITY = 25.0f;
	public static final float ACCELERATION = 9.8f;
	public static final float HANDLING = 100.0f;
	private int direction;

	public PlayerShip() {
		scale = 7.0f;
		angle = 0.0f;
		direction = 0;
		this.max_velocity = MAX_VELOCITY;
		setSprite(new StaticSprite(ResourceLoader.getTexture("rocket")));
	}

	public void setDirection(int direction) {
		this.direction = direction;
	}

	public void killVelocity() {
		velocity.x = 0;
		velocity.y = 0;
	}

	public void accelerate(float timestep) {
		Vector2f aforce = new Vector2f();
		aforce.x = timestep * ACCELERATION * (float)Math.cos(Math.toRadians(angle));
		aforce.y = timestep * ACCELERATION * (float)Math.sin(Math.toRadians(angle));
		addForce(aforce);
	}

	@Override
	public void update(float interval) {
		angle += (float)direction * HANDLING * interval;
		move(interval); //physics object update
		sprite.update(interval);
		rotation = angle;
	}

}
