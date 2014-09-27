package edu.wpi.first.wpilibj;

import java.awt.Color;

public class Gyro extends AnalogDevice {
	private static final Color STATUS = Color.YELLOW;
	private volatile double value;
	
	public Gyro( int channel ) {
		super( channel );
		this.value = 0.0;
	}
	
	public double getAngle() {
		return value;
	}
	
	public void reset() {
		zero();
	}
	
	public void increment( double inc ) {
		value += inc;
	}
	
	@Override
	protected String getDrawValue() {
		return String.format( "%.2f", value );
	}
	
	@Override
	protected Color selectStatusColor() {
		return STATUS;
	}
	
	@Override
	public void zero() {
		this.value = 0.0;
	}
	
}
