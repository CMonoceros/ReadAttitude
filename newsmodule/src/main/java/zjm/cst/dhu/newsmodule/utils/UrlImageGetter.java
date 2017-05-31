package zjm.cst.dhu.newsmodule.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.media.ThumbnailUtils;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutionException;

import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import zjm.cst.dhu.library.Constant;
import zjm.cst.dhu.library.MyApplication;
import zjm.cst.dhu.library.R;
import zjm.cst.dhu.library.api.repository.Repository;
import zjm.cst.dhu.library.utils.RetrofitUtil;

import static android.text.Html.FROM_HTML_MODE_COMPACT;
import static android.text.Html.FROM_HTML_MODE_LEGACY;
import static zjm.cst.dhu.library.Constant.NETEASE_NEWS_BASE_URL;

/**
 * Created by zjm on 2017/5/14.
 */

public class UrlImageGetter implements Html.ImageGetter {

    private Map<Integer, Drawable> drawableHashMap = new HashMap<>();

    private TextView mTextView;
    private String mBody;
    private Fragment fragment;
    private int imgCount;
    private int nowCount=0;
    private int mImgWidth;
    private int mImgHeight;

    public UrlImageGetter(TextView textView, String body, int imgCount,Fragment fragment) {
        this.mTextView = textView;
        this.mBody = body;
        this.fragment = fragment;
        this.imgCount=imgCount;
        mImgWidth = mTextView.getWidth();
    }

    @Override
    public Drawable getDrawable(final String source) {
        Drawable drawable = drawableHashMap.get(source.hashCode());
        if (drawable == null) {
            Glide.with(fragment).load(source)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(new SimpleTarget<GlideDrawable>(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL) {
                        @Override
                        public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                            float imgWidth = resource.getIntrinsicWidth();
                            float imgHeight = resource.getIntrinsicHeight();
                            float rate = imgHeight / imgWidth;
                            mImgHeight = (int) (mImgWidth * rate);
                            resource.setBounds(0, 0, mImgWidth, mImgHeight);
                            drawableHashMap.put(source.hashCode(), resource);
                            nowCount++;
                            if(imgCount==nowCount){
                                loadImage();
                            }
                        }
                    });
            return createPicPlaceholder();
        } else {
            return drawable;
        }
    }

    private void loadImage() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            mTextView.setText(Html.fromHtml(mBody,
                    FROM_HTML_MODE_LEGACY, UrlImageGetter.this, null));
        } else {
            mTextView.setText(Html.fromHtml(mBody, UrlImageGetter.this, null));
        }
    }

    @SuppressWarnings("deprecation")
    private Drawable createPicPlaceholder() {
        Drawable drawable;
        drawable = new ColorDrawable(MyApplication.getContext().getResources().getColor(R.color.colorGreyDark));
        drawable.setBounds(0, 0, mImgWidth, mImgWidth / 3);
        return drawable;
    }
}
