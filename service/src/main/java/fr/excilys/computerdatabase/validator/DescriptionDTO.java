package fr.excilys.computerdatabase.validator;

import java.time.LocalDate;

import javax.validation.constraints.Pattern;


public class DescriptionDTO {

	private int id;

	@Pattern(regexp = "^([a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,})?$", message = "Email in wrong format")
	private String email;

	private String information;

	private String firstname;

	private String lastname;

	private int user_id;

	private int company_id;
	
	private String company;

	public String getInformation() {
		return information;
	}

	public void setInformation(String information) {
		this.information = information;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getInformations() {
		return information;
	}

	public void setInformations(String information) {
		this.information = information;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public int getCompany_id() {
		return company_id;
	}

	public void setCompany_id(int company_id) {
		this.company_id = company_id;
	}
	
	public static class Builder {
		private int id;

		private String email;

		private String information;

		private String firstname;

		private String lastname;

		private int user_id;

		private int company_id;
		
		private String company;

		public Builder id(int id) {
			this.id = id;
			return this;
		}
		
		public Builder user_id(int user_id) {
			this.user_id = user_id;
			return this;
		}
		
		public Builder company_id(int company_id) {
			this.company_id = company_id;
			return this;
		}
		
		public Builder email(String email) {
			this.email = email;
			return this;
		}

		public Builder information(String information) {
			this.information = information;
			return this;
		}

		public Builder firstname(String firstname) {
			this.firstname = firstname;
			return this;
		}

		public Builder lastname(String lastname) {
			this.lastname = lastname;
			return this;
		}
		
		public Builder company(String company) {
			this.company = company;
			return this;
		}
		

		public DescriptionDTO build() {
			return new DescriptionDTO(this);
		}

	}

	private DescriptionDTO(Builder b) {

		this.id = b.id;
		this.information = b.information;
		this.email = b.email;
		this.lastname = b.lastname;
		this.firstname = b.firstname;
		this.user_id = b.user_id;
		this.company_id = b.company_id;
		this.company = b.company;
	}
	
	public DescriptionDTO() {}

}
