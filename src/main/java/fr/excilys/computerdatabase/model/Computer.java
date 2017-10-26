package fr.excilys.computerdatabase.model;

import java.sql.Timestamp;


/**
 * Computer Model
 * @author krmmlsh
 *
 */
public class Computer {

	private int id;
	
	private String company;
	
	private String name;
	
	private Timestamp introducedDate;
	
	private Timestamp discontinuedDate;
	
	private int compId;
	
	public Computer() {
	}

	public Computer( String name, String company, Timestamp introducedDate, Timestamp discontinuedDate) {
		this.company = company;
		this.name = name;
		this.introducedDate = introducedDate;
		this.discontinuedDate = discontinuedDate;
	}

	public Computer(String name, int i) {
		this.name = name;
		compId = i;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Timestamp getIntroducedDate() {
		return introducedDate;
	}

	public void setIntroducedDate(Timestamp introducedDate) {
		this.introducedDate = introducedDate;
	}

	public Timestamp getDiscontinuedDate() {
		return discontinuedDate;
	}

	public void setDiscontinuedDate(Timestamp discontinuedDate) {
		this.discontinuedDate = discontinuedDate;
	}
	
	
	public String toString() {
		return "Description of your computer : \n"
				+ "Id : " + id + "\n"
				+ "Name : " + name + "\n"
				+ "Brand : " + company + "\n"
				+ "Introduction date : " + introducedDate+ "\n"
				+  "Discontinued date : " + discontinuedDate + "\n"; 
	}

	public int getCompId() {
		return compId;
	}

	public void setCompId(int comp_id) {
		this.compId = comp_id;
	}
	
	@Override
	public boolean equals (Object o) {
		if (o.getClass() != this.getClass()) {
			return false;
		}
		Computer c = (Computer)o;
		return c.name.equals(name) && c.id == id;
	}

}
