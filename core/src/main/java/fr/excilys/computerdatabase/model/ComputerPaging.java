package fr.excilys.computerdatabase.model;

import java.util.List;

public class ComputerPaging {

		
	private int computerNumber;
	private List<ComputerDTO> computerList;
	private List<Integer> currentPage;
	
	
	
	public int getComputerNumber() {
		return computerNumber;
	}



	public List<ComputerDTO> getComputerList() {
		return computerList;
	}



	public void setComputerList(List<ComputerDTO> computerList) {
		this.computerList = computerList;
	}



	public List<Integer> getCurrentPage() {
		return currentPage;
	}



	public void setCurrentPage(List<Integer> currentPage) {
		this.currentPage = currentPage;
	}



	public void setComputerNumber(int computerNumber) {
		this.computerNumber = computerNumber;
	}

	public ComputerPaging ()
	{}
	

	public ComputerPaging(List<ComputerDTO> computerList, int computerNumber, List<Integer> currentPage) {
		this.computerList = computerList;
		this.computerNumber = computerNumber;
		this.currentPage = currentPage;
	}

}
