package com.geekerit.springbootalipay;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledOnJre;
import org.junit.jupiter.api.condition.JRE;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author Aaryn
 */
public class Junit5Test {
    @Test
    void firstTest(){
        assertEquals(2, 1 + 1);
    }

    @Test
    @EnabledOnJre(JRE.JAVA_9)
    void testOnJava4(){
        Assertions.assertTimeout(Duration.ofMillis(10),() -> {
            Thread.sleep(20);
        });
    }
}
