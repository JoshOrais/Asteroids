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
    this.max_velocity = 0.5f;
    this.hitbox = new HitBox(this.position, scale);
		setBounds(-3000.f, -300.f, 3000.f, 300.f);
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
    System.out.println("WTF");
    this.kill();
  }
}
