package com.tms.controllers;

import com.tms.exceptions.UserUpdateException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.Duration;

public class UserControllerTest {

    @Test
    public void myFirstTest() {
        //1. донастройка
        //2. запуск метода
        //3.сравнение
        Assertions.assertEquals(1, 1);
        Assertions.assertNotEquals(1, 2);
        Assertions.assertTrue(true);
        Assertions.assertFalse(false);
        Assertions.assertNull(null);
        Assertions.assertNotNull(1);
        Assertions.assertArrayEquals(new int[]{1, 2, 3}, new int[]{1, 2, 3});
        Assertions.assertThrows(UserUpdateException.class, () -> {
            throw new UserUpdateException("can not update :(");
        });
        Assertions.assertTimeout(Duration.ofMillis(2000), () -> {
            Thread.sleep(1000);
        });
        //Assertions.fail("Не удалось подключиться к БД");
        Assertions.assertDoesNotThrow(() -> {
            System.out.println(10);
        });
    }
}
