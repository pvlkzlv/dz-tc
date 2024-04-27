package com.example.dztc.api.requests;

public interface CrudInterface {
    public Object create(Object obj);

    public Object get(String id);

    public Object update(Object obj);

    public Object delete(String id);
}