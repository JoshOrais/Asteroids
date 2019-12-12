package game.asteroids.entities;

import org.joml.Vector3f;
import org.joml.Vector2f;

import game.asteroids.DeathBehaviours;

public class AsteroidFactory{
  public static float ASTEROID_SIZE_LARGE = 32.0f,
                      ASTEROID_SIZE_MEDIUM = 23.0f,
                      ASTEROID_SIZE_SMALL = 15.0f,
                      ASTEROID_SPEED_LARGE = 0.5f,
                      ASTEROID_SPEED_MEDIUM = 2.0f,
                      ASTEROID_SPEED_SMALL = 5.8f,
                      ASTEROID_DAMAGE_LARGE = 7.f,
                      ASTEROID_DAMAGE_MEDIUM = 5.2f,
                      ASTEROID_DAMAGE_SMALL = 2.f,
                      ASTEROID_HP_LARGE = 37.f,
                      ASTEROID_HP_MEDIUM = 25.f,
                      ASTEROID_HP_SMALL = 15.f;

  public static Asteroid createLargeAsteroid(Vector2f loc, Vector2f initialVelocity){
    Vector2f velocity_actual = initialVelocity.normalize().mul(ASTEROID_SPEED_LARGE);
    Asteroid a = new Asteroid( new Vector3f(loc, 0.0f), velocity_actual, ASTEROID_SIZE_LARGE);
    a.setDeathBehaviour(DeathBehaviours.getbigAsteroidDeath());
    a.setHitDamage(ASTEROID_DAMAGE_LARGE);
    a.setHP(ASTEROID_HP_LARGE);
    return a;
  }

  public static Asteroid createLargeAsteroid(float x, float y, float velocity_x, float velocity_y){
    return createLargeAsteroid(new Vector2f(x, y), new Vector2f(velocity_x, velocity_y));
  }

  public static Asteroid createMediumAsteroid(Vector2f loc, Vector2f initialVelocity){
    Vector2f velocity_actual = initialVelocity.normalize().mul(ASTEROID_SPEED_MEDIUM);
    Asteroid a = new Asteroid( new Vector3f(loc, 0.0f), velocity_actual, ASTEROID_SIZE_MEDIUM);
    a.setHP(ASTEROID_HP_MEDIUM);
    a.setHitDamage(ASTEROID_DAMAGE_MEDIUM);
    a.setDeathBehaviour(DeathBehaviours.getMediumAsteroidDeath());
    return a;
  }

  public static Asteroid createMediumAsteroid(float x, float y, float velocity_x, float velocity_y){
    return createMediumAsteroid(new Vector2f(x, y), new Vector2f(velocity_x, velocity_y));
  }

  public static Asteroid createSmallAsteroid(Vector2f loc, Vector2f initialVelocity){
    Vector2f velocity_actual = initialVelocity.normalize().mul(ASTEROID_SPEED_SMALL);
    Asteroid a =  new Asteroid( new Vector3f(loc, 0.0f), velocity_actual, ASTEROID_SIZE_SMALL);
    a.setHP(ASTEROID_HP_SMALL);
    a.setHitDamage(ASTEROID_DAMAGE_SMALL);
    return a;
  }

  public static Asteroid createSmallAsteroid(float x, float y, float velocity_x, float velocity_y){
    return createSmallAsteroid(new Vector2f(x, y), new Vector2f(velocity_x, velocity_y));
  }

  public static Asteroid createLargeAsteroidWithinBounds(float xmin, float ymin, float xmax, float ymax){
    float xPos = (float)Math.random() * (float)(xmax - xmin) + xmin;
    float yPos = (float)Math.random() * (float)(ymax - ymin) + ymin;
    float xVel = (float)Math.random() * 200.f - 100.f;
    float yVel = (float)Math.random() * 200.f - 100.f;

    return createLargeAsteroid(xPos, yPos, xVel, yVel);
  }
}
