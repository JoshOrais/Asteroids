package engine.graphics;

import org.joml.Matrix4f;
import org.joml.Vector3f;

public class Camera {
	
	private Vector3f pos;
	private Vector3f rotation;
	private Matrix4f viewMatrix;
	private float xmin, xmax, ymin, ymax;
	
	public Camera() {
		pos = new Vector3f();
		rotation = new Vector3f();
		viewMatrix = new Matrix4f();
	}

	public Vector3f getPosition() {
		return pos;
	}

	public void setPosition(float x, float y, float z) {
		this.pos.x = x;
		this.pos.y = y;
		this.pos.z = z;
	}
	
	public void setPosition(float x, float y) {
		this.pos.x = x;
		this.pos.y = y;
	}
	
	public void addPosition(float x, float y, float z) {
		this.pos.x += x;
		this.pos.y += y;
		this.pos.z += z;
	}

	public Vector3f getRotation() {
		return rotation;
	}

	public void setRotation(float x, float y, float z) {
		this.rotation.x = x;
		this.rotation.y = y;
		this.rotation.z = z;
	}
	
	public void setBounds(float xmin, float ymin, float xmax, float ymax) {
		this.xmin = xmin;
		this.ymin = ymin;
		this.xmax = xmax;
		this.ymax = ymax;
	}
	
	
	public void lerpPosition(Vector3f v, float degree) {
		this.pos.lerp(v, degree);
	}
	

    public void movePosition(float offsetX, float offsetY, float offsetZ) {
        if ( offsetZ != 0 ) {
            pos.x += (float)Math.sin(Math.toRadians(rotation.y)) * -1.0f * offsetZ;
            pos.z += (float)Math.cos(Math.toRadians(rotation.y)) * offsetZ;
        }
        if ( offsetX != 0) {
            pos.x += (float)Math.sin(Math.toRadians(rotation.y - 90)) * -1.0f * offsetX;
            pos.z += (float)Math.cos(Math.toRadians(rotation.y - 90)) * offsetX;
        }
        pos.y += offsetY;
    }
    
    public void bound() {
    	if (pos.x > xmax) 
    		pos.x = xmin + (pos.x - xmax);
    	if (pos.x < xmin)
    		pos.x = xmax + (xmin - pos.x);
    	if (pos.y > ymax)
    		pos.y = ymin + (pos.y - ymax);
    	if (pos.y < ymin)
    		pos.y = ymax + (ymin - pos.y);
    }
   
    public void moveRotation(float offsetX, float offsetY, float offsetZ) {
        rotation.x += offsetX;
        rotation.y += offsetY;
        rotation.z += offsetZ;
    }
    
    
    public Matrix4f getViewMatrix(float screenwidth, float screenheight) {
    	viewMatrix.identity();
    	
    	viewMatrix.translate(-pos.x + (screenwidth / 2.0f), -pos.y + (screenheight / 2.0f) , -pos.z );
    	
    	return viewMatrix;
    }
	
}
