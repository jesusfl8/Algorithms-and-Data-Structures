package aed.delivery;

import es.upm.aedlib.positionlist.PositionList;
import es.upm.aedlib.Position;
import es.upm.aedlib.positionlist.NodePositionList;
import es.upm.aedlib.graph.DirectedGraph;
import es.upm.aedlib.graph.DirectedAdjacencyListGraph;
import es.upm.aedlib.graph.Vertex;
import es.upm.aedlib.graph.Edge;
import es.upm.aedlib.map.HashTableMap;
import es.upm.aedlib.map.Map;
import es.upm.aedlib.set.HashTableMapSet;
import es.upm.aedlib.set.Set;
import java.util.Iterator;

public class Delivery<V> {

	private DirectedGraph<V, Integer> grafo;

	// Construct a graph out of a series of vertices and an adjacency matrix.
	// There are 'len' vertices. A negative number means no connection. A
	// non-negative
	// number represents distance between nodes.
	public Delivery(V[] places, Integer[][] gmat) {

		grafo = new DirectedAdjacencyListGraph<>();
		for (int i = 0; i < places.length; i++) {
			grafo.insertVertex(places[i]);
		}
		Iterator<Vertex<V>> vertI = grafo.vertices().iterator();
		Iterator<Vertex<V>> vertJ = grafo.vertices().iterator();
		Vertex<V> vertSalida;
		Vertex<V> vertEntrada;

		for (int i = 0; i < places.length; i++) {
			vertSalida = vertI.next();
			vertJ = grafo.vertices().iterator();
			for (int j = 0; j < places.length; j++) {
				vertEntrada = vertJ.next();
				if (gmat[i][j] != null) {
					grafo.insertDirectedEdge(vertSalida, vertEntrada, gmat[i][j]);
				}
			}
		}

	}

	// Just return the graph that was constructed
	public DirectedGraph<V, Integer> getGraph() {
		return grafo;
	}

	// Return a Hamiltonian path for the stored graph, or null if there is noe.
	// The list containts a series of vertices, with no repetitions (even if the
	// path
	// can be expanded to a cycle).
	public PositionList<Vertex<V>> tour() {

		for (Vertex<V> vert : grafo.vertices()) {

			PositionList<Vertex<V>> path = new NodePositionList<>();
			Set<Vertex<V>> mapAux = new HashTableMapSet<>();
			path.addLast(vert);
			mapAux.add(vert);

			path = tourAux(path, mapAux, vert);
			if (path.size() == grafo.numVertices()) {
				return path;
			}

		}
		return null;

	}

	private PositionList<Vertex<V>> tourAux(PositionList<Vertex<V>> path, Set<Vertex<V>> mapAux, Vertex<V> vert) {

		for (Edge<Integer> e : grafo.outgoingEdges(vert)) {
			Vertex<V> vertAux = grafo.endVertex(e);
			if (!mapAux.contains(vertAux)) {
				mapAux.add(vertAux);
				path.addLast(vertAux);
				path = tourAux(path, mapAux, vertAux);
			}
		}
		if (path.size() == grafo.numVertices()) {
			return path;
		}

		path.remove(path.last());
		mapAux.remove(vert);

		return path;
	}

	public int length(PositionList<Vertex<V>> path) {
		int sum = 0;
		Position<Vertex<V>> cursor = path.first();
		Vertex<V> vert;
		while (path.next(cursor) != null) {
			vert = cursor.element();
			cursor = path.next(cursor);
			for (Edge<Integer> e : grafo.outgoingEdges(vert)) {
				if (grafo.endVertex(e).equals(cursor.element()))
					sum += e.element();
			}
		}
		return sum;
	}

	public String toString() {
		return "Delivery";
	}
}
