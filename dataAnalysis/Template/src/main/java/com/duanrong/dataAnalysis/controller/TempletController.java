package com.duanrong.dataAnalysis.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.ibatis.scripting.xmltags.WhereSqlNode;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.duanrong.dataAnalysis.business.user.model.TemplateUser;
import com.duanrong.dataAnalysis.business.user.model.User;
import com.duanrong.dataAnalysis.business.user.service.UserService;
import com.duanrong.dataAnalysis.util.IdCardGenerator;
import com.duanrong.dataAnalysis.util.IdGenerator;
import com.duanrong.dataAnalysis.util.MyStringUtils;
import com.duanrong.dataAnalysis.util.ShortUrlGenerator;




@Controller
public class TempletController {
	
	@Resource
	private UserService userService;
	
	
	@ResponseBody
	@RequestMapping(value="/add")
	public String addUser(HttpServletRequest request,HttpServletResponse response){
		
		String year=request.getParameter("year");
		String month=request.getParameter("month");
		List<TemplateUser> list=userService.getUserTemplate( year, month);
		List<User> users=new ArrayList<>();
		for (TemplateUser templateUser : list) {
			String time=templateUser.getTime();
			int  count=templateUser.getCount();
			int i=0;	
			while (count!=i) {
					Map<String,Object>map=new HashMap<>();
					String userId = MyStringUtils.append(
							ShortUrlGenerator.shortUrl(IdGenerator.randomUUID()),
							ShortUrlGenerator.shortUrl(IdGenerator.randomUUID()),
							MyStringUtils.multipleLetter(4));
					IdCardGenerator g = new IdCardGenerator();  
					  String idCard=g.generate();
			      	System.out.println(idCard);
			      	String sex=idCard.substring(13,14);
					int sex1=Integer.parseInt(sex);
					if(sex1/2==0){
						sex1=0;
					}else{
						sex1=1;
					}
					// 13306363756
					//姓名  0为女　１为男
					String[] params=IdCardGenerator.getChineseName(sex1).split(",");
					String name=params[0];
					String sex2=params[1];
					User user=new User();
					map.put("time", time+" "+IdCardGenerator.getTime());
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");                
					try {
						user.setRegisterTime(sdf.parse(time+" "+IdCardGenerator.getTime()));
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					user.setIdCard(idCard);
					map.put("idCard",idCard);
					map.put("userId", userId);
					user.setUserId(userId);
					map.put("realName",name);
					user.setRealname(name);
					map.put("sex",sex2);
					user.setSex(sex2);
					String mobileNumber=IdCardGenerator.getTel();
					map.put("mobileNumber",mobileNumber);
					user.setMobileNumber(mobileNumber);
					map.put("password",IdCardGenerator.getPassword());
					user.setPassword(IdCardGenerator.getPassword());
					map.put("isReal",1);
					user.setIsReal(1);
					map.put("status",1);
					user.setStatus("1");
					int user1=userService.getUserCountByMobileNumber(mobileNumber);
					int user2=userService.getUserByIdCard(idCard);
					if (user1==0&&user2==0) {
						/*userServ-ice.insertUser(map);*/
						users.add(user);
						i++;
					}
				}
				/*s*/
		}
		userService.insertUsers(users);
		return "ok";
	}
	        
	
	
	
	public static void main(String[] args) {
		for (int i = 0; i < 100; i++) {
			IdCardGenerator g = new IdCardGenerator();  
			  String idCard=g.generate();
			  System.out.println(idCard.length());
		}
		
	}
	
} 
