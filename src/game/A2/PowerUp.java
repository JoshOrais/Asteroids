package game.A2;

public class PowerUp {
  public final int maxLevel;
  private Sprite cardSprite;
  private float rollWeight;
  private int level = 0;

  public PowerUp (String spriteKey, int maxLevel, float rollWeight) {
    this.maxLevel = maxLevel;
    this.rollWeight = rollWeight;
    this.cardSprite = new StaticSprite(spriteKey);
  }

  public float getRollWeight() {
    return level > maxLevel && maxLevel > 0 ? 0f : rollWeight;
  }

  public int getLevel() {
    return level;
  }

  public void levelUp() {
    if (level < maxLevel) {
      level++;
    }
  }

  public void reset() {
    level = 0;
  }

  public Sprite getCardSprite() {
    return cardSprite;
  }
}