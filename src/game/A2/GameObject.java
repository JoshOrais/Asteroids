package game.A2;

import engine.GameEntity;

import org.joml.Vector2f;
import java.util.ArrayList;
import java.awt.event.ActionListener;

public class GameObject extends GameEntity {
  private float size = 30.f;
  private Sprite sprite;
  private ArrayList<TimedAction> timedEvents = new ArrayList<>();
  private boolean alive = true;
  private ActionListener onDeath = null;

  public GameObject(Vector2f pos) {
    getPosition().set(pos);
  }

  public void addTimedAction(TimedAction t) {
    timedEvents.add(t);
  }

  public float getSize() {
    return size;
  }

  public void setSize(float value) {
    size = value;
  }

  public Sprite getSprite() {
    return sprite;
  }

  public void setSprite(Sprite sprite) {
    this.sprite = sprite;
  }


  public void update(float dt) {
    for(TimedAction ta: timedEvents) {
      ta.countdown(dt);
    }
  }

  public boolean isDead() {
    return !alive;
  }

  public void kill() {
    alive = false;
  }

  public ActionListener getOnDeath() {
    return onDeath;
  }

  public void setOnDeath(ActionListener al) {
    onDeath = al;
  }
}