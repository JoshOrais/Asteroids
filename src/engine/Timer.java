package engine;

import static org.lwjgl.glfw.GLFW.*;

public class Timer {
	
	private double lastTime;
	
	public void init() {
		lastTime = getTime();
	}
	
	public double getTime() {
		return glfwGetTime();
	}
	
	public float getElapsedTime() {
		double timenow = getTime();
		float elapsedTime = (float)(timenow - lastTime);
		lastTime = timenow;
		
		return elapsedTime;
	}
	
	public double getLastTime() {
		return this.lastTime;
	}
	
}
