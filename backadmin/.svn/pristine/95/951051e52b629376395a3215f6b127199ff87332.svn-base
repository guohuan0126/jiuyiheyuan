package com.duanrong.newadmin.controllhelper;

import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.annotation.adapters.XmlAdapter;

import util.map.MapConvertor;
import util.map.MapConvertor.MapEntry;

public class MapAdapter extends XmlAdapter<MapConvertor, Map<String, Object>> {

	@Override
	public MapConvertor marshal(Map<String, Object> map) throws Exception {
		return null;
	}

	@Override
	public Map<String, Object> unmarshal(MapConvertor map) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		for (MapEntry e : map.getEntries()) {
			result.put(e.getKey(), e.getValue());
		}
		return result;
	}

	public static Map<String, Object> getMap(MapConvertor mapConvertor) {
		try {
			return new MapAdapter().unmarshal(mapConvertor);
		} catch (Exception e) {
			return null;
		}
	}

}

/*使用方法说明
public void test() throws Exception {
	LoanWebService loanWebService = new LoanWebServiceService()
			.getLoanWebServicePort();
	MapConvertor mapConvertor = loanWebService
			.getLoanDetailDate("201409261719000001");

	Map<String, Object> map = MapAdapter.getMap(mapConvertor);
	String releaseDate = (String) map.get("releaseDate");
	String quotaDate = (String) map.get("quotaDate");
	String interestDate = (String) map.get("interestDate");
	String completeDate = (String) map.get("completeDate");
	
	System.out.println(releaseDate);
	System.out.println(quotaDate);
	System.out.println(interestDate);
	System.out.println(completeDate);

}
*/