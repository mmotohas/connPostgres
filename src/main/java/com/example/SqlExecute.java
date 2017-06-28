package com.example;

import java.util.List;
//import java.util.Map;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
//import org.springframework.jdbc.core.JdbcTemplate;

import jp.sf.amateras.mirage.SqlManager;
import jp.sf.amateras.mirage.session.Session;
import jp.sf.amateras.mirage.session.SessionFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
		log.info("aaa");
		SqlManager sqlManager = session.getSqlManager();
		log.info("bbb");
		session.begin();
		log.info("ccc");

		try {
			log.info("ddd");
			List<Person> list = sqlManager.getResultList(Person.class, "META-INF/selectPerson.sql");
			log.info("eee");
			//String str = list.get(2).get("name").toString();
			String str = "Hello, world!";
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
