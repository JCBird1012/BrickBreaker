import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JApplet;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

public class BrickBreaker extends JApplet {

	//set the size of the game window (setting static variables creates issues with different monitor sizes).
	public final static int GAME_WIDTH = 565;
	public final static int GAME_HEIGHT = 960;


	private StartScreen startscreen;
	private StagePanel gameScreen;
	public boolean gameOver;

	public BrickBreaker() {
		startscreen = new StartScreen();
		add(startscreen);
	}

	public static void main(String[] args) throws IOException {
		JFrame frame = new JFrame("Brick Breaker");
		BrickBreaker myApplet = new BrickBreaker();
		frame.add(myApplet);
		myApplet.init();
		myApplet.start();

		frame.setSize(GAME_WIDTH, GAME_HEIGHT);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.setResizable(false);
		Image icon = null;
		icon = ImageIO.read(BrickBreaker.class.getResource("/images/Ball.png"));
		frame.setIconImage(icon);

	}

	public void loadGameScreen()
	{
		gameScreen = new StagePanel();
		remove (startscreen);
		add (gameScreen);
		gameScreen.requestFocusInWindow();
		invalidate();
		validate();

	}

	public void loadStartScreen()
	{
		remove (gameScreen);
		startscreen = new StartScreen();
		add (startscreen);
		startscreen.requestFocusInWindow();
		invalidate();
		validate();


	}

	//Start Screen
	public class StartScreen extends JPanel
	{
		ImageSprite background;
		public StartScreen()
		{
			//creates background for StartScreen
			setLayout(null);
			background = new ImageSprite(0,0,0,0,this.getClass().getResource("images/START SCREEN BACKGROUND.png"),true);

			//adds a mouse listener (primarily for click event)
			MouseListener myMouseListener = new MyMouseListener();
			addMouseListener(myMouseListener);

		}

		@Override
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			background.draw(g);

		}

		protected class MyMouseListener implements MouseListener {

			@Override
			//on the click event, the startScreen will be unloaded and the gameScreen loaded.
			public void mouseClicked(MouseEvent e) {
				loadGameScreen();
			}

			@Override
			public void mouseEntered(MouseEvent e) {


			}

			@Override
			public void mouseExited(MouseEvent e) {


			}

			@Override
			public void mousePressed(MouseEvent e) {


			}

			@Override
			public void mouseReleased(MouseEvent e) {


			}

		}
	}

	private class StagePanel extends JPanel {
		int x, y = 400, width = 120, height = 32, ballx, bally, ballW = 20,
				ballH = 20, brickx = 0, bricky = 75, brickW = 80, brickH = 30,
				numRow = 5, numCol = 7;
		int lives;
		int count = 0;

		boolean moveWithPaddle;

		PaddleClass paddle;
		Ball ball;
		Brick brick;
		ArrayList<Brick> bricks = new ArrayList<Brick>();
		ImageSprite heart;
		ImageSprite background;


		public StagePanel() {
			this.setFocusable(true);

			paddle = new PaddleClass(x, y, this.getClass().getResource(
					"images/Paddle.png"));
			ball = new Ball(ballx, bally, this.getClass().getResource(
					"images/Ball.png"));
			heart = new ImageSprite(570, 100, 0, 0, this.getClass()
					.getResource("images/Heart.png"), true);
			background = new ImageSprite(0, 0, 0, 0, this.getClass()
					.getResource("images/Background.png"), true);
			moveWithPaddle = true;
			gameOver = false;

			lives = 3;

			MouseMotionListener myMouseMotion = new MyMouseMotion();
			addMouseMotionListener(myMouseMotion);

			MouseListener myMouseListener = new MyMouseListener();
			addMouseListener(myMouseListener);

			Timer myTimer = new Timer(33, new MyTimerListener());
			myTimer.start();

			//for loop will create bricks on each row, with a different color based on health
			for (int row = 0; row < numRow; row++) {
				for (int col = 0; col < numCol; col++) {

					URL url = null;
					int health = 0;

					if (row == 0) {
						url = this.getClass().getResource("images/Block5.png");
						health = 5;
					} else if (row == 1) {
						url = this.getClass().getResource("images/Block4.png");
						health = 4;
					} else if (row == 2) {
						url = this.getClass().getResource("images/Block3.png");
						health = 3;
					} else if (row == 3) {
						url = this.getClass().getResource("images/Block2.png");
						health = 2;
					} else if (row == 4) {
						url = this.getClass().getResource("images/Block.png");
						health = 1;

					}

					//creates an array of bricks, given the input arguments
					bricks.add(new Brick(brickx + col * brickW, bricky
							+ row * brickH, health, url));

					count++;
				}
			}

}


		@Override
		public void paintComponent(Graphics g) {
			super.paintComponent(g);

			g.drawImage(background.img, 0, 0, this);
			background.draw(g);
			paddle.draw(g);
			ball.draw(g);

			for (int i = 0; i < bricks.size(); i++) {

				if (bricks.get(i) != null && bricks.get(i).health != 0)
				{
					bricks.get(i).draw(g);
				}

			}

			for (int i = 0; i < lives; i++) {
				g.drawImage(heart.bimg, 390 + i * (heart.width + 20), 15, this);

			}

		}

		protected class MyMouseMotion implements MouseMotionListener {

			@Override
			public void mouseDragged(MouseEvent arg0) {

			}

			@Override
			public void mouseMoved(MouseEvent mouse) {

				paddle.x = mouse.getX();
				if (moveWithPaddle == true) {
					ball.setBallXY(paddle.x + 60, paddle.y - 30);

				}

				if (paddle.x + 150 >= GAME_WIDTH)
				{
					paddle.x = GAME_WIDTH - 150;

				}

				if (paddle.x + 150 >= GAME_WIDTH && moveWithPaddle == true)
				{
					ball.setBallXY(paddle.x + 60, paddle.y - 30);
				}
				repaint();


			}

		}

		protected class MyMouseListener implements MouseListener {

			@Override
			public void mouseClicked(MouseEvent arg0) {
				moveWithPaddle = false;
				ball.y -= 15;

			}

			@Override
			public void mouseEntered(MouseEvent arg0) {

			}

			@Override
			public void mouseExited(MouseEvent arg0) {

			}

			@Override
			public void mousePressed(MouseEvent arg0) {

			}

			@Override
			public void mouseReleased(MouseEvent arg0) {

			}

		}

		private class MyTimerListener implements ActionListener {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (moveWithPaddle == false) {
					ball.moveBall();

				}

				if (ball.x >= GAME_WIDTH - 15 || ball.x <= 0) {
					ball.reverseDirectionX();

				}

				if (ball.y >= GAME_HEIGHT) {
					lives--;
					moveWithPaddle = true;
					ball.y = paddle.y;
					ball.x = paddle.x;
				}

				if (ball.y >= GAME_HEIGHT + 10 || ball.y <= 0) {
					ball.reverseDirectionY();
				}

				if (ball.hitObject(paddle)) 
				{
					ball.reverseDirectionY();
				}


				count = 0;
				boolean allBricksGone = true;
				
				for (int count = 0; count < numCol * numRow; count++) 
				{
					if (bricks.get(count) != null && ball.hitObject(bricks.get(count)))
					{
						ball.reverseDirectionY();
						bricks.get(count).health--;}

					if (bricks.get(count) != null && bricks.get(count).health == 5)
					{
						bricks.get(count).blockColor(this.getClass()
								.getResource("images/Block5.png"));
					}

					else if (bricks.get(count) != null && bricks.get(count).health == 4)
					{
						bricks.get(count).blockColor(this.getClass()
								.getResource("images/Block4.png"));
					}

					else if (bricks.get(count) != null && bricks.get(count).health == 3)
					{
						bricks.get(count).blockColor(this.getClass()
								.getResource("images/Block3.png"));
					}

					else if (bricks.get(count) != null && bricks.get(count).health == 2)
					{
						bricks.get(count).blockColor(this.getClass()
								.getResource("images/Block2.png"));
					}

					else if (bricks.get(count) != null && bricks.get(count).health == 1)
					{
						bricks.get(count).blockColor(this.getClass()
								.getResource("images/Block.png"));
					}

					else 
					{
						bricks.set(count, null);
					}

				}
				
				for (int i=0; i < bricks.size(); i++) 
				{
					  if (bricks.get(i) != null) 
					  {
					    allBricksGone = false;
					    break;
					  }
				}
				
				if (lives == 0 || allBricksGone)
				{
					loadStartScreen();
					ball.setBallXY(paddle.x + 50, paddle.y - 30);
				}

				//repaint() is important (without it, the game will not draw elements when needed)
				repaint();

			}
		}

	}

}
