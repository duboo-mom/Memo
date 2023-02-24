package com.duboomom.memo.common;

import java.io.IOException;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Component
public class PermissionInterceptor implements HandlerInterceptor {
	
	@Override
	public boolean preHandle(
			HttpServletRequest request
			, HttpServletResponse response
			, Object handler) throws IOException {
		
		HttpSession session = request.getSession();
		// null이 저장될 수 도 있으니 래퍼클래스
		Integer userId = (Integer)session.getAttribute("userId");
		
		// /user/signin/view
		String uri = request.getRequestURI();
		
		if(userId != null) {
			// 로그인이 되어 있는 경우
			// 회원가입페이지, 로그인페이지를 접근할 경우
			// /user/signup/view, /user/signin/view
			// /user로 시작하는 페이지 접근할 경우
			if(uri.startsWith("/user")) {
				// 메모 리스트로 페이지 이동
				response.sendRedirect("/post/list/view");
				return false;
			}
			
		} else {
			// 로그인이 되어 있지 않은 경우
			// 메모리스트, 입력화면, 디테일화면으로 접근할 경우
			// /post/...
			if(uri.startsWith("/post")) {
				// 로그인 페이지로 이동
				response.sendRedirect("/user/signin/view");
				return false;
			}
			
		}
		
		return true;
		
	}

	@Override
	public void postHandle(
			HttpServletRequest request
			, HttpServletResponse response
			, Object handler
			, ModelAndView modelAndView) {
		
	}
	
	@Override
	public void afterCompletion(
			HttpServletRequest request
			, HttpServletResponse response
			, Object handler
			, Exception ex) {
		
	}
	
}
