package game.asteroids;

import org.joml.Vector2f;

import engine.ResourceLoader;
import game.asteroids.graphics.StaticSprite;
import game.asteroids.entities.*;
import game.AsteroidsGame;

public class PlayerShip extends AsteroidsGameObject{
	private float angle;
	public static final float MAX_VELOCITY = 4.0f;
	public static final float ACCELERATION = 3.8f;
	public static final float HANDLING = 120.f;
	public static final float ATTACK_RATE = 0.05f;
	public static final float MAX_HP = 100.f;
	private int direction;
	private Behavior firingBehaviour, singleBullet, tripleBullet;
	private Timer fireCooldown, iFrames, tripleFireDuration;
	private boolean invul = false, tripleFire = false;

	public PlayerShip() {
		scale = 15.0f;
		angle = 0.0f;
		direction = 0;
		this.max_velocity = MAX_VELOCITY;
		this.hitbox = new HitBox(position, scale);
		this.hitpoints = MAX_HP;
		setSprite(new StaticSprite(ResourceLoader.getTexture("rocket")));
		singleBullet = FiringBehaviours.getNormalBulletBehaviour();
		tripleBullet = FiringBehaviours.getTripleBulletBehaviour();
		setFiringBehaviour(singleBullet);
		fireCooldown = new Timer(ATTACK_RATE);
		iFrames = new Timer(0.0f, false);
		tripleFireDuration = new Timer(0.0f, false);
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
		if (!fireCooldown.isReady()) return;
		fireCooldown.fire();
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
		fireCooldown.update(interval);
		sprite.update(interval);
		rotation = angle;

		if (isInvulnerable()){
			iFrames.update(interval);
			if (iFrames.isReady())
				invul = false;
		}

		if (hasTripleFire()){
			tripleFireDuration.update(interval);

			if (tripleFireDuration.isReady()){
				tripleFire = false;
				setFiringBehaviour(singleBullet);
			}
		}
	}

	public void collisionAction(AsteroidsGameObject K){
		if (K instanceof Asteroid){
			if (!invul)
				damage(K.getHitDamage());
		}
	}

	@Override
	public void damage(float amount){
		addIFrames(1.15f);
		AsteroidsGame.getGame().flashDamage();
		super.damage(amount);
	}

	public void revive(){
		this.hitpoints = MAX_HP;
		this.alive = true;
	}

	public void addIFrames(float amount){
		this.iFrames.setInterval(amount);
		this.iFrames.reset();

		invul = true;
	}

	public void addTripleFire(float amount){
		this.tripleFireDuration.setInterval(amount);
		this.tripleFireDuration.reset();

		setFiringBehaviour(tripleBullet);

		tripleFire = true;
	}

	public boolean isInvulnerable(){
		return invul;
	}

	public boolean hasTripleFire(){
		return tripleFire;
	}

	public void heal(float amount){
		this.hitpoints += amount;
		if (hitpoints > MAX_HP)
			hitpoints = MAX_HP;
	}

}
