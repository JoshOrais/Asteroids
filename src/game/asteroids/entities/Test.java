package game.asteroids.entities;

import org.joml.Vector2f;
import org.joml.Vector3f;
import game.asteroids.AsteroidsGameObject;
import game.asteroids.graphics.*;
import game.asteroids.PlayerShip;
import game.asteroids.HitBox;
import engine.ResourceLoader;
public class Test extends AsteroidsGameObject{
  public static final float MAX_VELOCITY = 0.2f,
                            SIZE = 15.f;

  public Test(){
    this.position = new Vector3f(15.f, 30.f, 0.0f);
    this.scale = 10.f;
    this.velocity = new Vector2f(0.f, 0.f);
    this.max_velocity = 1.0f;
    this.hitbox = new HitBox(this.position, scale);
    setBounds(-3000.f, -3000.f, 3000.f, 3000.f);
    AnimatedSprite a = new AnimatedSprite(3, 5.f);
    a.addFrame(ResourceLoader.getTexture("rocket"));
    a.addFrame(ResourceLoader.getTexture("anime"));
    a.addFrame(ResourceLoader.getTexture("default"));
    setSprite(a);
  }

  public void collisionAction(AsteroidsGameObject K){
  }

  public void update(float timestep){
    getSprite().update(timestep);
  }
}
