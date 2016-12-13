package com.duanrong.cps.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import util.Log;

import com.alibaba.fastjson.JSONObject;
import com.duanrong.cps.business.activity.model.RedPacketDetail;
import com.duanrong.cps.business.activity.service.RedPacketDetailService;
import com.duanrong.cps.business.activity.service.RedPacketRuleService;


/**
 * 活动参加人Controller
 * @author Administrator
 *
 */
@Controller
public class RedPacketDetailController extends BaseController {
	
	@Autowired
	private RedPacketRuleService redPacketRuleService;
	@Autowired
	private RedPacketDetailService redPacketDetailService;

	/**
	 * 获取加息卷详细信息
	 * @return
	 */
	@RequestMapping(value="/redPacketDetail/getRedPacket")
	@ResponseBody
	public String getRedPacketDetailById(String redPacketId){
		String retStr = null;
		try {
			RedPacketDetail redPacketDetail = redPacketDetailService.get(Integer.parseInt(redPacketId));
			retStr = JSONObject.toJSONString(redPacketDetail);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return retStr;
	}
	/**
	 * 查询可以使用的加息卷
	 * @param redPacketId
	 * @return
	 */
	@RequestMapping(value="/redPacketDetail/toRedPacketUse/{investId}/{redpacketId}/{mobileNumber}")
	public String toRedPacketUse(@PathVariable("investId")String investId,@PathVariable("redpacketId")String redpacketId,
			@PathVariable("mobileNumber")String mobileNumber,Model m){
		//查询可使用加息卷
		List<RedPacketDetail> packetList = redPacketDetailService.getRedPacketUse(investId,mobileNumber,redpacketId);
		m.addAttribute("packetUseList", packetList);
		m.addAttribute("investId", investId);
		m.addAttribute("redpacketId", redpacketId);
		return "invest/redPacketUse";
	}
	
} 

