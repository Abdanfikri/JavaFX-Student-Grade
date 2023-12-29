import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ControllerTest {

    @Test
    void isInputValid() {
        assertTrue(Controller.isInputValid("100", "75", "90", "85", "88", "92", "78", "87", "88", "95"));
        assertFalse(Controller.isInputValid("", "75", "90", "85", "88", "92", "78", "87", "88", "95"));
    }

    @Test
    void isAlphaWithSpace() {
        assertTrue(Controller.isAlphaWithSpace("Abdan"));
        assertFalse(Controller.isAlphaWithSpace("Abdan fikri 123"));
        assertFalse(Controller.isAlphaWithSpace("Abdan@Dfikri"));
    }


    @Test
    void isInRange() {
        assertTrue(Controller.isInRange(100, 75, 90, 85, 88, 92, 78, 87, 88,75));
        assertFalse(Controller.isInRange(110, 75, 90, 85, 88, 92, 78, 87, 88, 95));
        assertFalse(Controller.isInRange(-5, 75, 90, 85, 88, 92, 78, 87, 88, 95));

    }


}