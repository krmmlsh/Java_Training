package fr.excilys.computerdatabase.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import fr.excilys.computerdatabase.service.UserServices;
import fr.excilys.computerdatabase.validator.DescriptionDTO;
import fr.excilys.computerdatabase.validator.UserDTO;

@Controller
public class UserController {

	private static final String VIEW_LOGIN = "view/login";
	private static final String VIEW_SIGN_UP = "view/signUp";
	private static final String VIEW_DESCRIPTION = "view/profilPage";
	private static final String VIEW_DASHBOARD = "view/dashboard";


	
	@Autowired
	UserServices userServices;

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
		model.addAttribute("descriptionDTO", userServices.getDescription(user.getUsername()));
		return VIEW_DESCRIPTION;
	}

	@RequestMapping(value = "/computer/description", method = RequestMethod.POST)
	public String profil(@Valid DescriptionDTO descriptionDTO, BindingResult result, Model model) {

		if (result.hasErrors()) {
			return VIEW_DESCRIPTION;
		}
		if(!userServices.modifyDescription(descriptionDTO)) {
			model.addAttribute("error", "An error has occured please, try again !");
		}
		return "redirect:../computer";
	}
}
