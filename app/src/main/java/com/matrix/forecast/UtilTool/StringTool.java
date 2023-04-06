package com.matrix.forecast.UtilTool;

import java.util.ArrayList;
import java.util.Arrays;

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

    /***
     * 去除String数组中的空值
     */
    public static String[] deleteArrayNull(String[] mString) {
        String[] strArr = mString;

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

    /**
     * 中文与数字关系
     * @param mString
     * @return
     */
    public static String FindCh2Num(String[] mString){
        String temp,result ="";
        //String[] result_list=null;
        for(int i=0;i<mString.length;i++){
            switch (mString[i]){
                case "鼠":
                    temp="4 16 28 40 ";
                    result+=temp;
                    break;
                case "牛":
                    temp="3 15 27 39 ";
                    result+=temp;
                    break;
                case "虎":
                    temp="2 14 26 38 ";
                    result+=temp;
                    break;
                case "兔":
                    temp="1 13 25 37 49 ";
                    result+=temp;
                    break;
                case "龙":
                    temp="12 24 36 48 ";
                    result+=temp;
                    break;
                case "蛇":
                    temp="11 23 35 47 ";
                    result+=temp;
                    break;
                case "马":
                    temp="10 22 34 46 ";
                    result+=temp;
                    break;
                case "羊":
                    temp="9 21 33 45 ";
                    result+=temp;
                    break;
                case "猴":
                    temp="8 20 32 44 ";
                    result+=temp;
                    break;
                case "鸡":
                    temp="7 19 31 43 ";
                    result+=temp;
                    break;
                case "狗":
                    temp="6 18 30 42 ";
                    result+=temp;
                    break;
                case "猪":
                    temp="5 17 29 41 ";
                    result+=temp;
                    break;
            }
        }

        //result_list=result.split(" ");
        return result;
    }
}
