package demo.com.android.photos;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by keynes-os on 2018/10/9.
 */

public class MyPileAvertView extends LinearLayout {

    @BindView(R.id.pile_view)
    MyPileView pileView;

    private List<Drawable> imageList;
    private List<Drawable> visibleList = null;
    private Context context = null;
    public static final int VISIBLE_COUNT = 3;//默认显示个数

    public MyPileAvertView(Context context) {
        this(context, null);
        this.context = context;
    }

    public MyPileAvertView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initView();
    }

    private void initView() {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_group_pile_avert, this);
        ButterKnife.bind(view);
    }

    public void setAvertImages(List<Drawable> imageList) {
        this.imageList = imageList;
        setAvertImages(this.imageList,VISIBLE_COUNT);
    }

    //如果imageList>visiableCount,显示List最上面的几个
    public void setAvertImages(List<Drawable> imageList, int visibleCount) {
        if (imageList.size() > visibleCount + 1) {
            visibleList = imageList.subList(imageList.size() - 1 - visibleCount, imageList.size() - 1);
        } else {
            visibleList = imageList;
        }
        pileView.removeAllViews();
        for (int i = 0; i < visibleList.size(); i++) {
            CircleImageView image = (CircleImageView) LayoutInflater.from(context).inflate(R.layout.item_group_round_avert, pileView, false);
            image.setImageDrawable(visibleList.get(i));
            pileView.addView(image);
        }
    }

    public void addAvertImage(Drawable drawable) {
        CircleImageView image= (CircleImageView) LayoutInflater.from(context).inflate(R.layout.item_group_round_avert, pileView, false);
        image.setImageDrawable(drawable);
        pileView.addView(image, 3);
    }

    public void removeAvertImage(List<Drawable> imageList1) {
        this.imageList.clear();
        this.imageList.addAll(imageList1);
        int count = imageList.size();
        Log.e("YYYYYY", imageList1.size() + " imageList1");
        Log.e("YYYYYY", count + "");
        List<Drawable> visibleList = null;
        if (imageList.size() > VISIBLE_COUNT) {
            visibleList = imageList.subList(imageList.size() - 1 - VISIBLE_COUNT, imageList.size() - 1);
        } else {
            visibleList = imageList;
        }
        pileView.removeAllViews();
        if(visibleList != null) {
            for (int i = 0; i < visibleList.size(); i++) {
                CircleImageView image = (CircleImageView) LayoutInflater.from(context).inflate(R.layout.item_group_round_avert, pileView, false);
                image.setImageDrawable(visibleList.get(i));
                pileView.addView(image);
            }
        }
    }

}
