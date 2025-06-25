import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

public class Main {

	public static void main(String[] args) {
		pong();
	}

	static void pong() {

		Pong pong = new Pong();

		JFrame frame = new JFrame("Pong");
		frame.setResizable(false);
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setBackground(Color.BLACK);
		panel.setPreferredSize(new Dimension(800, 400));
		panel.setFocusable(true);
		panel.addKeyListener(new KeyListener() {
			@Override
			public void keyPressed(KeyEvent e) {
				frame.setVisible(false);
				frame.dispose();
				
				if (e.getKeyCode() == KeyEvent.VK_2) {
					pong.setTwoPlayer();
				}
				
				SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {
						pong.run();
					}
				});
			}

			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
			}

			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
			}
		});

		JLabel imageLabel = new JLabel(new ImageIcon("../img/titleScreen.png"));

		imageLabel.setBounds(0, 0, 800, 400);
		panel.add(imageLabel);

		frame.add(panel);
		frame.pack();
		frame.setVisible(true);

	}

}