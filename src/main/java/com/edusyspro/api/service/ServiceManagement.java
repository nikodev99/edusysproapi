package com.edusyspro.api.service;

public class ServiceManagement {

    private final String myVar;

    public ServiceManagement(String myVar) {
        this.myVar = myVar;
    }

    public String sayHello() {
        return "Running from Service Management ==> myVar=" + myVar;
    }

}
