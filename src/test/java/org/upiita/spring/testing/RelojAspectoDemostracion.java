package org.upiita.spring.testing;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.upiita.spring.dao.PostDAO;

public class RelojAspectoDemostracion {

	private static ApplicationContext contexto;

	private static PostDAO postDAO;
	
	public static void main(String args[]) {

		contexto = new ClassPathXmlApplicationContext("dao-context-testing.xml");
		postDAO = (PostDAO) contexto.getBean("postDAO");
		
		postDAO.buscaPorId(1);
		
	}

}
