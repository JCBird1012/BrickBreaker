import java.awt.Graphics;


public class Sprite
{
	int x, y;
	int width, height;
	
	
	
		public Sprite(int x, int y, int width, int height)
		{
			this.x = x;
			this.y = y;
			this.width = width;
			this.height = height;
			
		}

		public boolean rectCollide(Sprite sp) 
		{
			int left1, left2;
			int right1, right2;
			int top1, top2;
			int bottom1, bottom2;

			left1 = x;
			left2 = sp.x;
			right1 = x + width;
			right2 = sp.x + sp.width;
			top1 = y;
			top2 = sp.y;
			bottom1 = y + height;
			bottom2 = sp.y + sp.height;

			if (bottom1 < top2)
				return false;
			if (top1 > bottom2)
				return false;
			if (right1 < left2)
				return false;
			if (left1 > right2)
				return false;

			return true;
		}
}	
