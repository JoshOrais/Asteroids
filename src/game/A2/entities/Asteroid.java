package game.A2.entities;

import game.A2.PhysicsObject;
import game.A2.StaticSprite;
import game.A2.AtlasSprite;
import game.A2.SpriteAnimator;
import game.A2Game;
import org.joml.Vector2f;

public class Asteroid extends PhysicsObject {
  private float hp;

  public Asteroid(Vector2f pos, Vector2f vel) {
    super(pos, vel);
    setSprite(new StaticSprite("asteroid"));
    hp = 1.5f;

    setOnDeath(e -> {
      if (e.getSource() instanceof A2Game) {
        Asteroid a = new Asteroid(getPosition(), getVelocity());
        a.setSize(15);
        ((A2Game)e.getSource()).getEntitySpawner().spawnEnemy(a);

        SpriteAnimator sAnim = new SpriteAnimator(
          new AtlasSprite("booms", 11, 15), 
          new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9},
          0.12f
        );
        ((A2Game)e.getSource()).getEntitySpawner().spawnParticle(sAnim,  getPosition(), 1.1f, getSize(), 0f);
      }
    });
  }

  @Override
  public boolean isDead() {
    return hp <= 0;
  }

  public void damage(float amount) {
    hp -= amount;
  }
}