package game.asteroids.entities;

import org.joml.Vector2f;
import org.joml.Vector3f;
import game.asteroids.AsteroidsGameObject;
import game.asteroids.graphics.*;
import game.asteroids.PlayerShip;
import game.asteroids.HitBox;
import engine.ResourceLoader;
public class HunterMissile extends AsteroidsGameObject{
  public static HunterMissile instance = null;

  public static final float MAX_VELOCITY = 0.2f,
                            SIZE = 15.f;

  private HunterMissile(Vector3f position, Vector2f velocity, float radius){
    this.position = position;
    this.scale = radius;
    this.velocity = velocity;
    this.max_velocity = 1.0f;
    this.hitbox = new HitBox(this.position, scale);
    setBounds(-3000.f, -3000.f, 3000.f, 3000.f);
    setSprite(new StaticSprite(ResourceLoader.getTexture("anime")));
    setTimedLife(true);
    setLifeSpan(60.f);
    alive = false;
  }

  public void collisionAction(AsteroidsGameObject K){
    if (K instanceof PlayerShip || K instanceof Asteroid){
      K.damage(100.f);
      kill();
    }
  }

  public void update(float timestep){
    move(timestep);
  }

  public void spawn(Vector2f location){}

  public static HunterMissile getHunterMissile(){
    if (instance == null)
      instance = new HunterMissile(new Vector3f(0.0f, 0.0f, 0.0f), new Vector2f(0.0f, 0.0f), 20.f);
    return instance;
  }

}
