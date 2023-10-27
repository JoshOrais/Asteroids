package game.A2;

import java.util.ArrayList;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class TimedAction {
  private float timeElapsed = 0f;
  private float timeLeft;

  private boolean repeatable;
  private boolean exhausted = false;

  private ArrayList<ActionListener> actionListeners = new ArrayList<>();

  public TimedAction(ActionListener al, float duration, boolean repeatable) {
    this(duration, repeatable);
    addActionListener(al);
  }
  public TimedAction(ActionListener al, float duration) {
    this(al, duration, false);
  }

  public TimedAction(float duration, boolean repeatable) {
     timeLeft = duration;
     this.repeatable = repeatable;
  }

  public TimedAction(float duration) {
    this(duration, false);
  }

  public void countdown(float dT) {
     timeElapsed += dT;
     if (!exhausted && timeElapsed >= timeLeft) {
       timeElapsed = timeElapsed % timeLeft;
       if (!repeatable) exhausted = true;
       
       for (ActionListener al: actionListeners) {
         al.actionPerformed(
           new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "Timed Action")
         );
       }
     }
  }

  public void addActionListener(ActionListener al) {
    actionListeners.add(al);
  }
}