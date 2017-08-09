package com.example.randytia.tonjootest;

/**
 * Created by Randytia on 08/08/2017.
 */

public class Tonjoo {

    private String mFirstName;
    private String mLastName;
    private String mGender;
    private String mEmail;
    private String mAvatar;

    public Tonjoo(String firstName, String lastName, String gender, String email, String avatar) {
        mFirstName = firstName;
        mLastName = lastName;
        mGender = gender;
        mEmail = email;
        mAvatar = avatar;
    }

    public String getmFirstName() {
        return mFirstName;
    }

    public String getmLastName() {
        return mLastName;
    }

    public String getmGender() {
        return mGender;
    }

    public String getmEmail() {
        return mEmail;
    }

    public String getmAvatar() {
        return mAvatar;
    }
}
