package com.xsworld.jwt_auth.models;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString

public class JwtResponse {
    private String jwtToken;
    private String username;
    private String refreshToken;
}
