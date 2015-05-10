package net.tf2calc.view;

import java.net.URL;
import java.util.ResourceBundle;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.LookAndFeel;
import javax.swing.UIManager;

import com.jgoodies.looks.windows.WindowsLookAndFeel;

public class FrameUtil {
	
	//***CONSTANTS***///
	
	//private static final LookAndFeel LOOK_AND_FEEL_THEME = new AcrylLookAndFeel();
	private static final LookAndFeel LOOK_AND_FEEL_THEME = new WindowsLookAndFeel();
	
	private static final String APPLICATION_ICON = "/tf2_calc_logo_48x48.png";
	
	public static final String TITLE = ResourceBundle.getBundle("application").getString("application.name");
	
	public static final String VERSION = "v" + ResourceBundle.getBundle("application").getString("application.version");
	
	public static final String URL = ResourceBundle.getBundle("application").getString("application.url");
	
	//***CONSTANTS***///
	
	private FrameUtil(){

	}
	
	public static void loadFrameIcon(JFrame argFrame) {
		ImageIcon tmpIcon = getIcon();
		
		if(tmpIcon == null){
			return;
		}
		
		argFrame.setIconImage( tmpIcon.getImage() );
	}
	
	public static ImageIcon getIcon(){
		URL tmmpImagePath = FrameUtil.class.getResource(APPLICATION_ICON);

		if(tmmpImagePath == null){
			return null;
		}

		ImageIcon tmpIcon = new ImageIcon( tmmpImagePath );
		
		return tmpIcon;
	}
	
	public static void setLookAndFeel() { 
		try {
			UIManager.setLookAndFeel(LOOK_AND_FEEL_THEME);
		} catch (Exception  e) {
			e.printStackTrace();
		}
	}
	
}
