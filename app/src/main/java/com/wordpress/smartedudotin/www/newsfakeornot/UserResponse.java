package com.wordpress.smartedudotin.www.newsfakeornot;

import com.parse.ParseUser;

public class UserResponse {

    private String userEmail;
   // private ParseUser parseUser;

    public UserResponse(String userEmail) {
        this.userEmail = userEmail;
       // this.parseUser = parseUser;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

//    public ParseUser getParseUser() {
//        return parseUser;
//    }
//
//    public void setParseUser(ParseUser parseUser) {
//        this.parseUser = parseUser;
//    }


    @Override
    public String toString() {
        return "{" +
                "userEmail:'" + userEmail +
                '}';
    }
}
