package com.matrix.forecast.UtilTool;

import java.util.ArrayList;

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

    public static String[] deleteArrayNull(String string[]) {
        String strArr[] = string;

        //step1:定义一个list列表，并循环赋值
        ArrayList<String> strList = new ArrayList<String>();
        for (int i = 0; i < strArr.length; i++) {
            strList.add(strArr[i]);
        }
        //step2:删除list列表中所有的空值
        while (strList.remove(null));
        while (strList.remove(""));

        //step3:把list列表转换给一个新定义的中间数组，并赋值给它
        String strArrLast[] = strList.toArray(new String[strList.size()]);

        return strArrLast;
    }
}
