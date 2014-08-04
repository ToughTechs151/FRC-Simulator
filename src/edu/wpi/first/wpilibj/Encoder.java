package edu.wpi.first.wpilibj;

import java.awt.Color;

public class Encoder extends DIODevice {
	private static final Color STATUS = Color.CYAN;
	private double distancePerPulse = 1.0;
	private volatile double pulses;
	private boolean reversed;
	private boolean running;
	
	public Encoder( int channel ) {
		this( channel, false );
	}
	
	public Encoder( int channel, boolean reversed ) {
		super( channel );
		this.reversed = reversed;
		this.running = false;
	}
	
	public void setDistancePerPulse( double dpp ) {
		distancePerPulse = dpp;
	}
	
	public void setReversed( boolean reversed ) {
		this.reversed = reversed;
	}
	
	protected String getDrawValue() {
		return String.format( "%.2f", getDistance() );
	}
	
	protected Color selectStatusColor() {
		return STATUS;
	}
	
	public double get() {
		return pulses;
	}
	
	public double getDistance() {
		return pulses * distancePerPulse;
	}
	
	public void increment( double inc ) {
		pulses += reversed ? -inc : inc;
	}
	
	public void start() {
		running = true;
	}
	
	public void stop() {
		running = false;
	}
	
	public void reset() {
		zero();
	}
	
	public void zero() {
		pulses = 0.0;
	}
}
