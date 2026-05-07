package com.tms.controllers;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@DisplayName("Класс тестирования юзер контроллера")
public class UserControllerTest {

    @BeforeAll
    public static void beforeAll() {
        System.out.println("Before All");
    }

    @BeforeEach
    public void beforeEach() {
        System.out.println("Before Each");
    }

    @AfterEach
    public void afterEach() {
        System.out.println("After Each");
    }

    @AfterAll
    public static void afterAll() {
        System.out.println("After All");
    }

    @Disabled
    @RepeatedTest(5)
    @DisplayName("Мой первый тест")
    public void myFirstTest() {
        System.out.println("myFirstTest");
    }

    @Test
    public void mySecondTest() {
        System.out.println("mySecondTest");
    }

    @Tag("tag-annotation")
    @Test
    public void myThirdTest() {
        System.out.println("myThirdTest");
    }
}
