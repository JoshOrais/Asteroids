package game.asteroids.entities;

import game.asteroids.AsteroidsGameObject;
import game.asteroids.HitBox;
import org.joml.*;

public class PlayerBullet extends AsteroidsGameObject{
  private AsteroidsGameObject source;
  public static final float PLAYER_BULLET_SIZE = 0.5f;

  public PlayerBullet(AsteroidsGameObject source, Vector3f loc, Vector2f initialVelocity){
    this.velocity= initialVelocity;
    this.position = loc;
    this.source = source;

    this.scale = PLAYER_BULLET_SIZE;
    this.hitbox = new HitBox(new Vector2f(loc.x, loc.y), PLAYER_BULLET_SIZE);
  }

  public void update(float interval){
    move(interval);
  }

  public void collisionAction(AsteroidsGameObject K){
    if (K == source)
      return;
    this.kill();
  }

}
