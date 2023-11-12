package game.A2.hud;

import org.joml.Vector2f;
import game.A2.Sprite;
import game.A2.StaticSprite;

public class HPBar {
  private float amount = 1f;
  private Vector2f pos0 = new Vector2f();
  private Vector2f size0 = new Vector2f();
  
  private Vector2f position = new Vector2f();
  private Vector2f size = new Vector2f();
  private Vector2f uvScale = new Vector2f();
  private Sprite sprite;

  private float wMax;

  public HPBar(float x, float y, float w, float h) {
    size.set(w, h);
    position.set(x + w * 0.5f, y + h * 0.5f);
    pos0.set(x, y);
    size0.set(w, h);
    
    float s = Math.min(w, h);
    wMax = w;
    sprite = new StaticSprite("smoke_04");
    uvScale.set(w / s, h / s);
    sprite.getUVScale().set(uvScale);
  }

  public void setHP(float percentage) {
    float s = Math.min(size0.x * percentage, size0.y);
    size.set(percentage * wMax, size.y);
    float s1 = size0.x / size.x;
    sprite.getUVScale().set(uvScale.x * percentage, uvScale.y);
    position.set(size.x * 0.5 + pos0.x, size.y * 0.5 + pos0.y);
  }

  public Vector2f getPosition() {
    return position;
  }

  public Vector2f getSize() {
    return size;
  }

  public Sprite getSprite() {
    return sprite;
  }
}