package aed.individual4;

import java.util.Iterator;
import java.util.NoSuchElementException;

import es.upm.aedlib.Pair;
import es.upm.aedlib.Position;
import es.upm.aedlib.positionlist.PositionList;

public class MultiSetListIterator<E> implements Iterator<E> {
  PositionList<Pair<E,Integer>> list;

  Position<Pair<E,Integer>> cursor;
  int counter;
  Position<Pair<E,Integer>> prevCursor;

  public MultiSetListIterator(PositionList<Pair<E,Integer>> list) {
    this.list = list;
    
    cursor=list.first();
    counter=1;
    prevCursor=null;
   
  }

  public boolean hasNext() {
	  return cursor!=null;
  }

  public E next() {
	  if(cursor==null) {
		  throw new NoSuchElementException();
		}
		E res;
		
		if (counter < cursor.element().getRight()) {
			counter++;
			 res = cursor.element().getLeft();
			 prevCursor=cursor;
		} else {
			prevCursor=cursor;
			counter = 1;
			res = cursor.element().getLeft();
			cursor = list.next(cursor);
		}

		return res;
	}
  
  public void remove() {
	  if(prevCursor==null) 
		  throw new IllegalStateException();
  
		if (counter == 1) {
			prevCursor.element().setRight(prevCursor.element().getRight()-1);	
		}
		else {
			cursor.element().setRight(cursor.element().getRight()-1);
			counter--;
		}
		if(prevCursor.element().getRight()==0)
			list.remove(prevCursor);
		prevCursor=null;
		
    // opcionalmente se puede modificar para obtener mas puntos
    // NO ES OBLIGATORIO
  }
}
