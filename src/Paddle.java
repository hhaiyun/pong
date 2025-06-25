import java.awt.Graphics;

public class Paddle {

	private int x, y, width, height;

	Paddle(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}

	int getXCoord() {
		return x;
	}

	int getYCoord() {
		return y;
	}

	void setXCoord(int x) {
		this.x = x;
	}

	void setYCoord(int y) {
		this.y = y;
	}

	void moveYCoord(int n) {
		y += n;
	}

	void paint(Graphics g) {
		g.fillRect(x, y, width, height);
	}

}
