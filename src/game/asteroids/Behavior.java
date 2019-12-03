package game.asteroids;

import org.joml.Vector2f;
import org.joml.Vector3f;

public abstract class Behavior{
  protected Vector2f location, target;

  public abstract void execute();

  public Vector2f getLocation(){
    return location;
  }

  public void setLocation(Vector2f loc){
    this.location = loc;
  }

  public void setLocation(float x, float y){
    setLocation( new Vector2f(x, y) );
  }

  public Vector2f getTarget(){
    return target;
  }

  public void setTarget(Vector2f target){
    this.target = target;
  }

  public void setTarget(float x, float y){
    setTarget( new Vector2f(x, y) );
  }
}
