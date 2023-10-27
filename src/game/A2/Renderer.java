package game.A2;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.joml.Vector2f;

import engine.GameEntity;
import engine.ResourceLoader;
import engine.graphics.Camera;
import engine.graphics.Shader;

public class Renderer {
  private Matrix4f projMatrix;
  private Matrix4f modelMatrix;
  private Matrix4f viewMatrix;

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

  public void render(GameObject e) {
    shader.bind();

    //do model matrix
    modelMatrix.identity();
    modelMatrix.translate(new Vector3f(e.getPosition(), 0.f));
    modelMatrix.rotateZ(e.getRotation());
    modelMatrix.scale(e.getSize() * 2);

    shader.setUniformMatrix4f("view", viewMatrix);
    shader.setUniformMatrix4f("model", modelMatrix);
    shader.setUniform2fv("uvCoords", e.getSprite().getUVTranslate());
    shader.setUniform2fv("uvScale", e.getSprite().getUVScale());
  
    //if (e.getSprite() != null) {
    //   e.getSprite().bindTexture();
    //}

    if (e.getSprite() != null) {
      e.getSprite().getTexture().bind();
    }

    ResourceLoader.getQuadMesh().renderInstanced(shader, 9);
  }
}