package com.login.beachstop.garcom.android.models;

import java.io.Serializable;

/**
 * Created by Argus on 24/10/2014.
 */
public abstract class Base implements Serializable {

    public abstract Long getId();

    public abstract void setId(Long id);

    public abstract String getServiceName();

    public abstract void setServiceName(String serviceName);

}
