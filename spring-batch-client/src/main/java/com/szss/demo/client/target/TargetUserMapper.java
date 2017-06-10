package com.szss.demo.client.target;

import com.szss.demo.client.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TargetUserMapper {

  @Insert("INSERT INTO my_user (id,username, password) VALUES (#{id}, #{username}, #{password})")
  void saveUser(User user);
}
