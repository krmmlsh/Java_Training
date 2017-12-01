package fr.excilys.computerdatabase.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class ComputerDTO {

	private int id;
	
	
	private String company;
	
	@NotNull
	@Size(min = 4, max = 14, message = "Please enter a valid name, size between 4 and 14 characters")
	private String name;
	

	@Pattern(regexp="^(((0[1-9]|[12]\\d|3[01])(\\/|-)(0[1-9]|1[012])(\\/|-)\\d\\d(\\d\\d)?|\\d\\d\\d\\d-(0[1-9]|1[012])-(0[1-9]|[12]\\d|3[01])))?$", message="WRONG DATE FORMAT")
	private String discontinued;
	
	@Pattern(regexp="^(((0[1-9]|[12]\\d|3[01])(\\/|-)(0[1-9]|1[012])(\\/|-)\\d\\d(\\d\\d)?|\\d\\d\\d\\d-(0[1-9]|1[012])-(0[1-9]|[12]\\d|3[01])))?$", message="WRONG DATE FORMAT")
	private String introduced;
	
	private int companyId;

	
	
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


	public String getIntroduced() {
		return introduced;
	}

	public void setIntroduced(String introduced) {
		this.introduced = introduced;
	}

	public String getDiscontinued() {
		return discontinued;
	}

	public void setDiscontinued(String discontinued) {
		this.discontinued = discontinued;
	}

	public int getCompanyId() {
		return companyId;
	}

	public void setCompanyId(int compId) {
		this.companyId = compId;
	}

	
	public static class Builder {
		private int id;
		
		private int compId;
		
		private String company;
		
		private String name;
		
		private String introducedDate;
		
		private String discontinuedDate;

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
        
        public int getCompId() {
        	return compId;
        }
        
        public Builder introducedDate(String introducedDate) {
            this.introducedDate = introducedDate;
            return this;
        }
        
        public Builder discontinuedDate(String discontinuedDate) {
            this.discontinuedDate = discontinuedDate;
            return this;
        }

        public Builder compId(int compId) {
            this.compId = compId;
            return this;
        }

        public ComputerDTO build() {
            return new ComputerDTO(this);
        }

    }
	public String toString() {
		return "Description of your computer : \n"
				+ "Id : " + id + "\n"
				+ "Name : " + name + "\n"
				+ "Brand : " + company + "\n"
				+ "Introduction date : " + introduced+ "\n"
				+  "Discontinued date : " + discontinued + "\n"; 
	}

    public ComputerDTO( String name, int companyId) {
		this.name = name;
		this.companyId = companyId;
	}
    
    public ComputerDTO() {
    	
    }

	public ComputerDTO( String name, String discontinued, String introduced,int companyId) {
		this.name = name;
		this.discontinued = discontinued;
		this.introduced = introduced;
		this.companyId = companyId;
	}

	private ComputerDTO(Builder b) {
        this.id = b.id;
        this.companyId = b.compId;
        this.name = b.name;
        this.company = b.company;
        this.introduced = b.introducedDate;
        this.discontinued = b.discontinuedDate;

    }


}
