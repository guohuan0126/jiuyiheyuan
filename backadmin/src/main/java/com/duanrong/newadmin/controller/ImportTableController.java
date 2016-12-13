package com.duanrong.newadmin.controller;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;

import util.MyStringUtils;
import base.pagehelper.PageInfo;

import com.duanrong.business.ruralfinance.model.AgricultureLoaninfo;
import com.duanrong.business.ruralfinance.service.AgricultureLoaninfoService;
import com.duanrong.newadmin.controllhelper.UserCookieHelper;
import com.duanrong.newadmin.model.UserCookie;
import com.duanrong.newadmin.utility.DateUtil;
import com.duanrong.newadmin.utility.IdGenerator;
import com.duanrong.newadmin.utility.RegexInput;
@Controller
public class ImportTableController {
	
	//借款人的数据
	
	@Resource
	private AgricultureLoaninfoService agricultureLoaninfoService;
	private Boolean jy=true;
	//数据错误
	private int sjErr=0;
	//合同编号重复 
	private int bhErr=0;
	//数据错误
	private List<AgricultureLoaninfo> contractDataErrList=new ArrayList<AgricultureLoaninfo>();
	private List<AgricultureLoaninfo> contractDataErrListAno=new ArrayList<AgricultureLoaninfo>();
	//合同编号重复
	private List<String> contractHTErrList=new ArrayList<String>();
	private List<String> contractHTErrListAno=new ArrayList<String>();
	//韭农贷在先息后本的时候已经提前还款，第二部分等额本息的就不用上传了
	//已经放过款的项目
	
	private List<String>  contractIsFK=new ArrayList<String>();
	//最后都正确保存所有
	private List<AgricultureLoaninfo> saveAll=new ArrayList<AgricultureLoaninfo>();
	private  InputStream stream1;
	
	//先判断excle的合同编号的重复数据
	private List<String> contractExcleCF=new ArrayList<String>();
	//1.导入Excel
	public void inputExcel(CommonsMultipartFile file,HttpServletRequest request,HttpServletResponse response,int type) throws Exception{
		InputStream input =  file.getInputStream();  
		ByteArrayOutputStream baos = new ByteArrayOutputStream();  
		byte[] buffer = new byte[1024];  
		int len;  
		while ((len = input.read(buffer)) > -1 ) {  
		    baos.write(buffer, 0, len);  
		}    
		baos.flush();                
		 stream1 = new ByteArrayInputStream(baos.toByteArray()); 
		jy=true;
		//先清空内容防止多个excle的增加
		contractDataErrList.clear();
		contractHTErrList.clear();
		contractExcleCF.clear();
		contractDataErrListAno.clear();
		contractHTErrListAno.clear();
		bhErr=0;
		sjErr=0;
		//根据type进入录入数据还是查看是否已放款的
		if(type==0){
			
		}else{
			impExcel(stream1,request,response,file);
		}
	}
	
	@ResponseBody
	@RequestMapping(value="/showData", method = RequestMethod.POST)
	public ModelAndView impData(@RequestParam("file") CommonsMultipartFile file,HttpServletRequest request,HttpServletResponse response,Model model) throws Exception{
		
        //1.导入
		inputExcel(file,request,response,1);
		//有数据错误
		if(sjErr==1||bhErr==1){
			//错误的
			for(int i=0;i<contractDataErrList.size();i++){
				if(contractDataErrList.get(i)!=null&&contractDataErrList.get(i).getId()!=null){
					contractDataErrListAno.add(contractDataErrList.get(i));
				} 
			}
			model.addAttribute("contractDataErrList", contractDataErrListAno);
			model.addAttribute("contractHTErrList", contractHTErrList);
		//没有数据错误
		}else{
			//上传oss
			agricultureLoaninfoService.uploadFile(file, request,response); 
			UserCookie user = UserCookieHelper.GetUserCookie(request, response);
	    	PageInfo  pageInfo = agricultureLoaninfoService.readUploadUser(1,20,"1");
	    	model.addAttribute("list", pageInfo.getResults());	    
	    	model.addAttribute("pageInfo", pageInfo);
	    	String type = request.getParameter("type");
	    	if(type==null){
	    		model.addAttribute("sc", "success");
	    	}
		}
		return new ModelAndView("ruralfinance/showImpData");
	}
	
	/** 
     * 导入Excel 
     * @param execelFile 
	 * @throws Exception 
     */  
	@SuppressWarnings("unchecked")
	public  boolean impExcel(InputStream execelFile,HttpServletRequest request,HttpServletResponse response,CommonsMultipartFile files) throws Exception{  
		  // 构造 Workbook 对象，execelFile 是传入文件路径(获得Excel工作区)  
		 Workbook book = null;  
		try {       
            String realName = files.getOriginalFilename();
 			String ext = realName.substring(realName.lastIndexOf(".")); 
 			  if(".xls".equals(ext)){  
 				// Excel 2003获取方法
 					book = new HSSFWorkbook(execelFile);  
 	            }else if(".xlsx".equals(ext)){  
 	            	// Excel 2007获取方法
 	    			book = new XSSFWorkbook(execelFile);
 	            }  		
            	// 读取表格的第一个sheet页  
                Sheet sheet = book.getSheetAt(0);  
            	// 定义 row、cell  
            	org.apache.poi.ss.usermodel.Row row;  
            	// 总共有多少行,从0开始  
                row = sheet.getRow(3);  
                // 总共有多少列,从0开始  
                @SuppressWarnings("unused")
				int totalCells = row.getLastCellNum() ;  
                
                org.apache.poi.ss.usermodel.Row rowSec=sheet.getRow(2);
          	  List<AgricultureLoaninfo> agricultureLoaninfos=new ArrayList<AgricultureLoaninfo>();
                //最后一行
          	AgricultureLoaninfo agricultureLoaninfo=null;
          	contractHTErrList.clear();
          	contractExcleCF.clear();
          	saveAll.clear();
          	//判断excle的合同编号重复
          	//获取所有的行
          	for(int f=3;f<sheet.getLastRowNum()+1;f++){
	          	Row rr=	sheet.getRow(f);
	          	if(rr==null){
	          		
	          	}else{
	          		Cell currentContent = rr.getCell(17);
	          		if(currentContent==null){
	          		}else{
	          			contractExcleCF.add(currentContent.toString());
	          		}
	          	}
          	}
          	
            for  ( int  i=0;i<contractExcleCF.size() ; i ++ )   {
            	 //跟数据库比对
          	  	Map map1=new HashMap<>();
    			map1.put("byType", "1");
    			map1.put("contractId",contractExcleCF.get(i));
            	AgricultureLoaninfo  AgricultureLoaninfo1  =	agricultureLoaninfoService.readByCondition(map1);
          	  if(AgricultureLoaninfo1==null){
          		  
          	  }else{
          		  contractHTErrList.add(contractExcleCF.get(i));
          	  }
                for  ( int  j =contractExcleCF.size()-1 ; j  >  i; j -- )   { 
                  if  (contractExcleCF.get(j).equals(contractExcleCF.get(i)))   { 
                	  contractHTErrList.add(contractExcleCF.get(i));
                  }else{
                	  System.out.println(contractExcleCF.get(i));
                	  //跟数据库比对
                	  Map map=new HashMap<>();
          			map.put("byType", "1");
          			map.put("contractId",contractExcleCF.get(i));
          			//查出来没有
          			AgricultureLoaninfo  AgricultureLoaninfo  =	agricultureLoaninfoService.readByCondition(map);
                	  if(AgricultureLoaninfo==null){
                		  
                	  }else{
                		  contractHTErrList.add(contractExcleCF.get(i));
                	  }
                  } 
                } 
              } 
          //显示所有的合同编号重复  不往下执行
          	if(contractHTErrList.size()>0){
          		bhErr=1;
          			Set<String>  set=new HashSet<String>(contractHTErrList);
          			contractHTErrList.clear();
          			contractHTErrList.addAll(set);
          		return false;
          	}else{
          	//跟数据库比较	
          		
          	}
                for(int y=3;y<sheet.getLastRowNum()+1;y++){
                //用户名	
                int	yhm=0;
            	//身份证号
                int	sfzh=0;
                //手机号
                int sjh=0;
                //银行卡号
                int	yhkh=0;
                //两个金额之和错误
                int	jeh=0;
                //放款日期
                int	fkrq=0;
                //银行名称
                int	yhmc=0;
                //银行分行 
                int	yhfh=0;
                //省份
                int	sf=0;
                //城市
                int	cs=0;
                //合同金额
                int	htje=0;
                //放款金额
                int	fkje=0;
                //服务费
                int	fwf=0;
                //借款期限
                int	jkqx=0;
                //利率
                int	ll=0;
                //综合利率
                int zhll=0;
                //核算部门
                int depart=0;
                //还款方式
                int hkfs=0;
                //项目类型
                int xmlx=0;
                jy=true;
                	row = sheet.getRow(y);
                	agricultureLoaninfo=new AgricultureLoaninfo();
            		
            	//获取列
            	for(int t=1;t<rowSec.getLastCellNum();t++){
            		if(row==null){
            			
            		}else{
            			String uuid = IdGenerator.randomUUID();
                		agricultureLoaninfo.setId(uuid);
                		
                		UserCookie user = UserCookieHelper.GetUserCookie(request, response);
                		agricultureLoaninfo.setUserId(user.getUserId());
                		agricultureLoaninfo.setCreateTime(new Date());
                		//agricultureLoaninfo.setRepayType("等额本息");
                		agricultureLoaninfo.setArea("");
                		agricultureLoaninfo.setOperateTime(new Date());
                		agricultureLoaninfo.setStatus("repay");
                		agricultureLoaninfo.setRemark1("excel");
                		agricultureLoaninfo.setRemark2("");
                		
                		//客户姓名
                		if(t==1&&isOK(1,row)){
                				agricultureLoaninfo.setName(row.getCell(1).toString());
                		}else if(t==1&&!isOK(1,row)) {
                			yhm=1;
            				jy=false; 
            				agricultureLoaninfo.setName("");
            				if(row.getCell(1)!=null){
            					agricultureLoaninfo.setName(row.getCell(1).toString());
            				}
                		}
                		//证件类型
                		if(t==2&&isOK(2,row)){
                			
                			if(row.getCell(2).toString() != null && row.getCell(2).toString().contains("身份证")){
                				agricultureLoaninfo.setTypeOfId("1");
                			}else if(row.getCell(2).toString() != null && row.getCell(2).toString().contains("护照")){
                				agricultureLoaninfo.setTypeOfId("0");
                			}
                			
                		}
                		//身份证号		
                		if(t==3&&isOK(3,row)){
                			
                			if(row.getCell(3).toString().length()!=18){
                				sfzh=1;
                				agricultureLoaninfo.setIdCard(row.getCell(3).toString());
                				jy=false;
                				System.out.println("身份证");
                			}else{
                				
                				agricultureLoaninfo.setIdCard(row.getCell(3).toString());
                    			
                    			Calendar ca=Calendar.getInstance();
                    			int year = ca.get(Calendar.YEAR);
                    			String sfz =  row.getCell(3).toString();
                    			if(sfz.length()==18){
                    				String nf =sfz.substring(6, 10);
                    				int nl=year-Integer.parseInt(nf);
                    				
                    				agricultureLoaninfo.setAge(nl);
                    				
                    				//0是男
                    				String sex=	sfz.substring(sfz.length()-2,sfz.length()-1);
                    				int se = Integer.parseInt(sex);
                    				if(se%2==0){
                    					//女
                    					agricultureLoaninfo.setSex("1");
                    				}else{
                    					//男
                    					agricultureLoaninfo.setSex("0");
                    				}
                    			}
                			}
                			
                		}else if(t==3&&!isOK(3,row)){
                			sfzh=1;
            				jy=false;
            				agricultureLoaninfo.setIdCard("");
                		}
                		
                		
                		//电话号码
                		if(t==4&&isOK(4,row)){
                			if(!RegexInput.isNumber(row.getCell(4).toString())||row.getCell(4).toString().length()!=11){
                				sjh=1;
                				jy=false;
                				System.out.println("电话号码");
                				agricultureLoaninfo.setMobileNumber(row.getCell(4).toString());
                			}else{
                				agricultureLoaninfo.setMobileNumber(row.getCell(4).toString());
                			}
                		}else if(t==4&&!isOK(4,row)){
                			sjh=1;
            				jy=false; 
                		}
                		//客户类型
                		if(t==5&&isOK(5,row)){
                			String custType = row.getCell(5).toString();
              				if(custType.equals("个人")){
              					agricultureLoaninfo.setCustomerType("0");
              				}else{
              					agricultureLoaninfo.setCustomerType("1");
              				}               				
                		}else if(t==5&&!isOK(5,row)){
                			jy=false;
                		}
                		
                		//银行名称
                		if(t==6&&isOK(6,row)){
                				agricultureLoaninfo.setBank(row.getCell(6).toString());
                			
                		}else if(t==6&&!isOK(6,row)){
                			yhmc=1;
            				jy=false; 
                		}
                		//银行卡号
                		if(t==7&&isOK(7,row)){
                			if(!RegexInput.isNumber(row.getCell(7).toString())){
                				yhkh=1;
                				jy=false;
                				System.out.println("银行卡号");
                				agricultureLoaninfo.setBankcard(row.getCell(7).toString());
                			}else{
                				agricultureLoaninfo.setBankcard(row.getCell(7).toString());
                			}
                		
                		}else if(t==7&&!isOK(7,row)){
                			yhkh=1;
            				jy=false;
            				agricultureLoaninfo.setBankcard("");
                		}
                		//银行分行
                		if(t==8&&isOK(8,row)){
                				agricultureLoaninfo.setBranchname(row.getCell(8).toString());
                		}else if(t==8&&!isOK(8,row)){
                			yhfh=1;
            				jy=false;
                		}
                		//省份
                		if(t==9&&isOK(9,row)){
            				agricultureLoaninfo.setProvince(row.getCell(9).toString());
            		     }else if(t==9&&!isOK(9,row)){
            				sf=1;
            				jy=false;
                		}
                		//城市
                		if(t==10&&isOK(10,row)){
                			agricultureLoaninfo.setCity(row.getCell(10).toString());
                		
                		}else if(t==10&&!isOK(10,row)){
                			cs=1;
            				jy=false;
            				agricultureLoaninfo.setCity("");
                		}
                		//详细地址
                		if(t==11&&isOK(11,row)){
                			agricultureLoaninfo.setAddress(row.getCell(11).toString());
                		} 
                		//婚姻状况
                		if(t==12&&isOK(12,row)){
                				String mar = row.getCell(12).toString();
                				if(mar.equals("未婚")){
                					agricultureLoaninfo.setMaritalStatus(0);
                				}else if(mar.equals("已婚")){
                					agricultureLoaninfo.setMaritalStatus(1);
                				} else if(mar.equals("离异")){
                					agricultureLoaninfo.setMaritalStatus(2);
                				}else if(mar.equals("丧偶")){
                					agricultureLoaninfo.setMaritalStatus(3);
                				}
                		}
                		//年收入
                		if(t==13&&isOK(13,row)){
                			agricultureLoaninfo.setAnnualIncome(row.getCell(13).toString());
                		}
                		//借款人用途
                		if(t==14&&isOK(14,row)){
                			agricultureLoaninfo.setLoanApplication(row.getCell(14).toString());
                		}
                		//还款来源
                		if(t==15&&isOK(15,row)){
                			agricultureLoaninfo.setRepaymentSource(row.getCell(15).toString());
                		}
                		
                		//备注
                		if(t==16&&isOK(16,row)){
                			agricultureLoaninfo.setRemark(row.getCell(16).toString());
                		}
                		//合同编号
                		if(t==17&&isOK(17,row)){
                			if(RegexInput.ishanzi(row.getCell(17).toString())){
                				jy=false;
                				System.out.println("合同编号");
                				agricultureLoaninfo.setContractId(row.getCell(17).toString());
                			}else{
                				agricultureLoaninfo.setContractId(row.getCell(17).toString());
                			}
                		}
                		//签约日期
                		if(t==18&&isOK(18,row)){
                			row.getCell(18).toString();
                			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
                			Date da = sdf.parse(row.getCell(18).toString());
                			agricultureLoaninfo.setSigneTime(da);
                		}
                		
                		//合同金额
                		if(t==19&&isOK(19,row)){
                			agricultureLoaninfo.setMoney(Double.parseDouble(row.getCell(19).toString()));
                			if(row.getCell(19)==null||row.getCell(20)==null||row.getCell(21)==null){
                			}
                			else if(StringUtils.isBlank(row.getCell(19).toString())||StringUtils.isBlank(row.getCell(20).toString())
                					||StringUtils.isBlank(row.getCell(21).toString())){
                				
                			}else{
                				Double d1 = Double.parseDouble(row.getCell(19).toString());
                				Double d2 = Double.parseDouble(row.getCell(20).toString());
                				Double d3 = Double.parseDouble(row.getCell(21).toString());
                				String repayType = row.getCell(32).toString();
                				if("等额本息".equals(repayType)){
	                				if(d1!=d2+d3){
	                					jeh=1;
	                					jy=false;
	                					System.out.println("合同金额");
	                					agricultureLoaninfo.setMoney(Double.parseDouble(row.getCell(19).toString()));
	                				}
                				}
                			}
                		}else if(t==19&&!isOK(19,row)){
                			htje=1;
            				jy=false;
                		}
                		//放款金额
                		if(t==20&&isOK(20,row)){
                			agricultureLoaninfo.setLoanMoney(Double.parseDouble(row.getCell(20).toString()));
                			
                		}else if(t==20&&!isOK(20,row)){
                			fkje=1;
            				jy=false;
                		}
                		//服务费合计
                		if(t==21&&isOK(21,row)){
                			agricultureLoaninfo.setServiceMoney(Double.parseDouble(row.getCell(21).toString()));
                		}else if(t==21&&!isOK(21,row)){
                			fwf=1;
            				jy=false;
                		}
                		//借款期限
                		if(t==22&&isOK(22,row)){
                			String loanTerm = row.getCell(22).toString().replace(".0", "");
                			agricultureLoaninfo.setLoanTerm(Integer.parseInt(loanTerm));
                		}else if(t==22&&!isOK(22,row)){
                			jkqx=1;
            				jy=false;
                		}
                		//利率
                		if(t==23&&isOK(23,row)){
                			agricultureLoaninfo.setRate(Double.parseDouble(row.getCell(23).toString()));
                		}else if(t==23&&!isOK(23,row)){
                			ll=1;
            				jy=false;
                		}
                		//放款时间
                		if(t==24&&isOK(24,row)){
                			
                			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
                			Date da = sdf.parse(row.getCell(24).toString());
                			agricultureLoaninfo.setGiveMoneyTime(da);
                		}else if(t==24&&!isOK(24,row)){
                			jy=false;
                			fkrq=1;
                		}
                		//大区
                		if(t==25&&isOK(25,row)){
                			agricultureLoaninfo.setBigArea(row.getCell(25).toString());
                		}
                		//营业部
                		if(t==26&&isOK(26,row)){
                			agricultureLoaninfo.setBusinessOffic(row.getCell(26).toString());
                		}
                		//客户经理
                		if(t==27&&isOK(27,row)){
                			agricultureLoaninfo.setCustomManger(row.getCell(27).toString());
                		}
                		//县审批人
                		if(t==28&&isOK(28,row)){
                			agricultureLoaninfo.setCountryApprover(row.getCell(28).toString());
                		}
                		//总部审批人
                		if(t==29&&isOK(29,row)){
                			agricultureLoaninfo.setAllApprover(row.getCell(29).toString());
                		}
                		//综合利率
                		Pattern pattern = Pattern.compile("\\d+\\.\\d+$|-\\d+\\.\\d+$");
                		if(t==30&&isOK(30,row)&&pattern.matcher(row.getCell(30).toString()).find()){
                			agricultureLoaninfo.setCompositeInteresRate(Float.parseFloat(row.getCell(30).toString()));
                		}
                		if(t==30){
                			if(!isOK(30,row)||!pattern.matcher(row.getCell(30).toString()).find()){
                				jy=false;
                				zhll=1;
                			}
                		}
                		//核算部门
                		if(t==31&&isOK(31,row)){
                				String department = row.getCell(31).toString();
                				if(department.equals("山水")){
                					agricultureLoaninfo.setAccountingDepartment(1);
                				}else if(department.equals("久亿")){
                					agricultureLoaninfo.setAccountingDepartment(2);
                				}
                		}else if(t==31&&!isOK(31,row)){
                			jy=false;
                			depart=1;
                		}
                		//还款方式
                		if(t==32&&isOK(32,row)){
                			String repayType = row.getCell(32).toString();
            				if(repayType.equals("等额本息")){
            					agricultureLoaninfo.setRepayType("等额本息");
            					agricultureLoaninfo.setPackingStatus("");
                        		agricultureLoaninfo.setForkStatus(0);
            				}else{
            					agricultureLoaninfo.setRepayType("先息后本");
            					agricultureLoaninfo.setPackingStatus("0");
            					agricultureLoaninfo.setForkStatus(0);
            				}
            		     }else if(t==32&&!isOK(32,row)){
            				hkfs=1;
            				jy=false;
                		}
                		//农贷类型
                		if(t==33&&isOK(33,row)){
                			agricultureLoaninfo.setAgriculturalType(row.getCell(33).toString());
            		     }else if(t==33&&!isOK(33,row)){
            				xmlx=1;
            				jy=false;
                		}
            		}
            		
                }
            	//数据有问题
            	if(!jy){
            		sjErr=1;
            	}
            	if(agricultureLoaninfo.getContractId()==null){
            		
            	}else{
            		
            		Map map=new HashMap<>();
        			map.put("byType", "1");
        			map.put("contractId",agricultureLoaninfo.getContractId());
        			AgricultureLoaninfo agricultureLoaninfo2 = 	agricultureLoaninfoService.readByCondition(map);
        			//查出没有数据往数据库录数据
        			if(agricultureLoaninfo2==null){
        				saveAll.add(agricultureLoaninfo);
        			}else{
        				//合同编号有重复
        				bhErr=1;
        			}
            	}
            	//一下列是出现错误的情况
            	if(yhm==1){
            		agricultureLoaninfo.setYhm(1);
            	}
            	if(sfzh==1){
            		agricultureLoaninfo.setSfzh(1);
            	}if(sjh==1){
            		agricultureLoaninfo.setSjh(1);
            	}if(yhkh==1){
            		agricultureLoaninfo.setYhkh(1);
            	}if(jeh==1){
            		agricultureLoaninfo.setJeh(1);
            	}if(fkrq==1){
            		agricultureLoaninfo.setFkrq(1);
            	}if(yhmc==1){
            		agricultureLoaninfo.setYhmc(1);
            	}if(yhfh==1){
            		agricultureLoaninfo.setYhfh(1);
            	}if(sf==1){
            		agricultureLoaninfo.setSf(1);
            	}if(cs==1){
            		agricultureLoaninfo.setCs(1);
            	}if(htje==1){
            		agricultureLoaninfo.setHtje(1);
            	}if(fkje==1){
            		agricultureLoaninfo.setFkje(1);
            	}if(fwf==1){
            		agricultureLoaninfo.setFwf(1);
            	}if(jkqx==1){
            		agricultureLoaninfo.setJkqx(1);
            	}if(ll==1){
            		agricultureLoaninfo.setLl(1);
            	}if(zhll==1){
            		agricultureLoaninfo.setZhll(1);
            	}if(depart==1){
            		agricultureLoaninfo.setDepart(1);
            	}if(hkfs==1){
            		agricultureLoaninfo.setHkfs(1);
            	}if(xmlx==1){
            		agricultureLoaninfo.setXmlx(1);
            	}

            	if(yhm==1||sfzh==1||sjh==1||yhkh==1||jeh==1||fkrq==1||yhmc==1||yhfh==1||sf==1||cs==1||
            			htje==1||fkje==1||fwf==1||jkqx==1||ll==1||zhll==1||depart==1||hkfs==1||xmlx==1
            			){
            		contractDataErrList.add(agricultureLoaninfo);
            	}
            	 
              }
                //最后判断是否全部是合法数据
                if(bhErr==1||sjErr==1){
                	return false;
                }else{
                	//判断excle的第一条以后的数据重复
                	if(bhErr==1){
                		return false;
                	}else{
                		for(int i=0;i<saveAll.size();i++){
                			if(saveAll.get(i).getBranchname()==null){
                    			saveAll.get(i).setBranchname("");
                    		}
                			//如果是惠牧贷，就拆分两条项目信息前3期先息后本，后3期等额本息
                			if(saveAll.get(i).getAgriculturalType()!=null && "惠牧贷".equals(saveAll.get(i).getAgriculturalType())){
                				if(saveAll.get(i).getLoanTerm()==6){
                					String ContractId=saveAll.get(i).getContractId();
                					String ids=saveAll.get(i).getId();
                					for (int j = 0; j < 2; j++) {
										if(j==0){
										saveAll.get(i).setRepayType("先息后本");	
										}if(j==1){
										saveAll.get(i).setRepayType("等额本息");
										saveAll.get(i).setGiveMoneyTime(DateUtil.addMonth(saveAll.get(i).getGiveMoneyTime(),3));								
										}
										int num=j+1;
										saveAll.get(i).setId(ids+num);//主键
										saveAll.get(i).setContractId(ContractId+"_"+num);//合同编号分为1，2两期
										saveAll.get(i).setLoanTerm(3);//借款期限一共6期，前3期先息后3期等额本息
									agricultureLoaninfoService.saveAgricultureLoaninfo(saveAll.get(i),"1");		
									}
                				}
                			}else{
                			agricultureLoaninfoService.saveAgricultureLoaninfo(saveAll.get(i),"1");	
                			}
                		}
                		return true;
                	}
                }
                
        } catch (IOException e) {  
            e.printStackTrace();  
        }
		return false;  
    }
	
	public boolean isOK(int t,Row row){
		return row!=null&&row.getCell(t)!=null&&StringUtils.isNotBlank(row.getCell(t).toString());
	}
    
    @RequestMapping(value="/agricultural/impData")
    public ModelAndView impData(Model model,HttpServletRequest request,HttpServletResponse response){
    	 
    	int pageNo = 1;
		int pageSize = 20;
		if(MyStringUtils.isNumeric(request.getParameter("pageNo"))){
			pageNo = Integer.parseInt(request.getParameter("pageNo"));
		}
    	model.addAttribute("bl", 1);
    	Map<String,String> map=new HashMap<String,String>();
    	map.put("pageNo", String.valueOf(pageNo));
    	map.put("pageSize", String.valueOf(pageSize));
    	PageInfo  pageInfo =	agricultureLoaninfoService.readUploadUser(pageNo,pageSize,"1");
    	model.addAttribute("list", pageInfo.getResults());
    	model.addAttribute("pageInfo", pageInfo);
    	
    	return new ModelAndView("ruralfinance/showImpData");
    }
    /**
     * 下载excel
     * @param key
     * @return
     */
    @ResponseBody
    @RequestMapping(value="/downExcle")
    public ModelAndView downExcle(@RequestParam("key") String key){
    	
    	return new ModelAndView("ruralfinance/showImpData");
    }
    
    /**
     * 显示数据
     * @return
     */
    @RequestMapping(value="/showData", method = RequestMethod.GET)
    public String show(){
    	
    	return "redirect:/agricultural/impData?type=1";
    }

    /**
     * 查看合同编号之前是否放过款
     */
    @ResponseBody
    @RequestMapping(value="isAdvance",method = RequestMethod.POST)
    public ModelAndView  isAdvance(@RequestParam("file") CommonsMultipartFile file,
    		HttpServletRequest request,HttpServletResponse response,Model model){
    	contractIsFK.clear();
            // 读取表格的第一个sheet页  
			Sheet sheet = null;
			try {
				inputExcel(file,request,response,0);
				// 构造 Workbook 对象，execelFile 是传入文件路径(获得Excel工作区)  
				 Workbook book = null;  
				try {  
				    // Excel 2007获取方法  
				    book = new XSSFWorkbook(stream1);  
				} catch (Exception ex) {  
				    // Excel 2003获取方法  
				    book = new HSSFWorkbook(stream1);  
				}   
					sheet = book.getSheetAt(0);  
					// 定义 row、cell  
					org.apache.poi.ss.usermodel.Row row;  
					// 总共有多少行,从0开始  
				    row = sheet.getRow(3);
				    
				    
			} catch (IOException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}  
			
		 Set<String> contractIsAdvanceSet=new HashSet<String>();
	    	for(int f=3;f<sheet.getLastRowNum()+1;f++){
	          	Row rr=	sheet.getRow(f);
	          	if(rr==null){
	          		
	          	}else{
	          		Cell currentContent = rr.getCell(17);
	          		if(currentContent==null){
	          		}else{
	          			if(!currentContent.toString().trim().equals("")){
	          				
	          				contractIsAdvanceSet.add(currentContent.toString());
	          			}
	          		}
	          	}
	      	}
	    	List<String> contractIsAdvanceList = new ArrayList<String>(contractIsAdvanceSet);
	    	List<String> contractIdList =  agricultureLoaninfoService.readAllContractId();
	    	
	    	for(int i=0;i<contractIsAdvanceList.size();i++){
	    		
	    		for(int s=0;s<contractIdList.size();s++){
	    			
	    			if(contractIsAdvanceList.get(i).equals(contractIdList.get(s))){
	    				
	    				contractIsFK.add(contractIsAdvanceList.get(i));
	    			}
	    			
	    		}
	    	}
	    	model.addAttribute("contractIsFK", contractIsFK);
	    	model.addAttribute("type", 1);
	    	
    	return new ModelAndView("ruralfinance/showImpData");
    }
    
    /**
     * 显示数据
     * @return
     */
    @RequestMapping(value="/isAdvance", method = RequestMethod.GET)
    public String isAdvance(){
    	
    	return "redirect:/agricultural/impData?type=1";
    }
    
}