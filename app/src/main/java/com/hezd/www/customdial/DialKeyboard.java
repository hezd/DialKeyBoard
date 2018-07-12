package com.hezd.www.customdial;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


/**
 * 自定义电话拨号器
 * @auther hezd
 * created on 2018/7/11 15:18
 */
public class DialKeyboard extends LinearLayout implements View.OnClickListener {
    private static final String TAG = "hezd---";
    public static final int TYPE_REMOVE_LOOP = 0x1;
    public static final int TYPE_REMOVE = 0x2;
    public static final int REMOVE_DELAYED_TIME = 200;
    private static OnCallBtnClickListener mListener;
    private TextView mInputContentTv;
    private ImageView mClearIv;
    private Button mOneBtn;
    private Button mTwoBtn;
    private View mThreeBtn;
    private View mFourBtn;
    private View mFiveBtn;
    private View mSixBtn;
    private View mSevenBtn;
    private View mEightBtn;
    private View mNineBtn;
    private View mZeroBtn;
    private View mStarBtn;
    private View mHashSignBtn;
    private Button mCallbtn;

    @SuppressLint("HandlerLeak")
    private Handler mHandler  = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case TYPE_REMOVE_LOOP:
                    removeWord();
                    mHandler.sendEmptyMessageDelayed(TYPE_REMOVE_LOOP,REMOVE_DELAYED_TIME);
                    break;
                case TYPE_REMOVE:
                    removeWord();
                    break;
            }
        }
    };


    private long startTime;
    /**是否正在处理长按删除*/
    private boolean isHandling;
    private String realContent;
    private String showContent;

    public DialKeyboard(Context context) {
        super(context);
        init(context);
    }

    public DialKeyboard(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }


    private void init(Context context) {
        setOrientation(VERTICAL);
        setGravity(Gravity.CENTER);
        View keyboardView =  LayoutInflater.from(context).inflate(R.layout.layout_keyboard,this);
        initView(keyboardView);
        setListeners();
    }


    private void initView(View keyboardView) {
        mInputContentTv = keyboardView.findViewById(R.id.tv_result);
        mCallbtn = keyboardView.findViewById(R.id.btn_call);
        mClearIv = keyboardView.findViewById(R.id.iv_clear);
        mOneBtn = keyboardView.findViewById(R.id.btn_one);
        mTwoBtn = keyboardView.findViewById(R.id.btn_two);
        mThreeBtn = keyboardView.findViewById(R.id.btn_three);
        mFourBtn = keyboardView.findViewById(R.id.btn_four);
        mFiveBtn = keyboardView.findViewById(R.id.btn_five);
        mSixBtn = keyboardView.findViewById(R.id.btn_six);
        mSevenBtn = keyboardView.findViewById(R.id.btn_seven);
        mEightBtn = keyboardView.findViewById(R.id.btn_eight);
        mNineBtn = keyboardView.findViewById(R.id.btn_nine);
        mZeroBtn = keyboardView.findViewById(R.id.btn_zero);
        mStarBtn = keyboardView.findViewById(R.id.btn_star);
        mHashSignBtn = keyboardView.findViewById(R.id.btn_has_sign);
    }

    @SuppressLint("ClickableViewAccessibility")
    private void setListeners() {
        mCallbtn.setOnClickListener(this);
        mOneBtn.setOnClickListener(this);
        mTwoBtn.setOnClickListener(this);
        mThreeBtn.setOnClickListener(this);
        mFourBtn.setOnClickListener(this);
        mFiveBtn.setOnClickListener(this);
        mSixBtn.setOnClickListener(this);
        mSevenBtn.setOnClickListener(this);
        mEightBtn.setOnClickListener(this);
        mNineBtn.setOnClickListener(this);
        mZeroBtn.setOnClickListener(this);
        mStarBtn.setOnClickListener(this);
        mHashSignBtn.setOnClickListener(this);
        mInputContentTv.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String content = mInputContentTv.getText().toString();
                mClearIv.setVisibility(TextUtils.isEmpty(content)?INVISIBLE:VISIBLE);

            }
        });

        mClearIv.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                Log.d(TAG,"MotionEvent: "+motionEvent);
               if(motionEvent.getAction() == MotionEvent.ACTION_UP) {
                   isHandling = false;
                    mHandler.removeMessages(TYPE_REMOVE_LOOP);
                   long time = System.currentTimeMillis() - startTime;
                   Log.d(TAG,"间隔时间是(毫秒): "+time);
                   if(time<500) {
                       mHandler.sendEmptyMessage(TYPE_REMOVE);
                   }else {
                       return false;
                   }
               }else if(motionEvent.getAction() == MotionEvent.ACTION_DOWN){
                       startTime = System.currentTimeMillis();
               }else {
                   if(System.currentTimeMillis() - startTime>=500&&!isHandling) {
                       Log.d("hezd---","长按..."+motionEvent.toString());
                       isHandling = true;
                       mHandler.sendEmptyMessageDelayed(TYPE_REMOVE_LOOP,REMOVE_DELAYED_TIME);
                   }else {
                       return false;
                   }
               }
                return true;
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_one:
                clickKey("1");
                break;
            case R.id.btn_two:
                clickKey("2");
                break;
            case R.id.btn_three:
                clickKey("3");
                break;
            case R.id.btn_four:
                clickKey("4");
                break;
            case R.id.btn_five:
                clickKey("5");
                break;
            case R.id.btn_six:
                clickKey("6");
                break;
            case R.id.btn_seven:
                clickKey("7");
                break;
            case R.id.btn_eight:
                clickKey("8");
                break;
            case R.id.btn_nine:
                clickKey("9");
                break;
            case R.id.btn_zero:
                clickKey("0");
                break;
            case R.id.btn_star:
                clickKey("*");
                break;
            case R.id.btn_has_sign:
                clickKey("#");
                break;
            case R.id.btn_call:
                if(mListener!=null)
                    mListener.onCallBtnClick(realContent);
                break;
        }
    }

    private void clickKey(String key) {
        StringBuilder sb = new StringBuilder();
        sb.append(TextUtils.isEmpty(realContent)?"":realContent);
        sb.append(key);
        realContent = sb.toString();
        formatContent();

    }

    private void formatContent() {
        if(!TextUtils.isEmpty(realContent))  {
            if(realContent.length()>11) {
                showContent = new StringBuilder().append(realContent.substring(0,5)).
                        append("...")
                        .append(realContent.substring(realContent.length()-5,realContent.length())).toString();
            }else {
                showContent = realContent;
            }
            mInputContentTv.setText(showContent);
        }
    }

    public void setOnCallBtnCLickListener(OnCallBtnClickListener listener) {
        mListener = listener;
    }

    public interface OnCallBtnClickListener {
        void onCallBtnClick(String phoneNum);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        mHandler.removeMessages(TYPE_REMOVE);
        mHandler.removeMessages(TYPE_REMOVE_LOOP);
        mHandler.removeCallbacksAndMessages(null);
    }

    private void removeWord() {
        if(!TextUtils.isEmpty(realContent)) {
            realContent = new StringBuilder(realContent).substring(0,realContent.length()-1);
           formatContent();
        }
    }
}
