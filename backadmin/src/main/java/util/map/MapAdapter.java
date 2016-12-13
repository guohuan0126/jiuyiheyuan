package util.map;

import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 * @Description: Map适配器，可以调用方法获取Map对象
 * @Author: 林志明
 * @CreateDate: Jan 9, 2015
 */
public class MapAdapter extends XmlAdapter<MapConvertor, Map<String, Object>> {

	@Override
	public MapConvertor marshal(Map<String, Object> map) throws Exception {
		MapConvertor convertor = new MapConvertor();
		for (Map.Entry<String, Object> entry : map.entrySet()) {
			MapConvertor.MapEntry e = new MapConvertor.MapEntry(entry);
			convertor.addEntry(e);
		}
		return convertor;
	}

	@Override
	public Map<String, Object> unmarshal(MapConvertor map) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		for (MapConvertor.MapEntry e : map.getEntries()) {
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