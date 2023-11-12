package engine;

import org.joml.Vector2f;
import engine.graphics.Texture;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.util.HashMap;

public class FontTexture {
  private HashMap<Character, CharInfo> charMap = new HashMap<>();
  private Texture texture;
  private float width;
  private float height;

  private FontTexture() {  }

  public Texture getTexture() {
    return texture;
  }

  public Vector2f getUVTranslate(char c) {
    CharInfo cInfo = charMap.get(c);
    return new Vector2f(cInfo.startX / width, 0f);
  }

  public Vector2f getUVScale(char c) {
    CharInfo cInfo = charMap.get(c);
    return new Vector2f(cInfo.width / width, 0f);
  }

  public static FontTexture from(Font font, String chars) throws Exception {
    BufferedImage img = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
    Graphics2D g2D = img.createGraphics();
    g2D.setFont(font);
    FontMetrics fM = g2D.getFontMetrics();

    FontTexture result = new FontTexture();
    float width = 0f;
    float height = 0f;
    
    for (char c: chars.toCharArray()) {
      CharInfo cInfo = new CharInfo(width, fM.charWidth(c));
      result.charMap.put(c, cInfo);
      width += cInfo.getWidth();
      height = Math.max(height, fM.getHeight());
    }
    g2D.dispose();

    img = new BufferedImage((int)width, (int)height, BufferedImage.TYPE_INT_ARGB);
    g2D = img.createGraphics();

    g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    g2D.setFont(font);
    fM = g2D.getFontMetrics();
    g2D.setColor(Color.WHITE);
    g2D.drawString(chars, 0, fM.getAscent());

    g2D.dispose();

    result.width = width;
    result.height = height;
    result.texture = new Texture(img);

    return result;
  }

  public static class CharInfo {
    public final float startX;
    public final float width;
    public CharInfo(float sx, float w) {
      startX = sx;
      width = w;
    }

    public float getWidth() {
      return width;
    }
  
    public float getStartX() {
      return startX;
    }
  }
}