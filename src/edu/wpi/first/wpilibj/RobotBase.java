package edu.wpi.first.wpilibj;

public abstract class RobotBase {
	private static DriverStation ds = DriverStation.getInstance();
	
	protected RobotBase() {}
	
	protected abstract void robotInit();
	
	protected abstract void disabled();
	
	public abstract void autonomous();
	
	public abstract void operatorControl();
	
	public abstract void test();
	
	public abstract void robotMain();
	
	public boolean isEnabled() {
		return ds.isEnabled();
	}
	
	public boolean isDisabled() {
		return ds.isDisabled();
	}
	
	public boolean isAutonomous() {
		return ds.isAutonomous();
	}
	
	public boolean isOperatorControl() {
		return ds.isOperatorControl();
	}
	
	public boolean isTest() {
		return ds.isTest();
	}
}
