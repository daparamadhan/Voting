package programmer.zaman.now.maven;

import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Unit test for App.
 */
public class AppTest {

    private App app;

    /**
     * Method ini dijalankan sebelum setiap @Test
     */
    @Before
    public void setUp() {
        app = new App();
        System.out.println("Set up App instance before test.");
    }

    /**
     * Method ini dijalankan setelah setiap @Test
     */
    @After
    public void tearDown() {
        System.out.println("Clean up after test.");
    }

    /**
     * Test method isActive harus mengembalikan true.
     */
    @Test
    public void testIsActive() {
        assertTrue("App seharusnya aktif", app.isActive());
    }

    /**
     * Test sum untuk memastikan penjumlahan benar.
     */
    @Test
    public void testSum() {
        assertEquals("2 + 3 seharusnya 5", 5, app.sum(2, 3));
        assertEquals("0 + 0 seharusnya 0", 0, app.sum(0, 0));
        assertEquals("-1 + (-1) seharusnya -2", -2, app.sum(-1, -1));
    }

    /**
     * Contoh test dummy, mirip default generate Maven.
     */
    @Test
    public void shouldAnswerWithTrue() {
        assertTrue(true);
    }
}
