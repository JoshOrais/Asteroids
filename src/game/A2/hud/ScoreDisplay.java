package game.A2.hud;

import org.joml.Vector2f;
import game.A2.Sprite;
import game.A2.StaticSprite;

public class ScoreDisplay extends HudItem {
  public static final int DIGITS_COUNT = 5;
  public static final int DIGITS_PAD = 15;

  private StaticSprite sprite0 = new StaticSprite("ui-glyph-0");
  private StaticSprite sprite1 = new StaticSprite("ui-glyph-1");
  private StaticSprite sprite2 = new StaticSprite("ui-glyph-2");
  private StaticSprite sprite3 = new StaticSprite("ui-glyph-3");
  private StaticSprite sprite4 = new StaticSprite("ui-glyph-4");
  private StaticSprite sprite5 = new StaticSprite("ui-glyph-5");
  private StaticSprite sprite6 = new StaticSprite("ui-glyph-6");
  private StaticSprite sprite7 = new StaticSprite("ui-glyph-7");
  private StaticSprite sprite8 = new StaticSprite("ui-glyph-8");
  private StaticSprite sprite9 = new StaticSprite("ui-glyph-9");

  private Glyph[] glyphs;
  
  
  public ScoreDisplay(float x, float y, float w, float h) {
    super(x, y, w, h);
    System.out.println(x - w - DIGITS_PAD);
    glyphs = new Glyph[] {
      new Glyph(sprite0, -w * 2 - DIGITS_PAD * 2, 0, w, h),
      new Glyph(sprite0, -w - DIGITS_PAD, 0, w, h),
      new Glyph(sprite0, 0, 0, w, h),
      new Glyph(sprite0, w + DIGITS_PAD, 0, w, h),
      new Glyph(sprite0, w * 2 + DIGITS_PAD * 2, 0, w, h)
    };
    for (Glyph g: glyphs) {
      addChild(g);
    }
  }

  private void setGlyph(int index, int value) {
    switch(value) {
      case 0:
        glyphs[index].setSprite(sprite0);
        break;
      case 1:
        glyphs[index].setSprite(sprite1);
        break;
      case 2:
        glyphs[index].setSprite(sprite2);
        break;
      case 3:
        glyphs[index].setSprite(sprite3);
        break;
      case 4:
        glyphs[index].setSprite(sprite4);
        break;
      case 5:
        glyphs[index].setSprite(sprite5);
        break;
      case 6:
        glyphs[index].setSprite(sprite6);
        break;
      case 7:
        glyphs[index].setSprite(sprite7);
        break;
      case 8:
        glyphs[index].setSprite(sprite8);
        break;
      case 9:
        glyphs[index].setSprite(sprite9);
        break;
    }
  }

  public void setScore(int value) {
    value = value % 100000; //mimic overflow
    for (int i = DIGITS_COUNT - 1; i >= 0; i--) {
      setGlyph(i, value % 10);
      value /= 10;
    }
  }
   
  public Sprite getSprite() {
    return null;
  }

  private class Glyph extends HudItem {
    private Sprite sprite;
    public Glyph(Sprite sp, float x, float y, float w, float h) {
      super(x, y, w, h);
      sprite = sp;
    }

    public Sprite getSprite() {
      return sprite;
    }
    
    public void setSprite(Sprite sp) {
      sprite = sp;
    }
  }
}