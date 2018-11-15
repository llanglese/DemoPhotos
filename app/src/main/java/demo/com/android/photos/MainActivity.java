package demo.com.android.photos;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {

    private MyPileAvertView myPileAvertView;

    private List<Drawable> drawables = new ArrayList<>();
    int i = 1;
    Handler mHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myPileAvertView = findViewById(R.id.myPileAvertView);

        drawables.add(getDrawable(R.drawable.timg));
        drawables.add(getDrawable(R.drawable.ic_launcher_background));
        drawables.add(getDrawable(R.drawable.ic_launcher_foreground));
        drawables.add(getDrawable(R.drawable.test));
        drawables.add(getDrawable(R.drawable.live_room_heart0));
        drawables.add(getDrawable(R.drawable.live_room_heart1));
        drawables.add(getDrawable(R.drawable.live_room_heart2));
        drawables.add(getDrawable(R.drawable.live_room_heart3));
        drawables.add(getDrawable(R.drawable.live_singer_hot1));
        drawables.add(getDrawable(R.drawable.live_singer_hot2));
        drawables.add(getDrawable(R.drawable.live_singer_hot3));
        drawables.add(getDrawable(R.drawable.live_singer_msn));
        drawables.add(getDrawable(R.drawable.live_singer_no2));
        drawables.add(getDrawable(R.drawable.live_singer_no3));
        drawables.add(getDrawable(R.drawable.live_singer_no4));
        drawables.add(getDrawable(R.drawable.live_singer_no5));
        drawables.add(getDrawable(R.drawable.timg));
        drawables.add(getDrawable(R.drawable.ic_launcher_background));
        drawables.add(getDrawable(R.drawable.ic_launcher_foreground));
        drawables.add(getDrawable(R.drawable.test));
        drawables.add(getDrawable(R.drawable.live_room_heart0));
        drawables.add(getDrawable(R.drawable.live_room_heart1));
        drawables.add(getDrawable(R.drawable.live_room_heart2));
        drawables.add(getDrawable(R.drawable.live_room_heart3));
        drawables.add(getDrawable(R.drawable.live_singer_hot1));
        drawables.add(getDrawable(R.drawable.live_singer_hot2));
        drawables.add(getDrawable(R.drawable.live_singer_hot3));
        drawables.add(getDrawable(R.drawable.live_singer_msn));
        drawables.add(getDrawable(R.drawable.live_singer_no2));
        drawables.add(getDrawable(R.drawable.live_singer_no3));
        drawables.add(getDrawable(R.drawable.live_singer_no4));
        drawables.add(getDrawable(R.drawable.live_singer_no5));
        myPileAvertView.setAvertImages(drawables);
        mHandler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
//            myPileAvertView.setAvertImages(drawables);
                switch (msg.what) {
                    case 1:
                        if(i >= drawables.size()) {
                            break;
                        }
                        Message message = Message.obtain();
                        message.what = 2;
                        Bundle bundle = new Bundle();
                        bundle.putInt("index", i);
                        message.setData(bundle);
                        message.what = 2;
                        sendMessageDelayed(message, 800);
                        i++;
                        break;
                    case 2:
                        int index = msg.getData().getInt("index");
                        myPileAvertView.addAvertImage(drawables.get(index));
                        sendEmptyMessageDelayed(1, 100);
                        break;

                }

            }
        };

        Button button = findViewById(R.id.add);
        i = 0;
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Drawable drawable = getDrawable(R.drawable.test);
//                myPileAvertView.addAvertImage(drawable);
                if(i >= drawables.size()) {
                    i = 0;
                }
                myPileAvertView.addAvertImage(drawables.get(i));
                i++;
            }
        });

        Button remove = findViewById(R.id.remove);
        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Drawable> drawables = new ArrayList<>();//删除之后最顶端的三个用户头像
                Drawable drawable = getDrawable(R.drawable.timg);
                drawables.add(drawable);
                drawables.add(drawable);
                Drawable drawable1 = getDrawable(R.drawable.test);
                drawables.add(drawable1);
                myPileAvertView.removeAvertImage(drawables);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
//        mHandler.sendEmptyMessageDelayed(0, 1000);
        mHandler.sendEmptyMessageDelayed(1, 100);
    }


}
