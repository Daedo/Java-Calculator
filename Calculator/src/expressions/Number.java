package expressions;

public class Number {
	private double value;
	private String literal;
	
	public Number(String newLiteral,double newValue) {
		this.value = newValue;
		this.literal = newLiteral;
	}

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}

	public String getLiteral() {
		return literal;
	}

	public void setLiteral(String literal) {
		this.literal = literal;
	}
}
