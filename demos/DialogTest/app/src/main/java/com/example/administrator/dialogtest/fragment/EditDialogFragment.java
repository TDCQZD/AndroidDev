package com.example.administrator.dialogtest.fragment;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.example.administrator.dialogtest.R;

/**
 * Created by Administrator on 2017/3/20.
 */

public class EditDialogFragment extends DialogFragment  {



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉对话框标题
        View view = inflater.inflate(R.layout.fragment_edit_name, container);

        return view;
    }

}
