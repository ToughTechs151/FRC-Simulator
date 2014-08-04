package edu.wpi.first.wpilibj;

import java.awt.Color;

public class Relay extends RelayDevice {
	
	public enum Value {
		kOff( "OFF", Color.YELLOW ),
		kOn( "ON", Color.BLUE ),
		kForward( "FWD", Color.GREEN ),
		kReverse( "REV", Color.RED );
		
		public int value = ordinal();
		public String message;
		public Color col;
		
		private Value( String msg, Color col ) {
			this.message = msg;
			this.col = col;
		}
	}
	
	public enum Direction {
		kBoth,
		kForward,
		kReverse;
		
		public int value = ordinal();
	}
	
	private volatile Direction direction;
	private volatile Value value;
	
	public Relay( int channel, Direction direction ) {
		super( channel );
		this.direction = direction;
		this.value = Value.kOff;
		drawFontSize = 30.0f;
	}
	
	public Relay( int channel ) {
		this( channel, Direction.kBoth );
	}
	
	protected String getDrawValue() {
		return value.message;
	}
	
	protected Color selectStatusColor() {
		return value.col;
	}
	
	public void set( Value value ) {
		if ( value == null ) {
			System.err.println( "Relay value cannot be null! Reverting..." );
			return;
		}
		switch ( value ) {
			case kOff:
			case kOn:
				this.value = value;
				break;
			case kForward:
				if ( direction == Direction.kReverse ) {
					System.err.println( "A relay configured for forward cannot be set to reverse! Reverting..." );
					return;
				}
				this.value = value;
				break;
			case kReverse:
				if ( direction == Direction.kForward ) {
					System.err.println( "A relay configured for reverse cannot be set to forward! Reverting..." );
					return;
				}
				this.value = value;
				break;
		
		}
	}
	
	public Value get() {
		return value;
	}
	
	public void setDirection( Direction direction ) {
		if ( direction == null ) {
			System.err.println( "Relay direction cannot be null! Reverting..." );
			return;
		}
		this.direction = direction;
	}
	
	public void zero() {
		value = Value.kOff;
	}
}
