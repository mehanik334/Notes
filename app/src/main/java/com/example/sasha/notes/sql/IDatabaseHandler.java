package com.example.sasha.notes.sql;

import com.example.sasha.notes.Person;

import java.util.List;


public interface IDatabaseHandler {
    public void addPerson(Person person);
    public Person getPersonById( int id);
    public void deleteAll();
    public List<Person> getAllPerson();
    public void deletePerson(Person person);

}
