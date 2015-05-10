package net.tf2calc.view;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

public class JTextFieldLimiter extends PlainDocument {

	private static final long serialVersionUID = 201209172232L;

	private int limit;

	JTextFieldLimiter(int limit) {
		super();
		this.limit = limit;
	}

	public void insertString(int argOffset, String  argString, AttributeSet argAttribute) throws BadLocationException {
		if (argString == null){ 
			return; 
		}

		if ( (getLength() + argString.length()) <= limit ) {
			super.insertString(argOffset, argString, argAttribute);
		}
	}

}
