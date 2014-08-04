package org.nashua.tt151;

public class Joystick {
	static final byte kDefaultXAxis = 1;
	static final byte kDefaultYAxis = 2;
	static final byte kDefaultZAxis = 3;
	static final byte kDefaultTwistAxis = 3;
	static final byte kDefaultThrottleAxis = 4;
	static final int kDefaultTriggerButton = 1;
	static final int kDefaultTopButton = 2;
	
	public static enum AxisType {
		kX( 0 ),
		kY( 1 ),
		kZ( 2 ),
		kTwist( 3 ),
		kThrottle( 4 );
		
		public int value;
		
		private AxisType( int value ) {
			this.value = value;
		}
	}
	
	public static enum ButtonType {
		kTrigger( 0 ),
		kTop( 1 );
		
		public int value;
		
		private ButtonType( int value ) {
			this.value = value;
		}
	}
	
	private DriverStation m_ds;
	private final int m_port;
	private final byte[] m_axes;
	private final byte[] m_buttons;
	
	public Joystick( final int port ) {
		this( port, AxisType.values().length, ButtonType.values().length );
		
		m_axes[AxisType.kX.value] = kDefaultXAxis;
		m_axes[AxisType.kY.value] = kDefaultYAxis;
		m_axes[AxisType.kZ.value] = kDefaultZAxis;
		m_axes[AxisType.kTwist.value] = kDefaultTwistAxis;
		m_axes[AxisType.kThrottle.value] = kDefaultThrottleAxis;
		
		m_buttons[ButtonType.kTrigger.value] = kDefaultTriggerButton;
		m_buttons[ButtonType.kTop.value] = kDefaultTopButton;
	}
	
	protected Joystick( int port, int numAxisTypes, int numButtonTypes ) {
		m_ds = DriverStation.getInstance();
		m_axes = new byte[numAxisTypes];
		m_buttons = new byte[numButtonTypes];
		m_port = port;
	}
	
	public double getX() {
		return getRawAxis( m_axes[AxisType.kX.value] );
	}
	
	public double getY() {
		return getRawAxis( m_axes[AxisType.kY.value] );
	}
	
	public double getZ() {
		return getRawAxis( m_axes[AxisType.kZ.value] );
	}
	
	public double getTwist() {
		return getRawAxis( m_axes[AxisType.kTwist.value] );
	}
	
	public double getThrottle() {
		return getRawAxis( m_axes[AxisType.kThrottle.value] );
	}
	
	public double getRawAxis( final int axis ) {
		return m_ds.getStickAxis( m_port, axis );
	}
	
	public double getAxis( final AxisType axis ) {
		switch ( axis ) {
			case kX:
				return getX();
			case kY:
				return getY();
			case kZ:
				return getZ();
			case kTwist:
				return getTwist();
			case kThrottle:
				return getThrottle();
			default:
				return 0.0;
		}
	}
	
	public boolean getTrigger() {
		return getRawButton( m_buttons[ButtonType.kTrigger.value] );
	}
	
	public boolean getTop() {
		return getRawButton( m_buttons[ButtonType.kTop.value] );
	}
	
	public boolean getBumper() {
		return false;
	}
	
	public boolean getRawButton( final int button ) {
		return m_ds.getStickButton( m_port, button );
	}
	
	public boolean getButton( ButtonType button ) {
		switch ( button ) {
			case kTrigger:
				return getTrigger();
			case kTop:
				return getTop();
			default:
				return false;
		}
	}
	
	public double getMagnitude() {
		return Math.sqrt( Math.pow( getX(), 2 ) + Math.pow( getY(), 2 ) );
	}
	
	public double getDirectionRadians() {
		return Math.atan2( getX(), -getY() );
	}
	
	public double getDirectionDegrees() {
		return Math.toDegrees( getDirectionRadians() );
	}
	
	public int getAxisChannel( AxisType axis ) {
		return m_axes[axis.value];
	}
	
	public void setAxisChannel( AxisType axis, int channel ) {
		m_axes[axis.value] = (byte) channel;
	}
}