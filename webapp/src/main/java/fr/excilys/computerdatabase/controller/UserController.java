package fr.excilys.computerdatabase.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import fr.excilys.computerdatabase.model.ComputerDTO;
import fr.excilys.computerdatabase.model.Description;
import fr.excilys.computerdatabase.service.ComputerServices;
import fr.excilys.computerdatabase.service.UserServices;
import fr.excilys.computerdatabase.validator.DescriptionDTO;
import fr.excilys.computerdatabase.validator.UserDTO;

@Controller
public class UserController {

	private static final String VIEW_LOGIN = "view/login";
	private static final String VIEW_SIGN_UP = "view/signUp";
	private static final String VIEW_DESCRIPTION = "view/profilPage";
	private static final String VIEW_DASHBOARD = "view/dashboard";
	private static final String VIEW_DESCRIPTION_USER = "view/profilPageUser";


	@Autowired
	UserServices userServices;

	@Autowired
	ComputerServices computerServices;
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login(@RequestParam(value = "error", required = false) String error, Model model) {

		if (error != null) {
			model.addAttribute("error", "Username or Password false");
		}

		return VIEW_LOGIN;
	}

	@RequestMapping(value = "/signup", method = RequestMethod.GET)
	public String signUpPage(Model model) {
		model.addAttribute("userDTO", new UserDTO());
		return VIEW_SIGN_UP;
	}

	@RequestMapping(value = "/signup", method = RequestMethod.POST)
	public String signUp(@Valid UserDTO user, BindingResult result, Model model) {
		if (result.hasErrors()) {
			return VIEW_SIGN_UP;
		}
		if(!userServices.createUser(user)) {
			model.addAttribute("error", "An error has occured please, try again !");
		}
		return VIEW_LOGIN;
	}
	
	@RequestMapping(value = "/computer/description", method = RequestMethod.GET)
	public String profilPage(Model model) {
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		model.addAttribute("username", user.getUsername());
		DescriptionDTO description = userServices.getDescription(user.getUsername());
		model.addAttribute("descriptionDTO", description);
		List<ComputerDTO> computersDTO = computerServices.getComputerByUserId(description.getUser_id());
		model.addAttribute("computers", computersDTO);
		List<Description> descList = userServices.findAllUsers();
		model.addAttribute("desclist", descList);
		List<? extends GrantedAuthority> authorities = (List<? extends GrantedAuthority>) SecurityContextHolder.getContext().getAuthentication().getAuthorities();
		model.addAttribute("authority", authorities.get(0).toString());

		return VIEW_DESCRIPTION;
	}
	
	@RequestMapping(value = "/computer/description/user", method = RequestMethod.GET)
	public String profilPageUser(Model model, @RequestParam(required=false) String userUsername) {
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		model.addAttribute("username", user.getUsername());
		DescriptionDTO description = userServices.getDescription(userUsername);
		model.addAttribute("descriptionDTO", description);
		List<ComputerDTO> computersDTO = computerServices.getComputerByUserId(description.getUser_id());
		model.addAttribute("computers", computersDTO);
		model.addAttribute("userUsername", userUsername);
		List<Description> descList = userServices.findAllUsers();
		model.addAttribute("desclist", descList);
		List<? extends GrantedAuthority> authorities = (List<? extends GrantedAuthority>) SecurityContextHolder.getContext().getAuthentication().getAuthorities();
		model.addAttribute("authority", authorities.get(0).toString());
		return VIEW_DESCRIPTION_USER;
	}


	@RequestMapping(value = "/computer/description", method = RequestMethod.POST)
	public String profil(@Valid DescriptionDTO descriptionDTO, BindingResult result, Model model) {
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		model.addAttribute("username", user.getUsername());
		if (result.hasErrors()) {
			return VIEW_DESCRIPTION;
		}
		if(!userServices.modifyDescription(descriptionDTO)) {
			model.addAttribute("error", "An error has occured please, try again !");
		}
		return "redirect:../computer";
	}
}
