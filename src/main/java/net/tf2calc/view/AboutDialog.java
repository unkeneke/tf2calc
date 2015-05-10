package net.tf2calc.view;

import java.awt.Font;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;

public class AboutDialog extends JDialog{

	private static final long serialVersionUID = 201209180037L;
	
	public AboutDialog() {
		super();
		
		this.setTitle("About " + FrameUtil.TITLE);
		this.setSize(215, 350);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
		this.setResizable(false);
		this.setAlwaysOnTop(true);

		initContent();
		initEvents();
	}

	private void initContent() {
		MigLayout tmpLayout = new MigLayout("", "[100%, center]", "[]5[]3[]20[]1[]10[]1[]1[]1[]");
		JPanel tmpPanel = new JPanel();
		tmpPanel.setLayout(tmpLayout);
		
		
		JLabel tmpIcon = new JLabel( FrameUtil.getIcon() );
		
		JLabel tmpTitle = new JLabel(FrameUtil.TITLE);
		Font tmpFont = new Font(tmpTitle.getFont().getName(), Font.BOLD, tmpTitle.getFont().getSize() + 3);
		tmpTitle.setFont(tmpFont);
		
		JLabel tmpVersion = new JLabel(FrameUtil.VERSION);
		JLabel tmpMadeBy = new JLabel("Created by: kanaka");
		LinkTextField tmpEmail = new LinkTextField(FrameUtil.URL, FrameUtil.URL);
		
		MigLayout tmpThanksLayout = new MigLayout("", "[]5[]", "[]1[]");
		JPanel tmpThanksPanel = new JPanel();
		tmpThanksPanel.setLayout(tmpThanksLayout);
		
        JLabel tmpTesters = new JLabel("Thanks:");
		JLabel tmpSlowhand = new JLabel("Slowhand");
		JLabel tmpXvt = new JLabel("xygvot");
		
		tmpThanksPanel.add(tmpTesters, "cell 0 0");
		tmpThanksPanel.add(tmpSlowhand, "cell 1 0");
		tmpThanksPanel.add(tmpXvt, "cell 1 1");
		
		MigLayout tmpCredit1Layout = new MigLayout("", "[]5[]", "[]1[]");
		JPanel tmpCredit1Panel = new JPanel();
		tmpCredit1Panel.setLayout(tmpCredit1Layout);
		
		JLabel tmpTF2Label = new JLabel("TF2 Base Logo:");
		LinkTextField tmpLink = new LinkTextField("Balthazar", "http://commons.wikimedia.org/wiki/User:Balthazar");
		
		tmpCredit1Panel.add(tmpTF2Label, "cell 0 0");
		tmpCredit1Panel.add(tmpLink, "cell 1 0");
		
		MigLayout tmpCredit2Layout = new MigLayout("", "[]5[]", "[]1[]");
		JPanel tmpCredit2Panel = new JPanel();
		tmpCredit2Panel.setLayout(tmpCredit2Layout);
		
		JLabel tmpLookAndFeel = new JLabel("Look & Feel:");
		LinkTextField tmpLink2 = new LinkTextField("JGoodies", "http://www.jgoodies.com/");
		//LinkTextArea tmpLink2 = new LinkTextArea("JTattoo", "http://www.jtattoo.net/");
		
		tmpCredit2Panel.add(tmpLookAndFeel, "cell 0 0");
		tmpCredit2Panel.add(tmpLink2, "cell 1 0");
		
		MigLayout tmpCredit3Layout = new MigLayout("", "[]5[]", "[]1[]");
		JPanel tmpCredit3Panel = new JPanel();
		tmpCredit3Panel.setLayout(tmpCredit3Layout);
		
		JLabel tmpLayoutLabel = new JLabel("Layout:");
		LinkTextField tmpLayoutLink = new LinkTextField("MigLayout", "http://www.miglayout.com/");
		
		tmpCredit3Panel.add(tmpLayoutLabel, "cell 0 0");
		tmpCredit3Panel.add(tmpLayoutLink, "cell 1 0");
		
		tmpPanel.add(tmpIcon, "wrap");
		tmpPanel.add(tmpTitle, "wrap");
		tmpPanel.add(tmpVersion, "wrap");
		tmpPanel.add(tmpMadeBy, "wrap");
		tmpPanel.add(tmpEmail, "wrap");
		tmpPanel.add(tmpThanksPanel, "wrap");
		tmpPanel.add(tmpCredit1Panel, "wrap");
		tmpPanel.add(tmpCredit2Panel, "wrap");
		tmpPanel.add(tmpCredit3Panel, "wrap");
		
		this.add(tmpPanel);
	}
		
	private void initEvents(){
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowDeactivated(WindowEvent argEvent) {
				super.windowDeactivated(argEvent);
				JDialog tmpDialog = (JDialog)argEvent.getComponent();
				tmpDialog.setVisible(false);
			}
		});
		
		this.addKeyListener(new KeyAdapter() {
			
			@Override
			public void keyPressed(KeyEvent argEvent) { 
				char tmpKey = argEvent.getKeyChar();
				if(tmpKey == KeyEvent.VK_ESCAPE){
					JDialog tmpDialog = (JDialog)argEvent.getComponent();
					tmpDialog.setVisible(false);
				}
			}
		});
	}
	
	
}
