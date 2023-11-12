package game.A2.entities;

import game.A2.PhysicsObject;
import game.A2.StaticSprite;
import game.A2.AtlasSprite;
import game.A2.SpriteAnimator;
import game.A2.EntitySpawner;
import game.A2Game;
import org.joml.Vector2f;

public class Asteroid extends PhysicsObject {
  private static final float[] SIZE_CUTOFFS = {45f, 20f, 13f};
  private static final float[] VEL_MULT = {1f, 1.2f, 2f};
  private static final float[] MASS_VALUES = {1f, 0.7f, 0.2f};
  private static final float[] DAMAGE_AMOUNT = {0.1f, 0.05f, 0.01f};
  private static final int[] BREAK_COUNT = {1, 2, 3};
  private float hp;
  private float damage = 0.1f;

  public Asteroid(Vector2f pos, Vector2f vel) {
    super(pos, vel);
    setSize(45f);
    setSprite(new StaticSprite("asteroid"));
    hp = 1.5f;

    setOnDeath(e -> {
      if (e.getSource() instanceof A2Game) {
        EntitySpawner spawner = ((A2Game)e.getSource()).getEntitySpawner();
        float size = -1;
        float count = 1;
        float vmult = 1;
        float mass = 1f;
        float damage = 0f;
        for (int i = 0; i < SIZE_CUTOFFS.length; ++i) {
          if (getSize() > SIZE_CUTOFFS[i]) {
            size = SIZE_CUTOFFS[i];
            count = BREAK_COUNT[i];
            vmult = VEL_MULT[i];
            mass = MASS_VALUES[i];
            damage = DAMAGE_AMOUNT[i];
            break;
          }
        }

        if (size > 0) {
          float angle = 2f * (float)Math.PI / (float) count;
          for (int i = 0; i < count; ++i) {
            float c = (float)Math.cos(i * angle);
            float s = (float)Math.sin(i * angle);

            float offX = c * getSize();
            float offY = s * getSize();
            Vector2f spawnPoint = new Vector2f(getPosition()).add(offX, offY);

            float rotX = (c * getVelocity().x - s * getVelocity().y) * vmult;
            float rotY = (s * getVelocity().x + c * getVelocity().y) * vmult;
            
            Asteroid a = new Asteroid(spawnPoint, new Vector2f(rotX, rotY));
            a.setSize(size);
            a.setMass(mass);
            a.setDamage(damage);
            spawner.spawnEnemy(a);
          }
        }

        SpriteAnimator sAnim = new SpriteAnimator(
            new AtlasSprite("booms", 11, 15), 
            new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9},
            0.12f
        );
        spawner.spawnParticle(sAnim,  getPosition(), 1.1f, getSize(), 0f);
      }
    });
  }

  public float getDamage() {
    return damage;
  }

  public void setDamage(float value) {
    this.damage = value;
  }

  @Override
  public boolean isDead() {
    return hp <= 0;
  }

  public void damage(float amount) {
    hp -= amount;
  }
}