package com.example.invoiceapp.models;

public class PurchasedProduct {

	private int id;
	private String invoice_prodcutid;
	private String invoiceId;
	private String product_id;
	private String productCost;
	private String productQuantity;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getInvoice_prodcutid() {
		return invoice_prodcutid;
	}
	public void setInvoice_prodcutid(String invoice_prodcutid) {
		this.invoice_prodcutid = invoice_prodcutid;
	}
	public String getInvoiceId() {
		return invoiceId;
	}
	public void setInvoiceId(String invoiceId) {
		this.invoiceId = invoiceId;
	}
	public String getProduct_id() {
		return product_id;
	}
	public void setProduct_id(String product_id) {
		this.product_id = product_id;
	}
	public String getProductCost() {
		return productCost;
	}
	public void setProductCost(String productCost) {
		this.productCost = productCost;
	}
	public String getProductQuantity() {
		return productQuantity;
	}
	public void setProductQuantity(String productQuantity) {
		this.productQuantity = productQuantity;
	}
	
	
}
