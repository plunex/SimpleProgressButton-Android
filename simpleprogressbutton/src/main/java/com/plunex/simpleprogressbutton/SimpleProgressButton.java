package com.plunex.simpleprogressbutton;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;

public class SimpleProgressButton extends LinearLayout {

    private LayoutInflater inflater;
    private TextView mTextView;
    private ProgressBar mProgressBar;
    private ImageView mImageView;

    private float density = getContext().getResources().getDisplayMetrics().density;
    private int paddingLeftRight = (int) (16 * density);
    private int paddingTopBottom = (int) (8 * density);

    private String initialText;

    private int mIntState;
    private int mResImageState;
    private String mTextState;

    private static final String KEY_SUPER_STATE = "superState";
    private static final String KEY_INT_STATE = "intState";
    private static final String KEY_TEXT_STATE = "textState";
    private static final String KEY_RES_IMAGE_STATE = "resImageState";


    public SimpleProgressButton(Context context) {
        super(context);
        init(context, null);
    }

    public SimpleProgressButton(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public SimpleProgressButton(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }


    private void init(Context context, AttributeSet attrs) {
        // check for android integer to Text size or toast It after getting mTView
        // check for android default button color

        int textSize = 14;
        int textColor = Color.BLACK;
        int progressBar_color = Color.WHITE;
        int progressBar_height = 18;
        int progressBar_width = 18;
        int image_height = 18;
        int image_width = 18;

        if (attrs != null) {
            TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.SimpleProgressButton);
            textSize = array.getDimensionPixelSize(R.styleable.SimpleProgressButton_text_size, textSize);
            textColor = array.getColor(R.styleable.SimpleProgressButton_text_color, textColor);

            progressBar_color = array.getColor(R.styleable.SimpleProgressButton_progressbar_color, progressBar_color);
            progressBar_height = array.getDimensionPixelSize(R.styleable.SimpleProgressButton_progressBar_height, progressBar_height);
            progressBar_width = array.getDimensionPixelSize(R.styleable.SimpleProgressButton_progressBar_width, progressBar_width);

            image_height = array.getDimensionPixelSize(R.styleable.SimpleProgressButton_image_height, image_height);
            image_width = array.getDimensionPixelSize(R.styleable.SimpleProgressButton_image_width, image_width);

        }

        inflater = LayoutInflater.from(getContext());
        inflater.inflate(R.layout.simple_progress_button, this);
        setOrientation(LinearLayout.HORIZONTAL);
        setPadding(paddingLeftRight, paddingTopBottom, paddingLeftRight, paddingTopBottom);

        mTextView = findViewById(R.id.textView);
        mTextView.setTextColor(textColor);
        mProgressBar = findViewById(R.id.progressBar);
        mProgressBar.getIndeterminateDrawable().setColorFilter(progressBar_color, PorterDuff.Mode.MULTIPLY);
        mImageView = findViewById(R.id.imageView);

        mIntState = 0;
        initialText = mTextView.getText().toString();
        mTextState = mTextView.getText().toString();

    }


    public void showDefault() {
        mIntState = 0;
        if (initialText != null) {
            mTextView.setText(initialText);
        }
        render();
    }

    public void showDefaultProgress(String title) {
        mIntState = 1;
        if (!title.equals("")) {
            mTextView.setText(title);
            mTextState = title;
        }
        render();
    }

    public void showCustomMessage(String message, int image) {
        mIntState = 2;
        if (!message.equals("")) {
            mTextState = message;
        }

        // must be provided by the library
        if (image == 0) {
            mResImageState = R.drawable.ic_reload;
            render();
            return;
        }

        // must be provided by the library
        if (image == 1) {
            mResImageState = R.drawable.ic_checked;
            render();
            return;
        }

        mResImageState = image;
        render();
    }


    private void render() {
        switch (mIntState) {
            case 0:
                mProgressBar.setVisibility(View.GONE);
                mImageView.setVisibility(View.GONE);
                mTextView.setText(initialText);
                break;
            case 1:
                mProgressBar.setVisibility(View.VISIBLE);
                mImageView.setVisibility(View.GONE);
                mTextView.setText(mTextState);
                break;
            case 2:
                mProgressBar.setVisibility(View.GONE);
                mImageView.setVisibility(View.VISIBLE);
                mTextView.setText(mTextState);
                mImageView.setImageResource(mResImageState);
                break;

            case 3:
                mProgressBar.setVisibility(View.GONE);
                mImageView.setVisibility(View.GONE);
                mTextView.setText(mTextState);
                break;

            default:
                break;
        }
    }


    @Override
    protected Parcelable onSaveInstanceState() {
        Bundle bundle = new Bundle();
        bundle.putParcelable(KEY_SUPER_STATE, super.onSaveInstanceState());
        bundle.putInt(KEY_INT_STATE, mIntState);
        bundle.putString(KEY_TEXT_STATE, mTextView.getText().toString());
        bundle.putInt(KEY_RES_IMAGE_STATE, mResImageState);
        return bundle;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        if (state instanceof Bundle) {
            Bundle bundle = (Bundle) state;
            mIntState = bundle.getInt(KEY_INT_STATE);
            mTextState = bundle.getString(KEY_TEXT_STATE);
            mResImageState = bundle.getInt(KEY_RES_IMAGE_STATE);
            super.onRestoreInstanceState(bundle.getParcelable(KEY_SUPER_STATE));
        } else {
            super.onRestoreInstanceState(state);
        }
        render();
    }

}
