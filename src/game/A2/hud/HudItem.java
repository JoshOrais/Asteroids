package game.A2.hud;

import java.util.ArrayList;
import org.joml.Vector2f;
import game.A2.Sprite;

public abstract class HudItem {
  private Vector2f size = new Vector2f();
  private Vector2f pos = new Vector2f();
  private ArrayList<HudItem> children = new ArrayList<>();

  public HudItem(float x, float y, float w, float h) {
    size.set(w, h);
    pos.set(x, y);
  }

  public Vector2f getSize() {
    return size;
  }
   
  public Vector2f getPosition() {
    return pos;
  }

  public abstract Sprite getSprite();

  public ArrayList<HudItem> getChildren() {
    return children;
  }

  //fucking kill me
  public void addChild(HudItem hi) {
    children.add(hi);
  }
}