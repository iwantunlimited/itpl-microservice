package io.itpl.microservice.utils;

import com.google.common.base.Strings;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *  Reading data from any Map<> needs lots of check points.
 *  For example,
 *   1-Check if requested key exists of not.
 *   2-Whether value of null or not-null.
 *   3-Casting of value to required type.
 *
 *   This class contains an utility method for following types of values.
 *   - String,
 *   - Double,
 *   - List<T>
 */
public class MapUtils {
	/**
	 *
	 * @param source
	 * @param key
	 * @return
	 */
	public static String getString(Map<String, Object> source, String key) {
		if(source == null || source.isEmpty() || Strings.isNullOrEmpty(key)) {
			return "";
		}
		if(source.containsKey(key)) {
			Object value = source.get(key);
			if(value!= null && value instanceof String) {
				return (String) value;
			}else {
				return "";
			}
		}else {
			return "";
		}
	}

	/**
	 *
	 * @param source
	 * @param key
	 * @return
	 */
	public static Integer getInteger(Map<String, Object> source, String key) {
		if(source == null || source.isEmpty() || Strings.isNullOrEmpty(key)) {
			return 0;
		}
		if(source.containsKey(key)) {
			Object value = source.get(key);
			if(value!= null && value instanceof Integer) {
				return (Integer) value;
			}else {
				return 0;
			}
		}else {
			return 0;
		}
	}

	/**
	 *
	 * @param source
	 * @param key
	 * @return
	 */
	public static Double getDouble(Map<String, Object> source, String key) {
		if(source == null || source.isEmpty() || Strings.isNullOrEmpty(key)) {
			return 0d;
		}
		if(source.containsKey(key)) {
			Object value = source.get(key);
			if(value!= null && value instanceof Double) {
				return (Double) value;
			}else {
				return 0d;
			}
		}else {
			return 0d;
		}
	}

	/**
	 *
	 * @param source source map object from which value needs to be extracted.
	 * @param key key of the element.
	 * @param type Class of content type in List.
	 * @param <T> Class Object
	 * @return
	 */
	public static <T> List<T> getList(Map<String, Object> source, String key,Class<T> type) {
		List<T> empty = new ArrayList<>();
		if(source == null || source.isEmpty() || Strings.isNullOrEmpty(key)) {
			return empty;
		}
		if(source.containsKey(key)) {
			Object value = source.get(key);
			if(value!= null && value instanceof List) {
				return (List<T>) value;
			}else {
				return empty;
			}
		}else {
			return empty;
		}
	}

}
