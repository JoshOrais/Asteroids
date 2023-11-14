package game.A2;

import game.A2.entities.Asteroid;
import org.joml.Vector2f;

public class DifficultyManager {
  private static final float DAMAGE_GROWTH_PER_DIFFICULTY_STEP = 0.978f;
  private static final float VELOCITY_RANDOM_COMPONENT = 20f;
  private static final int Q2_SINGLE_STEP = 3;
  private static final int Q4_SINGLE_STEP = 6;
  private static final int Q1_DOUBLE_STEP = 9;
  private static final int Q3_DOUBLE_STEP = 15;
  private static final int Q2_DOUBLE_STEP = 24;
  private static final int Q4_DOUBLE_STEP = 39;
  
  private EntitySpawner spawner;
  private float damageMult = 1f;
  private int difficultyStep = 0;

  public DifficultyManager(EntitySpawner spawner) {
    this.spawner = spawner;
  }

  public void advanceDifficulty() {
    difficultyStep++;
    damageMult *= DAMAGE_GROWTH_PER_DIFFICULTY_STEP;
  }

  public void reset() {
    difficultyStep = 0;
    damageMult = 1f;
  }

  public float getDamageMult() {
    return damageMult;
  }

  //idk why im doing it like this
  public void spawnEnemies(float px, float py, float screenW, float screenH) {
    float halfWidth = screenW / 2;
    float halfHeight = screenH / 2;
    spawnQuadrant1(px            , py             , px + halfWidth, py + halfHeight);
    spawnQuadrant2(px + halfWidth, py             , px + screenW  , py + halfHeight);
    spawnQuadrant3(px + halfWidth, py + halfHeight, px + screenW  , py + screenH);
    spawnQuadrant4(px            , py + halfHeight, px + halfWidth, py + screenH);
  }

  private void spawnQuadrant1(float x0, float y0, float x1, float y1) {
     float dx = x1 - x0;
     float dy = y1 - y0;

     //on candy stripe legs the spiderman comes
     //softly through the shadown of the evening sun
     //stealing past the windows of the blissfully dead
     //looking for the victim shivering in bed
     spawnAsteroid(
       x0 + 0.2f * dx,
       y0 + 0.2f * dy,
       x0 + 0.5f * dx,
       y0 + 0.3f * dy
     );

     if (difficultyStep > Q1_DOUBLE_STEP) {
       spawnAsteroid(
         x0 + 0.6f * dx,
         y0 + 0.6f * dy,
         x0 + 0.8f * dx,
         y0 + 0.8f * dy
       );
     }
  }

  private void spawnQuadrant2(float x0, float y0, float x1, float y1) {
     float dx = x1 - x0;
     float dy = y1 - y0;
     if (difficultyStep > Q2_SINGLE_STEP) {
       spawnAsteroid(
         x0 + 0.15f * dx,
         y0 + 0.2f * dy,
         x0 + 0.25f * dx,
         y0 + 0.3f * dy
       );
     }

     if (difficultyStep > Q2_DOUBLE_STEP) {
       spawnAsteroid(
         x0 + 0.5f * dx,
         y0 + 0.6f * dy,
         x0 + 0.8f * dx,
         y0 + 0.66f * dy
       );
     }
  }

  private void spawnQuadrant3(float x0, float y0, float x1, float y1) {
     float dx = x1 - x0;
     float dy = y1 - y0;
     spawnAsteroid(
       x0 + 0.2f * dx,
       y0 + 0.2f * dy,
       x0 + 0.4f * dx,
       y0 + 0.4f * dy
     );

     if (difficultyStep > Q3_DOUBLE_STEP) {
       spawnAsteroid(
         x0 + 0.5f * dx,
         y0 + 0.6f * dy,
         x0 + 0.8f * dx,
         y0 + 0.7f * dy
       );
     }
  }

  private void spawnQuadrant4(float x0, float y0, float x1, float y1) {
     float dx = x1 - x0;
     float dy = y1 - y0;
     if (difficultyStep > Q4_SINGLE_STEP) {
       spawnAsteroid(
         x0 + 0.2f * dx,
         y0 + 0.15f * dy,
         x0 + 0.25f * dx,
         y0 + 0.3f * dy
       );
     }

     if (difficultyStep > Q4_DOUBLE_STEP) {
       spawnAsteroid(
         x0 + 0.4f * dx,
         y0 + 0.6f * dy,
         x0 + 0.6f * dx,
         y0 + 0.7f * dy
       );
     }
  }

  private void spawnAsteroid(float x0, float y0, float x1, float y1) {
    float xRoll = (float)Math.random() * (x1 - x0);
    float yRoll = (float)Math.random() * (y1 - y0);
    float vxRoll = (float)(Math.random() * 2f - 1f) * VELOCITY_RANDOM_COMPONENT;
    float vyRoll = (float)(Math.random() * 2f - 1f) * VELOCITY_RANDOM_COMPONENT;
    
    spawner.spawnEnemy(new Asteroid(
      new Vector2f(x0 + xRoll, y0 + yRoll), 
      new Vector2f(vxRoll, vyRoll)
    ));
  }
 
}