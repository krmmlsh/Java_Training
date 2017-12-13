package fr.excilys.computerdatabase.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;



@Entity
@Table(name="description")
public class Description {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	@OneToOne
	private Company company;
	
	@OneToOne
	private User user;

	private String email;
	
	private String information;
	
	private String lastname;
	
	private String firstname;
	
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getInformation() {
		return information;
	}

	public void setInformation(String information) {
		this.information = information;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	
	
	public static class Builder {
		private int id;

		private String email;

		private String information;

		private String firstname;

		private String lastname;

		private User user;

		private Company company;

		public Builder id(int id) {
			this.id = id;
			return this;
		}
		
		public Builder user(User user) {
			this.user = user;
			return this;
		}
		
		public Builder company(Company company) {
			this.company = company;
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

		public Description build() {
			return new Description(this);
		}

	}

	private Description(Builder b) {

		this.id = b.id;
		this.information = b.information;
		this.email = b.email;
		this.lastname = b.lastname;
		this.firstname = b.firstname;
		this.user = b.user;
		this.company = b.company;



	}
	
	public Description() {}

	
}
