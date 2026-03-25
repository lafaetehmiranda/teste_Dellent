package org.acme;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;
import java.math.BigInteger;
import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTimeout;

@QuarkusTest
class LabSeqServiceTest {

    @Inject
    LabSeqService labSeqService;

    @Test
    void testBaseCases() {
        assertEquals(BigInteger.ZERO, labSeqService.calculate(0));
        assertEquals(BigInteger.ONE, labSeqService.calculate(1));
        assertEquals(BigInteger.ZERO, labSeqService.calculate(2));
        assertEquals(BigInteger.ONE, labSeqService.calculate(3));
    }

    @Test
    void testSequenceCalculation() {
        // Known values from documentation
        // 0, 1, 0, 1, 1, 1, 1, 2, 2, 2, 3
        assertEquals(BigInteger.ONE, labSeqService.calculate(4));
        assertEquals(BigInteger.ONE, labSeqService.calculate(5));
        assertEquals(BigInteger.ONE, labSeqService.calculate(6));
        assertEquals(BigInteger.valueOf(2), labSeqService.calculate(7));
        assertEquals(BigInteger.valueOf(2), labSeqService.calculate(8));
        assertEquals(BigInteger.valueOf(2), labSeqService.calculate(9));
        assertEquals(BigInteger.valueOf(3), labSeqService.calculate(10));
    }

    @Test
    void testPerformance100k() {
        // Requires calculation of l(100000) in less than 10 seconds.
        assertTimeout(Duration.ofSeconds(10), () -> {
            BigInteger result = labSeqService.calculate(100000);
            System.out.println("Length of l(100000): " + result.toString().length() + " digits");
        }, "Calculation took longer than 10 seconds");
    }
}
