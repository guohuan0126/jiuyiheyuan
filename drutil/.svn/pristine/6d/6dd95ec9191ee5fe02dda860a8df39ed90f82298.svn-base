package com.duanrong.util.json;

import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializeFilter;
import com.alibaba.fastjson.serializer.SerializerFeature;

/**
 * FastJson 序列化工具
 * 使用此类序列化的数据只能通过此类反序列化
 * @author xiao
 * 
 */
public final class FastJsonUtil {

	/**
	 * 序列化参数
	 */
	private static final SerializerFeature[] features = {
			SerializerFeature.WriteClassName,
			SerializerFeature.WriteMapNullValue,
			SerializerFeature.WriteNullBooleanAsFalse,
			SerializerFeature.WriteNullStringAsEmpty,
			SerializerFeature.WriteNullListAsEmpty,
			SerializerFeature.DisableCircularReferenceDetect,
			SerializerFeature.WriteNullNumberAsZero };


	/**
	 * 类型转换 过滤器(处理null的情况)
	 */
	private static final SerializeFilter[] filters = { new ValueToEmptyFilter() };
	
	/**
	 * 对象转换成json 支持list,map,array
	 * 
	 * @param obj
	 * @return
	 */
	public static String objToJson(Object obj) {
		return JSON.toJSONString(obj, features);
	}
	
	/**
	 * 对象转换成json 支持list,map,array
	 * 
	 * @param obj
	 * @return
	 */
	public static String objToJsonWriteNullToEmpty(Object obj) {
		return JSON.toJSONString(obj, filters, features);
	}

	/**
	 * json 转换成对象
	 * 
	 * @param json
	 * @param clazz
	 * @return
	 */
	public static <T> T jsonToObj(String json, Class<T> clazz) {
		return JSON.parseObject(json, clazz);
	}
	
	/**
	 * json 转换成对象
	 * 
	 * @param json
	 * @param clazz
	 * @return
	 */
	public static <T> List<T> jsonToArray(String json, Class<T> clazz) {
		return JSON.parseArray(json, clazz);
	}

}
