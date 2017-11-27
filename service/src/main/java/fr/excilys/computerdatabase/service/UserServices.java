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

import fr.excilys.computerdatabase.model.User;
import fr.excilys.computerdatabase.persistence.UserDao;

@Service
public class UserServices implements UserDetailsService {
	
	@Autowired
	UserDao userDao;
	
	public UserDetails loadUserByUsername (String username) throws 	UsernameNotFoundException {
		User user = userDao.findUserByUsername(username);
		
		if (user == null) {
			throw new UsernameNotFoundException(" Username not found ! ");
		}
		
		List<String> roles = userDao.findRolesForUser(username);
		
		List<GrantedAuthority> grantList = new ArrayList<>();
		if (roles != null) {
			for (String role : roles) {
				GrantedAuthority authority = new SimpleGrantedAuthority(role);
				grantList.add(authority);
			}
		}
		
		UserDetails userDetails = new org.springframework.security.core.userdetails.User(user.getUsername(),user.getPassword(), grantList);
		
		return userDetails;
	}

}
