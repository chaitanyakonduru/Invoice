package com.example.invoiceapp.models;

public class Priority  {

	private int priority;
	
	public int getPriority() {
		return priority;
	}
	
	public void setPriority(int priority) {
		this.priority = priority;
	}
	
	public int compareTo(Priority another) {
		return (this.priority - another.getPriority());
	}
}