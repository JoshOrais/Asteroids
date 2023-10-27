package engine.graphics;

import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.glDrawArrays;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL15.glBufferData;
import static org.lwjgl.opengl.GL15.glDeleteBuffers;
import static org.lwjgl.opengl.GL15.glGenBuffers;
import static org.lwjgl.opengl.GL20.glDisableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glDeleteVertexArrays;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;
import static org.lwjgl.opengl.GL31.glDrawArraysInstanced;

import java.nio.FloatBuffer;

import org.lwjgl.system.MemoryUtil;

public class Mesh {
	private int VAO, VBO, texCoordsVBO, vertexcount;
	
	public Mesh(float[] vertices, float[] texCoords) {

    	FloatBuffer vertexBuffer = null;
    	FloatBuffer coordsBuffer = null;
    	try {
    		vertexBuffer = MemoryUtil.memAllocFloat(vertices.length);
    		vertexBuffer.put(vertices).flip();
    		 
    		VAO = glGenVertexArrays();
    		glBindVertexArray(VAO);
    		
    		VBO = glGenBuffers();
    		
    		glBindBuffer(GL_ARRAY_BUFFER, VBO);
    		glBufferData(GL_ARRAY_BUFFER, vertexBuffer, GL_STATIC_DRAW);
            glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);
            
            if (texCoords != null) {
            	coordsBuffer = MemoryUtil.memAllocFloat(texCoords.length);
            	coordsBuffer.put(texCoords).flip();
            	
            	texCoordsVBO = glGenBuffers();
        		glBindBuffer(GL_ARRAY_BUFFER, texCoordsVBO);
        		glBufferData(GL_ARRAY_BUFFER, coordsBuffer, GL_STATIC_DRAW);
            	glVertexAttribPointer(1, 2,GL_FLOAT, false, 0, 0);
            }
            
            glBindBuffer(GL_ARRAY_BUFFER, 0);

            glBindVertexArray(0);
    	} finally {
            if (vertexBuffer != null) {
                MemoryUtil.memFree(vertexBuffer);
                this.vertexcount = vertices.length;
            }
            if (coordsBuffer != null) {
                MemoryUtil.memFree(coordsBuffer);
            }
        }
	}
	

	public Mesh(float[] vertices) {
		this(vertices, null);
	}
	
	public int getVAO() {
		return VAO;
	}
	
	public int getLength() {
		return this.vertexcount;
	}
	
	public void bind() {
    	glBindVertexArray(VAO);
    	glEnableVertexAttribArray(0);
    	glEnableVertexAttribArray(1);
	}
	
	public void unbind() {
    	glDisableVertexAttribArray(0);
    	glDisableVertexAttribArray(1);
    	glBindVertexArray(0);
	}
	
	public void render(Shader s) {
		s.bind();
		bind();
		glDrawArrays(GL_TRIANGLES, 0 , vertexcount);
		unbind();
	}

  public void renderInstanced(Shader s, int instances) {
    s.bind();
    bind();
    glDrawArraysInstanced(GL_TRIANGLES, 0, vertexcount, instances);
    unbind();
  }
	
	public void dispose() {
        glDisableVertexAttribArray(0);

        glBindBuffer(GL_ARRAY_BUFFER, 0);
        glDeleteBuffers(VBO);
        glDeleteBuffers(texCoordsVBO);

        glBindVertexArray(0);
        glDeleteVertexArrays(VAO);
	}
}
