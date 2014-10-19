package expressions;

import java.util.Collections;
import java.util.Comparator;
import java.util.Vector;

/**
 * Stores all expression parts, that the calculator knows.
 * @author Daedo
 */
public class Expressions {
	private Vector<String> expressionList;
	private Vector<Number> numberList;
	private Vector<Operator> operatorList;
	private Vector<Function> functionList;
	private Vector<Operator> unaryList; 

	public Expressions() {
		this.expressionList = new Vector<>();
		this.expressionList.add(",");
		this.expressionList.add("(");
		this.expressionList.add(")");

		//Unary Operators
		this.unaryList = new Vector<>();
		this.unaryList.add(new Operator("+", 0, false, 1){
			@Override
			protected double calculateValue(Vector<Double> parameterList) throws Exception {
				double parameter = parameterList.get(0).doubleValue();
				return parameter;
			}
		});
		this.unaryList.add(new Operator("-", 0, false, 1){
			@Override
			protected double calculateValue(Vector<Double> parameterList) throws Exception {
				double parameter = parameterList.get(0).doubleValue();
				return parameter*(-1);
			}
		});

		//Number Constants
		this.numberList = new Vector<>();
		this.numberList.add(new Number("pi", Math.PI));
		this.numberList.add(new Number("tau", Math.PI*2.0));
		this.numberList.add(new Number("e", Math.E));
		this.numberList.add(new Number("phi", (1.0+Math.sqrt(5.0))/2.0));

		//Binary Operators
		this.operatorList = new Vector<>();
		this.operatorList.add(new Operator("+", 0, false, 2){
			@Override
			protected double calculateValue(Vector<Double> parameterList) throws Exception {
				double parameter1 = parameterList.get(0).doubleValue();
				double parameter2 = parameterList.get(1).doubleValue();
				return parameter1+parameter2;
			}
		});

		this.operatorList.add(new Operator("-", 0, true , 2){
			@Override
			protected double calculateValue(Vector<Double> parameterList) throws Exception {
				double parameter1 = parameterList.get(0).doubleValue();
				double parameter2 = parameterList.get(1).doubleValue();
				return parameter2-parameter1;
			}
		});

		this.operatorList.add(new Operator("*", 1, false, 2){
			@Override
			protected double calculateValue(Vector<Double> parameterList) throws Exception {
				double parameter1 = parameterList.get(0).doubleValue();
				double parameter2 = parameterList.get(1).doubleValue();
				return parameter1*parameter2;
			}
		});

		this.operatorList.add(new Operator("/", 1, true , 2){
			@Override
			protected double calculateValue(Vector<Double> parameterList) throws Exception {
				double parameter1 = parameterList.get(0).doubleValue();
				double parameter2 = parameterList.get(1).doubleValue();
				return parameter2/parameter1;
			}
		});

		this.operatorList.add(new Operator("^", 2, false, 2){
			@Override
			protected double calculateValue(Vector<Double> parameterList) throws Exception {
				double parameter1 = parameterList.get(0).doubleValue();
				double parameter2 = parameterList.get(1).doubleValue();
				return Math.pow(parameter2, parameter1);
			}
		});

		//Functions
		this.functionList = new Vector<>();

		this.functionList.add(new Function("sin", 1){
			@Override
			protected double calculateValue(Vector<Double> parameterList) throws Exception {
				double parameter1 = parameterList.get(0).doubleValue();
				return Math.sin(parameter1);
			}
		});

		this.functionList.add(new Function("cos", 1){
			@Override
			protected double calculateValue(Vector<Double> parameterList) throws Exception {
				double parameter1 = parameterList.get(0);
				return Math.cos(parameter1);
			}
		});

		this.functionList.add(new Function("tan", 1){
			@Override
			protected double calculateValue(Vector<Double> parameterList) throws Exception {
				double parameter1 = parameterList.get(0);
				return Math.tan(parameter1);
			}
		});

		this.functionList.add(new Function("log10", 1){
			@Override
			protected double calculateValue(Vector<Double> parameterList) throws Exception {
				double parameter1 = parameterList.get(0);
				return Math.log10(parameter1);
			}
		});

		this.functionList.add(new Function("ln", 1){
			@Override
			protected double calculateValue(Vector<Double> parameterList) throws Exception {
				double parameter1 = parameterList.get(0);
				return Math.log(parameter1);
			}
		});

		this.functionList.add(new Function("ceil", 1){
			@Override
			protected double calculateValue(Vector<Double> parameterList) throws Exception {
				double parameter1 = parameterList.get(0);
				return Math.ceil(parameter1);
			}
		});

		this.functionList.add(new Function("floor", 1){
			@Override
			protected double calculateValue(Vector<Double> parameterList) throws Exception {
				double parameter1 = parameterList.get(0);
				return Math.floor(parameter1);
			}
		});

		this.functionList.add(new Function("round", 1){
			@Override
			protected double calculateValue(Vector<Double> parameterList) throws Exception {
				double parameter1 = parameterList.get(0);
				return Math.round(parameter1);
			}
		});

		int numberCount = this.numberList.size();
		for(int i = 0;i<numberCount;i++) {
			Number currentNumber = this.numberList.get(i);
			String literal = currentNumber.getLiteral();
			this.expressionList.add(literal);
		}

		int operatorCount = this.operatorList.size();
		for(int i = 0;i<operatorCount;i++) {
			Operator currentOperator = this.operatorList.get(i);
			String literal = currentOperator.getLiteral();
			this.expressionList.add(literal);
		}

		int functionCount = this.functionList.size();
		for(int i = 0;i<functionCount;i++) {
			Function currentFunction = this.functionList.get(i);
			String literal = currentFunction.getLiteral();
			this.expressionList.add(literal);
		}

		int unaryCount = this.unaryList.size();
		for(int i = 0;i<unaryCount;i++) {
			Operator currentOperator = this.unaryList.get(i);
			String literal = currentOperator.getLiteral();
			this.expressionList.add(literal);
		}

		//Sort by length(longest first)
		//We check from long to short in order to prevent errors like this:
		//Expressions log and log10; log10 is provided, log is checked first and gets a match
		Comparator<String> comp = new Comparator<String>() {
			@Override
			public int compare(String o1, String o2) {
				if(o1.length() > o2.length()) {
					return -1;
				}

				if(o1.length() < o2.length()) {
					return 1;
				}

				return 0;
			}
		};

		Collections.sort(this.expressionList, comp);
	}

	public int getExpressionCount() {
		return this.expressionList.size();
	}

	public String getExpression(int nbr) {
		return this.expressionList.get(nbr);
	}

	/**
	 * Checks if a String starts with a known expression, if so it is returned, else it just returns null. 
	 * 
	 * @param text The text you want to check as a String.
	 * @return Returns either the matching expression or null;
	 */
	public String getMatchingStartExpression(String text) {

		String matchedExpression = null;
		int expressionCount = getExpressionCount();

		for (int i = 0; i < expressionCount; i++) {
			// Look for a matching Expression
			String currentExpression = getExpression(i);

			boolean isMatchingExpression = text.startsWith(currentExpression);

			if (isMatchingExpression) {
				matchedExpression = currentExpression;
				break;
			}
		}
		return matchedExpression;
	}

	/**
	 * Checks if a String matches a known expression.
	 * @param text The expression literal as a String
	 * @return Returns a boolean depending on weather it matches or not
	 */
	public boolean matchesExpression(String text) {

		int expressionCount = getExpressionCount();
		for(int i = 0;i<expressionCount;i++) {
			String currentExpression = getExpression(i);

			boolean isMatchingExpression = text.equals(currentExpression);

			if (isMatchingExpression) {
				return true;
			}
		}

		return false;
	}

	/**
	 * Checks if a String matches a known number literal.
	 * @param text The expression literal as a String
	 * @return Returns a boolean depending on weather it matches or not
	 */
	public boolean matchesNumber(String text) {
		int numberCount = this.numberList.size();
		for(int i = 0;i<numberCount;i++) {
			String currentNumber = this.numberList.get(i).getLiteral();

			boolean isMatchingExpression = text.equals(currentNumber);

			if (isMatchingExpression) {
				return true;
			}
		}

		return false;
	}

	/**
	 * Returns the {@link Number} instance with the provided Literal.
	 * If no such such number exists Null is returned.
	 * 
	 * @param literal The Literal as a String
	 * @return Returns the Number instance with the provided Literal or Null
	 */
	public Number getNumber (String literal) {
		int numberCount = this.numberList.size();
		for(int i = 0;i<numberCount;i++) {
			Number currentNumber = this.numberList.get(i);
			String currentText = currentNumber.getLiteral();

			boolean isMatchingExpression = literal.equals(currentText);

			if (isMatchingExpression) {
				return currentNumber;
			}
		}

		return null;
	}

	/**
	 * Checks if a String matches a known binary operator literal.
	 * @param text The expression literal as a String
	 * @return Returns a boolean depending on weather it matches or not
	 */
	public boolean matchesOperator(String text) {
		int operatorCount = this.operatorList.size();
		for(int i = 0;i<operatorCount;i++) {
			String currentOperator = this.operatorList.get(i).getLiteral();

			boolean isMatchingExpression = text.equals(currentOperator);

			if (isMatchingExpression) {
				return true;
			}
		}

		return false;
	}

	/**
	 * Returns the {@link Operator} instance with the provided Literal.
	 * If no such such operator exists Null is returned.
	 * 
	 * @param literal The Literal as a String
	 * @return Returns the Operator instance with the provided Literal or Null
	 */
	public Operator getOperator(String literal) {
		int operatorCount = this.operatorList.size();
		for(int i = 0;i<operatorCount;i++) {
			Operator currentOperator = this.operatorList.get(i);
			String currentText = currentOperator.getLiteral();

			boolean isMatchingExpression = literal.equals(currentText);

			if (isMatchingExpression) {
				return currentOperator;
			}
		}

		return null;
	}

	/**
	 * Checks if a String matches a known function literal.
	 * @param text The expression literal as a String
	 * @return Returns a boolean depending on weather it matches or not
	 */
	public boolean matchesFunction(String text) {

		int functionCount = this.functionList.size();
		for(int i = 0;i<functionCount;i++) {
			String currentFunction = this.functionList.get(i).getLiteral();

			boolean isMatchingExpression = text.equals(currentFunction);

			if (isMatchingExpression) {
				return true;
			}
		}

		return false;
	}

	/**
	 * Returns the {@link Function} instance with the provided Literal.
	 * If no such such function exists Null is returned.
	 * 
	 * @param literal The Literal as a String
	 * @return Returns the Function instance with the provided Literal or Null
	 */
	public Function getFunction (String literal) {
		int functionCount = this.functionList.size();
		for(int i = 0;i<functionCount;i++) {
			Function currentFunction = this.functionList.get(i);
			String currentText = currentFunction.getLiteral();

			boolean isMatchingExpression = literal.equals(currentText);

			if (isMatchingExpression) {
				return currentFunction;
			}
		}

		return null;
	}

	/**
	 * Checks if a String matches a known unary operator literal.
	 * @param text The expression literal as a String
	 * @return Returns a boolean depending on weather it matches or not
	 */
	public boolean matchesUnaryOperator(String text) {
		int unaryCount = this.unaryList.size();
		for(int i = 0;i<unaryCount;i++) {
			String currentUnaryOperator = this.unaryList.get(i).getLiteral();

			boolean isMatchingExpression = text.equals(currentUnaryOperator);

			if (isMatchingExpression) {
				return true;
			}
		}

		return false;
	}

	/**
	 * Returns the unary {@link Operator} instance with the provided Literal.
	 * If no such such operator exists Null is returned.
	 * 
	 * @param literal The Literal as a String
	 * @return Returns the unary Operator instance with the provided Literal or Null
	 */
	public Operator getUnaryOperator(String literal) {
		int operatorCount = this.unaryList.size();
		for(int i = 0;i<operatorCount;i++) {
			Operator currentOperator = this.unaryList.get(i);
			String currentText = currentOperator.getLiteral();

			boolean isMatchingExpression = literal.equals(currentText);

			if (isMatchingExpression) {
				return currentOperator;
			}
		}

		return null;
	}
}
