package com.model2.mvc.service.purchase.test;

import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.domain.User;
import com.model2.mvc.service.purchase.PurchaseService;

/*
 *	FileName :  UserServiceTest.java
 * ㅇ JUnit4 (Test Framework) 과 Spring Framework 통합 Test( Unit Test)
 * ㅇ Spring 은 JUnit 4를 위한 지원 클래스를 통해 스프링 기반 통합 테스트 코드를 작성 할 수 있다.
 * ㅇ @RunWith : Meta-data 를 통한 wiring(생성,DI) 할 객체 구현체 지정
 * ㅇ @ContextConfiguration : Meta-data location 지정
 * ㅇ @Test : 테스트 실행 소스 지정
 */
@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = { "classpath:config/commonservice.xml" })
@ContextConfiguration	(locations = {	"classpath:config/context-common.xml",
		"classpath:config/context-aspect.xml",
		"classpath:config/context-mybatis.xml",
		"classpath:config/context-transaction.xml" })
public class PurchaseServiceTest {

	//==>@RunWith,@ContextConfiguration 이용 Wiring, Test 할 instance DI
	@Autowired
	@Qualifier("purchaseServiceImpl")
	private PurchaseService purchaseService;

	//@Test
	public void testAddPurchase() throws Exception {
		Product product = new Product();
		product.setProdNo(10003);
		
		User user = new User();
		user.setUserId("user02");
		
		Purchase purchase = new Purchase();
		purchase.setPurchaseProd(product);
		purchase.setBuyerId(user);
		purchase.setDivyRequest("빨리");
		purchase.setDivyDate("20220401");
		
		purchaseService.addPurchase(purchase);
		
		System.out.println(purchase);
		
		//Assert.assertEquals();
	}
	@Test
	public void testGetPurchase() throws Exception {
		Purchase purchase = purchaseService.getPurchase(10000);
		System.out.println(purchase);
	}
	
	//@Test
	public void testUpdatePurchase() throws Exception{
		Purchase purchase = purchaseService.getPurchase(10000);
		Assert.assertNotNull(purchase);
		System.out.println("purchase update start"+purchase);
		
		Assert.assertEquals("서울시 강북구 ", purchase.getDivyAddr());
		
		purchase.setDivyAddr("강북구");
		
		System.out.println("purchase update end"+purchase);
		purchaseService.updatePurchase(purchase);
		
		purchase = purchaseService.getPurchase(10000);
		Assert.assertNotNull(purchase);
		
		System.out.println(purchase);
//		Assert.assertEquals("바꿈", purchase.getDivyRequest());
	}
//	
	//@Test	
	public void testUpdateTranCode() throws Exception{
		Purchase purchase = purchaseService.getPurchase(10000);
		Assert.assertNotNull(purchase);
		System.out.println("purchase update start"+purchase);
		
		Assert.assertEquals("3  ", purchase.getTranCode());
		
		purchase.setTranCode("2");
		
		System.out.println("purchase update end"+purchase);
		purchaseService.updateTranCode(purchase);
		
		purchase = purchaseService.getPurchase(10000);
		Assert.assertNotNull(purchase);
		
		System.out.println(purchase);
	}
//	

//	
//	 //==>  주석을 풀고 실행하면....
	 @Test
	 public void testGetPurchaseListAll() throws Exception{
//		Purchase purchase = new Purchase();
//		purchase.getBuyerId();
	 	Search search = new Search();
	 	search.setCurrentPage(1);
	 	search.setPageSize(3);
	 	Map<String,Object> map = purchaseService.getPurchaseList(search,"user01");
	 	
	 	List<Object> list = (List<Object>)map.get("list");
	 	for(int i=0;i<list.size();i++){
	 		System.out.println(list.get(i));
	 	}
	 }

}