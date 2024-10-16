package aed.multisets;

import es.upm.aedlib.Pair;
import es.upm.aedlib.Position;
import es.upm.aedlib.positionlist.PositionList;
import es.upm.aedlib.positionlist.NodePositionList;

/**
 * An implementation of a multiset using a positionlist.
 */
public class MultiSetList<Element> implements MultiSet<Element> {

	/**
	 * Datastructure for storing the multiset.
	 */
	private PositionList<Pair<Element, Integer>> elements;

	private int size;

	/**
	 * Constructs an empty multiset.
	 */
	public MultiSetList() {
		this.elements = new NodePositionList<Pair<Element, Integer>>();
	}

	public void add(Element elem, int n) {
		// TODO Auto-generated method stub
		if (n < 0)
			throw new IllegalArgumentException();
		if (n != 0) {
			Position<Pair<Element, Integer>> pos = elements.first();
			Pair<Element, Integer> elemAniadir = new Pair<Element, Integer>(elem, n);
			int contador = 0;
			boolean salida = false;
			if (elements.isEmpty()) {
				elements.addFirst(elemAniadir);
				salida = true;
			}

			while (!salida && contador < elements.size()) {
				Element val = pos.element().getLeft();
				if ((val == null && elem == null) || ((elem != null) && (val != null) && elem.equals(val))) {
					pos.element().setRight(pos.element().getRight()+n);
					salida = true;
				}
				contador++;
				pos = elements.next(pos);
			}
			if (!salida) {
				elements.addLast(elemAniadir);
			}
			this.size += n;
		}

	}

	public void remove(Element elem, int n) {
		// TODO Auto-generated method stub
		if (n < 0 || n > count(elem))
			throw new IllegalArgumentException();
		if (n != 0) {
			Position<Pair<Element, Integer>> pos = elements.first();
			int cont = 0;
			boolean salir = false;
			boolean removed = false;
			while (!salir && cont < elements.size()) {
				Element val = pos.element().getLeft();
				if ((val == null && elem == null) || ((elem != null) && (val != null) && elem.equals(val))) {
					if ((pos.element().getRight() - n) == 0) {
						elements.remove(pos);
						salir = true;
						removed = true;
					} else {
						pos.element().setRight(pos.element().getRight()-n);
						salir = true;
					}
				}
				cont++;
				if (!removed)
					pos = elements.next(pos);
			}
			this.size -= n;
		}

	}

	public int count(Element elem) {
		Position<Pair<Element, Integer>> pos = elements.first();
		int res = 0;
		boolean enc = false;
		for (int i = 0; i < elements.size() && !enc; i++) {
			if (pos.element().getLeft() == null && elem == null
					|| (pos.element().getLeft() != null && elem != null && pos.element().getLeft().equals(elem))) {
				res = pos.element().getRight();
				enc = true;
			}
			pos = elements.next(pos);
		}
		return res;
	}

	public int size() {
		// TODO Auto-generated method stub
		return this.size;
	}

	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return this.size() == 0;
	}

	public PositionList<Element> allElements() {
        // TODO Auto-generated method stub
        PositionList<Element> copia = new NodePositionList<Element>();
        Position<Pair<Element, Integer>> aux = elements.first();
        int contador=0;
        while(aux!=null) {
            contador=aux.element().getRight();
            for (int i = 0; i < contador; i++) {
                copia.addLast(aux.element().getLeft());
            }
            aux=elements.next(aux);
        }

        return copia;
    }

	public MultiSet<Element> intersection(MultiSet<Element> s) {
		// TODO Auto-generated method stub
		MultiSet<Element> inters = new MultiSetList<>();
		Position<Pair<Element, Integer>> aux = elements.first();
		boolean parar=false;
		int cont=0;
		for(int i=0; i<s.size();i++) {
			parar=false;
			while(cont<elements.size() && !parar) {
				if(s.count(aux.element().getLeft())>0) {
					if(s.count(aux.element().getLeft())>=aux.element().getRight())
						inters.add(aux.element().getLeft(), aux.element().getRight());
					else
						inters.add(aux.element().getLeft(), s.count(aux.element().getLeft()));
				}
				cont++;
				aux=elements.next(aux);
			}
		}
		return inters;
	}

	public MultiSet<Element> sum(MultiSet<Element> s) {
		// TODO Auto-generated method stub
		MultiSet<Element> suma = new MultiSetList<>();
		Position<Pair<Element, Integer>> aux = elements.first();
		PositionList<Element> copia=s.allElements();
		Position<Element> aux2 = copia.first();
		
		while(aux!=null ) {
			if(s.count(aux.element().getLeft())>0)
				suma.add(aux.element().getLeft(), aux.element().getRight()+s.count(aux.element().getLeft()));
			else {
				suma.add(aux.element().getLeft(), aux.element().getRight());
				
			}
			aux=elements.next(aux);
		}
		while(aux2!=null) {
			if(count(aux2.element())==0) {
				suma.add(aux2.element(), 1);
			}
			aux2=copia.next(aux2);
		}
		  
		
		
		return suma;
	}

	public MultiSet<Element> minus(MultiSet<Element> s) {
		// TODO Auto-generated method stub
		MultiSet<Element> resta = new MultiSetList<>();
		Position<Pair<Element, Integer>> aux = elements.first();
		boolean parar=false;
		int cont=0;
		for(int i=0; i<s.size();i++) {
			parar=false;
			while(aux!=null && !parar) {
				if(s.count(aux.element().getLeft())>=0 && s.count(aux.element().getLeft())<=aux.element().getRight()) {
					resta.add(aux.element().getLeft(), (aux.element().getRight())-s.count(aux.element().getLeft()));
					parar=true;
				}
				cont++;
				aux=elements.next(aux);
			}
		}
		if(cont==elements.size()-1 && aux==null) {
			resta.add(elements.prev(aux).element().getLeft(), elements.prev(aux).element().getRight());
		}
		
		return resta;
	}
}
