package com.example.Spring_Boot_JPA.utils;

import java.util.ArrayList;
import java.util.List;

import com.example.Spring_Boot_JPA.model.Topic;

public class TestUtils {

    /* Test util Function: It is used to create 'n' fake Topic object.
    Input : number of fake Topic required
    Output : return List of fake Topic objects
    Logic : creates fake Topic object using Math.random method
    */
    public static List<Topic> createFakeTopicList(int num) {
        List<Topic> fakeTopicList = new ArrayList<>();

        for(int i=0;i<num;i++) {
            char character = (char)((int)(Math.random()*26)+97);
            Topic t = new Topic(character+"", character+"", character+"");
            fakeTopicList.add(t);
        }

        return fakeTopicList;
    }

    /* Test util Function : It is used to create fake Topic object.
    Input : All attributes of fake Topic object
    Output : returns fake Topic object with passed fake attributes.
    Logic : Just creates and returns new fake Topic object.
    */
    public static Topic createTopicObject(
            String fakeID,
            String fakeName,
            String fakeDescription) {
        return new Topic(fakeID, fakeName, fakeDescription);
    }
}