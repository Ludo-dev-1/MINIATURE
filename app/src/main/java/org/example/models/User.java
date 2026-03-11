package org.example.models;

public class User {
    private long id;
    private String username;
    private String password;
    private String email;

    public User(String username, String password, String email, long id) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public long getId() {
        return id;
    }  

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public String toString() {
       
        return "User{" +
                "username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", id=" + id +
                '}';
    }
}
