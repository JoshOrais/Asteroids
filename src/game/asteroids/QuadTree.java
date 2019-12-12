package game.asteroids;

import org.joml.Vector2f;
import java.util.ArrayList;

public class QuadTree{
  private Quad root;
  public static final int MAX_SIZE_PER_QUARTER = 5;

  public QuadTree(int xmin, int ymin, int xmax, int ymax){
    root = new Quad(xmin, ymin, xmax, ymax);
  }

  public void insert(AsteroidsGameObject e){
    root.insert(e);
  }

  public ArrayList<AsteroidsGameObject> queryCircle(Vector2f circleCenter, float radius){
    HitBox m = new HitBox(circleCenter, radius);
    ArrayList<AsteroidsGameObject> ar = new ArrayList<AsteroidsGameObject>();
    root.queryCircle(m, ar);
    // System.out.println("ArrayList size " + ar.size());
    return ar;
  }

  private class Quad{
    private float x1, x2, y1, y2;
    private Quad TL = null, TR = null, BL = null, BR = null;
    private AsteroidsGameObject[] objects;
    private int cursor = 0;

    public Quad(float xmin, float ymin, float xmax, float ymax){
      objects = new  AsteroidsGameObject[MAX_SIZE_PER_QUARTER];
      x1 = xmin;
      x2 = xmax;
      y1 = ymin;
      y2 = ymax;
    }

    public boolean isDivided(){
      return TL == null;
    }

    public boolean insert(AsteroidsGameObject e){
      //check if e is inside this quad;

      if (e.getHitBox().getCenterX() <= x1 || e.getHitBox().getCenterX() > x2 ||
          e.getHitBox().getCenterY() <= y1 || e.getHitBox().getCenterY() > y2 ){
            return false;
      }

      if (cursor < objects.length && TL == null){
        objects[cursor++] = e;
        return true;
      }

      if (TL == null) divide();

      if (TL.insert(e)) return true;
      if (TR.insert(e)) return true;
      if (BL.insert(e)) return true;
      if (BR.insert(e)) return true;

      return false;
    }

    public void divide(){
      TL = new Quad(x1                  , y1                  , x1 + (x2 - x1) /2.0f, y1 + (y2 - y1) /2.0f);
      TR = new Quad(x1 + (x2 - x1) /2.0f, y1                  , x2                  , y1 + (y2 - y1) /2.0f);
      BL = new Quad(x1                  , y1 + (y2 - y1) /2.0f, x1 + (x2 - x1) /2.0f, y2                  );
      BR = new Quad(x1 + (x2 - x1) /2.0f, y1 + (y2 - y1) /2.0f, x2                  , y2                  );
    }

    public ArrayList queryCircle(HitBox box, ArrayList<AsteroidsGameObject> result){
      if (!this.insersectsCircle(box.position, box.radius))
        return result;

      for (int i = 0; i < cursor; ++i){
        if (objects[i].getHitBox().intersects(box)) {
            result.add(objects[i]);
        }
      }

      if (TL != null){
        TL.queryCircle(box, result);
        TR.queryCircle(box, result);
        BL.queryCircle(box, result);
        BR.queryCircle(box, result);
      }

      return result;
    }

    public boolean insersectsCircle(Vector2f center, float radius){
      return MathHelper.rectIntersectsCircle(x1, y1, x2, y2, center.x, center.y, radius);
    }

    public Quad getBottomLeft() {return BL;}
    public Quad getBottomRight() {return BR;}
    public Quad getTopLeft() {return TL;}
    public Quad getTopRight() {return TR;}
  }
}
