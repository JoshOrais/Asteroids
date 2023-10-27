package game.asteroids.entities;

import game.asteroids.AsteroidsGameObject;
import game.asteroids.HitBox;
import game.asteroids.graphics.AnimatedSprite;
import engine.ResourceLoader;

import org.joml.*;

public class SmokePuff extends Particle{
  public static final float SMOKEPUFFSPEED = 0.01f;

  public SmokePuff(Vector2f loc, Vector2f initialVelocity){
    super(loc, initialVelocity, 8.f);
    getVelocity().normalize().mul(SMOKEPUFFSPEED);

    setBounded(false);
    AnimatedSprite asprite = new AnimatedSprite(5, 0.1f);
    asprite.addFrame(ResourceLoader.getTexture("smoke_01"));
    asprite.addFrame(ResourceLoader.getTexture("smoke_02"));
    asprite.addFrame(ResourceLoader.getTexture("smoke_03"));
    asprite.addFrame(ResourceLoader.getTexture("smoke_04"));
    asprite.addFrame(ResourceLoader.getTexture("smoke_05"));
    setSprite(asprite);
    setLifeSpan(0.17f);
  }

  public void update(float interval){
    move(interval);
    age(interval);
    getSprite().update(interval);
  }

  public void collisionAction(AsteroidsGameObject K){
  }

}
