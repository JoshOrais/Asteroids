package game.A2.hud;

import org.joml.Vector2f;
import game.A2.Sprite;
import game.A2.StaticSprite;

public class HealthBar extends HudItem {
  private StaticSprite sprite;
  private BarContent fill;

  public HealthBar(float x, float y, float w, float h) {
    super(x, y, w, h);
    sprite = new StaticSprite("ui-barframe");
    fill = new BarContent(0, 0, w, h);
    addChild(fill);
  }

  public void setHPPercentage(float val) {
    fill.setPercentage(val);
  }

  @Override
  public Sprite getSprite() {
    return sprite;
  }

  private class BarContent extends HudItem {
    private Vector2f size;
    private Vector2f uvScale0;
    private StaticSprite sprite;

    public BarContent(float x, float y, float w, float h) {
      super(x, y, w, h);
      size = new Vector2f(w, h);
      sprite = new StaticSprite("ui-barfill");

      float s = Math.min(w, h);
      uvScale0 = new Vector2f(w / s, h /s);
      sprite.getUVScale().set(uvScale0);
    }

    public void setPercentage(float val) {
      Vector2f s0 = super.getSize();
     
      size.set(val * s0.x, s0.y);

      sprite.getUVScale().set(
        uvScale0.x * val, uvScale0.y
      );
    }
    
    @Override
    public Vector2f getSize() { 
      return size;
    }

    @Override
    public Sprite getSprite() {
      return sprite;
    }
  }
}