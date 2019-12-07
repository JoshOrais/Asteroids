package game.asteroids.graphics;

import org.joml.Matrix4f;

import engine.GameEntity;
import engine.ResourceLoader;
import engine.graphics.Camera;
import engine.graphics.Shader;
import game.asteroids.AsteroidsGameObject;
import game.AsteroidsGame;

public class Renderer {
	// should handle setting uniforms and all that shit
	// this includes the modelMatrices
	public Matrix4f projectionMatrix;
	public Shader entityShader, backgroundShader, hudShader;
	// projectionmatrix should also be stored here
	// do this wednesday 11/26/2019 no later

	public Shader getEntityShader() {
		return entityShader;
	}

	public void setEntityShader(Shader entityShader) {
		this.entityShader = entityShader;
	}

	public Shader getBackgroundShader() {
		return backgroundShader;
	}

	public void setBackgroundShader(Shader backgroundShader) {
		this.backgroundShader = backgroundShader;

		backgroundShader.bind();
		backgroundShader.setUniformMatrix4f("projection", new Matrix4f().identity());
		backgroundShader.setUniformMatrix4f("view", new Matrix4f().identity());
		backgroundShader.unbind();
	}

	public Shader getHudShader() {
		return hudShader;
	}

	public void setHudShader(Shader hudShader) {
		this.hudShader = hudShader;
	}

	public void setProjectionMatrix(Matrix4f projectionMatrix) {
		this.projectionMatrix = projectionMatrix;
	}

	public void renderEntity(GameEntity E, Camera cam) {
		entityShader.bind();
		entityShader.setUniformMatrix4f("view", cam.getViewMatrix(AsteroidsGame.GAME_WIDTH, AsteroidsGame.GAME_HEIGHT));
		entityShader.setUniformMatrix4f("projection", projectionMatrix);
		entityShader.setUniformMatrix4f("model", E.getModelMatrix());

		if (E instanceof AsteroidsGameObject) {
			if ( ((AsteroidsGameObject)E).getSprite() != null)
				((AsteroidsGameObject)E).getSprite().bindTexture();
		}

		ResourceLoader.getQuadMesh().render(entityShader);
	}

	public void renderHud(Shader s) {
		// TODO should have a parameter hud
		// hud class has hud objects inside
		// hud should be done soon
		// stop procrastinating
		// you cunt
	}

	public void renderBackground(Background bg) {
		backgroundShader.bind();

		backgroundShader.setUniformMatrix4f("model", bg.getModelMatrix());
		backgroundShader.setUniform1f("scale", 0.8f);
		backgroundShader.setUniform1i("texture_sampler", 0);
		for (int i = 0; i < bg.getSize(); ++i) {
			bg.getLayer(i).bindTexture();
			backgroundShader.setUniform1f("offx", ((i) * Background.PARRALAX_FACTOR) * bg.getTransform().x);
			backgroundShader.setUniform1f("offy", ((i) * Background.PARRALAX_FACTOR) * bg.getTransform().y);
			ResourceLoader.getQuadMesh().render(backgroundShader);
		}
	}
}
