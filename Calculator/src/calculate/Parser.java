package calculate;

import java.util.Stack;
import java.util.Vector;

import expressions.Expressions;
import expressions.Function;
import expressions.Operator;

/**
 * Is used to parse a String into Tokens,
 * convert them into postfix notation and
 * determine their value.
 * @author Daedo
 */
public class Parser {
	/**
	 * Takes a text String and parses it into a Vector of {@link Token}
	 * instances.
	 * 
	 * @param text
	 *            The input text as a String.
	 * @return Returns a Vector of {@link Token} instances.
	 */
	public static Vector<Token> tokenize(String text) {
		// We use the "Global" Expressions Instance provided by the Calculator
		// Class
		Expressions expressions = Calculator.expressions;

		Vector<Token> tokens = new Vector<>();
		String currentToken = "";
		boolean isInDigit = false;

		String workingText = text;
		int workingTextLength = workingText.length();
		while (workingTextLength > 0) {
			boolean hasFoundExpression = false;
			boolean isEmptyToken = currentToken.equals("");

			String matchedExpression = expressions.getMatchingStartExpression(workingText);
			hasFoundExpression = matchedExpression!=null;
			
			if(hasFoundExpression) {
				if (!isEmptyToken) {
					Token token = new Token(currentToken);
					tokens.add(token);
					currentToken = "";
				}

				Token token = new Token(matchedExpression);
				tokens.add(token);

				workingText = workingText.substring(matchedExpression.length());
			}

			isEmptyToken = currentToken.equals("");

			if (!hasFoundExpression) {
				// The Current Token is not a Expression
				String currentChar = workingText.substring(0, 1);

				boolean isDot = currentChar.equals(".");
				boolean isDigit = currentChar.matches("\\d");

				if (!isEmptyToken) {
					if (isInDigit && (!isDigit) && (!isDot)) {
						// End the Current Number and start a Variable
						Token token = new Token(currentToken);
						tokens.add(token);
						currentToken = "";
						isInDigit = false;
					} else {
						if (!isInDigit && (isDigit || isDot)) {
							// End the current Variable and start a Number
							Token token = new Token(currentToken);
							tokens.add(token);
							currentToken = "";
							isInDigit = true;
						}
					}
				} else {
					if (isDigit || isDot) {
						isInDigit = true;
					}
				}
				currentToken = currentToken + currentChar;
				workingText = workingText.substring(1);
			} else {
				// Expression
				if (!isEmptyToken) {
					Token token = new Token(currentToken);
					tokens.add(token);
					currentToken = "";
				}
			}

			workingTextLength = workingText.length();
		}

		// Adds the rest, that wasn't parsed already.
		boolean isEmptyToken = currentToken.equals("");
		if (!isEmptyToken) {
			Token token = new Token(currentToken);
			tokens.add(token);
			currentToken = "";
		}

		return tokens;
	}

	/**
	 * Takes a Vector of Tokens in infix Notation (like the ones provided by the
	 * tokenize method) and converts them into a Vector of Tokens in postfix
	 * (reverse polish) notation using the "shunting-yard" algorithm.
	 * 
	 * @param infixTokens
	 *            A Vector of Tokens in infix notation.
	 * @return Returns a Vector of Tokens in postfix notation.
	 * @throws Exception 
	 */
	public static Vector<Token> convertToPostfixTokens(Vector<Token> infixTokens) throws Exception {
		Vector<Token> inVector = prewortDetermineUnaryOperators(infixTokens);
		inVector = preworkConversion(inVector);
		Vector<Token> outVector = new Vector<>();
		Stack<Token> workStack = new Stack<>();
		
		Expressions expressions = Calculator.expressions;
		
		boolean inVectorIsEmpty = inVector.isEmpty();
		
		while(!inVectorIsEmpty) {
			Token currentToken = inVector.get(0);
			inVector.remove(0);
			
			String currentText = currentToken.getTokenText();
			
			int currentType = currentToken.getTokenType();
			
			switch(currentType) {
			case Token.TOKEN_NUMBER:		//Falls through
			case Token.TOKEN_VARIABLE:		//Falls through
			case Token.TOKEN_NUMBER_LITERAL: 
				outVector.add(currentToken);
				break;
				
			case Token.TOKEN_FUNCTION:
				workStack.push(currentToken);
				break;
				
			case Token.TOKEN_ARGUMENT_SEPERATOR:
				while(true) {
					Token topToken = workStack.pop();
					int topTokenType = topToken.getTokenType();
					boolean topTokenIsOpenBracket = topTokenType == Token.TOKEN_OPEN_BRACKET;
					
					if(topTokenIsOpenBracket) {
						break;
					}
					
					outVector.add(topToken);
					
					boolean workStackIsEmpty = workStack.isEmpty();
					if(workStackIsEmpty) {
						throw new Exception("Argument Seperator Error. Reasons: Missplaced Argument Seperator or unmatched closing bracket");
					}
				}
				break;
				
			case Token.TOKEN_UNARY_OPERATOR: //Falls through
			case Token.TOKEN_OPERATOR:
				
				while(true) {
					boolean workStackIsEmpty = workStack.isEmpty();
					
					if(workStackIsEmpty) {
						break;
					}
					
					Token topToken = workStack.peek();
					String topTokenText = topToken.getTokenText();
					int topTokenType = topToken.getTokenType();
					boolean topTokenIsOperator = topTokenType == Token.TOKEN_OPERATOR;
					
					if(topTokenIsOperator) {
						Operator currentOperator 	= expressions.getOperator(currentText);
						int currentPriority 		= currentOperator.getPriority();
						boolean isLeftToRight 		= currentOperator.isLeftToRight();
						
						Operator topTokenOperator 	= expressions.getOperator(topTokenText);
						int topTokenPriority 		= topTokenOperator.getPriority();
						
						boolean currentPriorityIsBiggerOrEqualTopPriority = currentPriority <= topTokenPriority;
						boolean currentPriorityIsBiggerTopPriority		  = currentPriority <  topTokenPriority;
						
						boolean isCaseA = ((currentPriorityIsBiggerOrEqualTopPriority && isLeftToRight)&&!currentPriorityIsBiggerTopPriority);
						boolean isCaseB = (!(currentPriorityIsBiggerOrEqualTopPriority && isLeftToRight))&&currentPriorityIsBiggerTopPriority;
						if(isCaseA || isCaseB) {
							outVector.add(topToken);
							workStack.pop();
						} else {
							break;
						}
					} else {
						break;
					}
				}
				workStack.push(currentToken);
				break;
				
			case Token.TOKEN_OPEN_BRACKET:
				workStack.push(currentToken);
				break;
				
			case Token.TOKEN_CLOSE_BRACKET:
				while(true) {
					Token topToken = workStack.pop();
					int topTokenType = topToken.getTokenType();
					boolean topTokenIsOpenBracket = topTokenType == Token.TOKEN_OPEN_BRACKET;
					
					if(topTokenIsOpenBracket) {
						break;
					}
					
					outVector.add(topToken);
					
					boolean workStackIsEmpty = workStack.isEmpty();
					if(workStackIsEmpty) {
						throw new Exception("Close Bracket Error. Reason: Unmatched closing bracket");
					}
					
				}
				
				Token topToken = workStack.peek();
				int topTokenType = topToken.getTokenType();
				boolean topIsFunction = topTokenType == Token.TOKEN_FUNCTION;
				
				if(topIsFunction) {
					workStack.pop();
					outVector.add(topToken);
				}
				
				break;
				
			default:
				break;
			}
			
			inVectorIsEmpty = inVector.isEmpty();
		}
		
		boolean workStackIsEmpty = workStack.isEmpty();
		while(!workStackIsEmpty) {
			Token currentToken = workStack.pop();
			int currentTokenType = currentToken.getTokenType();
			
			boolean isOpenBracket = currentTokenType == Token.TOKEN_OPEN_BRACKET;
			if(isOpenBracket) {
				throw new Exception("Open Bracket Error. Reason: Unmatched open bracket");
			}
			outVector.add(currentToken);
			workStackIsEmpty = workStack.isEmpty();
		}
		
		return outVector;
	}	

	
	/**
	 * Iterates over the Tokens looking for unary Operators (+,- Signs).
	 * The signs for a unary operator:
	 * 
	 * 1. It is the first {@link Token} of the expression or follows a argument separator (= first token of a sub expression) 
	 * 2. It follows another Operator
	 * 3. It follows an open bracket
	 * 
	 * @param postfixTokens A Vector of Tokens in postfix notation.
	 * @return Returns a Vector of Tokens in postfix notation.
	 */
	private static Vector<Token> prewortDetermineUnaryOperators(Vector<Token> postfixTokens) {
		Vector<Token> outVector = postfixTokens;
		Expressions expr = Calculator.expressions;
		
		int tokenCount = outVector.size();
		for(int i=0;i<tokenCount;i++) {
			Token currentToken = outVector.get(i);
			String currentText = currentToken.getTokenText();
			int tokenType = currentToken.getTokenType();
			
			boolean isFirstToken 		 	= i==0;
			boolean canBeValidUnaryOperator = expr.matchesUnaryOperator(currentText);
			
			if(canBeValidUnaryOperator) {
				if(isFirstToken) {
					currentToken.setTokenType(Token.TOKEN_UNARY_OPERATOR);
				} else {
					Token prevToken = outVector.get(i-1);
					int prevType = prevToken.getTokenType();
					
					boolean isValidUnaryOperator = prevType==Token.TOKEN_ARGUMENT_SEPERATOR || prevType==Token.TOKEN_OPEN_BRACKET || prevType==Token.TOKEN_OPERATOR ||prevType==Token.TOKEN_UNARY_OPERATOR;
					if(isValidUnaryOperator) {
						currentToken.setTokenType(Token.TOKEN_UNARY_OPERATOR);
					}
					
				}
			}
		}
		
		return outVector;
	}
	
	/**
	 * Iterates over the Tokens looking for implied multiplications
	 * 2x -> 2*x; (a-b)(a+b) -> (a-b)*(a+b)
	 * 
	 * @param postfixTokens A Vector of Tokens in postfix notation.
	 * @return Returns a Vector of Tokens in postfix notation.
	 */
	private static Vector<Token> preworkConversion(Vector<Token> postfixTokens) {
		
		Vector<Token> outVector = new Vector<>();
		
		int tokenCount = postfixTokens.size();
		for(int i = 0;i < tokenCount;i++) {
			
			Token firstToken = postfixTokens.get(i);
			outVector.add(firstToken);
			
			boolean isLastElement = i == (tokenCount-1);
			
			if(!isLastElement) {
				int firstType = firstToken.getTokenType();
				
				Token secondToken = postfixTokens.get(i+1);
				int secondType = secondToken.getTokenType();
				
				boolean firstIsNumber = firstType==Token.TOKEN_NUMBER || firstType==Token.TOKEN_NUMBER_LITERAL || firstType==Token.TOKEN_VARIABLE;
				boolean secoundIsNumber = secondType==Token.TOKEN_NUMBER || secondType==Token.TOKEN_NUMBER_LITERAL || secondType==Token.TOKEN_VARIABLE;
				
				boolean firstIsCloseBreacket = firstType == Token.TOKEN_CLOSE_BRACKET;
				boolean secondIsOpenBreacket = secondType == Token.TOKEN_OPEN_BRACKET;
				
				if((firstIsNumber && secoundIsNumber)||(firstIsCloseBreacket && secondIsOpenBreacket)) {
					outVector.add(new Token("*"));
				}
			}
		}
		
		return outVector;
	}

	/**
	 * Calculates a expression.
	 * 
	 * @param postfixTokens The expression as a Vector of {@link Token}s in postfix Notation.
	 * 
	 * @return Returns the value of the expression as a double.
	 * @throws Exception
	 */
	public static double calculatePostfixTokens(Vector<Token> postfixTokens) throws Exception{
		Stack<Double> workStack = new Stack<>();
		Expressions expr = Calculator.expressions;
		
		
		for(Token currentToken: postfixTokens) {
			int currentType = currentToken.getTokenType();
			String currentText = currentToken.getTokenText();
			
			switch(currentType) {
			case Token.TOKEN_NUMBER:
				Double value = new Double(currentText);
				workStack.push(value);
				break;
				
			case Token.TOKEN_NUMBER_LITERAL: 
				expressions.Number number = expr.getNumber(currentText);
				Double literalValue = new Double(number.getValue());
				workStack.push(literalValue);
				break;
				
			case Token.TOKEN_FUNCTION:
				Function function = expr.getFunction(currentText);
				int parameterCount = function.getParameters();
				Vector<Double>parameterList = new Vector<>();
				int providedValues = workStack.size();
				
				boolean hasEnoughValues = parameterCount <= providedValues;
				
				if(hasEnoughValues) {
					for(int i=0; i<parameterCount;i++) {
						Double providedValue = workStack.pop();
						parameterList.add(providedValue);
					}
					
					Double result = new Double(function.useFunction(parameterList));
					workStack.push(result);
				} else {
					throw new Exception("Syntax Error. Function '"+currentText+"' requires "+parameterCount+" parameters. Only "+providedValues+" are provided.");
				}
				break;
				
				
			case Token.TOKEN_UNARY_OPERATOR: 
			case Token.TOKEN_OPERATOR:
				boolean isUnary	   = currentType==Token.TOKEN_UNARY_OPERATOR;
				
				Operator operator;
				
				if(isUnary) {
					operator = expr.getUnaryOperator(currentText);
				} else {
					operator = expr.getOperator(currentText);	
				}
				
				int operatorParameterCount = operator.getParameters();
				Vector<Double>operatorParameterList = new Vector<>();
				int operatorProvidedValues = workStack.size();
				
				boolean operatorHasEnoughValues = operatorParameterCount <= operatorProvidedValues;
				
				if(operatorHasEnoughValues) {
					for(int i=0; i<operatorParameterCount;i++) {
						Double providedValue = workStack.pop();
						operatorParameterList.add(providedValue);
					}					
					
					Double result = new Double(operator.useOperator(operatorParameterList));
					workStack.push(result);
				} else {
					throw new Exception("Syntax Error. Function '"+currentText+"' requires "+operatorParameterCount+" parameters. Only "+operatorProvidedValues+" are provided.");
				}
				break;
				
			case Token.TOKEN_OPEN_BRACKET:		//Falls through
			case Token.TOKEN_CLOSE_BRACKET:		//Falls through
			case Token.TOKEN_VARIABLE:			//Falls through
			case Token.TOKEN_ARGUMENT_SEPERATOR://Falls through
			default:
				throw new Exception("Parsing Error. Calculator can't handle Token '"+currentText+"'. Please convert to Postfix tokens.");
			}
		}
		
		double result = workStack.peek().doubleValue();
		return result;
	}
}
