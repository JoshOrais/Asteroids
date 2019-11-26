package engine;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Scanner;

import javax.imageio.ImageIO;

import engine.graphics.GameFont;
import engine.graphics.Mesh;
import engine.graphics.Shader;
import engine.graphics.Texture;

public class ResourceLoader {
	public static HashMap<String, Shader> shaders = null;
	public static HashMap<String, Texture> textures = null;
	
	public static String loadFile(String fileName) throws Exception {
//		String a = Class.forName(ResourceLoader.class.getName()).getProtectionDomain().getCodeSource().getLocation().getPath();
//		System.out.println(a);
//		System.out.println("^ is loading " + fileName);
        String result;
        try (InputStream in = Class.forName(ResourceLoader.class.getName()).getResourceAsStream(fileName);
                Scanner scanner = new Scanner(in, "UTF-8")) {
            result = scanner.useDelimiter("\\A").next();
        }
        return result;
    }
	
	public static BufferedImage loadImage(String fileName) throws Exception {
		BufferedImage img = null;
		String a = Class.forName(ResourceLoader.class.getName()).getProtectionDomain().getCodeSource().getLocation().getPath();
		System.out.println(a);
        try ( InputStream iis = Class.forName(ResourceLoader.class.getName()).getResourceAsStream(fileName);){
        	img = ImageIO.read(new File(fileName));
        }
        
        return img;
	}
	
	public static Texture loadTexture(String dir) throws Exception{
		BufferedImage img = loadImage(dir);
		return new Texture(img);
	}
	
	public static Texture addTexture(String key, String dir) throws Exception {
		Texture tex = loadTexture(dir);
		if (textures == null)
			textures = new HashMap<String, Texture>();
		
		if (textures.containsKey(key)) throw new Exception();
		else textures.put(key, tex);
		
		return tex;
	}
	
	
	public static Shader addShader(String key, String vertexShaderDIR, String fragmentShaderDIR) throws Exception {
		Shader shader = loadShader(vertexShaderDIR, fragmentShaderDIR);
		if (shaders == null)
			shaders = new HashMap<String, Shader>();
		
		if (shaders.containsKey(key)) throw new Exception();
		else shaders.put(key, shader);
		return shader;
	}
	
	//i dont want to do try catch everytime i wanna get a shader;
	public static Shader getShader(String key) {
		if (!shaders.containsKey(key)) return null;
		
		return shaders.get(key);
	}
	
	public static Texture getTexture(String key) {
		if (!textures.containsKey(key)) return null;

		return textures.get(key);
	}
	
	public static Shader loadShader(String vertexDIR, String fragmentDIR) throws Exception {
		String vertexSource = loadFile(vertexDIR);
		String fragSource = loadFile(fragmentDIR);
		return new Shader(vertexSource, fragSource);
	}
	
	public static void disposeShaders() {
		if (shaders == null) return;
		
		for (Shader s: shaders.values())
			s.dispose();
	}
	
	public static void disposeTextures() {
		if (textures == null) return;
		
		for (Texture t: textures.values())
			t.dispose();
	}
	
	
	public static Mesh quadMesh = null;
	
	public static Mesh getQuadMesh() {
		if (quadMesh == null) {
			float[] vertices = {
					0.5f,  0.5f,  0.0f,
				   -0.5f,  0.5f,  0.0f,
				   -0.5f, -0.5f,  0.0f,
				   -0.5f, -0.5f,  0.0f,
					0.5f, -0.5f,  0.0f,
					0.5f,  0.5f,  0.0f,
			};
   	
			float[] texcoords = {
					1.0f,  1.0f,
					0.0f,  1.0f,
					0.0f,  0.0f,
					0.0f,  0.0f,
					1.0f,  0.0f,
					1.0f,  1.0f,
			};
			
			quadMesh = new Mesh(vertices, texcoords);
		}
		
		return quadMesh;
	}
	
	public static GameFont buildFont(String file) throws Exception{
		String f = loadFile(file);
		return new GameFont().fromString(f);
	}
}
