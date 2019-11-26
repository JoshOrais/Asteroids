package engine;

import org.joml.Matrix4f;
import org.joml.Vector3f;

import engine.graphics.Shader;

public abstract class GameEntity {
	protected Vector3f position;
	protected float scale, rotation;
	protected Matrix4f modelMatrix;
	
	public GameEntity() {
		this.position = new Vector3f();
		this.scale = 1.0f;
		this.rotation = 0.0f;
		this.modelMatrix = new Matrix4f();
	}
	
	public abstract void update(float interval);
	public abstract void render(Shader s);
	
	public Vector3f getPosition() {
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
		this.position = new Vector3f(x,y,z);
	}
	
	public void addPosition(float offsetx, float offsety, float offsetz) {
		Vector3f addf = new Vector3f(offsetx, offsety, offsetz);
		
		this.position.add(addf);
	}
	
	public Matrix4f getModelMatrix() {
		modelMatrix.identity();
		modelMatrix.translate(position).rotateZ((float)Math.toRadians(rotation)).scale(scale);
		return modelMatrix;
	}
	
}
