package com.matrix.forecast.UtilTool;

public class StringTool {

    /***
     * 将map.toString内容转为String
     * @param mapStr
     * @return
     */
    public static String GetMapStr2String(String mapStr){
        String resultStr=mapStr.substring(0,mapStr.length()-1);//去尾
        resultStr=resultStr.substring(1,resultStr.length());//去头
        resultStr=resultStr.replace(" ","");//将=变成:
        resultStr=resultStr.replace("=",":");//将=变成:
        resultStr=resultStr.replace(",","\n");//将,变成\n
        return resultStr;
    }
}
