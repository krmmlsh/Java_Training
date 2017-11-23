package fr.excilys.computerdatabase.main;

import java.util.ArrayList;
import java.util.List;

/**
 * Pagination class
 * 
 * @author krmmlsh
 *
 * @param <T> 
 */


public class Pages<T> {

	 private int cpt;
	 
	 private int length;
	 
	 private int pageLength;
	 
	 private List<T> list;
	
	 public Pages(int pageLength, List<T> list) {
		 this.pageLength = pageLength;
		 this.list = list;
		 this.length = list.size();
	 }
	 
	 /**
	  * Return the next pageLength elements 
	  * @return List of pageLength (or until the end of the list) T elements
	  */
	 public List<T> getNextPagesElements(){
		 List<T> elementList = new ArrayList<>();
		 for(int i = 0; i < pageLength && cpt < length; i++) {
			 elementList.add(list.get(cpt++));
		 }
		 return elementList;
	 }
}


