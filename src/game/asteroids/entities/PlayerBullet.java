package game.asteroids.entities;

import game.asteroids.AsteroidsGameObject;
import game.asteroids.HitBox;
import game.asteroids.graphics.StaticSprite;
import engine.ResourceLoader;

import org.joml.*;

public class PlayerBullet extends AsteroidsGameObject{
  private AsteroidsGameObject source;
  public static final float PLAYER_BULLET_SIZE = 1.5f,
                            PLAYER_BULLET_MAX_VELOCITY = 5.17f;

  public PlayerBullet(AsteroidsGameObject source, Vector3f loc, Vector2f initialVelocity){
    this.velocity= initialVelocity;
    this.velocity.normalize().mul(PLAYER_BULLET_MAX_VELOCITY);
    this.max_velocity = PLAYER_BULLET_MAX_VELOCITY;
    this.position = loc;
    this.source = source;

    this.scale = PLAYER_BULLET_SIZE;
    this.hitbox = new HitBox(new Vector2f(loc.x, loc.y), PLAYER_BULLET_SIZE);
    setBounded(false);
    setSprite(new StaticSprite(ResourceLoader.getTexture("bullet")));
  }

  public void update(float interval){
    move(interval);
  }

  public void collisionAction(AsteroidsGameObject K){
    if (K == source){
      System.out.println("WHY WOULD YOU KILL YOURSEL");
      return;
    }
    if (K instanceof PlayerBullet) return;
    this.kill();
  }

}
