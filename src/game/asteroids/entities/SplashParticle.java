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
    asprite.addFrame(ResourceLoader.getTexture("puff_1"));
    asprite.addFrame(ResourceLoader.getTexture("puff_2"));
    asprite.addFrame(ResourceLoader.getTexture("puff_3"));
    asprite.addFrame(ResourceLoader.getTexture("puff_4"));
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
