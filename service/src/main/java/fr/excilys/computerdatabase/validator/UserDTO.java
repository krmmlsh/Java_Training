package fr.excilys.computerdatabase.validator;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;


public class UserDTO {

	private int id;

	@NotNull
	@Size(min = 4, max = 14, message = "Please enter a valid name, size between 4 and 14 characters")
	@Pattern(regexp = "^[a-zA-Z0-9_-]*$", message = "Please enter a valid username ( letters, numbers and - _ )")
	@UniqueUsername(message = "Username already in use, please choose another one")
	private String username;
	
	@Size(min=4, max=20, message = "Please enter a password of size between 4 and 20")
	private String password;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}
