package fr.excilys.computerdatabase.main;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Map.Entry;
import java.util.Scanner;

import fr.excilys.computerdatabase.model.Computer;
import fr.excilys.computerdatabase.service.CompanyServices;
import fr.excilys.computerdatabase.service.ComputerServices;

/**
 * Main class to launch the application and manage the user.
 * 
 * @author Rimk
 *
 */
public class Main {
	public static void main(String[] args) {
		ComputerServices computerService = ComputerServices.getComputerServices();
		CompanyServices companyService = CompanyServices.getCompanyServices();

		// User information
		System.out.println("a to add a new computer" + "\n" + "c to get an existing computer by id" + "\n"
				+ "p to get an existing computer by name " + "\n" + "r to remove a computer" + "\n"
				+ "s to get all computers from the database" + "\n" + "C to list all the companies" + "\n");
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
							computerService.addComputer(new Computer(name, -1));
							break;
						}
						// A brand must exist to create the computer with it
						int comp_id = companyService.getCompany(company);
						if (!company.isEmpty() && comp_id < 0) {
							System.out.println("Brand name not incorrect");
							break;
						}

						// Date creations
						System.out.println("Introduction date : (dd/mm/yyyy)");
						String introduction = s.nextLine();
						Timestamp intro = Util.convertStringToTimestamp(introduction);

						System.out.println("Discontinuation date : (dd/mm/yyyy)");
						String discontinuation = s.nextLine();
						Timestamp disc = Util.convertStringToTimestamp(discontinuation);
						computerService.addComputer(new Computer(name, company, intro, disc));

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
						} finally {
							// nextInt() doesn't consume \n
							s.nextLine();
						}
						// Get the computer if it exists or return null
						Computer computer = computerService.getComputerById(id);
						if (computer != null)
							System.out.println(computer);
						break;
					}
					// get a computer by name
					case 'p': {
						System.out.println("Enter the name of the computer :");
						String name = s.nextLine();
						computerService.getComputerByName(name);
//						if (computer != null)
//							System.out.println(computer);
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
						Computer c = computerService.getComputerById(id);
						if (c == null) {
							System.out.println("Can't update a non existing computer");
						}
						// for the fields left empty, we just take the already existing value from the computer
						System.out.println("Leave the field empty if you do not want to modiy it :");
						System.out.println("name : ");
						String name = s.nextLine();
						c.setName((name.isEmpty()) ? c.getName() : name);
						
						System.out.println("Brand : ");
						String brand = s.nextLine();
						int compId = -1;
						// break the loop if a wrong brand is entered
						if (!brand.isEmpty() && (compId = companyService.getCompany(brand)) < 0) {
							System.out.println("Company doesn't exist");
							break;
						}
						c.setCompId((brand.isEmpty()) ? c.getCompId() : compId);
						
						// date treatment
						System.out.println("Introduction date : (dd/mm/yyyy");
						String introduction = s.nextLine();
						if (!introduction.isEmpty()) {
							Timestamp intro = Util.convertStringToTimestamp(introduction);
							c.setIntroducedDate(intro);

						}
						System.out.println("Discontinuation date : (dd/mm/yyyy");
						String discontinuation = s.nextLine();
						if (!discontinuation.isEmpty()) {
							Timestamp disc = Util.convertStringToTimestamp(discontinuation);
							c.setDiscontinuedDate(disc);

						}
						computerService.updateComputer(c);
						break;
					}
					case 'r': {
						
						System.out.println("Id of the computer to remove : ");
						int id;
						try {
							id = s.nextInt();
						} catch (InputMismatchException e) {
							System.out.println("You must enter a valid id !");
							break;
						} finally {
							s.nextLine();
						}
						computerService.removeComputer(id);
						break;
					}
					// get all the computers from the database
					case 's': {
						System.out.println("Get all the computers from the database");
						List<Computer> computers = computerService.getAllComputers();
						Pages<Computer> p = new Pages<>(10, computers);
						String decision = "N";
						do {
							for (Computer c : p.getNextPagesElements()) {
								System.out.println(c);
							}
							System.out.println("Continues ? : (Y:N)");
							decision = s.next();
						} while (decision.toUpperCase().equals("Y"));
						break;
					}
					// get all the companies
					case 'C':
						System.out.println("Get all the companies from the database");
						List<Entry<Integer, String>> entryList = new ArrayList<>(
								companyService.getAllCompanies().entrySet());
						Pages<Entry<Integer, String>> p = new Pages<Entry<Integer, String>>(10, entryList);
						String decision = "N";
						do {
							for (Entry<Integer, String> c : p.getNextPagesElements()) {
								System.out.println("Id : " + c.getKey() + "\nCompany : " + c.getValue() + "\n");

							}
							System.out.println("Continues ? : (Y:N)");
							decision = s.next();
						} while (decision.toUpperCase().equals("Y"));
						break;
					default:
						System.out.println("Command invalid\n");
					}
				}
				System.out.println("" + "a to add a new computer" + "\n" + "c to get an existing computer by id" + "\n"
						+ "p to get an existing computer by name " + "r to remove a computer" + "\n"
						+ "s to get all computers from the database" + "\n" + "C to list all the companies" + "\n");

			}
		}
	}
}
