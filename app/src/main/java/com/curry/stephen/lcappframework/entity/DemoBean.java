package com.curry.stephen.lcappframework.entity;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/1/25.
 */

public class DemoBean implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * info : {"name":"","age":""}
     */

    private InfoBean info;

    public InfoBean getInfo() {
        return info;
    }

    public void setInfo(InfoBean info) {
        this.info = info;
    }

    public static class InfoBean {
        /**
         * name :
         * age :
         */

        private String name;
        private int age;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }
    }
}
