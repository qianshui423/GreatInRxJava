package com.hao.greatinrxjava.flow.model.bean;

/**
 * Created by liuxuehao on 16/12/26.
 * <p>
 * "now": {  //实况天气
 * "cond": {  //天气状况
 * "code": "104",  //天气状况代码
 * "txt": "阴"  //天气状况描述
 * },
 * "fl": "11",  //体感温度
 * "hum": "31",  //相对湿度（%）
 * "pcpn": "0",  //降水量（mm）
 * "pres": "1025",  //气压
 * "tmp": "13",  //温度
 * "vis": "10",  //能见度（km）
 * "wind": {  //风力风向
 * "deg": "40",  //风向（360度）
 * "dir": "东北风",  //风向
 * "sc": "4-5",  //风力
 * "spd": "24"  //风速（kmph）
 * }
 * }
 */

public class Now {
    private Cond cond;
    private String fl;
    private String hum;
    private String pcpn;
    private String pres;
    private String tmp;
    private String vis;
    private Wind wind;
    private String status;

    public Cond getCond() {
        return cond;
    }

    public void setCond(Cond cond) {
        this.cond = cond;
    }

    public String getFl() {
        return fl;
    }

    public void setFl(String fl) {
        this.fl = fl;
    }

    public String getHum() {
        return hum;
    }

    public void setHum(String hum) {
        this.hum = hum;
    }

    public String getPcpn() {
        return pcpn;
    }

    public void setPcpn(String pcpn) {
        this.pcpn = pcpn;
    }

    public String getPres() {
        return pres;
    }

    public void setPres(String pres) {
        this.pres = pres;
    }

    public String getTmp() {
        return tmp;
    }

    public void setTmp(String tmp) {
        this.tmp = tmp;
    }

    public String getVis() {
        return vis;
    }

    public void setVis(String vis) {
        this.vis = vis;
    }

    public Wind getWind() {
        return wind;
    }

    public void setWind(Wind wind) {
        this.wind = wind;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public static class Cond {
        private String code;
        private String txt;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getTxt() {
            return txt;
        }

        public void setTxt(String txt) {
            this.txt = txt;
        }
    }

    public static class Wind {
        private String deg;
        private String dir;
        private String sc;
        private String spd;

        public String getDeg() {
            return deg;
        }

        public void setDeg(String deg) {
            this.deg = deg;
        }

        public String getDir() {
            return dir;
        }

        public void setDir(String dir) {
            this.dir = dir;
        }

        public String getSc() {
            return sc;
        }

        public void setSc(String sc) {
            this.sc = sc;
        }

        public String getSpd() {
            return spd;
        }

        public void setSpd(String spd) {
            this.spd = spd;
        }
    }
}
