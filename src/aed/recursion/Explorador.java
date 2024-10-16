package aed.recursion;



import java.util.Iterator;

import es.upm.aedlib.Pair;
import es.upm.aedlib.positionlist.*;

public class Explorador {

	public static Pair<Object, PositionList<Lugar>> explora(Lugar inicialLugar) {

		PositionList<Lugar> movesList = new NodePositionList<Lugar>();
		movesList.addLast(inicialLugar);
		Pair<Object, PositionList<Lugar>> res = null;// Variable auxiliar res para guardar el resultado

		return recorrerLugares(inicialLugar, movesList, res);
	}

	private static Pair<Object, PositionList<Lugar>> recorrerLugares(Lugar inicialLugar, PositionList<Lugar> movesList,
			Pair<Object, PositionList<Lugar>> res) {

		if (inicialLugar.tieneTesoro()) {// Caso base

			Object tesoro = inicialLugar.getTesoro();
			res = new Pair<Object, PositionList<Lugar>>(tesoro, movesList);//
			return res;
		}
		inicialLugar.marcaSueloConTiza();

		Iterator<Lugar> caminosIter = inicialLugar.caminos().iterator();

		while (caminosIter.hasNext()) {// Si hay caminos por explorar y aun no se ha encontrado el tesoro se ejecuta
			Lugar lugarNext = caminosIter.next();
			if (!lugarNext.sueloMarcadoConTiza()) {// Si "lugarNext" no esta marcado con tiza, lo aniadimos a
													// "movesList"(llamando recursivamente a explora).
				res = explora(lugarNext);
			}
			if (res != null) {
				movesList = res.getRight();
				movesList.addFirst(inicialLugar);
				res = new Pair<Object, PositionList<Lugar>>(res.getLeft(), movesList);
				return res;
			}
		}
		return null;
	}
}
