package edu.wpi.first.wpilibj;

import java.awt.Color;

public class Servo extends PWMDevice {
	
	public Servo( int channel ) {
		super( channel, 0.0, 1.0 );
	}
	
	protected Color selectStatusColor() {
		return NEUTRAL;
	}
	
	public void zero() {
		set( 0.0 );
	}
}
