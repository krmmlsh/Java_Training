package fr.excilys.computerdatabase.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import fr.excilys.computerdatabase.service.UserServices;

public class UniqueUsernameValidator implements ConstraintValidator<UniqueUsername, String>{
	
	@Autowired
	private UserServices userServices;

	@Override
	public boolean isValid(String username, ConstraintValidatorContext arg1) {
 		return userServices.userNotExist(username);
	}
	
	@Override
	public void initialize(UniqueUsername constraintAnnotation) {
		
	}

}
