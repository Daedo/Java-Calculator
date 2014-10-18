package calculate;

import expressions.Expressions;

/**
 * Stores information about an expression part.Used by the {@link Parser} class.
 * 
 * @author Daedo
 *
 */
public class Token {
	//Type Constants
	public static final int TOKEN_NUMBER 			= 1;
	public static final int TOKEN_NUMBER_LITERAL	= 2;
	public static final int TOKEN_FUNCTION			= 3;
	public static final int TOKEN_OPERATOR			= 4;
	public static final int TOKEN_UNARY_OPERATOR	= 5;
	public static final int TOKEN_OPEN_BRACKET		= 6;
	public static final int TOKEN_CLOSE_BRACKET		= 7;
	public static final int TOKEN_ARGUMENT_SEPERATOR= 8;
	public static final int TOKEN_VARIABLE 			= 9;
	
	private int tokenType;
	private String tokenText;
	
	public Token(String text) {
		this.tokenText = text;
		this.tokenType = getStringType(text);
	}
	
	public String getTokenText() {
		return this.tokenText;
	}
	
	/**
	 * Calculates the Type of a Expression and returns the type constant.
	 * @param text The expression as a String
	 * @return Returns the type constant as an integer.
	 */
	private static int getStringType(String text) {
		Expressions expressions = Calculator.expressions;
		
		boolean isFunction = expressions.matchesFunction(text); 
		if(isFunction) {
			return TOKEN_FUNCTION;
		}
		
		boolean isNumberLiteral = expressions.matchesNumber(text);
		if(isNumberLiteral) {
			return TOKEN_NUMBER_LITERAL;
		}
		
		boolean isOperator = expressions.matchesOperator(text);
		if(isOperator) {
			return TOKEN_OPERATOR;
		}
		
		boolean isOpenBracket = text.equals("(");
		if(isOpenBracket) {
			return TOKEN_OPEN_BRACKET;
		}
		
		boolean isClosedBracket = text.equals(")");
		if(isClosedBracket) {
			return TOKEN_CLOSE_BRACKET;
		}
		
		boolean isArgumentSperator = text.equals(",");
		if(isArgumentSperator) {
			return TOKEN_ARGUMENT_SEPERATOR;
		}
		
		String numberRegexp = "\\d*(\\.\\d*)?";
		boolean isNumber = text.matches(numberRegexp);
		if(isNumber) {
			return TOKEN_NUMBER;
		}
		
		return TOKEN_VARIABLE;
	}
	
	public int getTokenType (){
		return this.tokenType;
	}

	/**
	 * Prints out the type and the literal of a token
	 */
	public void print() {
		System.out.println(this.tokenText+"\t-\t"+this.tokenType);
	}
	
	public void setTokenType(int newTokenType) {
		boolean isValidType = 0<newTokenType && 10>newTokenType;
		if(isValidType) {
			this.tokenType = newTokenType;
		}
	}
}
