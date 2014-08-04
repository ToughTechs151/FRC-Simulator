package edu.wpi.first.wpilibj;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JPanel;

import edu.wpi.first.wpilibj.ui.SleekFrame;
import edu.wpi.first.wpilibj.util.SwingUtils;

public class RoboRIO {
	public enum DeviceType {
		PWM( "PWM" ),
		DIO( "DIO" ),
		RELAY( "Relay" ),
		ANALOG( "Analog" );
		
		private String name;
		
		private DeviceType( String name ) {
			this.name = name;
		}
		
		public String getName() {
			return name;
		}
	}
	
	private static RoboRIO instance = null;
	
	// constant arrays of set size representing device ports
	private static PWMDevice[] pwm = new PWMDevice[10];
	private static DIODevice[] dio = new DIODevice[10];
	private static RelayDevice[] relay = new RelayDevice[4];
	private static AnalogDevice[] analog = new AnalogDevice[4];
	// Up to 10 placeholder devices
	private static BlankDevice[] blanks = new BlankDevice[10];
	
	private SleekFrame frame;
	
	public static RoboRIO getInstance() {
		if ( instance == null ) {
			instance = new RoboRIO();
		}
		return instance;
	}
	
	private RoboRIO() {
		// Create blanks
		for ( int i = 0, len = blanks.length; i < len; i++ ) {
			blanks[i] = new BlankDevice( i );
		}
		
		// Create window
		frame = new SleekFrame( "RoboRIO" );
		frame.setTitleFont( new Font( "OCR A Std", Font.PLAIN, 20 ) );
		
		// Create display panel
		JPanel panel = new JPanel();
		
		// Device Panels
		JPanel pwms = SwingUtils.placeInTitledEtchedJPanel( new DevicePanel( DeviceType.PWM, pwm.length ), DeviceType.PWM.getName(), Color.WHITE );
		JPanel dios = SwingUtils.placeInTitledEtchedJPanel( new DevicePanel( DeviceType.DIO, dio.length ), DeviceType.DIO.getName(), Color.WHITE );
		JPanel relays = SwingUtils.placeInTitledEtchedJPanel( new DevicePanel( DeviceType.RELAY, relay.length ), DeviceType.RELAY.getName(), Color.WHITE );
		JPanel analogs = SwingUtils.placeInTitledEtchedJPanel( new DevicePanel( DeviceType.ANALOG, analog.length ), DeviceType.ANALOG.getName(), Color.WHITE );
		
		// Add device panels
		panel.add( pwms );
		panel.add( dios );
		JPanel relAna = new JPanel( new GridLayout( 2, 1 ) );
		relAna.add( relays );
		relAna.add( analogs );
		panel.add( relAna );
		
		SwingUtils.setBG_r( panel, Color.GRAY.darker().darker().darker() );
		
		// Add display and custom UI size fix
		frame.add( panel );
		frame.pack();
		frame.setVisible( true );
	}
	
	public boolean requestDeviceChannel( Device device, DeviceType type ) {
		switch ( type ) {
			case PWM:
				return tryInsertDevice( device, pwm );
			case DIO:
				return tryInsertDevice( device, dio );
			case RELAY:
				return tryInsertDevice( device, relay );
			case ANALOG:
				return tryInsertDevice( device, analog );
			default:
				return false;
		}
	}
	
	private boolean tryInsertDevice( Device device, Device[] channels ) {
		int ch = device.getChannel();
		if ( ch < 0 || ch >= channels.length ) {
			System.err.println( "Channel " + ch + " out of range!" );
			return false;
		}
		if ( channels[ch] instanceof BlankDevice || channels[ch] == null ) {
			channels[ch] = device;
			return true;
		}
		return false;
	}
	
	public Device getDevice( DeviceType type, int channel ) {
		switch ( type ) {
			case PWM:
				return tryGetDevice( pwm, channel );
			case DIO:
				return tryGetDevice( dio, channel );
			case RELAY:
				return tryGetDevice( relay, channel );
			case ANALOG:
				return tryGetDevice( analog, channel );
			default:
				return null;
		}
	}
	
	private Device tryGetDevice( Device[] channels, int channel ) {
		if ( channel < 0 || channel >= channels.length ) {
			System.err.println( "Channel " + channel + " out of range!" );
			return null;
		}
		return channels[channel] != null ? channels[channel] : blanks[channel];
	}
	
	public void setDeviceName( DeviceType type, int channel, String name ) {
		Device d = null;
		switch ( type ) {
			case PWM:
				d = tryGetDevice( pwm, channel );
				break;
			case DIO:
				d = tryGetDevice( dio, channel );
				break;
			case RELAY:
				d = tryGetDevice( relay, channel );
				break;
			case ANALOG:
				d = tryGetDevice( analog, channel );
				break;
		}
		if ( d != null ) {
			d.setName( name );
		}
	}
	
	public void resetAll() {
		for ( PWMDevice d : pwm ) {
			if ( d != null ) {
				d.zero();
			}
		}
		for ( DIODevice d : dio ) {
			if ( d != null ) {
				d.zero();
			}
		}
		for ( RelayDevice d : relay ) {
			if ( d != null ) {
				d.zero();
			}
		}
		for ( AnalogDevice d : analog ) {
			if ( d != null ) {
				d.zero();
			}
		}
	}
}
