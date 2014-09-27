package edu.wpi.first.wpilibj;

public class Test extends SimpleRobot {
	
	Talon t = new Talon( 0 );
	DigitalInput d = new DigitalInput( 0 );
	Encoder e = new Encoder( 1 );
	Gyro g = new Gyro( 0 );
//	Joystick j1 = new Joystick( 1 );
	
	public Test() {
		super();
	}
	
	public static void main( String[] args ) {
		new Test();
	}
	
	public void operatorControl() {
		while ( isOperatorControl() && isEnabled() ) {
			
		}
	}
}
