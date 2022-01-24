package kh.spring.controller;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;

import kh.spring.dto.MemberDTO;

@Controller
@RequestMapping("/tmp/")
public class tmp {

	@Autowired
	private SqlSessionTemplate mybatis;

	@ResponseBody
	@RequestMapping("showMember")
	public String showMember(int mem_seq) {
		//DAO
		MemberDTO dto = mybatis.selectOne("Member.myInfoSelectAll",mem_seq);

		//Service
		Gson gson = new Gson();
		dto.setPw("");
		if(!dto.getPh_Open().equals("on")) {
			dto.setPhone("비공개 연락처입니다.");
		}			

		String result = gson.toJson(dto);
		return result;
	}
}
