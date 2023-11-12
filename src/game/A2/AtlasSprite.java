package game.A2;

import org.joml.Vector2f;

import engine.graphics.Texture;
import engine.ResourceLoader;

public class AtlasSprite extends Sprite {
  private Texture texture;
  public final int W;
  public final int H;

  public AtlasSprite(String textureKey, int w, int h) {
    texture = ResourceLoader.getTexture(textureKey);
    
    getUVScale().mul(1f / (float)w, 1f / (float)h);
    
    W = w;
    H = h;
  }

  public AtlasSprite setActiveTexture(int x, int y) {
    float yTop = (float)(H - y - 1);
    getUVTranslate().set((float)x, (float)yTop);

    return this;
  }

  public Texture getTexture() {
    return texture;
  }
}