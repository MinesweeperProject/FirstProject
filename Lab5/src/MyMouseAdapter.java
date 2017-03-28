import java.awt.Color;
import java.awt.Component;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class MyMouseAdapter extends MouseAdapter {
	private Random generator = new Random();
	private int [] posXBomb = new int [10];
	private int [] posYBomb = new int [10];
	private int [][] minedCell = new int [10][10];
	private Color bombs = Color.BLACK;
	private Color uncoveredCell = Color.LIGHT_GRAY;
	private Color coveredCell = Color.WHITE;
	private boolean bomb = false;
	private boolean check = false;
	private int [][] minesAround = new int [10][10];
	private boolean [][] empty = new boolean [10][10];
	private boolean gameOver = false;
	private int countCells = 0;


	public void mousePressed(MouseEvent e) {
		switch (e.getButton()) {
		case 1:		//Left mouse button
			Component c = e.getComponent();
			while (!(c instanceof JFrame)) {
				c = c.getParent();
				if (c == null) {
					return;
				}
			}
			JFrame myFrame = (JFrame) c;
			MyPanel myPanel = (MyPanel) myFrame.getContentPane().getComponent(0);
			Insets myInsets = myFrame.getInsets();
			int x1 = myInsets.left;
			int y1 = myInsets.top;
			e.translatePoint(-x1, -y1);
			int x = e.getX();
			int y = e.getY();
			myPanel.x = x;
			myPanel.y = y;
			myPanel.mouseDownGridX = myPanel.getGridX(x, y);
			myPanel.mouseDownGridY = myPanel.getGridY(x, y);
			myPanel.repaint();
			break;
		case 3:		//Right mouse button
			Component d = e.getComponent();
			while (!(d instanceof JFrame)) {
				d = d.getParent();
				if (d == null) {
					return;
				}
			}
			JFrame frame = (JFrame) d;
			MyPanel panel = (MyPanel) frame.getContentPane().getComponent(0);
			Insets outsideInSets = frame.getInsets();
			int x1Outside = outsideInSets.left;
			int y1Outside = outsideInSets.top;
			e.translatePoint(-x1Outside, -y1Outside);
			d = e.getComponent();
			int xOutside = e.getX();
			int yOutside = e.getY();
			panel.x = xOutside;
			panel.y = yOutside;
			panel.mouseDownGridX = panel.getGridX(xOutside, yOutside);
			panel.mouseDownGridY = panel.getGridY(xOutside, yOutside);
			panel.repaint();
			break;
		default:    //Some other button (2 = Middle mouse button, etc.)
			//Do nothing
			break;
		}
	}
	public void mouseReleased(MouseEvent e) {
		switch (e.getButton()) {
		case 1:		//Left mouse button
			Component c = e.getComponent();
			while (!(c instanceof JFrame)) {
				c = c.getParent();
				if (c == null) {
					return;
				}
			}
			JFrame myFrame = (JFrame)c;
			MyPanel myPanel = (MyPanel) myFrame.getContentPane().getComponent(0);  //Can also loop among components to find MyPanel
			Insets myInsets = myFrame.getInsets();
			int x1 = myInsets.left;
			int y1 = myInsets.top;
			e.translatePoint(-x1, -y1);
			int x = e.getX();
			int y = e.getY();
			myPanel.x = x;
			myPanel.y = y;
			int gridX = myPanel.getGridX(x, y);
			int gridY = myPanel.getGridY(x, y);
			
			int quantityCol = 0;
			int quantityRow = 0;
			
			//METHOD: Create bombs coordinates
			createMines(myPanel, quantityCol, quantityRow);
			
			//METHOD: Array to mark cells with bombs. Bomb = 1. Empty cell = 0
			markMines(myPanel);
			
			//METHOD: How many mines around each cell
			minesAroundCells(myPanel, gridX, gridY);
			
			//Released the mouse button on the same cell where it was pressed
			//METHOD: Is on grid? From (1,1) to (9,9)
			if (onGrid(myPanel, gridX, gridY)) {
				
				//METHOD: GameOver, clicked one mine
				GameOver(myPanel, gridX, gridY, myFrame);
				
				//Put on array the empty cells. Taking on consideration borders.
				emptyCells(myPanel,gridX,gridY);
				
				//EXPAND empty cells around click!!! Taking on consideration borders.
				expandCells(myPanel);
				
				//Cell clicked
				if(minedCell[gridX][gridY] == 0 && myPanel.colorArray[gridX][gridY] == Color.RED){
					
				}else if(minedCell[gridX][gridY] == 0){
					myPanel.colorArray[gridX][gridY] = uncoveredCell;
					myPanel.bombsAround[gridX][gridY] = minesAround[gridX][gridY];
					myPanel.repaint();					
				}
				
				//METHOD: GAME WON!!
				winGame(myPanel, myFrame);
			}
			break;
			
		case 3:		//Right mouse button
			//Use of Flag
			Component d = e.getComponent();
			while (!(d instanceof JFrame)) {
				d = d.getParent();
				if (d == null) {
					return;
				}
			}
			JFrame frame = (JFrame) d;
			MyPanel panel = (MyPanel) frame.getContentPane().getComponent(0);
			Insets flagInSets = frame.getInsets();
			int x1Flag = flagInSets.left;
			int y1Flag = flagInSets.top;
			e.translatePoint(-x1Flag, -y1Flag);
			int xFlag = e.getX();
			int yFlag = e.getY();
			panel.x = xFlag;
			panel.y = yFlag;
			int gridXFlag = panel.getGridX(xFlag, yFlag);
			int gridYFlag = panel.getGridY(xFlag, yFlag);
			Color flag = Color.RED;
			int useFlag = 1;
			if(useFlag == 1){
				if(gridXFlag != -1 && gridXFlag != 0 & gridYFlag != -1 && gridYFlag != 0 && panel.colorArray[gridXFlag][gridYFlag].equals(coveredCell)){
					panel.colorArray[gridXFlag][gridYFlag] = flag;
					panel.repaint();
					useFlag = 2;
				}else if(gridXFlag != -1 && gridXFlag != 0 & gridYFlag != -1 && gridYFlag != 0 && panel.colorArray[gridXFlag][gridYFlag].equals(flag)){
					panel.colorArray[gridXFlag][gridYFlag] = coveredCell;
					panel.repaint();
					useFlag = 1;
				}
			}else if(useFlag == 2){
				if(gridXFlag != -1 && gridXFlag != 0 & gridYFlag != -1 && gridYFlag != 0 && panel.colorArray[gridXFlag][gridYFlag].equals(flag)){
				panel.colorArray[gridXFlag][gridYFlag] = coveredCell;
				panel.repaint();
				useFlag = 1;
				}else if(gridXFlag != -1 && gridXFlag != 0 & gridYFlag != -1 && gridYFlag != 0 && panel.colorArray[gridXFlag][gridYFlag].equals(coveredCell)){
					panel.colorArray[gridXFlag][gridYFlag] = flag;
					panel.repaint();
					useFlag = 2;
				}
			}
			break;
		default:    //Some other button (2 = Middle mouse button, etc.)
			//Do nothing
			break;
		}
	}


	public void createMines(MyPanel myPanel, int quantityCol, int quantityRow){
		if(bomb == false){
			boolean repeat = false;
			for(int i = 1; i <= posXBomb.length; i++){
				do{
					quantityCol = generator.nextInt(10);
					quantityRow = generator.nextInt(10);
					repeat = false;
					for(int check = 1; check < 10; check++){
						if(posXBomb[check-1] == quantityCol && posYBomb[check-1] == quantityRow){
							repeat = true;
						}
					}
				}while(quantityCol == 0 || quantityRow == 0 || repeat == true);
				posXBomb[i - 1] = quantityCol;
				posYBomb[i - 1] = quantityRow;
				bomb = true;
				}
			}
	}
	
	public void markMines(MyPanel myPanel){
		int posBomb = 0;
		if(bomb == true){
			for(int col = 1; col < 10; col++){
				for(int row = 1; row < 10; row++){
					do{
						if(col == posXBomb[posBomb] && row == posYBomb[posBomb]){
							minedCell[col][row] = 1;
						}
						posBomb++;
					}while(posBomb < 10);
					posBomb = 0;
				}
			}
		}
	}
	
	public void minesAroundCells(MyPanel myPanel, int gridX, int gridY){
		int countBomb = 0;
		if(check == false){
		int countCells = 1;
		do{
			int moveOnGridX;
			int moveOnGridY;
		   
			for(int allGridX = 1; allGridX < 10; allGridX++){
				for(int allGridY = 1; allGridY < 10; allGridY++){
					for(int col = -1; col <= 1; col++){
						for(int row = -1; row <= 1; row++){
							if(myPanel.colorArray[allGridX][allGridY].equals(coveredCell)){
								if(minedCell[allGridX][allGridY] == 1){
									countBomb = 9;
								}else if(minedCell[allGridX][allGridY] == 0){
									moveOnGridX = allGridX + col;
									moveOnGridY = allGridY + row;
									//Corner(1,1)
									if(allGridX == 1 && allGridY == 1 && col > -1 && row > -1){
										if(minedCell[moveOnGridX][moveOnGridY] == 1){
											countBomb = countBomb + 1;
										}
									//Left side
									}else if(allGridX == 1 && allGridY != 9 && col > -1){
										if(minedCell[moveOnGridX][moveOnGridY] == 1){
											countBomb = countBomb + 1;
										}
									//Bottom side
									}else if(allGridY == 9 && allGridX != 9 && allGridX != 1 && row < 1){
										if(minedCell[moveOnGridX][moveOnGridY] == 1){
											countBomb = countBomb + 1;
										}
									//Up side
									}else if(allGridY == 1 && allGridX != 9 && allGridX != 1 && row > -1){
										if(minedCell[moveOnGridX][moveOnGridY] == 1){
											countBomb = countBomb + 1;
										}
									//Corner(1,9)
									}else if(allGridX == 1 && allGridY == 9 && col > -1 && row < 1){
										if(minedCell[moveOnGridX][moveOnGridY] == 1){
											countBomb = countBomb + 1;
										}
									//Corner(9,1)
									}else if(allGridX == 9 && allGridY == 1 && col < 1 && row > -1){
										if(minedCell[moveOnGridX][moveOnGridY] == 1){
											countBomb = countBomb + 1;
										}
									//Corner(9,9)
									}else if(allGridX == 9 && allGridY == 9 && col < 1 && row < 1){
										if(minedCell[moveOnGridX][moveOnGridY] == 1){
											countBomb = countBomb + 1;
										}
									//Right side
									}else if(allGridX == 9 && allGridY != 1 && allGridY != 9 && col < 1){
										if(minedCell[moveOnGridX][moveOnGridY] == 1){
											countBomb = countBomb + 1;
										}
									//Rest of Grid
									}else if(allGridX != 9 && allGridX != 1 && allGridY != 9 && allGridY != 1){
										if(minedCell[moveOnGridX][moveOnGridY] == 1){
											countBomb = countBomb + 1;
										}
									}
								}
							}
						}
					}
					minesAround [allGridX][allGridY] = countBomb;
					if(minesAround[allGridX][allGridY] == 0){
		      
					}
					countBomb = 0;
				}
				countCells = countCells + 1;
			}
			check = true;
		}while(countCells < 10);
		}
	}
	
	public boolean onGrid(MyPanel myPanel, int gridX, int gridY){
		if ((myPanel.mouseDownGridX != -1) && (myPanel.mouseDownGridY != -1)) {
			if ((gridX != -1) && (gridY != -1)) {
				if ((myPanel.mouseDownGridX == gridX) && (myPanel.mouseDownGridY == gridY)) {
					return true;
				} 
			}
		} 
		return false;
	}

	public void GameOver(MyPanel myPanel, int gridX, int gridY, JFrame myFrame){
		if(gameOver == false){
			if(minedCell[gridX][gridY] == 1 && myPanel.colorArray[gridX][gridY] != Color.RED){
				gameOver = true;
			}	
		}
		if(gameOver == true){
			for(int col = 1; col < 10; col++){
				for(int row = 1; row < 10; row++){
					if(minedCell[col][row] == 1){
						myPanel.colorArray[col][row] = bombs;
						myPanel.repaint();
					}else if(minedCell[col][row] == 0){
						myPanel.colorArray[col][row] = uncoveredCell;
						myPanel.repaint();
					}
				}
			}
			Object[] options = {"Yes","No, thanks"};
			int selectedOption = JOptionPane.showOptionDialog(myFrame,"      Want to PLAY AGAIN??","BOOM! YOU LOST!",JOptionPane.YES_NO_OPTION, JOptionPane.ERROR_MESSAGE, null, options, options[1]);
			if(selectedOption == JOptionPane.NO_OPTION){
				System.exit(0);
			}else if(selectedOption == JOptionPane.YES_OPTION){
				myFrame.setVisible(false);
				MainReRun playAgain = new MainReRun();
				playAgain.reRun();
			}
		}
	}
	public void emptyCells(MyPanel myPanel, int gridX, int gridY){
		if(minesAround[gridX][gridY] == 0){
			for(int i = -1; i < 2; i++){
				for(int j = -1; j < 2; j++){
					//Left side
					if(gridX == 1 && i > -1 && gridY != 1 && gridY != 9){
						if(minesAround[gridX + i][gridY + j] == 0){
							empty[gridX + i][gridY + j] = true;
						}
					//Right Side
					}else if(gridX == 9 && i < 1 && gridY != 1 && gridY != 9){
						if(minesAround[gridX + i][gridY + j] == 0){
							empty[gridX + i][gridY + j] = true;
						}
					//Up Side
					}else if(gridY == 1 && j > -1 && gridX != 1 && gridX != 9){
						if(minesAround[gridX + i][gridY + j] == 0){
							empty[gridX + i][gridY + j] = true;
						}
					//Bottom Side
					}else if(gridY == 9 && j < 1 && gridX != 1 && gridX != 9){
						if(minesAround[gridX + i][gridY + j] == 0){
							empty[gridX + i][gridY + j] = true;
						}
						//Corner(1,1)
					}else if(gridX == 1 && gridY == 1 && i > -1 && j > -1){
						if(minesAround[gridX + i][gridY + j] == 0){
							empty[gridX + i][gridY + j] = true;
						}
					//Corner(1,9)
					}else if(gridX == 1 && gridY == 9 && i > -1 && j < 1){
						if(minesAround[gridX + i][gridY + j] == 0){
							empty[gridX + i][gridY + j] = true;
						}
					//Corner(9,1)
					}else if(gridX == 9 && gridY == 1 && i < 1 && j > -1){
						if(minesAround[gridX + i][gridY + j] == 0){
							empty[gridX + i][gridY + j] = true;
						}
					//Corner(9,9)
					}else if(gridX == 9 && gridY == 9 && i < 1 && j < 1){
						if(minesAround[gridX + i][gridY + j] == 0){
							empty[gridX + i][gridY + j] = true;
						}
					//Rest of Grid
					}else if(gridX != 9 && gridY != 1 && gridX != 1 && gridY != 9 && gridY != 0 && gridX != 0){
						if(minesAround[gridX + i][gridY + j] == 0){
							empty[gridX + i][gridY + j] = true;
						}
					}
				}
			}
		}
	}
	
	public void expandCells (MyPanel myPanel){
		for(int check = 1; check < 10; check++){
			for(int fullGridX = 1; fullGridX < 10; fullGridX++){
				for(int fullGridY = 1; fullGridY < 10; fullGridY++){
					if(empty[fullGridX][fullGridY] == true){
						for(int i = -1; i < 2; i++){
							for(int j = -1; j < 2; j++){
								//Left side
								if(fullGridX == 1 && i > -1 && fullGridY != 1 && fullGridY != 9){
									myPanel.colorArray[fullGridX + i][fullGridY + j] = uncoveredCell;
									myPanel.bombsAround[fullGridX + i][fullGridY + j] = minesAround[fullGridX + i][fullGridY + j];
									myPanel.repaint();
									if(minesAround[fullGridX + i][fullGridY + j] == 0){
										empty[fullGridX + i][fullGridY + j] = true;
									}
								//Right Side
								}else if(fullGridX == 9 && i < 1 && fullGridY != 1 && fullGridY != 9){
									myPanel.colorArray[fullGridX + i][fullGridY + j] = uncoveredCell;
									myPanel.bombsAround[fullGridX + i][fullGridY + j] = minesAround[fullGridX + i][fullGridY + j];
									myPanel.repaint();
									if(minesAround[fullGridX + i][fullGridY + j] == 0){
										empty[fullGridX + i][fullGridY + j] = true;
									}
								//Up side
								}else if(fullGridY == 1 && j > -1 && fullGridX != 1 && fullGridX != 9){
									myPanel.colorArray[fullGridX + i][fullGridY + j] = uncoveredCell;
									myPanel.bombsAround[fullGridX + i][fullGridY + j] = minesAround[fullGridX + i][fullGridY + j];
									myPanel.repaint();
									if(minesAround[fullGridX + i][fullGridY + j] == 0){
										empty[fullGridX + i][fullGridY + j] = true;
									}
								//Bottom side
								}else if(fullGridY == 9 && j < 1 && fullGridX != 1 && fullGridX != 9){
									myPanel.colorArray[fullGridX + i][fullGridY + j] = uncoveredCell;
									myPanel.bombsAround[fullGridX + i][fullGridY + j] = minesAround[fullGridX + i][fullGridY + j];
									myPanel.repaint();
									if(minesAround[fullGridX + i][fullGridY + j] == 0){
										empty[fullGridX + i][fullGridY + j] = true;
									}
								//Corner(1,1)
								}else if(fullGridX == 1 && fullGridY == 1 && i > -1 && j > -1){
									myPanel.colorArray[fullGridX + i][fullGridY + j] = uncoveredCell;
									myPanel.bombsAround[fullGridX + i][fullGridY + j] = minesAround[fullGridX + i][fullGridY + j];
									myPanel.repaint();
									if(minesAround[fullGridX + i][fullGridY + j] == 0){
										empty[fullGridX + i][fullGridY + j] = true;
									}
								//Corner(1,9)
								}else if(fullGridX == 1 && fullGridY == 9 && i > -1 && j < 1){
									myPanel.colorArray[fullGridX + i][fullGridY + j] = uncoveredCell;
									myPanel.bombsAround[fullGridX + i][fullGridY + j] = minesAround[fullGridX + i][fullGridY + j];
									myPanel.repaint();
									if(minesAround[fullGridX + i][fullGridY + j] == 0){
										empty[fullGridX + i][fullGridY + j] = true;
									}
								//Corner(9,1)
								}else if(fullGridX == 9 && fullGridY == 1 && i < 1 && j > -1){
									myPanel.colorArray[fullGridX + i][fullGridY + j] = uncoveredCell;
									myPanel.bombsAround[fullGridX + i][fullGridY + j] = minesAround[fullGridX + i][fullGridY + j];
									myPanel.repaint();
									if(minesAround[fullGridX + i][fullGridY + j] == 0){
										empty[fullGridX + i][fullGridY + j] = true;
									}
								//Corner(9,9)
								}else if(fullGridX == 9 && fullGridY == 9 && i < 1 && j < 1){
									myPanel.colorArray[fullGridX + i][fullGridY + j] = uncoveredCell;
									myPanel.bombsAround[fullGridX + i][fullGridY + j] = minesAround[fullGridX + i][fullGridY + j];
									myPanel.repaint();
									if(minesAround[fullGridX + i][fullGridY + j] == 0){
										empty[fullGridX + i][fullGridY + j] = true;
									}
								//Rest of Grid
								}else if(fullGridX != 9 && fullGridY != 1 && fullGridX != 1 && fullGridY != 9){
									myPanel.colorArray[fullGridX + i][fullGridY + j] = uncoveredCell;
									myPanel.bombsAround[fullGridX + i][fullGridY + j] = minesAround[fullGridX + i][fullGridY + j];
									myPanel.repaint();
									if(minesAround[fullGridX + i][fullGridY + j] == 0){
										empty[fullGridX + i][fullGridY + j] = true;
									}
								}
							}
						}
					}
				}
			}
		}
	}
	
	public void winGame(MyPanel myPanel, JFrame myFrame){
		int sumCount = 0;
		for(int col = 1; col < 10; col++){
			for(int row = 1; row < 10; row++){
				if(myPanel.colorArray[col][row] == uncoveredCell && myPanel.colorArray[col][row] != Color.RED){
					countCells = countCells + 1;
				}
			}
			sumCount = sumCount + countCells;
			countCells = 0;
			if(sumCount > 70){
				for(int colX = 1; colX < 10; colX++){
					for(int rowY = 1; rowY < 10; rowY++){
						if(minedCell[colX][rowY] == 1){
							myPanel.colorArray[colX][rowY] = bombs;
							myPanel.repaint();
						}
					}
				}
			}
			if(sumCount > 70 && gameOver == false){
				Object[] options = {"Yes","No, thanks"};
				int selectedOption = JOptionPane.showOptionDialog(myFrame,"      Want to PLAY AGAIN??","NICE GAME! YOU WON!",JOptionPane.YES_NO_OPTION, JOptionPane.ERROR_MESSAGE, null, options, options[1]);
				if(selectedOption == JOptionPane.NO_OPTION){
					System.exit(0);
				}else if(selectedOption == JOptionPane.YES_OPTION){
					myFrame.setVisible(false);
					MainReRun playAgain = new MainReRun();
					playAgain.reRun();
				}
			}
		}
	}

}