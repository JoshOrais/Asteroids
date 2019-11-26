package game.asteroids.graphics;

import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

import javax.imageio.ImageIO;

public class FontMaker3000 {
	public static final String FONTSTRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890 ";
	public static final int FONT_PADDING = 2;
	private java.awt.Font font;
	private int fontsize;
	
	public FontMaker3000(java.awt.Font font, int fontsize) {
		this.font = font;
		this.fontsize = fontsize;
	}
	
	public void makeFontTexture() throws Exception {
		String filestring = "";
		BufferedImage img = new BufferedImage(1,1,BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = img.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setFont(font);
        FontMetrics fm = g2d.getFontMetrics();
        
        int width = 0;
        int height = fm.getHeight();
        filestring += String.valueOf(height) + "\n";
        
        String m = "";
        for (char c: FONTSTRING.toCharArray()) {
        	m += c + "\n";
        	m += String.valueOf(width) + "\n";        	
        	m += String.valueOf(fm.charWidth(c)) + "\n";
        	width += fm.charWidth(c) + FONT_PADDING;
        }
        filestring += String.valueOf(width) + "\n";
        filestring += m;
        
        img  = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        g2d = img.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setFont(font);
        fm = g2d.getFontMetrics();
        
        int startx = 0;
        
        for (char c: FONTSTRING.toCharArray()) {
        	g2d.drawString("" + c, startx, fm.getAscent());
        	startx += fm.charWidth(c) + FONT_PADDING;
        }
        
        File f = new File("font.png");
        ImageIO.write(img, "png", f);
        File t = new File("font.font");
        BufferedWriter bw = new BufferedWriter(new FileWriter(t));
        bw.write(filestring);
        bw.close();
	}
	
	public static void main(String[] args) {
		try {
			new FontMaker3000(new java.awt.Font("calibri", java.awt.Font.PLAIN, 20), 20).makeFontTexture();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
}
