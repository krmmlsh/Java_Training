package fr.excilys.computerdatabase.servlet;

public class ComputerDTO {

	private int id;
	
	private String company;
	
	private String name;
	
	private String introducedDate;
	
	private String discontinuedDate;
	
	private int compId;

	
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

	public String getIntroducedDate() {
		return introducedDate;
	}

	public void setIntroducedDate(String introducedDate) {
		this.introducedDate = introducedDate;
	}

	public String getDiscontinuedDate() {
		return discontinuedDate;
	}

	public void setDiscontinuedDate(String discontinuedDate) {
		this.discontinuedDate = discontinuedDate;
	}

	public int getCompId() {
		return compId;
	}

	public void setCompId(int compId) {
		this.compId = compId;
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


    private ComputerDTO(Builder b) {
        this.id = b.id;
        this.compId = b.compId;
        this.name = b.name;
        this.company = b.company;
        this.introducedDate = b.introducedDate;
        this.discontinuedDate = b.discontinuedDate;

    }
	
}
