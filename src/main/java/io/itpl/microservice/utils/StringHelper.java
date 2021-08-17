package io.itpl.microservice.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringHelper {

    /**
     *  Check whether the provided string contains the letters only.
     * @param value
     * @return
     */
    public static boolean isLettersOnly(String value){
        Pattern pattern = Pattern.compile(new String ("^[a-zA-Z\\s]*$"));
        Matcher matcher = pattern.matcher(value);
        return  matcher.matches();
    }

    /**
     *
     * @param value
     * @return
     */
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

    /**
     * Generate String with random digits and specified length
     * @param length no's of digits in the string
     * @return string with random numbers.
     */
    public static String generateRandomString(int length) {
        char [] digits = new char []{
                '1','2','3','4','5','6','7','8','9',
                'A','B','C','D','E','F','G','H','J',
                'K','L','M','N','P','Q','R','S','T',
                'U','V','W','X','Y','Z'
        };
        int max = digits.length -1;
        char[] output = new char[length];

        for(int i=0;i<length;i++) {

            double rand = Math.random();
            long index = Math.round(rand * max);

            // Lets make sure index < max value,
            // If index > max, then regenerate the value.
            // Maximum 4 iteration.
            int counter = 0;
            while(index>max || counter < 4) {
                index = Math.round(rand * max);
                counter++;
            }
            // Failed to generate valid index even after 4 iteration.
            // Setting it to max value.
            if(index > max) {
                index = max;
            }
            output[i]=digits[(int) index];
        }

        return new String(output);
    }
    public static void main234(String []args){
        String caption = "Body Color64";
        boolean valid = isLettersOnly(caption);
        System.out.println("Valid:"+valid);
        System.out.println(caption +" >> "+toCamelCase(caption));
    }

}
