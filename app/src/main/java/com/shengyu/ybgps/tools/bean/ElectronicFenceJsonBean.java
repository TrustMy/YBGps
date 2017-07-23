package com.shengyu.ybgps.tools.bean;

import java.util.List;

/**
 * Created by Trust on 2016/10/25.
 */
public class ElectronicFenceJsonBean {

    /**
     * status : true
     * circleFences : [{"uid":1,"circleName":"共富南方","lat":31.266941,"lng":121.395436,"radius":157.314957,"status":1},{"uid":2,"circleName":"共富南方","lat":31.346272,"lng":121.422304,"radius":111.837352,"status":2}]
     */

    private boolean status;
    private List<CircleFencesBean> circleFences;
    private String Erro;

    public String getErro() {
        return Erro;
    }

    public void setErro(String erro) {
        Erro = erro;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public void setCircleFences(List<CircleFencesBean> circleFences) {
        this.circleFences = circleFences;
    }

    public boolean getStatus() {
        return status;
    }

    public List<CircleFencesBean> getCircleFences() {
        return circleFences;
    }

    public static class CircleFencesBean {
        /**
         * uid : 1
         * circleName : 共富南方
         * lat : 31.266941
         * lng : 121.395436
         * radius : 157.314957
         * status : 1
         */

        private int uid;
        private String circleName;
        private double lat;
        private double lng;
        private double radius;
        private int status;

        public void setUid(int uid) {
            this.uid = uid;
        }

        public void setCircleName(String circleName) {
            this.circleName = circleName;
        }

        public void setLat(double lat) {
            this.lat = lat;
        }

        public void setLng(double lng) {
            this.lng = lng;
        }

        public void setRadius(double radius) {
            this.radius = radius;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getUid() {
            return uid;
        }

        public String getCircleName() {
            return circleName;
        }

        public double getLat() {
            return lat;
        }

        public double getLng() {
            return lng;
        }

        public double getRadius() {
            return radius;
        }

        public int getStatus() {
            return status;
        }
    }
}
