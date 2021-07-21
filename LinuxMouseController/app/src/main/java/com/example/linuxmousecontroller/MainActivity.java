package com.example.linuxmousecontroller;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    ConnectTask connectTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        connectTask = new ConnectTask();
        connectTask.execute("");
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Rect rectangle = new Rect();
        Window window = getWindow();
        window.getDecorView().getWindowVisibleDisplayFrame(rectangle);
        int statusBarHeight = rectangle.top;
        int contentViewTop =
                window.findViewById(Window.ID_ANDROID_CONTENT).getTop();
        int titleBarHeight = contentViewTop - statusBarHeight;

        float x = event.getRawX();

        // Decrement stupid Android base height that keeps getting sent
        float y = event.getRawY() + titleBarHeight;

        View mainView = findViewById(R.id.mainView);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int viewHeight = displayMetrics.heightPixels;
        int viewWidth = displayMetrics.widthPixels;

        float hostScreenHeight = 1152;
        float hostScreenWidth = 2048;

        int relativeHeight = (int)((y / viewHeight) * hostScreenHeight);
        int relativeWidth = (int)((x / viewWidth) * hostScreenWidth);

        connectTask.mTcpClient.sendMessage(relativeWidth + "," + relativeHeight);

        Toast.makeText(this, "x: " + relativeWidth + ", y: " + relativeHeight, Toast.LENGTH_SHORT).show();

        return true;
    }
}