package com.github.esjdbcweb;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by abo on 10/15/15.
 */
@Entity
@Table
public class Task implements Serializable {

    public static final Integer STATE_WAITING = 0;
    public static final Integer STATE_EXECUTING = 1;
    public static final Integer STATE_FINISHED = 2;
    public static final Integer STATE_FAILED = 3;

    @Id
    @GeneratedValue
    private Long id;

    @Column
    private String name;

    @Column(columnDefinition = "text")
    private String setting;

    @Column
    private Integer state;

    @Column
    private Long createdAt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSetting() {
        return setting;
    }

    public void setSetting(String setting) {
        this.setting = setting;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Long createdAt) {
        this.createdAt = createdAt;
    }
}
