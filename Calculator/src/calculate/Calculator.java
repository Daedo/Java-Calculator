package calculate;

import java.math.BigDecimal;
import java.util.Scanner;
import java.util.Vector;

import expressions.Expressions;


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
		System.out.println("Please input an expression:");

		try (Scanner sc = new Scanner(System.in)) {
			//Get the Expression
			boolean hasLine = sc.hasNextLine();
			if(hasLine) {
				inputText = sc.nextLine();
			}
			sc.close();

			//Parse
			Vector<Token> tokens = Parser.tokenize(inputText);

			tokens = Parser.convertToPostfixTokens(tokens);

			double result = Parser.calculatePostfixTokens(tokens);
			
			//Round to the second decimal place
			BigDecimal bigDecimal = new BigDecimal(result);
			bigDecimal = bigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP);
			result = bigDecimal.doubleValue();
			
			System.out.println("Result: "+result);
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}
}
