package com.example.myapplication.activities;

import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;

import com.example.myapplication.R;
import com.example.myapplication.databinding.ActivityPaletteBinding;

public class PaletteActivity extends AppCompatActivity {

    private ActivityPaletteBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBinding = DataBindingUtil.setContentView(this,
                R.layout.activity_palette);


        BitmapFactory.Options options = new BitmapFactory.Options();
        // 비트맵 샘플링 (용량줄이기) 2의 배수
        options.inSampleSize = 2;

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),
                R.drawable.man, options);
        createPaletteAsync(bitmap);
    }

    public void createPaletteAsync(Bitmap bitmap) {
        Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
            public void onGenerated(Palette p) {
                // Use generated instance

                Palette.Swatch vibrantSwatch = p.getVibrantSwatch();
                if (vibrantSwatch != null) {
                    mBinding.layout1.setBackgroundColor(vibrantSwatch.getRgb());
                    mBinding.title1.setTextColor(vibrantSwatch.getTitleTextColor());
                    mBinding.content1.setTextColor(vibrantSwatch.getBodyTextColor());
                }
                Palette.Swatch darkVibrantSwatch = p.getDarkVibrantSwatch();
                if (darkVibrantSwatch != null) {
                    mBinding.layout2.setBackgroundColor(darkVibrantSwatch.getRgb());
                    mBinding.title2.setTextColor(darkVibrantSwatch.getTitleTextColor());
                    mBinding.content2.setTextColor(darkVibrantSwatch.getBodyTextColor());
                }
                Palette.Swatch lightVibrantSwatch = p.getLightVibrantSwatch();
                if (lightVibrantSwatch != null) {
                    mBinding.layout3.setBackgroundColor(lightVibrantSwatch.getRgb());
                    mBinding.title3.setTextColor(lightVibrantSwatch.getTitleTextColor());
                    mBinding.content3.setTextColor(lightVibrantSwatch.getBodyTextColor());
                }
                Palette.Swatch mutedSwatch = p.getMutedSwatch();
                if (mutedSwatch != null) {
                    mBinding.layout4.setBackgroundColor(mutedSwatch.getRgb());
                    mBinding.title4.setTextColor(mutedSwatch.getTitleTextColor());
                    mBinding.content4.setTextColor(mutedSwatch.getBodyTextColor());
                }
                Palette.Swatch darkMutedSwatch = p.getDarkMutedSwatch();
                if (darkMutedSwatch != null) {
                    mBinding.layout5.setBackgroundColor(darkMutedSwatch.getRgb());
                    mBinding.title5.setTextColor(darkMutedSwatch.getTitleTextColor());
                    mBinding.content5.setTextColor(darkMutedSwatch.getBodyTextColor());
                }
                Palette.Swatch lightMutedSwatch = p.getLightMutedSwatch();
                if (lightMutedSwatch != null) {
                    mBinding.layout6.setBackgroundColor(lightMutedSwatch.getRgb());
                    mBinding.title6.setTextColor(lightMutedSwatch.getTitleTextColor());
                    mBinding.content6.setTextColor(lightMutedSwatch.getBodyTextColor());
                }
                Palette.Swatch dominantSwatch = p.getDominantSwatch();
                if (dominantSwatch != null) {
                    mBinding.layout7.setBackgroundColor(dominantSwatch.getRgb());
                    mBinding.title7.setTextColor(dominantSwatch.getTitleTextColor());
                    mBinding.content7.setTextColor(dominantSwatch.getBodyTextColor());
                }
            }
        });
    }

}
