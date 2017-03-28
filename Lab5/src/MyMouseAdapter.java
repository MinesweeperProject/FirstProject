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
	private int useFlag = 1;
	private int [][] bombsAround = new int [81][81];
	 private int [][] minesAround = new int [10][10];
	 private boolean [][] empty = new boolean [10][10];

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
			
			//METHOD: Create bombs coordinates (BUG: can have similar coordinate. FIX!!!!!)
			createMines(myPanel, quantityCol, quantityRow);
			//METHOD: Array to mark cells with bombs. Bomb = 1
			markMines(myPanel);
			//METHOD: How many mines around each cell
			minesAroundCells(myPanel, gridX, gridY);
			//METHOD: Is on grid?
			if (onGrid(myPanel, gridX, gridY)) {
				//Released the mouse button on the same cell where it was pressed
				//On the grid from (1,1) to (9,9)	
								 
				emptyCells(myPanel,gridX,gridY);
				
				if(minedCell[gridX][gridY] == 0 && myPanel.colorArray[gridX][gridY] == Color.RED){
					
				}else if(minedCell[gridX][gridY] == 0){
					myPanel.colorArray[gridX][gridY] = uncoveredCell;
					myPanel.bombsAround[gridX][gridY] = minesAround[gridX][gridY];
					myPanel.repaint();					
				}
				
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


//Pone numero de minas alrededor del click en un array para uso futuro (borrar //comentario. Lo puse para que entendieras lo que hace el metodo)



//Metodo para chequear las celdas vacias alrededor del click. El array de empty se //utiliza para expandir. (Comentario puesto para explicar metodo. Borrar.)







public void createMines(MyPanel myPanel, int quantityCol, int quantityRow){
	if(bomb == false){
		for(int i = 1; i <= posXBomb.length; i++){
			do{
				quantityCol = generator.nextInt(10);
				quantityRow = generator.nextInt(10);
			}while(quantityCol == 0 || quantityRow == 0 || myPanel.colorArray[quantityCol][quantityRow].equals(bombs));
			posXBomb[i - 1] = quantityCol;
			posYBomb[i - 1] = quantityRow;
			bomb = true;
			System.out.print(posXBomb[i-1] + "");
			System.out.print(posYBomb[i-1]);
			System.out.print(" ");
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
   }else if(gridX != 9 && gridY != 1 && gridX != 1 && gridY != 9 && gridY != 0){
    if(minesAround[gridX + i][gridY + j] == 0){
     empty[gridX + i][gridY + j] = true;
    }
   }
  }
 }
}
}
}