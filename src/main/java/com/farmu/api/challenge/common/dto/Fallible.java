package com.farmu.api.challenge.common.dto;

public interface Fallible {

    boolean success();

    String errorMessage();

}