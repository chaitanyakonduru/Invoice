package com.example.invoiceapp.models;

public class Invoice extends Priority{

	private int id;
	private String invoiceId;
	private String customerId;
	private String purchased_date;
	private boolean paid;
	private String paymentMode;
	private String totalAmount;
	private String dues;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getInvoiceId() {
		return invoiceId;
	}
	public void setInvoiceId(String invoiceId) {
		this.invoiceId = invoiceId;
	}
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	public String getPurchased_date() {
		return purchased_date;
	}
	public void setPurchased_date(String purchased_date) {
		this.purchased_date = purchased_date;
	}
	public boolean isPaid() {
		return paid;
	}
	public void setPaid(boolean paid) {
		this.paid = paid;
	}
	public String getPaymentMode() {
		return paymentMode;
	}
	public void setPaymentMode(String paymentMode) {
		this.paymentMode = paymentMode;
	}
	public String getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(String totalAmount) {
		this.totalAmount = totalAmount;
	}
	public String getDues() {
		return dues;
	}
	public void setDues(String dues) {
		this.dues = dues;
	}
	
	
}
