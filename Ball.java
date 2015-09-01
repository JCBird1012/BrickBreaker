import java.awt.Color;
import java.awt.Graphics;
import java.net.URL;

public class Ball extends ImageSprite {
	
	//these variables control the speed of the ball
	public int vy = -15, vx = 15;
	
	//creates a ball a the given coordinates with specific image (inherited from ImageSprite)
	public Ball(int x, int y, URL url) 
	{
		super(x, y, 0, 0, url, false);
	}
	
	//moves coordinates of ball by vx and vy
	//preconditons: requires ball to be created
	//postconditions: none
	public void moveBall()
	{
		x -= vx;
		y += vy;
	}
	
	//reverses ball direction on the x-axis
	public void reverseDirectionX()
	{
		
		vx = -vx;
		
	}
	
	//reverses ball direction on the y-axis
	public void reverseDirectionY()
	{
		
		vy = -vy;
	}
	
	//sets balls x and y to the passed coordinates given in the method arguments
	public void setBallXY(int x, int y)
	{
		this.x = x;
		this.y = y; 
		
	}
	
}
			
		
	
		

	



	
	
	

	


	