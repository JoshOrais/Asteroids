package game.asteroids.entities;

import org.joml.*;
import game.asteroids.AsteroidsGameObject;
import game.asteroids.graphics.*;
import game.asteroids.HitBox;
import engine.ResourceLoader;

public class Asteroid extends AsteroidsGameObject{
  private float radius;
  private Vector2f v;

  public Asteroid(Vector2f position, Vector2f velocity, float radius){
    super(position, velocity, radius, null);
    setBounds(-3000.f, -3000.f, 3000.f, 3000.f);
    setSprite(new StaticSprite(ResourceLoader.getTexture("asteroid")));
  }

  public void change(){
    setSprite(new StaticSprite(ResourceLoader.getTexture("rocket")));
  }

  @Override
  public void update(float interval){
    this.move(interval);
  }

  public void collisionAction(AsteroidsGameObject K){
    if (!(K instanceof Asteroid))
      damage(K.getHitDamage());
  }

  @Override
  public void kill(){
    super.kill();
  }

}
