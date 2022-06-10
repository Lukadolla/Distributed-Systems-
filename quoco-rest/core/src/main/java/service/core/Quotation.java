package service.core;

import  java.io.Serializable;

/**
 * Class to store the quotations returned by the quotation services
 * 
 * @author Rem
 *
 */
public class Quotation implements Serializable {

	public Quotation(String company, String reference, double price) {
		this.company = company;
		this.reference = reference;
		this.price = price;
	}

	public Quotation(){

	}

	private String company;
	private String reference;
	private double price;

	public void setCompany(String company) {
		this.company = company;
	}

	public void setReference(String reference) {
		this.reference = reference;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getCompany() {
		return company;
	}

	public String getReference() {
		return reference;
	}

	public double getPrice() {
		return price;
	}
}
