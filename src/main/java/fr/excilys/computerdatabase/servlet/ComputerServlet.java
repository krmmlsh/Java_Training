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
			String delta = request.getParameter("plus");
			String currentPage = request.getParameter("page");
			if (currentPage == null ) {
				request.setAttribute("page", 0);
			}
			if (delta != null) {
				int page = Integer.valueOf(currentPage);
				int deltaInt = Integer.valueOf(delta);
				if (Integer.valueOf(delta) == 1) {
					request.setAttribute(COMPUTERS, computerService.getAllComputers(page, deltaInt));
					request.setAttribute("page", ++page);
				} else {
					if(page > 0) {
						deltaInt = -1;
					}
					request.setAttribute(COMPUTERS, computerService.getAllComputers(page, deltaInt));
					request.setAttribute("page", deltaInt + page);
				}
			} else {
				request.setAttribute(COMPUTERS, computerService.getAllComputers(0, 0));
			}
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
				if(request.getParameter("name") != null)
				computerService.addComputer(request);
				request.setAttribute(COMPUTERS, computerService.getAllComputers(0, 0));
				request.getRequestDispatcher(DASHBOARD).forward(request, response);
				break;
			}
			case DELETE: {
				String cbSelection = request.getParameter("selection");
				computerService.removeComputer(cbSelection);
				request.setAttribute(COMPUTERS, computerService.getAllComputers(0, 0));
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
