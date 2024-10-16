package aed.indexedlist;
import es.upm.aedlib.indexedlist.*;

public class Utils {
  public static <E> IndexedList<E> deleteRepeated(IndexedList<E> l) {
      // Hay que modificar este metodo
	  IndexedList<E> resList = new ArrayIndexedList<E>();
	  boolean parar=false;
	  
	  resList.add(0, l.get(0));
	  
	  for(int i=1; i<l.size(); i++) {
		  parar=false;
		  for(int j=0; j<resList.size() && !parar; j++) {
			  if((l.get(i).equals(resList.get(j)))) {
				  parar=true;
			  }
		  }
		  if(!parar) {
			  resList.add(resList.size(), l.get(i));
		  }
	  }
    return resList;
  }
}
