import java.awt.Color;
import java.awt.Component;
import java.awt.Insets;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;

import javax.swing.JFrame;

public class MyMouseAdapter extends MouseAdapter {
	private Random generator = new Random();
	public int [] posXBomb = new int [10];
	public int [] posYBomb = new int [10];
	public Point [][] BombsLoc = new Point [10][10];
	public Color bombs = Color.BLACK;
	public Color uncoveredCell = Color.LIGHT_GRAY;
	public Color coveredCell = Color.WHITE;
	public boolean bomb = false;
	public int useFlag = 1;
	public boolean hasBomb = false;
	int QuantityOfBombsAround = 0;
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
	public int BombsAround(MouseEvent e,int ClickedInX,int ClickedInY){
		Component e2 = e.getComponent();
		JFrame myFrameB = (JFrame)e2;
		MyPanel myPanelB = (MyPanel) myFrameB.getContentPane().getComponent(0);  //Can also loop among components to find MyPanel
		Insets myInsetsB = myFrameB.getInsets();
		int x1 = myInsetsB.left;
		int y1 = myInsetsB.top;
		e.translatePoint(-x1, -y1);
		int x = e.getX();
		int y = e.getY();
		myPanelB.x = x;
		myPanelB.y = y;
		int gridX = myPanelB.getGridX(x, y);
		int gridY = myPanelB.getGridY(x, y);
		int centerx = ClickedInX;
		int centery = ClickedInY;
		int newGridX = ClickedInX - 1;
		int newGridY = ClickedInY -1;
		//FIXX	!!!!
		//If X=0 and Y =0
		
		
		System.out.println(QuantityOfBombsAround);
		return QuantityOfBombsAround;
	}
	
	public boolean HasBomb(MouseEvent e, int ClickedInX,int ClickedInY){
		Component e2 = e.getComponent();
		JFrame myFrameB = (JFrame)e2;
		MyPanel myPanelB = (MyPanel) myFrameB.getContentPane().getComponent(0);  //Can also loop among components to find MyPanel
		Insets myInsetsB = myFrameB.getInsets();
		int x1 = myInsetsB.left;
		int y1 = myInsetsB.top;
		e.translatePoint(-x1, -y1);
		int x = e.getX();
		int y = e.getY();
		myPanelB.x = x;
		myPanelB.y = y;
		int gridX = myPanelB.getGridX(x, y);
		int gridY = myPanelB.getGridY(x, y);
		hasBomb = false;
		for( int posArray = 0; posArray < posXBomb.length;posArray++){
			for (int i = 1; i <=1 ; i++){
				if(myPanelB.colorArray[ClickedInX][ClickedInY].equals(coveredCell)){
					
					if(ClickedInX == posXBomb[posArray] && ClickedInY == posYBomb[posArray]){
						
						hasBomb = true;
						
					}
				}
			}
		}
		System.out.println(hasBomb);
		return hasBomb;
		
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
			
			int quantityCol;
			int quantityRow;
			
			//Create bombs coordinates (BUG: can have similar coordinate. FIX!!!!!)
			if(bomb == false){
			for(int i = 1; i <= posXBomb.length; i++){
				do{
					quantityCol = generator.nextInt(10);
					quantityRow = generator.nextInt(10);
				}while(quantityCol == 0 || quantityRow == 0 || myPanel.colorArray[quantityCol][quantityRow].equals(bombs));
				posXBomb[i - 1] = quantityCol;
				posYBomb[i - 1] = quantityRow;
				//BombsLoc [i - 1][i - 1] = [ quantityCol] [ quantityRow];
				bomb = true;
				System.out.print(posXBomb[i-1] + "");
				System.out.print(posYBomb[i-1]);
				System.out.print(" ");
				}
			}
			
			if ((myPanel.mouseDownGridX == -1) || (myPanel.mouseDownGridY == -1)) {
				//Had pressed outside
				//Do nothing
			} else {
				if ((gridX == -1) || (gridY == -1)) {
					//Is releasing outside
					//Do nothing
				} else {
					if ((myPanel.mouseDownGridX != gridX) || (myPanel.mouseDownGridY != gridY)) {
						//Released the mouse button on a different cell where it was pressed
						//Do nothing
					} else {
						//Released the mouse button on the same cell where it was pressed
						//On the left column and on the top row... do nothing
						if ((gridX == 0) || (gridY == 0)) {
							//Paint randomly the row of the right gray cell click it
							if(gridX == 0 && gridY !=0 && gridY != 10){
								for(int i = 1; i < 10; i++){
									Color newColor = null;
									do{
									switch (generator.nextInt(5)) {
									case 0:
										newColor = Color.YELLOW;
										break;
									case 1:
										newColor = Color.RED;
										break;
									case 2:
										newColor = Color.BLUE;
										break;
									case 3:
										newColor = new Color(0x964B00);   //Brown (from http://simple.wikipedia.org/wiki/List_of_colors)
										break;
									case 4:
										newColor = new Color(0xB57EDC);   //Lavender (from http://simple.wikipedia.org/wiki/List_of_colors)
										break;
									}
									}while(myPanel.colorArray[myPanel.mouseDownGridX + i][myPanel.mouseDownGridY].equals(newColor));
									myPanel.colorArray[myPanel.mouseDownGridX + i][myPanel.mouseDownGridY] = newColor;
									myPanel.repaint();
								}
								//Paint randomly the bottom column of the gray cell click it
							}else if(gridY == 0 && gridX != 0){
								for(int i = 1; i < 10; i++){
									Color newColor = null;
									do{
									switch (generator.nextInt(5)) {
									case 0:
										newColor = Color.YELLOW;
										break;
									case 1:
										newColor = Color.RED;
										break;
									case 2:
										newColor = Color.BLUE;
										break;
									case 3:
										newColor = new Color(0x964B00);   //Brown (from http://simple.wikipedia.org/wiki/List_of_colors)
										break;
									case 4:
										newColor = new Color(0xB57EDC);   //Lavender (from http://simple.wikipedia.org/wiki/List_of_colors)
										break;
									}
									}while(myPanel.colorArray[myPanel.mouseDownGridX][myPanel.mouseDownGridY + i].equals(newColor));
									myPanel.colorArray[myPanel.mouseDownGridX][myPanel.mouseDownGridY + i] = newColor;
									myPanel.repaint();
								}
								//Paint diagonal cells when click is on (0, 0)
							}else if (gridX == 0 && gridY == 0){
								for(int i = 1; i < 10; i++){
									Color newColor = null;
									do{
									switch (generator.nextInt(5)) {
									case 0:
										newColor = Color.YELLOW;
										break;
									case 1:
										newColor = Color.RED;
										break;
									case 2:
										newColor = Color.BLUE;
										break;
									case 3:
										newColor = new Color(0x964B00);   //Brown (from http://simple.wikipedia.org/wiki/List_of_colors)
										break;
									case 4:
										newColor = new Color(0xB57EDC);   //Lavender (from http://simple.wikipedia.org/wiki/List_of_colors)
										break;
									}
									}while(myPanel.colorArray[myPanel.mouseDownGridX+i][myPanel.mouseDownGridY+i].equals(newColor));
									myPanel.colorArray[myPanel.mouseDownGridX + i][myPanel.mouseDownGridY + i] = newColor;
									myPanel.repaint();
								}
								//Click on last left bottom cell to change center of grid (3x3)
							}else if (gridX == 0 && gridY == 10){
								for(int moveOnCol = 4; moveOnCol <=6; moveOnCol++){
									for(int moveOnRow = 4; moveOnRow <= 6; moveOnRow++){
										Color newColor = null;
										do{
										switch (generator.nextInt(5)) {
										case 0:
											newColor = Color.YELLOW;
											break;
										case 1:
											newColor = Color.MAGENTA;
											break;
										case 2:
											newColor = Color.BLACK;
											break;
										case 3:
											newColor = new Color(0x964B00);   //Brown (from http://simple.wikipedia.org/wiki/List_of_colors)
											break;
										case 4:
											newColor = new Color(0xB57EDC);   //Lavender (from http://simple.wikipedia.org/wiki/List_of_colors)
											break;
										}
										}while(myPanel.colorArray[gridX + moveOnCol][gridY - moveOnRow].equals(newColor));
										myPanel.colorArray[gridX + moveOnCol][gridY - moveOnRow] = newColor;
										myPanel.repaint();
									}
								}				
							}
							}else {
							//On the grid other than on the left column and on the top row:
                               HasBomb(e,gridX,gridY);
                               BombsAround(e,gridX,gridY);
//								for(int posArray = 0; posArray < posXBomb.length; posArray++){
//									
//									
//									for(int i = 1; i <= 1; i++){
//										if(myPanel.colorArray[gridX][gridY].equals(coveredCell)){
//											if(gridX == posXBomb[posArray] && gridY == posYBomb[posArray]){
//												myPanel.colorArray[gridX][gridY] = bombs;
//												HasBomb(e,gridX,gridY);
//												System.out.println(hasBomb);
//												myPanel.repaint();
//											}
//										}
//									}
//									
//								}
								
								if((myPanel.colorArray[gridX][gridY].equals(coveredCell)) && HasBomb(e,gridX,gridY) == false){
									   
									
									myPanel.colorArray[gridX][gridY] = uncoveredCell;
									myPanel.repaint();
								}
								else{
									if( HasBomb(e,gridX,gridY)){
									myPanel.colorArray[gridX][gridY] = bombs;
									myPanel.repaint();
									}
								}
				
								
								
								
								}
							}
						}
					}
			//gridX != posXBomb[posArray] && gridY != posYBomb[posArray]
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
}


	