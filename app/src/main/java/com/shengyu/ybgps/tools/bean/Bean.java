package com.shengyu.ybgps.tools.bean;

/**
 * Created by Trust on 2016/8/16.
 */
public class Bean {

    private String version ,url ,description;




    @Override
    public String toString() {
        return "Bean{" +
                "version='" + version + '\'' +
                ", url='" + url + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
