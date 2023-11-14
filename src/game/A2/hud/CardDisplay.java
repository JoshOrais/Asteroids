package game.A2.hud;

import org.joml.Vector2f;
import game.A2.Sprite;
import game.A2.StaticSprite;
import game.A2.PowerUp;

public class CardDisplay extends HudItem {
  private static final float CARD_PAD = 20f;
  private Card[] cards;
  public CardDisplay(float x, float y, float w, float h) {
    super(x, y, w, h);
    
    cards = new Card[] {
      new Card(-CARD_PAD - w, 0, w, h),
      new Card(0, 0, w, h),
      new Card(CARD_PAD + w, 0, w, h)
    };

    for (Card card: cards) {
      addChild(card);
    }
  }

  public PowerUp selectPowerUp(Vector2f mouse) {
    System.out.println(mouse);
    for (Card card: cards) {
      Vector2f pos = new Vector2f(getPosition()).add(card.getPosition());
      mouse.sub(pos, pos);
      System.out.println(pos);
      if (
        pos.x > 0 && pos.y < 0 &&
        pos.x < card.getSize().x && pos.y > -card.getSize().y
      ) {
        return card.getPowerUp();
      }
    }
    return null;
  }

  public void setCards(PowerUp[] pUp) {
    for (int i = 0; i < cards.length; ++i) {
      cards[i].setPowerUp(pUp[i]);
    }
  }

  public Sprite getSprite() {
    return null;
  }

  private class Card extends HudItem {
    private PowerUp pUp = null;
    public Card(float x, float y, float w, float h) {
      super(x, y, w, h);
    }

    public void setPowerUp(PowerUp value) {
      pUp = value;
    }

    public PowerUp getPowerUp() {
      return pUp;
    }

    public Sprite getSprite() {
      if (pUp != null) return pUp.getCardSprite();
      return null;
    }
  }
}