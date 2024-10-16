package aed.individual6;

import es.upm.aedlib.graph.Edge;
import es.upm.aedlib.graph.Vertex;
import es.upm.aedlib.graph.DirectedGraph;
import es.upm.aedlib.map.Map;
import es.upm.aedlib.map.HashTableMap;

public class Suma {
	public static <E> Map<Vertex<Integer>, Integer> sumVertices(DirectedGraph<Integer, E> g) {

		Map<Vertex<Integer>, Integer> res = new HashTableMap<Vertex<Integer>, Integer>();


		for (Vertex<Integer> v : g.vertices()) {
			
			Map<Vertex<Integer>, Integer> auxMap = new HashTableMap<Vertex<Integer>, Integer>();
			res.put(v, sumAux(g, auxMap, v));
		}

		return res;
	}

	private static <E> Integer sumAux(DirectedGraph<Integer, E> g, Map<Vertex<Integer>, Integer> auxMap,
			Vertex<Integer> v) {
		Vertex<Integer> auxVert;
		Integer vertValue=v.element();
		Integer sum = vertValue;
		auxMap.put(v, vertValue);

		for (Edge<E> auxEdge : g.outgoingEdges(v)) {
			auxVert = g.endVertex(auxEdge);
			if (!auxMap.containsKey(auxVert)) {
				sum += sumAux(g, auxMap, auxVert);
			}
		}
		return sum;
	}
}

