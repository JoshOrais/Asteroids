package game.asteroids.entities;

import game.asteroids.AsteroidsGameObject;
import game.asteroids.HitBox;
import game.asteroids.graphics.AnimatedSprite;
import engine.ResourceLoader;

import org.joml.*;

public class SplashParticle extends Particle{

  public SplashParticle(Vector3f loc, float lifespan){
    this.position = loc;

    this.scale = 5f;
    this.hitbox = new HitBox(new Vector2f(loc.x, loc.y), scale);
    setBounded(false);
    AnimatedSprite asprite = new AnimatedSprite(4, lifespan);
    asprite.addFrame(ResourceLoader.getTexture("boom_01"));
    asprite.addFrame(ResourceLoader.getTexture("boom_02"));
    asprite.addFrame(ResourceLoader.getTexture("boom_03"));
    asprite.addFrame(ResourceLoader.getTexture("boom_04"));
    asprite.addFrame(ResourceLoader.getTexture("boom_05"));
    asprite.addFrame(ResourceLoader.getTexture("boom_06"));
    asprite.addFrame(ResourceLoader.getTexture("boom_07"));
    asprite.addFrame(ResourceLoader.getTexture("boom_08"));
    asprite.addFrame(ResourceLoader.getTexture("boom_09"));
    asprite.addFrame(ResourceLoader.getTexture("boom_10"));
    setSprite(asprite);
    setLifeSpan(lifespan);
  }

  public void update(float interval){
    age(interval);
    this.scale += 2.5 * interval;
    getSprite().update(interval);
  }

  public void collisionAction(AsteroidsGameObject K){
  }


}
