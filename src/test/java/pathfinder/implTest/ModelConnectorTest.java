package pathfinder.implTest;

import org.junit.Before;
import org.junit.Test;
import pathfinder.ModelConnector;
import pathfinder.datastructures.*;

import static org.junit.Assert.assertEquals;

public class ModelConnectorTest {

    private ModelConnector modelConnector;

    @Before
    public void setUp() {
        modelConnector = new ModelConnector();
    }

    @Test
    public void testGetCoordinates() {
        assertEquals(new Point(1724.1276, 1208.4754), modelConnector.getCoordinate("OUG"));
        assertEquals(new Point(1625.2679, 1783.5181), modelConnector.getCoordinate("PAA"));
    }
}
