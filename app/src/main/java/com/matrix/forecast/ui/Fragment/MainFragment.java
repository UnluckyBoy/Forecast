package com.matrix.forecast.ui.Fragment;

import static android.os.Environment.MEDIA_MOUNTED;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.matrix.forecast.R;
import com.matrix.forecast.UtilTool.FileUitl;

import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainFragment extends Fragment {
    private View view;
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
        Button mForecastBtn;
        mEditText=(EditText)view.findViewById(R.id.edit_text);
        mForecastBtn=(Button)view.findViewById(R.id.forecast_btn);

        mForecastBtn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
               String mText= mEditText.getText().toString();
               //Log.i("获取文本内容:",mText);
               Toast.makeText(view.getContext(), "获取文本内容:"+mText,Toast.LENGTH_SHORT).show();

               GetNum(mText);
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void GetNum(String mText) {

        String regEx = "[^0-9]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(mText);
        String result = m.replaceAll(" ").trim();
        Log.i("","字符串筛选:"+result);//字符串筛选:4 13 26 33 45 10
        String[] result_list=result.split(" ");

        Map<String,String> mNumMap=new HashMap<>();
        Log.i("","字符串筛选:"+result_list.length);
        String mMoney=result_list[5];
        for(int i=0;i<result_list.length-1;i++){
            mNumMap.put(result_list[i],mMoney);
        }

        Log.i("","字典内容:"+mNumMap.get("13"));//10

        try {
            String jsonPath="/sdcard/Download/forecast.txt";
            FileUitl.createFileRecursion(jsonPath, 0);
            FileWriter fileWriter = new FileWriter(jsonPath);
            fileWriter.write(String.valueOf(mNumMap.toString()));
            fileWriter.flush();
            fileWriter.close();
            Log.i("JSON_WRITE", "jsonPath: " + jsonPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

//    /***
//     * 保存到文件
//     */
//    @RequiresApi(api = Build.VERSION_CODES.O)
//    public static void createFileRecursion(String fileName, Integer height) throws IOException {
//        Path path = Paths.get(fileName);
//        if (Files.exists(path)) {
//            return;
//        }
//        if (Files.exists(path.getParent())) {
//            if (height == 0) {
//                Files.createFile(path);
//            } else {
//                Files.createDirectory(path);
//            }
//        } else {
//            createFileRecursion(path.getParent().toString(), height + 1);
//            createFileRecursion(fileName, height);
//        }
//    }

}
