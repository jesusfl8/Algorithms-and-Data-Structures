package aed.airport;

import es.upm.aedlib.Entry;
import es.upm.aedlib.priorityqueue.*;
import es.upm.aedlib.map.*;
import es.upm.aedlib.positionlist.*;

/**
 * A registry which organizes information on airplane arrivals.
 */
public class IncomingFlightsRegistry {

	private PriorityQueue<Long, FlightArrival> aviones;
	private Map<String, FlightArrival> registryMap;
	private Map<String, Entry<Long, FlightArrival>> mapAux;

	/**
	 * Constructs an class instance.
	 */

	public IncomingFlightsRegistry() {
		aviones = new SortedListPriorityQueue<>();
		registryMap = new HashTableMap<>();
		mapAux = new HashTableMap<>();

	}

	/**
	 * A flight is predicted to arrive at an arrival time (in seconds).
	 */
	public void arrivesAt(String flight, long time) {
		FlightArrival flightAux = new FlightArrival(flight, time);
		if (registryMap.containsKey(flight)) {
			registryMap.get(flight).setRight(time);
			aviones.replaceKey(mapAux.get(flight), time);
		} else {
			registryMap.put(flight, flightAux);
			mapAux.put(flight, aviones.enqueue(time, flightAux));
		}

	}

	/**
	 * A flight has been diverted, i.e., will not arrive at the airport.
	 */
	public void flightDiverted(String flight) {
		if (registryMap.containsKey(flight)) {
			aviones.remove(mapAux.get(flight));
			registryMap.remove(flight);
			mapAux.remove(flight);
		}
	}

	/**
	 * Returns the arrival time of the flight.
	 * 
	 * @return the arrival time for the flight, or null if the flight is not
	 *         predicted to arrive.
	 */
	public Long arrivalTime(String flight) {

		if (registryMap.containsKey(flight)) {
			return registryMap.get(flight).getRight();
		}

		return null;
	}

	/**
	 * Returns a list of "soon" arriving flights, i.e., if any is predicted to
	 * arrive at the airport within nowTime+180 then adds the predicted earliest
	 * arriving flight to the list to return, and removes it from the registry.
	 * Moreover, also adds to the returned list, in order of arrival time, any other
	 * flights arriving withinfirstArrivalTime+120; these flights are also removed
	 * from the queue of incoming flights.
	 * 
	 * @return a list of soon arriving flights.
	 */
	public PositionList<FlightArrival> arriving(long nowTime) {
		PositionList<FlightArrival> arrFlights = new NodePositionList<FlightArrival>();
		Long auxTime = (long) 1;

		while (!aviones.isEmpty() && aviones.first().getKey() - nowTime <= 180 && aviones.first().getKey() >= nowTime) {

			auxTime = aviones.first().getKey();
			arrFlights.addLast(aviones.first().getValue());
			registryMap.remove(aviones.first().getValue().getLeft());
			aviones.dequeue();

		}

		while (!aviones.isEmpty() && aviones.first().getKey() - auxTime <= 120) {

			arrFlights.addLast(aviones.first().getValue());
			registryMap.remove(aviones.first().getValue().getLeft());
			aviones.dequeue();

		}

		return arrFlights;
	}

}
