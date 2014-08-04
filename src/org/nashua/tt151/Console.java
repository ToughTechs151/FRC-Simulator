package org.nashua.tt151;

import java.awt.Color;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.JTextComponent;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

public class Console {
	private JTextComponent textArea;
	private Document doc;
	private int MAX_LINES = 100;
	
	public Console( JTextComponent textArea ) {
		this.textArea = textArea;
		this.doc = textArea.getDocument();
		this.textArea.setEditable( false );
	}
	
	public void redirectOut() {
		redirectOut( null );
	}
	
	public void redirectOut( Color color ) {
		System.setOut( new PrintStream( new ColoredOutputStream( color, System.out ), true ) );
	}
	
	public void redirectErr() {
		redirectErr( null );
	}
	
	public void redirectErr( Color color ) {
		System.setErr( new PrintStream( new ColoredOutputStream( color, System.err ), true ) );
	}
	
	private class ColoredOutputStream extends OutputStream {
		private SimpleAttributeSet attributes;
		private OutputStream alt;
		
		public ColoredOutputStream( Color color ) {
			this( color, null );
		}
		
		public ColoredOutputStream( Color color, OutputStream alt ) {
			if ( color != null ) {
				attributes = new SimpleAttributeSet();
				StyleConstants.setForeground( attributes, color );
			}
			this.alt = alt;
		}
		
		private void updateDocument( String text ) {
			try {
				doc.insertString( doc.getLength(), text, attributes );
				String doctext =  doc.getText( 0, doc.getLength() );
				int lines = doctext.split( "\n" ).length;
				if ( lines > MAX_LINES ) {
					int index = 0;
					for ( int i = 0; i < lines - MAX_LINES; i++ ) {
						index = doctext.substring( index ).indexOf( "\n" ) + 1;
					}
					doc.remove( 0, index );
//					textArea.setText( doctext.substring( index ) );
				}
			} catch ( BadLocationException e ) {
				e.printStackTrace();
			}
			textArea.setCaretPosition( doc.getLength() );
			if ( alt != null ) {
				try {
					alt.write( text.getBytes() );
				} catch ( IOException e ) {
					e.printStackTrace();
				}
			}
		}
		
		public void write( final int b ) throws IOException {
			updateDocument( String.valueOf( (char) b ) );
		}
		
		public void write( byte[] b, int off, int len ) throws IOException {
			updateDocument( new String( b, off, len ) );
		}
		
		public void write( byte[] b ) throws IOException {
			write( b, 0, b.length );
		}
	}
}
