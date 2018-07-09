package com.mytest.demo.controller;

import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class TestController {

    private static final Map<Integer, String> publicResource = new HashMap<>(), privateResource = new HashMap<>();

    static {
        publicResource.put(1, "Public Resource 1");
        publicResource.put(2, "Public Resource 2");
        publicResource.put(3, "Public Resource 3");
        privateResource.put(1, "Private Resource 1");
        privateResource.put(2, "Private Resource 2");
        privateResource.put(3, "Private Resource 3");
    }

    @GetMapping("/public/{id}")
    public String getPublic(@PathVariable String id) {

        if (StringUtils.isEmpty(id) || !publicResource.containsKey(Integer.valueOf(id))) {
            return "No public resource found! (id=" + id + ")";
        }

        return "The public resource is: " + publicResource.get(Integer.valueOf(id)) + ". (id=" + id + ")";
    }

    @GetMapping("/private/{id}")
    public String getPrivate(@PathVariable String id) {

        if (StringUtils.isEmpty(id) || !privateResource.containsKey(Integer.valueOf(id))) {
            return "No private resource found! (id=" + id + ")";
        }

        return "The private resource is: " + privateResource.get(Integer.valueOf(id)) + ". (id=" + id + ")";
    }
}