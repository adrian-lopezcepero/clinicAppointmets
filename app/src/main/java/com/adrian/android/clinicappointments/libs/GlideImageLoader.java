package com.adrian.android.clinicappointments.libs;

import android.app.Fragment;
import android.widget.ImageView;

import com.adrian.android.clinicappointments.libs.base.ImageLoader;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

/**
 * Created by adrian on 12/07/16.
 */
public class GlideImageLoader implements ImageLoader {
    private RequestManager glideRequestManager;

    public void setLoaderContext(Fragment fragment) {
        this.glideRequestManager = Glide.with(fragment);
    }

    @Override
    public void load(ImageView imageView, String URL) {
        glideRequestManager.
                load(URL)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .override(200, 200)
                .centerCrop()
                .into(imageView);
    }
}
