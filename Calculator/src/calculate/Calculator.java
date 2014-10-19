package calculate;

import java.math.BigDecimal;
import java.util.Scanner;
import java.util.Vector;

import expressions.Expressions;


//TODO Add Hyperbolic,Sign Functions
//TODO Add Radiant/ Degree mode
//TODO Add Variable number of decimal place
//TODO Add GUI

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

	public static void main(String[] args) {

		String inputText = null;

		try (Scanner sc = new Scanner(System.in)) {
			while(true) {
				//Get the Expression
				System.out.println("Please input an expression\ninput \"help\" for help, \"exit\" to exit the program:");
				inputText = sc.nextLine();

				boolean isExit = inputText.toLowerCase().equals("exit");

				if(isExit) {
					break;
				}

				boolean isHelp = inputText.toLowerCase().equals("help");

				if(isHelp) {
					displayHelp();
				} else {
					//Parse
					Vector<Token> tokens = Parser.tokenize(inputText);

					tokens = Parser.convertToPostfixTokens(tokens);

					double result = Parser.calculatePostfixTokens(tokens);

					//Round to the second decimal place
					BigDecimal bigDecimal = new BigDecimal(result);
					bigDecimal = bigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP);
					result = bigDecimal.doubleValue();

					System.out.println("Result: "+result);
				}
				System.out.println("");
			}
			sc.close();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}

		inputText = null;
	}

	private static void displayHelp() {
		System.out.println("Help:");
		System.out.println("Input the expression, that you want to calculate. Allowed symbols:");
		System.out.println("+-*/");
		System.out.println("^ for powers");
		System.out.println("( )");
		System.out.println("sin(x)			Sine of x");
		System.out.println("cos(x)			Cosine of x");
		System.out.println("tan(x)			Tangent of x");
		System.out.println("cot(x)			Cotangent  of x");
		System.out.println("sec(x)			Secant of x");
		System.out.println("csc(x)			Cosecant of x");
		System.out.println("versin(x)		Versine/versed sine of x");
		System.out.println("coversin(x)		Coversine/ coversed sine of x");
		System.out.println("sem(x)			Semiversine of x");
		System.out.println("exsec(x)		exsecant of x");
		System.out.println("excsc(x)		excosecant of x");
		
		System.out.println("asin(x)			Arcus Sine of x");
		System.out.println("acos(x)			Arcus Cosine of x");
		System.out.println("atan(x)			Arcus Tangent of x");
		System.out.println("acot(x)			Arcus Cotangent  of x");
		System.out.println("asec(x)			Arcus Secant of x");
		System.out.println("acsc(x)			Arcus Cosecant of x");
		System.out.println("aversin(x)		Arcus Versine/versed sine of x");
		System.out.println("acoversin(x)		Arcus Coversine/ coversed sine of x");
		System.out.println("asem(x)			Arcus Semiversine of x");
		System.out.println("aexsec(x)		Arcus exsecant of x");
		System.out.println("aexcsc(x)		Arcus excosecant of x");
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
		System.out.println("log10(x)		Log of the base 10.");
		System.out.println("ln(x)			Log of the base e (Natural Log).");
		System.out.println("floor(x)		Round a number down.");
		System.out.println("ceil(x)			Round a number up.");
		System.out.println("round(x)		Round a number to the next integer.");
		System.out.println("pi			3.1415...");
		System.out.println("tau			2*pi");
		System.out.println("e			Eurlers number");
		System.out.println("phi			Golden ratio");
	}
}
