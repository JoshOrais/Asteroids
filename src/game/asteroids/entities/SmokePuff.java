package game.asteroids.entities;

import game.asteroids.AsteroidsGameObject;
import game.asteroids.HitBox;
import game.asteroids.graphics.AnimatedSprite;
import engine.ResourceLoader;

import org.joml.*;

public class SmokePuff extends AsteroidsGameObject{
  public static final float SMOKEPUFFSPEED = 0.17f;

  public SmokePuff(Vector3f loc, Vector2f initialVelocity){
    this.velocity= initialVelocity;
    this.velocity.normalize().mul(SMOKEPUFFSPEED);
    this.max_velocity = SMOKEPUFFSPEED;
    this.position = loc;

    this.scale = 5.2f;
    this.hitbox = new HitBox(new Vector2f(loc.x, loc.y), scale);
    setBounded(false);
    AnimatedSprite asprite = new AnimatedSprite(4, 0.35f);
    asprite.addFrame(ResourceLoader.getTexture("puff_1"));
    asprite.addFrame(ResourceLoader.getTexture("puff_2"));
    asprite.addFrame(ResourceLoader.getTexture("puff_3"));
    asprite.addFrame(ResourceLoader.getTexture("puff_4"));
    setSprite(asprite);
    setLifeSpan(0.35f);
  }

  public void update(float interval){
    move(interval);
    age(interval);
    getSprite().update(interval);
  }

  public void collisionAction(AsteroidsGameObject K){
  }

}
