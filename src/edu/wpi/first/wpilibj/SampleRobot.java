package edu.wpi.first.wpilibj;

public class SampleRobot extends RobotBase {
	
	protected SampleRobot() {
		super();
		robotMain();
	}
	
	protected void robotInit() {
		System.out.println( "Default robotInit() method running, consider providing your own" );
	}
	
	protected void disabled() {
		System.out.println( "Default disabled() method running, consider providing your own" );
	}
	
	public void autonomous() {
		System.out.println( "Default autonomous() method running, consider providing your own" );
	}
	
	public void operatorControl() {
		System.out.println( "Default operatorControl() method running, consider providing your own" );
	}
	
	public void test() {
		System.out.println( "Default test() method running, consider providing your own" );
	}
	
	public void robotMain() {
		robotInit();
		new Thread() {
			public void run() {
				while ( true ) {
					if ( isDisabled() ) {
						disabled();
						while ( isDisabled() ) {
							delay( 10 );
						}
					} else if ( isAutonomous() ) {
						autonomous();
						while ( isAutonomous() && isEnabled() ) {
							delay( 10 );
						}
					} else if ( isTest() ) {
						test();
						while ( isTest() && isEnabled() ) {
							delay( 10 );
						}
					} else {
						operatorControl();
						while ( isOperatorControl() && isEnabled() ) {
							delay( 10 );
						}
					}
				}
			}
		}.start();
	}
	
	private void delay( long millis ) {
		try {
			Thread.sleep( millis );
		} catch ( InterruptedException e ) {}
	}
}
