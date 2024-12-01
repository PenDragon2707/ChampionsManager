package vn.edu.stu.championsmanager.models;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class Role implements Serializable {
    private int id;
    private String name;

    public Role() {
    }

    public Role(String name) {
        this.name = name;
    }

    public Role(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    @NonNull
    @Override
    public String toString() {
        return id + "    " + name;
    }
}
