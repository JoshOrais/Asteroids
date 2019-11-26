package game.asteroids.graphics;

import java.awt.image.BufferedImage;
import java.io.*;

public class FontMaker3000 {
	public static final String FONTSTRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890";
	private Font font;
	private int fontsize;
	
	public FontMaker3000(Font font, int fontsize) {
		this.font = font;
		this.fontsize = fontsize;
	}
	
	public void makeFontTexture() {
		BufferedImage img = new BufferedImage(1,1,BufferedImage.TYPE_INT_ARGB);
	}
	
	
}
