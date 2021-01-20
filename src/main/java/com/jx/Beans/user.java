package com.jx.Beans;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class user {
    private int id;
    private String username;
    private String password;
    private String salt;
    private List<Role> roles;
}
