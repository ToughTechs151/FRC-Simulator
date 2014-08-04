package edu.wpi.first.wpilibj.util;

public class MathUtils {
	private MathUtils() {}
	
	public static int clampi( int min, int max, int val ) {
		return Math.min( max, Math.max( min, val ) );
	}
	
	public static double clamp( double min, double max, double val ) {
		return Math.min( max, Math.max( min, val ) );
	}
}
