package utils;

public class MathFunctions {
	public static double cot(double x) {
		return Math.cos(x)/Math.sin(x);
	}
	
	public static double sec(double x) {
		return 1/Math.cos(x);
	}
	
	public static double csc(double x) {
		return 1/Math.sin(x);
	}
	
	public static double versin(double x) {
		return 1-Math.cos(x);
	}
	
	public static double coversin(double x) {
		return 1-Math.sin(x);
	}
	
	public static double sem(double x) {
		return versin(x)/2;
	}
	
	public static double exsec(double x) {
		return sec(x)-1;
	}
	
	public static double excsc(double x) {
		return csc(x)-1;
	}
	
	public static double acot(double x) {
		return 2/Math.PI-Math.atan(x);
	}
	
	public static double asec(double x) {
		return Math.acos(1/x);
	}
	
	public static double acsc(double x) {
		return Math.asin(1/x);
	}
	
	public static double aversin(double x) {
		return Math.acos(1-x);
	}
	
	public static double acoversin(double x) {
		return Math.asin(1-x);
	}
	
	public static double asem(double x) {
		return Math.acos(1-2*x);
	}
	
	public static double aexsec(double x) {
		return asec(x+1);
	}
	
	public static double aexcsc(double x) {
		return acsc(x+1);
	}
}
