package com.example.deafdumbcomunication;

public class users {

    public String fullName, userName, email, password, gender;

    public users(){
        email = email;
    }

    public users(String fullName, String userName, String email, String password, String gender) {
        this.fullName = fullName;
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.gender = gender;
    }

    public String getFullName() {
        return fullName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }
}

