package com.eidiko.springbatchexample1.batch;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.eidiko.springbatchexample1.model.User;
import com.eidiko.springbatchexample1.repository.UserRepository;

@Component
public class DBWriter implements ItemWriter<User> {

	private static final Logger LOG = LoggerFactory
			.getLogger(DBWriter.class);

	private UserRepository userRepository;

	@Autowired
	public DBWriter(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public void write(List<? extends User> users) throws Exception {
		LOG.info("Data Saved for Users: " + users);
		userRepository.saveAll(users);
	}
}
