package com.example.NGGG.common.security;

public enum UserType {
    MEMBER{
        @Override
        public String toString(){
            return "M";
        }
    },
    ADMIN{
        @Override
        public String toString(){
            return "A";
        }
    }
}
