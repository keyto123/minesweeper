package principal;

import java.awt.Color;

import javax.swing.JButton;

public class GameButton extends JButton {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private boolean mine;
	private byte adjacentMines;
	private int posx, posy;
	private Color infoColor;

	public GameButton(int posx, int posy, boolean mine) {
		super();
		this.posx = posx;
		this.posy = posy;
		this.mine = mine;
		if (mine) {
			infoColor = Color.BLACK;
		}
	}

	public int getPosX() {
		return posx;
	}

	public int getPosY() {
		return posy;
	}

	public boolean hasMine() {
		return mine;
	}

	public void setMine(boolean mine) {
		this.mine = mine;
	}

	public byte getAdjacentMines() {
		return adjacentMines;
	}

	public void setAdjacentMines(byte mines) {
		this.adjacentMines = mines;
		switch (adjacentMines) {
		case 0:
			infoColor = Color.CYAN;
			break;

		case 1:
			infoColor = Color.WHITE;
			break;

		case 2:
			infoColor = Color.BLUE;
			break;

		case 3:
			infoColor = Color.YELLOW;
			break;

		case 4:
			infoColor = Color.getColor("aa0000");
			break;

		default:
			infoColor = Color.RED;
			break;
		}
	}

	public Color getInfoColor() {
		return infoColor;
	}
}
