package com.login.beachstop.android.model;

import org.droidpersistence.annotation.Column;

import java.io.Serializable;

/**
 * Created by Argus on 24/10/2014.
 */
public abstract class Base implements Serializable {

    @Column(name = "ID")
    protected Long id;

    protected String serviceName;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }
}
