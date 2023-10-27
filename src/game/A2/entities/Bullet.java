package game.A2.entities;

import game.A2.PhysicsObject;
import game.A2.StaticSprite;

import org.joml.Vector2f;

public class Bullet extends PhysicsObject {
  private final float DEFAULT_LIFESPAN = 0.5f;
  private final float DEFAULT_SIZE = 20f;
  private final float DEFAULT_SPEED = 600f;
  private final float DEFAULT_DAMAGE = 1f;
  private float lifespan;
  private float damage;

  public Bullet(Vector2f pos, Vector2f iVel, float direction, float rangeMult, float sizeMult) {
    super(pos, iVel);
    setSize(DEFAULT_SIZE * sizeMult);
    setSprite(new StaticSprite("puff_1"));

    float speedX = (float)Math.cos(direction) * DEFAULT_SPEED;
    float speedY = (float)Math.sin(direction) * DEFAULT_SPEED;
    getVelocity().add(speedX, speedY);
    damage = DEFAULT_DAMAGE;

    lifespan = rangeMult * DEFAULT_LIFESPAN;    
  }

  public float getAttackPower() {
    return damage;
  }

  @Override
  public void update(float dT) {
    super.update(dT);
    lifespan -= dT;
    if (lifespan <= 0) {
      kill();
    }
  }
}