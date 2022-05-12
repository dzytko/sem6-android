package com.example.project2.data;

import com.example.project2.entities.Phone;

import java.util.Arrays;
import java.util.List;

public class PhoneSeeder {
    public static List<Phone> getPhones() {
        return Arrays.asList(
                new Phone("Manufacturer 1", "Model 1", "Version 1", "url 1"),
                new Phone("Manufacturer 2", "Model 2", "Version 2", "url 2"),
                new Phone("Manufacturer 3", "Model 3", "Version 3", "url 3"),
                new Phone("Manufacturer 4", "Model 4", "Version 4", "url 4"),
                new Phone("Manufacturer 5", "Model 5", "Version 5", "url 5")
        );
    }
}
