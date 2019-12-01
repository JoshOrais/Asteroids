package game.asteroids;

import org.joml.Vector2f;
import org.joml.Vector3f;
import game.asteroids.entities.*;
import game.AsteroidsGame;

public class FiringBehaviours{
  public static Behavior getNormalBulletBehaviour(){
    return (Behavior)(new normalBulletBehaviour());
  }

  public static class normalBulletBehaviour implements Behavior{
    public void execute(Vector2f location, Vector2f target){
      PlayerBullet pb = new PlayerBullet(AsteroidsGame.getGame().getPlayer(), new Vector3f(location, 0.0f), target);
      AsteroidsGame.getGame().addEntity(pb);
    }
  }
}
