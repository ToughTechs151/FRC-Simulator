package edu.wpi.first.wpilibj;

import java.awt.Color;

import edu.wpi.first.wpilibj.RoboRIO.DeviceType;
import edu.wpi.first.wpilibj.util.MathUtils;

public abstract class PWMDevice extends Device {
	protected static final Color NEUTRAL = Color.YELLOW;
	protected static final Color FORWARD = Color.GREEN;
	protected static final Color BACKWARD = Color.RED;
	
	protected volatile double value = 0.0;
	private double min;
	private double max;
	
	public PWMDevice( int channel, double min, double max ) {
		super( channel, DeviceType.PWM );
		this.min = min;
		this.max = max;
		if ( min > max ) {
			System.err.println( "Invalid value range for PWM " + channel );
		}
		drawFontSize = 30.0f;
	}
	
	public void set( double val ) {
		value = MathUtils.clamp( min, max, val );
	}
	
	public double get() {
		return value;
	}
	
	protected String getDrawValue() {
		return String.format( "%.2f", value );
	}
	
}
