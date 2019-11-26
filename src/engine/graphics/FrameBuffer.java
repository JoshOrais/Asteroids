package engine.graphics;

import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL33.*;
import static org.lwjgl.system.MemoryUtil.NULL;

public class FrameBuffer {
	private int ID, texture, rbo;
	
	public FrameBuffer(int width, int height) throws Exception{
		this.ID = glGenFramebuffers();
		glBindFramebuffer(GL_FRAMEBUFFER, ID);
		
		this.texture = glGenTextures();
		glBindTexture(GL_TEXTURE_2D, texture);
	    glTexImage2D(GL_TEXTURE_2D, 0, GL_RGB, width, height, 0, GL_RGB, GL_UNSIGNED_BYTE, NULL);
	    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
	    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
	    glFramebufferTexture2D(GL_FRAMEBUFFER, GL_COLOR_ATTACHMENT0, GL_TEXTURE_2D, texture, 0);

	    this.rbo = glGenRenderbuffers();
	    glBindRenderbuffer(GL_RENDERBUFFER, rbo);
	    glRenderbufferStorage(GL_RENDERBUFFER, GL_DEPTH24_STENCIL8, width, height); // use a single renderbuffer object for both a depth AND stencil buffer.
	    glFramebufferRenderbuffer(GL_FRAMEBUFFER, GL_DEPTH_STENCIL_ATTACHMENT, GL_RENDERBUFFER, rbo); // now actually attach it
	    
	    if (glCheckFramebufferStatus(GL_FRAMEBUFFER) != GL_FRAMEBUFFER_COMPLETE) {
	    	throw new Exception("FRAME BUFFER is not complete");
	    }
		glBindFramebuffer(GL_FRAMEBUFFER, 0);
	}
	
	
	public void bind() {
		glBindFramebuffer(GL_FRAMEBUFFER, ID);
	}
	
	public void bindTexture() {
		glBindTexture(GL_TEXTURE_2D, this.texture);
	}
	
	public void unbind() {
		glBindFramebuffer(GL_FRAMEBUFFER, 0);
	}
	
	public int getTexture() {
		return texture;
	}
	
	public void dispose() {
		glBindFramebuffer(GL_FRAMEBUFFER, 0);
		glDeleteFramebuffers(ID);
	}
}
