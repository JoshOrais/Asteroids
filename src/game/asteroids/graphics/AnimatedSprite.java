package game.asteroids.graphics;

import engine.graphics.Texture;

public class AnimatedSprite  {
	private float duration, timeperframe, timeelapsed;
	private Texture[] textures;
	private int current, size;
	
	public AnimatedSprite(Texture[] textures, float duration) {
		this.textures = textures;
		this.duration = duration;
		this.size = textures.length;
		this.timeperframe = (float) size / duration;
		this.timeelapsed = 0;
		current = 0;
	}
	public AnimatedSprite(int frames, float duration) {
		this.textures = new Texture[frames];
		this.duration = duration;
		this.size = 0;
		this.timeperframe = duration;
		this.timeelapsed = 0;
		current = 0;
	}
	
	public void addFrame(Texture t) {
		if (size == textures.length) return;
		
		textures[size++] = t;
		timeperframe = (float)size/duration;
	}
	
	public void update(float interval) {
		if (interval > duration) {
			timeelapsed = 0.0f;
			current = 0;
			return;
		}
		
		timeelapsed += interval;
		
		if (timeelapsed > timeperframe)
			current = (current + 1) % size;
	}
	
	
	public void bindTexture() {
		if (textures[current] == null) {
			System.out.println("KILL ME");
			return;
		}
		
    	textures[current].bind();
	}
}
