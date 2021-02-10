package com.company.Entities;

import java.io.Serializable;

public abstract class Person implements Serializable {
    private String name;
    private String username;
    private String password;
    private boolean isLoggedIn = false;
    private static final long serialVersionUID = 0L;

    public Person() {
    }

    public Person(String name, String username, String password) {
        this.name = name;
        this.username = username;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public boolean isLoggedIn() {
        return isLoggedIn;
    }

    public void setLoggedIn(boolean loggedIn) {
        isLoggedIn = loggedIn;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", isLoggedIn=" + isLoggedIn +
                '}';
    }
}
