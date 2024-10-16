package aed.individual3;

import java.util.Iterator;

import es.upm.aedlib.Position;
import es.upm.aedlib.positionlist.NodePositionList;
import es.upm.aedlib.positionlist.PositionList;

public class OperacionCompactar {

	/**
	 * Metodo que reduce los elementos iguales consecutivos de una lista a una unica
	 * repeticion
	 * 
	 * @param lista Lista de entrada
	 * @return Lista nueva compactada sin elementos iguales consecutivos
	 */
	public static <E> PositionList<E> compactar(Iterable<E> lista) {
		PositionList<E> result = new NodePositionList<E>();
		Iterator<E> iter = lista.iterator();
		E aux;
		if (lista == null)
			throw new IllegalArgumentException();

		result.addLast(iter.next());

		Position<E> cursor = result.first();

		while (iter.hasNext()) {
			aux = iter.next();
			if (aux == null || cursor.element() == null) {
				if (aux != cursor.element()) {
					result.addLast(aux);
					cursor = result.next(cursor);
				}
			}

			else if (!aux.equals(cursor.element())) {
				result.addLast(aux);
				cursor = result.next(cursor);
			}

		}
		return result;
	}
}
