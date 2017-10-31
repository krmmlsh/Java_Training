package fr.excilys.computerdatabase.servlet;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.excilys.computerdatabase.main.Util;
import fr.excilys.computerdatabase.model.Computer;
import fr.excilys.computerdatabase.service.CompanyServices;
import fr.excilys.computerdatabase.service.ComputerServices;

public class EditComputerServlet extends HttpServlet {

	private final CompanyServices companyServices = CompanyServices.getCompanyServices();
	
	private final ComputerServices computerService = ComputerServices.getComputerServices();
	
	Computer computer;
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Integer id = Integer.valueOf(request.getParameter("computerId"));
		Optional<Computer> computerOptional = ComputerServlet
				.computers
				.stream()
				.filter(c -> c.getId() == id).findFirst();
		computer = computerOptional.get();
		request.setAttribute("computer", computer);
		request.setAttribute("companies", companyServices.getAllCompanies());

		request.getRequestDispatcher("editComputer.jsp").forward(request, response);

	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
			
			computer.setName(request.getParameter("computerName"));
			computer.setIntroducedDate(Util.convertStringToLocalDate(request.getParameter("introduced")));
			computer.setDiscontinuedDate(Util.convertStringToLocalDate(request.getParameter("discontinued")));
			computer.setCompId(Integer.valueOf(request.getParameter("companyId")));
			computer.setCompany(companyServices.getAllCompanies().get(computer.getCompId()));
			request.setAttribute("computer", computer);
			request.setAttribute("companies", companyServices.getAllCompanies());
			computerService.updateComputer(computer);
			request.getRequestDispatcher("editComputer.jsp").forward(request, response);
		}
}
