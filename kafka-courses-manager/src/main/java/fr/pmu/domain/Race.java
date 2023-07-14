package fr.pmu.domain;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;


public class Race implements Serializable {
    private Long id;
    private LocalDate date;
    private String name;
    private String number;
    private Set<Starter> starters = new HashSet<>();
}
