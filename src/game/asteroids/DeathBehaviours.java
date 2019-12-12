package game.asteroids;

import game.asteroids.entities.*;
import game.AsteroidsGame;

import org.joml.Vector2f;
import org.joml.Matrix2f;

public class DeathBehaviours{
  public static final int BIG_ASTEROID_ON_DEATH_SPAWN_COUNT = 3,
                          MEDIUM_ASTEROID_ON_DEATH_SPAWN_COUNT = 5;
  public static final float HEALING_POWER_UP_CHANCE = 0.19f,
                            TRIPLE_FIRE_POWER_UP_CHANCE = 0.09f;

  public static Behavior getbigAsteroidDeath() {
    return new bigAsteroidDeath();
  }
  public static Behavior getMediumAsteroidDeath() {
    return new mediumAsteroidDeath();
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

      if (HEALING_POWER_UP_CHANCE >= Math.random()){
        AsteroidsGame.getGame().addEntity(HealingPowerUp.createPowerUp(location));
      }

      if (TRIPLE_FIRE_POWER_UP_CHANCE >= Math.random()){
        AsteroidsGame.getGame().addEntity(TriplePowerUp.createPowerUp(location));
      }
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
    }
  }
}