package engine.graphics;

import static org.lwjgl.opengl.GL20.GL_COMPILE_STATUS;
import static org.lwjgl.opengl.GL20.GL_FRAGMENT_SHADER;
import static org.lwjgl.opengl.GL20.GL_LINK_STATUS;
import static org.lwjgl.opengl.GL20.GL_VALIDATE_STATUS;
import static org.lwjgl.opengl.GL20.GL_VERTEX_SHADER;
import static org.lwjgl.opengl.GL20.glAttachShader;
import static org.lwjgl.opengl.GL20.glCompileShader;
import static org.lwjgl.opengl.GL20.glCreateProgram;
import static org.lwjgl.opengl.GL20.glCreateShader;
import static org.lwjgl.opengl.GL20.glDeleteShader;
import static org.lwjgl.opengl.GL20.glGetProgramInfoLog;
import static org.lwjgl.opengl.GL20.glGetProgrami;
import static org.lwjgl.opengl.GL20.glGetShaderInfoLog;
import static org.lwjgl.opengl.GL20.glGetShaderi;
import static org.lwjgl.opengl.GL20.glGetUniformLocation;
import static org.lwjgl.opengl.GL33.*;
import static org.lwjgl.opengl.GL20.glShaderSource;
import static org.lwjgl.opengl.GL20.glUniform1i;
import static org.lwjgl.opengl.GL20.glUniform2fv;
import static org.lwjgl.opengl.GL20.glUseProgram;
import static org.lwjgl.opengl.GL20.glValidateProgram;
import static org.lwjgl.opengl.GL32.GL_GEOMETRY_SHADER;

import java.nio.FloatBuffer;

import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.lwjgl.system.MemoryStack;

public class Shader {
	private final int ID;
	private int vertexID, fragmentID, geometryID;
	
	public Shader(String vertexSource, String geomSource, String fragmentSource) throws Exception{
		this.ID = glCreateProgram();
		
		if (this.ID == 0) {
			throw new Exception();
		}
		
		this.vertexID = createShader(vertexSource, GL_VERTEX_SHADER);
		this.fragmentID = createShader(fragmentSource, GL_FRAGMENT_SHADER);
		
		glAttachShader(ID, this.vertexID);
		glAttachShader(ID, this.fragmentID);
		
		if (geomSource != null) {
			this.geometryID = createShader(geomSource, GL_GEOMETRY_SHADER);
			glAttachShader(ID, this.geometryID);
		}
		
		glLinkProgram(ID);

        if (glGetProgrami(ID, GL_LINK_STATUS) == 0) {
            throw new Exception("Error linking Shader code: " + glGetProgramInfoLog(ID, 1024));
        }
        
        glValidateProgram(ID);
        if (glGetProgrami(ID, GL_VALIDATE_STATUS) == 0) {
            System.err.println("Warning validating Shader code: " + glGetProgramInfoLog(ID, 1024));
        }
        
        glDeleteShader(vertexID);
        glDeleteShader(fragmentID);
        if (geomSource != null)
        	glDeleteShader(geometryID);
	}
	
	public Shader(String vertexSource, String fragmentSource) throws Exception{
		this(vertexSource, null, fragmentSource);
	}
	
	private int createShader(String source, int type) throws Exception {
		int shaderID = glCreateShader(type);
		
		if (shaderID == 0)
			throw new Exception();
		
		glShaderSource(shaderID, source);
		glCompileShader(shaderID);
		
		if ( glGetShaderi(shaderID, GL_COMPILE_STATUS) == 0 ) {
			System.out.println(glGetShaderInfoLog(shaderID, 1024));
			throw new Exception();
		}
		
		return shaderID;
	}
	
	public void bind() {
		glUseProgram(ID);
	}
	
	public void unbind() {
		glUseProgram(0);
	}
	
	public void dispose() {
		unbind();
		if (ID != 0)
			glDeleteShader(ID);
	}
	
	
	public void setUniform1i(String name, int value) {
		glUniform1i(glGetUniformLocation(ID, name), value);
	}
	
	public void setUniform1f(String name, float value) {
		glUniform1f(glGetUniformLocation(ID, name), value);
	}

        public void setUniform2fv(String name, float[] value) {
           glUniform2fv(glGetUniformLocation(ID, name), value);
        }

        public void setUniform2fv(String name, Vector2f value) {
           float[] values = new float[]{value.x, value.y};
           glUniform2fv(glGetUniformLocation(ID, name), values);
        }
	
	public void setUniformMatrix4f(String name, Matrix4f value) {
		try (MemoryStack stack = MemoryStack.stackPush()){
			FloatBuffer buffer = stack.mallocFloat(16);
			value.get(buffer);
			glUniformMatrix4fv(glGetUniformLocation(ID, name), false, buffer);
		}
	}
	
}
