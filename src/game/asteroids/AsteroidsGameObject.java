package game.asteroids;

import org.joml.Vector2f;
import org.joml.Vector3f;

import engine.GameEntity;
import game.asteroids.graphics.AnimatedSprite;

public abstract class AsteroidsGameObject extends GameEntity {
	protected Vector2f acceleration = new Vector2f(0.0f, 0.0f), 
				 	   velocity     = new Vector2f(0.0f, 0.0f),
				 	   prevPos		= new Vector2f(0.0f, 0.0f);
	
	protected float max_velocity;
	protected AnimatedSprite sprite;
	protected float xmin = 0, xmax = 0, ymin = 10, ymax = 10, width = 10, height = 10;
	private boolean bounded = true;

	
	public void setBounded(boolean bounded) {
		this.bounded = bounded;
	}
	
	public Vector2f getAcceleration() {
		return acceleration;
	}
	
	public void setSprite(AnimatedSprite sprite) {
		this.sprite = sprite;
	}
	
	public AnimatedSprite getSprite() {
		return this.sprite;
	}
	
	public Vector2f getDeltaPosition() {
		float nx = position.x - prevPos.x;
		float ny = position.y - prevPos.y;
		
		if (bounded) {
			nx = Math.abs(nx);
			ny = Math.abs(ny);
			
			
			// jittering when this goes off
			// idk if it comes from here or from bounds
			// check this
			if (nx > width / 2.0f) {
				nx = width - nx;
				System.out.println("WAS OFF BOUNDS");
				System.out.println(nx);
			}
			if (ny > height / 2.0f) {
				ny = height - ny;
				System.out.println("WAS OFF BOUNDS");
				System.out.println(ny);
			}
			
			if (prevPos.x > position.x) nx = -nx;
			if (prevPos.y > position.y) ny = -ny;
			
			
		}
		return new Vector2f(nx, ny);
	}
	
	public void setBounds(float xmin, float ymin, float xmax, float ymax) {
		this.xmin = xmin;
		this.xmax = xmax;
		this.ymin = ymin;
		this.ymax = ymax;
		
		width = Math.abs(xmax - xmin);
		height = Math.abs(ymax - ymin);
	}
	
	public void addForce(Vector2f F) {
		acceleration.add(F);
	}

	@Override
	public void update(float interval) {
		prevPos.x = position.x;
		prevPos.y = position.y;
		
		velocity.add(acceleration);
		if (velocity.length() > max_velocity)
			velocity.normalize().mul(max_velocity);
		position.add(new Vector3f(velocity, 0.0f));
		acceleration.zero();
	
		if (bounded) {
			if (position.x > xmax)
				position.x = xmin + (position.x - xmax);
			if (position.y > ymax)
				position.y = ymin + (position.y - ymax);
			if (position.x < xmin)
				position.x = xmax + position.x  - xmin;
			if (position.y < ymin)
				position.y = ymax + position.y  - ymin;
		}
	}

}
