package com.ims.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class Users {
    @Getter
    @Setter
    private String email;

    @Getter
    @Setter
    private String password;
}
