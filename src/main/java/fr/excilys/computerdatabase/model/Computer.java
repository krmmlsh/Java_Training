package fr.excilys.computerdatabase.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import fr.excilys.computerdatabase.main.LocalDateConverter;

/**
 * Computer Model
 * 
 * @author krmmlsh
 *
 */

@Entity
@Table(name = "computer")
public class Computer {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	@ManyToOne
	private Company company;

	@Column(name = "name")
	private String name;

	@Convert(converter = LocalDateConverter.class)
	@Column(name = "introduced")
	private LocalDate introduced;

	@Convert(converter = LocalDateConverter.class)
	@Column(name = "discontinued")
	private LocalDate discontinued;

	public Computer() {
	}

	public Computer(String name, Company company, LocalDate introducedDate, LocalDate discontinuedDate) {
		this.company = company;
		this.name = name;
		this.introduced = introducedDate;
		this.discontinued = discontinuedDate;
	}

	public Computer(String name, LocalDate introducedDate, LocalDate discontinuedDate) {
		this.name = name;
		this.introduced = introducedDate;
		this.discontinued = discontinuedDate;
	}

	public Computer(String name) {
		this.name = name;
	}

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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public LocalDate getIntroducedDate() {
		return introduced;
	}

	public void setIntroducedDate(LocalDate introducedDate) {
		this.introduced = introducedDate;
	}

	public LocalDate getDiscontinuedDate() {
		return discontinued;
	}

	public void setDiscontinuedDate(LocalDate discontinuedDate) {
		this.discontinued = discontinuedDate;
	}

	public String toString() {
		return "Description of your computer : \n" + "Id : " + id + "\n" + "Name : " + name + "\n" + "Brand : "
				+ company + "\n" + "Introduction date : " + introduced + "\n" + "Discontinued date : " + discontinued
				+ "\n";
	}

	@Override
	public boolean equals(Object o) {
		if (o.getClass() != this.getClass()) {
			return false;
		}
		Computer c = (Computer) o;
		return c.name.equals(name) && c.id == id;
	}

	public static class Builder {
		private int id;

		private Company company;

		private String name;

		private LocalDate introducedDate;

		private LocalDate discontinuedDate;

		public Builder id(int id) {
			this.id = id;
			return this;
		}

		public Builder company(Company company) {
			this.company = company;
			return this;
		}

		public Builder name(String name) {
			this.name = name;
			return this;
		}

		public Builder introducedDate(LocalDate introducedDate) {
			this.introducedDate = introducedDate;
			return this;
		}

		public Builder discontinuedDate(LocalDate discontinuedDate) {
			this.discontinuedDate = discontinuedDate;
			return this;
		}

		public Computer build() {
			return new Computer(this);
		}

	}

	private Computer(Builder b) {
		this.id = b.id;
		this.name = b.name;
		this.company = b.company;
		this.introduced = b.introducedDate;
		this.discontinued = b.discontinuedDate;

	}

}
