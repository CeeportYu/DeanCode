package yu.test;

import java.util.Set;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import model.Person;

public class Test03 {
	
	
		@Test
		public void test01() {
			//1.獲得Spring工廠(幫我們創建物件)，此例為非Web的工廠，ClassPathXmlApplicationContext()，需要參數(工廠配置文件位置)-->applicationContext.xml。
		ApplicationContext springFactory = new ClassPathXmlApplicationContext("/applicationContext.xml");
		
		//2.透過getBean方法，讓工廠為我們創建物件。
		 Person person = springFactory.getBean("person",Person.class);
		 
		 
		  String[] mails  = person.getMail();
		  Set<String> chName = person.getChName();
		  
		  
		  for(String ch: chName) {
			  System.out.println(ch);
		  }
		 
		 
		 
		 
		 
		}
	
	
	
		
		
		
		
}
