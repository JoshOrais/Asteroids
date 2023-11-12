package game.A2.entities;

import game.A2.PhysicsObject;
import game.A2.StaticSprite;
import game.A2.Sprite;

import org.joml.Vector2f;

public class Particle extends PhysicsObject {
  private float lifespan;

  public Particle(String textureKey, Vector2f pos, Vector2f vel, float lifespan, float size) {
    this(new StaticSprite(textureKey), pos, vel, lifespan, size);
  }

  public Particle(Sprite sprite, Vector2f pos, Vector2f vel, float lifespan, float size) {
    super(pos, vel);
    setSize(size);
    setSprite(sprite);

    this.lifespan = lifespan;
  }
  
  public Particle(String textureKey, Vector2f pos, float lifespan, float size) {
    this(textureKey, pos, new Vector2f(), lifespan, size);
  }

  @Override
  public void update(float dT) {
    super.update(dT);
    lifespan -= dT;
    if (lifespan <= 0) {
      kill();
    }
  }
}