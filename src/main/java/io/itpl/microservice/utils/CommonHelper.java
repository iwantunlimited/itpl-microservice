package io.itpl.microservice.utils;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Strings;
import io.itpl.microservice.exceptions.ApiException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.awt.*;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.*;


public class CommonHelper {

	private static final Logger logger = LoggerFactory.getLogger(CommonHelper.class);
	/**
	 *  LC RNG Function Constant (i.e. a)
	 */
	private static final BigInteger preA = new BigInteger("3781927463263421");
	/**
	 *  LC RNG Function Constant (i.e. c)
	 */
	private static final BigInteger preC = new BigInteger("2113323684682149");

	public static final String ENCRYPTION_NONE = "NONE";
	public static final String ENCRYPTION_AES128 = "AES128";
	public static final String SYSTEM_DEFAULT_KEY = "!WantUnlimited.SNS@2020";

	/**
	 * Verify the String value for @Notnull check.
	 * @param input
	 * @return
	 */
	public static boolean isNull(String input) {
		return input==null || input.isEmpty();
	}

	/**
	 *  Verify whether requested collection is usable or not.
	 * @param input
	 * @return
	 */
	public static boolean isNull(Collection input) {
		return input==null || input.isEmpty();
	}

	/**
	 *  Validate String input before conversion to Integer to prevent NumberFormatException.
	 * @param input
	 * @return
	 */
	public static boolean isInteger(String input) {
		try {
			int value =  Integer.parseInt(input);
			return true;
		}catch(NumberFormatException e) {
			return false;
		}
	}

	/**
	 * Validate String input before conversion to Double to prevent NumberFormatException.
	 * @param input
	 * @return
	 */
	public static boolean isDecimal(String input) {
		try {
			double value =  Double.parseDouble(input);
			return true;
		}catch(NumberFormatException e) {
			return false;
		}
	}

	/**
	 *  Transform the JSON value to Map<String,Object>.
	 * @param obj
	 * @return
	 */
	public Map<String,Object> jsonToMap(JsonNode obj){
		ObjectMapper mapper = new ObjectMapper();
		Map<String, Object> result = mapper.convertValue(obj, new TypeReference<Map<String, Object>>(){});
		return result;
	}

	/**
	 *  Dynamically build the mongoDB Sort object based on String input with comma separated field names.
	 * @param feed
	 * @return
	 */
	public static Sort sortBy(String feed) {
		// Empty List of Fields
		List<String> sortingByFields = new ArrayList<String>();
		// Empty List of Sort Orders
		List<Sort.Order> sortOrder = new ArrayList<Sort.Order>();
		if(feed==null || feed.isEmpty()) {
			return null;
		}
		// We must adjust feed content to be usable in terms of parsing.
		// Remove the noise from feed (i.e. unwanted extra spaces)
		// Generalise the tokens (i.e. replace tabs with space etc.)
		feed = feed.trim();
		feed = feed.replace("  ", " ")
				.replace("  ", " ")
				.replace("/t", " ")
				.replace(":", " ");
		// It is expected that feed will include comma(",") separated list of the fields.
		// However in case of single field, the "," will not exist
		if(feed.contains(",")) {
			String []fields = feed.split(",");
			for(String field:fields)
				sortingByFields.add(field);
		}else {
			sortingByFields.add(feed);
		}

		// Now All fields have been identified and added to List.
		// Build Sort.Order for each field and add it to sortOrder Collection.
		for(String field:sortingByFields) {
			if(field.contains(" ")) {
				String [] tokens = field.split(" ");
				if(tokens[1].toLowerCase().contains("desc")) {
					sortOrder.add(new Sort.Order(Sort.Direction.DESC, tokens[0]));
				}else {
					sortOrder.add(new Sort.Order(Sort.Direction.ASC, tokens[0]));
				}
			}else {
				sortOrder.add(new Sort.Order(Sort.Direction.ASC, field));
			}
		}
		return Sort.by(sortOrder);
	}



	/**
	 *  Create LC RNG (Linear Congruential random number generator) Hash based on given Numeric Seed value and no's of digit length.
	 *  For example,
	 *  	Output Value = [a * (seedValue) + c] mod (10 ^ length)
	 *  	wherein, input = a and c are constants and seedValue and  length are input for hashing.
	 *
	 * @param seedValue input number as a seed value
	 * @param length expected length of the hashed value.
	 * @return String of given length generated using LC RNG. All Digits will be numeric. In case LC function produce a small value
	 * the function will add "0" paddings to the value to ensure it returns exact same length as requested in input.
	 */
	public static String random(long seedValue, int length) {


		long lMod = (long) Math.pow(10,length);

		//long lC = (long) Math.round((0.5 - (Math.sqrt(3)/6)) * lMod);
		BigInteger preM = new BigInteger(String.valueOf(lMod));
		BigInteger x = new BigInteger(seedValue+"");
		BigInteger y = x.multiply(preA).add(preC);
		String res = y.mod(preM).toString();

		while(res.length() < length) res = "0"+res; // supply leading 0s to small numbers.
		return res;
	}

	/**
	 *  Transform a integer number to a String value in desired length with zero("0") padding.
	 * @param seqId
	 * @param length
	 * @return
	 */
	public static String withPadding(long seqId, int length) {
		String res = ""+seqId;
		while(res.length() < length) res = "0"+res;
		return res;
	}

	/**
	 * UrlEncoding of a String value.
	 * @param input
	 * @return
	 */
	public static String urlEncode(String input) {
		try {
			return URLEncoder.encode(input, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return input;
	}

	/**
	 *  Transform Hex Value to java.awt.Color object.
	 * @param colorStr
	 * @return
	 */
	public static Color hex2Rgb(String colorStr) {
		return new Color(
				Integer.valueOf( colorStr.substring( 1, 3 ), 16 ),
				Integer.valueOf( colorStr.substring( 3, 5 ), 16 ),
				Integer.valueOf( colorStr.substring( 5, 7 ), 16 ) );
	}

	/**
	 * find index of requested value from existing String array.
	 * @param source
	 * @param value
	 * @return
	 */
	public static int mapIndex(String[] source, String value) {

		int index=0;
		for(String item:source) {
			if(item.equals(value))
				return index;
			index++;
		}
		throw new ApiException("Requested element not found in Source");
	}





	/**
	 *  Masking the String value with keeping limited last digits visible.
	 * @param value
	 * @param lastVisible
	 * @return
	 */
	public static String mask(String value,int lastVisible) {
		StringBuffer buffer = new StringBuffer();
		int length = value.length();
		int maskingIndex = length -1;
		if(lastVisible<length && lastVisible>0) {
			maskingIndex = (length - lastVisible)-1;
		}
		for(int i=0;i<length;i++) {
			buffer.append(i<=maskingIndex?"*":value.charAt(i));
		}
		return buffer.toString();

	}

	/**
	 * Encryption of the input String using requested method and key.
	 * @param encryptionMethod
	 * @param content
	 * @param key
	 * @return
	 */
	public static String encrypt(String encryptionMethod, String content,String key) {
		switch(encryptionMethod) {
			case CommonHelper.ENCRYPTION_NONE:
				return content;
			case CommonHelper.ENCRYPTION_AES128:
				return AES128.encrypt(content, key);
		}
		throw new ApiException("Failed to decrypt content using "+encryptionMethod);
	}

	/**
	 * Decryption of the input String using requested method and key.
	 * @param encryptionMethod
	 * @param content
	 * @param key
	 * @return
	 */
	public static String decrypt(String encryptionMethod, String content,String key) {
		switch(encryptionMethod) {
			case CommonHelper.ENCRYPTION_NONE:
				return content;
			case CommonHelper.ENCRYPTION_AES128:
				return AES128.decrypt(content, key);
		}
		throw new ApiException("Failed to decrypt content using "+encryptionMethod);
	}

	/**
	 * Transform a Single String value to List<String>  object.
	 * @param value
	 * @return
	 */
	public static List<String> asList(String value) {
		List<String> items = new ArrayList<>();
		items.add(value);
		return items;
	}

	/**
	 * Transform a Single Object value to List<T>  object.
	 * @param value
	 * @param t
	 * @param <T>
	 * @return
	 */
	public static <T> List<T> asList(Object value,Class<T> t) {
		List<T> items = new ArrayList<>();
		items.add((T) value);
		return items;
	}

	/**
	 * MongoDb query helper method for building selection dynamically based on comma separated String value.
	 * @param query
	 * @param fields comma separated fields with ascending or descending.
	 * @param DEFAULT_FIELDS In case value of fields is null or empty, the array will be used as fields list.
	 * @return
	 */
	public static Query selectFields(Query query, String fields,String [] DEFAULT_FIELDS) {
		String [] selectedFields = null;
		boolean allFields = false;
		if(!Strings.isNullOrEmpty(fields)) {
			if(fields.contains(",")) {
				selectedFields = fields.split(",");
			}else {
				if(fields.equalsIgnoreCase("all")) {
					allFields = true;
				}else {
					selectedFields = DEFAULT_FIELDS;
				}
			}
		}else {
			selectedFields = DEFAULT_FIELDS;

		}
		if(!allFields) {
			for(String key:selectedFields) {
				logger.trace("fields included : " +key);
				query.fields().include(key);
			}
		}
		return query;
	}

	/**
	 * MongoDb Query helper method for findById query.
	 * @param field
	 * @param value
	 * @return
	 */
	public static Query eq(String field,Object value) {
		return new Query(Criteria.where(field).is(value));
	}

	/**
	 * Generate MD5 hash based on given two input parameters.
	 * @param clientId
	 * @param transactionId
	 * @return
	 */
	public static String signatureKey(String clientId,String transactionId){
		String passwordToHash = clientId + transactionId;
		String generatedPassword = null;
		try {
			MessageDigest sha512 = MessageDigest.getInstance("MD5");

			byte[] bytes = sha512.digest(passwordToHash.getBytes(StandardCharsets.UTF_8));
			StringBuilder sb = new StringBuilder();
			for(int i=0; i< bytes.length ;i++){
				sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
			}
			generatedPassword = sb.toString();

			return generatedPassword;
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 *  Generate sid (SignatureId) value from the given timestamp.
	 */
	public static String sid(long timestamp,String clientId){
		String rid = random(timestamp,16);
		String sid = getMd5(clientId+rid);
		logger.trace("Local SID Verification>> tid:{}",timestamp);
		logger.trace("Local SID Verification>> rid:{}",rid);
		logger.trace("Local SID Verification>> sid:{}",sid);
		return sid;
	}

	/**
	 * Generate sid by converting String transactionID to the numeric timestamp.
	 * @param transactionId
	 * @param clientId
	 * @return
	 */
	public static String sid(String transactionId,String clientId){
		long timestamp = System.currentTimeMillis();
		try {
			timestamp = Long.parseLong(transactionId);
		}catch(NumberFormatException ne) {
			// No Need to create any noise.
		}
		return  sid(timestamp,clientId);
	}

	/**
	 * Transform a Single String value to Map<String,String>.
	 * @param key
	 * @param value
	 * @return
	 */
	public static Map<String,String> asMap(String key,String value){
		Map<String,String> map = new HashMap<>();
		map.put(key,value);
		return map;
	}
	public static String getMd5(String input)
	{
		try {

			// Static getInstance method is called with hashing MD5
			MessageDigest md = MessageDigest.getInstance("MD5");

			// digest() method is called to calculate message digest
			//  of an input digest() return array of byte
			byte[] messageDigest = md.digest(input.getBytes(StandardCharsets.UTF_8));

			// Convert byte array into signum representation
			BigInteger no = new BigInteger(1, messageDigest);

			// Convert message digest into hex value
			String hashtext = no.toString(16);
			while (hashtext.length() < 32) {
				hashtext = "0" + hashtext;
			}
			return hashtext;
		}

		// For specifying wrong message digest algorithms
		catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
	}

	public static void main(String args[]) {
		String clientId = "SS7052021-001";
		long time = System.currentTimeMillis() ;
		String random = random(time,16);
		String result = signatureKey(clientId,random);
		System.out.println("time:"+time);
		System.out.println("random:"+random);
		System.out.println(("Signature A:"+result));

		System.out.println(("Signature B:"+sid(time,clientId)));

		/*
		c43232daa72853e8f182fbca3fd109af3c4562ee3eab1969c38f7b59f838a8157805fc8a30567cd10164871464ff59d3b8449a5d419da73bd338a5df3361b7c6
		 */
	}


}

