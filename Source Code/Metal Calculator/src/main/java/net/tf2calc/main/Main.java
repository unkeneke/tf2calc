package net.tf2calc.main;

import net.tf2calc.view.FrameUtil;
import net.tf2calc.view.MainFrame;


public class Main {

	public static void main(String[] args) {
		FrameUtil.setLookAndFeel();
		
		MainFrame tmpFrame = new MainFrame();

		tmpFrame.startProgram();
		
		//calculatePI();
	}
	
	/** LIKE A BOSS */
	@SuppressWarnings("unused")
	private static void calculatePI(){
		float pi = 1.0f;
		
		for(float i = 3.0f; i <= 319595; i++){
			pi *= -1;
			pi += (1.0f / i);
			i++;
		}

		pi = 4.0f * (pi < 0 ? pi * -1.0f : pi);
		System.out.println(pi);
	}

}
