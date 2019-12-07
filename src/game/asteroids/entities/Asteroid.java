package game.asteroids.entities;

import org.joml.*;
import game.asteroids.AsteroidsGameObject;
import game.asteroids.graphics.*;
import game.asteroids.HitBox;
import engine.ResourceLoader;

public class Asteroid extends AsteroidsGameObject{
  private float radius;
  private Vector2f v;

  public Asteroid(Vector3f position, Vector2f velocity, float radius){
    this.position = position;
    this.radius = radius;
    this.scale = radius;
    this.velocity = velocity;
    this.max_velocity = 1.0f;
    this.hitbox = new HitBox(this.position, scale);
		setBounds(-3000.f, -3000.f, 3000.f, 3000.f);
		setSprite(new StaticSprite(ResourceLoader.getTexture("default")));
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
      this.kill();
  }

  @Override
  public void kill(){
    if (this.deathBehaviour != null){
      deathBehaviour.setTarget(velocity);
      deathBehaviour.setLocation(new Vector2f(position.x, position.y));
    }
    if (scale > 15.f)
      super.kill();
  }

}
