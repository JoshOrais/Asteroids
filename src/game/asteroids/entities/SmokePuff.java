package game.asteroids.entities;

import game.asteroids.AsteroidsGameObject;
import game.asteroids.HitBox;
import game.asteroids.graphics.AnimatedSprite;
import engine.ResourceLoader;

import org.joml.*;

public class SmokePuff extends Particle{
  public static final float SMOKEPUFFSPEED = 0.01f;

  public SmokePuff(Vector3f loc, Vector2f initialVelocity){
    this.velocity= initialVelocity;
    this.velocity.normalize().mul(SMOKEPUFFSPEED);
    this.max_velocity = SMOKEPUFFSPEED;
    this.position = loc;

    this.scale = 8f;
    this.hitbox = new HitBox(new Vector2f(loc.x, loc.y), scale);
    setBounded(false);
    AnimatedSprite asprite = new AnimatedSprite(5, 0.1f);
    asprite.addFrame(ResourceLoader.getTexture("smoke_01"));
    asprite.addFrame(ResourceLoader.getTexture("smoke_02"));
    asprite.addFrame(ResourceLoader.getTexture("smoke_03"));
    asprite.addFrame(ResourceLoader.getTexture("smoke_04"));
    asprite.addFrame(ResourceLoader.getTexture("smoke_05"));
    setSprite(asprite);
    setLifeSpan(0.12f);
  }

  public void update(float interval){
    move(interval);
    age(interval);
    getSprite().update(interval);
  }

  public void collisionAction(AsteroidsGameObject K){
  }

}
