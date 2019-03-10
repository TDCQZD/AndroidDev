package com.example.administrator.dialogtest.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.example.administrator.dialogtest.R;

public class LoginDialogFragment extends DialogFragment {
	private EditText mUsername;
	private EditText mPassword;

	public interface LoginInputListener
	{
		void onLoginInputComplete(String username, String password);
	}
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState)
	{
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

		LayoutInflater inflater = getActivity().getLayoutInflater();
		View view = inflater.inflate(R.layout.fragment_login_dialog, null);
		mUsername = (EditText) view.findViewById(R.id.id_txt_username);
		mPassword = (EditText) view.findViewById(R.id.id_txt_password);

		builder.setView(view)
				// Add action buttons
				.setPositiveButton("登录",
						new DialogInterface.OnClickListener()
						{
							@Override
							public void onClick(DialogInterface dialog, int id)
							{
								LoginInputListener listener = (LoginInputListener) getActivity();
								listener.onLoginInputComplete(mUsername
										.getText().toString(), mPassword
										.getText().toString());
							}
						}).setNegativeButton("取消", null);

		return builder.create();
	}
}
