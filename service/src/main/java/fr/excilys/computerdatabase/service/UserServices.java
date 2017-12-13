package fr.excilys.computerdatabase.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import fr.excilys.computerdatabase.model.Description;
import fr.excilys.computerdatabase.model.Role;
import fr.excilys.computerdatabase.model.User;
import fr.excilys.computerdatabase.persistence.UserDao;
import fr.excilys.computerdatabase.validator.DescriptionDTO;
import fr.excilys.computerdatabase.validator.UserDTO;

@Service
public class UserServices implements UserDetailsService {
	
	@Autowired
	UserDao userDao;
	
	@Autowired
	CompanyServices companyServices;

	public User findUserByUsername(String username) {
		return userDao.findUserByUsername(username);
	}
	
	public UserDetails loadUserByUsername (String username) throws 	UsernameNotFoundException {
		User user = userDao.findUserByUsername(username);
		
		if (user == null) {
			throw new UsernameNotFoundException(" Username not found ! ");
		}
		
		List<Role> roles = userDao.findRolesForUser(username);
		
		List<GrantedAuthority> grantList = new ArrayList<>();
		if (roles != null) {
			for (Role role : roles) {
				GrantedAuthority authority = new SimpleGrantedAuthority(role.getRole());
				grantList.add(authority);
			}
		}
		
		UserDetails userDetails = new org.springframework.security.core.userdetails.User(user.getUsername(),user.getPassword(), grantList);
		
		return userDetails;
	}

	public boolean createUser(UserDTO user) {
		User userToInsert = new User(user.getUsername(), user.getPassword());
		userDao.createUser(userToInsert);
		return true;
	}

	public boolean userNotExist(String username) {
		return userDao.usernameNotExist(username);
		
	}

	public boolean modifyDescription(DescriptionDTO descriptionDTO) {
		Description description  = new Description.Builder()
				.id(descriptionDTO.getId())
				.email(descriptionDTO.getEmail())
				.firstname(descriptionDTO.getFirstname())
				.lastname(descriptionDTO.getLastname())
				.information(descriptionDTO.getInformation())
				.user(userDao.findUserById(descriptionDTO.getUser_id()))
				.company(descriptionDTO.getCompany()!= null && !descriptionDTO.getCompany().isEmpty() ? companyServices.getOrCreateCompany(descriptionDTO.getCompany()) : null)
				.build();
		userDao.modifyDescription(description);
		return true;
	}

	public DescriptionDTO getDescription(String username) {
		Description description =  userDao.findDescription(username);
		DescriptionDTO dDto  = new DescriptionDTO.Builder()
				.id(description.getId())
				.email(description.getEmail())
				.firstname(description.getFirstname())
				.lastname(description.getLastname())
				.information(description.getInformation())
				.user_id(description.getUser().getId())
				.company_id(description.getCompany() != null ? description.getCompany().getId() : -1)
				.company(description.getCompany()!= null ? description.getCompany().getName() : "")
				.build();
		return dDto;
	}

}
