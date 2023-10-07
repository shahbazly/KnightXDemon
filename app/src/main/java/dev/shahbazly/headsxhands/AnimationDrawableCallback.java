package dev.shahbazly.headsxhands;

import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;

public abstract class AnimationDrawableCallback implements Drawable.Callback {

    /**
     * The last frame of {@link Drawable} in the {@link AnimationDrawable}.
     */
    private Drawable mLastFrame;

    private Drawable.Callback mWrappedCallback;


    private boolean mIsCallbackTriggered = false;


    public AnimationDrawableCallback(AnimationDrawable animationDrawable, Drawable.Callback callback) {
        mLastFrame = animationDrawable.getFrame(animationDrawable.getNumberOfFrames() - 1);
        mWrappedCallback = callback;
    }

    @Override
    public void invalidateDrawable(Drawable who) {
        if (mWrappedCallback != null) {
            mWrappedCallback.invalidateDrawable(who);
        }

        if (!mIsCallbackTriggered && mLastFrame != null && mLastFrame.equals(who.getCurrent())) {
            mIsCallbackTriggered = true;
            onAnimationComplete();
        }
    }

    @Override
    public void scheduleDrawable(Drawable who, Runnable what, long when) {
        if (mWrappedCallback != null) {
            mWrappedCallback.scheduleDrawable(who, what, when);
        }
    }

    @Override
    public void unscheduleDrawable(Drawable who, Runnable what) {
        if (mWrappedCallback != null) {
            mWrappedCallback.unscheduleDrawable(who, what);
        }
    }

    public abstract void onAnimationComplete();
}