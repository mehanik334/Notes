package com.example.sasha.notes;


public class Person {

    private String name;
    private String numberTelefon;

    public Person() {
    }

    public Person(String name, String numberTelefon) {
        this.name = name;
        this.numberTelefon = numberTelefon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumberTelefon() {
        return numberTelefon;
    }

    public void setNumberTelefon(String numberTelefon) {
        this.numberTelefon = numberTelefon;
    }

    public String toString(){
        return getName()+" "+getNumberTelefon();
    }
}
