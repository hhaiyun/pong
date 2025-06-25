import java.awt.Graphics;

public class Ball {

	private int x, y, dx, dy, sideLength;

	Ball(int x, int y, int dx, int dy, int sideLength) {
		this.x = x;
		this.y = y;
		this.dx = dx;
		this.dy = dy;
		this.sideLength = sideLength;
	}

	int getXCoord() {
		return x;
	}

	int getYCoord() {
		return y;
	}

	int getDX() {
		return dx;
	}

	int getDY() {
		return dy;
	}

	void setXCoord(int x) {
		this.x = x;
	}

	void setYCoord(int y) {
		this.y = y;
	}

	void setDX(int dx) {
		this.dx = dx;
	}

	void setDY(int dy) {
		this.dy = dy;
	}

	void moveXCoord(int n) {
		x += n;
	}

	void moveYCoord(int n) {
		y += n;
	}

	void paint(Graphics g) {
		g.fillRect(x, y, sideLength, sideLength);
	}

}
