package com.sonusourav.MILErecorder;

public class ListItem {


        private String name,phone, time;

        public ListItem() {
        }

        public ListItem(String name, String phone, String time) {
            this.name = name;
            this.phone = phone;
            this.time = time;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone =phone ;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }
    }

