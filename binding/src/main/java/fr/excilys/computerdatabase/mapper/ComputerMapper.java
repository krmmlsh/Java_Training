package fr.excilys.computerdatabase.mapper;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import fr.excilys.computerdatabase.main.Util;
import fr.excilys.computerdatabase.model.Company;
import fr.excilys.computerdatabase.model.Computer;
import fr.excilys.computerdatabase.model.ComputerDTO;
import fr.excilys.computerdatabase.model.User;

@Component
public class ComputerMapper {

	public ComputerDTO computerToComputerDTO(Computer computer) {
		return new ComputerDTO.Builder().id(computer.getId()).name(computer.getName())
				.introducedDate((computer.getIntroducedDate() != null) ? computer.getIntroducedDate().toString() : null)
				.discontinuedDate(
						(computer.getDiscontinuedDate() != null) ? computer.getDiscontinuedDate().toString() : null)
				.company((computer.getCompany() != null) ? computer.getCompany().getName() : null)
				.userId(computer.getUser() != null ? computer.getUser().getId() : -1).build();
	}

	public Computer computerDTOtoComputer(ComputerDTO computerDTO, List<Company> companies, User user) {
		Optional<Company> comp = companies.stream().filter(c -> c.getId() == computerDTO.getCompanyId()).findFirst();
		return new Computer.Builder().id(computerDTO.getId()).name(computerDTO.getName())
				.introducedDate((computerDTO.getIntroduced() != null)
						? Util.convertStringToLocalDate(computerDTO.getIntroduced().toString(), "dd/MM/yyyy")
						: null)
				.discontinuedDate((computerDTO.getDiscontinued() != null)
						? Util.convertStringToLocalDate(computerDTO.getDiscontinued().toString(), "dd/MM/yyyy")
						: null)
				.company((comp.isPresent()) ? comp.get() : null)
				.user(user).build();
	}
}
