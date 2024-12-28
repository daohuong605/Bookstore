package dto;

public class customer {
	private String customerID;
	private String customerName;
	private String email;
	private String phone;
	private String address;
	
	public customer(String customerID, String customerName, String email, String phone, String address) {
		this.customerID=(customerID);
		this.customerName=(customerName);
		this.email=(email);
		this.phone=(phone);
		this.address=address;
	}

	public String getCustomerID() {
		return customerID;
	}
	
	public String getCustomerName() {
		return customerName;
	}
	
	public String getEmail() {
		return email;
	}
	
	public String getPhone() {
		return phone;
	}
	
	public String getAddress() {
		return address;
	}
}
