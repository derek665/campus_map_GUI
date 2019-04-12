/**
 * This is part of a CSE331 Assignment: Environment Setup and Java Introduction.
 */
package setup;

import static org.junit.Assert.assertEquals;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import org.junit.Test;
import java.util.Scanner;

/**
 * HolaWorldTest is a simple test of the HolaWorld class that you
 * are to fix.  This test just makes sure that the program
 * does not crash and that the correct greeting is printed.
 */
public class HolaWorldTest {

    /**
     * Tests that HolaWorld does not crash
     */
    @Test
    public void testCrash() {
        /* Any exception thrown will be caught by JUnit. */
        HolaWorld.main(new String[0]);
    }

    /**
     * Tests that the HolaWorld greeting is correct.
     */
    @Test
    public void testGreeting() {
        HolaWorld world = new HolaWorld();
        assertEquals(HolaWorld.SPANISH_GREETING, world.getGreeting());
    }

    /**
     * Tests that the output of HolaWorld.main() is correct.
     * @throws FileNotFoundException 
     */
    @Test
    public void testMainOutput() throws FileNotFoundException {
        
        // Redirect System.out to an OutputStream
        PrintStream sysoutStream = System.out;
        ByteArrayOutputStream mainOutput = new ByteArrayOutputStream();
        System.setOut(new PrintStream(mainOutput));
        
        HolaWorld.main(new String[0]);
        System.setOut(sysoutStream);

        Scanner scan = new Scanner(mainOutput.toString());
        assertEquals(scan.nextLine(), HelloWorld.GREETING);
        assertEquals(scan.nextLine(), HolaWorld.SPANISH_GREETING);
    }
}
