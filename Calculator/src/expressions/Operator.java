package expressions;

import java.util.Vector;

/**
 *  Stores a Operator, with its literal, the priority of the operator the number of parameters and the type of the operator (if it's left to right or not)
 *  the calculation formula.
 * 
 * @author Daedo
 */
public class Operator {
	private int priority;
	private String literal;
	private boolean isLeftToRight;
	private int parameters;
	
	public Operator(String newLiteral,int newPriority,boolean leftToRight,int newParameters) {
		this.isLeftToRight = leftToRight;
		this.literal = newLiteral;
		this.priority = newPriority;
		this.setParameters(newParameters);
	}

	public int getPriority() {
		return this.priority;
	}

	public void setPriority(int newPriority) {
		this.priority = newPriority;
	}

	public String getLiteral() {
		return this.literal;
	}

	public void setLiteral(String newLiteral) {
		this.literal = newLiteral;
	}

	public boolean isLeftToRight() {
		return this.isLeftToRight;
	}

	public void setLeftToRight(boolean newIsLeftToRight) {
		this.isLeftToRight = newIsLeftToRight;
	}

	public int getParameters() {
		return this.parameters;
	}

	public void setParameters(int newParameters) {
		this.parameters = newParameters;
	}
	
	/*
	 * DON'T USE THIS METHOD! IT IS USED TO CALCULATE THE EXPRESSION VALUE AND HAS TO BE OVERWRITTEN, BUT YOU SHOULD CALL THE useOperator METHOD INSTEAD,
	 * BECAUSE IT CHECKS FOR THE RIGHT PARAMETERCOUNT
	 * Not the nicest solution, but it works.
	 */
	/**
	 * The real operator function. Must be overwritten, but must not be called directly!
	 * 
	 * @param parameterList The list of Parameter as a Vector of {@link Double}s.
	 * @return Returns the result as a double.
	 * @throws Exception Throws an exception if a calculation error occurs or the method isn't overwritten.
	 */
	protected double calculateValue(Vector<Double> parameterList) throws Exception{
		throw new Exception("Must Override calculateValue");
	}
	
	/**
	 * Calculates the value of the function with the provided Parameters.
	 * 
	 * @param parameterList The list of Parameter as a Vector of {@link Double}s.
	 * @return	Returns the result as a double
	 * @throws Exception Throws an exception, if the wrong number of parameters are provided or a calculation error occurs.
	 */
	public double useOperator(Vector<Double> parameterList) throws Exception {
		int parameterCount = parameterList.size();
		if(this.parameters!=parameterCount) {
			throw new Exception("Invalid number of Parameters");
		}
		
		return calculateValue(parameterList);
	}
	
}
