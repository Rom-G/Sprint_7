package ru.scooter.models;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OrdersListModel {

    private List<Order> orders;
    private PageInfo pageInfo;
    private List<Station> availableStations;
}
