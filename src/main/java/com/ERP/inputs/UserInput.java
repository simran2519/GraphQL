package com.ERP.inputs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInput
{
//    private long userId;
    private String name;
    private String email;
    private String password;
    private String phone;
}