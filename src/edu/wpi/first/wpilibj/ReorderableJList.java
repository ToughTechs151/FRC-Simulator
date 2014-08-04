package edu.wpi.first.wpilibj;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;

public class ReorderableJList<E> extends JPanel {
	private JList<E> list;
	private JButton up;
	private JButton down;
	
	public ReorderableJList() {
		list = new JList<E>();
		initGUI();
	}
	
	public ReorderableJList( E[] listData ) {
		list = new JList<E>( listData );
		initGUI();
	}
	
	public ReorderableJList( ListModel<E> dataModel ) {
		list = new JList<E>( dataModel );
		initGUI();
	}
	
	public ReorderableJList( Vector<? extends E> listData ) {
		list = new JList<E>( listData );
		initGUI();
	}
	
	private void initGUI() {
		list.setSelectionMode( ListSelectionModel.SINGLE_SELECTION );
		JPanel controls = new JPanel( new GridLayout( 2, 1 ) );
		up = new JButton( "" + '\u25b2' );
		up.addActionListener( new ActionListener() {
			public void actionPerformed( ActionEvent arg0 ) {
				shiftUp( list.getSelectedIndex() );
			}
		} );
		down = new JButton( "" + '\u25bc' );
		down.addActionListener( new ActionListener() {
			public void actionPerformed( ActionEvent arg0 ) {
				shiftDown( list.getSelectedIndex() );
			}
		} );
		controls.add( up );
		controls.add( down );
		add( list );
		add( controls );
	}
	
	public void shiftUp( int index ) {
		if ( index > 0 && index < list.getModel().getSize() ) {
			swap( index, index - 1 );
		}
	}
	
	public void shiftDown( int index ) {
		if ( index >= 0 && index < list.getModel().getSize() - 1 ) {
			swap( index, index + 1 );
		}
	}
	
	public DefaultListModel<E> getDefaultListModel() {
		// "Cast" ListModel into DefaultListModel by copying elements over
		DefaultListModel<E> lm = new DefaultListModel<E>();
		for ( int i = 0; i < list.getModel().getSize(); i++ ) {
			lm.addElement( list.getModel().getElementAt( i ) );
		}
		return lm;
	}
	
	private void swap( int a, int b ) {
		DefaultListModel<E> lm = getDefaultListModel();
		// Swap
		E temp = lm.get( a );
		lm.set( a, lm.get( b ) );
		lm.set( b, temp );
		// Update the list
		list.setModel( lm );
		list.setSelectedIndex( b );
	}
	
	public void addElement( E e ) {
		DefaultListModel<E> lm = getDefaultListModel();
		lm.addElement( e );
		list.setModel( lm );
	}
	
	public void removeElementAt( int i ) {
		DefaultListModel<E> lm = getDefaultListModel();
		lm.remove( i );
		list.setModel( lm );
	}
	
	public void removeElement( E e ) {
		DefaultListModel<E> lm = getDefaultListModel();
		lm.removeElement( e );
		list.setModel( lm );
	}
	
	public JList<E> getList() {
		return list;
	}
	
	public void setEnabled( boolean enabled ) {
		super.setEnabled( enabled );
		list.setEnabled( enabled );
		up.setEnabled( enabled );
		down.setEnabled( enabled );
	}
}
