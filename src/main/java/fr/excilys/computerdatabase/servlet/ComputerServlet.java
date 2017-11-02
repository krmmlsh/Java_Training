package fr.excilys.computerdatabase.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
			case GET_ALL: {
				request.setAttribute(COMPUTERS, computerService.getAllComputers());
				request.getRequestDispatcher(DASHBOARD).forward(request, response);
				break;
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
			request.setAttribute(COMPUTERS, computerService.getAllComputers());
			request.getRequestDispatcher(DASHBOARD).forward(request, response);

		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String requestType = request.getParameter(ACTION_TYPE);
		if (requestType != null) {
			switch (requestType) {
			case POST: {
				computerService.addComputer(request);
				request.setAttribute(COMPUTERS, computerService.getAllComputers());
				request.getRequestDispatcher(DASHBOARD).forward(request, response);
				break;
			}
			case DELETE: {
				String cbSelection = request.getParameter("selection");
				computerService.removeComputer(cbSelection);
				request.setAttribute(COMPUTERS, computerService.getAllComputers());
				request.getRequestDispatcher(DASHBOARD).forward(request, response);
				break;
			}
			case UPDATE: {
				request.setAttribute("computer", computerService.updateComputer(request));
				request.setAttribute("companies", companyServices.getAllCompanies());
				request.getRequestDispatcher("editComputer.jsp").forward(request, response);
			}
			}

		}

	}

}
