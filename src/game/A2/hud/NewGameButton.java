package game.A2.hud;

import org.joml.Vector2f;
import game.A2.Sprite;
import game.A2.StaticSprite;

public class NewGameButton extends HudItem {
  private Sprite sprite1;
  private Sprite sprite2;
  private boolean mouseOver = false;
  
  public NewGameButton(float x, float y, float w, float h) {
    super(x, y, w, h);
    sprite1 = new StaticSprite("ui-newgame");
    sprite2 = new StaticSprite("ui-newgame-active");
  }

  public void mouseInput(Vector2f pos, boolean mouseDown) {
    Vector2f diff = pos.sub(getPosition(), new Vector2f());
    mouseOver = diff.x < getSize().x && diff.y > -getSize().y &&
      diff.x > 0 && diff.y < 0;
  }

  public boolean isMouseOver() {
    return mouseOver;
  }

  public Sprite getSprite() {
    if (mouseOver) {
      return sprite2;
    }
    return sprite1;
  }
}