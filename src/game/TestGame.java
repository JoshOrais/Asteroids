package game;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_DOWN;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_LEFT;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_RIGHT;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_UP;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_TEST;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.glActiveTexture;

import org.joml.Matrix4f;

import engine.Game;
import engine.GameEntity;
import engine.ResourceLoader;
import engine.Window;
import engine.graphics.Camera;
import engine.graphics.FrameBuffer;
import engine.graphics.Texture;

public class TestGame extends Game{


    private int direction = 0, bx = 0, by = 0;

    private float color = 0.0f;

    private Texture tex;
    private GameEntity box;
    private Camera cam;
    private FrameBuffer fb;
//    private final Renderer renderer;

    public TestGame() {
//        renderer = new Renderer();
    }

    @Override
    public void init() throws Exception {
//        renderer.init();
    	ResourceLoader.addShader("test", "../res/shaders/test.vertex", "../res/shaders/test.frag");

    	tex = ResourceLoader.loadTexture("res/textures/try.png");
//    	box = new GameEntity();
    	box.setScale(300);
    	box.setPosition(600.0f, 0.0f, 0.0f);

    	cam = new Camera();
    	fb = new FrameBuffer(1200, 720);
    }

    @Override
    public void input(Window window) {
        if ( window.isKeyPressed(GLFW_KEY_UP) ) {
        	by = 1;
            direction = 1;
        } else if ( window.isKeyPressed(GLFW_KEY_DOWN) ) {
        	by = -1;
            direction = -1;
        } else if ( window.isKeyPressed(GLFW_KEY_LEFT) ) {
        	bx = -1;

        } else if ( window.isKeyPressed(GLFW_KEY_RIGHT) ) {
        	bx = 1;
        } else {
            direction = 0;
            by = 0;
            bx = 0;
        }
    }

    @Override
    public void update(float interval) {
        color += direction * 0.01f;
        if (color > 1) {
            color = 1.0f;
        }
        else if ( color < 0 ) {
            color = 0.0f;
        }

        box.addPosition(bx * 120.0f * interval, by * 120.0f * interval, 0.0f);
    }

    @Override
    public void render(Window window) {
    	glDisable(GL_DEPTH_TEST);

        fb.bind();
        window.setClearColor(1.0f, color, color, 1.0f);
        glClear(GL_COLOR_BUFFER_BIT);
    	ResourceLoader.getShader("test").bind();
    	Matrix4f mf = new Matrix4f().identity();

    	mf.ortho(0, 1280, 0, 720, 0, 1);
    	ResourceLoader.getShader("test").setUniformMatrix4f("projection", mf);


//    	Matrix4f view = new Matrix4f().identity();
//    	Vector3f boxpos = new Vector3f(-box.getPosition().x,-box.getPosition().y,-box.getPosition().z);
//    	view.translate(boxpos);
    	cam.lerpPosition(box.getPosition(), 0.2f);
    	ResourceLoader.getShader("test").setUniformMatrix4f("view", cam.getViewMatrix(1200.0f, 720.0f));

    	glActiveTexture(GL_TEXTURE0);
    	tex.bind();
    	ResourceLoader.getShader("test").setUniform1i("texture_sampler", 0);
//    	m.render(ResourceLoader.getShader("test"));


    	box.render(ResourceLoader.getShader("test"));

    	fb.unbind();
        window.setClearColor(color, 0.0f, color, 1.0f);
        glClear(GL_COLOR_BUFFER_BIT);

    	fb.bindTexture();
    	box.render(ResourceLoader.getShader("test"));

//        renderer.clear();
    }

    public void dispose() {};
}
