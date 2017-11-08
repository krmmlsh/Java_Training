package fr.excilys.computerdatabase.main;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Map.Entry;
import java.util.Scanner;

import fr.excilys.computerdatabase.model.Company;
import fr.excilys.computerdatabase.model.Computer;
import fr.excilys.computerdatabase.service.CompanyServices;
import fr.excilys.computerdatabase.service.ComputerServices;
import fr.excilys.computerdatabase.servlet.ComputerDTO;

/**
 * Main class to launch the application and manage the user.
 * 
 * @author Rimk
 *
 */
public class Main {
	public static void main(String[] args) throws SQLException {
		ComputerServices computerService = ComputerServices.getComputerServices();
		CompanyServices companyService = CompanyServices.getCompanyServices();

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
							computerService.addComputer(new Computer(name, -1));
							break;
						}
						// A brand must exist to create the computer with it
						Company comp = companyService.getCompanyByName(company);
						if (!company.isEmpty() && comp == null) {
							System.out.println("Brand name not incorrect");
							break;
						}

						// Date creations
						System.out.println("Introduction date : (dd/mm/yyyy)");
						String introduction = s.nextLine();
						LocalDate intro = Util.convertStringToLocalDate(introduction, "dd/MM/yyyy");

						System.out.println("Discontinuation date : (dd/mm/yyyy)");
						String discontinuation = s.nextLine();
						LocalDate disc = Util.convertStringToLocalDate(discontinuation, "dd/MM/yyyy");
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
						ComputerDTO computer = computerService.getComputerById(id);
						if (computer != null) {
							System.out.println(computer);
						}
						break;
					}
					// get a computer by name
					case 'p': {
						System.out.println("Enter the name of the computer :");
						String name = s.nextLine();
						List<ComputerDTO> computerDTOList = computerService.getComputerByName(name);
						for(ComputerDTO computer : computerDTOList) {
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
						ComputerDTO c = computerService.getComputerById(id);
						if (c == null) {
							System.out.println("Can't update a non existing computer");
							break;
						}
						Computer computer = new Computer();
						computer.setId(c.getId());
						// for the fields left empty, we just take the already existing value from the computer
						System.out.println("Leave the field empty if you do not want to modiy it :");
						System.out.println("name : ");
						String name = s.nextLine();
						computer.setName((name.isEmpty()) ? c.getName() : name);
						
						System.out.println("Brand : ");
						String brand = s.nextLine();
						Company comp = null;
						// break the loop if a wrong brand is entered
						if (!brand.isEmpty() && (comp = companyService.getCompanyByName(brand)) == null ) {
							System.out.println("Company doesn't exist");
							break;
						}
						computer.setCompId((brand.isEmpty()) ? c.getCompId() : comp.getId());
						
						// date treatment
						System.out.println("Introduction date : (dd/mm/yyyy");
						String introduction = s.nextLine();
						if (!introduction.isEmpty()) {
							LocalDate intro = Util.convertStringToLocalDate(introduction, "dd/MM/yyyy");
							computer.setIntroducedDate(intro);

						}
						System.out.println("Discontinuation date : (dd/mm/yyyy");
						String discontinuation = s.nextLine();
						if (!discontinuation.isEmpty()) {
							LocalDate disc = Util.convertStringToLocalDate(discontinuation, "dd/MM/yyyy");
							computer.setDiscontinuedDate(disc);

						}
						computerService.updateComputer(computer);
						break;
					}
					case 'r': {
						
						System.out.println("Id of the computer to remove : ");
						try {
							int id = s.nextInt();
							computerService.removeComputer(String.valueOf(id));
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
						List<ComputerDTO> computers = computerService.getAllComputers();
						Pages<ComputerDTO> p = new Pages<>(10, computers);
						String decision = "N";
						do {
							for (ComputerDTO c : p.getNextPagesElements()) {
								System.out.println(c);
							}
							System.out.println("Continues ? : (Y:N)");
							decision = s.next();
						} while (decision.toUpperCase().equals("Y"));
						break;
					}
					// get all the companies
					case 'C': {
						System.out.println("Get all the companies from the database");
						List<Company> list = companyService.getAllCompanies();
						Pages<Company> p = new Pages<>(10, list);
						String decision = "N";
						do {
							for (Company c : p.getNextPagesElements()) {
								System.out.println("Id : " + c.getId() + "\nCompany : " + c.getName() + "\n");

							}
							System.out.println("Continues ? : (Y:N)");
							decision = s.next();
						} while (decision.toUpperCase().equals("Y"));
						break;
					}
					case 'd' :{
						System.out.println("Remove a company named :");
						String company = s.nextLine();
						Company companyObject = companyService.getCompanyByName(company);
						if (companyObject == null) {
							System.out.println("Company does not exist");
							break;
						}
						companyService.deleteCompany(companyObject.getId());
						break;
					}
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
