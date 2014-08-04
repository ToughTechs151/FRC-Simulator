package edu.wpi.first.wpilibj;

import java.awt.Color;

public class Victor extends PWMDevice {
	
	public Victor( int channel ) {
		super( channel, -1.0, 1.0 );
	}
	
	protected Color selectStatusColor() {
		return value == 0 ? NEUTRAL : ( value > 0 ? FORWARD : BACKWARD );
	}
	
	public void zero() {
		set( 0.0 );
	}
}
