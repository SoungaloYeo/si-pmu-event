package fr.pmu.controller.utils;

import org.springframework.http.HttpHeaders;

public final class HeaderUtil {

    private HeaderUtil (){}


    public static HttpHeaders createHeaders(String message) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("pmu-si-app", message);
        return headers;
    }
}
