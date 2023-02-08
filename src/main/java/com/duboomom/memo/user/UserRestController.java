package com.duboomom.memo.user;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.duboomom.memo.user.bo.UserBO;
import com.duboomom.memo.user.model.User;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@RestController // @Controller + @ResponseBody
@RequestMapping("/user")
public class UserRestController {

	@Autowired
	private UserBO userBO;
	
	// 회원가입 API
	@PostMapping("/signup")
	public Map<String, String> signup(
			@RequestParam("loginId") String loginId
			, @RequestParam("password") String password
			, @RequestParam("name") String name
			, @RequestParam("email") String email) {
		
		// 여기서 암호화X
		// controller는 request,response만 !!!
		
		int count = userBO.addUser(loginId, password, name, email);
		
		Map<String, String> result = new HashMap<>();
		
		if(count == 1) {
			result.put("result", "success");
		} else {
			result.put("result", "fail");
		}
		
		return result;
		
	}
	
	// 로그인 API
	@PostMapping("/signin")
	public Map<String, String> signin(
			@RequestParam("loginId") String loginId
			, @RequestParam("password") String password
			, HttpServletRequest request) {
		
		User user = userBO.getUser(loginId, password);
		
		// 세션 객체 얻어 오기
		HttpSession session = request.getSession();
		// 세션에 특정한 값이 저장되어 있으면 로그인 된 상태
		// 세션에 특정한 값이 저장되어 있지 않으면 로그인이 되어있지 않은 상태
		session.setAttribute("userId", user.getId());
		session.setAttribute("userName", user.getName());
		
		Map<String, String> result = new HashMap<>();
		
		if(user != null) {
			result.put("result", "success");
		} else {
			result.put("result", "fail");
		}
		
		return result;
		
	}
	
	
	
	
	
}
