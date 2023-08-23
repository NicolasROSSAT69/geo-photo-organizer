package org.nrossat.processing;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class ChronometerTest {
    @Test
    public void testChronometer() throws InterruptedException {
        Chronometer chronometer = new Chronometer();

        chronometer.start();
        Thread.sleep(100);  // Attend 100 millisecondes
        long elapsed = chronometer.stop();

        // Vérifie si le temps écoulé est approximativement 100 millisecondes
        // Autorise une petite marge d'erreur (par exemple, 10 millisecondes) pour tenir compte des imprécisions
        assertTrue(elapsed >= 90 && elapsed <= 110);
    }
}
