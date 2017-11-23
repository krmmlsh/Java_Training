package fr.excilys.computerdatabase.validator;

public class Validator {
	
	public static boolean isNameValid (String name) {
		if (name == null || name.length() < 4) {
			return false;
		}
		return true;
	}
}
