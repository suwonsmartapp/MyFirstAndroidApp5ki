package com.example.myapplication.models;

import java.io.Serializable;

/**
 * Created by junsuk on 2017. 2. 7..
 */

public class Memo implements Serializable {
    private long id;
    private String title;
    private String content;
    private String imagePath;

    public Memo(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Memo{");
        sb.append("id=").append(id);
        sb.append(", title='").append(title).append('\'');
        sb.append(", content='").append(content).append('\'');
        sb.append(", imagePath='").append(imagePath).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
