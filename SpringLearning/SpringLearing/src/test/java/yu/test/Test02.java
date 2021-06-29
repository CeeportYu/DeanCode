package yu.test;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import model.Person;

public class Test02 {
	
	//測試Spring提供的其他方法:getBeanDefinitionNames()
	@Test
	public void test01() {
		//1.獲得Spring工廠(幫我們創建物件)，此例為非Web的工廠，ClassPathXmlApplicationContext()，需要參數(工廠配置文件位置)-->applicationContext.xml。
		ApplicationContext springFactory = new ClassPathXmlApplicationContext("/applicationContext.xml");
		
		//2.獲取Spring工廠配置文件中，所有Bean標籤的id值
		String[] beansNames = springFactory.getBeanDefinitionNames();
		for(String beanName : beansNames) {
			System.out.println(beanName);
		}				
	}
	
	
	
		//測試Spring提供的其他方法:getBeanNamesForType()
		@Test
		public void test02() {
			//1.獲得Spring工廠(幫我們創建物件)，此例為非Web的工廠，ClassPathXmlApplicationContext()，需要參數(工廠配置文件位置)-->applicationContext.xml。
			ApplicationContext springFactory = new ClassPathXmlApplicationContext("/applicationContext.xml");
			
			//2.獲取Spring工廠配置文件中，根據Class類型獲取，Bean標籤的ID值
			String[] beansNames = springFactory.getBeanNamesForType(Person.class);
			for(String beanName : beansNames) {
				System.out.println("ID:" + beanName);
			}				
		}
		
	
	
				//測試Spring提供的其他方法:containsBeanDefinition()
				@Test
				public void test03() {
					//1.獲得Spring工廠(幫我們創建物件)，此例為非Web的工廠，ClassPathXmlApplicationContext()，需要參數(工廠配置文件位置)-->applicationContext.xml。
					ApplicationContext springFactory = new ClassPathXmlApplicationContext("/applicationContext.xml");
					
					//2.獲取Spring工廠配置文件中，尋找特定ID值得Bean是否存在，返回布林值。
					Boolean booleanSign =  springFactory.containsBeanDefinition("bike");
					
					System.out.println("ID為bike的Bean是否存在:" + booleanSign);
										
				}
				
				
				
				//測試Spring提供的其他方法:containsBean()
				@Test
				public void test04() {
					//1.獲得Spring工廠(幫我們創建物件)，此例為非Web的工廠，ClassPathXmlApplicationContext()，需要參數(工廠配置文件位置)-->applicationContext.xml。
					ApplicationContext springFactory = new ClassPathXmlApplicationContext("/applicationContext.xml");
					
					//2.獲取Spring工廠配置文件中，尋找特定ID值得Bean是否存在，返回布林值。
					Boolean booleanSign =  springFactory.containsBean("bike");
					
					System.out.println("ID為bike的Bean是否存在02:" + booleanSign);
									 	
				}
	
	
	
}
