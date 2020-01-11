package com.monty.tool.photo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;

import com.thefuntasty.taste.bitmap.TBitmap;
import com.thefuntasty.taste.display.TDisplay;
import com.thefuntasty.taste.file.TFile;
import com.thefuntasty.taste.intent.TIntent;
import com.thefuntasty.taste.res.TRes;

import java.io.File;

import javax.inject.Inject;
import javax.inject.Singleton;

import androidx.annotation.ArrayRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

@Singleton
public class TasteWrapper {

    @Inject
    public TasteWrapper(TRes tRes, TDisplay tDisplay) {
        this.tRes = tRes;
        this.tDisplay = tDisplay;
    }

    private final TDisplay tDisplay;
    private final TRes tRes;


    public int getTResInteger(int id) {
        return tRes.integer(id);
    }

    public String getTResString(int id) {
        return tRes.string(id);
    }

    public String getTResString(int id, Object... formatArgs) {
        return tRes.string(id, formatArgs);
    }

    public String[] getTresStringArray(@ArrayRes int id) {
        return tRes.array(id);
    }

    public String getTResPlurals(int id, int quantity) {
        return tRes.plurals(id, quantity);
    }

    public String getTResPlurals(int id, int quantity, Object... formatArgs) {
        return tRes.plurals(id, quantity, formatArgs);
    }

    public int getTDisplayWindowWidth() {
        return tDisplay.getWindowWidth();
    }

    public int getTDisplayWindowHeight() {
        return tDisplay.getWindowHeight();
    }

    public int getTResPixel(int id) {
        return tRes.pixel(id);
    }

    public int getTResColor(int color) {
        return tRes.color(color);
    }


    public void hideSoftKeyboard(Activity activity) {
        View focusedView = activity.getCurrentFocus();
        if (focusedView != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(focusedView.getWindowToken(), 0);
        }

    }

    public void hideSoftKeyboard(Context context, View focusedView) {
        if (focusedView != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(focusedView.getWindowToken(), 0);
        }

    }

    public void showSoftKeyboard(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        view.requestFocus();
        inputMethodManager.showSoftInput(view, 0);
    }

    public File createTempFile(Context context) {
        return TFile.temp(context);
    }

    // region TIntent

    public Intent createCameraIntent(Uri fileUri) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);

        return intent;
    }

    public Intent createGalleryIntent(boolean allowMultiple) {
        return TIntent.createLibraryIntent(allowMultiple);
    }

    public Bitmap getBitmapFromLibrary(Context context, @NonNull Uri uri, int size) {
        return TBitmap.getScaledBitmapFromLibrary(context, uri, size);
    }

    // endregion

    // region TBitmap

    public void saveBitmapToFile(Bitmap bitmap, File file, @Nullable Bitmap.CompressFormat compressFormat, int quality) {
        TBitmap.saveBitmapToFile(bitmap, file, compressFormat, quality);
    }

    public Bitmap getScaledBitmapFromPath(String path, int size) {
        return TBitmap.getScaledBitmapFromPath(path, size);
    }

    public Bitmap rotateBitmap(Bitmap src, float angle) {
        return TBitmap.rotateImage(src, angle);
    }

    public int calculateBitmapInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        return TBitmap.calculateInSampleSize(options, reqWidth, reqHeight);
    }

    public int getStatusBarHeight(Activity activity) {
        Rect rectangle = new Rect();
        Window window = activity.getWindow();
        window.getDecorView().getWindowVisibleDisplayFrame(rectangle);
        return rectangle.top;
    }
    // endregion
}
