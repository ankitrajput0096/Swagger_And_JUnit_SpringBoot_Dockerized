package com.example.Spring_Boot_JPA.controller;


import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
public class SimpleControllerTest {

    //This signifies the component for which test case is being used for.
    @InjectMocks
    SimpleController simpleController;

    //This is used to set up context at entire test case file level
    @BeforeClass
    public static void beforeClassTestBegins() {
        System.out.println("Before Class");
        System.out.println("Executed once, before all tests start. It " +
                "is used to perform time intensive activities, " +
                "for example, to connect to a database");
    }

    //This is used to set up context of single test case level
    @Before
    public void beforeTestBegins() {
        System.out.println("This is excepted before each test. It is " +
                "used to prepare the test environment");
    }

    @Test
    public void testHelloFriendsMethod() {

        // Given
        // setup fake data here when needed.

        // When
        ResponseEntity<String> response = simpleController.helloFriendsMethod();

        // Then
        // Assertions
        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        assertNotNull("Checking whether response body is of type 'String'", response.getBody());
        assertEquals("hello friends", response.getBody());
        assertEquals("Response should be 'OK'", HttpStatus.OK,
                response.getStatusCode());
        assertNotNull("Checking whether response is not null or not",
                response.getBody());
    }

    //This is used to destroy context of single test case level
    @After
    public void afterTestCompletes() {
        System.out.println("This is excepted after each test. It is used" +
                " to cleanup the test environment");
    }

    //This is used to destroy context at entire test case file level
    @AfterClass
    public static void afterClassTestCompletes() {
        System.out.println("After Class");
        System.out.println("Executed once, after all tests completes. It " +
                "is used to perform clean-up activities, " +
                "for example, to disconnect from a database");
    }
}