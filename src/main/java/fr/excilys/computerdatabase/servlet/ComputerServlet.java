package fr.excilys.computerdatabase.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

	private final ComputerServices computerService = ComputerServices.getComputerServices();

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String requestType = (String) request.getAttribute(ACTION_TYPE);
		if (requestType != null) {
			switch (requestType) {
			case GET_ALL:
				request.setAttribute("computers", computerService.getAllComputers());
				request.getRequestDispatcher("WEB-INF/dashboard.jsp").forward(request, response);
				break;
			case GET_BY_ID:
				String name = (String) request.getAttribute(NAME);
				request.setAttribute("computer", computerService.getComputerByName(name));
				break;
			case GET_BY_NAME:
				Integer id = (Integer) request.getAttribute(ID);
				request.setAttribute("computer", computerService.getComputerById(id));
				break;
			default:

			}
		}
		request.setAttribute("computers",computerService.getAllComputers());
		request.getRequestDispatcher("dashboard.jsp").forward(request, response);

	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String requestType = (String) request.getAttribute(ACTION_TYPE);
		switch (requestType) {
		case POST:
			// computerService.getAllComputers();
			break;
		case UPDATE:
			// String name = (String) request.getAttribute(NAME);
			// computerService.getComputerByName(name);
			break;
		case DELETE:
			Integer id = (Integer) request.getAttribute(ID);
			computerService.removeComputer(id);
			break;
		}

	}

}
