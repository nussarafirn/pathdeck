package edu.tacoma.uw.finalproject.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class User implements Serializable{
    private String firstName;
    private String lastName;
    private String userName;
    private String password;
    private String email;
    private String phone;

    public static final String FIRST_NAME = "first";
    public static final String LAST_NAME = "last";
    public static final String USER_NAME = "username";
    public static final String PASS_WORD = "password";
    public static final String EMAIL = "email";
    public static final String PHONE = "phone";

    public User(String firstName, String lastName, String userName,
                String password, String email, String phone) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.password = password;
        this.email = email;
        this.phone = phone;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public static List<User> parseUserJson(String userJson) throws JSONException {
        List<User> userInfo = new ArrayList<>();
        if (userJson != null) {

            JSONArray arr = new JSONArray(userJson);

            for (int i = 0; i < arr.length(); i++) {
                JSONObject obj = arr.getJSONObject(i);
                User user = new User(obj.getString(User.FIRST_NAME), obj.getString(User.LAST_NAME),
                        obj.getString(User.USER_NAME), obj.getString(User.PASS_WORD), obj.getString(User.EMAIL),
                        obj.getString(User.PHONE));
                userInfo.add(user);
            }

        }
        return userInfo;
    }
}
