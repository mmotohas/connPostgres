package com.example;

import java.util.HashMap;
import java.util.List;
//import java.util.Map;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
//import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jp.sf.amateras.mirage.SqlManager;
import jp.sf.amateras.mirage.session.Session;
import jp.sf.amateras.mirage.session.SessionFactory;

@Configuration
@RestController
public class SqlExecute {

//	@Autowired
//	private JdbcTemplate jdbcTemplate;

	private static final Logger log = LoggerFactory.getLogger(ConnPostgresApplication.class);

	@Bean
	@RequestMapping(name="/")
	public String select() {
		log.info("Method started!");
		
		Session session = SessionFactory.getSession();
		SqlManager sqlManager = session.getSqlManager();
		session.begin();

		try {
			Map<String, Integer> params = new HashMap<>();
			params.put("id", 3);
			List<Person> list = sqlManager.getResultList(Person.class, "META-INF/selectPerson.sql", params);
			String str = list.get(0).toString();
//			String str = "Hello, world!";
			log.info("Select Succeeded!");
			return str;
		} catch (Exception e) {
			e.printStackTrace();
			session.rollback();
			return "Select Exception Occured!";
		} finally {
			session.release();
		}
	}
}
