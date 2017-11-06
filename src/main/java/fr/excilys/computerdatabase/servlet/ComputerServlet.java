package fr.excilys.computerdatabase.servlet;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.excilys.computerdatabase.main.NbTotal;
import fr.excilys.computerdatabase.service.CompanyServices;
import fr.excilys.computerdatabase.service.ComputerServices;

public class ComputerServlet extends HttpServlet {

	private static final String DASHBOARD = "dashboard.jsp";

	private static final String COMPUTERS = "computers";

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final String ID = "ID";

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

	private NbTotal nbTotal = new NbTotal();

	private int currentPage = 0;

	private int limit = 10;

	private List<Integer> listOfPages() {
		
		if (currentPage <3 ) {
			return IntStream.range(1, 6).boxed().collect(Collectors.toList());
		}
		if (nbTotal.nomberOfComputer < currentPage - 2) {
			return IntStream.range(nbTotal.nomberOfComputer - 4, nbTotal.nomberOfComputer+1).boxed().collect(Collectors.toList());

		}
		return IntStream.range(currentPage-1, currentPage+4).boxed().collect(Collectors.toList());
	}
	
	private void pagination(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String delta = request.getParameter("plus");
		String currentPage = request.getParameter("page");
		String length = request.getParameter("length");

		if (length != null) {
			this.limit = Integer.valueOf(length);
			this.currentPage = 0;
		}
		if (currentPage != null) {
			this.currentPage = Integer.valueOf(currentPage)-1;
		}
		if (delta != null) {
			int deltaInt = Integer.valueOf(delta);
			if (!(this.currentPage == 0 && deltaInt < 0)) {
				this.currentPage += deltaInt;
			}
		}
		request.setAttribute(COMPUTERS, computerService.getAllComputers(this.currentPage, limit, nbTotal));
		request.setAttribute("nbTotal", nbTotal.nomberOfComputer);
		request.setAttribute("pages", listOfPages());
		request.getRequestDispatcher(DASHBOARD).forward(request, response);

	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String requestType = (String) request.getParameter(ACTION_TYPE);
		if (requestType != null) {
			switch (requestType) {
			case GET_BY_ID: {
				Integer id = (Integer) request.getAttribute(ID);
				request.setAttribute(COMPUTERS, computerService.getComputerById(id));
				request.getRequestDispatcher(DASHBOARD).forward(request, response);
				break;
			}
			case GET_BY_NAME: {
				String name = (String) request.getParameter("search");
				if (!name.isEmpty()) {
					request.setAttribute(COMPUTERS, computerService.getComputerByName(name));
					request.getRequestDispatcher(DASHBOARD).forward(request, response);
					break;
				}
			}
			case CREATE: {
				request.setAttribute("companies", companyServices.getAllCompanies());
				request.getRequestDispatcher("addComputer.jsp").forward(request, response);
				break;
			}
			case UPDATE: {
				Integer id = Integer.valueOf(request.getParameter("computerId"));
				request.setAttribute("computer", computerService.getComputerById(id));
				request.setAttribute("companies", companyServices.getAllCompanies());
				request.getRequestDispatcher("editComputer.jsp").forward(request, response);
				break;

			}
			}
		} else {
			pagination(request, response);
		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String requestType = request.getParameter(ACTION_TYPE);
		if (requestType != null) {
			switch (requestType) {
			case POST: {
				if (request.getParameter("name") != null)
					computerService.addComputer(request);
				break;
			}
			case DELETE: {
				String cbSelection = request.getParameter("selection");
				computerService.removeComputer(cbSelection);

				break;
			}
			case UPDATE: {
				request.setAttribute("computer", computerService.updateComputer(request));
				request.setAttribute("companies", companyServices.getAllCompanies());
				request.getRequestDispatcher("editComputer.jsp").forward(request, response);
			}
			}
			pagination(request, response);

		}

	}

}
