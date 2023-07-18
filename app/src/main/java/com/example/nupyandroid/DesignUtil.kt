package com.example.nupyandroid

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.util.TypedValue
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

object DesignUtil {
    @JvmStatic
    @BindingAdapter("circleImageUrl")
    fun ImageView.setCircleImageUrl(url: String?) {
        url?.let {
            Glide.with(context)
                .load(it)
                .thumbnail(0.1f)
                .error(
                    ResourcesCompat.getDrawable(
                        resources,
                        R.drawable.ic_upload_img_default,
                        null
                    )
                )
                .circleCrop()
                .placeholder(ColorDrawable(Color.parseColor("#DEE2E6")))
                .into(this)
        }
    }


    @JvmStatic
    @BindingAdapter("imageUrl")
    fun ImageView.setImageUrl(url: String?) {
        url?.let {
            Glide.with(context)
                .load(it)
                .thumbnail(0.1f)
                .error(
                    ResourcesCompat.getDrawable(resources, R.drawable.ic_upload_img_default, null)
                )
                .placeholder(ColorDrawable(Color.parseColor("#DEE2E6")))
                .into(this)
        }
    }

    @JvmStatic
    @BindingAdapter("imageUrl300")
    fun ImageView.setImageUrl300(url: String?) {
        url?.let {
            Glide.with(context)
                .load(it)
                .override(300, 300)
                .thumbnail(0.1f)
                .placeholder(ColorDrawable(Color.parseColor("#DEE2E6")))

                .into(this)
        }
    }

    @JvmStatic
    fun dpToPx(context: Context, dp: Int): Int {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            dp.toFloat(),
            context.resources.displayMetrics
        ).toInt()
    }

    @JvmStatic
    @BindingAdapter("historyType")
    fun TextView.setHistoryType(boolean: Boolean) {
        if (boolean) {
            setTextColor(this.resources.getColor(R.color.green))
            background = this.resources.getDrawable(R.drawable.box_r100_green)
            text = "Obtaining completion"
        } else {
            setTextColor(this.resources.getColor(R.color.red))
            background = this.resources.getDrawable(R.drawable.box_r100_red)
            text = "overdue"
        }
    }

    @JvmStatic
    @BindingAdapter("visibleGone")
    fun View.setVisibleGone(isVisible: Boolean) {
        visibility = if (isVisible) View.VISIBLE else View.GONE
    }

    @JvmStatic
    @BindingAdapter("visibleInvisible")
    fun View.setVisibleInvisible(isVisible: Boolean) {
        visibility = if (isVisible) View.VISIBLE else View.INVISIBLE
    }

}
