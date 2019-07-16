
package com.reactlibrary;

import android.os.Handler;
import android.os.Looper;
import android.telecom.Call;
import android.util.Log;

import com.adcolony.sdk.AdColony;
import com.adcolony.sdk.AdColonyAppOptions;
import com.adcolony.sdk.AdColonyInterstitial;
import com.adcolony.sdk.AdColonyInterstitialListener;
import com.adcolony.sdk.AdColonyReward;
import com.adcolony.sdk.AdColonyRewardListener;
import com.adcolony.sdk.AdColonyZone;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.Callback;

import java.util.HashMap;

public class RNAdcolonyModule extends ReactContextBaseJavaModule {

  private final ReactApplicationContext reactContext;
  private static final String TAG = "RNAdcolony";
  private final HashMap<String, AdColonyInterstitial> mAds = new HashMap<>();
  private final HashMap<String, Callback> mClosedCallbacks = new HashMap<>();
  private final HashMap<String, Callback> mRewardCallbacks = new HashMap<>();

  public RNAdcolonyModule(ReactApplicationContext reactContext) {
    super(reactContext);
    this.reactContext = reactContext;
  }

  private final AdColonyRewardListener mRewardListener = new AdColonyRewardListener() {
    @Override
    public void onReward(final AdColonyReward reward) {
      final Callback callback = mRewardCallbacks.remove(reward.getZoneID());

      if (callback != null) {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
          @Override
          public void run() {
            callback.invoke(reward.getRewardName(), reward.getRewardAmount());
          }
        });
      }
    }
  };

  private boolean innerIsReady(final String zoneId) {
    final AdColonyInterstitial ad = mAds.get(zoneId);
    return ad != null && !ad.isExpired();
  }

  private void innerLoadAds(final String zoneId) {
    AdColony.requestInterstitial(zoneId, new AdColonyInterstitialListener() {
      @Override
      public void onRequestFilled(AdColonyInterstitial adColonyInterstitial) {
        Log.d(TAG, zoneId + ": onRequestFilled");
        mAds.put(zoneId, adColonyInterstitial);
      }

      @Override
      public void onRequestNotFilled(AdColonyZone zone) {
        Log.d(TAG, zoneId + ": onRequestNotFilled");
        super.onRequestNotFilled(zone);
      }

      @Override
      public void onOpened(AdColonyInterstitial ad) {
        Log.d(TAG, zoneId + ": onOpened");
        if (!mAds.containsKey(zoneId) || mAds.get(zoneId) == ad) {
          mAds.remove(zoneId);
          AdColony.requestInterstitial(zoneId, this);
        }
        super.onOpened(ad);
      }

      @Override
      public void onExpiring(AdColonyInterstitial ad) {
        Log.d(TAG, zoneId + ": onExpiring");
        if (!mAds.containsKey(zoneId) || mAds.get(zoneId) == ad) {
          mAds.remove(zoneId);
          AdColony.requestInterstitial(zoneId, this);
        }
        super.onExpiring(ad);
      }

      @Override
      public void onClosed(AdColonyInterstitial ad) {
        final Callback callback = mClosedCallbacks.remove(ad.getZoneID());

        if (callback != null) {
          new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
              callback.invoke();
            }
          });
        }
        super.onClosed(ad);
      }
    });
  }

  private void innerShowAd(final String zoneId) {
    new Handler(Looper.getMainLooper()).post(new Runnable() {
      @Override
      public void run() {
        AdColonyInterstitial ad = mAds.get(zoneId);
        if (ad == null || ad.isExpired()) {
          innerLoadAds(zoneId);
        } else {
          ad.show();
        }
      }
    });
  }

  @ReactMethod
  public void setUser(final String userId) {
    AdColonyAppOptions appOptions = AdColony.getAppOptions().setUserID(userId);
    AdColony.setAppOptions(appOptions);
  }

  @ReactMethod
  public void isReady(final String zoneId, final Callback callback) {
    new Handler(Looper.getMainLooper()).post(new Runnable() {
      @Override
      public void run() {
        callback.invoke(innerIsReady(zoneId));
      }
    });
  }

  @ReactMethod
  public void loadAds(final String zoneId) {
    AdColony.setRewardListener(mRewardListener);
    innerLoadAds(zoneId);
  }

  @ReactMethod
  public void showAdInterstitial(final String zoneId, final Callback callback) {
    mClosedCallbacks.put(zoneId, callback);
    innerShowAd(zoneId);
  }

  @ReactMethod
  public void showAdReward(final String zoneId, final Callback callback) {
    mRewardCallbacks.put(zoneId, callback);
    innerShowAd(zoneId);
  }

  @ReactMethod
  public void configure(final String appId,final String zoneId) {
    AdColony.configure(getCurrentActivity(), appId, zoneId);
  }

  @Override
  public String getName() {
    return "RNAdcolony";
  }
}
