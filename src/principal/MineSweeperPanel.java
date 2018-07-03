package principal;

import java.awt.Color;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.ImageIcon;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

public class MineSweeperPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static final int BUTTONWIDTH = 25;
	private static final int BUTTONHEIGHT = 25;

	private Random rand = new Random();

	private MouseListener listener = new MouseAdapter() {
		public void mouseClicked(MouseEvent e) {
			GameButton gb = (GameButton) e.getSource();
			if (!gb.isEnabled()) {
				clickAdjacentButtons(gb);
			} else if (e.getButton() == MouseEvent.BUTTON1) {
				gameButton_clickAction(gb);
			} else {
				if(gb.getIcon() == flagIcon) {
					gb.setIcon(baseIcon);
				} else {
					gb.setIcon(flagIcon);					
				}
			}
		}
	};

	private void gameButton_clickAction(GameButton button) {
		button.setEnabled(false);
		if (button.hasMine()) {
			gameEnd(false);
		} else {
			setButtonInformation(button);
			if (button.getAdjacentMines() == 0) {
				clickAdjacentButtons(button);
			}
		}
		checkWin();
	}

	private void clickAdjacentButtons(GameButton button) {
		Point start = computeStartPoint(button);
		Point end = computeEndPoint(button);

		for (int i = start.x; i <= end.x; i++) {
			for (int j = start.y; j <= end.y; j++) {
				if (buttons[i][j].isEnabled() && buttons[i][j].getIcon() == baseIcon)
					gameButton_clickAction(buttons[i][j]);
			}
		}
	}

	private void checkWin() {
		for (GameButton[] row : buttons) {
			for (GameButton button : row) {
				if (!button.hasMine() && button.isEnabled() || (button.hasMine() && !button.isEnabled())) {
					return;
				}
			}
		}
		gameEnd(true);
	}

	private void gameEnd(boolean win) {
		if (win) {
			winLabel.setText("You won!");
		} else {
			winLabel.setText("you lost!");
		}
		disableAllButtons();
	}

	private void disableAllButtons() {
		for (GameButton[] row : buttons) {
			for (GameButton button : row) {
				button.setEnabled(false);
				setButtonInformation(button);
			}
		}
	}

	private byte checkAdjacentMines(GameButton button) {
		int x = button.getPosX(), y = button.getPosY();
		byte mines = 0;

		Point startPoint = computeStartPoint(button);
		Point endPoint = computeEndPoint(button);

		for (int i = startPoint.x; i <= endPoint.x; i++) {
			for (int j = startPoint.y; j <= endPoint.y; j++) {
				if (i == x && j == y) {
					continue;
				}
				mines += buttons[i][j].hasMine() ? 1 : 0;
			}
		}
		if(mines > 0) {
			button.setDisabledIcon(getIcon(mines + ".png"));
		} else {
			button.setDisabledIcon(null);
		}
		return mines;
	}

	private Point computeStartPoint(GameButton button) {
		int x = button.getPosX(), y = button.getPosY();

		if (x != 0) {
			x--;
		}

		if (y != 0) {
			y--;
		}

		return new Point(x, y);
	}

	private Point computeEndPoint(GameButton button) {
		int x = button.getPosX(), y = button.getPosY();

		if (x != rows - 1) {
			x++;
		}

		if (y != cols - 1) {
			y++;
		}

		return new Point(x, y);
	}

	private int rows = GameConfig.getButtonRows();
	private int cols = GameConfig.getButtonCols();

	private GameButton buttons[][] = new GameButton[rows][cols];
	private JButton restartButton = new JButton();
	private JButton changeDifficult = new JButton();
	private JLabel winLabel = new JLabel("");
	private ImageIcon flagIcon;
	private ImageIcon baseIcon;
	private ImageIcon bombIcon;
	private ImageIcon difficultIcon;
	
	private JFrame frame;

	public MineSweeperPanel(JFrame frame) {
		this.frame = frame;
		this.setLayout(null);
		this.setBounds(0, 0, frame.getWidth(), frame.getHeight());
		this.setBackground(Color.DARK_GRAY);

		baseIcon = getIcon("base.png");
		flagIcon = getIcon("flag.png");
		bombIcon = getIcon("bomb.png");
		difficultIcon = getIcon("difficult.png");
		
		initButtons();
		mapKeys();
		setRestartButton();
		setDifficultChangeButton();
		initAdjacentMines();

		winLabel.setBounds(120, 0, 200, 25);
		winLabel.setForeground(Color.CYAN);
		this.add(winLabel);
		
	}

	private void initButtons() {
		for (int i = 0; i < buttons.length; i++) {
			for (int j = 0; j < buttons[0].length; j++) {
				boolean mine = mineComputing();
				buttons[i][j] = new GameButton(i, j, mine);
				buttons[i][j].setIcon(baseIcon);
				buttons[i][j].setBounds(5 + j * (BUTTONWIDTH + 2), 40 + i * (BUTTONHEIGHT + 2), BUTTONWIDTH,
						BUTTONHEIGHT);
				buttons[i][j].setBorder(null);
				buttons[i][j].addMouseListener(listener);
				this.add(buttons[i][j]);
			}
		}
	}

	private ImageIcon getIcon(String name) {
		ImageIcon icon = null;
		try {
			icon = new ImageIcon(ImageIO.read(getClass().getResource("/" + name)));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return icon;
	}

	private boolean mineComputing() {
		return Math.abs(rand.nextInt(100)) < GameConfig.getMinePercent();
	}

	private void setRestartButton() {
		// Restart Button
		restartButton.setBounds(1, 1, 70, 29);
		restartButton.addActionListener(e -> {
			restartButton_Action();
		});
		restartButton.setBorder(null);
		restartButton.setIcon(getIcon("restart.png"));
		this.add(restartButton);
	}
	
	private void setDifficultChangeButton() {
		changeDifficult.setBounds(300, 0, 105, 29);
		changeDifficult.addActionListener(e -> {
			changeDifficultButton_Action();
		});
		changeDifficult.setBorder(null);
		changeDifficult.setIcon(difficultIcon);
		this.add(changeDifficult);
	}

	private void restartButton_Action() {
		for (GameButton[] row : buttons) {
			for (GameButton button : row) {
				button.setMine(mineComputing());
				button.setIcon(baseIcon);
				button.setEnabled(true);
			}
		}
		initAdjacentMines();
		winLabel.setText("");
	}
	
	private void changeDifficultButton_Action() {
		frame.remove(Principal.game);
		frame.add(Principal.difficulties);
		frame.repaint();
	}
	
	// Must be fixed
	private void mapKeys() {
		InputMap map = this.getInputMap();
		ActionMap actionMap = this.getActionMap();
		
		// R to restart game
		map.put(KeyStroke.getKeyStroke(KeyEvent.VK_R, 0), "r");
		actionMap.put("r", new AbstractAction() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				restartButton_Action();
			}
			
		});
		
		map.put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "escape");
		actionMap.put("escape", new AbstractAction() {

			@Override
			public void actionPerformed(ActionEvent e) {
				changeDifficultButton_Action();
			}
		
		});
	}

	private void initAdjacentMines() {
		for (GameButton[] row : buttons) {
			for (GameButton button : row) {
				button.setAdjacentMines(this.checkAdjacentMines(button));
			}
		}
	}

	private void setButtonInformation(GameButton button) {
		button.setForeground(button.getInfoColor());

		if (button.hasMine()) {
			button.setIcon(bombIcon);
			button.setDisabledIcon(bombIcon);
		}
	}
}
