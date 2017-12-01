package fr.excilys.computerdatabase.main;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;

import fr.excilys.computerdatabase.model.Company;
import fr.excilys.computerdatabase.model.ComputerDTO;
import fr.excilys.computerdatabase.model.ComputerPaging;

/**
 * Main class to launch the application and manage the user.
 * 
 * 
 * @author Rimk
 *
 */

public class Main {

	private static final String COMPANY_BY_NAME = "http://localhost:8080/rest/webservice/company?name=";
	private static final String GET_ALL_COMPANIES = "http://localhost:8080/rest/webservice/companies";

	private static final String ADD_COMPUTER = "http://localhost:8080/rest/webservice/addComputer";
	private static final String UPDATE_COMPUTER = "http://localhost:8080/rest/webservice/editComputer";
	private static final String DELETE_COMPUTER = "http://localhost:8080/rest/webservice/deleteComputer";

	private static final String GET_ALL_COMPUTERS = "http://localhost:8080/rest/webservice/dashboard";
	private static final String GET_COMPUTER = "http://localhost:8080/rest/webservice/computer?id=";
	private static final String SEARCH = "http://localhost:8080/rest/webservice/dashboard?search=";

	public static void main(String[] args) {

		Client client = ClientBuilder.newClient();
		// User information
		System.out.println("a to add a new computer" + "\n" + "c to get an existing computer by id" + "\n"
				+ "p to get an existing computer by name " + "\n" + "r to remove a computer" + "\n"
				+ "s to get all computers from the database" + "\n" + "C to list all the companies" + "\n"
				+ "d to delete a company and all its computer\n");
		try (Scanner s = new Scanner(System.in)) {
			while (s.hasNext()) {

				String string = s.nextLine();
				// We only take single letter command from the user
				if (string.length() == 1) {
					switch (string.charAt(0)) {

					// add a computer
					case 'a': {

						System.out.println("Name of the computer");
						String name = s.nextLine();
						// Never add a computer with an empty name
						if (name.isEmpty()) {
							System.out.println("Name cannot be empty.");
							break;
						}
						System.out.println("What is the brand ? (Leave the field blank to not add a brand)");
						String company = s.nextLine();
						if (company.isEmpty()) {
							// Add a computer only with its name
							ComputerDTO c = new ComputerDTO(name, -1);
							client.target(ADD_COMPUTER).request(MediaType.APPLICATION_JSON)
									.post(Entity.entity(c, MediaType.APPLICATION_JSON));
							break;
						}
						// A brand must exist to create the computer with it
						Company comp = client.target(COMPANY_BY_NAME + company).request(MediaType.APPLICATION_JSON)
								.get(Company.class);
						if (!company.isEmpty() && comp == null) {
							System.out.println("Brand name not incorrect");
							break;
						}
						// Date creations
						System.out.println("Introduction date : (dd/mm/yyyy)");
						String introduction = s.nextLine();
						System.out.println("Discontinuation date : (dd/mm/yyyy)");
						String discontinuation = s.nextLine();
						ComputerDTO c = new ComputerDTO(name, introduction, discontinuation, comp.getId());
						client.target(ADD_COMPUTER).request(MediaType.APPLICATION_JSON)
								.post(Entity.entity(c, MediaType.APPLICATION_JSON));
						break;
					}
					// get a computer by id
					case 'c': {
						System.out.println("Enter the id of the computer :");
						int id;

						// Must be a number or it throws an error
						try {
							id = s.nextInt();
						} catch (InputMismatchException e) {
							System.out.println("You must enter a valid id !");
							break;
						}
						// nextInt() doesn't consume \n
						s.nextLine();
						// Get the computer if it exists or return null
						ComputerDTO computer = client.target(GET_COMPUTER + id).request(MediaType.APPLICATION_JSON)
								.get(ComputerDTO.class);
						if (computer != null) {
							System.out.println(computer);
						}
						break;

					}
					// get a computer by name
					case 'p': {
						System.out.println("Enter the name of the computer :");
						String name = s.nextLine();
						List<ComputerDTO> computerDTOList = (List<ComputerDTO>) client.target(SEARCH + name)
								.request(MediaType.APPLICATION_JSON).get(List.class);
						for (ComputerDTO computer : computerDTOList) {
							System.out.println(computer);
						}
						break;
					}
					// update an existing computer
					case 'u': {
						System.out.println("You have to provide the exact id of a computer to modify it : ");
						int id;
						try {
							// Must be a number
							id = s.nextInt();
						} catch (InputMismatchException e) {
							System.out.println("You must enter a valid id !");
							break;
						} finally {
							s.nextLine();
						}
						ComputerDTO c = client.target(GET_COMPUTER + id).request(MediaType.APPLICATION_JSON)
								.get(ComputerDTO.class);
						if (c == null) {
							System.out.println("Can't update a non existing computer");
							break;
						}
						// for the fields left empty, we just take the already existing value from the
						// computer
						System.out.println("Leave the field empty if you do not want to modiy it :");
						System.out.println("name : ");
						String name = s.nextLine();
						c.setName((name.isEmpty()) ? c.getName() : name);

						System.out.println("Brand : ");
						String brand = s.nextLine();

						Company comp = client.target(COMPANY_BY_NAME + brand).request(MediaType.APPLICATION_JSON)
								.get(Company.class);
						if (!brand.isEmpty() && comp == null) {
							System.out.println("Company doesn't exist");
							break;
						}
						c.setCompany(comp.getName());

						// date treatment System.out.println("Introduction date : (dd/mm/yyyy");
						String introduction = s.nextLine();
						if (!introduction.isEmpty()) {
							c.setIntroduced(introduction);
						}
						System.out.println("Discontinuation date : (dd/mm/yyyy");
						String discontinuation = s.nextLine();
						if (!discontinuation.isEmpty()) {
							c.setIntroduced(discontinuation);
						}
						client.target(UPDATE_COMPUTER).request(MediaType.APPLICATION_JSON)
								.post(Entity.entity(c, MediaType.APPLICATION_JSON));
						break;
					}
					case 'r': {

						System.out.println("Id of the computer to remove : ");
						try {
							int id = s.nextInt();
							client.target(DELETE_COMPUTER).request(MediaType.APPLICATION_JSON)
									.post(Entity.entity(id, MediaType.APPLICATION_JSON));
						} catch (InputMismatchException e) {
							System.out.println("You must enter a valid id !");
							break;
						} finally {
							s.nextLine();
						}
						break;
					}
					// get all the computers from the database
					case 's': {
						System.out.println("Get all the computers from the database");
						ComputerPaging computers = client.target(GET_ALL_COMPUTERS).request(MediaType.APPLICATION_JSON)
								.get(ComputerPaging.class);
						Pages<ComputerDTO> p = new Pages<>(10, computers.getComputerList());
						for (ComputerDTO c : p.getNextPagesElements()) {
							System.out.println(c);
						}
						break;
					}
					 // get all the companies 
					case 'C': {
					  System.out.println("Get all the companies from the database"); 
					  List<Company> list = client.target(GET_ALL_COMPANIES).request(MediaType.APPLICATION_JSON)
								.get(List.class);
					  for (Company c : list){ 
						  System.out.println("Id : " + c.getId() + "\nCompany : " + c.getName() + "\n");
					  } 
					  break; 
					  }
//					case 'd' :{
//						  System.out.println("Remove a company named :"); String company =
//						  s.nextLine(); Company companyObject =
//						  companyService.getCompanyByName(company); if (companyObject == null) {
//						  System.out.println("Company does not exist"); break; }
//						  companyService.deleteCompany(companyObject.getId()); break; } default:
//						  System.out.println("Command invalid\n"); } } System.out.println("" +
//						  "a to add a new computer" + "\n" + "c to get an existing computer by id" +
//						  "\n" + "p to get an existing computer by name " + "r to remove a computer" +
//						  "\n" + "s to get all computers from the database" + "\n" +
//						  "C to list all the companies" + "\n");
//					}
				}
			}
		}
	}
}
