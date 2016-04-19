/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.schernolyas.appmetertest;

import java.util.Date;
import java.util.concurrent.Callable;

/**
 *
 * @author Sergey Chernolyas <sergey.chernolyas@gmail.com>
 */
public class Task<V> {

    private long id;
    private Date executeFrom;

    private Callable<V> code;

    private Throwable throwable;

    public Date getExecuteFrom() {
        return executeFrom;
    }

    public void setExecuteFrom(Date executeFrom) {
        this.executeFrom = executeFrom;
    }

    public Callable<V> getCode() {
        return code;
    }

    public void setCode(Callable<V> code) {
        this.code = code;
    }

    public Throwable getThrowable() {
        return throwable;
    }

    public void setThrowable(Throwable throwable) {
        this.throwable = throwable;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
