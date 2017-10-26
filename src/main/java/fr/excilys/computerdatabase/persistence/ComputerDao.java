package fr.excilys.computerdatabase.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import fr.excilys.computerdatabase.model.Computer;

public class ComputerDao {

	private static ComputerDao computerDao;
	
	private CompanyDao companyDao = CompanyDao.getInstance();
	
	private ComputerDao() {
		
	}
	
	public synchronized static ComputerDao getInstance() {
		if (computerDao == null)
			computerDao = new ComputerDao();
		return computerDao;
	}
	
	private Computer createComputerFromDatabase(ResultSet rs) throws SQLException {
		Computer c = new Computer();
		c.setId(rs.getInt("id"));
		c.setName(rs.getString("name"));
		c.setCompany(companyDao.getCompany(rs.getInt("company_id")));
		c.setIntroducedDate(rs.getTimestamp("introduced"));
		c.setDiscontinuedDate(rs.getTimestamp("discontinued"));
		return c;

	}

	public Computer getComputer(int id) {
		try (Connection conn = DatabaseConnexion.getConnection();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery("SELECT * FROM computer where id = " + id)) {

			if (rs.next()) {

				return createComputerFromDatabase(rs);
			} else {
				System.out.println("No computer found");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public Computer getComputer(String name) {
		try (Connection conn = DatabaseConnexion.getConnection();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery("SELECT * FROM computer where name = " + name)) {

			if (rs.next()) {
				return createComputerFromDatabase(rs);
			} else {
				System.out.println("No computer found");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public List<Computer> getComputers() {
		List<Computer> list = new ArrayList<>();
		try (Connection conn = DatabaseConnexion.getConnection();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery("SELECT * FROM computer");) {
			while (rs.next()) {
				list.add(createComputerFromDatabase(rs));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	public boolean removeComputer(int id) {
		try (Connection conn = DatabaseConnexion.getConnection(); Statement stmt = conn.createStatement()) {

			int i = stmt.executeUpdate("DELETE FROM computer WHERE id = " + id);
			if (i == 1) {
				System.out.println("Computer deleted");

				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println("Computer cannot be deleted");

		return false;
	}

	public boolean insertComputer(Computer c) {
		try (Connection conn = DatabaseConnexion.getConnection();
				PreparedStatement ps = conn.prepareStatement("INSERT INTO computer values (NULL, ?, ?, ?, ?)")) {

			ps.setString(1, c.getName());
			ps.setTimestamp(2, c.getIntroducedDate());
			ps.setTimestamp(3, c.getDiscontinuedDate());
			ps.setObject(4, (c.getCompId() >= 0) ? companyDao.getCompany(c.getCompany()) : null);
			int i = ps.executeUpdate();

			if (i == 1) {
				System.out.println("Computer added");
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println("Computer cannot be added");

		return false;
	}

	public boolean updateComputer(Computer c) {
		try (Connection conn = DatabaseConnexion.getConnection();
				PreparedStatement ps = conn.prepareStatement(
						"UPDATE computer set name=?, introduced=?, discontinued=?, company_id=? where id=" + c.getId())) {

			ps.setString(1, c.getName());
			ps.setTimestamp(2, c.getIntroducedDate());
			ps.setTimestamp(3, c.getDiscontinuedDate());
			ps.setObject(4, c.getCompId());

			int i = ps.executeUpdate();

			if (i == 1) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
}
