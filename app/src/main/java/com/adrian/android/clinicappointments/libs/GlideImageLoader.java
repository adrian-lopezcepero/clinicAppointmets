package com.adrian.android.clinicappointments.libs;

import android.widget.ImageView;

import com.adrian.android.clinicappointments.libs.base.ImageLoader;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

/**
 * Created by adrian on 12/07/16.
 */
public class GlideImageLoader implements ImageLoader {
    RequestManager glideRequestManager;

    public GlideImageLoader(RequestManager glideRequestManager) {
        this.glideRequestManager = glideRequestManager;
    }

    //    public void setActivity(Activity activity) {
//        glideRequestManager = Glide.with(activity);
//    }

    @Override
    public void load(ImageView imageView, String URL) {
        glideRequestManager
                .load(URL)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .override(200, 200)
                .centerCrop()
                .into(imageView);
    }
}
