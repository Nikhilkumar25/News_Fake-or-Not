package com.wordpress.smartedudotin.www.newsfakeornot;

import android.graphics.Bitmap;
import android.media.Image;

import com.parse.ParseFile;
import com.parse.ParseUser;

import java.io.File;
import java.io.PrintStream;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class NewsFeed {

    private String ObjId;
    private ParseFile image;
    private String newsTitle;
    private String newsBody;
    private int isTrueCount;
    private int isFalseCount;
    private boolean isTrueResponded;
    private boolean isFalseResponded;
    private ArrayList<String> userTrueResponse;
    private ArrayList<String> userFalseResponse;

    private ParseUser parseUser;

    public NewsFeed(String ObjId,ParseFile image,String newsTitle, String newsBody, ArrayList<String> userTrueResponse,ArrayList<String> userFalseResponse){


        this.ObjId = ObjId;
        this.image = image;
        this.newsTitle = newsTitle;
        this.newsBody = newsBody;
        this.isTrueCount = userTrueResponse.size ();
        this.isFalseCount = userFalseResponse.size ();
        this.userTrueResponse = userTrueResponse;
        this.userFalseResponse = userFalseResponse;

        parseUser = ParseUser.getCurrentUser ();
        this.isTrueResponded = ifContains(userTrueResponse,parseUser);
        this.isFalseResponded = ifContains (userFalseResponse, parseUser);

    }

    private boolean ifContains(ArrayList<String> userResponses, ParseUser currentUser) {

        for (String uR : userResponses){
            if (uR.equals (currentUser.getEmail ())){
                return true;
            }

        }

        return false;

    }


    public ArrayList<String> getUserTrueResponse() {
        return userTrueResponse;
    }

    public void setUserTrueResponse(ArrayList<String> userTrueResponse) {
        this.userTrueResponse = userTrueResponse;
    }

    public ArrayList<String> getUserFalseResponse() {
        return userFalseResponse;
    }

    public void setUserFalseResponse(ArrayList<String> userFalseResponse) {
        this.userFalseResponse = userFalseResponse;
    }

    public boolean isTrueResponded() {
        return isTrueResponded;
    }

    public void setTrueResponded(boolean trueResponded) {
        isTrueResponded = trueResponded;
    }

    public boolean isFalseResponded() {
        return isFalseResponded;
    }

    public void setFalseResponded(boolean falseResponded) {
        isFalseResponded = falseResponded;
    }



    public String getNewsTitle() {
        return newsTitle;
    }

    public void setNewsTitle(String newsTitle) {
        this.newsTitle = newsTitle;
    }

    public String getObjId() {
        return ObjId;
    }

    public void setObjId(String objId) {
        ObjId = objId;
    }

    public ParseFile getImage() {
        return image;
    }

    public void setImage(ParseFile image) {
        this.image = image;
    }

    public String getNewsBody() {
        return newsBody;
    }

    public void setNewsBody(String newsBody) {
        this.newsBody = newsBody;
    }

    public int getIsTrueCount() {
        return isTrueCount;
    }

    public void setIsTrueCount(int isTrueCount) {
        this.isTrueCount = isTrueCount;
    }

    public int getIsFalseCount() {
        return isFalseCount;
    }

    public void setIsFalseCount(int isFalseCount) {
        this.isFalseCount = isFalseCount;
    }


}
