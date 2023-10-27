package game.asteroids.entities;

import game.asteroids.AsteroidsGameObject;
import game.asteroids.HitBox;
import game.asteroids.graphics.AnimatedSprite;
import engine.ResourceLoader;

import org.joml.*;

public class SplashParticle extends Particle{

  public SplashParticle(Vector2f loc, float lifespan){
    super(loc, new Vector2f(), 5f);

    setBounded(false);
    AnimatedSprite asprite = new AnimatedSprite(4, lifespan);
    asprite.addFrame(ResourceLoader.getTexture("puff_1"));
    asprite.addFrame(ResourceLoader.getTexture("puff_2"));
    asprite.addFrame(ResourceLoader.getTexture("puff_3"));
    asprite.addFrame(ResourceLoader.getTexture("puff_4"));
    setSprite(asprite);
    setLifeSpan(lifespan);
  }

  public void update(float interval){
    age(interval);
    float newScale = getScale() + 2.5f * interval;
    setScale(newScale);
    getSprite().update(interval);
  }

  public void collisionAction(AsteroidsGameObject K){
  }


}
