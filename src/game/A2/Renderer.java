package game.A2;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.joml.Vector2f;

import engine.GameEntity;
import engine.ResourceLoader;
import engine.graphics.Camera;
import engine.graphics.Shader;

import game.A2.hud.HudItem;

public class Renderer {
  private Matrix4f projMatrix;
  private Matrix4f modelMatrix;
  private Matrix4f viewMatrix;

  private static final float PARRALLAX_MULT0 = 0.000005f;
  private static final float PARRALLAX_MULT1 = 0.000001f;
  private static final float PARRALLAX_MULT2 = 0.000010f;

  private final float HALF_WIDTH;
  private final float HALF_HEIGHT;
  private final float WIDTH;
  private final float HEIGHT;

  private Shader shader;

  public Renderer(float screenWidth, float screenHeight) {
    viewMatrix = new Matrix4f().identity();
    viewMatrix.translate(screenWidth/2f, screenHeight/2f, 0.f);
    projMatrix = new Matrix4f().identity();
    projMatrix.ortho(0, screenWidth, 0, screenHeight, -1.f, 1.f);
    modelMatrix = new Matrix4f();
    
    HALF_WIDTH = screenWidth / 2f;
    HALF_HEIGHT = screenHeight / 2f;
    WIDTH = screenWidth;
    HEIGHT = screenHeight;
  }

  public void setShader(Shader shader) {
    this.shader = shader;
    shader.bind();
    shader.setUniform2fv("offsets[0]", new float[]{0f, 0f});
    shader.setUniform2fv("offsets[1]", new float[]{0f, (float)HEIGHT});
    shader.setUniform2fv("offsets[2]", new float[]{0f, (float)-HEIGHT});
    shader.setUniform2fv("offsets[3]", new float[]{(float)WIDTH, 0f});
    shader.setUniform2fv("offsets[4]", new float[]{(float)WIDTH, (float)HEIGHT});
    shader.setUniform2fv("offsets[5]", new float[]{(float)WIDTH, (float)-HEIGHT});
    shader.setUniform2fv("offsets[6]", new float[]{(float)-WIDTH, 0f});
    shader.setUniform2fv("offsets[7]", new float[]{(float)-WIDTH, (float)HEIGHT});
    shader.setUniform2fv("offsets[8]", new float[]{(float)-WIDTH, (float)-HEIGHT});

    shader.setUniformMatrix4f("projection", projMatrix);
    shader.unbind();
  }

  public void setCameraPosition(Vector2f pos) {
    viewMatrix.identity().translate(
       -pos.x + HALF_WIDTH,
       -pos.y + HALF_HEIGHT,
       0f
    );
  }

  public void updateParrallax(Vector2f by) {
    bgLayer0Translate.add(new Vector2f(by).mul(PARRALLAX_MULT0));
    bgLayer1Translate.add(new Vector2f(by).mul(PARRALLAX_MULT1));
    bgLayer2Translate.add(new Vector2f(by).mul(PARRALLAX_MULT2));
  }

  public void render(GameObject e) {
    shader.bind();

    //do model matrix
    modelMatrix.identity();
    modelMatrix.translate(new Vector3f(e.getPosition(), 0.f));
    modelMatrix.rotateZ(e.getRotation());
    modelMatrix.scale(e.getSize() * 2);

    shader.setUniformMatrix4f("view", viewMatrix);
    shader.setUniformMatrix4f("model", modelMatrix);

    if (e.getSprite() != null) {
      drawSprite(e.getSprite(), 9);
    }
  }

  public void renderHUD(HudItem b) {
    renderHud(b, modelMatrix.identity());
  }

  public void renderHud(HudItem b, Matrix4f posMatrix) {
    shader.bind();
    Matrix4f mm = new Matrix4f(posMatrix);

    mm.translate(new Vector3f(b.getPosition(), 0f));
    for (HudItem c: b.getChildren()) {
      renderHud(c, mm);
    }
    mm.translate(new Vector3f(
      b.getSize().x * 0.5f, b.getSize().y * -0.5f, 0f
    ));
    mm.scale(new Vector3f(b.getSize(), 1f));

    shader.setUniformMatrix4f("view", new Matrix4f());
    shader.setUniformMatrix4f("model", mm);

    drawSprite(b.getSprite(), 1);
  }

  public void drawSprite(Sprite sp, int instances) {
    if (sp == null) return;

    shader.setUniform2fv("uvCoords", sp.getUVTranslate());
    shader.setUniform2fv("uvScale", sp.getUVScale());
    
    sp.getTexture().bind();
    ResourceLoader.getQuadMesh().renderInstanced(shader, instances);
  }

  private Sprite bgLayer0 = new StaticSprite("nebula");
  private Vector2f bgLayer0Scale = new Vector2f();
  private Vector2f bgLayer0Translate = new Vector2f();
  private Sprite bgLayer1 = new StaticSprite("stars01");
  private Vector2f bgLayer1Scale = new Vector2f();
  private Vector2f bgLayer1Translate = new Vector2f();
  private Sprite bgLayer2 = new StaticSprite("asteroid_field");
  private Vector2f bgLayer2Scale = new Vector2f();
  private Vector2f bgLayer2Translate = new Vector2f();

  public void drawBackground() {
    shader.bind();
    modelMatrix.identity();
    modelMatrix.scale(HALF_WIDTH * 4f, HALF_HEIGHT * 4f, 1.0f);

    bgLayer0Scale.set(HALF_WIDTH / 300f, HALF_HEIGHT / 300f);

    shader.setUniformMatrix4f("view", new Matrix4f());
    shader.setUniformMatrix4f("model", modelMatrix);
    shader.setUniform2fv("uvCoords", bgLayer0Translate);;
    shader.setUniform2fv("uvScale", bgLayer0Scale);
    bgLayer0.getTexture().bind();
    
    ResourceLoader.getQuadMesh().renderInstanced(shader, 1);

    bgLayer1Scale.set(HALF_WIDTH / 200f, HALF_HEIGHT / 200f);
    shader.setUniform2fv("uvCoords", bgLayer1Translate);
    shader.setUniform2fv("uvScale", bgLayer1Scale);
    bgLayer1.getTexture().bind();
    
    ResourceLoader.getQuadMesh().renderInstanced(shader, 1);

    bgLayer2Scale.set(HALF_WIDTH / 400f, HALF_HEIGHT / 400f);
    shader.setUniform2fv("uvCoords", bgLayer2Translate);
    shader.setUniform2fv("uvScale", bgLayer2Scale);
    bgLayer2.getTexture().bind();
    
    ResourceLoader.getQuadMesh().renderInstanced(shader, 1);
  }
}