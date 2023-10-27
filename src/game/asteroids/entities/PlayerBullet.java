package game.asteroids.entities;

import game.asteroids.AsteroidsGameObject;
import game.asteroids.HitBox;
import game.asteroids.DeathBehaviours;
import game.asteroids.graphics.StaticSprite;
import engine.ResourceLoader;

import org.joml.*;

public class PlayerBullet extends AsteroidsGameObject{
  private AsteroidsGameObject source;
  public static final float PLAYER_BULLET_SIZE = 5f,
                            PLAYER_BULLET_MAX_VELOCITY = 5.17f;

  public PlayerBullet(AsteroidsGameObject source, Vector2f loc, Vector2f initialVelocity){
    super(loc, initialVelocity, PLAYER_BULLET_SIZE, null);
    getVelocity().normalize().mul(PLAYER_BULLET_MAX_VELOCITY);
    this.source = source;

    addForce(source.getVelocity());
    setBounded(false);
    setSprite(new StaticSprite(ResourceLoader.getTexture("bullet")));
    setLifeSpan(1.05f);
    setHitDamage(2.f);
    setDeathBehaviour(DeathBehaviours.getPowerUpSplash());
  }

  public void update(float interval){
    move(interval);
    age(interval);
  }

  public void collisionAction(AsteroidsGameObject K){
    if (K == source) return;
    if (K instanceof PlayerBullet) return;
    this.kill();
  }

}
