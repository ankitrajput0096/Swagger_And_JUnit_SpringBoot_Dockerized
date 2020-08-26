package com.example.Spring_Boot_JPA.controller;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.util.List;

import com.example.Spring_Boot_JPA.model.Topic;
import com.example.Spring_Boot_JPA.service.springBootService;
import com.example.Spring_Boot_JPA.utils.TestUtils;

import static org.hamcrest.Matchers.is;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;


@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
public class SpringControllerTest {

    /* mocking dependencies you want for this testcase */
    @Mock
    private springBootService springbootservice;
    /* Can add more dependencies here if required */

    /* mocking component for which the testcase is being written */
    @InjectMocks
    SpringController springController;


    //This is used to setup context at entire test case file level
    @BeforeClass
    public static void beforeClassTestBegins() {
        System.out.println("Before Class");
        System.out.println("Executed once, before all tests " +
                "start. It is used to perform time intensive " +
                "activities, for example, to connect to a database");
    }

    //This is used to setup context of single test case level
    @Before
    public void setupBeforeEachTestCase() {

        /*
        When using mocks for dependency injection, we need to reset
        the Mock objects before every test...
        */
        reset(springbootservice);
        /*
        Similarly, add all the mocked dependencies
        */
    }

    //-----------------------------------------------------------------------------------------------------------------------------------------------------------

    /*
    Method Name : helloMethod
    Description : This testcase tests the scenario where endpoint
    returns 'hello World' string.
    */
    @Test
    public void helloMethodTest() {
        // Test data

        // Mocks
        
        // The logic we're testing in this testcase
        ResponseEntity<String> response = springController.helloMethod();

        // Verify the times mocked component is called

        // Test case Assertions
        assertThat(response.getStatusCodeValue(), is(200));
        assertTrue("Checking whether respose body is of type 'String'",
                response.getBody() instanceof String);
        assertEquals("Hello World", response.getBody());
        assertEquals("Response should be 'OK'", HttpStatus.OK,
                response.getStatusCode());
        assertNotNull("Checking whether response is not null or not",
                response.getBody());
    }

    //-----------------------------------------------------------------------------------------------------------------------------------------------------------

    /*
    Method Name : listOfTopcs
    Description : This testcase tests the scenario where
    we retrive all list of Topic objects in db
    */
    @Test
    public void listOfTopcsTest() {
        // Test data
        List<Topic> fakeTopicList = TestUtils.createFakeTopicList(5);

        // Mocks
        when(springbootservice.getAllTopics()).thenReturn(fakeTopicList);

        // The logic we're testing in this testcase
        ResponseEntity<List<Topic>> response = springController.listOfTopcs();

        // Verify the times mocked component is called
        verify(springbootservice, times(1)).getAllTopics();

        // Test case Assertions
        assertArrayEquals("checking the fakelist are equal",
                fakeTopicList.toArray(), response.getBody().toArray());
        assertThat("the response status code should be '200'",
                response.getStatusCodeValue(), is(200));
        assertEquals("Response should be 'OK'", HttpStatus.OK,
                response.getStatusCode());
        assertTrue("Body should not be null", response.hasBody());
        assertFalse("Response should not be '404-not found'",
                HttpStatus.NOT_FOUND == response.getStatusCode());
        assertNotNull("Checking the response is not null", response.getBody());
        // NOTE : using streams in testcases
        assertTrue("All the objects of Topic list are of instance Topic",
                response.getBody().stream()
                        .allMatch((e) -> e instanceof Topic));
    }
    
    // ----------------------------------------------------------------------------------------------------------------------------------------------------------
    /* NOTE : 
    Here, we are writing test case for method 'getRequiredTopic'. 
    So, we are writing two testcases which cover both positive and
    negative scenario of this method.
     */
    /* 
    Method Name : getRequiredTopic
    Description : This testcase tests the scenario where Topic object
    is present with specific ID
    */
    @Test
    public void getRequiredTopicTest() {
        // Test data
        String fakeID = "fakeID";
        String fakeName = "fakeName";
        String fakeDescription = "fakeDescription";
        Topic fakeTopic = TestUtils.createTopicObject(
                fakeID,
                fakeName,
                fakeDescription);

        // Mocks
        when(springbootservice.getTopic(fakeID)).thenReturn(fakeTopic);

        // The logic we're testing in this testcase
        ResponseEntity<Topic> response = springController
                .getRequiredTopic(fakeID);

        // Verify the times mocked component is called
        verify(springbootservice, times(1)).getTopic(fakeID);

        // Test case Assertions
        assertThat("the response code should be '302'",
                response.getStatusCodeValue(), is(302));
        assertEquals("Response should be 'FOUND'",
                HttpStatus.FOUND, response.getStatusCode());
        assertTrue("Check fake Topic ID are equal",
                response.getBody().getId() == fakeID);
        assertFalse("Check fake Description is not empty string",
                response.getBody().getDescription() == "");
        assertNotNull("Response body is not null", response.hasBody());
        assertSame("Checking whether fakeName are same",
                response.getBody().getName(), fakeName);
        assertNotSame("Checking whether fakeName length not 1",
                new Integer(response.getBody().getName().length()),
                new Integer(2));
    }

    /* 
    Method Name : getRequiredTopic
    Description : This testcase tests the scenario where Topic
    object is not present with specific ID
    */
    @Test
    public void getRequiredTopicTest2() {
        // Test data
        String fakeID = "fakeID";
        String fakeName = "fakeName";
        String fakeDescription = "fakeDescription";
        Topic fakeTopic = null;

        // Mocks
        when(springbootservice.getTopic(fakeID)).thenReturn(fakeTopic);

        // The logic we're testing in this testcase
        ResponseEntity<Topic> response = springController
                .getRequiredTopic(fakeID);

        // Verify the times mocked component is called
        verify(springbootservice, times(1)).getTopic(fakeID);

        // Test case Assertions
        assertThat("the response code should be '404'",
                response.getStatusCodeValue(), is(404));
        assertEquals("Response should be 'NOT_FOUND'",
                HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull("Response body is null", response.getBody());
        assertSame("Checking whether response body is null",
                response.getBody(), fakeTopic);
        assertFalse("Check fake Topic is not equal to required object",
                response.getBody() == TestUtils.createTopicObject(fakeID,
                        fakeName, fakeDescription));
    }

    //-----------------------------------------------------------------------------------------------------------------------------------------------------------

    /* 
    Method Name : addTopicTest
    Description : This testcase tests the scenario where
    Topic object is created in DB.
    */
    @Test
    public void addTopicTest() {
        // Test data
        String fakeID = "fakeID";
        String fakeName = "fakeName";
        String fakeDescription = "fakeDescription";
        Topic fakeTopic = TestUtils.createTopicObject(
                fakeID, fakeName, fakeDescription);

        // Mocks
        when(springbootservice.addTopic(fakeTopic)).thenReturn(fakeTopic);

        // The logic we're testing in this testcase
        ResponseEntity<?> response = springController.addTopic(fakeTopic);

        // Verify the times mocked component is called
        verify(springbootservice, times(1)).addTopic(fakeTopic);

        // Test case Assertions
        assertThat("the response code should be '201'",
                response.getStatusCodeValue(), is(201));
        assertEquals("Response should be 'CREATED'",
                HttpStatus.CREATED, response.getStatusCode());
        assertTrue("Check response body as null", response.getBody() == null);
        assertFalse("Check response code is 'HttpStatus.OK'",
                response.getStatusCode() == HttpStatus.OK);
        assertNull("Response body is null", response.getBody());
    }

    //-----------------------------------------------------------------------------------------------------------------------------------------------------------

    /* 
    Method Name : updateTopicTest
    Description : This testcase tests the scenario where
    Topic object is updated in DB by passing new Topic object and
    Topic ID which needs to be updated.
    */
    @Test
    public void updateTopicTest() {
        // Test data
        String fakeID = "fakeID";
        String fakeName = "fakeName";
        String fakeDescription = "fakeDescription";
        Topic fakeTopic = TestUtils.createTopicObject(
                fakeID, fakeName, fakeDescription);

        // Mocks
        when(springbootservice.updatetopic(fakeTopic, fakeID))
                .thenReturn(fakeTopic);

        // The logic we're testing in this testcase
        ResponseEntity<?> response = springController
                .updateTopic(fakeTopic, fakeID);

        // Verify the times mocked component is called
        verify(springbootservice, times(1))
                .updatetopic(fakeTopic, fakeID);

        // Test case Assertions
        assertThat("the response code should be '200'",
                response.getStatusCodeValue(), is(200));
        assertEquals("Response should be 'OK'",
                HttpStatus.OK, response.getStatusCode());
        assertTrue("Check response body as null", response.getBody() == null);
        assertFalse("Check response code is 'HttpStatus.CREATED'",
                response.getStatusCode() == HttpStatus.CREATED);
        assertNull("Response body is null", response.getBody());
    }

    //-----------------------------------------------------------------------------------------------------------------------------------------------------------

    /* 
    Method Name : deleteTopicTest
    Description : This testcase tests the scenario where Topic object
    is deleted in DB by passing Topic ID which needs to be deleted.
    */
    @Test
    public void deleteTopicTest() {
        // Test data
        String fakeID = "fakeID";

        // Mocks
        // NOTE : way to mock method which does'nt return anything
        doNothing().when(springbootservice).deletetopic(fakeID);

        // The logic we're testing in this testcase
        ResponseEntity<?> response = springController.deleteTopic(fakeID);

        // Verify the times mocked component is called
        verify(springbootservice, times(1)).deletetopic(fakeID);

        // Test case Assertions
        assertThat("the response code should be '200'",
                response.getStatusCodeValue(), is(200));
        assertEquals("Response should be 'OK'", HttpStatus.OK,
                response.getStatusCode());
        assertTrue("Check response body as null", response.getBody() == null);
        assertFalse("Check response code is 'HttpStatus.CREATED'",
                response.getStatusCode() == HttpStatus.CREATED);
        assertNull("Response body is null", response.getBody());
    }


    // ----------------------------------------------------------------------------------------------------------------------------------------------------------
    /* NOTE : 
    Here, we are writing test case for method 'getByIdTest'. 
    So, we are writing two testcases which cover both positive and
    negative scenario of this method.
     */
    /* 
    Method Name : getById
    Description : This testcase tests the scenario where Topic object
    is present with specific ID
    */
    @Ignore /* ================> Way to explicitly ignore a test case */
    @Test
    public void getByIdTest() {
        // Test data
        String fakeID = "fakeID";
        String fakeName = "fakeName";
        String fakeDescription = "fakeDescription";
        Topic fakeTopic = TestUtils.createTopicObject(
                fakeID, fakeName, fakeDescription);

        // Mocks
        when(springbootservice.getById(fakeID)).thenReturn(fakeTopic);

        // The logic we're testing in this testcase
        ResponseEntity<Topic> response = springController.getById(fakeID);

        // Verify the times mocked component is called
        verify(springbootservice, times(1)).getById(fakeID);

        // Test case Assertions
        assertThat("the response code should be '302'",
                response.getStatusCodeValue(), is(302));
        assertEquals("Response should be 'FOUND'", HttpStatus.FOUND,
                response.getStatusCode());
        assertTrue("Check fake Topic ID are equal",
                response.getBody().getId() == fakeID);
        assertFalse("Check fake Description is not empty string",
                response.getBody().getDescription() == "");
        assertNotNull("Response body is not null", response.hasBody());
        assertSame("Checking whether fakeName are same",
                response.getBody().getName(), fakeName);
        assertNotSame("Checking whether fakeName length not 1",
                new Integer(response.getBody().getName().length()),
                new Integer(2));
    }

    /* 
    Method Name : getById
    Description : This testcase tests the scenario where Topic object
    is not present with specific ID
    */
    @Test
    public void getByIdTest2() {
        // Test data
        String fakeID = "fakeID";
        String fakeName = "fakeName";
        String fakeDescription = "fakeDescription";
        Topic fakeTopic = null;

        // Mocks
        when(springbootservice.getById(fakeID)).thenReturn(fakeTopic);

        // The logic we're testing in this testcase
        ResponseEntity<Topic> response = springController.getById(fakeID);

        // Verify the times mocked component is called
        verify(springbootservice, times(1)).getById(fakeID);

        // Test case Assertions
        assertThat("the response code should be '404'",
                response.getStatusCodeValue(), is(404));
        assertEquals("Response should be 'NOT_FOUND'",
                HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull("Response body is null", response.getBody());
        assertSame("Checking whether response body is null",
                response.getBody(), fakeTopic);
        assertFalse("Check fake Topic is not equal to required object",
                response.getBody() == TestUtils.createTopicObject(
                        fakeID, fakeName, fakeDescription));
    }

    // ----------------------------------------------------------------------------------------------------------------------------------------------------------

    /* NOTE : 
    Here, we are writing test case for method 'getByIdAndName'. 
    So, we are writing two testcases which cover both positive and
    negative scenario of this method.
     */
    /* 
    Method Name : getByIdAndName
    Description : This testcase tests the scenario where Topic object
    is present with specific ID and specific Name
    */
    @Test
    public void getByIdAndNameTest() {
        // Test data
        String fakeID = "fakeID";
        String fakeName = "fakeName";
        String fakeDescription = "fakeDescription";
        Topic fakeTopic = TestUtils.createTopicObject(
                fakeID, fakeName, fakeDescription);

        // Mocks
        when(springbootservice.getByIdAndName(fakeID, fakeName))
                .thenReturn(fakeTopic);

        // The logic we're testing in this testcase
        ResponseEntity<Topic> response = springController
                .getByIdAndName(fakeID, fakeName);

        // Verify the times mocked component is called
        verify(springbootservice, times(1)).getByIdAndName(fakeID, fakeName);

        // Test case Assertions
        assertThat("the response code should be '302'",
                response.getStatusCodeValue(), is(302));
        assertEquals("Response should be 'FOUND'",
                HttpStatus.FOUND, response.getStatusCode());
        assertTrue("Check fake Topic ID are equal",
                response.getBody().getId() == fakeID);
        assertFalse("Check fake Description is not empty string",
                response.getBody().getDescription() == "");
        assertNotNull("Response body is not null", response.hasBody());
        assertSame("Checking whether fakeName are same", response.getBody().getName(),
                fakeName);
        assertNotSame("Checking whether fakeName length not 1",
                new Integer(response.getBody().getName().length()), new Integer(2));
    }

    /* 
    Method Name : getByIdAndName
    Description : This testcase tests the scenario where Topic object
    is not present with specific ID and specific Name
    */
    @Test
    public void getByIdAndNameTest2() {
        // Test data
        String fakeID = "fakeID";
        String fakeName = "fakeName";
        String fakeDescription = "fakeDescription";
        Topic fakeTopic = null;

        // Mocks
        when(springbootservice.getByIdAndName(fakeID, fakeName))
                .thenReturn(fakeTopic);

        // The logic we're testing in this testcase
        ResponseEntity<Topic> response = springController
                .getByIdAndName(fakeID, fakeName);

        // Verify the times mocked component is called
        verify(springbootservice, times(1)).getByIdAndName(fakeID, fakeName);

        // Test case Assertions
        assertThat("the response code should be '404'",
                response.getStatusCodeValue(), is(404));
        assertEquals("Response should be 'NOT_FOUND'",
                HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull("Response body is null", response.getBody());
        assertSame("Checking whether response body is null",
                response.getBody(), fakeTopic);
        assertFalse("Check fake Topic is not equal to required object",
                response.getBody() == TestUtils.createTopicObject(
                        fakeID, fakeName, fakeDescription));
    }

    //-----------------------------------------------------------------------------------------------------------------------------------------------------------

    //This is used to destroy context of single test case level
    @After
    public void tearDownAfterEachTestCase() {

        /* 
        When using mocks for dependency injection,
        we need to verify after test case has
        ended no more interactions are made with mocked dependencies.
        */
        verifyNoMoreInteractions(springbootservice);
        /*
        Similarly, add all the mocked dependencies
        */
    }

    //This is used to destroy context at entire test case file level
    @AfterClass
    public static void afterClassTestCompletes() {
        System.out.println("After Class");
        System.out.println("Executed once, after all tests completes. " +
                "It is used to perform clean-up activities, for example, " +
                "to disconnect from a database");
    }
}