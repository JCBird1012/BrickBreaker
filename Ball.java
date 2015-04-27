import java.awt.Color;
import java.awt.Graphics;
import java.net.URL;

public class Ball extends ImageSprite {

	public int vy = -15, vx = 15;
	public Ball(int x, int y, URL url) 
	{
		super(x, y, 0, 0, url, false);
	}
	

	public void moveBall()
	{
		x += vx;
		y += vy;
	}
	
	public void reverseDirectionX()
	{
		
		vx = -vx;
		
	}
	
	public void reverseDirectionY()
	{
		
		vy = -vy;
	}
	
	public void setBallXY(int x, int y)
	{
		this.x = x;
		this.y = y; 
		
	}
	
}
			
		
	
		

	



	
	
	

	


	