package game.asteroids.entities;

import game.asteroids.AsteroidsGameObject;
import game.asteroids.HitBox;
import game.asteroids.DeathBehaviours;
import game.asteroids.graphics.AnimatedSprite;
import engine.ResourceLoader;

import org.joml.*;

public class HunterSpawner extends Particle{

  private int growth_direction = -1;

  public HunterSpawner(Vector3f loc){
    this.position = loc;

    this.scale = 30.2f;
    this.hitbox = new HitBox(new Vector2f(loc.x, loc.y), scale);
    setBounded(false);
    AnimatedSprite asprite = new AnimatedSprite(2, 2.1f);
    asprite.addFrame(ResourceLoader.getTexture("UFO"));
    asprite.addFrame(ResourceLoader.getTexture("UFO"));
    setSprite(asprite);
    setLifeSpan(1.7f);
    setDeathBehaviour(DeathBehaviours.getSpawnHunter());
  }

  public void update(float interval){
    age(interval);
    if (scale < 10.f) growth_direction = 1;
    this.scale += 10.f * interval * growth_direction;
    getSprite().update(interval);
  }

  public void collisionAction(AsteroidsGameObject K){

  }

}
