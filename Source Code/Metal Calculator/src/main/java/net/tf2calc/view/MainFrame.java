package net.tf2calc.view;

import javax.swing.JFrame;


public class MainFrame extends JFrame {

	//***CONSTANTS***//
	
	private static final long serialVersionUID = 201208302340L;
		
	//***CONSTANTS - END***//
	
	
	public MainFrame(){
		super();
		
		initFrame();
	}
	
	private void initFrame(){
		this.setTitle(FrameUtil.TITLE + "  " + FrameUtil.VERSION);
		this.setSize(430, 370);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//this.setResizable(false);

		FrameUtil.loadFrameIcon(this);
		
		ItemConverterPanel tmpItemConverterPanel = new ItemConverterPanel();
		
		this.add(tmpItemConverterPanel);
	}
	
	public void startProgram(){
		this.setVisible(true);
	}

}
