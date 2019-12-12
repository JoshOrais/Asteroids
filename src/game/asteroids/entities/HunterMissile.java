package game.asteroids.entities;

import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Matrix2f;
import game.asteroids.AsteroidsGameObject;
import game.AsteroidsGame;
import game.asteroids.graphics.*;
import game.asteroids.PlayerShip;
import game.asteroids.PointsCalculator;
import game.asteroids.HitBox;
import engine.ResourceLoader;
public class HunterMissile extends AsteroidsGameObject{
  public static HunterMissile instance = null;
  private Vector3f closestMissile = new Vector3f(0.f, 0.f, 0.f);

  public static final float MAX_VELOCITY = 4.08f,
                            SIZE = 12.f,
                            HANDLING = 100.f;

  private HunterMissile(){
    this.position = new Vector3f(0.f, 0.f, 0.f);
    this.scale = SIZE;
    this.velocity = new Vector2f(0.f, 0.f);
    this.max_velocity = MAX_VELOCITY;
    this.hitbox = new HitBox(this.position, scale);
    setBounds(-3000.f, -3000.f, 3000.f, 3000.f);
    setSprite(new StaticSprite(ResourceLoader.getTexture("missile")));
    setTimedLife(true);
    setLifeSpan(60.f);
    alive = false;
  }

  public void collisionAction(AsteroidsGameObject K){
    if (K instanceof PlayerShip || K instanceof Asteroid){
      // K.damage(100.f);
      // kill();
    }
  }

  public void spawn(Vector2f location, Vector2f initialVelocity){
    this.position = new Vector3f(location, 0.f);

    this.velocity = initialVelocity.normalize().mul(MAX_VELOCITY);
    double acos = Math.acos(velocity.x / velocity.length());
    this.rotation = (float)acos;

    this.alive = true;
    AsteroidsGame.getGame().addHunter();
  }

  public void update(float interval){
    age(interval);
    // move(interval);
    System.out.println("I AM ALIVE, MY position IS "+ position.x + ", " + position.y);
    System.out.println("PLAYER position is "+ AsteroidsGame.getGame().getPlayer().getPosition().x + ", " + AsteroidsGame.getGame().getPlayer().getPosition().y);
    Vector3f player_pos3 = AsteroidsGame.getGame().getPlayer().getPosition();
    Vector2f player_vel2 = AsteroidsGame.getGame().getPlayer().getVelocity();

    Vector2f player_pos = new Vector2f(player_pos3.x, player_pos3.y);
    Vector2f player_vel = new Vector2f(player_vel2);

    Vector2f hunter_pos = new Vector2f(position.x, position.y);
    Vector2f hunter_vel = new Vector2f(velocity);

    int a = PointsCalculator.calculateMove(player_pos, player_vel, HANDLING * interval, hunter_pos, hunter_vel, HANDLING * interval, new Vector2f(0.f), new Vector2f(1.f),4);
    Matrix2f rot = new Matrix2f().identity();
    switch(a){
      case 1:
        break;
      case 2:
        rot.rotate(-HANDLING * interval);
        rotation += -HANDLING * interval;
        break;
      case 3:
        rot.rotate(HANDLING * interval);
        rotation += HANDLING * interval;
        break;
    }

    hunter_vel.mul(rot);
    velocity = hunter_vel;
    position.add(new Vector3f(hunter_vel, 0.f));
  }

  public void setClosestMissile(Vector3f pos){

  }


  public static HunterMissile getHunterMissile(){
    if (instance == null)
      instance = new HunterMissile();
    return instance;
  }

}
