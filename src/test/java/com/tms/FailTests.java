package com.tms;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class FailTests {

    @Test
    public void testFail() {
        // This test is designed to fail
        Assertions.fail("This test is expected to fail");
    }
}
