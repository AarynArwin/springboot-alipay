package com.geekerit.springbootalipay.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Aaryn
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    private Long userId;

    private String username;

    private Integer age;
}
