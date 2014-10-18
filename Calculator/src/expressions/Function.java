package expressions;

import java.util.Vector;

public class Function {
	private int parameters;
	private String literal;
	public Function(String newLiteral,int newParameters) {
		this.parameters = newParameters;
		this.literal = newLiteral;
	}
	public int getParameters() {
		return this.parameters;
	}
	public void setParameters(int newParameters) {
		this.parameters = newParameters;
	}
	public String getLiteral() {
		return this.literal;
	}
	public void setLiteral(String newLiteral) {
		this.literal = newLiteral;
	}
	
	/*
	 * DON'T USE THIS METHOD! IT IS USED TO CALCULATE THE EXPRESSION VALUE AND HAS TO BE OVERWRITTEN, BUT YOU SHOULD CALL THE useFunction METHOD INSTEAD,
	 * BECAUSE IT CHECKS FOR THE RIGHT PARAMETERCOUNT
	 * Not the nicest solution, but it works.
	 */
	protected double calculateValue(Vector<Double> parameterList) throws Exception{
		throw new Exception("Must Override calculateValue");
	}
	
	public double useFunction(Vector<Double> parameterList) throws Exception {
		int parameterCount = parameterList.size();
		if(this.parameters!=parameterCount) {
			throw new Exception("Invalid number of Parameters");
		}
		
		return calculateValue(parameterList);
	}
}
