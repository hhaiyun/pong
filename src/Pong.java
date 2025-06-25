import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.WindowConstants;

public class Pong extends JPanel implements KeyListener, ActionListener {

	private Ball ball;
	private Image score, gameOverMsg;
	private JFrame frame;
	private Paddle p1, p2;
	private Random random;
	private Timer gameLoop;

	private boolean twoPlayer, gameEnd;
	private boolean upKey, downKey, wKey, sKey;
	private int p1Score, p2Score, fieldWidth, halfFieldWidth, fieldHeight, halfFieldHeight, paddleWidth, paddleHeight,
			halfPaddleHeight, ballSideLength, halfBallSideLength;

	private static final long serialVersionUID = 1L;

	Pong() {
		super();
		super.addKeyListener(this);
		super.setFocusable(true);
		super.setPreferredSize(new Dimension(800, 400));

		frame = new JFrame();
		twoPlayer = false;
		gameEnd = false;

		fieldWidth = 800;
		fieldHeight = 400;

		halfFieldWidth = fieldWidth / 2;
		halfFieldHeight = fieldHeight / 2;

		paddleWidth = 8;
		paddleHeight = 48;
		ballSideLength = 8;

		halfPaddleHeight = paddleHeight / 2;
		halfBallSideLength = ballSideLength / 2;

		p1 = new Paddle(20, (halfFieldHeight) - (halfPaddleHeight), paddleWidth, paddleHeight);
		p2 = new Paddle(fieldWidth - 20 - paddleWidth, (halfFieldHeight) - (halfPaddleHeight), paddleWidth,
				paddleHeight);

		score = new ImageIcon("../img/scores.png").getImage();
		p1Score = 0;
		p2Score = 0;

		wKey = false;
		sKey = false;
		upKey = false;
		downKey = false;

		random = new Random();
		ball = new Ball((halfFieldWidth) - (halfBallSideLength), (halfFieldHeight) - (halfBallSideLength),
				random.nextInt(2) == 0 ? -4 : 4, random.nextInt(2) == 0 ? -1 : 1, ballSideLength);
	}

	@Override
	public void paint(Graphics g) {
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, fieldWidth, fieldHeight);

		g.setColor(Color.WHITE);
		ball.paint(g);
		p1.paint(g);
		p2.paint(g);

		g.drawImage(score, halfFieldWidth - 120 - 28, 50, halfFieldWidth - 120, 50 + 24, p1Score * 28, 0,
				p1Score * 28 + 28, 24, null);
		g.drawImage(score, halfFieldWidth + 120, 50, halfFieldWidth + 120 + 28, 50 + 24, p2Score * 28, 0,
				p2Score * 28 + 28, 24, null);

		if (gameEnd) {
			g.drawImage(gameOverMsg, 260, 130, 540, 270, 0, 0, 280, 140, null);
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (gameEnd) {
			if (e.getKeyCode() == KeyEvent.VK_M) {
				frame.dispose();
				Main.pong();
			}
		}

		if (e.getKeyCode() == KeyEvent.VK_W) {
			wKey = true;
		}
		if (e.getKeyCode() == KeyEvent.VK_S) {
			sKey = true;
		}
		if (twoPlayer) {
			if (e.getKeyCode() == KeyEvent.VK_UP) {
				upKey = true;
			}
			if (e.getKeyCode() == KeyEvent.VK_DOWN) {
				downKey = true;
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_W) {
			wKey = false;
		}
		if (e.getKeyCode() == KeyEvent.VK_S) {
			sKey = false;
		}
		if (twoPlayer) {
			if (e.getKeyCode() == KeyEvent.VK_UP) {
				upKey = false;
			}
			if (e.getKeyCode() == KeyEvent.VK_DOWN) {
				downKey = false;
			}
		}
	}

	private void paddleMove() {
		if (wKey) {
			p1.moveYCoord(-5);
		}
		if (sKey) {
			p1.moveYCoord(5);
		}
		if (upKey) {
			p2.moveYCoord(-5);
		}
		if (downKey) {
			p2.moveYCoord(5);
		}
	}

	private void robotPaddleMove() {
		if (ball.getDX() < 0 && (p2.getYCoord() < (halfFieldHeight) - (halfPaddleHeight)
				|| p2.getYCoord() > (halfFieldHeight) - (halfPaddleHeight))) {
			p2.moveYCoord(p2.getYCoord() < (halfFieldHeight) - (halfPaddleHeight) ? 1 : -1);
		} else if (ball.getDX() > 0 && ball.getXCoord() >= halfFieldWidth && ball.getXCoord() <= fieldWidth - 20) {
			for (int i = 0; i < 5; i++) {
				if ((p2.getYCoord() + (halfBallSideLength) - ball.getYCoord()) > 0) {
					p2.moveYCoord(-1);
				} else if ((p2.getYCoord() + (halfBallSideLength) - ball.getYCoord()) < 0) {
					p2.moveYCoord(1);
				}
			}
		}
	}

	private void paddleCollision() {
		if (p1.getYCoord() <= 0) {
			p1.setYCoord(0);
		}
		if (p1.getYCoord() >= fieldHeight - paddleHeight) {
			p1.setYCoord(fieldHeight - paddleHeight);
		}
		if (p2.getYCoord() <= 0) {
			p2.setYCoord(0);
		}
		if (p2.getYCoord() >= fieldHeight - paddleHeight) {
			p2.setYCoord(fieldHeight - paddleHeight);
		}
	}

	private void ballCollision() {
		if (ball.getXCoord() >= fieldWidth - ballSideLength) {
			p1Score++;
			repaint();
			reset();
			gameEnd();
		}
		if (ball.getXCoord() <= 0) {
			p2Score++;
			repaint();
			reset();
			gameEnd();
		}

		if (ball.getYCoord() + ballSideLength >= fieldHeight || ball.getYCoord() <= 0) {
			ball.setDY(ball.getDY() * -1);
		}

		if (ball.getXCoord() >= p1.getXCoord() && ball.getXCoord() <= p1.getXCoord() + paddleWidth) {
			if (ball.getYCoord() < p1.getYCoord() + paddleHeight
					&& ball.getYCoord() + ballSideLength > p1.getYCoord()) {
				ball.setDY((int) (Math
						.sqrt(Math.abs(((ball.getYCoord() + halfBallSideLength) - (p1.getYCoord() + halfPaddleHeight))
								/ (float) ((paddleHeight + ballSideLength) / 2)))
						* Math.signum(((ball.getYCoord() + halfBallSideLength) - (p1.getYCoord() + halfPaddleHeight)))
						* 8));
				ball.setDX(ball.getDX() * -1);
			}
		}

		if (ball.getXCoord() + ballSideLength >= p2.getXCoord()
				&& ball.getXCoord() + ballSideLength <= p2.getXCoord() + paddleWidth) {
			if (ball.getYCoord() < p2.getYCoord() + paddleHeight
					&& ball.getYCoord() + ballSideLength > p2.getYCoord()) {
				ball.setDY((int) (Math
						.sqrt(Math.abs(((ball.getYCoord() + halfBallSideLength) - (p2.getYCoord() + halfPaddleHeight))
								/ (float) ((paddleHeight + ballSideLength) / 2)))
						* Math.signum(((ball.getYCoord() + halfBallSideLength) - (p2.getYCoord() + halfPaddleHeight)))
						* 8));
				ball.setDX(ball.getDX() * -1);
			}
		}

		ball.moveXCoord(ball.getDX());
		ball.moveYCoord(ball.getDY());
	}

	private void reset() {
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		p1.setXCoord(20);
		p1.setYCoord((halfFieldHeight) - (halfPaddleHeight));
		p2.setXCoord(fieldWidth - 20 - paddleWidth);
		p2.setYCoord((halfFieldHeight) - (halfPaddleHeight));

		ball.setXCoord((halfFieldWidth) - (halfBallSideLength));
		ball.setYCoord((halfFieldHeight) - (halfBallSideLength));
		ball.setDX(random.nextInt(2) == 0 ? -4 : 4);
		ball.setDY(random.nextInt(2) == 0 ? -1 : 1);
	}

	private void gameEnd() {
		if (p1Score == 10 || p2Score == 10) {
			gameLoop.stop();
			gameEnd = true;

			if (p1Score == 10) {
				gameOverMsg = new ImageIcon("../img/gameEndP1Win.png").getImage();
			} else {
				gameOverMsg = new ImageIcon("../img/gameEndP2Win.png").getImage();
			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (!twoPlayer) {
			robotPaddleMove();
		}
		paddleMove();
		paddleCollision();
		ballCollision();
		repaint();
	}

	void run() {
		frame.setTitle("pong");
		frame.setResizable(false);
		frame.setBackground(Color.BLACK);
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.add(this);
		frame.pack();
		frame.setVisible(true);

		gameLoop = new Timer(1000 / 60, this);
		gameLoop.start();
	}

	void setTwoPlayer() {
		twoPlayer = true;
	}

}
