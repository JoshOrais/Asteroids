package engine.graphics;

public class TextObject {
	private String string;
	private Mesh mesh;
	private GameFont font;

	public TextObject(String str, GameFont font) {
		string = str;
		this.font = font;
		createMesh(font);
	}

	public void setText(String string) {
		this.string = string;
		createMesh(font);
	}

	public void createMesh(GameFont font) {
		this.mesh = font.buildMesh(string);
	}

	public Mesh getMesh() {
		return mesh;
	}
}
