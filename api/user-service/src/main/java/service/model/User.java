package service.model;

public class User {

	private String first;
	private String last;
	private String email;
	private String phone;
	
	public User(){
		
	}
	
	public User(String first, String last, String email, String phone){
		this.first = first;
		this.last = last;
		this.email = email;
		this.phone = phone;
	}

	public String getFirst() {
		return first;
	}

	public String getLast() {
		return last;
	}

	public String getEmail() {
		return email;
	}

	public String getPhone() {
		return phone;
	}

	public void setFirst(String first) {
		this.first = first;
	}

	public void setLast(String last) {
		this.last = last;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
}
