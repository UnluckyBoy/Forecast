package com.matrix.forecast.ui.Fragment;

import static com.matrix.forecast.UtilTool.StringTool.GetMapStr2String;

import android.os.Build;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.matrix.forecast.R;
import com.matrix.forecast.UtilTool.FileUnit;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainFragment extends Fragment {

    private View view;

    private EditText mEditText;
    //private Button mForecastBtn,mReadBtn,mSaveBtn;
    private TextView mShow_View,mForecast_View;

    //private List<String> mNumList;
    private Map<String,Integer> mNumMap;

    private final String mForecastFilePath="/sdcard/Download/forecast.txt";
    private final String mOriginalFilePath="/sdcard/Download/original.txt";
    private final String mThrowResultPath="/sdcard/Download/ThrowResult.txt";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
//        view = inflater.inflate(R.layout.main_fragment,container,false);
//        // Inflate the layout for this fragment
//        return view;
        if(view!=null){
            ViewGroup parent=(ViewGroup) view.getParent();
            if(parent!=null){
                parent.removeView(view);
            }
        }else{
            view = inflater.inflate(R.layout.main_fragment, container, false);

            Bundle bundle = getArguments();

            Init_Component(view);
        }
        return view;
    }



    private void Init_Component(final View view) {

        Button mForecastBtn,mReadBtn,mSaveBtn,mDelSaveBtn;
        mEditText=(EditText)view.findViewById(R.id.edit_text);
        mForecastBtn=(Button)view.findViewById(R.id.forecast_btn);
        mReadBtn=(Button)view.findViewById(R.id.read_btn);
        mSaveBtn=view.findViewById(R.id.save_btn);
        mDelSaveBtn=view.findViewById(R.id.del_savebtn);
        mShow_View=view.findViewById(R.id.show_View);
        mForecast_View=view.findViewById(R.id.forecast_View);
        mShow_View.setMovementMethod(ScrollingMovementMethod.getInstance());//添加文本视图滚动条
        mForecast_View.setMovementMethod(ScrollingMovementMethod.getInstance());

        mForecastBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FreshTextview();
                String editText= mEditText.getText().toString();
                Log.i("获取文本内容:",editText);
                //Toast.makeText(view.getContext(), "获取文本内容:"+meditText,Toast.LENGTH_SHORT).show();
                if(editText.length()>0){
                    GetNum(editText);
                }else{
                    Toast.makeText(view.getContext(), "输入内容为空，请检查！！！",Toast.LENGTH_SHORT).show();
                }
            }
        });
        mReadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FreshTextview();
                String mOriginal=GetReadFile(mOriginalFilePath);//mForecastFilePath，mOriginalFilePath
                if(mOriginal==null||mOriginal.length()<=0){
                    Toast.makeText(view.getContext(), "无缓存，请输入！！！",Toast.LENGTH_SHORT).show();
                }
                mEditText.setText(mOriginal);
            }
        });
        mSaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FreshTextview();
                String meditText= mEditText.getText().toString();
                //Toast.makeText(view.getContext(), "暂存文本长度:"+meditText.length(),Toast.LENGTH_SHORT).show();//0
                if(meditText.length()<=0){
                    Toast.makeText(view.getContext(), "输入内容为空，请检查！！！",Toast.LENGTH_SHORT).show();
                }else{
                    GetWriteFile(meditText,mOriginalFilePath,true);//原始文件，追加
                    FreshEditView();//上一次截取之后，输入框置空
                }
            }
        });
        mDelSaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isDel=FileUnit.deleteFile(mOriginalFilePath);
                if(isDel){
                    FreshTextview();
                    Toast.makeText(view.getContext(), "！！！清空缓存！！！",Toast.LENGTH_SHORT).show();
                    FreshEditView();//上一次截取之后，输入框置空
                }else{
                    Toast.makeText(view.getContext(), "！！！没有缓存！！！",Toast.LENGTH_SHORT).show();
                }
            }
        });

//        mForecastBtn.setOnClickListener(new mClickLister());
//        mReadBtn.setOnClickListener(new mClickLister());
//        mSaveBtn.setOnClickListener(new mClickLister());
    }

    /***
     * 重写按钮响应函数
     */
//    private class mClickLister implements View.OnClickListener {
//        @Override
//        public void onClick(View v) {
//            final int mBtn_id=view.getId();
//            switch (mBtn_id){
//                case R.id.forecast_btn:
//                    String mText= mEditText.getText().toString();
//                    Log.i("获取文本内容:",mText);
//                    //Toast.makeText(view.getContext(), "获取文本内容:"+mText,Toast.LENGTH_SHORT).show();
//                    //GetNum(mText);
//                    break;
//                case R.id.read_btn:
//                    String test=GetReadFile(mOriginalFilePath);//mForecastFilePath，mOriginalFilePath
//                    mShow_View.setText(test);
//                    break;
//                case R.id.save_btn:
//
//                    break;
//            }
//        }
//    }

    /***
     * 将字符串提取数字
     * @param mText
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void GetNum(String mText) {

        //mNumList=new ArrayList<>();
        mNumMap=new HashMap<>();
        String[] mTexts=mText.split("\n");
        for (int i=0;i<mTexts.length;i++){
            //mNumList=GetSplit(mTexts[i]);
            mNumMap=GetSplit(mTexts[i]);
        }

        if(mNumMap.size()==0){
            Toast.makeText(view.getContext(), "输入内容为空，请检查！！！",Toast.LENGTH_SHORT).show();
        }else{
            GetWriteFile(mNumMap.toString(),mForecastFilePath,false);//写入文件,结果覆盖，为false
        }

        String mShow_Map=GetMapStr2String(mNumMap.toString());
        mShow_View.setText(mShow_Map);
        Show_ForecastView(mNumMap);
    }

    /***
     * 展示预测结果
     */
    private void Show_ForecastView(Map<String,Integer> map){
        int mAmount=0,mProfit=0;
        Map<String,Integer> mShowMap=new HashMap<>();
        for (String key : map.keySet()) {
            mAmount+=map.get(key);
        }

        mProfit=(int)(mAmount-mAmount*0.04)/47;
        for(String mkey : map.keySet()){
            if(map.get(mkey)>mProfit){
                mShowMap.put(mkey,map.get(mkey)-mProfit);
            }
        }

        String mTemp="本次统计共:"+map.size()+"个。\n"+
                "总金额:"+ String.valueOf(mAmount)+"\n"+
                "利润点:"+mProfit+"\n"+
                "预计抛出:\n"+GetMapStr2String(mShowMap.toString());
        mForecast_View.setText(mTemp);
        GetWriteFile(mTemp,mThrowResultPath,false);

        Toast.makeText(view.getContext(), "保存成功！！！",Toast.LENGTH_SHORT).show();
    }

    /***
     * 拆分数字存入list
     * @param mStr
     * @return
     */
    private Map<String,Integer> GetSplit(String mStr){
        String regEx = "[^0-9]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(mStr);
        String result = m.replaceAll(" ").trim();
        Log.i("","字符串筛选:"+result);//字符串筛选:4 13 26 33 45 10
        String[] result_list=result.split(" ");

        //List<String> mNumList=new ArrayList<>();
        Log.i("","字符串筛选:"+result_list.length);
        int mMoney=Integer.parseInt(result_list[result_list.length-1]);
        for(int i=0;i<result_list.length-1;i++){
            //mNumList.add(result_list[i]+":"+mMoney);
            if(mNumMap.containsKey(result_list[i])){
                mNumMap.put(result_list[i],mNumMap.get(result_list[i])+mMoney);
            }else{
                mNumMap.put(result_list[i],mMoney);
            }
        }
       FreshEditView();//上一次截取之后，输入框置空

        return mNumMap;
    }

    /**
     * 写入文件
     */
    private void GetWriteFile(String fileContent,String filePath,boolean isCover){
        try {
            //String mFilePath="/sdcard/Download/forecast.txt";
            FileUnit.createFileRecursion(filePath, 0);
            FileWriter fileWriter = new FileWriter(filePath,isCover);//覆盖写入:isCover是false
            //fileWriter.write(String.valueOf(mNumMap.toString()));
            fileWriter.write(fileContent+"\n");
            fileWriter.flush();
            fileWriter.close();
            //Log.i("File_WRITE", "FilePath: " + filePath);
            //Toast.makeText(view.getContext(), "保存成功！！！",Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * 读取文件内容
     */
    private String GetReadFile(String file_path){
        String mRead= null;
        String mfileName=file_path.substring(file_path.length()-12,file_path.length()-4);
        switch (mfileName){
            case "original":
                try {
                    mRead = FileUnit.readFileRecursion(file_path);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case "forecast":
                try {
                    mRead = FileUnit.readFileRecursion(file_path);
                    mRead=mRead.substring(0,mRead.length()-1);//去尾
                    mRead=mRead.substring(1,mRead.length());//去头
                    mRead=mRead.replaceAll("[ ]","").replaceAll("[ ]","");//去除空格
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
        }
        return mRead;
    }

    private void FreshTextview(){
        mShow_View.setText("");
        mForecast_View.setText("");
    }

    private void FreshEditView(){
        mEditText.setText("");
    }
}
