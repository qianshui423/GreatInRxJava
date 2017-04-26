package com.hao.greatinrxjava.flow.model.bean;

/**
 * Created by liuxuehao on 16/12/26.
 * <p>
 * "basic": { //基本信息
 * "city": "北京",  //城市名称
 * "cnty": "中国",   //国家
 * "id": "CN101010100",  //城市ID
 * "lat": "39.904000", //城市维度
 * "lon": "116.391000", //城市经度
 * "prov": "北京"  //城市所属省份（仅限国内城市）
 * "update": {  //更新时间
 * "loc": "2016-08-31 11:52",  //当地时间
 * "utc": "2016-08-31 03:52" //UTC时间
 * }
 * }
 */

public class Basic {
    private String city;
    private String cnty;
    private String id;
    private String lat;
    private String lon;
    private String prov;
    private Update update;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCnty() {
        return cnty;
    }

    public void setCnty(String cnty) {
        this.cnty = cnty;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public String getProv() {
        return prov;
    }

    public void setProv(String prov) {
        this.prov = prov;
    }

    public Update getUpdate() {
        return update;
    }

    public void setUpdate(Update update) {
        this.update = update;
    }

    public static class Update {
        private String loc;
        private String utc;

        public String getLoc() {
            return loc;
        }

        public void setLoc(String loc) {
            this.loc = loc;
        }

        public String getUtc() {
            return utc;
        }

        public void setUtc(String utc) {
            this.utc = utc;
        }
    }
}
