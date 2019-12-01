package game.asteroids;

public interface Collidable {
  public void collideWith(AsteroidsGameObject K);
  public void collisionAction();
}
