package game.asteroids;

import org.joml.Vector2f;

import engine.ResourceLoader;
import engine.graphics.Shader;
import game.asteroids.graphics.StaticSprite;

public class PlayerShip extends AsteroidsGameObject{
	private float angle;
	public static final float MAX_VELOCITY = 4.0f;
	public static final float ACCELERATION = 3.8f;
	public static final float HANDLING = 100.0f;
	private int direction;
	private Behavior firingBehaviour;

	public PlayerShip() {
		scale = 7.0f;
		angle = 0.0f;
		direction = 0;
		this.max_velocity = MAX_VELOCITY;
		this.hitbox = new HitBox(position, scale);
		setSprite(new StaticSprite(ResourceLoader.getTexture("rocket")));
		setFiringBehaviour(FiringBehaviours.getNormalBulletBehaviour());
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

	public void fire(){
		float x = (float)Math.cos(Math.toRadians(angle));
		float y = (float)Math.sin(Math.toRadians(angle));
		Vector2f target = new Vector2f(x, y).mul(scale);
		Vector2f location = new Vector2f(position.x, position.y);
		target.add(velocity);
		firingBehaviour.setLocation(position.x, position.y);
		firingBehaviour.setTarget(target);
		firingBehaviour.execute();
	}

	public void setFiringBehaviour(Behavior B){
		this.firingBehaviour = B;
	}

	@Override
	public void update(float interval) {
		angle += (float)direction * HANDLING * interval;
		move(interval); //physics object update
		sprite.update(interval);
		rotation = angle;
	}

	public void collisionAction(AsteroidsGameObject K){

	}

}
