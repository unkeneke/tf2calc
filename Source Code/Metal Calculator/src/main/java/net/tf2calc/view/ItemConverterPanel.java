package net.tf2calc.view;

import java.awt.AWTEvent;
import java.awt.Color;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.AWTEventListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import net.miginfocom.swing.MigLayout;
import net.tf2calc.controller.ItemConverterController;
import net.tf2calc.controller.TF2Items;

public class ItemConverterPanel extends JPanel{
	
	//***CONSTANTS***//

	private static final long serialVersionUID = 201209180019L;
	
	private static final String RIGHT_ARROW = "/right_arrow.png";
	
	private static final int TEXT_FIELD_LENGTH = 9;
	
	//***CONSTANTS - END***//
	
	
	
	//***VARIABLES***//
	
	private JTextField textFieldKeysBaseValue;
	
	private JTextField textFieldFromValue;
	private JTextArea textAreaResultValue;
	
	private JComboBox<TF2Items> comboBoxFrom;
	private JComboBox<TF2Items> comboBoxTo;
	
	private JButton buttonCalculate;
	
	private AboutDialog aboutDialog;
	
	//***VARIABLES - END***//
	
	
	public ItemConverterPanel(){
		super();
		
		initPanel();
	}
	
	private void initPanel(){
		MigLayout tmpLayout = new MigLayout("wrap 1", "[left, fill]", "[]5[]");
		this.setLayout(tmpLayout);
		
		initComponents();
		initEvents();
	}
	
	private void initComponents(){
		this.add( initKeyValueComponents() );
		this.add( initConvertorComponents() );
	}
	
	private JPanel initKeyValueComponents(){
		JLabel tmpKeyValueLabel = new JLabel("Key value:");
		
		textFieldKeysBaseValue = new JTextField(7);
		textFieldKeysBaseValue.setHorizontalAlignment(JTextField.RIGHT);
		textFieldKeysBaseValue.setDocument( new JTextFieldLimiter(9) );
		textFieldKeysBaseValue.setText("0.00");
		
		invalidateField(textFieldKeysBaseValue);
		
		setTextFieldKeysBaseValueEvents();
		
		MigLayout tmpLayout = new MigLayout("","[][][][100%]","");
		JPanel tmpPanel = new JPanel();
		tmpPanel.setLayout(tmpLayout);
				
		JLabel tmpRefLabel = new JLabel("refined");
		
		JButton tmpButton = new JButton("?");
		initAboutButton(tmpButton);
				
		tmpPanel.add(tmpKeyValueLabel);
		tmpPanel.add(textFieldKeysBaseValue, "");
		tmpPanel.add(tmpRefLabel, "");
		tmpPanel.add(tmpButton, "span, gap push, wrap");
		tmpPanel.add( createHelpTextArea(), "span 3");
		
		
		return tmpPanel;
	}
	
	private void initAboutButton(JButton argButton){
		Font tmpFont = new Font(argButton.getFont().getName(), Font.BOLD, argButton.getFont().getSize() + 3);
		argButton.setFont(tmpFont);
		
		argButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(aboutDialog == null){
					aboutDialog = new AboutDialog();
				}
				
				if( !aboutDialog.isShowing() ){
					aboutDialog.setLocationRelativeTo(null);
					aboutDialog.setVisible(true);
				}
			}
		});
	}
	
	private void setTextFieldKeysBaseValueEvents(){
		textFieldKeysBaseValue.addKeyListener(new KeyAdapter() {
		
			@Override
			public void keyTyped(KeyEvent argKeyEvent) {
				isTypedCharValid(argKeyEvent, true, hasPeriod(textFieldKeysBaseValue.getText()) );
			}

			@Override
			public void keyReleased(KeyEvent argKeyEvent) {
				if( ItemConverterController.isValidKeyValue(textFieldKeysBaseValue.getText()) ){
					validateField(textFieldKeysBaseValue);
				}else{
					invalidateField(textFieldKeysBaseValue);
				}
			}
		});
	}
	
	private JTextArea createHelpTextArea(){
		JTextArea tmpTextArea = new JTextArea();
		
		tmpTextArea.setText("1 refined = 3 reclaimed = 9 scrap\n" +
				            "0.33 ref = 1 rec = 3 scrap\n" +
				            "0.11 ref = 1 scrap = 2 weapons\n" +
				            "0.055 ref = 1 weapon\n\n" +
				            "E.g: 3 ref + 2 scraps + 1 weapon = 3.275 ref  ");
		
		tmpTextArea.setEditable(false);
		tmpTextArea.setBackground(Color.WHITE);
		
		return tmpTextArea;
	}
	
	private JPanel initConvertorComponents(){
		textFieldFromValue = new JTextField(10);
		textFieldFromValue.setHorizontalAlignment(JTextField.RIGHT);
		textFieldFromValue.setDocument( new JTextFieldLimiter(TEXT_FIELD_LENGTH) );
		textFieldFromValue.setText("0");
		
		setTextFieldFromValueEvents();
		
		textAreaResultValue = new JTextArea();
		textAreaResultValue.setWrapStyleWord(true);
		textAreaResultValue.setLineWrap(true);
		
		JScrollPane tmpAreaScrollPane = new JScrollPane(textAreaResultValue);
		tmpAreaScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		tmpAreaScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		
		URL tmmpImagePath = getClass().getResource(RIGHT_ARROW);
	
		ImageIcon tmpIcon = new ImageIcon( tmmpImagePath );
		JLabel tmpLabelTo = new JLabel(tmpIcon);
		
		comboBoxFrom = new JComboBox<TF2Items>(TF2Items.values());
		comboBoxTo = new JComboBox<TF2Items>(TF2Items.values());
		
		initFromComboBoxEvents();
		
		MigLayout tmpLayout = new MigLayout("wrap 5", "", "[]10[fill, 100%]");
		JPanel tmpPanelConvertor = new JPanel();
		tmpPanelConvertor.setLayout(tmpLayout);
		
		initCalculateButton();
		
		tmpPanelConvertor.add(textFieldFromValue);
		tmpPanelConvertor.add(comboBoxFrom);
		tmpPanelConvertor.add(tmpLabelTo);
		tmpPanelConvertor.add(comboBoxTo);
		tmpPanelConvertor.add(buttonCalculate);
		tmpPanelConvertor.add(tmpAreaScrollPane, "span, width 100%, height 100%");
		
		return tmpPanelConvertor;
	}
	
	private void initFromComboBoxEvents(){
		comboBoxFrom.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent argEvent) {
				TF2Items tmpItem = (TF2Items)argEvent.getItem();
				
				if( argEvent.getStateChange() != ItemEvent.SELECTED ){
					return;
				}
				
				validateField(textFieldFromValue);
				
				if(tmpItem.compareTo(TF2Items.REFINED) != 0){
					double tmpTextValue = TF2Items.parseStringToDouble( textFieldFromValue.getText() );
					String tmpIntegerPart = String.valueOf( TF2Items.getDoubleIntegerPart(tmpTextValue) );
					textFieldFromValue.setText(tmpIntegerPart);
					return;
				}
				
				String tmpSuffix = ".00";
				String tmpTextValue = textFieldFromValue.getText();
				
				int tmpSpace = TEXT_FIELD_LENGTH - textFieldFromValue.getText().length(); 
				
				if( tmpSpace <= tmpSuffix.length() ){
					tmpTextValue += tmpSuffix.substring(0, tmpSpace);
				}else{
					tmpTextValue += tmpSuffix;
				}
				
				textFieldFromValue.setText(tmpTextValue);
			}
		});
	}
	
	private void setTextFieldFromValueEvents(){
		textFieldFromValue.addKeyListener(new KeyAdapter() {

			@Override
			public void keyTyped(KeyEvent argKeyEvent) {
				TF2Items tmpSelected = (TF2Items)comboBoxFrom.getSelectedItem();
				
				boolean tmpIsRefined = tmpSelected.compareTo(TF2Items.REFINED) == 0; 
				
				isTypedCharValid(argKeyEvent, tmpIsRefined, hasPeriod(textFieldFromValue.getText()) );
			}

			@Override
			public void keyReleased(KeyEvent argKeyEvent) {
				if(ItemConverterController.isValidFromRefValue(textFieldFromValue.getText())){
					validateField(textFieldFromValue);
				}else{
					invalidateField(textFieldFromValue);
				}
			}
		});
	}
	
	private boolean hasPeriod(String argText){
		String tmpRegex = "[.]";
		
		Pattern tmpPattern = Pattern.compile(tmpRegex);
		
		Matcher tmpMatcher = tmpPattern.matcher(argText);
		
		boolean tmpResult = tmpMatcher.find();
				
		return tmpResult;
	}
	
	private boolean isTypedCharValid(KeyEvent argKeyEvent, boolean argAcceptPeriod, boolean argHasPeriod){
		if(argKeyEvent == null){
			return false;
		}
		
		boolean tmpHasNoProblems = true;
		
		char tmpCharPressed = argKeyEvent.getKeyChar();
		if ( !((tmpCharPressed >= '0') && (tmpCharPressed <= '9') ||
				(tmpCharPressed == KeyEvent.VK_BACK_SPACE) ||
				(tmpCharPressed == KeyEvent.VK_DELETE)) ) {
			tmpHasNoProblems = false;
		}
		
		if ( argAcceptPeriod && tmpCharPressed == KeyEvent.VK_PERIOD && !argHasPeriod) {
			tmpHasNoProblems = true;
		}
		
		if(!tmpHasNoProblems && tmpCharPressed != KeyEvent.VK_ENTER){
			getToolkit().beep();
			argKeyEvent.consume();
		}
		
		return tmpHasNoProblems;
	}
	
	private void initCalculateButton(){
		buttonCalculate = new JButton("Calculate");
		
		buttonCalculate.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent argAction) {
				doConversion();
			}
		});
	}
	
	private void invalidateField(JTextField argTextField){
		argTextField.setBackground(Color.PINK);
	}
	
	private void validateField(JTextField argTextField){
		argTextField.setBackground(Color.WHITE);
	}
	
	private void initEvents() {
		Toolkit tmpKit = Toolkit.getDefaultToolkit();
		
		tmpKit.addAWTEventListener( new AWTEventListener() {
			
			@Override
			public void eventDispatched(AWTEvent argEvent) {
				if(argEvent instanceof KeyEvent){
					KeyEvent tmpKeyEvent = (KeyEvent)argEvent;
					
					char tmpChar = tmpKeyEvent.getKeyChar();
					if(tmpChar == KeyEvent.VK_ENTER){
						doConversion();
					}
				}
			}
		}, AWTEvent.KEY_EVENT_MASK);
	}

	private void doConversion(){
		TF2Items tmpFromValue = (TF2Items)comboBoxFrom.getSelectedItem();
		TF2Items tmpToValue = (TF2Items)comboBoxTo.getSelectedItem();
		
		double tmpEnteredValue = TF2Items.parseStringToDouble( textFieldFromValue.getText() );
		
		String tmpResult = ItemConverterController.doConversion(tmpEnteredValue, tmpFromValue, tmpToValue);
		
		textAreaResultValue.setText( tmpResult );
	}
	
}
