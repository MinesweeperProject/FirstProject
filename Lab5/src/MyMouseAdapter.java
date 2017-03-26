import java.awt.Color;
import java.awt.Component;
import java.awt.Insets;
import java.awt.Label;
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
	public boolean hasBombAround = false;
	
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
	public void CreatesLabelOnGrid(MyPanel myPanelB, Point Loc, int Number) {
		int xLoc = myPanelB.mouseDownGridX;
		int yLoc = myPanelB.mouseDownGridY;
	     Label l = new Label();
	     l.setText(String.valueOf(Number));
	     l.setSize(29, 29);
	     l.setLocation(xLoc, yLoc);
	     l.setVisible(true);
	     myPanelB.add(l);
	     }

   
	// change to type boolean and add a variable of quanntity of bombs.
	public boolean BombsAround(MyPanel myPanelB,int ClickedInX,int ClickedInY){
		int newGridX ;
		int newGridY;
		int QuantityOfBombsAround = 0;
		hasBombAround = false;
		//FIXX	!!!!
		//If Bottom Left Corner
		if(ClickedInX == 1 && ClickedInY == 9 ){
			 newGridX = ClickedInX ;
			 newGridY = ClickedInY - 1;
			for(int Col = 0;Col < 2; Col++){
				for(int Row = 0; Row < 2; Row++){
				if ((newGridX + Col == ClickedInX) && (newGridY + Row == ClickedInY)){
					
					Row++;
					}
				if( HasBomb(myPanelB,newGridX + Col, newGridY + Row ) == true){
					QuantityOfBombsAround++;
					}
				}
		
			}
		}
		//If Bottom Right Corner
		else if(ClickedInX == 9 && ClickedInY == 9 ){
					 newGridX = ClickedInX - 1;
					 newGridY = ClickedInY - 1;
					for(int Col = 0;Col < 2; Col++){
						for(int Row = 0; Row < 2; Row++){
						if ((newGridX + Col == ClickedInX) && (newGridY + Row == ClickedInY)){
							
							Row++;
							
						}
						if( HasBomb(myPanelB,newGridX + Col, newGridY + Row ) == true){
							QuantityOfBombsAround++;
						}
				}
				
				}
				}
		//If Top Right Corner
				else if(ClickedInX == 9 && ClickedInY == 1 ){
							 newGridX = ClickedInX - 1;
							 newGridY = ClickedInY;
							for(int Col = 0;Col < 2; Col++){
								for(int Row = 0; Row < 2; Row++){
								if ((newGridX + Col == ClickedInX) && (newGridY + Row == ClickedInY)){
									
									Row++;
									
								}
								if( HasBomb(myPanelB,newGridX + Col, newGridY + Row ) == true){
									QuantityOfBombsAround++;
								}
								
						}
						
						
					
						
						}
						}
		//If Top Left Corner
				else if(ClickedInX == 1 && ClickedInY == 1 ){
							 newGridX = ClickedInX;
							 newGridY = ClickedInY;
							for(int Col = 0;Col < 2; Col++){
								for(int Row = 0; Row < 2; Row++){
								if ((newGridX + Col == ClickedInX) && (newGridY + Row == ClickedInY)){
									Row++;
									
								}
								if( HasBomb(myPanelB,newGridX + Col, newGridY + Row ) == true){
									
									QuantityOfBombsAround++;
								}
						}
						
					
						
						}
						}
		//If first column
				else if(ClickedInX == 1){
							 newGridX = ClickedInX;
							 newGridY = ClickedInY -1;
							for(int Col = 0;Col < 2; Col++){
								for(int Row = 0; Row < 3; Row++){
								if ((newGridX + Col == ClickedInX) && (newGridY + Row == ClickedInY)){
									Row++;
									
								}
								if( HasBomb(myPanelB,newGridX + Col, newGridY + Row ) == true){
									
									QuantityOfBombsAround++;
								}
							
						}
						
						
					
						
						}
						}
		//If last column
				else if(ClickedInX == 9 ){
							 newGridX = ClickedInX - 1;
							 newGridY = ClickedInY - 1;
							for(int Col = 0;Col < 2; Col++){
							
							for(int Row = 0; Row < 3; Row++){
								if ((newGridX + Col == ClickedInX) && (newGridY + Row == ClickedInY)){
									Row++;
									
								}
								if( HasBomb(myPanelB,newGridX + Col, newGridY + Row ) == true){
									
									QuantityOfBombsAround++;
								}
							
							
						}
						
						
						
						
						}
						}
		//if Top Row
				else if(ClickedInY == 1 ){
					 newGridX = ClickedInX - 1;
					 newGridY = ClickedInY;
					for(int Col = 0;Col < 3; Col++){
					
					for(int Row = 0; Row < 2; Row++){
						if ((newGridX + Col == ClickedInX) && (newGridY + Row == ClickedInY)){
							
							Row++;
							
						}
						if( HasBomb(myPanelB,newGridX + Col, newGridY + Row ) == true){
							QuantityOfBombsAround++;
						}
						
				}
				
				
				
				
				}
				}
		//if Last Row
				else if(ClickedInY == 9 ){
					 newGridX = ClickedInX - 1;
					 newGridY = ClickedInY - 1;
					for(int Col = 0;Col < 3; Col++){
					
					for(int Row = 0; Row < 2; Row++){
						if ((newGridX + Col == ClickedInX) && (newGridY + Row == ClickedInY)){
							Row++;
							
						}
						if( HasBomb(myPanelB,newGridX + Col, newGridY + Row ) == true){
							QuantityOfBombsAround++;
						}
						
				}
				
					
			
				
				}
				}
		//else on grid
							else{
							 newGridX = ClickedInX - 1;
							 newGridY = ClickedInY - 1;
							for(int Col = 0;Col < 3; Col++){
							
							for(int Row = 0; Row < 3; Row++){
								if ((newGridX + Col == ClickedInX) && (newGridY + Row == ClickedInY)){
									
									Row++;
									
								}
								if( HasBomb(myPanelB,newGridX + Col, newGridY + Row ) == true){
									
									QuantityOfBombsAround++;
								}
							
						}
						
						
						
						
						}
						}
	
		System.out.println("Bombs Around Clicked Grid: " + QuantityOfBombsAround);
		
		if(QuantityOfBombsAround > 0) {
			
			hasBombAround = true;
		}
		return hasBombAround;
		
	
		
		
	}
//public void MarkAround(MyPanel myPanelB,int ClickedInX,int ClickedInY){
//		
//		int newGridX ;
//		int newGridY;
//		int QuantityOfBombsAround = 0;
//		
//		
//		//FIXX	!!!!
//		//If Bottom Left Corner
//		if(ClickedInX == 1 && ClickedInY == 9 ){
//			
//			 newGridX = ClickedInX ;
//			 newGridY = ClickedInY - 1;
//			for(int Col = 0;Col < 2; Col++){
//			
//			for(int Row = 0; Row < 2; Row++){
//				if ((newGridX + Col == ClickedInX) && (newGridY + Row == ClickedInY)){
//					System.out.println("Original Coordinates: " + newGridX + Col + ", " + newGridY + Row);
//					Row++;
//					
//				}
//				if( HasBomb(myPanelB,newGridX + Col, newGridY + Row ) == true){
//					myPanelB.colorArray[newGridX + Col][newGridY + Row] = coveredCell;
//					
//					QuantityOfBombsAround++;
//				}
//				else{
//				myPanelB.colorArray[newGridX + Col][newGridY + Row] = uncoveredCell;
//				System.out.println("Coordinates: " + newGridX + Col + ", " + newGridY + Row);
//				myPanelB.repaint();
//				
//			}
//				
//			
//		}
//		
//		
//		System.out.println("Bombs Around Clicked Grid: " + QuantityOfBombsAround);
//		
//		
//		
//		}
//		}
//		//If Bottom Right Corner
//		else if(ClickedInX == 9 && ClickedInY == 9 ){
//					 newGridX = ClickedInX - 1;
//					 newGridY = ClickedInY - 1;
//					for(int Col = 0;Col < 2; Col++){
//					
//					for(int Row = 0; Row < 2; Row++){
//						if ((newGridX + Col == ClickedInX) && (newGridY + Row == ClickedInY)){
//							System.out.println("Original Coordinates: " + newGridX + Col + ", " + newGridY + Row);
//							Row++;
//							
//						}
//						if( HasBomb(myPanelB,newGridX + Col, newGridY + Row ) == true){
//							myPanelB.colorArray[newGridX + Col][newGridY + Row] = coveredCell;
//							QuantityOfBombsAround++;
//						}
//						else{
//						myPanelB.colorArray[newGridX + Col][newGridY + Row] = uncoveredCell;
//						System.out.println("Coordinates: " + newGridX + Col + ", " + newGridY + Row);
//						myPanelB.repaint();
//						
//					}
//					
//				}
//				
//				
//				System.out.println("Bombs Around Clicked Grid: " + QuantityOfBombsAround);
//				
//				}
//				}
//		//If Top Right Corner
//				else if(ClickedInX == 9 && ClickedInY == 1 ){
//							 newGridX = ClickedInX - 1;
//							 newGridY = ClickedInY;
//							for(int Col = 0;Col < 2; Col++){
//							
//							for(int Row = 0; Row < 2; Row++){
//								if ((newGridX + Col == ClickedInX) && (newGridY + Row == ClickedInY)){
//									System.out.println("Original Coordinates: " + newGridX + Col + ", " + newGridY + Row);
//									Row++;
//									
//								}
//								if( HasBomb(myPanelB,newGridX + Col, newGridY + Row ) == true){
//									myPanelB.colorArray[newGridX + Col][newGridY + Row] = coveredCell;
//									QuantityOfBombsAround++;
//								}
//								else{
//								myPanelB.colorArray[newGridX + Col][newGridY + Row] = uncoveredCell;
//								System.out.println("Coordinates: " + newGridX + Col + ", " + newGridY + Row);
//								myPanelB.repaint();
//								
//							}
//							
//						}
//						
//						
//						System.out.println("Bombs Around Clicked Grid: " + QuantityOfBombsAround);
//						
//						}
//						}
//		//If Top Left Corner
//				else if(ClickedInX == 1 && ClickedInY == 1 ){
//							 newGridX = ClickedInX;
//							 newGridY = ClickedInY;
//							for(int Col = 0;Col < 2; Col++){
//							
//							for(int Row = 0; Row < 2; Row++){
//								if ((newGridX + Col == ClickedInX) && (newGridY + Row == ClickedInY)){
//									System.out.println("Original Coordinates: " + newGridX + Col + ", " + newGridY + Row);
//									Row++;
//									
//								}
//								if( HasBomb(myPanelB,newGridX + Col, newGridY + Row ) == true){
//									myPanelB.colorArray[newGridX + Col][newGridY + Row] = coveredCell;
//									QuantityOfBombsAround++;
//								}
//								else{
//								myPanelB.colorArray[newGridX + Col][newGridY + Row] = uncoveredCell;
//								System.out.println("Coordinates: " + newGridX + Col + ", " + newGridY + Row);
//								myPanelB.repaint();
//								
//							}
//							
//						}
//						
//						
//						System.out.println("Bombs Around Clicked Grid: " + QuantityOfBombsAround);
//						
//						}
//						}
//		//If first column
//				else if(ClickedInX == 1){
//							 newGridX = ClickedInX;
//							 newGridY = ClickedInY -1;
//							for(int Col = 0;Col < 2; Col++){
//							
//							for(int Row = 0; Row < 3; Row++){
//								if ((newGridX + Col == ClickedInX) && (newGridY + Row == ClickedInY)){
//									System.out.println("Original Coordinates: " + newGridX + Col + ", " + newGridY + Row);
//									Row++;
//									
//								}
//								if( HasBomb(myPanelB,newGridX + Col, newGridY + Row ) == true){
//									myPanelB.colorArray[newGridX + Col][newGridY + Row] = coveredCell;
//									QuantityOfBombsAround++;
//								}
//								else{
//								myPanelB.colorArray[newGridX + Col][newGridY + Row] = uncoveredCell;
//								System.out.println("Coordinates: " + newGridX + Col + ", " + newGridY + Row);
//								myPanelB.repaint();
//								
//							}
//							
//						}
//						
//						
//						System.out.println("Bombs Around Clicked Grid: " + QuantityOfBombsAround);
//						
//						}
//						}
//		//If last column
//				else if(ClickedInX == 9 ){
//							 newGridX = ClickedInX - 1;
//							 newGridY = ClickedInY - 1;
//							for(int Col = 0;Col < 2; Col++){
//							
//							for(int Row = 0; Row < 3; Row++){
//								if ((newGridX + Col == ClickedInX) && (newGridY + Row == ClickedInY)){
//									System.out.println("Original Coordinates: " + newGridX + Col + ", " + newGridY + Row);
//									Row++;
//									
//								}
//								if( HasBomb(myPanelB,newGridX + Col, newGridY + Row ) == true){
//									myPanelB.colorArray[newGridX + Col][newGridY + Row] = coveredCell;
//									QuantityOfBombsAround++;
//								}
//								else{
//								myPanelB.colorArray[newGridX + Col][newGridY + Row] = uncoveredCell;
//								System.out.println("Coordinates: " + newGridX + Col + ", " + newGridY + Row);
//								myPanelB.repaint();
//								
//							}
//							
//						}
//						
//						
//						System.out.println("Bombs Around Clicked Grid: " + QuantityOfBombsAround);
//						
//						}
//						}
//		//else on grid
//				else{
//							 newGridX = ClickedInX - 1;
//							 newGridY = ClickedInY - 1;
//							for(int Col = 0;Col < 3; Col++){
//							
//							for(int Row = 0; Row < 3; Row++){
//								if ((newGridX + Col == ClickedInX) && (newGridY + Row == ClickedInY)){
//									System.out.println("Original Coordinates: " + newGridX + Col + ", " + newGridY + Row);
//									Row++;
//									
//								}
//								if( HasBomb(myPanelB,newGridX + Col, newGridY + Row ) == true){
//									myPanelB.colorArray[newGridX + Col][newGridY + Row] = coveredCell;
//									QuantityOfBombsAround++;
//								}
//								else{
//								myPanelB.colorArray[newGridX + Col][newGridY + Row] = uncoveredCell;
//								System.out.println("Coordinates: " + newGridX + Col + ", " + newGridY + Row);
//								myPanelB.repaint();
//								
//							}
//							
//						}
//						
//						
//						System.out.println("Bombs Around Clicked Grid: " + QuantityOfBombsAround);
//						
//						}
//						}
//}
	public void CheckColUpper(MyPanel myPanelB,int ClickedInX, int ClickedInY){
		Color hasNumber = Color.ORANGE;
		
		int newGridX = ClickedInX;
		int newGridY = ClickedInY;
		while(!BombsAround(myPanelB, newGridX,newGridY)){
			myPanelB.colorArray[newGridX][newGridY] = uncoveredCell;
			newGridY--;
			myPanelB.repaint();
			}
		myPanelB.colorArray[newGridX][newGridY] = hasNumber;
		
		}
	public void CheckColDown(MyPanel myPanelB,int ClickedInX, int ClickedInY){
		Color hasNumber = Color.ORANGE;
		
		int newGridX = ClickedInX;
		int newGridY = ClickedInY;
		while(!BombsAround(myPanelB, newGridX,newGridY)){
			myPanelB.colorArray[newGridX][newGridY] = uncoveredCell;
			newGridY++;
			myPanelB.repaint();
			}
		myPanelB.colorArray[newGridX][newGridY] = hasNumber;
		
		}
	public void CheckRowLeft(MyPanel myPanelB,int ClickedInX, int ClickedInY){
		Color hasNumber = Color.ORANGE;
		
		int newGridX = ClickedInX;
		int newGridY = ClickedInY;
		while(!BombsAround(myPanelB, newGridX,newGridY)){
			myPanelB.colorArray[newGridX][newGridY] = uncoveredCell;
			newGridX--;
			myPanelB.repaint();
			}
		myPanelB.colorArray[newGridX][newGridY] = hasNumber;
		
		}
	public void CheckRowRight(MyPanel myPanelB,int ClickedInX, int ClickedInY){
		Color hasNumber = Color.ORANGE;
		
		int newGridX = ClickedInX;
		int newGridY = ClickedInY;
		while(!BombsAround(myPanelB, newGridX,newGridY)){
			myPanelB.colorArray[newGridX][newGridY] = uncoveredCell;
			newGridX++;
			myPanelB.repaint();
			}
		myPanelB.colorArray[newGridX][newGridY] = hasNumber;
		
		}
	public void CheckDiagonalTopLeft(MyPanel myPanelB,int ClickedInX, int ClickedInY){
		Color hasNumber = Color.ORANGE;
		
		int newGridX = ClickedInX;
		int newGridY = ClickedInY;
		while(!BombsAround(myPanelB, newGridX,newGridY)){
			myPanelB.colorArray[newGridX][newGridY] = uncoveredCell;
			newGridX--;
			newGridY--;
			myPanelB.repaint();
			}
		myPanelB.colorArray[newGridX][newGridY] = hasNumber;
		
		}
	public void CheckDiagonalTopRight(MyPanel myPanelB,int ClickedInX, int ClickedInY){
		Color hasNumber = Color.ORANGE;
		
		int newGridX = ClickedInX;
		int newGridY = ClickedInY;
		while(!BombsAround(myPanelB, newGridX,newGridY)){
			myPanelB.colorArray[newGridX][newGridY] = uncoveredCell;
			newGridX++;
			newGridY--;
			myPanelB.repaint();
			}
		myPanelB.colorArray[newGridX][newGridY] = hasNumber;
		
		}
	public void CheckDiagonalBottomLeft(MyPanel myPanelB,int ClickedInX, int ClickedInY){
		Color hasNumber = Color.ORANGE;
		
		int newGridX = ClickedInX;
		int newGridY = ClickedInY;
		while(!BombsAround(myPanelB, newGridX,newGridY)){
			myPanelB.colorArray[newGridX][newGridY] = uncoveredCell;
			newGridX--;
			newGridY++;
			myPanelB.repaint();
			}
		myPanelB.colorArray[newGridX][newGridY] = hasNumber;
		
		}
	public void CheckDiagonalBottomRight(MyPanel myPanelB,int ClickedInX, int ClickedInY){
		Color hasNumber = Color.ORANGE;
		
		int newGridX = ClickedInX;
		int newGridY = ClickedInY;
		while(!BombsAround(myPanelB, newGridX,newGridY)){
			myPanelB.colorArray[newGridX][newGridY] = uncoveredCell;
			newGridX++;
			newGridY++;
			myPanelB.repaint();
			}
		myPanelB.colorArray[newGridX][newGridY] = hasNumber;
		
		}
	public void Expand(MyPanel myPanelB,int ClickedInX, int ClickedInY){
		Color hasNumber = Color.ORANGE;
		int newGridX = ClickedInX;
		int newGridY = ClickedInY;
		if(BombsAround(myPanelB, ClickedInX,ClickedInY) && !HasBomb(myPanelB, ClickedInX,ClickedInY)){
			myPanelB.colorArray[ClickedInX][ClickedInY] = hasNumber;
			myPanelB.repaint();
			
		}
		else if(!BombsAround(myPanelB, ClickedInX,ClickedInY) && !HasBomb(myPanelB, ClickedInX,ClickedInY)){
			CheckColUpper(myPanelB, ClickedInX, ClickedInY);
			CheckColDown(myPanelB, ClickedInX, ClickedInY);
			CheckRowLeft(myPanelB, ClickedInX, ClickedInY);
			CheckRowRight(myPanelB, ClickedInX, ClickedInY);
			CheckDiagonalTopLeft(myPanelB, ClickedInX, ClickedInY);
			CheckDiagonalTopRight(myPanelB, ClickedInX, ClickedInY);
			CheckDiagonalBottomLeft(myPanelB, ClickedInX, ClickedInY);
			CheckDiagonalBottomRight(myPanelB, ClickedInX, ClickedInY);
		}
		
	}
	public void GameOver(MyPanel myPanelB,int ClickedInX, int ClickedInY){
		
		if(HasBomb(myPanelB,ClickedInX,ClickedInY)){
			for( int posArray = 0; posArray < posXBomb.length;posArray++){
				myPanelB.colorArray[posXBomb[posArray]][posYBomb[posArray]] = bombs;
				myPanelB.repaint();
			}
		}
	}
	public boolean HasBomb(MyPanel myPanelB, int ClickedInX,int ClickedInY){
		
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
			
			int quantityCol = 0;
			int quantityRow = 0;
			
			//Create bombs coordinates (BUG: can have similar coordinate. FIX!!!!!)
			createMines(myPanel, quantityCol, quantityRow);
			
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
							
								//Paint randomly the bottom column of the gray cell click it
							}else if(gridY == 0 && gridX != 0){
								
								//Paint diagonal cells when click is on (0, 0)
							}else if (gridX == 0 && gridY == 0){
								
								//Click on last left bottom cell to change center of grid (3x3)
							}else if (gridX == 0 && gridY == 10){
												
							
							}else {
							//On the grid other than on the left column and on the top row:
								if(HasBomb(myPanel,gridX,gridY)){
									GameOver(myPanel,gridX,gridY);
								}
                               BombsAround(myPanel,gridX,gridY);
                               
                               Expand(myPanel,gridX,gridY);
                              
                               
								
								if((myPanel.colorArray[gridX][gridY].equals(coveredCell)) && HasBomb(myPanel,gridX,gridY) == false){
									   
									
									myPanel.colorArray[gridX][gridY] = uncoveredCell;
									myPanel.repaint();
								}
								else{
									if( HasBomb(myPanel,gridX,gridY)){
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
}


	