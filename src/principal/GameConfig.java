package principal;

public class GameConfig {
	private static int minePercent;
	private static int buttonRows;
	private static int buttonCols;
	
	public static void setConfigurations(int rows, int cols, int minePercent) {
		setButtonRows(rows);
		setButtonCols(cols);
		setMinePercent(minePercent);
	}
	
	private static void setMinePercent(int percent) {
		if(percent < 10) {
			percent = 10;
		} else if(percent > 90) {
			percent = 90;
		}
		minePercent = percent;
	}
	
	public static int getMinePercent() {
		return minePercent;
	}
	
	private static void setButtonRows(int rows) {
		if(rows < 5) {
			rows = 5;
		} else if(rows > 20) {
			rows = 20;
		}
		
		buttonRows = rows;
	}
	
	public static int getButtonRows() {
		return buttonRows;
	}
	
	private static void setButtonCols(int cols) {
		if(cols < 5) {
			cols = 5;
		} else if(cols > 20) {
			cols = 20;
		}
		buttonCols = cols;
	}
	
	public static int getButtonCols() {
		return buttonCols;
	}
	
}
