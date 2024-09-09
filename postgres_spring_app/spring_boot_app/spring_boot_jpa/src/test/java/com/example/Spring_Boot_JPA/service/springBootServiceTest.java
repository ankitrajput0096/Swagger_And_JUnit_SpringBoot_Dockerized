package com.example.Spring_Boot_JPA.service;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;

import java.util.List;

import com.example.Spring_Boot_JPA.model.Topic;
import com.example.Spring_Boot_JPA.repository.TopicRepository;
import com.example.Spring_Boot_JPA.utils.TestUtils;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import java.util.Optional;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
public class springBootServiceTest {

    /* mocking dependencies you want for this testcase */
    @Mock
    private TopicRepository topicRepository;
    /* Can add more dependencies here if required */

    /* mocking component for which the testcase is being written */
    @InjectMocks
    springBootService springbootService;

    //This is used to setup context at entire test case file level
    @BeforeClass
    public static void beforeClassTestBegins() {
        System.out.println("Before Class");
        System.out.println("Executed once, before all tests start. " +
                "It is used to perform time intensive activities, " +
                "for example, to connect to a database");
    }

    //This is used to setup context of single test case level
    @Before
    public void setupBeforeEachTestCase() {

        /*
        When using mocks for dependency injection, we need to reset
        the Mock objects before every test...
        */
        reset(topicRepository);
        /*
        Similarly, add all the mocked dependencies
        */
    }

    //-----------------------------------------------------------------------------------------------------------------------------------------------------------

    /*
    Method Name : getAllTopics
    Description : This testcase tests the scenario where we retrive
    all list of Topic objects in db
    */
    @Test
    public void getAllTopicsTest() {
        // Test data
        List<Topic> fakeTopicList = TestUtils.createFakeTopicList(5);

        // Mocks
        when(topicRepository.findAll()).thenReturn(fakeTopicList);

        // The logic we're testing in this testcase
        List<Topic> allFakeTopics = springbootService.getAllTopics();

        // Verify the times mocked component is called
        verify(topicRepository, times(1)).findAll();

        // Test case Assertions
        assertArrayEquals("checking the fakelist are equal",
                fakeTopicList.toArray(), allFakeTopics.toArray());
        assertTrue("The fake list size is 5", allFakeTopics.size() == 5);
        assertFalse("The fake list size is not 0", allFakeTopics.size() == 0);
        // NOTE : using streams in testcases
        assertTrue("All the objects of Topic list are of instance Topic",
                allFakeTopics.stream().allMatch((e) -> e instanceof Topic));
    }

    // ----------------------------------------------------------------------------------------------------------------------------------------------------------
    /* NOTE : 
    Here, we are writing test case for method 'getTopicTest'. 
    So, we are writing two testcases which cover both positive
    and negative scenario of this method.
     */
    /* 
    Method Name : getTopicTest
    Description : This testcase tests the scenario where Topic
     object is present with specific ID
    */
    @Test
    public void getTopicTest() {
        // Test data
        String fakeID = "fakeID";
        String fakeName = "fakeName";
        String fakeDescription = "fakeDescription";
        Topic fakeTopicObject = TestUtils.createTopicObject(
                fakeID, fakeName, fakeDescription);
        Optional<Topic> fakeTopic = Optional.of(fakeTopicObject);

        // Mocks
        when(topicRepository.findById(fakeID)).thenReturn(fakeTopic);

        // The logic we're testing in this testcase
        Topic TopicWithGivenID = springbootService.getTopic(fakeID);

        // Verify the times mocked component is called
        verify(topicRepository, times(1)).findById(fakeID);

        // Test case Assertions
        assertEquals("The fake result Topic object should be equal" +
                " to fakeTopic object", TopicWithGivenID, fakeTopic.get());
        assertEquals("Check fake Topic ID are equal", TopicWithGivenID.getId(), fakeID);
        assertNotSame("Check fake Description is not empty string",
                TopicWithGivenID.getDescription(), "");
        assertNotNull("Response body is not null", TopicWithGivenID);
        assertSame("Checking whether fakeName are same",
                TopicWithGivenID.getName(), fakeName);
        assertNotSame("Checking whether fakeName length not 1",
                TopicWithGivenID.getName().length(),2);
    }

    /*
    ===============> Prefer this for UnitTest Cases <==============

    Method Name : getTopic
    Description : This testcase tests the scenario where Topic
    object is not present with specific ID
    */
    @Test
    public void getTopicTest2() {
        // Test data
        String fakeID = "fakeID";
        String fakeName = "fakeName";
        String fakeDescription = "fakeDescription";
        Topic fakeTopicObject = mock(Topic.class, RETURNS_MOCKS);
        Optional<Topic> fakeTopic = Optional.of(fakeTopicObject);

        // Mocks
        when(topicRepository.findById(fakeID)).thenReturn(fakeTopic);

        /**
         * Also, please explore:
         * 1.CALLS_REAL_METHODS
         * An answer that calls the real methods (used for partial mocks).
         * 2. RETURNS_DEEP_STUBS
         * An answer that returns deep stubs (not mocks).
         * 3. RETURNS_DEFAULTS
         * The default configured answer of every mock.
         * 4. RETURNS_MOCKS
         * An answer that returns mocks (not stubs).
         * 5. RETURNS_SELF
         * An answer that tries to return itself.
         * 6. RETURNS_SMART_NULLS
         * An answer that returns smart-nulls.
         */

        // Mocks
        when(fakeTopic.get().getDescription()).thenReturn(fakeDescription);
        when(fakeTopic.get().getName()).thenReturn(fakeName);
        when(fakeTopic.get().getId()).thenReturn(fakeID);
        when(topicRepository.findById(fakeID)).thenReturn(fakeTopic);

        // The logic we're testing in this testcase
        Topic TopicWithGivenID = springbootService.getTopic(fakeID);

        // Verify the times mocked component is called
        verify(topicRepository, times(1)).findById(fakeID);

        // Test case Assertions
        assertEquals("The fake result Topic object should be " +
                "equal to fakeTopic object", TopicWithGivenID, fakeTopic.get());
        assertSame("Checking whether topic object is null",
                TopicWithGivenID, fakeTopic.get());
        assertNotSame("Check fake Topic is not equal to required object", TopicWithGivenID, TestUtils.createTopicObject(
                fakeID, fakeName, fakeDescription));
    }

    /* 
    Method Name : getTopic
    Description : This testcase tests the scenario where Topic
    object is not present with specific ID
    */
    @Test
    public void getTopicTest3() {
        // Test data
        String fakeID = "fakeID";
        String fakeName = "fakeName";
        String fakeDescription = "fakeDescription";
        Topic fakeTopicObject = mock(Topic.class, RETURNS_MOCKS);
        Optional<Topic> fakeTopic = Optional.of(fakeTopicObject);

        // Mocks
        when(topicRepository.findById(fakeID)).thenReturn(fakeTopic);

        // The logic we're testing in this testcase
        Topic TopicWithGivenID = springbootService.getTopic(fakeID);

        // Verify the times mocked component is called
        verify(topicRepository, times(1)).findById(fakeID);

        // Test case Assertions
        assertEquals("The fake result Topic object should be " +
                "equal to fakeTopic object", TopicWithGivenID, fakeTopic.get());
        assertNotNull("Checking if required object is null", TopicWithGivenID);
        assertSame("Checking whether topic object is null",
                TopicWithGivenID, fakeTopic.get());
        assertNotSame("Check fake Topic is not equal to required object", TopicWithGivenID, TestUtils.createTopicObject(
                fakeID, fakeName, fakeDescription));
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
        when(topicRepository.save(fakeTopic)).thenReturn(fakeTopic);

        // The logic we're testing in this testcase
        Topic addedFakeTopic = springbootService.addTopic(fakeTopic);

        // Verify the times mocked component is called
        verify(topicRepository, times(1)).save(fakeTopic);

        // Test case Assertions
        assertThat("The fake topic object has description " +
                        "as 'fakeDescription'", addedFakeTopic.getDescription(),
                is(fakeDescription));
        assertEquals("The fake result Topic object should be " +
                "equal to fakeTopic object", addedFakeTopic, fakeTopic);
        assertSame("Check fake Topic ID are equal", addedFakeTopic.getId(), fakeID);
        assertNotSame("Check fake Description is not empty string", "", addedFakeTopic.getDescription());
        assertNotNull("resultant topic is not null", addedFakeTopic);
        assertSame("Checking whether fakeName are same",
                addedFakeTopic.getName(), fakeName);
        assertNotSame("Checking whether fakeName length not 1",
                addedFakeTopic.getName().length(), 2);
    }

    //-----------------------------------------------------------------------------------------------------------------------------------------------------------

    /* 
    Method Name : updatetopicTest
    Description : This testcase tests the scenario where Topic
    object is updated in DB by passing new Topic object and Topic
    ID which needs to be updated.
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
        doNothing().when(topicRepository).deleteById(fakeID);
        when(topicRepository.save(fakeTopic)).thenReturn(fakeTopic);

        // The logic we're testing in this testcase
        Topic updatedFakeTopic = springbootService
                .updatetopic(fakeTopic, fakeID);

        // Verify the times mocked component is called
        verify(topicRepository, times(1)).deleteById(fakeID);
        verify(topicRepository, times(1)).save(fakeTopic);

        // Test case Assertions
        assertThat("The fake topic object has description " +
                        "as 'fakeDescription'", updatedFakeTopic.getDescription(),
                is(fakeDescription));
        assertEquals("The fake result Topic object should be" +
                " equal to fakeTopic object", updatedFakeTopic, fakeTopic);
        assertSame("Check fake Topic ID are equal", updatedFakeTopic.getId(), fakeID);
        assertNotSame("Check fake Description is not empty string", "", updatedFakeTopic.getDescription());
        assertNotNull("resultant topic is not null", updatedFakeTopic);
        assertSame("Checking whether fakeName are same",
                updatedFakeTopic.getName(), fakeName);
        assertNotSame("Checking whether fakeName length not 1",
                updatedFakeTopic.getName().length(),
                2);
    }

    //-----------------------------------------------------------------------------------------------------------------------------------------------------------

    /*
      Method Name : deletetopicTest
      Description : This testcase tests the scenario where Topic
      object is deleted in DB by passing Topic ID which needs to be deleted.
      */
    @Test
    public void deleteTopicTest() {
        // Test data
        String fakeID = "fakeID";

        // Mocks
        // NOTE : way to mock method which doesn't return anything
        doNothing().when(topicRepository).deleteById(fakeID);

        // The logic we're testing in this testcase
        springbootService.deletetopic(fakeID);

        // Verify the times mocked component is called
        verify(topicRepository, times(1)).deleteById(fakeID);

        // Test case Assertions
    }


    // ----------------------------------------------------------------------------------------------------------------------------------------------------------
    /* NOTE : 
    Here, we are writing test case for method 'getByIdTest'. 
    So, we are writing two testcases which cover both positive
    and negative scenario of this method.
     */
    /* 
    Method Name : getById
    Description : This testcase tests the scenario where Topic
    object is present with specific ID
    */
    @Ignore /* =======> Way to explicitly ignore a test case */
    @Test
    public void getByIdTest() {
        // Test data
        String fakeID = "fakeID";
        String fakeName = "fakeName";
        String fakeDescription = "fakeDescription";
        Topic fakeTopic = TestUtils.createTopicObject(
                fakeID, fakeName, fakeDescription);

        // Mocks
        when(topicRepository.getById(fakeID)).thenReturn(fakeTopic);

        // The logic we're testing in this testcase
        Topic TopicWithGivenID = springbootService.getById(fakeID);

        // Verify the times mocked component is called
        verify(topicRepository, times(1)).getById(fakeID);

        // Test case Assertions
        assertEquals("The fake result Topic object should " +
                "be equal to fakeTopic object", TopicWithGivenID, fakeTopic);
        assertSame("Check fake Topic ID are equal", TopicWithGivenID.getId(), fakeID);
        assertNotSame("Check fake Description is not empty string", "", TopicWithGivenID.getDescription());
        assertNotNull("Response body is not null", TopicWithGivenID);
        assertSame("Checking whether fakeName are same",
                TopicWithGivenID.getName(), fakeName);
        assertNotSame("Checking whether fakeName length not 1",
                TopicWithGivenID.getName().length(),
                2);
    }

    /* 
    Method Name : getById
    Description : This testcase tests the scenario where Topic
    object is not present with specific ID
    */
    @Test
    public void getByIdTest2() {
        // Test data
        String fakeID = "fakeID";
        String fakeName = "fakeName";
        String fakeDescription = "fakeDescription";

        // Mocks
        when(topicRepository.getById(fakeID)).thenReturn(null);

        // The logic we're testing in this testcase
        Topic TopicWithGivenID = springbootService.getById(fakeID);

        // Verify the times mocked component is called
        verify(topicRepository, times(1)).getById(fakeID);

        // Test case Assertions
        assertNull("The fake result Topic object should " +
                "be equal to fakeTopic object", TopicWithGivenID);
        assertNull("Checking if required object is null", TopicWithGivenID);
        assertSame("Checking whether topic object is null",
                null, null);
        assertNotSame("Check fake Topic is not equal to required object", TopicWithGivenID, TestUtils.createTopicObject(
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
    Description : This testcase tests the scenario where Topic
    object is present with specific ID and specific Name
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
        when(topicRepository.getByIdAndName(fakeID, fakeName))
                .thenReturn(fakeTopic);

        // The logic we're testing in this testcase
        Topic TopicWithGivenIDAndName = springbootService
                .getByIdAndName(fakeID, fakeName);

        // Verify the times mocked component is called
        verify(topicRepository, times(1)).getByIdAndName(fakeID, fakeName);

        // Test case Assertions
        assertEquals("The fake result Topic object should be equal" +
                " to fakeTopic object", TopicWithGivenIDAndName, fakeTopic);
        assertSame("Check fake Topic ID are equal", TopicWithGivenIDAndName.getId(), fakeID);
        assertNotSame("Check fake Description is not empty string", "", TopicWithGivenIDAndName.getDescription());
        assertNotNull("Response body is not null",
                TopicWithGivenIDAndName);
        assertSame("Checking whether fakeName are same",
                TopicWithGivenIDAndName.getName(), fakeName);
        assertNotSame("Checking whether fakeName length not 1",
                TopicWithGivenIDAndName.getName().length(),
                2);
    }

    /* 
    Method Name : getByIdAndName
    Description : This testcase tests the scenario where Topic
    object is not present with specific ID and specific Name
    */
    @Test
    public void getByIdAndNameTest2() {
        // Test data
        String fakeID = "fakeID";
        String fakeName = "fakeName";
        String fakeDescription = "fakeDescription";

        // Mocks
        when(topicRepository.getByIdAndName(
                fakeID, fakeName)).thenReturn(null);

        // The logic we're testing in this testcase
        Topic TopicWithGivenIDAndName = springbootService
                .getByIdAndName(fakeID, fakeName);

        // Verify the times mocked component is called
        verify(topicRepository, times(1)).getByIdAndName(fakeID, fakeName);

        // Test case Assertions
        assertNull("The fake result Topic object should " +
                "be equal to fakeTopic object", TopicWithGivenIDAndName);
        assertNull("Checking if required object is null", TopicWithGivenIDAndName);
        assertSame("Checking whether topic object is null",
                TopicWithGivenIDAndName, null);
        assertNotSame("Check fake Topic is not equal to required object", TopicWithGivenIDAndName, TestUtils.createTopicObject(
                fakeID, fakeName, fakeDescription));
    }

    //-----------------------------------------------------------------------------------------------------------------------------------------------------------

    //This is used to destroy context of single test case level
    @After
    public void tearDownAfterEachTestCase() {

        /* 
        When using mocks for dependency injection, we need to verify after test case has
        ended no more interactions are made with mocked dependencies.
        */
        verifyNoMoreInteractions(topicRepository);
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