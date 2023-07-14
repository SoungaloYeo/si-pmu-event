package fr.pmu.config;

import java.time.format.DateTimeFormatter;

public final class Constants  {

    private static final String RACES_NUMBER_PATTERN = "HHmmssSSS";
    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern(RACES_NUMBER_PATTERN);


    private Constants() {
    }
}
