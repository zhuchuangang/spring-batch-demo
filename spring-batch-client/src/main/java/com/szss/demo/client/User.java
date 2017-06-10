package com.szss.demo.client;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Created by zcg on 2017/6/7.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class User {
  private Long id;
  private String username;
  private String password;
}
