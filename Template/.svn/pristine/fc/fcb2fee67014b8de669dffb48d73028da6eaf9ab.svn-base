package com.duanrong.dataAnalysis.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.duanrong.dataAnalysis.business.CapitalData.model.CapitalDataVO;
import com.duanrong.dataAnalysis.business.CapitalData.service.CapitalDataService;
import com.duanrong.dataAnalysis.business.user.model.User;
import com.duanrong.dataAnalysis.controllerHelper.UserCookieHelper;

@Controller
@RequestMapping(value="/capitalData")
public class CapitalDataController extends BaseController{

	@Autowired
	private CapitalDataService capitalDataService;
	List<CapitalDataVO> capitalDataVOs;
	@RequestMapping(value="/show")
	public String showCapitalData(HttpServletRequest request,HttpServletResponse response,Model model){
		String type=request.getParameter("type");
		if(null!=type&&"".equals(type)){
			Date now = new Date();
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd ");
			String hehe = dateFormat.format(now); 
			return "";
		}
		Map<String, String> map=new HashMap<String, String>();
		map.put("time", "2015-10-15");
		map.put("argType", "1");
		List<CapitalDataVO> capitalDataVOsList =	capitalDataService.getCount(map,0);
		
		/*User user=UserCookieHelper.GetUserCookie(request, response);
		model.addAttribute("user", user);*/
		return "capitalData";
	}
	@RequestMapping(value="/ano")
	public String showAnother(){
		
		return "houtai1";
	}
	
	
	@SuppressWarnings("unused")
	@ResponseBody
	@RequestMapping(value="/byType")
	public List<CapitalDataVO> byType(HttpServletRequest request,HttpServletResponse response){
		
		Integer type=Integer.parseInt(request.getParameter("type"));
		
		if(null!=type&&!"".equals(type)){
			Map<String, String> map=new HashMap<String, String>();
			ArrayList<CapitalDataVO> capitalDataVOsList =new ArrayList<CapitalDataVO>();
			CapitalDataVO capitalDataVO =new CapitalDataVO();
			Date now = new Date();
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			String nowTimer = dateFormat.format(now); 
			
			//今日
			if(type==0){
				
				List<CapitalDataVO> getCapitalDataVOsList =	capitalDataService.getCount(map,type);
				return getCapitalDataVOsList;
				//一周
			}if(type==1){
				
			}
			if(type==2){
				
				//一个月
				List<CapitalDataVO> getCapitalDataVOsList =	capitalDataService.getCountByMonth(map, type);
				return getCapitalDataVOsList;
				//阶段
			}if(type==3){
				
				map.put("beginTimer", "2015-11-02");
				map.put("endTimer", "2015-12-02");
				List<CapitalDataVO> getCapitalDataVOsList =	capitalDataService.getCountByPhase(map, type);
				return getCapitalDataVOsList;
			}
			
		
			
		}
		return null;
	}
	
	
	@ResponseBody
	@RequestMapping(value="/monOrWeek")
	public Map<String,List<CapitalDataVO>>  monOrWeek(HttpServletRequest request){
		
		String type=request.getParameter("type");
		Map<String, String> map1=new HashMap<String, String>();
		List<CapitalDataVO> capitalDataVOsList =null;
		if(type.equals("1")){
			capitalDataVOsList = capitalDataService.getCountByWeek(map1, 2);
		}if(type.equals("2")){
			capitalDataVOsList = capitalDataService.getCountByMonth(map1, 2);
		}if(type.equals("3")){
		String beginTimer=	request.getParameter("beginTimer");
		String endTimer=	request.getParameter("endTimer");
			map1.put("beginTimer", beginTimer);
			map1.put("endTimer", endTimer);
			capitalDataVOsList = capitalDataService.getCountByPhase(map1, 2);
		}
		
		Set<String> setTimer=new HashSet<String>();
		for(int i=0;i<capitalDataVOsList.size();i++){
			setTimer.add(capitalDataVOsList.get(i).getByTimer().split(" ")[0]);
			capitalDataVOsList.get(i).setByTimer(capitalDataVOsList.get(i).getByTimer().split(" ")[0]);
			
		}
		Map<String, List<CapitalDataVO> > map=new HashMap<String, List<CapitalDataVO>>();
			//判断两个时间相同
			for(int i=0;i<capitalDataVOsList.size();i++){
				for(String aa:setTimer){
					
				if(aa.equals(capitalDataVOsList.get(i).getByTimer())){
					
					List<CapitalDataVO>  capitalDataVOsList1=new ArrayList<CapitalDataVO>();
					
					CapitalDataVO capitalDataVO=new CapitalDataVO();
					
					capitalDataVO.setArgtype(capitalDataVOsList.get(i).getArgtype());
					capitalDataVO.setCount(capitalDataVOsList.get(i).getCount());
					capitalDataVO.setByTimer(aa);
					capitalDataVOsList1.add(capitalDataVO);
					
					map.put(aa, capitalDataVOsList1);
					
					
				}
				
				
			}
			}
			
			for(int i=0;i<capitalDataVOsList.size();i++){
				
				for (Map.Entry<String, List<CapitalDataVO>> entry : map.entrySet()) {
					if(!map.isEmpty()&&entry.getKey().contains(capitalDataVOsList.get(i).getByTimer())&&!entry.getValue().get(0).getArgtype().equals(capitalDataVOsList.get(i).getArgtype())){
						
						
						capitalDataVOsList.get(i);
						
						
						List<CapitalDataVO>  capitalDataVOs2=	entry.getValue();
						capitalDataVOs2.add(capitalDataVOsList.get(i));
						entry.setValue(capitalDataVOs2);
						//最大5 
					}
				}
			}
			List<List<CapitalDataVO>> listsss=new ArrayList<List<CapitalDataVO>>();
			for (Map.Entry<String, List<CapitalDataVO>> entry : map.entrySet()){
				if(!map.isEmpty()&&entry.getValue().size()!=5){
					List<CapitalDataVO> resData = entry.getValue();
					 capitalDataVOs=	entry.getValue();
					int currentCount=capitalDataVOs.size();
					
					
						
//						List<String> con=new ArrayList<>();
					String con[]= new String[5];
						for(int i=0;i<capitalDataVOs.size();i++){
							con[i]=capitalDataVOs.get(i).getArgtype().trim();
							
						}
						for(int i=0;i<con.length;i++){
							if(con[i]==null){
								con[i]="0";
							}
							
						}
						String[] conAno=new String[5];
					/*	conAno[0]="企业贷";
						conAno[1]="房贷";
						conAno[2]="车贷";
						conAno[3]="金农宝";
						conAno[4]="供应宝";*/
						conAno[0]="车贷";
						conAno[1]="房贷";
						conAno[2]="企业贷";
						conAno[3]="供应宝";
						conAno[4]="金农宝";
						String[] conAnoA=new String[5];
						for(int t=0;t<con.length;t++){
							
							for(int s=0;s<conAno.length;s++){
								if(con[t].equals(conAno[s])){
									conAnoA[s]=con[t];
								}
							}
						}
						
						for(int f=0;f<conAnoA.length;f++){
							
									if(conAnoA[f]==null){
										conAnoA[f]="0";
									}
						}
					for(int f=0;f<1;f++){
						capitalDataVOs.get(f).getArgtype();
						//==========
					/*	if(isHave(conAnoA,"企业贷") ){
							for(int i=0;i<capitalDataVOs.size();i++){
								if(capitalDataVOs.get(i).getArgtype().equals("企业贷")){
									CapitalDataVO capitalDataVO=new CapitalDataVO();
									capitalDataVO.setArgtype("企业贷");
									capitalDataVO.setCount(capitalDataVOs.get(i).getCount());
									capitalDataVO.setByTimer(entry.getKey());
									capitalDataVOs.add(0, capitalDataVO);
									break;
								}
								
							}
							

						}*/
						if(isHave(conAnoA,"车贷") ){
							for(int i=0;i<capitalDataVOs.size();i++){
								if(capitalDataVOs.get(i).getArgtype().equals("车贷")){
									CapitalDataVO capitalDataVO=new CapitalDataVO();
									capitalDataVO.setArgtype("车贷");
									capitalDataVO.setCount(capitalDataVOs.get(i).getCount());
									capitalDataVO.setByTimer(entry.getKey());
									capitalDataVOs.add(0, capitalDataVO);
									break;
								}
								
							}
							

						}
						if(!isHave(conAnoA,"车贷")){
							CapitalDataVO capitalDataVO=new CapitalDataVO();
							capitalDataVO.setArgtype("车贷");
							capitalDataVO.setCount("0");
							capitalDataVO.setByTimer(entry.getKey());
							capitalDataVOs.add(0, capitalDataVO);
							
						}
						
						
						//==========
						if(isHave(conAnoA,"房贷") ){
							for(int i=0;i<capitalDataVOs.size();i++){
								if(capitalDataVOs.get(i).getArgtype().equals("房贷")){
									
									CapitalDataVO capitalDataVO=new CapitalDataVO();
									capitalDataVO.setArgtype("房贷");
									capitalDataVO.setCount(capitalDataVOs.get(i).getCount());
									capitalDataVO.setByTimer(entry.getKey());
									capitalDataVOs.add(1, capitalDataVO);
									break;
								}
								
							}
							

						}
						if( !isHave(conAnoA,"房贷") ){
							CapitalDataVO capitalDataVO=new CapitalDataVO();
							capitalDataVO.setArgtype("房贷");
							capitalDataVO.setCount("0");
							capitalDataVO.setByTimer(entry.getKey());
							capitalDataVOs.add(1, capitalDataVO);
							
						}
						
						//==========
						if(isHave(conAnoA,"企业贷") ){
							for(int i=0;i<capitalDataVOs.size();i++){
								if(capitalDataVOs.get(i).getArgtype().equals("企业贷")){
									
									CapitalDataVO capitalDataVO=new CapitalDataVO();
									capitalDataVO.setArgtype("企业贷");
									capitalDataVO.setCount(capitalDataVOs.get(i).getCount());
									capitalDataVO.setByTimer(entry.getKey());
									capitalDataVOs.add(2, capitalDataVO);
									break;
								}
								
							}
							

						}
						if( !isHave(conAnoA,"企业贷")){
							CapitalDataVO capitalDataVO=new CapitalDataVO();
							capitalDataVO.setArgtype("企业贷");
							capitalDataVO.setCount("0");
							capitalDataVO.setByTimer(entry.getKey());
							capitalDataVOs.add(2, capitalDataVO);
							
						}
						
						//=========
						if(isHave(conAnoA,"供应宝") ){
							for(int i=0;i<capitalDataVOs.size();i++){
								if(capitalDataVOs.get(i).getArgtype().equals("供应宝")){
									
									CapitalDataVO capitalDataVO=new CapitalDataVO();
									capitalDataVO.setArgtype("供应宝");
									capitalDataVO.setCount(capitalDataVOs.get(i).getCount());
									capitalDataVO.setByTimer(entry.getKey());
									capitalDataVOs.add(3, capitalDataVO);
									break;
								}
								
							}
							

						}
						if(!isHave(conAnoA,"供应宝") ){
							CapitalDataVO capitalDataVO=new CapitalDataVO();
							capitalDataVO.setArgtype("供应宝");
							capitalDataVO.setCount("0");
							capitalDataVO.setByTimer(entry.getKey());
							capitalDataVOs.add(3, capitalDataVO);

						}
						
						//=========
						if(isHave(conAnoA,"金农宝") ){
							for(int i=0;i<capitalDataVOs.size();i++){
								if(capitalDataVOs.get(i).getArgtype().equals("金农宝")){
									
									CapitalDataVO capitalDataVO=new CapitalDataVO();
									capitalDataVO.setArgtype("金农宝");
									capitalDataVO.setCount(capitalDataVOs.get(i).getCount());
									capitalDataVO.setByTimer(entry.getKey());
									capitalDataVOs.add(4, capitalDataVO);
									break;
								}
								
							}
							

						}
						if(!isHave(conAnoA,"金农宝") ){
							CapitalDataVO capitalDataVO=new CapitalDataVO();
							capitalDataVO.setArgtype("金农宝 ");
							capitalDataVO.setCount("0");
							capitalDataVO.setByTimer(entry.getKey());
							capitalDataVOs.add(4, capitalDataVO);
							
						}
							
						entry.setValue(capitalDataVOs);
						listsss.add(capitalDataVOs);
					}
						
					}
				
				
			}
			
			
			
//			Object[] key = map.keySet().toArray(); 
//		     Arrays.sort(key);
//		     Map<String, List<CapitalDataVO>> map3=new HashMap<String, List<CapitalDataVO>>();
//		     for(int i=0;i<key.length;i++){
//		    	 for (Map.Entry<String, List<CapitalDataVO>> entry : map.entrySet()){
//		    		 
//		    		 if(key[i].equals(entry.getKey())){
//		    			 map3.put(entry.getKey(), entry.getValue());
//		    		 }
//		    		 
//		    	 }
//		     }
	        Map<String, List<CapitalDataVO>> map11 = new TreeMap<String, List<CapitalDataVO>>(

	                new Comparator<String>() {

	                    public int compare(String obj1, String obj2) {
	                        // 降序排序
	                        return obj1.compareTo(obj2);

	                    }

	                });
	        map11.putAll(map);
		return map11;
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
	
	public int getIndex(String type){
		int io = -1;
		for(int i=0;i<capitalDataVOs.size();i++){
		io=capitalDataVOs.indexOf(capitalDataVOs.get(i).getArgtype().equals(type));
			
		}
		return io;
	}
	@SuppressWarnings("unused")
	public boolean arrExit(String con,String arr[]){
		for(int i=0;i<arr.length;i++){
			if(arr[i].equals(con)){
				return true;
			}else{
				return false;
			}
		}
		
		return false;
	}
}
