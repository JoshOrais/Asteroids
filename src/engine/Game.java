package engine;

public abstract class Game {
	public abstract void init() throws Exception;
	public abstract void input(Window window);
	public abstract void update(float timestep);
	public abstract void render(Window window);
}
