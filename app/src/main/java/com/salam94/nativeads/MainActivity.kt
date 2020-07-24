package com.salam94.nativeads

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdLoader
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.formats.MediaView
import com.google.android.gms.ads.formats.NativeAdOptions
import com.google.android.gms.ads.formats.UnifiedNativeAd
import com.google.android.gms.ads.formats.UnifiedNativeAdView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val adLoader = AdLoader.Builder(this, "ca-app-pub-3940256099942544/2247696110")
            .forUnifiedNativeAd { unifiedNativeAd : UnifiedNativeAd ->
                // Show the ad.

                val adView = layoutInflater
                    .inflate(R.layout.ad_unified, null) as UnifiedNativeAdView
                populateUnifiedNativeAdView(unifiedNativeAd, adView)
                if (isDestroyed) {
                    unifiedNativeAd.destroy()
                    return@forUnifiedNativeAd
                }

            }
            .withAdListener(object : AdListener() {
                override fun onAdFailedToLoad(errorCode: Int) {
                    // Handle the failure by logging, altering the UI, and so on.
                }
            })
            .withNativeAdOptions(NativeAdOptions.Builder()
                // Methods in the NativeAdOptions.Builder class can be
                // used here to specify individual options settings.
                .build())
            .build()

        adLoader.loadAd(AdRequest.Builder().build())
    }

    private fun populateUnifiedNativeAdView(unifiedNativeAd: UnifiedNativeAd, adView: UnifiedNativeAdView) {
        // Locate the view that will hold the headline, set its text, and use the
        // UnifiedNativeAdView's headlineView property to register it.
        val headlineView = adView.findViewById<TextView>(R.id.ad_headline)
        headlineView.text = unifiedNativeAd.headline
        adView.headlineView = headlineView

        val mediaView = adView.findViewById<MediaView>(R.id.ad_app_icon)
        adView.mediaView = mediaView

        // Call the UnifiedNativeAdView's setNativeAd method to register the
        // NativeAdObject.
        adView.setNativeAd(unifiedNativeAd)

        // Ensure that the parent view doesn't already contain an ad view.
        ad_frame.removeAllViews()

        // Place the AdView into the parent.
        ad_frame.addView(adView)
    }
}