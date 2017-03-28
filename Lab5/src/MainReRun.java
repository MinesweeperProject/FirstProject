import javax.swing.JFrame;

public class MainReRun {
	public void reRun(){
	JFrame myFrame = new JFrame("MINESWEEPER");
	myFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	myFrame.setLocation(400, 150);
	myFrame.setSize(400, 400);

	MyPanel myPanel = new MyPanel();
	myFrame.add(myPanel);
	
	MyMouseAdapter myMouseAdapter = new MyMouseAdapter();
	myFrame.addMouseListener(myMouseAdapter);

	myFrame.setVisible(true);
	} 
}
