package fr.excilys.computerdatabase.rest;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import fr.excilys.computerdatabase.main.NbTotal;
import fr.excilys.computerdatabase.model.Company;
import fr.excilys.computerdatabase.model.ComputerDTO;
import fr.excilys.computerdatabase.model.ComputerPaging;
import fr.excilys.computerdatabase.service.CompanyServices;
import fr.excilys.computerdatabase.service.ComputerServices;

@RestController
@RequestMapping("/webservice")
public class ComputerWebService {

	@Autowired
	private ComputerServices computerServices;

	@Autowired
	private CompanyServices companyServices;

	private NbTotal nbTotal = new NbTotal();

	private int currentPage = 0;

	private int limit = 10;

	private ComputerPaging pagination(String plus, String page, String length) {

		if (length != null) {
			this.limit = Integer.valueOf(length);
			this.currentPage = 0;
		}
		if (page != null) {
			this.currentPage = Integer.valueOf(page) - 1;
		}
		if (plus != null) {
			int deltaInt = Integer.valueOf(plus);
			if (!(this.currentPage == 0 && deltaInt < 0)) {
				this.currentPage += deltaInt;
			}
		}
		List<ComputerDTO> list = computerServices.getAllComputers(this.currentPage, limit, nbTotal);
		return new ComputerPaging(list, nbTotal.nomberOfComputer, listOfPages());
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

	@RequestMapping(value = "/dashboard", method = RequestMethod.GET)
	public @ResponseBody ComputerPaging getComputerByPaging(@RequestParam(required = false, defaultValue="1") String plus,
			@RequestParam(required = false, defaultValue="0") String page, @RequestParam(required = false, defaultValue="10") String length) {
		return pagination(plus, page, length);
	}

	@RequestMapping(value = "/search",  method = RequestMethod.GET)
	public @ResponseBody List<ComputerDTO> getComputerByName(@RequestParam String search) {
		return computerServices.getComputerByName(search);
	}

	@RequestMapping(value = "/addComputer", method = RequestMethod.GET)
	public @ResponseBody ComputerDTO createPage(Model model) {
		return new ComputerDTO();
	}

	@RequestMapping(value = "/editComputer", method = RequestMethod.GET)
	public @ResponseBody ComputerDTO updatePage(@RequestParam int computerId) {
		return computerServices.getComputerById(computerId);
	}

	@RequestMapping(value = "/addComputer", method = RequestMethod.POST)
	public @ResponseBody ComputerDTO createComputer(@Valid ComputerDTO computerDTO, BindingResult result) {
		List<Company> companies = companyServices.getAllCompanies();

		if (!computerServices.addComputer(computerDTO, companies)) {
		}
		return computerDTO;
	}

	@RequestMapping(value = "/editComputer", method = RequestMethod.POST)
	public @ResponseBody ComputerDTO updateComputer(@Valid ComputerDTO computerDTO, BindingResult result) {
		List<Company> companies = companyServices.getAllCompanies();

		if (!computerServices.updateComputer(computerDTO, companies)) {
		}
		return computerDTO;
	}
	
	@RequestMapping(value = "/deleteComputer", method = RequestMethod.POST)
	public @ResponseBody boolean deleteComputer(@RequestParam String selection) {
		computerServices.removeComputer(selection);
		return 	computerServices.removeComputer(selection);
	}
	
	@RequestMapping(value = "/deleteCompany", method = RequestMethod.POST)
	public @ResponseBody void deleteCompany(@RequestParam int companyIdDeleted) {
		companyServices.deleteCompany(companyIdDeleted);
	}
}
