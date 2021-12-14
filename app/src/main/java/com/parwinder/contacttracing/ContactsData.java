package com.parwinder.contacttracing;

import java.io.Serializable;

/*model class with private properties and public getter-setter*/
public class ContactsData implements Serializable {

    private String name;
    private String number;
    private String email;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
