package com.connect.oneboardserver.config.socket;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class InitObject {

    private String userType;
    private String room;

    public InitObject(String userType, String room) {
        this.userType = userType;
        this.room = room;
    }
}