package yu.test;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import model.Person;

public class Test01 {
	
	//第一個Spring程式:.getBean(欲創建Bean的ID名稱)
	@Test
	public void test01() {
		//1.獲得Spring工廠(幫我們創建物件)，此例為非Web的工廠，ClassPathXmlApplicationContext()，需要參數(工廠配置文件位置)-->applicationContext.xml。
		ApplicationContext springFactory = new ClassPathXmlApplicationContext("/applicationContext.xml");
		
		//2.通過工廠，獲得物件。.getBean(欲創建Bean的ID名稱)，返回Object物件，要做類型轉換。
		 Person person = (Person)springFactory.getBean("person");
		 
		 //3.確認物件創建成功
		 System.out.println(person);
				
	}
	
		//測試Spring提供的其他方法:.getBean(Bean的ID名稱,class名稱)
		@Test
		public void test02() {
			//1.獲得Spring工廠(幫我們創建物件)，此例為非Web的工廠，ClassPathXmlApplicationContext()，需要參數(工廠配置文件位置)-->applicationContext.xml。
			ApplicationContext springFactory = new ClassPathXmlApplicationContext("/applicationContext.xml");
			
			//2.通過工廠，獲得物件。.getBean(Bean的ID名稱,class名稱)-->這樣就不用強轉了
			 Person person = springFactory.getBean("person",Person.class);
			 
			 //3.確認物件創建成功
			 System.out.println(person);
					
		}
		
		
		//測試Spring提供的其他方法02:.getBean(class名稱)
				@Test
				public void test03() {
					//1.獲得Spring工廠(幫我們創建物件)，此例為非Web的工廠，ClassPathXmlApplicationContext()，需要參數(工廠配置文件位置)-->applicationContext.xml。
					ApplicationContext springFactory = new ClassPathXmlApplicationContext("/applicationContext.xml");
					
					//2.通過工廠，獲得物件。.getBean(class名稱)-->只用class名稱來創建，但確保applicationContext.xml裡面只能有一個Person.class的Bean對象，不然會報錯。
					 Person person = springFactory.getBean(Person.class);
					 
					 //3.確認物件創建成功
					 System.out.println(person);
							
				}
				
				
				
				
	
	
	
	
}
