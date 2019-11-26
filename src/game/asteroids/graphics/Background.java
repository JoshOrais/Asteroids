package game.asteroids.graphics;

import org.joml.Matrix4f;
import org.joml.Vector2f;

import engine.graphics.Texture;

public class Background {
	private int size;
	private Layer[] layers;
	private Matrix4f modelMatrix;
	public static final float PARRALAX_FACTOR = 1.3f;
	public static final float IDK_WTF_THIS_IS = 170.3f;
	private Vector2f transform;
	
	public Background(int numLayers) {
		size = 0;
		layers = new Layer[numLayers];
		modelMatrix = new Matrix4f();
		transform = new Vector2f();
	}
	
	public Background addLayer(Texture tex) {
		if (size == layers.length) return this;
		
		layers[size++] = new Layer(tex);
		return this;
	}
	
	public void move(Vector2f v, float timestep) {
		transform.x = (transform.x + v.x * timestep * (1.0f / IDK_WTF_THIS_IS)) ;
		transform.y = (transform.y + v.y * timestep * (1.0f / IDK_WTF_THIS_IS)) ;
	}
	
	public Matrix4f getModelMatrix() {
		this.modelMatrix.identity().scale(2.0f, 2.0f, 0.0f);
		return modelMatrix;
	}
	
	public int getSize() {
		return size;
	}
	
	public Layer getLayer(int index) {
		return layers[index];
	}
	
	public Vector2f getTransform() {
		return transform;
	}
	
	public class Layer {
		public Texture texture;
		
		public Layer(Texture tex) {
			texture = tex;
		}
		
		public void bindTexture() {
			texture.bind();
		}
	}
}
