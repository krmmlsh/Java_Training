package fr.excilys.computerdatabase.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import fr.excilys.computerdatabase.main.NbTotal;
import fr.excilys.computerdatabase.model.Company;
import fr.excilys.computerdatabase.model.ComputerDTO;
import fr.excilys.computerdatabase.service.CompanyServices;
import fr.excilys.computerdatabase.service.ComputerServices;
import fr.excilys.computerdatabase.service.UserServices;
import fr.excilys.computerdatabase.validator.DescriptionDTO;

@RequestMapping("/computer")
@Controller
public class ComputerController {
	private static final String COMPANIES = "companies";

	private static final String DASHBOARD = "view/dashboard";

	private static final String EDITCOMPUTER = "view/editComputer";

	private static final String ADDCOMPUTER = "view/addComputer";

	private static final String COMPUTERS = "computers";

	private static final long serialVersionUID = 1L;

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

	@Autowired
	private UserServices userServices;

	private NbTotal nbTotal = new NbTotal();

	private int currentPage = 0;

	private int limit = 10;

	ApplicationContext context;

	private List<ComputerDTO> pagination(String plus, String page, String length, Model model) {
		try {
			if (length != null) {
				this.limit = Integer.valueOf(length);
				if (this.limit <= 0 ) {
					this.limit = 10;
				}
				this.currentPage = 0;
			}
			if (page != null) {
				if (Integer.valueOf(page) > (nbTotal.nomberOfComputer / limit) + 1|| Integer.valueOf(page) < 0) {
					this.currentPage = 0;
				} else {
					this.currentPage = Integer.valueOf(page) - 1;
				}
			}
			if (plus != null) {
				int deltaInt = Integer.valueOf(plus);
				if (!(this.currentPage == 0 && deltaInt < 0)) {
					if (!(this.currentPage >= (nbTotal.nomberOfComputer / limit))) {
						this.currentPage += deltaInt;
					}
				}
			}
			model.addAttribute("pages", listOfPages());
			List<ComputerDTO> list = computerServices.getAllComputers(this.currentPage, limit, nbTotal);
			model.addAttribute("nbTotal", nbTotal.nomberOfComputer);
			return list;

		} catch (NumberFormatException e) {
			this.currentPage = 0;
			this.limit=10;
			model.addAttribute("pages", listOfPages());
			List<ComputerDTO> list = computerServices.getAllComputers(this.currentPage, this.limit, nbTotal);
			model.addAttribute("nbTotal", nbTotal.nomberOfComputer);
			return list;
		}
	}

	private List<Integer> listOfPages() {

		if (currentPage < 3) {
			return IntStream.range(1, 6).boxed().collect(Collectors.toList());
		}
		if (nbTotal.nomberOfComputer / limit - 2 <= currentPage) {
			return IntStream.range(nbTotal.nomberOfComputer / limit - 3, nbTotal.nomberOfComputer / limit + 2).boxed()
					.collect(Collectors.toList());

		}
		return IntStream.range(currentPage - 1, currentPage + 4).boxed().collect(Collectors.toList());
	}

	@RequestMapping(method = RequestMethod.GET)
	public String getComputerByPaging(@RequestParam(required = false) String plus,
			@RequestParam(required = false) String page, @RequestParam(required = false) String length, Model model) {
		User userDetails = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		List<? extends GrantedAuthority> authorities = (List<? extends GrantedAuthority>) SecurityContextHolder
				.getContext().getAuthentication().getAuthorities();
		fr.excilys.computerdatabase.model.User user = userServices.findUserByUsername(userDetails.getUsername());
		model.addAttribute("userId", user.getId());
		model.addAttribute("authority", authorities.get(0).toString());
		model.addAttribute("username", userDetails.getUsername());
		model.addAttribute(COMPUTERS, pagination(plus, page, length, model));
		model.addAttribute(COMPANIES, companyServices.getAllCompanies());
		return DASHBOARD;
	}

	@RequestMapping(params = ACTION_TYPE + "=" + GET_BY_NAME, method = RequestMethod.GET)
	public String getComputerByName(@RequestParam String search, Model model) {
		if (search.length() > 20) {
			return "redirect:computer";
		}
		User userDetails = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		List<? extends GrantedAuthority> authorities = (List<? extends GrantedAuthority>) SecurityContextHolder
				.getContext().getAuthentication().getAuthorities();
		fr.excilys.computerdatabase.model.User user = userServices.findUserByUsername(userDetails.getUsername());

		model.addAttribute("userId", user.getId());
		model.addAttribute("authority", authorities.get(0).toString());
		model.addAttribute("username", userDetails.getUsername());
		model.addAttribute(COMPUTERS, computerServices.getComputerByName(search, nbTotal));
		model.addAttribute("nbTotal", nbTotal.nomberOfComputer);
		model.addAttribute(COMPANIES, companyServices.getAllCompanies());
		return DASHBOARD;
	}

	@RequestMapping(params = ACTION_TYPE + "=" + CREATE, method = RequestMethod.GET)
	public String createPage(Model model) {
		User userDetails = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		List<? extends GrantedAuthority> authorities = (List<? extends GrantedAuthority>) SecurityContextHolder
				.getContext().getAuthentication().getAuthorities();
		DescriptionDTO desc = userServices.getDescription(userDetails.getUsername());
		model.addAttribute("desc", desc);
		model.addAttribute("username", userDetails.getUsername());
		model.addAttribute("authority", authorities.get(0).toString());
		model.addAttribute("computerDTO", new ComputerDTO());
		model.addAttribute(COMPANIES, companyServices.getAllCompanies());
		return ADDCOMPUTER;
	}

	@RequestMapping(params = ACTION_TYPE + "=" + UPDATE, method = RequestMethod.GET)
	public String updatePage(@RequestParam String computerId, Model model) {
		User userDetails = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		List<? extends GrantedAuthority> authorities = (List<? extends GrantedAuthority>) SecurityContextHolder
				.getContext().getAuthentication().getAuthorities();
		DescriptionDTO desc = userServices.getDescription(userDetails.getUsername());
		model.addAttribute("desc", desc);
		model.addAttribute("username", userDetails.getUsername());
		model.addAttribute("authority", authorities.get(0).toString());
		try {
			int cId = Integer.valueOf(computerId);
			ComputerDTO cDTO = computerServices.getComputerById(cId);
			if (cDTO != null) {
				model.addAttribute("computerDTO", cDTO);
			} else {
				return "redirect:computer";
			}
		} catch (NumberFormatException e ) {
			return "redirect:computer";
		}
		model.addAttribute(COMPANIES, companyServices.getAllCompanies());
		return EDITCOMPUTER;
	}

	@RequestMapping(params = ACTION_TYPE + "=" + POST, method = RequestMethod.POST)
	public String createComputer(@Valid ComputerDTO computerDTO, BindingResult result, Model model) {
		User userDetails = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		model.addAttribute("username", userDetails.getUsername());
		List<Company> companies = companyServices.getAllCompanies();

		if (result.hasErrors()) {
			return ADDCOMPUTER;
		}

		User userDet = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		fr.excilys.computerdatabase.model.User user = userServices.findUserByUsername(userDet.getUsername());

		if (!computerServices.addComputer(computerDTO, companies, user)) {
			model.addAttribute("error", "An error has occured please, try again !");
		}
		return "redirect:computer";
	}

	@RequestMapping(params = ACTION_TYPE + "=" + UPDATE, method = RequestMethod.POST)
	public String updateComputer(@Valid ComputerDTO computerDTO, BindingResult result, Model model) {
		User userDetails = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		model.addAttribute("username", userDetails.getUsername());
		List<Company> companies = companyServices.getAllCompanies();
		if (result.hasErrors()) {
			return EDITCOMPUTER;
		}

		User userDet = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		fr.excilys.computerdatabase.model.User user = userServices.findUserByUsername(userDet.getUsername());

		if (!computerServices.updateComputer(computerDTO, companies, user)) {
			model.addAttribute("error", "An error has occured please, try again !");
		}
		return "redirect:computer";
	}

	@RequestMapping(params = ACTION_TYPE + "=" + DELETE, method = RequestMethod.POST)
	public String deleteComputer(@RequestParam String selection, @RequestParam(required = false) String profil,
			Model model) {
		computerServices.removeComputer(selection);
		if (profil != null && profil.equals("user")) {
			return "redirect:computer/description";
		}
		return "redirect:computer";
	}

	@RequestMapping(params = ACTION_TYPE + "=" + "deleteFormCompany", method = RequestMethod.POST)
	public String deleteCompany(@RequestParam int companyIdDeleted, Model model) {
		companyServices.deleteCompany(companyIdDeleted);
		return "redirect:computer";
	}

}
