package game.asteroids.graphics;

import engine.graphics.Texture;

public class StaticSprite extends AnimatedSprite {

	public StaticSprite(Texture t) {
		super(1, 1.0f);
		this.addFrame(t);
	}

}
