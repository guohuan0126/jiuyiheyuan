package com.duanrong.dataAnalysis.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.duanrong.dataAnalysis.business.CapitalData.model.CapitalDataVO;
import com.duanrong.dataAnalysis.business.user.model.ResData;
import com.duanrong.dataAnalysis.business.user.model.User;
import com.duanrong.dataAnalysis.business.user.model.UserData;
import com.duanrong.dataAnalysis.business.user.model.UserDataView;
import com.duanrong.dataAnalysis.business.user.service.UserDataService;
import com.duanrong.dataAnalysis.controllerHelper.UserCookieHelper;

@Controller
public class userDataController extends BaseController {

	@Resource
	UserDataService userDataService;

	@RequestMapping("/UserData/ShowUserData")
	public String toShowUserData(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		/*User user=UserCookieHelper.GetUserCookie(request, response);
		model.addAttribute("user", user);*/
		return "houtai2";
	}

	@ResponseBody
	@RequestMapping("/userData/showTUserData")
	public String shouUserData(
	/*
	 * @RequestParam("id")Integer id,
	 * 
	 * @RequestParam("sTime")String sTime,
	 * 
	 * @RequestParam("eTime")String eTime,
	 */
	HttpServletRequest request, HttpServletResponse response) {
		String id = request.getParameter("id");
		UserData userData = null;
		UserDataView userDataView = new UserDataView();
		if (id.equals("today")) {
			userData = userDataService.getUserDataByT();
			userDataView.setNewAllUserCount(userData.getNewAllUserCount());
			userDataView.setNewAllUserRegisterCount(userData.getNewAllUserRegisterCount());
			userDataView.setNewInvestCount(userData.getNewInvestCount());
			return JSONObject.fromObject(userDataView).toString();
		} else if (id.equals("week")) {
			// 用户注册数量
			List<ResData> cData = userDataService.getUserCountByW();
			// 用户注册开户
			List<ResData> rData = userDataService.getUserRByW();
			// 用户注册投资
			List<ResData> iData = userDataService.getUserIByW();
			// 用于将存放时间 利用set集合 存放不重复的元素用来存放时间
			Set<String> setTimer=new HashSet<String>();
			//将处理好的数据存放到map中 使用json在页面接收 拼装成插件需要的数据类型
			Map<String, List<ResData>> map=new HashMap<String, List<ResData>>();
			
			List<ResData> resDatasList=new ArrayList<ResData>();
			//遍历注册数量的集合 获取用户注册的时间
			for(ResData rd:cData){
				//放到set里面 在取出来当做key放到map中
				setTimer.add(rd.getTimer());
			}
			for(ResData rd:rData){
				setTimer.add(rd.getTimer());
			}
			for(ResData rd:iData){
				setTimer.add(rd.getTimer());
			}
			//遍历set集合 将时间拿出来 放到map中作为key
			for (String str : setTimer) {
				map.put(str, null);				
				for (int i = 0; i < cData.size(); i++) {
					
					if(str.equals(cData.get(i).getTimer())){
						
						resDatasList.add(cData.get(i));
					}
				}
				for (int i = 0; i < rData.size(); i++) {
						
					if(str.equals(rData.get(i).getTimer())){
						resDatasList.add(rData.get(i));
					}

				}
				for (int i = 0; i < iData.size(); i++) {
					if(str.equals(iData.get(i).getTimer())){
						resDatasList.add(iData.get(i));
					}
				}
				
				
				
			}  
			
			for (Map.Entry<String, List<ResData>> entry : map.entrySet()) {  
				List<ResData> resDatasLists=new ArrayList<>();
				for(int i=0;i<resDatasList.size();i++){
					if(entry.getKey().equals(resDatasList.get(i).getTimer())){
						resDatasLists.add(resDatasList.get(i));
						entry.setValue(resDatasLists);
					}
				}
			}  
			
			for (Map.Entry<String, List<ResData>> entry : map.entrySet()){
				List<ResData> ResData = entry.getValue();
				if (entry.getValue().size() != 3){
					
//					List<String> con=new ArrayList<>();
					String con[]= new String[ResData.size()];;
					for(int i=0;i<ResData.size();i++){
						con[i]=ResData.get(i).getType();
						
						
					}

					if(!isHave(con,"用户注册")){
						ResData resData = new ResData();
					resData.setType("用户注册");
					resData.setCount(0);
					ResData.add(0, resData);
					}
					
					if(!isHave(con,"用户开户")){
					ResData resData = new ResData();
					resData.setType("用户开户");
					resData.setCount(0);
					ResData.add(1, resData);
					
					}
//					kh,zc,tz  zc,kh,tz 
					
					if(!isHave(con,"用户投资")){
					ResData resData = new ResData();
					resData.setType("用户投资");
					resData.setCount(0);
					ResData.add(2, resData);
					}
				
					
				}
				
				
				
			}
			
			JSONObject json = new JSONObject();
			Map<String, List<ResData>> map2=	toSort();
			map2.putAll(map);
			json.putAll(map2);
			return json.toString();
			//周查询结束
		} else if (id.equals("month")) {
			List<ResData> cData = userDataService.getUserCountByM();
			List<ResData> rData = userDataService.getUserRByM();
			List<ResData> iData = userDataService.getUserIByM();
			Set<String> setTimer=new HashSet<String>();
			Map<String, List<ResData>> map=new HashMap<String, List<ResData>>();
			
			List<ResData> resDatasList=new ArrayList<ResData>();
			
			for(ResData rd:cData){
				
				setTimer.add(rd.getTimer());
			}
			for(ResData rd:rData){
				setTimer.add(rd.getTimer());
			}
			for(ResData rd:iData){
				setTimer.add(rd.getTimer());
			}
			for (String str : setTimer) {
				map.put(str, null);				
				for (int i = 0; i < cData.size(); i++) {
					
					if(str.equals(cData.get(i).getTimer())){
						
						resDatasList.add(cData.get(i));
					}
				}
				for (int i = 0; i < rData.size(); i++) {
						
					if(str.equals(rData.get(i).getTimer())){
						resDatasList.add(rData.get(i));
					}

				}
				for (int i = 0; i < iData.size(); i++) {
					if(str.equals(iData.get(i).getTimer())){
						resDatasList.add(iData.get(i));
					}
				}
				
				
				
			}  
			
			for (Map.Entry<String, List<ResData>> entry : map.entrySet()) {  
				List<ResData> resDatasLists=new ArrayList<>();
				for(int i=0;i<resDatasList.size();i++){
					if(entry.getKey().equals(resDatasList.get(i).getTimer())){
						resDatasLists.add(resDatasList.get(i));
						entry.setValue(resDatasLists);
					}
				}
			}  
			
			for (Map.Entry<String, List<ResData>> entry : map.entrySet()){
				List<ResData> ResData = entry.getValue();
				if (entry.getValue().size() != 3){
					
//					List<String> con=new ArrayList<>();
					String con[]= new String[ResData.size()];;
					for(int i=0;i<ResData.size();i++){
						con[i]=ResData.get(i).getType();
						
						
					}
					if(!isHave(con,"用户注册")){
						ResData resData = new ResData();
					resData.setType("用户注册");
					resData.setCount(0);
					ResData.add(0, resData);
					}
					
					if(!isHave(con,"用户开户")){
					ResData resData = new ResData();
					resData.setType("用户开户");
					resData.setCount(0);
					ResData.add(1, resData);
					
					}
//					kh,zc,tz  zc,kh,tz 
					
					if(!isHave(con,"用户投资")){
					ResData resData = new ResData();
					resData.setType("用户投资");
					resData.setCount(0);
					ResData.add(2, resData);
					}
				
					
				}
				
				
				
			}

			JSONObject json = new JSONObject();
			Map<String, List<ResData>> map2=	toSort();
			map2.putAll(map);
			json.putAll(map2);
			return json.toString();
			//月查询结束
		} else if (id.equals("query")) {
			String sTime = request.getParameter("sTime");
			String eTime = request.getParameter("eTime")+"23:59";
			List<ResData> cData = userDataService.getUserCountByD(sTime, eTime);
			List<ResData> rData = userDataService.getUserRByD(sTime, eTime);
			List<ResData> iData = userDataService.getUserIByD(sTime, eTime);
			Set<String> setTimer=new HashSet<String>();
			Map<String, List<ResData>> map=new HashMap<String, List<ResData>>();
			
			List<ResData> resDatasList=new ArrayList<ResData>();
			
			for(ResData rd:cData){
				
				setTimer.add(rd.getTimer());
			}
			for(ResData rd:rData){
				setTimer.add(rd.getTimer());
			}
			for(ResData rd:iData){
				setTimer.add(rd.getTimer());
			}
			for (String str : setTimer) {
				map.put(str, null);				
				for (int i = 0; i < cData.size(); i++) {
					
					if(str.equals(cData.get(i).getTimer())){
						
						resDatasList.add(cData.get(i));
					}
				}
				for (int i = 0; i < rData.size(); i++) {
						
					if(str.equals(rData.get(i).getTimer())){
						resDatasList.add(rData.get(i));
					}

				}
				for (int i = 0; i < iData.size(); i++) {
					if(str.equals(iData.get(i).getTimer())){
						resDatasList.add(iData.get(i));
					}
				}
				
				
				
			}  
			
			for (Map.Entry<String, List<ResData>> entry : map.entrySet()) {  
				List<ResData> resDatasLists=new ArrayList<>();
				for(int i=0;i<resDatasList.size();i++){
					if(entry.getKey().equals(resDatasList.get(i).getTimer())){
						resDatasLists.add(resDatasList.get(i));
						entry.setValue(resDatasLists);
					}
				}
			}  
			
			for (Map.Entry<String, List<ResData>> entry : map.entrySet()){
				List<ResData> ResData = entry.getValue();
				if (entry.getValue().size() != 3){
					
//					List<String> con=new ArrayList<>();
					String con[]= new String[ResData.size()];;
					for(int i=0;i<ResData.size();i++){
						con[i]=ResData.get(i).getType();
						
						
					}
					if(!isHave(con,"用户注册")){
						ResData resData = new ResData();
					resData.setType("用户注册");
					resData.setCount(0);
					ResData.add(0, resData);
					}
					
					if(!isHave(con,"用户开户")){
					ResData resData = new ResData();
					resData.setType("用户开户");
					resData.setCount(0);
					ResData.add(1, resData);
					
					}
//					kh,zc,tz  zc,kh,tz 
					
					if(!isHave(con,"用户投资")){
					ResData resData = new ResData();
					resData.setType("用户投资");
					resData.setCount(0);
					ResData.add(2, resData);
					}
					
				}
				
				
				
			}
			

			JSONObject json = new JSONObject();
			Map<String, List<ResData>> map2=	toSort();
			map2.putAll(map);
			json.putAll(map2);
			return json.toString();

		}

		return JSONObject.fromObject(userDataView).toString();
	}

	public static boolean isHave(String[] strs,String s){
		  /*此方法有两个参数，第一个是要查找的字符串数组，第二个是要查找的字符或字符串
		   * */
		  for(int i=0;i<strs.length;i++){
		   if(strs[i].indexOf(s)!=-1){//循环查找字符串数组中的每个字符串中是否包含所有查找的内容
		    return true;//查找到了就返回真，不在继续查询
		   }
		  }
		  return false;//没找到返回false
		 }
	
	//对日期排序
	public Map<String, List<ResData>> toSort(){
		
		Map<String, List<ResData>> map11 = new TreeMap<String, List<ResData>>(

                new Comparator<String>() {

                    public int compare(String obj1, String obj2) {
                        // 降序排序
                        return obj1.compareTo(obj2);

                    }

                });
		return map11;
		
	}
	
	
	
	/**
	 * 跳转到用户来源查询
	 * 
	 */
	@RequestMapping(value="/userData/toUserSourceData")
	public String toShowUserInfo(HttpServletRequest request,HttpServletResponse response,Model model){
		/*User user=UserCookieHelper.GetUserCookie(request, response);
		model.addAttribute("user", user);*/
		return "userSourceData";
	}
	
	@RequestMapping(value="/userData/userSourceData")
	public String showUserDataByuserSource(HttpServletRequest request,Model model){
		String start=request.getParameter("start");
		String end=request.getParameter("end");
		String userSource=request.getParameter("userSource");
		//来源注册人数
 		double registerCount=userDataService.getRegisterUserCount(start,end,userSource);
		//来源实名用户
		double realNameCount=userDataService.getRealNameUserCount(start,end,userSource);
		//首投人数
		double fristInvestCount=userDataService.getFristInvestCount(start,end,userSource);
		//首投金额
		double fristInvestMoney=userDataService.getFristInvestMoney(start,end,userSource);
		//复投人数
		double investUserCountAgain=userDataService.getInvestMoneyAgain(start,end,userSource);
		//复投金额
		double investUserMoneyAgain=userDataService.getInvestUserMoneyAgain(start,end,userSource);
		model.addAttribute("registerCount", registerCount);
		model.addAttribute("realNameCount", realNameCount);
		model.addAttribute("fristInvestCount", fristInvestCount);
		model.addAttribute("fristInvestMoney", fristInvestMoney);
		model.addAttribute("investUserCountAgain", investUserCountAgain);
		model.addAttribute("investUserMoneyAgain", investUserMoneyAgain);
		model.addAttribute("start", start);
		model.addAttribute("end", end);
		model.addAttribute("userSource",userSource);
		return "userSourceData";
	}
}
