package io.itpl.microservice.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringHelper {

    public static boolean isLettersOnly(String value){
        Pattern pattern = Pattern.compile(new String ("^[a-zA-Z\\s]*$"));
        Matcher matcher = pattern.matcher(value);
        return  matcher.matches();
    }

    public static String toCamelCase(String value){
        String key = null;
        if(value.contains(" ")){
            String [] tokens = value.split(" ");
            StringBuffer keyBuffer = new StringBuffer();
            int index = 0;
            for(String token:tokens){
                token = token.toLowerCase();
                if(index > 0){
                    String first = token.charAt(0) + "";
                    System.out.println(index + ">>"+first+ ">> " + first.toUpperCase() + ">> "+token.substring(1));
                    token = first.toUpperCase() + token.substring(1);
                }else{
                    System.out.println(index + ">> "+token);
                }
                keyBuffer.append(token);
                index++;
            }
            key = keyBuffer.toString();
        }else{
            key = value.toLowerCase();
        }
        return key;
    }

    /**
     *
     * @param input Any String digits
     * @return transform given string to xxxx-xxxx-xxxx-xxxx format.
     */
    public static String toCardNumber(String input){
        StringBuffer buffer = new StringBuffer();
        int counter = 1;
        for(int index=0;index<16;index++){
            if(index < input.length())
                buffer.append(input.charAt(index));
            else
                buffer.append(""+ (int) Math.round(Math.random()*9) );
            if(counter % 4 == 0 && counter < 15) buffer.append("-") ;
            counter++;
        }
        return buffer.toString();
    }

    public static void main234(String []args){
        String caption = "Body Color64";
        boolean valid = isLettersOnly(caption);
        System.out.println("Valid:"+valid);
        System.out.println(caption +" >> "+toCamelCase(caption));
    }

}
