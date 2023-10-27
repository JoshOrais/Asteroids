package game.asteroids.entities;

import game.asteroids.AsteroidsGameObject;

import org.joml.Vector2f;

public abstract class Particle extends AsteroidsGameObject{
  public Particle(Vector2f pos, Vector2f velocity, float radius) {
    super(pos, velocity, radius, null);
  }
}
