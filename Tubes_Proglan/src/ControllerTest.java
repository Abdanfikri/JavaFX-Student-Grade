import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ControllerTest {

    @Test
    void isInputValid() {
        assertTrue(Controller.isInputValid("100", "75", "90", "85", "88", "92", "78", "87", "88", "95"));
        assertFalse(Controller.isInputValid("", "75", "90", "85", "88", "92", "78", "87", "88", "95")); // Input pertama kosong

    }

    @Test
    void isAlphaWithSpace() {
        assertTrue(Controller.isAlphaWithSpace("Abdan"));
        assertFalse(Controller.isAlphaWithSpace("Abdan fikri 123")); // Terdapat angka dalam nama
        assertFalse(Controller.isAlphaWithSpace("Abdan@Dfikri")); // Terdapat karakter khusus dalam nama
    }


    @Test
    void isInRange() {
        assertTrue(Controller.isInRange(100, 75, 90, 85, 88, 92, 78, 87, 88,75));
        assertFalse(Controller.isInRange(110, 75, 90, 85, 88, 92, 78, 87, 88, 95)); // Salah satu nilai di atas 100
        assertFalse(Controller.isInRange(-5, 75, 90, 85, 88, 92, 78, 87, 88, 95)); // Salah satu nilai di bawah 0

    }


}