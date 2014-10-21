package gui;

public class FunctionDescription {
	

	private String function;
	private String description;

	public FunctionDescription(String newFunction, String newDescription) {
		this.setFunction(newFunction);
		this.setDescription(newDescription);
	}
	
	public String getFunction() {
		return this.function;
	}
	
	public void setFunction(String newFunction) {
		this.function = newFunction;
	}
	
	public String getDescription() {
		return this.description;
	}
	
	public void setDescription(String newDescription) {
		this.description = newDescription;
	}
	
	@Override
	public String toString() {
		return this.function + ": "+ this.description;
	}
	
}
