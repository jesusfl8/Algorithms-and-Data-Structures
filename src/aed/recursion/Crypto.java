package aed.recursion;

import es.upm.aedlib.Pair;
import es.upm.aedlib.Position;
import es.upm.aedlib.positionlist.PositionList;
import es.upm.aedlib.positionlist.NodePositionList;

public class Crypto {

	public static PositionList<Integer> encrypt(PositionList<Character> key, PositionList<Character> text) {

		PositionList<Integer> moves = new NodePositionList<Integer>();
		Position<Character> cursorKey = key.first();
		Position<Character> cursorText = text.first();
		Integer cont = 0;

		movimientos(key, text, cursorText, cont, cursorKey, moves);

		return moves;
	}

	private static void movimientos(PositionList<Character> key, PositionList<Character> text,
			Position<Character> cursorText, Integer cont, Position<Character> cursorKey, PositionList<Integer> moves) {

		if (text.size() == moves.size()) {// Caso Base
			return;
		}

		if (cursorKey.element().equals(cursorText.element())) {
			cursorText = text.next(cursorText);
			moves.addLast(cont);
			cont = 0;
			movimientos(key, text, cursorText, cont, cursorKey, moves);

		} else {
			if (cursorText.element() > cursorKey.element()) {
				cursorKey = key.next(cursorKey);
				cont++;
				movimientos(key, text, cursorText, cont, cursorKey, moves);
			} else {
				cursorKey = key.prev(cursorKey);
				cont--;
				movimientos(key, text, cursorText, cont, cursorKey, moves);
			}
		}
	}

	public static PositionList<Character> decrypt(PositionList<Character> key, PositionList<Integer> encodedText) {
		PositionList<Character> letras = new NodePositionList<Character>();
		Position<Character> cursorKey = key.first();
		Position<Integer> cursorEn = encodedText.first();
		Integer cont = 0;

		desencriptar(key, encodedText, cursorKey, cursorEn, cont, letras);

		return letras;
	}

	private static void desencriptar(PositionList<Character> key, PositionList<Integer> encodedText,
			Position<Character> cursorKey, Position<Integer> cursorEn, Integer cont, PositionList<Character> letras) {

		if (encodedText.size() == letras.size())
			return;

		if (cont.equals(cursorEn.element())) {
			cursorEn = encodedText.next(cursorEn);
			letras.addLast(cursorKey.element());
			cont = 0;
			desencriptar(key, encodedText, cursorKey, cursorEn, cont, letras);
		} else {
			if (cont < cursorEn.element()) {
				cursorKey = key.next(cursorKey);
				cont++;
				desencriptar(key, encodedText, cursorKey, cursorEn, cont, letras);
			} else {
				cursorKey = key.prev(cursorKey);
				cont--;
				desencriptar(key, encodedText, cursorKey, cursorEn, cont, letras);
			}

		}
	}

}