package zjm.cst.dhu.newsmodule.utils

import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.os.Build
import android.support.v4.app.Fragment
import android.text.Html
import android.widget.TextView

import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.GlideDrawable
import com.bumptech.glide.request.animation.GlideAnimation
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.target.Target

import zjm.cst.dhu.library.MyApplication
import zjm.cst.dhu.library.R

import android.text.Html.FROM_HTML_MODE_LEGACY

/**
 * Created by zjm on 2017/5/14.
 */

class UrlImageGetter(private val mTextView: TextView, private val mBody: String,
                     private val imgCount: Int, private val fragment: Fragment)
    : Html.ImageGetter {

    private val drawableHashMap = HashMap<Int, Drawable>()
    private var nowCount = 0
    private val mImgWidth: Int = mTextView.width
    private var mImgHeight: Int = 0

    override fun getDrawable(source: String): Drawable {
        val drawable = drawableHashMap[source.hashCode()]
        if (drawable == null) {
            loadGlide(source)
            return createPicPlaceholder()
        } else {
            return drawable
        }
    }

    private fun loadGlide(source: String) {
        Glide.with(fragment).load(source)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(object : SimpleTarget<GlideDrawable>(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL) {
                    override fun onResourceReady(resource: GlideDrawable,
                                                 glideAnimation: GlideAnimation<in GlideDrawable>) {
                        val imgWidth = resource.intrinsicWidth.toFloat()
                        val imgHeight = resource.intrinsicHeight.toFloat()
                        val rate = imgHeight / imgWidth
                        mImgHeight = (mImgWidth * rate).toInt()
                        resource.setBounds(0, 0, mImgWidth, mImgHeight)
                        drawableHashMap.put(source.hashCode(), resource)
                        nowCount++
                        if (imgCount == nowCount) {
                            loadImage()
                        }
                    }
                })
    }

    private fun loadImage() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            mTextView.text = Html.fromHtml(mBody,
                    FROM_HTML_MODE_LEGACY, this@UrlImageGetter, null)
        } else {
            mTextView.text = Html.fromHtml(mBody, this@UrlImageGetter, null)
        }
    }

    private fun createPicPlaceholder():
            Drawable {
        val drawable: Drawable
        drawable = ColorDrawable(MyApplication.context!!.resources.getColor(R.color.colorGreyDark))
        drawable.setBounds(0, 0, mImgWidth, mImgWidth / 3)
        return drawable
    }
}
