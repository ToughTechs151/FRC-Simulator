package org.nashua.tt151;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextPane;

import net.java.games.input.Component.Identifier.Axis;
import net.java.games.input.Component.Identifier.Button;
import net.java.games.input.Controller;
import net.java.games.input.ControllerEnvironment;
import net.java.games.input.ControllerEvent;
import net.java.games.input.ControllerListener;

import org.nashua.tt151.sim.SimulationManager;
import org.nashua.tt151.ui.SleekFrame;
import org.nashua.tt151.util.SwingUtils;

public class DriverStation {
	private static DriverStation instance;
	
	private SleekFrame frame;
	
	private JButton enable = new JButton( "ENABLE" );
	private JButton disable = new JButton( "DISABLE" );
	private JRadioButton autonomous = new JRadioButton( "Autonomous" );
	private JRadioButton operatorControl = new JRadioButton( "Teleoperated" );
	private JRadioButton test = new JRadioButton( "Test" );
	private ReorderableJList<Controller> joysticks;
	private boolean enabled = false;
	
	public static DriverStation getInstance() {
		if ( instance == null ) {
			instance = new DriverStation();
		}
		return instance;
	}
	
	private DriverStation() {
		// First things first: initialize console
		JTextPane console = new JTextPane();
		// Set the system console to this text pane
		Console redir = new Console( console );
		redir.redirectOut();
		redir.redirectErr( Color.RED );
		
		// Initialize RoboRIO
		RoboRIO.getInstance();
		
		// Add currently connected joysticks to list
		ArrayList<Controller> sticks = new ArrayList<Controller>();
		Controller[] all = ControllerEnvironment.getDefaultEnvironment().getControllers();
		for ( int i = 0; i < all.length; i++ ) {
			if ( validType( all[i].getType() ) ) {
				sticks.add( all[i] );
			}
		}
		joysticks = new ReorderableJList<Controller>( sticks.toArray( new Controller[sticks.size()] ) );
		
		// Add a listener to check for joysticks being added or removed
		ControllerEnvironment.getDefaultEnvironment().addControllerListener( new ControllerListener() {
			DefaultListModel<Controller> model = joysticks.getDefaultListModel();
			
			public void controllerRemoved( ControllerEvent ev ) {
				Controller c = ev.getController();
				if ( validType( c.getType() ) && model.contains( c ) ) {
					joysticks.removeElement( c );
				}
			}
			
			public void controllerAdded( ControllerEvent ev ) {
				Controller c = ev.getController();
				if ( validType( c.getType() )) {
					joysticks.addElement( c );
				}
			}
		} );
		
		// Create GUI
		frame = new SleekFrame( "Driver Station" );
		frame.setTitleFont( new Font( "OCR A Std", Font.PLAIN, 20 ) );
		
		// Main panel - contains control panel and console
		JPanel panel = new JPanel( new GridLayout( 1, 2 ) );
		
		// Control panel - contains mode, state, joysticks
		JPanel controls = new JPanel( new GridLayout( 1, 2 ) );
		
		// Internal panel only for mode and state
		JPanel modeState = new JPanel( new GridLayout( 2, 1 ) );
		
		// MODE CHOICES
		JPanel modes = new JPanel( new GridLayout( 3, 1 ) );
		ButtonGroup m = new ButtonGroup();
		m.add( operatorControl );
		m.add( autonomous );
		m.add( test );
		operatorControl.setSelected( true );
		modes.add( operatorControl );
		modes.add( autonomous );
		modes.add( test );
		
		// ENABLE / DISABLE
		JPanel states = new JPanel( new GridLayout( 1, 2 ) );
		enable = new JButton( "ENABLE" );
		enable.setFont( new Font( "Arial", Font.TRUETYPE_FONT, 24 ) );
		enable.addActionListener( new ActionListener() {
			public void actionPerformed( ActionEvent e ) {
				enabled = true;
				enable.setEnabled( false );
				disable.setEnabled( true );
				operatorControl.setEnabled( false );
				autonomous.setEnabled( false );
				test.setEnabled( false );
				joysticks.setEnabled( false );
			}
		} );
		disable = new JButton( "DISABLE" );
		disable.setFont( new Font( "Arial", Font.TRUETYPE_FONT, 24 ) );
		disable.addActionListener( new ActionListener() {
			public void actionPerformed( ActionEvent e ) {
				enabled = false;
				enable.setEnabled( true );
				disable.setEnabled( false );
				operatorControl.setEnabled( true );
				autonomous.setEnabled( true );
				test.setEnabled( true );
				joysticks.setEnabled( true );
				RoboRIO.getInstance().resetAll();
			}
		} );
		disable.doClick();
		states.add( enable );
		states.add( disable );
		
		modeState.add( modes );
		modeState.add( states );
		
		controls.add( modeState );
		controls.add( SwingUtils.placeInTitledEtchedJPanel( joysticks, "Joystick Setup", Color.WHITE ) );
		
		// Console panel
		JPanel consolePanel = new JPanel( new BorderLayout() );
		consolePanel.add( new JScrollPane( console ), BorderLayout.CENTER );
		
		// Control border
		controls = SwingUtils.placeInTitledEtchedJPanel( controls, "Control Panel", Color.WHITE );
		// Console border
		consolePanel = SwingUtils.placeInTitledEtchedJPanel( consolePanel, "Console", Color.WHITE );
		
		panel.add( controls );
		panel.add( consolePanel );
		
		// Coloring
		SwingUtils.setColor_r( panel, Color.GRAY.darker().darker().darker(), Color.WHITE );
		SwingUtils.setColor_r( console, Color.WHITE, Color.BLACK );
		enable.setForeground( Color.GREEN );
		disable.setForeground( Color.RED );
		
		frame.add( panel );
		frame.pack();
		
		// Rectangle of the screen size not including taskbar
		Rectangle maxWindow = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
		frame.setLocation( 0, maxWindow.height - frame.getHeight() );
		
		frame.setVisible( true );
	}
	
	public boolean isAutonomous() {
		return autonomous.isSelected();
	}
	
	public boolean isOperatorControl() {
		return operatorControl.isSelected();
	}
	
	public boolean isTest() {
		return test.isSelected();
	}
	
	public boolean isEnabled() {
		return enabled;
	}
	
	public boolean isDisabled() {
		return !enabled;
	}
	
	private boolean validType( Controller.Type type ) {
		return type == Controller.Type.STICK || type == Controller.Type.GAMEPAD;
	}
	
	private Controller getController( int port ) {
		DefaultListModel<Controller> model = joysticks.getDefaultListModel();
		if ( port < 0 && port >= model.size() ) {
			System.err.println( "Controller " + port + " does not exist!" );
			return null;
		}
		return model.get( port - 1 );
	}
	
	private Axis getAxisFromInt( int axis ) {
		switch ( axis ) {
			case 1:
				return Axis.X;
			case 2:
				return Axis.Y;
			case 3:
				return Axis.Z;
			case 4:
				return Axis.RZ;
			case 5:
				return Axis.RY;
			case 6:
				return Axis.RX;
			default:
				return Axis.UNKNOWN;
		}
	}
	
	private Button getButtonFromInt( int button ) {
		switch ( button ) {
			case 0:
				return Button._0;
			case 1:
				return Button._1;
			case 2:
				return Button._2;
			case 3:
				return Button._3;
			case 4:
				return Button._4;
			case 5:
				return Button._5;
			case 6:
				return Button._6;
			case 7:
				return Button._7;
			case 8:
				return Button._8;
			case 9:
				return Button._9;
			case 10:
				return Button._10;
			case 11:
				return Button._11;
			case 12:
				return Button._12;
			default:
				return Button.UNKNOWN;
		}
	}
	
	public double getStickAxis( int port, int axis ) {
		Controller c = getController( port );
		if ( c == null ) {
			return 0.0;
		}
		c.poll();
		return c.getComponent( getAxisFromInt( axis ) ).getPollData();
	}
	
	public boolean getStickButton( int port, int button ) {
		Controller c = getController( port );
		if ( c == null ) {
			return false;
		}
		c.poll();
		return c.getComponent( getButtonFromInt( button - 1 ) ).getPollData() == 1.0f;
	}
}
