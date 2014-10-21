package calculate;

import java.math.BigDecimal;
import java.util.Scanner;
import java.util.Vector;

import expressions.Expressions;


//TODO Add Hyperbolic,Sign Functions

/**
 * The Main Class
 * Takes Strings and Calculates them. 
 * 
 * @author Daedo
 *
 */
public class Calculator {

	//Okay I know, this might not be the Best solution, but I need these Expressions "global" and I don't want to create multiple Expressions Instances
	public static Expressions expressions = new Expressions();
	private static  boolean useDegrees =true;

	public static String calculate(String expression,boolean useDegree,int decimalPlaces){

		useDegrees = useDegree;

		try {
			//Parse
			Vector<Token> tokens = Parser.tokenize(expression);

			tokens = Parser.convertToPostfixTokens(tokens);

			double result = Parser.calculatePostfixTokens(tokens);

			//Round to the second decimal place
			BigDecimal bigDecimal = new BigDecimal(result);
			bigDecimal = bigDecimal.setScale(decimalPlaces, BigDecimal.ROUND_HALF_UP);
			result = bigDecimal.doubleValue();

			//System.out.println("Result: "+result);

			return result+"";
		} catch( Exception e) {

			String errorMessage = e.getMessage();
			if(errorMessage!=null) {
				return errorMessage;
			}
			return "Calculator Error: \""+e.getClass().getName()+"\"";
		}
	}

	/*
		versed sine, or versine					versin    = 1-cos
		versed cosine, or vercosine					vercosin=1+cos
		coversed sine, or coversine					coversin=1-sin
		coversed cosine						covercosin=1+sin
		haversed sine, or haversine, or semiversed sine
		haversed cosine, or havercosine or semivo
		hacoversed sine, also called hacoversine or cohaversine
		hacoversed cosine, also called hacovercosine or cohavercosine 
		exsecant
		excosecant
		chord
	 */


	public static boolean getUseDegrees() {
		return useDegrees;
	}
}
