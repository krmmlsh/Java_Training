package fr.excilys.computerdatabase.main;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


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
  public static Timestamp convertStringToTimestamp(String str_date) {
    try {
      DateFormat formatter;
      formatter = new SimpleDateFormat("dd/MM/yyyy");
       // you can change format of date
      Date date = formatter.parse(str_date);
      Timestamp timeStampDate = new Timestamp(date.getTime());

      return timeStampDate;
    } catch (ParseException e) {
    	System.err.println("Value was not entered in the right format, it will not be stored !");
      return null;
    }
  }
}

