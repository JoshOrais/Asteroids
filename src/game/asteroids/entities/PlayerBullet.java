package game.asteroids.entities;

import game.asteroids.AsteroidsGameObject;
import game.asteroids.Collidable;

public class PlayerBullet extends AsteroidsGameObject implements Collidable{
  private AsteroidsGameObject source;
  public static final float PLAYER_BULLET_SIZE = 0.5f;

  public PlayerBullet(AsteroidsGameObject source, Vector2f loc, Vector2f initialVelocity){
    this.velocity= initialVelocity;
    this.position = loc;
    this.source = source;

    this.scale = PLAYER_BULLET_SIZE;
    this.hitbox = new HitBox(loc, PLAYER_BULLET_SIZE);
  }

  public void collideWith(AsteroidsGameObject K){
    if (K == source)
      return;
      collisionAction();
      K.collisionAction();
  }

  public void collisionAction(){

  }

}
