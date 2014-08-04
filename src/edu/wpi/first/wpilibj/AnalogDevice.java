package edu.wpi.first.wpilibj;

import edu.wpi.first.wpilibj.RoboRIO.DeviceType;


public abstract class AnalogDevice extends Device {
	
	public AnalogDevice( int channel ) {
		super( channel, DeviceType.ANALOG );
	}
	
}
