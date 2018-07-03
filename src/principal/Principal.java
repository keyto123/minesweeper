package principal;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class Principal extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static MineSweeperPanel game;
	public static JPanel difficulties;
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> new Principal()); // lambda
	}
	
	public Principal() {
		this.setTitle("MineSweeper");
		this.setResizable(false);
		this.setLayout(null);
		this.setSize(600, 600);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		difficulties = new DifficultiesPanel(this);
		
		this.add(difficulties);
		this.setVisible(true);
	}

}
