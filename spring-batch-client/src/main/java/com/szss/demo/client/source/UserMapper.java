package com.szss.demo.client.source;


import com.szss.demo.client.User;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * Created by zcg on 2017/6/7.
 */
@Mapper
public interface UserMapper {

  @Select("select id,username,password from user")
  List<User> findAll();

  @Select("select id,username,password from user where id=#{id}")
  List<User> findUserById(Long id);

  @Select("select id,username,password from user LIMIT #{_skiprows}, #{_pagesize}")
  List<User> findPage();
}
