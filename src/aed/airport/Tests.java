package aed.airport;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import es.upm.aedlib.positionlist.*;

public class Tests {
	@Test
	public void prop1() {
		IncomingFlightsRegistry airport = new IncomingFlightsRegistry();
		airport.arrivesAt("avion", 1050);
		airport.arrivesAt("avion", 1200);
		assertEquals(1200, airport.arrivalTime("avion"));
	}

	@Test
	public void prop2() {
		IncomingFlightsRegistry airport = new IncomingFlightsRegistry();
		NodePositionList<FlightArrival> expected = new NodePositionList<FlightArrival>();
		airport.arrivesAt("avion1", 20);
		airport.arrivesAt("avion2", 10);
		expected.addFirst(new FlightArrival("avion2", 10));
		expected.addLast(new FlightArrival("avion1", 20));
		assertEquals(expected, airport.arriving(0));

	}

}
