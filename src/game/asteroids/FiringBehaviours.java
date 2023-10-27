package game.asteroids;

import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Matrix2f;
import game.asteroids.entities.*;
import game.AsteroidsGame;

public class FiringBehaviours{
  public static Behavior getNormalBulletBehaviour(){
    return (Behavior)(new normalBulletBehaviour());
  }

  public static Behavior getTripleBulletBehaviour(){
    return (Behavior)(new tripleBulletBehaviour());
  }

  public static Behavior getSpawnAsteroidBehaviour(){
    return (Behavior)(new spawnAsteroidBehaviour());
  }

  public static Behavior getPuffSpawner(){
    return (Behavior)(new smokePuffSpawner());
  }

  public static class normalBulletBehaviour extends Behavior{
    public void execute(){
      PlayerBullet pb = new PlayerBullet(AsteroidsGame.getGame().getPlayer(), new Vector2f(location), target);
      AsteroidsGame.getGame().addEntity(pb);
    }
  }

  public static class tripleBulletBehaviour extends Behavior{
    public void execute(){
      Matrix2f rot = new Matrix2f().rotate(-0.223599f); // -30.0 degrees in radians
      Vector2f vel = new Vector2f(target);
      vel.mul(rot);
      rot.identity().rotate(0.223599f);// -30.0 degrees in radians

      for (int i = 0; i < 3; ++i){
        AsteroidsGame.getGame().addEntity(new PlayerBullet(AsteroidsGame.getGame().getPlayer(), new Vector2f(location), new Vector2f(vel)));
        vel.mul(rot);
      }
    }
  }

  public static class spawnAsteroidBehaviour extends Behavior{
    public void execute(){
      AsteroidsGame.getGame().addEntity(
        AsteroidFactory.createLargeAsteroidWithinBounds(AsteroidsGame.GAME_BOUNDS_MIN_X,
                                                        AsteroidsGame.GAME_BOUNDS_MIN_Y,
                                                        AsteroidsGame.GAME_BOUNDS_MAX_X,
                                                        AsteroidsGame.GAME_BOUNDS_MAX_Y)
      );
    }
  }

  public static class smokePuffSpawner extends Behavior{
    public void execute(){
      for (int i = 0; i < 3; ++i){
        float rand = (float)Math.random();
        Vector2f vel = new Vector2f(target.x, target.y);
        AsteroidsGame.getGame().addEntity(new SmokePuff(new Vector2f(location.x + rand, location.y - rand), new Vector2f(vel.x, vel.y)));
      }
    }
  }
}
