package com.compulnyx.project.test_excercise.common.email;/*
*
@author ameda
@project Books
*
*/

public enum EmailTemplateName {
    ACTIVATE_ACCOUNT("activate_account");

    EmailTemplateName(String name){
        this.name = name;
    }
    private final String name;
    public String getName(){
        return name;
    }

}
