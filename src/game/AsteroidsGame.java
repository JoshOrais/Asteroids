package game;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_LEFT;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_RIGHT;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_SPACE;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_UP;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.glClear;

import java.util.ArrayList;

import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;

import engine.Game;
import engine.GameEntity;
import engine.ResourceLoader;
import engine.Window;
import engine.graphics.Camera;
import engine.graphics.GameFont;
import engine.graphics.TextObject;
import game.asteroids.PlayerShip;
import game.asteroids.entities.*;
import game.asteroids.AsteroidsGameObject;
import game.asteroids.QuadTree;
import game.asteroids.graphics.Background;
import game.asteroids.graphics.Renderer;

public class AsteroidsGame extends Game {
	// so much shit
	// delet this
	private ArrayList<AsteroidsGameObject> activeEntities, deadEntities, queuedEntities;
	private Camera cam;
	private PlayerShip player;
	private boolean accelerating, firing;
	private int direction;
	private Renderer renderer;
	private Matrix4f projectionMatrix;
	private Background bg;
	private GameFont font;
	private TextObject to;

	private AsteroidsGame() {};

	private static AsteroidsGame GAME = null;
	public static final int   SCREEN_WIDTH = 1280,   SCREEN_HEIGHT = 720;
	public static final float GAME_WIDTH   = 381.0f, GAME_HEIGHT   = 216.0f;
	//this maybe should not be static final
	public static final float GAME_BOUNDS_MIN_X = -3000.0f, GAME_BOUNDS_MAX_X =  3000.0f,
							  						GAME_BOUNDS_MIN_Y = -3000.0f, GAME_BOUNDS_MAX_Y =  3000.0f;

	public static final float GAME_BOUNDS_HEIGHT = GAME_BOUNDS_MAX_Y - GAME_BOUNDS_MIN_Y,
			 											GAME_BOUNDS_WIDTH  = GAME_BOUNDS_MAX_X - GAME_BOUNDS_MIN_X;

	private boolean caps = true;
	private int num = 0;

	@Override
	public void init() throws Exception {
		loadShaders();
		loadTextures();

		renderer = new Renderer();
		renderer.setEntityShader(ResourceLoader.getShader("entity"));
		renderer.setBackgroundShader(ResourceLoader.getShader("background"));

		activeEntities = new ArrayList<AsteroidsGameObject>();
		deadEntities = new ArrayList<AsteroidsGameObject>();
		queuedEntities = new ArrayList<AsteroidsGameObject>();

		cam = new Camera();
		cam.setBounds(GAME_BOUNDS_MIN_X, GAME_BOUNDS_MIN_Y, GAME_BOUNDS_MAX_X, GAME_BOUNDS_MAX_Y);

		player = new PlayerShip();
		player.setBounded(true);
		player.setBounds(GAME_BOUNDS_MIN_X, GAME_BOUNDS_MIN_Y, GAME_BOUNDS_MAX_X, GAME_BOUNDS_MAX_Y);
		activeEntities.add(player);

		projectionMatrix = new Matrix4f();
		projectionMatrix.identity().ortho(0.0f, GAME_WIDTH, 0.0f, GAME_HEIGHT, -1.0f, 1.0f);
		renderer.setProjectionMatrix(projectionMatrix);

		bg = new Background(3);
		bg.addLayer(ResourceLoader.getTexture("nebula"));
		bg.addLayer(ResourceLoader.getTexture("planets"));
		bg.addLayer(ResourceLoader.getTexture("stars"));

		font = ResourceLoader.buildFont("../res/shaders/font.font");
		font.setTexture(ResourceLoader.getTexture("font"));
		to = new TextObject("DEFAULTstring", font);

		float boundx = player.getPosition().x + GAME_BOUNDS_WIDTH / 4.f;
		float boundy = player.getPosition().x + GAME_BOUNDS_HEIGHT / 4.f;
		for (int  i = 0; i < 255; ++i){
			addEntity(
				AsteroidFactory.createLargeAsteroidWithinBounds(boundx, boundy, boundx + GAME_BOUNDS_WIDTH / 2.f, boundy + GAME_BOUNDS_HEIGHT / 2.f)
				);
		}
	}

	@Override
	public void input(Window window) {
        accelerating = window.isKeyPressed(GLFW_KEY_UP);
        if ( window.isKeyPressed(GLFW_KEY_LEFT) ) {
        	direction = 1;
        }
        else if ( window.isKeyPressed(GLFW_KEY_RIGHT) ) {
        	direction = -1;
        }
        else {
        	direction = 0;
        }

				if ( window.isKeyPressed(GLFW_KEY_SPACE)){
					firing = true;
				}
				else {
					firing = false;
				}
	}

	@Override
	public void update(float timestep) {

		bg.move(player.getDeltaPosition(), timestep);
		moveCamera();

		QuadTree qt = new QuadTree(-3000, -3000, 3000, 3000);

		activeEntities.addAll(queuedEntities);
		queuedEntities.clear();

		for (AsteroidsGameObject E : activeEntities){
			E.setCollided(false);

			if (E.isDead()) {
				deadEntities.add(E);
				continue;
			}
			E.update(timestep);
			qt.insert(E);
		}

		if (firing){
			player.fire();
		}
		player.setDirection(direction);
		if (accelerating) {
			player.accelerate(timestep);
		}


		ArrayList<AsteroidsGameObject> d = qt.queryCircle(new Vector2f(player.getPosition().x, player.getPosition().y), 200.0f);
		for (AsteroidsGameObject a : d){
			for (AsteroidsGameObject b : d){
				if (a != b)
					a.collideWith(b);
			}
		}

		activeEntities.removeAll(deadEntities);
		deadEntities.clear();

	}

	@Override
	public void render(Window window) {
		glClear(GL_COLOR_BUFFER_BIT);
		renderer.renderBackground(bg);

		for (GameEntity entity : activeEntities) {
			renderer.renderEntity(entity, cam);
		}

		renderer.getEntityShader().bind();
		if (caps)
			to.setText("Fuck you Cris");
		else
			to.setText("Fuck you Josh");
		if (++num > 60) {
			num = 0;
			caps = !caps;
		}

		font.getTexture().bind();
		Matrix4f model = new Matrix4f().identity().translate(15.0f, 15.0f, 0.0f).scale(1.0f);
		renderer.getEntityShader().setUniformMatrix4f("model", model);
		to.getMesh().render(renderer.getEntityShader());
	}


	public void moveCamera() {
		Vector2f m = new Vector2f(player.getPosition().x, player.getPosition().y);
		m.sub(player.getDeltaPosition());
		cam.setPosition(m.x, m.y);
	}

	public PlayerShip getPlayer(){
		return player;
	}

	public void addEntity(AsteroidsGameObject E){
		queuedEntities.add(E);
	}

	public void loadShaders() throws Exception{
		ResourceLoader.addShader("background", "../res/shaders/foreground.vs", "../res/shaders/background.fs");
		ResourceLoader.addShader("entity", "../res/shaders/foreground.vs", "../res/shaders/foreground.fs");
	}

	public void loadTextures() throws Exception{
		ResourceLoader.addTexture("rocket","../res/textures/player.png");
		// ResourceLoader.addTexture("asteroid","../res/textures/asteroid.png");
		ResourceLoader.addTexture("font","../res/textures/font.png");
		ResourceLoader.addTexture("stars","../res/textures/stars.png");
		ResourceLoader.addTexture("planets","../res/textures/planets.png");
		ResourceLoader.addTexture("nebula","../res/textures/nebula.png");
		ResourceLoader.addTexture("bullet","../res/textures/try.png");
		ResourceLoader.addTexture("default","../res/textures/sprite.png");
	}

	public void dispose(){
		ResourceLoader.disposeShaders();
		ResourceLoader.disposeTextures();
	}

	public static AsteroidsGame getGame() {
		if (GAME == null)
			GAME = new AsteroidsGame();
		return GAME;
	}
}
