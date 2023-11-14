package game.A2;

import game.A2.entities.Rocket;
import game.A2.entities.Bullet;
import org.joml.Vector2f;

public class PowersManager {
  private static final int MULTI_SHOT_MAX_LEVEL = 5;
  private static final int FIRE_RATE_MAX_LEVEL = 20;
  private static final int RANGE_UP_MAX_LEVEL = 5;
  private static final int SIZE_UP_MAX_LEVEL = 5;

  private static final float MULTI_SHOT_ROLL_WEIGHT = 0.2f;
  private static final float FIRE_RATE_ROLL_WEIGHT = 0.7f;
  private static final float RANGE_UP_ROLL_WEIGHT = 0.2f;
  private static final float SIZE_UP_ROLL_WEIGHT = 0.2f;
  private static final float DAMAGE_ROLL_WEIGHT = 1.0f;
  private static final float HEAL_ROLL_WEIGHT = 0.7f;
  private static final float SCORE_100_ROLL_WEIGHT = 0.01f;
  private static final float MULTI_SHOT_ANGLE = 0.175f;

  private PowerUp multiShot = new PowerUp("ui-card-multishot", MULTI_SHOT_MAX_LEVEL, MULTI_SHOT_ROLL_WEIGHT);
  private PowerUp damageUp = new PowerUp("ui-card-damage", 0, DAMAGE_ROLL_WEIGHT);
  private PowerUp fireRateUp = new PowerUp("ui-card-firerate", FIRE_RATE_MAX_LEVEL, FIRE_RATE_ROLL_WEIGHT );
  private PowerUp rangeUp = new PowerUp("ui-card-range", RANGE_UP_MAX_LEVEL , RANGE_UP_ROLL_WEIGHT);
  private PowerUp sizeUp = new PowerUp("ui-card-size", SIZE_UP_MAX_LEVEL , SIZE_UP_ROLL_WEIGHT);
  private PowerUp heal10 = new PowerUp("ui-card-p100", 0, HEAL_ROLL_WEIGHT);
  private PowerUp score100 = new PowerUp("ui-card-heal10", 0, SCORE_100_ROLL_WEIGHT);

  private PowerUp[] powerUpList = new PowerUp[] {
    multiShot, damageUp, fireRateUp, heal10, score100, sizeUp, rangeUp
  };

  public void reset() {
    for (PowerUp p: powerUpList) {
      p.reset();
    }
  }

  public float getDamageMult() {
    return 1f + damageUp.getLevel();
  }

  public float getFireCooldown() {
    return 0.372f - 0.017f * fireRateUp.getLevel();
  }

  public void fireBullets(EntitySpawner spawner, Rocket PC) {
    int numShots = 1 + 2 * multiShot.getLevel();
    float angle = -MULTI_SHOT_ANGLE * multiShot.getLevel();
  
    for (int i = 0; i < numShots; ++i) {
      spawner.spawnBullet(
        PC.getPosition(), 
        PC.getVelocity(), 
        PC.getRotation() + angle,
        1f + 0.27f * rangeUp.getLevel(),
        1f + 0.22f * sizeUp.getLevel()
      );
      angle += MULTI_SHOT_ANGLE;
    }
  }

  public PowerUp[] rollPowerUp() {
    float rollRange = 0f;
    PowerUp[] rollResult = new PowerUp[3];
    for (PowerUp p: powerUpList) {
      rollRange += p.getRollWeight();
    }

    float roll = (float)Math.random() * rollRange;
    
    for (PowerUp p: powerUpList) {
      if (roll < p.getRollWeight()) {
        rollResult[0] = p;
        rollRange -= p.getRollWeight();
        break;
      }
      roll -= p.getRollWeight();
    }

    roll = (float)Math.random() * rollRange;
    for (PowerUp p: powerUpList) {
      if (p != rollResult[0]) {
        if (roll < p.getRollWeight()) {
          rollResult[1] = p;
          rollRange -= p.getRollWeight();
          break;
        }
        roll -= p.getRollWeight();
      }
    }

    roll = (float)Math.random() * rollRange;
    for (PowerUp p: powerUpList) {
      if (p != rollResult[0] && p != rollResult[1]) {
        if (roll < p.getRollWeight()) {
          rollResult[2] = p;
          break;
        }
        roll -= p.getRollWeight();
      }
    }

    return rollResult;
  }
}