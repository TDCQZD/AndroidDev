package tester.ermu.com.handdrawdemo;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BlurMaskFilter;
import android.graphics.EmbossMaskFilter;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;


public class MainActivity extends Activity implements View.OnClickListener{
	EmbossMaskFilter emboss;
	BlurMaskFilter blur;
	DrawView drawView;
	private LinearLayout main_linlayout;
	private Button bt,bt_clear;
	private ImageView img;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		init();	//初始化组件
		getWH();//获取我们xml布局中view的宽高
	}

	//获取我们xml布局中view的宽高
	private void getWH() {
		// 获取创建的宽度和高度
		DisplayMetrics displayMetrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getRealMetrics(displayMetrics);
		// 创建一个DrawView，该DrawView的宽度、高度与该Activity保持相同
		main_linlayout = (LinearLayout)findViewById(R.id.main_linlayout);
		drawView = new DrawView(this, displayMetrics.widthPixels, displayMetrics.heightPixels);

		main_linlayout.addView(drawView);
		drawView.requestFocus();
	}

	private void init() {
		bt = (Button)findViewById(R.id.bt);
		bt.setOnClickListener(this);
		bt_clear = (Button)findViewById(R.id.bt_clear);
		bt_clear.setOnClickListener(this);

		img = (ImageView) findViewById(R.id.img);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()){
			case R.id.bt:
				Bitmap bit = drawView.getPaintBitmap();
				img .setImageBitmap(bit);
				break;

			case R.id.bt_clear:
				drawView.clear();

				break;
		}
	}
}

