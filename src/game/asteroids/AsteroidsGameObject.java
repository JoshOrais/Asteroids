package game.asteroids;

import org.joml.Vector2f;
import org.joml.Vector3f;

import engine.GameEntity;
import game.asteroids.graphics.AnimatedSprite;
import game.AsteroidsGame;

public abstract class AsteroidsGameObject extends GameEntity {
	protected Vector2f acceleration = new Vector2f(0.0f, 0.0f),
				 	   velocity     = new Vector2f(0.0f, 0.0f),
				 	   deltaPos		= new Vector2f(0.0f, 0.0f);

	protected float max_velocity;
	protected AnimatedSprite sprite;
	protected float xmin = 0, xmax = 0, ymin = 10, ymax = 10, width = 10, height = 10;
	private boolean bounded = true;
	protected HitBox hitbox = new HitBox(new Vector2f(0.0f, 0.0f), 100.0f);
	protected boolean collided;
	public boolean alive = true;

	public void setBounded(boolean bounded) {
		this.bounded = bounded;
		if (bounded){
			xmin = AsteroidsGame.GAME_BOUNDS_MIN_X;
			ymin = AsteroidsGame.GAME_BOUNDS_MIN_Y;
			xmax = AsteroidsGame.GAME_BOUNDS_MAX_X;
			ymax = AsteroidsGame.GAME_BOUNDS_MAX_Y;
		}
	}

	public Vector2f getAcceleration() {
		return acceleration;
	}

	public boolean isDead(){
		return !alive;
	}

	public void kill(){
		alive = false;
	}

	public void setSprite(AnimatedSprite sprite) {
		this.sprite = sprite;
	}

	public AnimatedSprite getSprite() {
		return this.sprite;
	}

	public Vector2f getDeltaPosition() {
		return deltaPos;
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

	public HitBox getHitBox(){
		return hitbox;
	}

	public void setVelocity(Vector2f v){
		velocity.x = v.x;
		velocity.y = v.y;
	}

	public void setHitBoxRadius(float r){
		hitbox.setRadius(r);
	}

	public abstract void collisionAction(AsteroidsGameObject K);

	public void setCollided(boolean collided){
		this.collided = collided;
	}

	public boolean isCollided(){
		return collided;
	}

	public void collideWith(AsteroidsGameObject K){
		if (isDead()) return;

		if (K.isCollided()) return;

		if (K.getHitBox().intersects(getHitBox())){
			collisionAction(K);
			K.collisionAction(this);

			setCollided(true);
			K.setCollided(true);
		}
	}

	public void move(float interval) {
		deltaPos.x = velocity.x;
		deltaPos.y = velocity.y;

		// System.out.println(velocity.x);

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

		if (hitbox != null)
			hitbox.setCenter(position.x, position.y);
	}
}
