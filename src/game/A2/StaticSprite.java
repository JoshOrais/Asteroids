package game.A2;

import org.joml.Vector2f;

import engine.graphics.Texture;
import engine.ResourceLoader;

public class StaticSprite extends Sprite {
  private Texture texture;
  public StaticSprite(String textureKey) {
    texture = ResourceLoader.getTexture(textureKey);
  }
  public Texture getTexture() {
    return texture;
  }
}