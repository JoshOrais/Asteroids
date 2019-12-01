package game.asteroids;

import org.joml.Vector2f;
import org.joml.Vector3f;

public interface Behavior{
  public void execute(Vector2f location, Vector2f target);
}
