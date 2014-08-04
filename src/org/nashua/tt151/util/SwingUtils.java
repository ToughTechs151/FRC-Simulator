package org.nashua.tt151.util;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

public class SwingUtils {
	private SwingUtils() {}
	
	public static void setBG_r( Component c, Color bg ) {
		setColor_r( c, bg, null );
	}
	
	public static void setFG_r( Component c, Color fg ) {
		setColor_r( c, null, fg );
	}
	
	public static void setColor_r( Component c, Color bg, Color fg ) {
		if ( bg != null ) {
			c.setBackground( bg );
		}
		if ( fg != null ) {
			c.setForeground( fg );
		}
		if ( c instanceof Container ) {
			Component[] comps = ( (Container) c ).getComponents();
			if ( comps.length > 0 ) {
				for ( Component comp : comps ) {
					setColor_r( comp, bg, fg );
				}
			}
		}
	}
	
	public static JPanel placeInTitledEtchedJPanel( Component c, String title, Color titleColor ) {
		JPanel parent = new JPanel( new BorderLayout() );
		parent.add( c, BorderLayout.CENTER );
		TitledBorder tb = new TitledBorder( title );
		tb.setBorder( BorderFactory.createEtchedBorder() );
		tb.setTitleColor( titleColor );
		parent.setBorder( tb );
		return parent;
	}
}
