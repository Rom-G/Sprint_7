package ru.scooter.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PageInfo {

    private int page;
    private int total;
    private int limit;
}
