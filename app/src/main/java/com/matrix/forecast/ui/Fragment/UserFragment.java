package com.matrix.forecast.ui.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.matrix.forecast.R;

public class UserFragment extends Fragment {
    private View view;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
//        view = inflater.inflate(R.layout.user_fragment,container,false);
//        // Inflate the layout for this fragment
//        return view;
        if(view!=null){
            ViewGroup parent=(ViewGroup) view.getParent();
            if(parent!=null){
                parent.removeView(view);
            }
        }else{
            view = inflater.inflate(R.layout.user_fragment, container, false);

            Bundle bundle = getArguments();
//          agrs1 = bundle.getString("agrs1");
//          Run_main_data(view);
//          Run_main_booklist(view);
//          initRefresh();
        }
        return view;
    }
}
