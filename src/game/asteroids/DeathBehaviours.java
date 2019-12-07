package game.asteroids;

import game.asteroids.entities.*;
import game.AsteroidsGame;

import org.joml.Vector2f;

public class DeathBehaviours{

  public static Behavior getbigAsteroidDeath() {
    return new bigAsteroidDeath();
  }

  public static class bigAsteroidDeath extends Behavior{
    public void execute(){
      Vector2f vel = new Vector2f();
      float random = (float)Math.random() * 3.1415f;
      vel.x = (float)Math.cos(random);
      vel.y = (float)Math.sin(random);
      Asteroid a = AsteroidFactory.createMediumAsteroid(location, vel);

       random = (float)Math.random() * 3.1415f;
      vel.x = (float)Math.cos(random);
      vel.y = (float)Math.sin(random);
      Asteroid b = AsteroidFactory.createMediumAsteroid(location, vel);

       random = (float)Math.random() * 3.1415f;
      vel.x = (float)Math.cos(random);
      vel.y = (float)Math.sin(random);
      Asteroid c = AsteroidFactory.createMediumAsteroid(location, vel);

      AsteroidsGame.getGame().addEntity(a);
      AsteroidsGame.getGame().addEntity(b);
      AsteroidsGame.getGame().addEntity(c);
    }
  }
}
