package net.tf2calc.view;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.URI;

import javax.swing.BorderFactory;
import javax.swing.JTextField;

public class LinkTextField extends JTextField implements MouseListener {

	private static final long serialVersionUID = 201210041951L;
	
	private static final Color EXITED_LINK_COLOR = Color.BLUE;
	private static final Color ENTERED_LINK_COLOR = Color.decode("0x0066FF");
	
	private String url;
	
	public LinkTextField(){
		super();
		
		url = "http://www.google.com";
		
		initLink();  	
	}

	public LinkTextField(String argText, String argURL){
		super(argText);
		
		url = argURL;
		
		initLink();		
	}
	
	private void initLink(){
		changeLinkColor(EXITED_LINK_COLOR);
		this.setBorder(BorderFactory.createLineBorder(Color.white, 0));
		this.setBackground(null);
		this.setEditable(false);
		this.setFocusable(false);
		
		this.addMouseListener(this);
	}
	
	private void changeLinkColor(Color argColor){
		this.setForeground(argColor);
		this.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, argColor));
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if( !Desktop.isDesktopSupported() ){
            System.err.println("Desktop is not supported (fatal)");
        }
		
        Desktop tmpDesktop = Desktop.getDesktop();
        
        if( !tmpDesktop.isSupported(Desktop.Action.BROWSE) ) {
            System.err.println( "Desktop doesn't support the browse action (fatal)" );
        }
		
		try {
			URI tmpUri = new URI(url);
            tmpDesktop.browse( tmpUri );
		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		this.setCursor( new Cursor(Cursor.HAND_CURSOR) );
		this.changeLinkColor(ENTERED_LINK_COLOR);
	}

	@Override
	public void mouseExited(MouseEvent e) {
		this.setCursor( new Cursor(Cursor.DEFAULT_CURSOR) );
		this.changeLinkColor(EXITED_LINK_COLOR);
		this.setBorder(BorderFactory.createLineBorder(Color.white, 0));
	}
	
	@Override
	public void mousePressed(MouseEvent e) { }

	@Override
	public void mouseReleased(MouseEvent e) { }
	
}
