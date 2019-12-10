package game.asteroids;

public class Timer{
  private boolean ready, repeat;
  private float interval, accumulatedTime;
  private Behavior action;

  public Timer(float interval, boolean repeat){
    this.interval = interval;
    this.repeat = repeat;
    ready = false;
  }

  public Timer(float interval){
    this(interval, true);
  }

  public void update(float timestep){
    accumulatedTime += timestep;
    if (accumulatedTime > interval){
      if (repeat)
        accumulatedTime = 0;
      ready = true;
    }
  }

  public void setBehaviour(Behavior B){
    this.action = B;
  }

  public float getAccumulatedTime(){
    return accumulatedTime;
  }

  public void setAction(Behavior A){
    this.action = A;
  }

  public boolean isReady(){
    return ready;
  }

  public void fire(){
    if (ready){
      action.execute();
      ready = false;
    }
  }
}
