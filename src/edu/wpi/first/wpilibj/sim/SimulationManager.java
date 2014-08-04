package edu.wpi.first.wpilibj.sim;

import java.util.Timer;
import java.util.TimerTask;

import edu.wpi.first.wpilibj.Device;

public abstract class SimulationManager {
	private static Timer timer;
	private static final long DEFAULT_DELAY = 10;
	
	protected SimulationManager() {
		timer = new Timer();
		init();
	}
	
	protected abstract void init();
	
	protected void addDeviceConnection( final DeviceConnection dc, final Device master, final Device connected ) {
		timer.scheduleAtFixedRate( new TimerTask() {
			public void run() {
				dc.invoke( master, connected );
			}
		}, DEFAULT_DELAY, DEFAULT_DELAY );
	}
	
	protected void addDelayedAction( final DelayedDeviceAction dc, final Device device, long delay ) {
		timer.schedule( new TimerTask() {
			public void run() {
				dc.invokeAction( device );
			}
		}, delay );
	}
}
