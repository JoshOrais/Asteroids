package engine;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.joml.Vector2f;

import engine.graphics.Shader;

public abstract class GameEntity {
	private Vector2f position;
	private float scale, rotation;
	private Matrix4f modelMatrix;

	public GameEntity() {
		this.position = new Vector2f();
		this.scale = 1.0f;
		this.rotation = 0.0f;
		this.modelMatrix = new Matrix4f();
	}



	public abstract void update(float interval);

	public Vector2f getPosition() {
		return this.position;
	}

	public float getScale() {
		return this.scale;
	}

	public float getRotation() {
		return this.rotation;
	}

	public void setScale(float scale) {
		this.scale = scale;
	}

	public void setRotation(float rotation) {
		this.rotation = rotation;
	}

	public void setPosition(float x, float y, float z) {
		position.set(x, y);
	} 

  public void setPosition(Vector2f pos) {
    this.position = pos;
  }

	public void addPosition(float x, float y) {
		position.add(x, y);
	}

	public Matrix4f getModelMatrix() {
		modelMatrix.identity();
       
		modelMatrix.translate(new Vector3f(position, 0.f)).rotateZ((float)Math.toRadians(rotation)).scale(scale * 2);
		return modelMatrix;
	}

}
