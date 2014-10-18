package calculate;

import java.math.BigDecimal;
import java.util.Scanner;
import java.util.Vector;

import expressions.Expressions;


/**
 * The Main Class
 * Takes Strings and Calculates them. 
 * 
 * @author Dominik
 *
 */
public class Calculator {

	//Okay I know, this might not be the Best solution, but I need these Expressions "global" and I don't want to create multiple Expressions Instances
	public static Expressions expressions = new Expressions();

	public static void main(String[] args) {//TODO Fix Sign Error -2.5 should be a Number not an Operator

		String inputText = null;
		
		System.out.println("Please input an expression:");
		
		Scanner sc = new Scanner(System.in);

		boolean hasLine = sc.hasNextLine();
		if(hasLine) {
			 inputText = sc.nextLine();
		}
		sc.close();


		//String inputText = "1+sin(4pi)(-2.5)";

		Vector<Token> tokens = Parser.tokenize(inputText);

		/*for(Token token:tokens) {
			token.print();
		}
		System.out.println("");*/

		try {
			tokens = Parser.convertToPostfixTokens(tokens);
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		/*for(Token token:tokens) {
			token.print();
		}*/


		try {
			double result = Parser.calculatePostfixTokens(tokens);
			//System.out.println(result);
			BigDecimal bigDecimal = new BigDecimal(result);
			bigDecimal = bigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP);
			result = bigDecimal.doubleValue();
			System.out.println(result);

		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}
}
