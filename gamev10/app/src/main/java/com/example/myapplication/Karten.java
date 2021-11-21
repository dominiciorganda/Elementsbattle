package com.example.myapplication;

public enum Karten {
    //Die Enumeration der Karten
    Cerberus(10, "Fire", "cerberus10.jpg"),
    Charmander(6, "Fire", "charmander6.jpg"),
    Deathwing(12, "Fire", "deathwing12.jpg"),
    Dolphin(10, "Water", "delfinu10.jpg"),
    Fox(5, "Grass", "fox5.jpg"),
    Gorilla(12, "Grass", "gorilla12.jpg"),
    Penguin(6, "Water", "penguin6.jpg"),
    Shark(12, "Water", "rechin12.jpg"),
    Tiger(10, "Grass", "tiger10.jpg"),
    Cinder(8, "Fire", "cinder8.jpg"),
    Crocodile(9, "Grass", "crocodile9.jpg"),
    Fish(7, "Water", "fish7.jpg"),
    Hydra(11, "Water", "hydra11.jpg"),
    Kraken(9, "Water", "kraken9.jpg"),
    Mamut(9, "Grass", "mamut9.jpg"),
    Phoenix(11, "Fire", "phoenix11.jpg"),
    Snake(11, "Grass", "snake11.jpg"),
    Apocalypse(7, "Fire", "apocalypse7.jpg");

    //Variablen fur die Eigenschaften der Karte
    private int power;
    private String type;
    private String path;

    Karten(int power, String type, String path) {
        this.power = power;
        this.type = type;
        this.path = path;
    }

    public int getPower() {
        return power;
    }

    public String getType() {
        return type;
    }

    public String getPath() {
        return path;
    }
}
