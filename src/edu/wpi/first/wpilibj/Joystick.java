package edu.wpi.first.wpilibj;

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
	
	private DriverStation ds;
	private final int port;
	private final byte[] axes;
	private final byte[] buttons;
	
	public Joystick( final int port ) {
		this( port, AxisType.values().length, ButtonType.values().length );
		
		axes[AxisType.kX.value] = kDefaultXAxis;
		axes[AxisType.kY.value] = kDefaultYAxis;
		axes[AxisType.kZ.value] = kDefaultZAxis;
		axes[AxisType.kTwist.value] = kDefaultTwistAxis;
		axes[AxisType.kThrottle.value] = kDefaultThrottleAxis;
		
		buttons[ButtonType.kTrigger.value] = kDefaultTriggerButton;
		buttons[ButtonType.kTop.value] = kDefaultTopButton;
	}
	
	protected Joystick( int port, int numAxisTypes, int numButtonTypes ) {
		ds = DriverStation.getInstance();
		axes = new byte[numAxisTypes];
		buttons = new byte[numButtonTypes];
		this.port = port;
	}
	
	public double getX() {
		return getRawAxis( axes[AxisType.kX.value] );
	}
	
	public double getY() {
		return getRawAxis( axes[AxisType.kY.value] );
	}
	
	public double getZ() {
		return getRawAxis( axes[AxisType.kZ.value] );
	}
	
	public double getTwist() {
		return getRawAxis( axes[AxisType.kTwist.value] );
	}
	
	public double getThrottle() {
		return getRawAxis( axes[AxisType.kThrottle.value] );
	}
	
	public double getRawAxis( final int axis ) {
		return ds.getStickAxis( port, axis );
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
		return getRawButton( buttons[ButtonType.kTrigger.value] );
	}
	
	public boolean getTop() {
		return getRawButton( buttons[ButtonType.kTop.value] );
	}
	
	public boolean getBumper() {
		return false;
	}
	
	public boolean getRawButton( final int button ) {
		return ds.getStickButton( port, button );
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
		return axes[axis.value];
	}
	
	public void setAxisChannel( AxisType axis, int channel ) {
		axes[axis.value] = (byte) channel;
	}
}