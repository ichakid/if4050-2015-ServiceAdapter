package service;

public class Error {
	String error;
	
	public Error(String error) {
		this.error = error;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	@Override
	public String toString() {
		return "Error [error=" + error + "]";
	}
}
