package org.nashua.tt151.device;

import java.awt.Color;

import org.nashua.tt151.util.MathUtils;

public class AnalogPotentiometer extends AnalogDevice {
	private static final Color STATUS = Color.GREEN;
	private volatile double value = 0.0;
	private double maxValue = 1.0;
	private boolean rollover = false;
	
	public AnalogPotentiometer( int channel ) {
		super( channel );
	}
	
	protected String getDrawValue() {
		return String.format( "%.2f", value );
	}
	
	protected Color selectStatusColor() {
		return STATUS;
	}
	
	public double get() {
		return value;
	}
	
	public void setMaxValue( double maxValue ) {
		if ( maxValue < 0 ) {
			System.err.println( "Potentiometer max cannot be < 0, reverting..." );
			return;
		}
		this.maxValue = maxValue;
	}
	
	public void setRollover( boolean rollover ) {
		this.rollover = rollover;
	}
	
	public void increment( double inc ) {
		if ( rollover ) {
			if ( inc >= 0 ) {
				value = ( value + inc ) % maxValue;
			} else {
				value += inc;
				while ( value < 0 ) {
					value += maxValue;
				}
			}
		} else {
			value = MathUtils.clamp( 0.0, maxValue, value + inc );
		}
	}
	
	public void zero() {
		value = 0.0;
	}
}