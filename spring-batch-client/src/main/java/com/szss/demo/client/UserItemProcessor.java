package com.szss.demo.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

public class UserItemProcessor implements ItemProcessor<User, User> {

  private static final Logger log = LoggerFactory.getLogger(UserItemProcessor.class);

  @Override
  public User process(final User user) throws Exception {
    final Long id=user.getId();
    final String password = user.getPassword().toUpperCase();
    final String username = user.getUsername().toUpperCase();

    final User transformedUser = new User(id,username, password);

    log.info("Converting (" + user + ") into (" + transformedUser + ")");

    return transformedUser;
  }

}
