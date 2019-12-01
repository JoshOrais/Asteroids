package game.asteroids.entities;

import org.joml.Vector3f;
import org.joml.Vector2f;

public class AsteroidFactory{
  public static float ASTEROID_SIZE_LARGE = 30.0f,
                      ASTEROID_SIZE_MEDIUM = 12.0f,
                      ASTEROID_SIZE_SMALL = 6.0f,
                      ASTEROID_SPEED_LARGE = 1.2f,
                      ASTEROID_SPEED_MEDIUM = 4.0f,
                      ASTEROID_SPEED_SMALL = 7.8f;

  public static Asteroid createLargeAsteroid(Vector2f loc, Vector2f initialVelocity){
    Vector2f velocity_actual = initialVelocity.normalize().mul(ASTEROID_SPEED_LARGE);
    return new Asteroid( new Vector3f(loc, 0.0f), velocity_actual, ASTEROID_SIZE_LARGE);
  }

  public static Asteroid createMediumAsteroid(Vector2f loc, Vector2f initialVelocity){
    Vector2f velocity_actual = initialVelocity.normalize().mul(ASTEROID_SPEED_MEDIUM);
    return new Asteroid( new Vector3f(loc, 0.0f), velocity_actual, ASTEROID_SIZE_MEDIUM);
  }

  public static Asteroid createSmallAsteroid(Vector2f loc, Vector2f initialVelocity){
    Vector2f velocity_actual = initialVelocity.normalize().mul(ASTEROID_SPEED_SMALL);
    return new Asteroid( new Vector3f(loc, 0.0f), velocity_actual, ASTEROID_SIZE_SMALL);
  }
}
