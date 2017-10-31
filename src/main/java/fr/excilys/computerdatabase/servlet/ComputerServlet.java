package fr.excilys.computerdatabase.servlet;

import java.io.IOException;
import java.util.List;
import java.util.stream.Stream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.excilys.computerdatabase.main.Util;
import fr.excilys.computerdatabase.model.Computer;
import fr.excilys.computerdatabase.service.CompanyServices;
import fr.excilys.computerdatabase.service.ComputerServices;

public class ComputerServlet extends HttpServlet {

	private static final String ID = "ID";

	private static final String NAME = "NAME";

	private static final String ACTION_TYPE = "ACTION_TYPE";

	public static final String GET_BY_ID = "getId";

	public static final String GET_BY_NAME = "getName";

	public static final String GET_ALL = "getAll";

	public static final String POST = "post";

	public static final String DELETE = "delete";

	public static final String UPDATE = "update";

	public static final String CREATE = "create";

	private final ComputerServices computerService = ComputerServices.getComputerServices();
	
	private final CompanyServices companyServices = CompanyServices.getCompanyServices();

	public static List<Computer> computers;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String requestType = (String) request.getParameter(ACTION_TYPE);
		if (requestType != null) {
			switch (requestType) {
			case GET_BY_ID: {
				Integer id = (Integer) request.getAttribute(ID);
				request.setAttribute("computers", computerService.getComputerById(id));
				request.getRequestDispatcher("dashboard.jsp").forward(request, response);
				break;
			}
			case GET_BY_NAME: {
				String name = (String) request.getParameter("search");
				if (!name.isEmpty()) {
					computers = computerService.getComputerByName(name);
					request.setAttribute("computers", computers);
					request.getRequestDispatcher("dashboard.jsp").forward(request, response);
					break;
				}
			}
			case GET_ALL: {
				computers = computerService.getAllComputers();
				request.setAttribute("computers", computers);
				request.getRequestDispatcher("dashboard.jsp").forward(request, response);
				break;
			}
			case CREATE: {
				request.setAttribute("companies", companyServices.getAllCompanies());
				request.getRequestDispatcher("addComputer.jsp").forward(request, response);
			}
			}
		} else {
			computers = computerService.getAllComputers();
			request.setAttribute("computers", computers);
			request.getRequestDispatcher("dashboard.jsp").forward(request, response);

		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Computer computer = new Computer();
		String requestType = (String) request.getParameter(ACTION_TYPE);
		switch (requestType) {
		case POST:
			computer.setName(request.getParameter("computerName"));
			computer.setIntroducedDate(Util.convertStringToLocalDate(request.getParameter("introduced")));
			computer.setDiscontinuedDate(Util.convertStringToLocalDate(request.getParameter("discontinued")));
			computer.setCompId(Integer.valueOf(request.getParameter("companyId")));
			computerService.addComputer(computer);
			computers = computerService.getAllComputers();
			request.setAttribute("computers", computers);
			request.getRequestDispatcher("dashboard.jsp").forward(request, response);
			break;
		case DELETE:
			Integer id = (Integer) request.getAttribute(ID);
			computerService.removeComputer(id);
			break;
		}

	}

}
