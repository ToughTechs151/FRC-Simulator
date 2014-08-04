package org.nashua.tt151;

import org.nashua.tt151.device.DigitalInput;
import org.nashua.tt151.device.Encoder;
import org.nashua.tt151.device.Talon;

public class Test extends SimpleRobot {
	
	Talon t = new Talon( 0 );
	DigitalInput d = new DigitalInput( 0 );
	Encoder e = new Encoder( 1 );
	Joystick j1 = new Joystick( 1 );
	
	public Test() {
		super();
	}
	
	public static void main( String[] args ) {
		new Test();
	}
	
	public void operatorControl() {
		while ( isOperatorControl() && isEnabled() ) {
//			if ( j1.getRawButton( 1 ) ) {
				System.out.println( j1.getRawAxis( 6 ) );
//			}
			Thread.yield();
		}
	}
}
