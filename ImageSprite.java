import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.net.URL;

import javax.swing.ImageIcon;


public class ImageSprite {
	
	int x,y, x0, y0, offsetX, offsetY;
	int width, height;
	BufferedImage bimg;
	boolean flip;
	Image img;
	
	public ImageSprite(int x, int y, int offsetX, int offsetY, URL url, boolean flip)
	{
		
		this.x = x;
		this.y = y;
		this.offsetX = offsetX;
		this.offsetY = offsetY;
		this.flip = flip;
		x0 = x - offsetX;
		y0 = y - offsetY;
		
		img = new ImageIcon(url).getImage();
		bimg = new BufferedImage(img.getWidth(null),img.getHeight(null),BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = bimg.createGraphics();
		g2d.drawImage(img, 0 , 0 , null);
		g2d.dispose();
		
		width = img.getWidth(null);
		height = img.getHeight(null);
			
	}
	
	public boolean hitTestObject(ImageSprite sp)
	{
		int left1, left2;
		int right1, right2;
		int top1, top2;
		int bottom1, bottom2;
		
		x0 = x - offsetX;
		y0 = y - offsetY;
		sp.x0 = sp.x - sp.offsetX;
		sp.y0 = sp.y - sp.offsetY;
		
		left1 = x0;
		left2  = sp.x0;
		right1 = x0 + width;
		right2 = sp.x0 + sp.width;
		top1 = y0;
		top2 = sp.y0;
		bottom1 = y0 + height;
		bottom2 = sp.y0 + sp.height;
		
		if (bottom1 < top2) return false;
		if (top1 > bottom2) return false;
		if (right1 < left2) return false;
		if (left1 > right2) return false;
		return true;
	}
	
	public boolean hitTestPoint(int x, int y)
	{boolean isHit =  false;
		
		int globalToLocalX = x - this.x;
		int globalToLocalY = y - this.y;
		if (globalToLocalX > 0 && globalToLocalX < bimg.getWidth())
		{
			if (globalToLocalY > 0 && globalToLocalY < bimg.getHeight())
			{
				int color = bimg.getRGB(globalToLocalX, globalToLocalY);
				int alpha = (color >> 24) & 0xff;
				if (alpha > 0) 
					
				isHit = true;
				
			}
		}
		
	 return isHit;
	}
	
	public void draw (Graphics g)
	{	x0 = x - offsetX;
		y0 = y - offsetY;
		// false = left, right = true
		if(flip){
	
		g.drawImage(bimg, x0, y0, width, height, null);}
		
		else{
			int w = bimg.getWidth(null);
			int h = bimg.getHeight(null);
			g.drawImage(bimg, w+x0, y0, -w, h, null);
		}
		
				
		
	}

}