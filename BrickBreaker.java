import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.net.URL;

import javax.swing.JApplet;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

public class BrickBreaker extends JApplet {
	public final static int GAME_WIDTH = 480;
	public final static int GAME_HEIGHT = 640;
	
	private StartScreen startscreen;
	private StagePanel gameScreen;
	public boolean gameOver;

	public BrickBreaker() {
		startscreen = new StartScreen();
		add(startscreen);
	}

	public static void main(String[] args) {
		JFrame frame = new JFrame("Brick Breaker");
		BrickBreaker myApplet = new BrickBreaker();
		frame.add(myApplet);
		myApplet.init();
		myApplet.start();

		frame.setSize(GAME_WIDTH, GAME_HEIGHT);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.setResizable(true);

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
			setLayout(null);
			background = new ImageSprite(0,0,0,0,this.getClass().getResource("images/START SCREEN BACKGROUND.png"),true);
			
			MouseListener myMouseListener = new MyMouseListener();
			addMouseListener(myMouseListener);
			
		}
		
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			background.draw(g);
			
		}
		
		protected class MyMouseListener implements MouseListener {

			@Override
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
		Brick[] bricks = new Brick[35];
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

					bricks[count] = new Brick(brickx + col * brickW, bricky
							+ row * brickH, health, url);

					count++;
				}
			}

		}

		public void paintComponent(Graphics g) {
			super.paintComponent(g);

			// g.drawImage(background.img, 0, 0, this);
			background.draw(g);
			paddle.draw(g);
			ball.draw(g);
			for (int i = 0; i < bricks.length; i++) {
				bricks[i].draw(g);
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
			public void mouseMoved(MouseEvent e) {

				paddle.x = e.getX();
				if (moveWithPaddle == true) {
					ball.setBallXY(paddle.x + 50, paddle.y - 30);

				}
				
				if (paddle.x + 150 >= 570)
				{
					paddle.x = 570 - 150;
					
				}
			
				if (paddle.x + 150 >= 570 && moveWithPaddle == true)
				{
					ball.setBallXY(paddle.x + 50, paddle.y - 30);
				}
				repaint();
				
				
			}

		}

		protected class MyMouseListener implements MouseListener {

			@Override
			public void mouseClicked(MouseEvent arg0) {
				moveWithPaddle = false;
				ball.y -= 5;

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

				if (ball.x >= GAME_WIDTH || ball.x <= 0) {
					ball.reverseDirectionX();

				}

				if (ball.y >= 450) {
					lives--;
					moveWithPaddle = true;
					ball.y = paddle.y;
					ball.x = paddle.x;
				}

				if (ball.y >= GAME_HEIGHT + 10 || ball.y <= 0) {
					ball.reverseDirectionY();
				}

				if (ball.hitTestObject(paddle)) {
					ball.reverseDirectionY();
				}
				count = 0;
				for (int count = 0; count < numCol * numRow; count++) {
					if (ball.hitTestObject(bricks[count])) {
						ball.reverseDirectionY();
						bricks[count].health--;}
						
						if (bricks[count].health == 5) {
							bricks[count].blockColor(this.getClass()
									.getResource("images/Block5.png"));
						}
						
						else if (bricks[count].health == 4)
						{
							bricks[count].blockColor(this.getClass()
									.getResource("images/Block4.png"));
						}
						
						else if (bricks[count].health == 3)
						{
							bricks[count].blockColor(this.getClass()
									.getResource("images/Block3.png"));
						}
						
						else if (bricks[count].health == 2)
						{
							bricks[count].blockColor(this.getClass()
									.getResource("images/Block2.png"));
						}
						
						else if (bricks[count].health == 1)
						{
							bricks[count].blockColor(this.getClass()
									.getResource("images/Block.png"));
						}

						if (bricks[count].health == 0) {
							bricks[count].x = -500;
							bricks[count].y = -500;

						}
					}
				
				if (lives == 0 )
				{
					loadStartScreen();
					ball.setBallXY(paddle.x + 50, paddle.y - 30);
					
				}
				
			
				repaint();

			}
		}

	}

}
