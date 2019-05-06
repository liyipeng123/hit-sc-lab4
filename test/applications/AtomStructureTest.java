package applications;

import org.junit.Test;
import static org.junit.Assert.*;

public class AtomStructureTest {
    AtomStructure atom = new AtomStructure();

    /**
     * Test strategy: create an instance to test the AtomStructure's function
     */

    @Test
    public void testGetGap() {
        assertEquals(47, atom.getGap());
    }

    @Test
    public void testJump() {
        assertEquals("(1,2)(2,8)(3,18)(4,8)(5,1)", atom.toString());

        atom.Jump(1,3);
        assertEquals("(1,1)(2,8)(3,19)(4,8)(5,1)", atom.toString());

        atom.Jump(5,2);
        assertEquals("(1,1)(2,9)(3,19)(4,8)(5,0)", atom.toString());
    }

    @Test
    public void testBack() {
        assertEquals("(1,2)(2,8)(3,18)(4,8)(5,1)", atom.toString());

        atom.Jump(5,2);
        assertEquals("(1,2)(2,9)(3,18)(4,8)(5,0)", atom.toString());

        atom.Jump(1,3);
        assertEquals("(1,1)(2,9)(3,19)(4,8)(5,0)", atom.toString());

        atom.Back();
        assertEquals("(1,2)(2,9)(3,18)(4,8)(5,0)", atom.toString());
    }

    @Test
    public void testGetElectronByID() {
        assertEquals(3, atom.getElectronByID(3).getID());

        assertEquals(2, atom.getElectronByID(2).getID());

        assertEquals(1, atom.getElectronByID(1).getID());

        assertEquals(5, atom.getElectronByID(5).getID());
    }

}
