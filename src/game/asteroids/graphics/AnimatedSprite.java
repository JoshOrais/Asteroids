package game.asteroids.graphics;

import engine.graphics.Texture;

public class AnimatedSprite  {
	public float duration, timeperframe, timeelapsed;
	private Texture[] textures;
	public int current, size;

	public AnimatedSprite(Texture[] textures, float duration) {
		this.textures = textures;
		this.duration = duration;
		this.size = textures.length;
		this.timeperframe = duration/(float)size;
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
		timeperframe = duration/(float)size;
	}

	public void update(float interval) {
		if (interval > duration) {
			timeelapsed = 0.0f;
			current = 0;
			return;
		}

		timeelapsed += interval;

		if (timeelapsed > timeperframe){
			current = (current + 1) % size;
			timeelapsed = 0;
		}
	}


	public void bindTexture() {
		if (textures[current] == null) {
			System.out.println("KILL ME");
			return;
		}

    	textures[current].bind();
	}
}
