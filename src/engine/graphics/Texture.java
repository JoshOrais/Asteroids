package engine.graphics;

import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.glActiveTexture;
import static org.lwjgl.opengl.GL33.*;

import java.awt.image.BufferedImage;
import java.nio.ByteBuffer;

import org.lwjgl.BufferUtils;

public class Texture {
	private int ID;
	private int width;
	private int height;

	public Texture(BufferedImage img) {
		this.width = img.getWidth();
		this.height = img.getHeight();

		int[] pixels = img.getRGB(0, 0, width, height, null, 0, width);

		ByteBuffer pixelBuffer = BufferUtils.createByteBuffer(width * height * 4);


		for (int i = 0; i < height; ++i) {
			for (int j = 0; j < width; j++) {
				int pixel = pixels[(height - 1 - i) * width + j];
				pixelBuffer.put((byte) ((pixel >> 16) & 0xFF ));
				pixelBuffer.put((byte) ((pixel >>  8) & 0xFF ));
				pixelBuffer.put((byte) ((pixel >>  0) & 0xFF ));
				pixelBuffer.put((byte) ((pixel >> 24) & 0xFF ));
			}
		}

		pixelBuffer.flip();

		this.ID = glGenTextures();

		glBindTexture(GL_TEXTURE_2D, ID);

		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);

		glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, pixelBuffer);

		glGenerateMipmap(GL_TEXTURE_2D);
	}

	public void bind() {
    glActiveTexture(GL_TEXTURE0);
		glBindTexture(GL_TEXTURE_2D, this.ID);
	}
	public void unbind() {
	  glActiveTexture(GL_TEXTURE0);
		glBindTexture(GL_TEXTURE_2D, 0);
	}

	public void dispose() {
		glDeleteTextures(ID);
	}
}
