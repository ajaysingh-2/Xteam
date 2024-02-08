package com.xsworld.jwt_auth.models;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class JwtRequest {
    public String Email;
    public String Password;
}
