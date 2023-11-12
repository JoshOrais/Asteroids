package game;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_LEFT;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_RIGHT;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_SPACE;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_UP;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_R;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.glClear;

import java.util.ArrayList;
import java.awt.event.ActionEvent;

import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;

import engine.*;
import game.A2.*;
import game.A2.entities.*;
import game.A2.hud.*;

public class A2Game extends Game {
  public static final int SCREEN_WIDTH = 1280, SCREEN_HEIGHT = 720;
  public static final float GAME_WIDTH = 381.f, GAME_HEIGHT = 216.f;
  public static final float ATTACK_CD = 0.372f;

  public static final float UI_VELOCITY_INDICATOR_MULT = 0.3f;

  private Vector2f camOffset = new Vector2f();
  private Vector2f mousePosition = new Vector2f();

  private ArrayList<Asteroid> asteroids = new ArrayList<>();
  private ArrayList<Particle> particles = new ArrayList<>();
  private ArrayList<Bullet> bullets = new ArrayList<>();
  private ArrayList<GameObject> deadThings = new ArrayList<>();
  private ArrayList<TimedAction> timedEvents = new ArrayList<>();

  private boolean firing = false;
  private boolean mouseDown = false;
  private boolean gameActive = false;
  private boolean interruptUpdate = false;

  private Rocket PC;
  private GameObject velocityIndicator;
  private EntitySpawner entityGen = new EntitySpawner();  
  private HealthBar hpbar;
  private NewGameButton ngButton;
  private ScoreDisplay scoreDisplay;
  private ScoreDisplay scoreDisplaySmall;
  private int score = 0;

  private float attackTimer = 0f;
  private float playerHP = 1f;
  private float paralyzeDuration = 0f;

  private Renderer renderer;
  private A2Game() {};

  @Override
  public void init() throws Exception {
    // load/register resources.
    try {
      loadShaders();
      registerTextures();      
    } catch (Exception e) {
      System.err.println("Error encountered while loading resources: ");
      System.err.println(e.getMessage());
      throw new Exception("Failed to initialize A2");
    }

    initRenderer();

    for (int i = 0; i < 5; ++i) {
      float x = (float)Math.random() * SCREEN_WIDTH;
      float y = (float)Math.random() * SCREEN_HEIGHT;
      float vx = (float)Math.random() * 150f - 75f;
      float vy = (float)Math.random() * 150f - 75f;
      asteroids.add(
        new Asteroid(new Vector2f(x, y), new Vector2f(vx, vy))
      );
    }
    PC = new Rocket();
    velocityIndicator = new GameObject(new Vector2f());
    velocityIndicator.setSize(10f);
    velocityIndicator.setSprite(
      new AtlasSprite("rocket", 6, 8)
        .setActiveTexture(5, 1));
    hpbar = new HealthBar(20f, 700f, 200f, 40f);
    ngButton = new NewGameButton(540f, 310f, 200f, 40f);
    scoreDisplay = new ScoreDisplay(620f, 500f, 40f, 100f);
    scoreDisplaySmall = new ScoreDisplay(630f, 700f, 20f, 50f);

    timedEvents.add(
      new TimedAction(e -> {
        float px = -20f * (float)Math.cos(PC.getRotation());
        float py = -20f * (float)Math.sin(PC.getRotation());
        entityGen.spawnParticle(
          new AtlasSprite("rocket", 6, 8).setActiveTexture(2, 4), 
          PC.getPosition().add(px, py, new Vector2f()),
          PC.getVelocity().mul(-0.5f, new Vector2f()),
          0.5f, 35f, 30f, 0f
        );
      }, 0.05f, true)
    );
  }

  public void initRenderer() {
    renderer = new Renderer(SCREEN_WIDTH, SCREEN_HEIGHT);
    renderer.setShader(ResourceLoader.getShader("entity"));
  }

  @Override
  public void update(float dt) {
    if (paralyzeDuration > 0f) {
      paralyzeDuration -= dt;
    }

    if (interruptUpdate) {
      return;
    }
    
    if (playerHP <= 0) {
      gameOver();
    }

    attackTimer += dt;
    if (firing && attackTimer > ATTACK_CD) {
      attackTimer = 0f;
      
      entityGen.spawnBullet(PC.getPosition(), PC.getVelocity(), PC.getRotation(), 1f, 1f);
    }

    PC.update(dt);
    placeVelocityIndicator();
    bound(PC.getPosition());
    for (TimedAction ta: timedEvents) {
      ta.countdown(dt);
    }

    for (Asteroid go: asteroids) {
      go.update(dt);
      for (PhysicsObject go2: asteroids) {
        if (go == go2) continue;
 
        collideWithPhysics(go, go2);
      }
      if (collideWithPhysics(go, PC)) {
        playerHP -= go.getDamage();
        hpbar.setHPPercentage(playerHP);
      }
      bound(go.getPosition());
      if (go.isDead()) {
        score += 10;
        scoreDisplaySmall.setScore(score);
        deadThings.add(go);
      }
    }

    for (Particle p: particles) {
      p.update(dt);
      if (p.isDead()) {
        deadThings.add(p);
      }
    }

    for (Bullet b: bullets) {
      b.update(dt);
      for (Asteroid go: asteroids) {
        if (collide(go, b)) {
          b.kill();
          go.damage(b.getAttackPower());
        }
      }
      if (b.isDead()) {
        deadThings.add(b);
      }
    }
   
    for (GameObject go: deadThings) {
      if (go.getOnDeath() != null) {
        go.getOnDeath().actionPerformed(
          new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "Trigger on death")
        );
      }
    }

    camOffset.sub(PC.getVelocity().mul(dt, new Vector2f()));
    renderer.setCameraPosition(PC.getPosition().add(camOffset, new Vector2f()));
    cameraInterpolate(camOffset, dt);
    renderer.updateParrallax(PC.getVelocity());

    entityGen.dumpParticles(particles);
    entityGen.dumpBullets(bullets);
    entityGen.dumpEnemies(asteroids);

    asteroids.removeAll(deadThings);
    particles.removeAll(deadThings);
    bullets.removeAll(deadThings);
    deadThings.clear();
    
    System.out.println(mouseDown);
    ngButton.mouseInput(mousePosition, false);
    if (!gameActive && ngButton.isMouseOver() && mouseDown) {
      gameStart();
    }
  }

  private void gameOver() {
    PC.getPosition().set(0f, 0f);
    PC.getVelocity().set(0f, 0f);
    paralyzeDuration = 1f;
    playerHP = 1f;
    scoreDisplay.setScore(score);
    score = 0;
    scoreDisplaySmall.setScore(0);
    hpbar.setHPPercentage(1f);
    gameActive = false;
  }

  private void gameStart() {
    gameActive = true;
    paralyzeDuration = 0f;
    System.out.println("GAME STARTED");
  }

  public void placeVelocityIndicator() {
    float angle = (float)Math.acos(PC.getVelocity().x / PC.getVelocity().length());
    if (PC.getVelocity().y < 0) {
      angle = -angle;
    }

    PC.getPosition().add(PC.getVelocity().mul(UI_VELOCITY_INDICATOR_MULT , new Vector2f()), velocityIndicator.getPosition());
    velocityIndicator.setRotation(angle);
  }

  public void cameraInterpolate(Vector2f v, float dT) {
    float lerp = 10f * v.lengthSquared() / 10000f;

    v.sub(v.mul(lerp * dT, new Vector2f()));
  } 

  
  @Override
  public void input(Window window) {
    PC.getControls().setSteering(
      window.isKeyPressed(GLFW_KEY_LEFT), window.isKeyPressed(GLFW_KEY_RIGHT)
    );
    PC.getControls().setAccelerating(window.isKeyPressed(GLFW_KEY_UP));
    firing = window.isKeyPressed(GLFW_KEY_SPACE);

    if (paralyzeDuration > 0f) {
      PC.getControls().setAccelerating(false);
      PC.getControls().setSteering(false, false);
      firing = false;
    }

    double[] mousePos = window.getCursorPosition();
    mousePosition.set(mousePos[0], SCREEN_HEIGHT - mousePos[1]);
    mouseDown = window.isMouseDown();
  }

  @Override
  public void render(Window window) {
    glClear(GL_COLOR_BUFFER_BIT);
    
    renderer.drawBackground();
    for (Bullet b: bullets) {
      renderer.render(b);
    }

    for (GameObject go: asteroids) {
      renderer.render(go);
    }

    for (Particle p: particles) {
      renderer.render(p);
    }

    renderer.render(velocityIndicator);
    renderer.render(PC);

    renderer.renderHUD(hpbar);
    if (gameActive) {
      renderer.renderHUD(scoreDisplaySmall);
    } else {
      renderer.renderHUD(scoreDisplay);
      renderer.renderHUD(ngButton);
    }
  }

  public Vector2f toroidalDest(Vector2f a, Vector2f b) {
    Vector2f dest = new Vector2f(b).sub(a);
    if (dest.x > SCREEN_WIDTH / 2f) {
      dest.set(dest.x - SCREEN_WIDTH, dest.y);
    } else if (dest.x < -SCREEN_WIDTH / 2f) {
      dest.set(dest.x + SCREEN_WIDTH, dest.y);
    }

    if (dest.y > SCREEN_HEIGHT / 2f) {
      dest.set(dest.x, dest.y - SCREEN_HEIGHT);
    } else if (dest.y < -SCREEN_HEIGHT / 2f) {
      dest.set(dest.x, dest.y + SCREEN_HEIGHT);
    }

    return dest;
  }

  public boolean collide(GameObject a, GameObject b) {
    //Vector2f dest = b.getPosition().sub(a.getPosition(), new Vector2f());
    Vector2f dest = toroidalDest(a.getPosition(), b.getPosition());
    float collisionLength = a.getSize() + b.getSize();
    
    if (dest.length() > collisionLength) {
      return false;
    }
    return true;
  }

  public boolean collideWithPhysics(PhysicsObject a, PhysicsObject b) {
    //Vector2f dest = b.getPosition().sub(a.getPosition(), new Vector2f());
    Vector2f dest = toroidalDest(a.getPosition(), b.getPosition());
    float collisionLength = a.getSize() + b.getSize();
    
    if (dest.length() > collisionLength) {
      return false;
    }
    Vector2f dx = dest.normalize(collisionLength + 0.5f, new Vector2f()).sub(dest);
    Vector2f dxA = dx.mul(-0.5f, new Vector2f());
    Vector2f dxB = dx.mul(0.5f, new Vector2f()); 

    a.getPosition().add(dxA);
    b.getPosition().add(dxB);

      // A_f1 = A_1 - ((2 * m_2) / m_total) * (x_1 - x_2((v_1 - v_2) dot (x_1 - x_2))/mag(x_1 - x_2)))
    Vector2f vDiff = a.getVelocity().sub(b.getVelocity(), new Vector2f());
    Vector2f xDiff = a.getPosition().sub(b.getPosition(), new Vector2f());

    float lSq = xDiff.lengthSquared();

    float factA = vDiff.dot(xDiff) / lSq;
    float fMassA = 2 * b.getMass() / (a.getMass() + b.getMass());
    Vector2f nvA = new Vector2f(a.getVelocity()).sub(new Vector2f(xDiff).mul(factA * fMassA));
 
    vDiff.mul(-1f);
    xDiff.mul(-1f);

    float factB = vDiff.dot(xDiff) / lSq;
    float fMassB = 2 * a.getMass() / (a.getMass() + b.getMass());
    Vector2f nvB = new Vector2f(b.getVelocity()).sub(xDiff.mul(factB * fMassB));

    a.getVelocity().set(nvA);
    b.getVelocity().set(nvB);
    
    return true;
  }

  public void bound(Vector2f v) {
    float nx = v.x;
    float ny = v.y;

    if (nx > SCREEN_WIDTH / 2.f) {
      float dx = nx - SCREEN_WIDTH / 2.f;
      nx = -SCREEN_WIDTH / 2.f + dx;
    }
    if (nx < -SCREEN_WIDTH / 2.f) {
      float dx = nx + SCREEN_WIDTH / 2.f;
      nx = SCREEN_WIDTH / 2.f + dx;
    }

    if (ny > SCREEN_HEIGHT / 2.f) {
      float dy = ny - SCREEN_HEIGHT / 2.f;
      ny = -SCREEN_HEIGHT / 2.f + dy;
    }
    if (ny < -SCREEN_HEIGHT / 2.f) {
      float dy = ny + SCREEN_HEIGHT / 2.f;
      ny = SCREEN_HEIGHT / 2.f + dy;
    }

    v.set(nx, ny);
  }
  
  public EntitySpawner getEntitySpawner() {
    return entityGen;
  }

  public void loadShaders() throws Exception{  
    ResourceLoader.addShader("background", "../res/shaders/foreground.vs", "../res/shaders/background.fs");
    ResourceLoader.addShader("entity", "../res/shaders/foreground.vs", "../res/shaders/foreground.fs");
    ResourceLoader.addShader("hud", "../res/shaders/foreground.vs", "../res/shaders/hud.fs");
  }

  private void registerTextures() throws Exception {
    ResourceLoader.addTexture("rocket",    "../res/textures/rocket.png");
    ResourceLoader.addTexture("rocketship",    "../res/textures/rocket01.png");
    ResourceLoader.addTexture("font",      "../res/textures/font.png");
    ResourceLoader.addTexture("arrow",      "../res/textures/player.png");
    ResourceLoader.addTexture("stars",     "../res/textures/art_assets/cloud.png");
    ResourceLoader.addTexture("planets",   "../res/textures/art_assets/stars.png");
    ResourceLoader.addTexture("nebula",    "../res/textures/Nebula Blue.png");
    ResourceLoader.addTexture("asteroid_field",    "../res/textures/asteroidField2.png");
    ResourceLoader.addTexture("stars01",    "../res/textures/Stars Small_1.png");
    ResourceLoader.addTexture("bullet",    "../res/textures/art_assets/bullet.png");
    ResourceLoader.addTexture("asteroid",  "../res/textures/asteroid05.png");
    ResourceLoader.addTexture("heal",      "../res/textures/art_assets/healup.png");
    ResourceLoader.addTexture("triple",    "../res/textures/art_assets/trishot.png");
    ResourceLoader.addTexture("shield",    "../res/textures/art_assets/shieldup.png");
    ResourceLoader.addTexture("invulsplat","../res/textures/art_assets/forcefield2.png");
    ResourceLoader.addTexture("hud",       "../res/textures/art_assets/HUD.png");
    ResourceLoader.addTexture("puff_1",    "../res/textures/puff_1.png");
    ResourceLoader.addTexture("puff_2",    "../res/textures/puff_2.png");
    ResourceLoader.addTexture("puff_3",    "../res/textures/puff_3.png");
    ResourceLoader.addTexture("puff_4",    "../res/textures/puff_4.png");
    ResourceLoader.addTexture("smoke_01",  "../res/textures/art_assets/smoke_01.png");
    ResourceLoader.addTexture("smoke_02",  "../res/textures/art_assets/smoke_02.png");
    ResourceLoader.addTexture("smoke_03",  "../res/textures/art_assets/smoke_03.png");
    ResourceLoader.addTexture("smoke_04",  "../res/textures/art_assets/smoke_04.png");
    ResourceLoader.addTexture("smoke_05",  "../res/textures/art_assets/smoke_05.png");
    ResourceLoader.addTexture("UFO",       "../res/textures/art_assets/UFO.png");
    ResourceLoader.addTexture("UFO2",      "../res/textures/art_assets/UFO2.png");
    ResourceLoader.addTexture("missile",   "../res/textures/art_assets/missile.png");
    ResourceLoader.addTexture("witch",   "../res/textures/art_assets/B_witch_run.png");
    ResourceLoader.addTexture("booms",   "../res/textures/booms.png");
    ResourceLoader.addTexture("ui-barframe",   "../res/textures/bar.png");
    ResourceLoader.addTexture("ui-barfill",   "../res/textures/hpbar.png");
    ResourceLoader.addTexture("ui-newgame",   "../res/textures/new-game.png");
    ResourceLoader.addTexture("ui-newgame-active",   "../res/textures/new-game-active.png");
    ResourceLoader.addTexture("ui-glyph-0",   "../res/textures/glyphs/0.png");
    ResourceLoader.addTexture("ui-glyph-1",   "../res/textures/glyphs/1.png");
    ResourceLoader.addTexture("ui-glyph-2",   "../res/textures/glyphs/2.png");
    ResourceLoader.addTexture("ui-glyph-3",   "../res/textures/glyphs/3.png");
    ResourceLoader.addTexture("ui-glyph-4",   "../res/textures/glyphs/4.png");
    ResourceLoader.addTexture("ui-glyph-5",   "../res/textures/glyphs/5.png");
    ResourceLoader.addTexture("ui-glyph-6",   "../res/textures/glyphs/6.png");
    ResourceLoader.addTexture("ui-glyph-7",   "../res/textures/glyphs/7.png");
    ResourceLoader.addTexture("ui-glyph-8",   "../res/textures/glyphs/8.png");
    ResourceLoader.addTexture("ui-glyph-9",   "../res/textures/glyphs/9.png");
  }

  public void dispose() {
    ResourceLoader.disposeShaders();
    ResourceLoader.disposeTextures();
  }

  // Singleton instance
  private static A2Game instance = null;
  public static A2Game getInstance() {
    if (instance == null) {
       instance = new A2Game();
    }

    return instance;
  }
}

