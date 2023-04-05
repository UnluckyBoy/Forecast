package com.matrix.forecast.ui.Fragment;

import android.os.Build;
import android.os.Bundle;
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

    private final String mForecastFilePath="/sdcard/Download/forecast.txt";
    private final String mOriginalFilePath="/sdcard/Download/original.txt";

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
        EditText mEditText;
        Button mForecastBtn,mReadBtn;
        TextView mShow_View;

        mEditText=(EditText)view.findViewById(R.id.edit_text);
        mForecastBtn=(Button)view.findViewById(R.id.forecast_btn);
        mReadBtn=(Button)view.findViewById(R.id.read_btn);
        mShow_View=view.findViewById(R.id.show_View);

        mForecastBtn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
               String mText= mEditText.getText().toString();
               Log.i("获取文本内容:",mText);
               //Toast.makeText(view.getContext(), "获取文本内容:"+mText,Toast.LENGTH_SHORT).show();

               GetNum(mText);
            }
        });

        mReadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String test=GetReadFile(mOriginalFilePath);//mForecastFilePath，mOriginalFilePath

                mShow_View.setText(test);
            }
        });
    }

    /***
     * 将字符串提取数字
     * @param mText
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void GetNum(String mText) {

        String regEx = "[^0-9]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(mText);
        String result = m.replaceAll(" ").trim();
        Log.i("","字符串筛选:"+result);//字符串筛选:4 13 26 33 45 10
        String[] result_list=result.split(" ");

        Map<String,String> mNumMap=new HashMap<>();
        List<String> mNumList=new ArrayList<>();
        List<String> mWriteNumList=new ArrayList<>();
        Log.i("","字符串筛选:"+result_list.length);
        String mMoney=result_list[result_list.length-1];
        for(int i=0;i<result_list.length-1;i++){
            //mNumMap.put(result_list[i],mMoney);
            mNumList.add(result_list[i]+":"+mMoney);
        }

        if(mNumList.size()==0){
            Toast.makeText(view.getContext(), "输入内容为空，请检查！！！",Toast.LENGTH_SHORT).show();
        }else{
//            String mRead=GetReadFile(mForecastFilePath);
//            List<String> mRead_result = Arrays.asList(mRead.split(","));
//
//            //Toast.makeText(view.getContext(), "保存，其中一个list元素:"+mRead_result,Toast.LENGTH_SHORT).show();
//            for(int i=0;i<mRead_result.size();i++){
//                Log.i("","读取的list:"+mRead_result.get(i));
//                for(int j=i;j<mNumList.size();j++){
//                    if(mRead_result.get(i).split(":")[0].equals(mNumList.get(j).split(":")[0])){
//                        Log.i("","存在相同的元素"+mRead_result.get(i).split(":")[0]+"---"+mNumList.get(j).split(":")[0]);
//
//                        mNumList.add(mNumList.get(j).split(":")[0]+":"+String.valueOf(
//                                Integer.parseInt(mRead_result.get(i).split(":")[1])+
//                                        Integer.parseInt(mNumList.get(j).split(":")[1])));
//                    }
//                }
//            }
            //暂时注释
            GetWriteFile(mNumList.toString());//写入文件
        }
    }

    /**
     * 写入文件
     */
    private void GetWriteFile(String fileContent){
        try {
            //String mFilePath="/sdcard/Download/forecast.txt";
            FileUnit.createFileRecursion(mForecastFilePath, 0);
            FileWriter fileWriter = new FileWriter(mForecastFilePath,false);//覆盖写入:isCover是false
            //fileWriter.write(String.valueOf(mNumMap.toString()));
            fileWriter.write(fileContent);
            fileWriter.flush();
            fileWriter.close();
            Log.i("File_WRITE", "FilePath: " + mForecastFilePath);
            Toast.makeText(view.getContext(), "保存成功！！！",Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * 读取文件内容
     */
    private String GetReadFile(String file_path){
        String mRead= null;
        try {
            mRead = FileUnit.readFileRecursion(file_path);
            mRead=mRead.substring(0,mRead.length()-1);//去尾
            mRead=mRead.substring(1,mRead.length());//去头
            mRead=mRead.replaceAll("[ ]","").replaceAll("[ ]","");//去除空格
        } catch (IOException e) {
            e.printStackTrace();
        }
        return mRead;
    }
}
