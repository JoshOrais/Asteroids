package game.A2;

import org.joml.Vector2f;

public class PhysicsObject extends GameObject {
  private Vector2f velocity = new Vector2f();
  private float mass = 1f;
  private float maximumVelocity = 2000f;
  
  public PhysicsObject(Vector2f pos) {
    super(pos);
  }

  public PhysicsObject(Vector2f pos, Vector2f vel) {
    super(pos);
    velocity.set(vel);
  }

  public Vector2f getVelocity() {
    return velocity;
  }
  
  public float getMass() {
    return mass;
  }
  
  public void setMass(float value) {
    mass = value;
  }

  public void setMaximumVelocity(float value) {
    maximumVelocity = value;
  }

  @Override
  public void update(float dT) {
    super.update(dT);
    getPosition().add(velocity.mul(dT, new Vector2f()));
   
    if (maximumVelocity >= 0 && velocity.length() > maximumVelocity) {
      velocity.normalize(maximumVelocity);
    } 
  }
}