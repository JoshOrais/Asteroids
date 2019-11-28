package engine.graphics;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class GameFont {
	private float height, width;
	private HashMap<Character, Glyph> glyphs = new HashMap<Character, Glyph>();
	private Texture texture;

	public static final int VERTICES_PER_QUAD = 6;

	public GameFont fromString(String str) {
		Scanner sc = new Scanner(str);

		height = (float)Integer.parseInt(sc.nextLine());
		width = (float)Integer.parseInt(sc.nextLine());

		while (sc.hasNext()) {
			char key = sc.nextLine().toCharArray()[0];
			float startx = (float)Integer.parseInt(sc.nextLine());
			float width = (float)Integer.parseInt(sc.nextLine());
			glyphs.put(key, new Glyph(startx, width));
		}

		sc.close();

		return this;
	}

	public void setTexture(Texture texture) {
		this.texture = texture;
	}

	public float getHeight() {
		return height;
	}

	public float getWidth() {
		return width;
	}

	public Texture getTexture() {
		return texture;
	}

	public Mesh buildMesh(String str) {
		ArrayList<Float> vertices = new ArrayList<Float>();
		ArrayList<Float> texcoords = new ArrayList<Float>();
		System.out.println(str);

		float startx = 0;
		for (char c: str.toCharArray()) {
			if (!glyphs.containsKey(c))
				System.out.println("glyphs not contain \'" + c +"\'");
			float charwidth = glyphs.get(c).getWidth();
			float charX = glyphs.get(c).getStartX();
			// X                              // Y                  // Z
			vertices.add(startx);             vertices.add(height); vertices.add(0.0f); // top    left
			vertices.add(startx + charwidth); vertices.add(height); vertices.add(0.0f); // top    right
			vertices.add(startx);             vertices.add(0.0f);   vertices.add(0.0f); // bottom left
			vertices.add(startx);             vertices.add(0.0f);   vertices.add(0.0f); // bottom left
			vertices.add(startx + charwidth); vertices.add(height); vertices.add(0.0f); // top    right
			vertices.add(startx + charwidth); vertices.add(0.0f);   vertices.add(0.0f); // bottom right

			float sx = charX / width;
			float ex = (charX + charwidth) / width;
			texcoords.add(sx); texcoords.add(1.0f); //top    left
			texcoords.add(ex); texcoords.add(1.0f); //top    right
			texcoords.add(sx); texcoords.add(0.0f); //bottom left
			texcoords.add(sx); texcoords.add(0.0f); //bottom left
			texcoords.add(ex); texcoords.add(1.0f); //top    right
			texcoords.add(ex); texcoords.add(0.0f); //bottom right

			startx += charwidth;
		}

		float[] v = new float[vertices.size()];
		for (int i = 0; i < vertices.size(); ++i) {
			v[i] = vertices.get(i);
		}

		float[] t = new float[texcoords.size()];
		for (int i = 0; i < texcoords.size(); ++i) {
			t[i] = texcoords.get(i);
		}

		return new Mesh(v, t);
	}

	public class Glyph{
		public float startx, width;
		public Glyph(float startx, float width) {
			this.startx = startx;
			this.width = width;
		}

		public float getStartX() {
			return startx;
		}

		public float getWidth() {
			return width;
		}
	}
}
