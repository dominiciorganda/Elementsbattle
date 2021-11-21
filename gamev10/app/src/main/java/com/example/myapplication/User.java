package com.example.myapplication;

public class User {
    private String username;
    private String password;
    private int nrWins;
    private int nrLosses;
    private int timePlayed;

    public User(String username, String password, int nrWins, int nrLosses, int timePlayed) {
        this.username = username;
        this.password = password;
        this.nrWins = nrWins;
        this.nrLosses = nrLosses;
        this.timePlayed = timePlayed;
    }

    public User() {
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

    public int getNrWins() {
        return nrWins;
    }

    public void setNrWins(int nrWins) {
        this.nrWins = nrWins;
    }

    public int getNrLosses() {
        return nrLosses;
    }

    public void setNrLosses(int nrLosses) {
        this.nrLosses = nrLosses;
    }

    public int getTimePlayed() {
        return timePlayed;
    }

    public void setTimePlayed(int timePlayed) {
        this.timePlayed = timePlayed;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", nrWins=" + nrWins +
                ", nrLosses=" + nrLosses +
                ", timePlayed=" + timePlayed +
                '}';
    }
}