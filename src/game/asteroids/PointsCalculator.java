package game.asteroids;

import org.joml.Vector2f;
import org.joml.Matrix2f;

public class PointsCalculator{
  public static final float PLAYER_DIST_FACTOR = 0.9f;
  public static final float MISSILE_DIST_FACTOR = 0.1f;

  public static float calcHunterPoints(Vector2f playerloc, Vector2f hunterloc, Vector2f closestMissile){
    float player_dist = hunterloc.distance(playerloc);
    float missile_dist = hunterloc.distance(closestMissile);

    return missile_dist * MISSILE_DIST_FACTOR - player_dist * PLAYER_DIST_FACTOR;
  }

  public static float calculateHunterMove(Vector2f playerloc, float player_rotation, Vector2f hunterloc, float hunter_rotation, Vector2f closestMissile, Vector2f missile_velocity, int depth){
    if (depth == 0) return calcHunterPoints(playerloc, hunterloc, closestMissile);

    Vector2f h_move1 = new Vector2f();
    hunterloc.add(new Vector2f(0.0f, 0.f).mul(new Matrix2f().identity().rotate(hunter_rotation)), h_move1);

    Vector2f h_move2 = new Vector2f();
    hunterloc.add(new Vector2f(0.0f, 0.f).mul(new Matrix2f().identity().rotate(hunter_rotation + 1)), h_move2);

    Vector2f h_move3 = new Vector2f();
    hunterloc.add(new Vector2f(0.0f, 0.f).mul(new Matrix2f().identity().rotate(hunter_rotation + 2)), h_move3);

    closestMissile.add(missile_velocity);

    float points1 = calculatePlayerMove(playerloc, player_rotation, h_move1, hunter_rotation, closestMissile, missile_velocity, depth);
    float biggest = points1;
    float points2 = calculatePlayerMove(playerloc, player_rotation, h_move2, hunter_rotation + 1, closestMissile, missile_velocity, depth);
    if (points2 > biggest) biggest = points2;
    float points3 = calculatePlayerMove(playerloc, player_rotation, h_move3, hunter_rotation + 2, closestMissile, missile_velocity, depth);
    if (points3 > biggest) biggest = points3;

    return biggest;
  }

  public static float calculatePlayerMove(Vector2f playerloc, float player_rotation, Vector2f hunterloc, float hunter_rotation, Vector2f closestMissile, Vector2f missile_velocity, int depth){
    Vector2f p_move1 = new Vector2f();
    playerloc.add(new Vector2f(0.0f, 0.f).mul(new Matrix2f().identity().rotate(hunter_rotation)), p_move1);

    Vector2f p_move2 = new Vector2f();
    playerloc.add(new Vector2f(0.0f, 0.f).mul(new Matrix2f().identity().rotate(hunter_rotation + 1)), p_move2);

    Vector2f p_move3 = new Vector2f();
    playerloc.add(new Vector2f(0.0f, 0.f).mul(new Matrix2f().identity().rotate(hunter_rotation + 1)), p_move3);


    float points1 = calculateHunterMove(p_move1, player_rotation, hunterloc, hunter_rotation, closestMissile, missile_velocity, depth - 1);
    float biggest = points1;
    float points2 = calculateHunterMove(p_move2, player_rotation + 1, hunterloc, hunter_rotation, closestMissile, missile_velocity, depth - 1);
    if (points2 > biggest) biggest = points2;
    float points3 = calculateHunterMove(p_move3, player_rotation + 2, hunterloc, hunter_rotation, closestMissile, missile_velocity, depth - 1);
    if (points3 > biggest) biggest = points3;

    return biggest;
  }

public static int calculateMove(Vector2f playerloc, float player_rotation, Vector2f hunterloc, float hunter_rotation, Vector2f closestMissile, Vector2f missile_velocity, int depth){
  Vector2f h_move1 = new Vector2f();
  hunterloc.add(new Vector2f(0.0f, 0.f).mul(new Matrix2f().identity().rotate(hunter_rotation)), h_move1);

  Vector2f h_move2 = new Vector2f();
  hunterloc.add(new Vector2f(0.0f, 0.f).mul(new Matrix2f().identity().rotate(hunter_rotation)), h_move2);

  Vector2f h_move3 = new Vector2f();
  hunterloc.add(new Vector2f(0.0f, 0.f).mul(new Matrix2f().identity().rotate(hunter_rotation)), h_move3);

  closestMissile.add(missile_velocity);

  float points1 = calculatePlayerMove(playerloc, player_rotation, h_move1, hunter_rotation, closestMissile, missile_velocity, depth);
  int biggest = 1;
  float points2 = calculatePlayerMove(playerloc, player_rotation, h_move2, hunter_rotation + 1, closestMissile, missile_velocity, depth);
  if (points2 > biggest) biggest = 2;
  float points3 = calculatePlayerMove(playerloc, player_rotation, h_move3, hunter_rotation + 2, closestMissile, missile_velocity, depth);
  if (points3 > biggest) biggest = 3;

  return biggest;
}
}
