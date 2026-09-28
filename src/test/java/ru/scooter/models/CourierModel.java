package ru.scooter.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CourierModel {

    private String login;
    private String password;
    private String firstName;

    public CourierModel(String login, String password) {
        this.login = login;
        this.password = password;
    }
}
