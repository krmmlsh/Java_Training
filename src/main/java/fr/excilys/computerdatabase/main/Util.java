package fr.excilys.computerdatabase.main;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;


/**
 * Utilities class
 * @author krmmlsh
 *
 */
public class Util{
	
	/**
	 * Format the String into a Timestamp
	 * @param str_date String corresponding to date
	 * @return Timestamp representation of str_date
	 */
  public static LocalDate convertStringToLocalDate(String str_date) {
    try {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
        LocalDate localDate = LocalDate.parse(str_date, formatter);

      return localDate;
    } catch (DateTimeParseException e) {
    	System.err.println("Value was not entered in the right format, it will not be stored !");
      return null;
    }
  }
}

