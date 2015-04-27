import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.net.URL;

import javax.swing.ImageIcon;


public class Brick extends ImageSprite
{
	
	public int maxHealth;
	public int health;
	
	public Brick(int x, int y, int maxHealth, URL url)
	{
		super(x, y, 0, 0, url, false);
		this.maxHealth = maxHealth;
		health = maxHealth;
	}
	public void blockColor(URL url) 
	{
		img = new ImageIcon(url).getImage();
		bimg = new BufferedImage(img.getWidth(null),img.getHeight(null),BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = bimg.createGraphics();
		g2d.drawImage(img, 0 , 0 , null);
		g2d.dispose();
	}
	
	
	
		
		
	
}
