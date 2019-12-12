package game.asteroids.entities;

import org.joml.Vector2f;
import org.joml.Vector3f;
import game.asteroids.AsteroidsGameObject;
import game.asteroids.graphics.*;
import game.asteroids.PlayerShip;
import game.asteroids.HitBox;
import game.asteroids.DeathBehaviours;
import engine.ResourceLoader;

public class TriplePowerUp extends AsteroidsGameObject{
  public static final float MAX_VELOCITY = 0.2f,
                            SIZE = 15.f;

  public TriplePowerUp(Vector3f position, Vector2f velocity, float radius){
    this.position = position;
    this.scale = radius;
    this.velocity = velocity;
    this.max_velocity = 1.0f;
    this.hitbox = new HitBox(this.position, scale);
    setBounds(-3000.f, -3000.f, 3000.f, 3000.f);
    setSprite(new StaticSprite(ResourceLoader.getTexture("triple")));
    setDeathBehaviour(DeathBehaviours.getPowerUpSplash());
  }

  public void collisionAction(AsteroidsGameObject K){
    if (K instanceof PlayerShip){
      ((PlayerShip)K).addTripleFire(15.f);
      kill();
    }
  }

  public void update(float timestep){
    move(timestep);
  }

  public static TriplePowerUp createPowerUp(Vector2f location){
    Vector2f vel = new Vector2f();
    vel.x = (float)Math.random() - 0.5f;
    vel.y = (float)Math.random() - 0.5f;

    vel.normalize().mul(MAX_VELOCITY);

    return new TriplePowerUp(new Vector3f(location, 0.f), vel, SIZE);
  }
}
