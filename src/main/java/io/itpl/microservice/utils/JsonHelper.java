package io.itpl.microservice.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Strings;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class JsonHelper {

    /**
     *
     * @param path Path of the Json element, i.e. "seller.id"
     * @param source JsonNode Object from which text needs to be extracted.
     * @return text value of the requested Json element path.
     */
    public static String readPathAsText(String path,JsonNode source){
        /**
         * i.e. "seller.id"
         */
        if(Strings.isNullOrEmpty(path)){
            return null;
        }
        boolean nested = path.contains(".");
        JsonNode selectedElement = null;
        if(!nested){

            selectedElement = source.path(path);
        }else{
            String [] childs = path.split("\\.");
            if(childs==null || childs.length<=0){
                return null;
            }
            int depth = childs.length;
            // i.e. 2 in case path = "seller.id"
            JsonNode child = null;
            for(int i=0;i<depth;i++){
                String childPath = childs[i]; //i.e. 0,1
                if(child == null){
                    child = source.path(childs[i]);
                }else{
                    child = child.path(childs[i]);
                }
            }
            selectedElement = child;
        }
        if(selectedElement.isObject()){
            return "[Object]";
        }else{
            String res = null;
            if(selectedElement.isNull()){
                res = "[null]";
            }
            if(selectedElement.isEmpty()){
                res = "[empty]";
            }
            if(selectedElement.isTextual()){
                res = selectedElement.textValue();
            }
            if(selectedElement.isBoolean()){
                res = String.valueOf(selectedElement.booleanValue());
            }
            if(selectedElement.isInt()){
                res = String.valueOf(selectedElement.intValue());
            }
            if(selectedElement.isLong()){
                res = String.valueOf(selectedElement.longValue());
            }
            if(selectedElement.isDouble()){
                res = String.valueOf(selectedElement.doubleValue());
            }
            if(selectedElement.isFloat()){
                res = String.valueOf(selectedElement.floatValue());
            }
            if(selectedElement.isArray()){
                res = "[Array]";
            }
            return res;
        }

    }
    public static void _main(String []args){
        Map<String,Object> content = new HashMap<>();
        Map<String,Object> data = new HashMap<>();
        Map<String,Object> user = new HashMap<>();

        user.put("id","1234567890");
        String name = (user.isEmpty())?null:"";
        user.put("name",name);
        user.put("active",Boolean.FALSE);
        user.put("age",new Integer(36));
        user.put("weight",new Float(70.50f));
        user.put("mobile",new Long(123456789012345l));
        user.put("tags", Arrays.asList(new String []{"one","two"}));
        data.put("user",user);
        content.put("data",data);
        ObjectMapper mapper = new ObjectMapper();
        JsonNode source = mapper.convertValue(content,JsonNode.class);
        String path = "data.user.age";
        String res = readPathAsText(path,source);
        System.out.println("Res:"+res);
    }
}
