package org.expressme.modules.web;

import java.io.Serializable;

/**
 * Hold viewer's identity which stores in the HTTP session / or cookie as well as 
 * thread local for each request handling.
 * 
 * @author Michael Liao (askxuefeng@gmail.com)
 */
public final class Identity implements Serializable {

    private final boolean admin;
    private final String id;
    private final String name;
    private final String email;
    private final String password;

    public Identity(String id, String name, String email, String password) {
        this(false, id, name, email, password);
    }

    public Identity(boolean admin, String id, String name, String email, String password) {
        this.admin = admin;
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public boolean isAdmin() {
        return admin;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

}
