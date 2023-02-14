package com.duboomom.memo.post;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.duboomom.memo.post.bo.PostBO;
import com.duboomom.memo.post.model.Post;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/post")
public class PostController {

	@Autowired
	private PostBO postBO;
	
	@GetMapping("/list/view")
	public String listView(
			Model model
			, HttpServletRequest request) {
		
		HttpSession session = request.getSession();
		int userId = (Integer)session.getAttribute("userId");
		
		List<Post> postList = postBO.getPostList(userId);
		
		model.addAttribute("postList", postList);
		
		return "post/list";
	}
	
	@GetMapping("/create/view")
	public String createView() {
		return "post/create";
	}
	
	@GetMapping("/detail/view")
	public String detailView(
			@RequestParam("postId") int postId
			, Model model) {
		
		Post post = postBO.getPost(postId);
		
		model.addAttribute("post", post);
		
		return "post/detail";
	}
	
}
