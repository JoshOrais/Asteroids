package game.A2;

import java.util.ArrayList;

import org.joml.Vector2f;

import game.A2.Sprite;
import game.A2.entities.Particle;
import game.A2.entities.AnimatedParticle;
import game.A2.entities.Bullet;
import game.A2.entities.Asteroid;

public class EntitySpawner {
  private ArrayList<Particle> particleSpawns = new ArrayList<>();
  private ArrayList<Bullet> bulletSpawns = new ArrayList<>();
  private ArrayList<Asteroid> enemySpawns = new ArrayList<>();

  public void dumpParticles(ArrayList<Particle> dest) {
    dest.addAll(particleSpawns);
    particleSpawns.clear();
  }

  public void dumpBullets(ArrayList<Bullet> dest) {
    dest.addAll(bulletSpawns);
    bulletSpawns.clear();
  }

  public void dumpEnemies(ArrayList<Asteroid> dest) {
    dest.addAll(enemySpawns);
    enemySpawns.clear();
  }

  public void spawnBullet(
    Vector2f pos,
    Vector2f vel,
    float direction,
    float rangeMult,
    float sizeMult
  ) {
    bulletSpawns.add(
      new Bullet(pos, vel, direction, rangeMult, sizeMult)
    );
  }

  public void spawnParticle(
    Sprite sprite, 
    Vector2f at, 
    Vector2f vel, 
    float lifespan, 
    float size,
    float posJitter,
    float velJitter
  ) {
    float pjx = posJitter * (float)Math.random() - 0.5f * posJitter;
    float pjy = posJitter * (float)Math.random() - 0.5f * posJitter;
    float vjx = velJitter * (float)Math.random() - 0.5f * velJitter;
    float vjy = velJitter * (float)Math.random() - 0.5f * velJitter;
    
    particleSpawns.add(
      new Particle(
        sprite, 
        new Vector2f(at).add(pjx, pjy),
        new Vector2f(vel).add(vjx, vjy),
        lifespan,
        size
      )
    );
  }

  public void spawnParticle(
    SpriteAnimator sAnim, 
    Vector2f at, 
    Vector2f vel, 
    float lifespan, 
    float size,
    float posJitter,
    float velJitter
  ) {
    float pjx = posJitter * (float)Math.random() - 0.5f * posJitter;
    float pjy = posJitter * (float)Math.random() - 0.5f * posJitter;
    float vjx = velJitter * (float)Math.random() - 0.5f * velJitter;
    float vjy = velJitter * (float)Math.random() - 0.5f * velJitter;
    
    particleSpawns.add(
      new AnimatedParticle(
        sAnim, 
        new Vector2f(at).add(pjx, pjy),
        new Vector2f(vel).add(vjx, vjy),
        lifespan,
        size
      )
    );
  }
  
  public void spawnParticle(
    String textureKey, 
    Vector2f at, 
    float lifespan, 
    float size,
    float posJitter
  ) {
    spawnParticle(new StaticSprite(textureKey), at, new Vector2f(), lifespan, size, posJitter, 0f);
  }

  public void spawnParticle(
    SpriteAnimator sAnim, 
    Vector2f at, 
    float lifespan, 
    float size,
    float posJitter
  ) {
    spawnParticle(sAnim, at, new Vector2f(), lifespan, size, posJitter, 0f);
  }

  public void spawnEnemy(Asteroid e) {
    enemySpawns.add(e);
  }
}