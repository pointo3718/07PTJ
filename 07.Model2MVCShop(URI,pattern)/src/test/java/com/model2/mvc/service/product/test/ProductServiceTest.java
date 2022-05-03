package com.model2.mvc.service.product.test;

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
import com.model2.mvc.service.product.ProductService;


/*
 *	FileName :  ProductServiceTest.java
 * §∑ JUnit4 (Test Framework) ∞˙ Spring Framework ≈Î«’ Test( Unit Test)
 * §∑ Spring ¿∫ JUnit 4∏¶ ¿ß«— ¡ˆø¯ ≈¨∑°Ω∫∏¶ ≈Î«ÿ Ω∫«¡∏µ ±‚π› ≈Î«’ ≈◊Ω∫∆Æ ƒ⁄µÂ∏¶ ¿€º∫ «“ ºˆ ¿÷¥Ÿ.
 * §∑ @RunWith : Meta-data ∏¶ ≈Î«— wiring(ª˝º∫,DI) «“ ∞¥√º ±∏«ˆ√º ¡ˆ¡§
 * §∑ @ContextConfiguration : Meta-data location ¡ˆ¡§
 * §∑ @Test : ≈◊Ω∫∆Æ Ω««‡ º“Ω∫ ¡ˆ¡§
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration	(locations = {	"classpath:config/context-common.xml",
		"classpath:config/context-aspect.xml",
		"classpath:config/context-mybatis.xml",
		"classpath:config/context-transaction.xml" })
public class ProductServiceTest {

	//==>@RunWith,@ContextConfiguration ¿ÃøÎ Wiring, Test «“ instance DI
	@Autowired
	@Qualifier("ProductServiceImpl")
	private ProductService productService;

//	@Test
	public void testAddProduct() throws Exception {
		
		Product product = new Product();
		product.setProdName("∏¡∞ÌΩ∫∆æ");
		product.setProdDetail("≤‹∏¿");
		product.setManuDate("20220401");
		product.setPrice(5000);
		product.setFileName("∏¡∞ÌΩ∫∆æ");
		
//		System.out.println(product);

		productService.addProduct(product);
		
//		product = productService.getProduct(10021);

		//==> console »Æ¿Œ
		System.out.println(product);
		
		//==> API »Æ¿Œ
		Assert.assertEquals("∏¡∞ÌΩ∫∆æ", product.getProdName());
		Assert.assertEquals("≤‹∏¿", product.getProdDetail());
		Assert.assertEquals("20220401", product.getManuDate());
		Assert.assertEquals(5000, product.getPrice());
		Assert.assertEquals("∏¡∞ÌΩ∫∆æ", product.getFileName());
	}
	
//	@Test
	public void testGetProduct() throws Exception {
		
		Product product = new Product();
		//==> « ø‰«œ¥Ÿ∏È...
//		product.setProdNo(10020);
//		product.setProdName("∏¡∞ÌΩ∫∆æ");
//		product.setProdDetail("≤‹∏¿");
//		product.setManuDate("20220401");
//		product.setPrice(5000);
//		product.setFileName("∏¡∞ÌΩ∫∆æ");
		
		product = productService.getProduct(10021);

		//==> console »Æ¿Œ
		System.out.println(product);
		
		//==> API »Æ¿Œ
		Assert.assertEquals(10021, product.getProdNo());
		Assert.assertEquals("∏¡∞ÌΩ∫∆æ", product.getProdName());
		Assert.assertEquals("≤‹∏¿", product.getProdDetail());
		Assert.assertEquals("20220401", product.getManuDate());
		Assert.assertEquals(5000, product.getPrice());
		Assert.assertEquals("∏¡∞ÌΩ∫∆æ", product.getFileName());

	}
	
//	@Test
	 public void testUpdateProduct() throws Exception{
		 
		Product product = productService.getProduct(10020);
		Assert.assertNotNull(product);
		System.out.println("1"+product);
		
		Assert.assertEquals("∏¡∞ÌΩ∫∆æ", product.getProdName());
		Assert.assertEquals("≤‹∏¿", product.getProdDetail());
		Assert.assertEquals("20220401", product.getManuDate());
		Assert.assertEquals(5000, product.getPrice());
		Assert.assertEquals("∏¡∞ÌΩ∫∆æ", product.getFileName());

		product.setProdName("ª˛¿Œ∏”Ω∫ƒπ");
		product.setProdDetail("≤‹∏¿≈ ");
		product.setManuDate("20220402");
		product.setPrice(8000);
		product.setFileName("ª˛¿Œ∏”Ω∫ƒπ");
		
		System.out.println("2"+product);
		productService.updateProduct(product);
		
		product = productService.getProduct(10021);
		Assert.assertNotNull(product);
		
		//==> console »Æ¿Œ
		System.out.println(product);

		//==> API »Æ¿Œ
		Assert.assertEquals("ª˛¿Œ∏”Ω∫ƒπ", product.getProdName());
		Assert.assertEquals("≤‹∏¿≈ ", product.getProdDetail());
		Assert.assertEquals("20220402", product.getManuDate());
		Assert.assertEquals(8000, product.getPrice());
		Assert.assertEquals("ª˛¿Œ∏”Ω∫ƒπ", product.getFileName());
	 }
	 
//	 @Test
	 public void testGetProductListAll() throws Exception{
		 
	 	Search search = new Search();
	 	search.setCurrentPage(1);
	 	search.setPageSize(3);
	 	Map<String,Object> map = productService.getProductList(search);
	 	
	 	List<Object> list = (List<Object>)map.get("list");
	 	Assert.assertEquals(3, list.size());
	 	
		//==> console »Æ¿Œ
	 	System.out.println(list);
	 	
	 	Integer totalCount = (Integer)map.get("totalCount");
	 	System.out.println(totalCount);
	 	
	 	//System.out.println("=======================================");
	 	
	 	search.setCurrentPage(1);
	 	search.setPageSize(3);
	 	search.setSearchCondition("0");
	 	search.setSearchKeyword("");
	 	map = productService.getProductList(search);
	 	
	 	list = (List<Object>)map.get("list");
	 	Assert.assertEquals(3, list.size());
	 	
	 	//==> console »Æ¿Œ
	 	System.out.println(list);
	 	
	 	totalCount = (Integer)map.get("totalCount");
	 	System.out.println(totalCount);
	 }
	 
//	 @Test
	 public void testGetProductListByProductNo() throws Exception{
		 
	 	Search search = new Search();
	 	search.setCurrentPage(1);
	 	search.setPageSize(3);
	 	search.setSearchCondition("0");
	 	search.setSearchKeyword("10020");
	 	Map<String,Object> map = productService.getProductList(search);
	 	
	 	List<Object> list = (List<Object>)map.get("list");
	 	Assert.assertEquals(1, list.size());
	 	
		//==> console »Æ¿Œ
	 	//System.out.println(list);
	 	
	 	Integer totalCount = (Integer)map.get("totalCount");
	 	System.out.println(totalCount);
	 	
	 	System.out.println("=======================================");
	 	
	 	search.setSearchCondition("0");
	 	search.setSearchKeyword(""+System.currentTimeMillis());
	 	map = productService.getProductList(search);
	 	
	 	list = (List<Object>)map.get("list");
	 	Assert.assertEquals(0, list.size());
	 	
		//==> console »Æ¿Œ
	 	//System.out.println(list);
	 	
	 	totalCount = (Integer)map.get("totalCount");
	 	System.out.println(totalCount);
	 }
	 
	 @Test
	 public void testGetProductListByProductName() throws Exception{
		 
	 	Search search = new Search();
	 	search.setCurrentPage(1);
	 	search.setPageSize(3);
	 	search.setSearchCondition("1");
	 	search.setSearchKeyword("∏¡∞ÌΩ∫∆æ");
	 	Map<String,Object> map = productService.getProductList(search);
	 	
	 	List<Object> list = (List<Object>)map.get("list");
	 	Assert.assertEquals(2, list.size());
	 	
		//==> console »Æ¿Œ
	 	System.out.println(list);
	 	
	 	Integer totalCount = (Integer)map.get("totalCount");
	 	System.out.println(totalCount);
	 	
	 	System.out.println("=======================================");
	 	
	 	search.setSearchCondition("1");
	 	search.setSearchKeyword(""+System.currentTimeMillis());
	 	map = productService.getProductList(search);
	 	
	 	list = (List<Object>)map.get("list");
	 	Assert.assertEquals(0, list.size());
	 	
		//==> console »Æ¿Œ
	 	System.out.println(list);
	 	
	 	totalCount = (Integer)map.get("totalCount");
	 	System.out.println(totalCount);
	 }	 
}