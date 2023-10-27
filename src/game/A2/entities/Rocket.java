package game.A2.entities;

import game.A2.PhysicsObject;
import game.A2.StaticSprite;
import game.A2.PlayerControls;
import org.joml.Vector2f;

public class Rocket extends PhysicsObject {
  private static final float TURN_RATE = 6f;
  private static final float ACC_RATE = 300f;
  private static final float MASS = 1f;

  private PlayerControls controls = new PlayerControls();

  public Rocket() {
    super(new Vector2f());
    setSprite(new StaticSprite("rocket"));
    setSize(20f);
    setMass(MASS);
    setMaximumVelocity(200f);
  }

  public PlayerControls getControls() {
    return controls;
  }

  @Override
  public void update(float dT) {
    super.update(dT);

    setRotation(
      getRotation() + TURN_RATE * dT * controls.getSteering()
    );

    if (controls.getAcceleration() > 0) {
      getVelocity().add(
        dT * ACC_RATE * (float)Math.cos(getRotation()),
        dT * ACC_RATE * (float)Math.sin(getRotation())
      );
    }
  }
}