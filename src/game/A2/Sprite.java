package game.A2;

import org.joml.Vector2f;

import engine.graphics.Texture;

public abstract class Sprite {
  private Vector2f uvTranslate = new Vector2f();
  private Vector2f uvScale = new Vector2f(1f, 1f);

  public abstract Texture getTexture();

  public Vector2f getUVTranslate() {
    return uvTranslate;
  }

  public Vector2f getUVScale() {
    return uvScale;
  }
}