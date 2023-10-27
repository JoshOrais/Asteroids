package game.A2.entities;

import game.A2.PhysicsObject;
import game.A2.StaticSprite;
import game.A2.SpriteAnimator;

import org.joml.Vector2f;

public class AnimatedParticle extends Particle {
  private float lifespan;

  public AnimatedParticle(SpriteAnimator sAnim, Vector2f pos, Vector2f vel, float lifespan, float size) {
    super("", pos, vel, lifespan, size);
    setSize(size);
    setSprite(sAnim.getSprite());
    addTimedAction(sAnim);
    this.lifespan = lifespan;
  }
  
  public AnimatedParticle(SpriteAnimator sAnim, Vector2f pos, float lifespan, float size) {
    this(sAnim, pos, new Vector2f(), lifespan, size);
  }
}