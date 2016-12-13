package com.duanrong.dataAnalysis.controller;

import java.util.ArrayList;
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

import com.duanrong.dataAnalysis.business.cashData.model.ResCashData;
import com.duanrong.dataAnalysis.business.cashData.model.cashData;
import com.duanrong.dataAnalysis.business.cashData.service.cashDataService;
import com.duanrong.dataAnalysis.business.user.model.User;
import com.duanrong.dataAnalysis.controllerHelper.UserCookieHelper;
import com.duanrong.dataAnalysis.util.GetTimeUtil;

@Controller
public class CashController extends BaseController {
	@Resource
	private cashDataService cashDataService;
	
	@RequestMapping("/CashData/toShowTCashData")
	public String toShowCash(HttpServletRequest request,HttpServletResponse response,Model model){
		/*User user=UserCookieHelper.GetUserCookie(request, response);
		model.addAttribute("user", user);*/

		return "cashData";
	}
	@ResponseBody
	@RequestMapping("/CashData/showTCashData")
	public String showCash(HttpServletRequest request, HttpServletResponse response){
		String id=request.getParameter("id");
		cashData cashData= new cashData();
		if (id.equals("today")) {
			cashData=cashDataService.getCashDataT();
		
			return JSONObject.fromObject(cashData).toString();	
		
		} else if (id.equals("week")){
			String beginTime=GetTimeUtil.getBeginTime(-7);
			String endTime=GetTimeUtil.getEndTime();
			List<ResCashData> pData = cashDataService.getPayData(beginTime,endTime);
			List<ResCashData> dData = cashDataService.getDrawData(beginTime,endTime);
			
			Set<String> setTimer=new HashSet<String>();
			Map<String, List<ResCashData>> map=new HashMap<String, List<ResCashData>>();
			List<ResCashData> resDatasList=new ArrayList<ResCashData>();
			for(ResCashData rd:pData){
				//放到set里面 在取出来当做key放到map中
				setTimer.add(rd.getTimer());
			}
			for(ResCashData rd:dData){
				setTimer.add(rd.getTimer());
			}
			//遍历set集合 将时间拿出来 放到map中作为key
			for (String str : setTimer) {
				map.put(str, null);				
				for (int i = 0; i < pData.size(); i++) {
					
					if(str.equals(pData.get(i).getTimer())){
						
						resDatasList.add(pData.get(i));
					}
				}
				for (int i = 0; i < dData.size(); i++) {
						
					if(str.equals(dData.get(i).getTimer())){
						resDatasList.add(dData.get(i));
					}

				}
			}  
			for (Map.Entry<String, List<ResCashData>> entry : map.entrySet()) {  
				List<ResCashData> resDatasLists=new ArrayList<>();
				for(int i=0;i<resDatasList.size();i++){
					if(entry.getKey().equals(resDatasList.get(i).getTimer())){
						resDatasLists.add(resDatasList.get(i));
						entry.setValue(resDatasLists);
					}
				}
			}  
			for (Map.Entry<String, List<ResCashData>> entry : map.entrySet()){
				List<ResCashData> ResData = entry.getValue();
				if (entry.getValue().size() != 3){
					
//					List<String> con=new ArrayList<>();
					String con[]= new String[ResData.size()];;
					for(int i=0;i<ResData.size();i++){
						con[i]=ResData.get(i).getType();
						
						
					}

					if(!isHave(con,"充值金额")){
						ResCashData resData = new ResCashData();
					resData.setType("充值金额");
					resData.setMoney(0);
					ResData.add(0, resData);
					}
					
					if(!isHave(con,"提现金额")){
					ResCashData resData = new ResCashData();
					resData.setType("提现金额");
					resData.setMoney(0);
					ResData.add(1, resData);
					
					}
					
				}
				
				
				
			}
			JSONObject json = new JSONObject();
			Map<String, List<ResCashData>> map2=	toSort();
			map2.putAll(map);
			json.putAll(map2);
			return json.toString();
			
		}else if (id.equals("month")){
			String beginTime=GetTimeUtil.getBeginTime(-30);
			String endTime=GetTimeUtil.getEndTime();
			List<ResCashData> pData = cashDataService.getPayData(beginTime,endTime);
			List<ResCashData> dData = cashDataService.getDrawData(beginTime,endTime);
			Set<String> setTimer=new HashSet<String>();
			Map<String, List<ResCashData>> map=new HashMap<String, List<ResCashData>>();
			List<ResCashData> resDatasList=new ArrayList<ResCashData>();
			for(ResCashData rd:pData){
				//放到set里面 在取出来当做key放到map中
				setTimer.add(rd.getTimer());
			}
			for(ResCashData rd:dData){
				setTimer.add(rd.getTimer());
			}
			//遍历set集合 将时间拿出来 放到map中作为key
			for (String str : setTimer) {
				map.put(str, null);				
				for (int i = 0; i < pData.size(); i++) {
					
					if(str.equals(pData.get(i).getTimer())){
						
						resDatasList.add(pData.get(i));
					}
				}
				for (int i = 0; i < dData.size(); i++) {
						
					if(str.equals(dData.get(i).getTimer())){
						resDatasList.add(dData.get(i));
					}

				}
			}  
			for (Map.Entry<String, List<ResCashData>> entry : map.entrySet()) {  
				List<ResCashData> resDatasLists=new ArrayList<>();
				for(int i=0;i<resDatasList.size();i++){
					if(entry.getKey().equals(resDatasList.get(i).getTimer())){
						resDatasLists.add(resDatasList.get(i));
						entry.setValue(resDatasLists);
					}
				}
			}  
			for (Map.Entry<String, List<ResCashData>> entry : map.entrySet()){
				List<ResCashData> ResData = entry.getValue();
				if (entry.getValue().size() != 2){
					
//					List<String> con=new ArrayList<>();
					String con[]= new String[ResData.size()];;
					for(int i=0;i<ResData.size();i++){
						con[i]=ResData.get(i).getType();
						
						
					}

					if(!isHave(con,"充值金额")){
						ResCashData resData = new ResCashData();
					resData.setType("充值金额");
					resData.setMoney(0);
					ResData.add(0, resData);
					}
					
					if(!isHave(con,"提现金额")){
					ResCashData resData = new ResCashData();
					resData.setType("提现金额");
					resData.setMoney(0);
					ResData.add(1, resData);
					
					}
					
				}
				
				
				
			}
			JSONObject json = new JSONObject();
			Map<String, List<ResCashData>> map2= toSort();
			map2.putAll(map);
			json.putAll(map2);
			return json.toString();
			
		}else if(id.equals("query")){
			String beginTime=request.getParameter("sTime");
			String endTime=request.getParameter("eTime");
			List<ResCashData> pData = cashDataService.getPayData(beginTime,endTime);
			List<ResCashData> dData = cashDataService.getDrawData(beginTime,endTime);
			Set<String> setTimer=new HashSet<String>();
			Map<String, List<ResCashData>> map=new HashMap<String, List<ResCashData>>();
			List<ResCashData> resDatasList=new ArrayList<ResCashData>();
			for(ResCashData rd:pData){
				//放到set里面 在取出来当做key放到map中
				setTimer.add(rd.getTimer());
			}
			for(ResCashData rd:dData){
				setTimer.add(rd.getTimer());
			}
			//遍历set集合 将时间拿出来 放到map中作为key
			for (String str : setTimer) {
				map.put(str, null);				
				for (int i = 0; i < pData.size(); i++) {
					
					if(str.equals(pData.get(i).getTimer())){
						
						resDatasList.add(pData.get(i));
					}
				}
				for (int i = 0; i < dData.size(); i++) {
						
					if(str.equals(dData.get(i).getTimer())){
						resDatasList.add(dData.get(i));
					}

				}
			}  
			for (Map.Entry<String, List<ResCashData>> entry : map.entrySet()) {  
				List<ResCashData> resDatasLists=new ArrayList<>();
				for(int i=0;i<resDatasList.size();i++){
					if(entry.getKey().equals(resDatasList.get(i).getTimer())){
						resDatasLists.add(resDatasList.get(i));
						entry.setValue(resDatasLists);
					}
				}
			}  
			for (Map.Entry<String, List<ResCashData>> entry : map.entrySet()){
				List<ResCashData> ResData = entry.getValue();
				if (entry.getValue().size() != 3){
					
//					List<String> con=new ArrayList<>();
					String con[]= new String[ResData.size()];;
					for(int i=0;i<ResData.size();i++){
						con[i]=ResData.get(i).getType();
						
						
					}

					if(!isHave(con,"充值金额")){
						ResCashData resData = new ResCashData();
					resData.setType("充值金额");
					resData.setMoney(0);
					ResData.add(0, resData);
					}
					
					if(!isHave(con,"提现金额")){
					ResCashData resData = new ResCashData();
					resData.setType("提现金额");
					resData.setMoney(0);
					ResData.add(1, resData);
					
					}
					
				}
				
				
				
			}
			JSONObject json = new JSONObject();
			Map<String, List<ResCashData>> map2=	toSort();
			map2.putAll(map);
			json.putAll(map2);
			return json.toString();
			
			
		}
		return JSONObject.fromObject(cashData).toString();
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
		public Map<String, List<ResCashData>> toSort(){
			
			Map<String, List<ResCashData>> map11 = new TreeMap<String, List<ResCashData>>(

	                new Comparator<String>() {

	                    public int compare(String obj1, String obj2) {
	                        // 降序排序
	                        return obj1.compareTo(obj2);

	                    }

	                });
			return map11;
			
		}
		
		
		
	
	
	
}