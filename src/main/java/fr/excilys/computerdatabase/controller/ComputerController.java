package fr.excilys.computerdatabase.controller;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import fr.excilys.computerdatabase.main.NbTotal;
import fr.excilys.computerdatabase.service.CompanyServices;
import fr.excilys.computerdatabase.service.ComputerServices;
import fr.excilys.computerdatabase.servlet.ComputerDTO;

@RequestMapping("/computer")
@Controller
public class ComputerController {
	private static final String DASHBOARD = "dashboard";

	private static final String COMPUTERS = "computers";

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

	@Autowired
	private ComputerServices computerServices;

	@Autowired
	private CompanyServices companyServices;

	private NbTotal nbTotal = new NbTotal();

	private int currentPage = 0;

	private int limit = 10;

	ApplicationContext context;

	private List<ComputerDTO> pagination(String plus, String page, String length, Model model) {

		if (length != null) {
			this.limit = Integer.valueOf(length);
			this.currentPage = 0;
		}
		if (page != null) {
			this.currentPage = Integer.valueOf(page) - 1;
			System.out.println(page);
		}
		if (plus != null) {
			int deltaInt = Integer.valueOf(plus);
			if (!(this.currentPage == 0 && deltaInt < 0)) {
				this.currentPage += deltaInt;
			}
		}
		model.addAttribute("pages", listOfPages());
		List<ComputerDTO> list = computerServices.getAllComputers(this.currentPage, limit, nbTotal);
		model.addAttribute("nbTotal", nbTotal.nomberOfComputer);
		return list;
	}	
	
	private List<Integer> listOfPages() {

		if (currentPage < 3) {
			return IntStream.range(1, 6).boxed().collect(Collectors.toList());
		}
		if (nbTotal.nomberOfComputer / limit - 2 <= currentPage) {
			return IntStream.range(nbTotal.nomberOfComputer / limit - 4, nbTotal.nomberOfComputer / limit + 1).boxed()
					.collect(Collectors.toList());

		}
		return IntStream.range(currentPage - 1, currentPage + 4).boxed().collect(Collectors.toList());
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public String getComputerByPaging(@RequestParam( required = false) String plus, @RequestParam( required = false) String page,
			@RequestParam( required = false) String length, Model model) {
		
		System.out.println("Ca va mal ma jeule");
		model.addAttribute("computers", pagination(plus, page, length, model));
		model.addAttribute("companies", companyServices.getAllCompanies());
		return "dashboard";
	}
	
	
	@RequestMapping(params = ACTION_TYPE + "=" + GET_BY_NAME, method = RequestMethod.GET)
	public String getComputerByName(@RequestParam String search, Model model) {
		model.addAttribute("computers", computerServices.getComputerByName(search));
		model.addAttribute("companies", companyServices.getAllCompanies());
		return DASHBOARD;
	}

	@RequestMapping(params = ACTION_TYPE + "=" + CREATE, method = RequestMethod.GET)
	public ModelAndView createPage() {
		return new ModelAndView("addComputer", "companies", companyServices.getAllCompanies());
	}

	@RequestMapping(params = ACTION_TYPE + "=" + UPDATE, method = RequestMethod.GET)
	public String updatePage(@RequestParam int computerId, Model model) {
		model.addAttribute("computer", computerServices.getComputerById(computerId));
		model.addAttribute("companies", companyServices.getAllCompanies());
		return "editComputer";
	}
	
	@RequestMapping(params = ACTION_TYPE + "=" + POST, method = RequestMethod.POST)
	public String createComputer(HttpServletRequest request, Model model) {
		if (computerServices.addComputer(request) == null) {
			request.setAttribute("error", "Something went wrong, please try again with correct inputs");
		}
		model.addAttribute("computers", pagination(null, null, null, model));
		model.addAttribute("companies", companyServices.getAllCompanies());
		return DASHBOARD;
	}
	
	@RequestMapping(params = ACTION_TYPE + "=" + DELETE, method = RequestMethod.POST)
	public String deleteComputer(@RequestParam String selection, Model model) {
		computerServices.removeComputer(selection);

		model.addAttribute("computers", pagination(null, null, null, model));
		model.addAttribute("companies", companyServices.getAllCompanies());
		return DASHBOARD;
	}

	@RequestMapping(params = ACTION_TYPE + "=" + UPDATE, method = RequestMethod.POST)
	public String updateComputer(HttpServletRequest request, Model model) {
		if (computerServices.updateComputer(request) == null) {
			model.addAttribute("error", "Something went wrong, please try again with correct inputs");
		}

		model.addAttribute("computers", pagination(null, null, null, model));
		model.addAttribute("companies", companyServices.getAllCompanies());
		return DASHBOARD;
	}
	
	@RequestMapping(params = ACTION_TYPE + "=" + "deleteFormCompany", method = RequestMethod.POST)
	public String deleteCompany(@RequestParam int companyIdDeleted, Model model) {
		companyServices.deleteCompany(companyIdDeleted);
		model.addAttribute("computers", pagination(null, null, null, model));
		model.addAttribute("companies", companyServices.getAllCompanies());
		return DASHBOARD;
	}
/*
	@Override
	public void init() {
		context = new AnnotationConfigApplicationContext(Config.class);
		context.getAutowireCapableBeanFactory().autowireBean(this);
	}
*//*


	






/*	
	
*/
}
