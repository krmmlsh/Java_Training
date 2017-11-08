package fr.excilys.computerdatabase.model;

import java.time.LocalDate;


/**
 * Computer Model
 * @author krmmlsh
 *
 */
public class Computer {

	private int id;
	
	private String company;
	
	private String name;
	
	private LocalDate introducedDate;
	
	private LocalDate discontinuedDate;
	
	private int compId;
	
	public Computer() {
	}

	public Computer( String name, String company, LocalDate introducedDate, LocalDate discontinuedDate) {
		this.company = company;
		this.name = name;
		this.introducedDate = introducedDate;
		this.discontinuedDate = discontinuedDate;
	}

	public Computer( String name, int compId, LocalDate introducedDate, LocalDate discontinuedDate) {
		this.compId = compId;
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

	public LocalDate getIntroducedDate() {
		return introducedDate;
	}

	public void setIntroducedDate(LocalDate introducedDate) {
		this.introducedDate = introducedDate;
	}

	public LocalDate getDiscontinuedDate() {
		return discontinuedDate;
	}

	public void setDiscontinuedDate(LocalDate discontinuedDate) {
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
	
	public static class Builder {
		private int id;
		
		private int compId;
		
		private String company;
		
		private String name;
		
		private LocalDate introducedDate;
		
		private LocalDate discontinuedDate;

        public Builder id(int id) {
            this.id = id;
            return this;
        }

        public Builder company(String company) {
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

        public Builder compId(int compId) {
            this.compId = compId;
            return this;
        }

        public Computer build() {
            return new Computer(this);
        }

    }
	
    private Computer(Builder b) {
        this.id = b.id;
        this.compId = b.compId;
        this.name = b.name;
        this.company = b.company;
        this.introducedDate = b.introducedDate;
        this.discontinuedDate = b.discontinuedDate;

    }

}
