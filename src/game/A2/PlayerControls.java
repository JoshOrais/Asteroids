package game.A2;

public class PlayerControls {
  private int steering = 0;
  private int acceleration = 0;

  public int getSteering() {
    return steering;
  }

  public int getAcceleration() {
    return acceleration;
  }

  public void setSteering(boolean left, boolean right) {
    if (left == right) {
      steering = 0;
    } else if (left) {
      steering = 1;
    } else {
      steering = -1;
    }
  }

  public void setAccelerating(boolean acc) {
    if (acc) { 
      acceleration = 1;
    } else {
      acceleration = 0;
    }
  }
}