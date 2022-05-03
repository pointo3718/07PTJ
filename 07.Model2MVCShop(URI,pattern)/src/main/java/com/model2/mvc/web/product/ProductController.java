package com.model2.mvc.web.product;

import java.awt.Menu;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.model2.mvc.common.Page;
import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.product.ProductService;

//상풍관리 Controller
@Controller
@RequestMapping("/product/*")
public class ProductController {
	
	//Field
	@Autowired
	@Qualifier("ProductServiceImpl")
	private ProductService productService;
	//setter Method 구현 X
	
	public ProductController() {
		System.out.println(this.getClass());
		//getClass() : 객체가 속하는 클래스의 정보를 알아내는 메소드이다.
	}
	
	// @Value : properties 파일에 세팅한 내용을 Spring 변수에 주입
	@Value("#{commonProperties['pageUnit']}")
	int pageUnit;
	
	@Value("#{commonProperties['pageSize']}")
	int pageSize;
	
	@RequestMapping(value="addProduct", method=RequestMethod.GET)
	public String addProduct() throws Exception{

		System.out.println("/product/addProduct : GET");

		return "redirect:/product/addProductView.jsp";
	}
	
	//@RequestMapping 요청이 왔을 때 어떤 컨트롤러가 호출이 되어야 하는지 알려주는 지표
//	@RequestMapping("/addProduct.do")
	@RequestMapping(value="addProduct", method=RequestMethod.POST)
	public String addProduct(@ModelAttribute("product")Product product,@RequestParam("manuDate")String manuDate) throws Exception{

		System.out.println("/product/addProduct : POST");

		product.setManuDate(manuDate.replace("-", ""));
		productService.addProduct(product);
		
		return "redirect:/product/addProductView.jsp";
	}
	
	/* @RequestParam 
	 * 1개의 HTTP 파라미터를 얻기 위해 사용됨
	 * 필수 여부가 true이기 때문에 반드시 필요한 경우가 아니라면 required=false 설정이 필요함
	 * @ModelAttribute 
	 * multipart/form-data 형태로 받은 HTTP Body 데이터와 HTTP 파라미터들을 Setter를 통해 1대1로 객체에 바인딩시킴
	 * 변환이 아닌 값을 주입시키므로, 변수들의 생성자나 Setter함수가 없으면 변수들이 저장되지 않음
	 */
	//@RequestMapping("getProduct")
	//@RequestParam("가져올 데이터의 이름") [데이터타입] [가져온데이터를 담을 변수] 형식으로 사용 model 객체를 이용해서 view 값 전달.
	@RequestMapping( value="getProduct", method=RequestMethod.GET)
	public String getProduct(@RequestParam("prodNo") int prodNo, Model model ) throws Exception {
		
		System.out.println("/product/getProduct");
		
		Product product= productService.getProduct(prodNo);
		
		//model.addAttribute는 (key,value) 메소드이용해서 view 전달할데이터 전달
		model.addAttribute("product",product);
		
		return "forward:/product/getProduct.jsp";
	}
	
//	@RequestMapping("/product/updateProductView")
	@RequestMapping( value="updateProductView", method=RequestMethod.GET)
	public String updateProductView(@RequestParam("prodNo") int prodNo, Model model ) throws Exception {
		
		System.out.println("/updateProductView.do");
		
		Product product = productService.getProduct(prodNo);
		
		model.addAttribute("product",product);
		
		return "forward:/product/updateProduct.jsp";
	}
	
//	@RequestMapping("/product/updateProduct")
	@RequestMapping( value="updateProduct", method=RequestMethod.POST)
	public String updateProduct(@ModelAttribute("product")Product product, Model model) throws Exception{
	
		System.out.println("/product/updateProduct");
//		Product prodNo=productService.getProduct(product.getProdNo());
		
		productService.updateProduct(product);
		
		return "redirect:/product/getProduct?prodNo="+product.getProdNo();
	}
	
//	@RequestMapping("/product/listProduct")
	@RequestMapping( value="listProduct")
	public String listProduct(@ModelAttribute("search") Search search ,@RequestParam("menu")String menu, Model model , HttpServletRequest request) throws Exception {
		
		System.out.println("/product/listProduct");
		
		if(search.getCurrentPage() ==0 ){
			search.setCurrentPage(1);
		}
		search.setPageSize(pageSize);	
		
		Map<String , Object> map = productService.getProductList(search);
		
		Page resultPage	= new Page( search.getCurrentPage(), ((Integer)map.get("totalCount")).intValue(), pageUnit, pageSize);
		System.out.println(resultPage);
		
		model.addAttribute("menu",menu);
		model.addAttribute("list", map.get("list"));
		model.addAttribute("resultPage", resultPage);
		model.addAttribute("search", search);
		
		return "forward:/product/listProduct.jsp";
	}

}
