package com.hari.kari.booking.Util;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ShowIdGeneratorTest {

    IdGenerator showIdGenerator;

    @BeforeAll
    void setup(){
        showIdGenerator = new ShowIdGenerator("Show");
    }
    @Test
    void generateId() {
        System.out.println(showIdGenerator.generateId());

    }

    @Test
    void getPrefix() {
        assertEquals("Show", showIdGenerator.getPrefix());
    }
}