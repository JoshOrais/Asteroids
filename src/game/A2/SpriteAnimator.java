package game.A2;

import java.util.ArrayList;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class SpriteAnimator extends TimedAction implements ActionListener {
  private int animIndex = 0;
  private int frameMax;
  private int[] frames = null;
  private AtlasSprite spriteTarget;

  public SpriteAnimator(AtlasSprite aSprite, int[] frames, float duration) {
    this(aSprite, duration);
    this.frames = frames;
    
    int w = frames[0] % aSprite.W;
    int h = (int)(frames[0] / aSprite.H);
    aSprite.setActiveTexture(w, h);
    frameMax = frames.length;
  }

  public SpriteAnimator(AtlasSprite aSprite, float duration) {
    super(duration, true);
    spriteTarget = aSprite;
    frameMax = aSprite.W * spriteTarget.H;

    addActionListener(this);
  }

  public AtlasSprite getSprite() {
    return spriteTarget;
  }

  public void actionPerformed(ActionEvent e) {
    animIndex = (animIndex + 1) % frameMax;
    int targetIndex = animIndex;
    if (frames != null) targetIndex = frames[animIndex];
   
    int w = targetIndex % spriteTarget.W;
    int h = (int)(targetIndex / spriteTarget.W);

    spriteTarget.setActiveTexture(w, h);
  }
}