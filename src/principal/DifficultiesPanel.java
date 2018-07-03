package principal;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class DifficultiesPanel extends JPanel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private JButton easy = new JButton("Easy");
	private JButton medium = new JButton("Medium");
	private JButton hard = new JButton("Hard");
	private JButton impossible = new JButton("Don't try");
	
	private JFrame frame;
	
	private Image backgroundImage;
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(backgroundImage, 0, 0, null);
	}
		
	public DifficultiesPanel(JFrame frame) {
		this.frame = frame;
		
		try {
			backgroundImage = ImageIO.read(getClass().getResource("/difficultiesBackground.png"));
		} catch(IOException e) {
			e.printStackTrace();
		}
		
		this.setLayout(null);
		this.setBounds(0, 0, frame.getWidth(), frame.getHeight());
		
		initButtons();
	}
	
	private void initButtons() {
		easy.setBounds(225, 255, 100, 25);
		easy.setBackground(Color.PINK);
		easy.setForeground(Color.GRAY);
		easy.setBorder(null);
		
		medium.setBounds(225, 285, 100, 25);
		medium.setBackground(Color.GREEN);
		medium.setForeground(Color.ORANGE);
		medium.setBorder(null);
		
		hard.setBounds(225, 315, 100, 25);
		hard.setBackground(Color.RED);
		hard.setForeground(Color.BLACK);
		hard.setBorder(null);
		
		impossible.setBounds(225, 345, 100, 25);
		impossible.setBackground(Color.BLACK);
		impossible.setForeground(new Color(0xaa, 0, 0));
		impossible.setBorder(null);
		
		easy.addActionListener(e -> {
			buttonAction(5, 5, 10);
		});
		
		medium.addActionListener(e -> {
			buttonAction(10, 10, 30);
		});
		
		hard.addActionListener(e -> {
			buttonAction(15, 15, 50);
		});
		
		impossible.addActionListener(e -> {
			buttonAction(20, 20, 90);
		});
		
		this.add(easy);
		this.add(medium);
		this.add(hard);
		this.add(impossible);
	}
	
	private void buttonAction(int rows, int cols, int percent) {
		GameConfig.setConfigurations(rows, cols, percent);
		Principal.game = new MineSweeperPanel(frame);
		frame.remove(this);
		frame.add(Principal.game);
		frame.repaint();
	}
}
