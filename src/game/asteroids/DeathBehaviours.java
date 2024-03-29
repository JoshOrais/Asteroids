package game.asteroids;

import game.asteroids.entities.*;
import game.AsteroidsGame;

import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Matrix2f;

public class DeathBehaviours{
  public static final int BIG_ASTEROID_ON_DEATH_SPAWN_COUNT = 3,
                          MEDIUM_ASTEROID_ON_DEATH_SPAWN_COUNT = 5;
  public static final float HEALING_POWER_UP_CHANCE = 0.09f,
                            TRIPLE_FIRE_POWER_UP_CHANCE = 0.09f,
                            SHIELD_POWER_UP_CHANCE = 0.09f;

  public static Behavior getbigAsteroidDeath() {
    return new bigAsteroidDeath();
  }
  public static Behavior getMediumAsteroidDeath() {
    return new mediumAsteroidDeath();
  }
  public static Behavior getSmallAsteroidDeath() {
    return new smallAsteroidDeath();
  }

  public static Behavior getPowerUpSplash(){
    return new powerUpSplash();
  }

  public static class bigAsteroidDeath extends Behavior{
    public void execute(){
      Vector2f vel = new Vector2f((float)Math.random() * 300.f, (float)Math.random() * 300.f);
      Matrix2f rotation = new Matrix2f().identity();

      rotation.identity().rotate((float)Math.toRadians(360.f / BIG_ASTEROID_ON_DEATH_SPAWN_COUNT));
      for (int i = 0; i < BIG_ASTEROID_ON_DEATH_SPAWN_COUNT; ++i){
        vel.mul(rotation);
        AsteroidsGame.getGame().addEntity(AsteroidFactory.createMediumAsteroid(location, new Vector2f(vel)));
      }

      AsteroidsGame.getGame().addScore(40);
    }
  }

  public static class mediumAsteroidDeath extends Behavior{
    public void execute(){
      Vector2f vel = new Vector2f((float)Math.random() * 300.f, (float)Math.random() * 300.f);
      Matrix2f rotation = new Matrix2f().identity();

      rotation.identity().rotate((float)Math.toRadians(360.f / MEDIUM_ASTEROID_ON_DEATH_SPAWN_COUNT));
      for (int i = 0; i < MEDIUM_ASTEROID_ON_DEATH_SPAWN_COUNT; ++i){
        vel.mul(rotation);
        AsteroidsGame.getGame().addEntity(AsteroidFactory.createSmallAsteroid(location, new Vector2f(vel)));
      }

      AsteroidsGame.getGame().addScore(20);
    }
  }

  public static class smallAsteroidDeath extends Behavior{
    public void execute(){
      AsteroidsGame.getGame().addScore(10);
    }
  }

  public static class powerUpSplash extends Behavior{
    public void execute(){
      AsteroidsGame.getGame().addEntity(new SplashParticle(location, 0.25f));
    }
  }
}
