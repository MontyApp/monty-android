package com.monty.tool.photo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;

import com.monty.R;
import com.monty.injection.ApplicationContext;

import java.io.File;
import java.io.InputStream;
import java.util.UUID;

import javax.inject.Inject;

import androidx.annotation.Nullable;
import androidx.exifinterface.media.ExifInterface;
import timber.log.Timber;

public class PhotoAttachmentProcessor {

    private final @ApplicationContext Context context;
    private final TasteWrapper tasteWrapper;

    @Inject
    public PhotoAttachmentProcessor(
            @ApplicationContext Context context,
            TasteWrapper tasteWrapper) {
        this.context = context;
        this.tasteWrapper = tasteWrapper;
    }

    public File getAttachmentFileFromGallery(Uri uri) {
        Bitmap bitmap = tryGetBitmapFromUri(uri, getImageSize());
        File oldImage = new File(uri.getPath());

        if (oldImage.delete()) {
            Timber.i(oldImage.getName() + " - file deleted");
        } else {
            Timber.i(oldImage.getName() + " - file not deleted");
        }

        if (bitmap == null) {
            throw new PhotoAttachmentProcessorException(tasteWrapper.getTResString(R.string.toast_cant_load_photo));
        }

        return getAttachmentFileFromBitmap(bitmap);
    }

    private int getImageSize() {
        return Math.max(tasteWrapper.getTDisplayWindowHeight(), tasteWrapper.getTDisplayWindowWidth());
    }

    private @Nullable Bitmap tryGetBitmapFromUri(Uri uri, int desiredSize) {
        try {
            // Open stream
            final BitmapFactory.Options options = new BitmapFactory.Options();
            InputStream is = context.getContentResolver().openInputStream(uri);
            if (is == null) {
                return null;
            }

            // Check dimensions
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(is, null, options);

            // Calculate sample size
            options.inSampleSize = tasteWrapper.calculateBitmapInSampleSize(options, desiredSize, desiredSize);

            // Reopen stream
            is.close();
            is = context.getContentResolver().openInputStream(uri);
            if (is == null) {
                return null;
            }

            // Decode bitmap
            options.inJustDecodeBounds = false;
            Bitmap resultBitmap = BitmapFactory.decodeStream(is, null, options);

            resultBitmap = correctOrientation(resultBitmap, uri);

            return resultBitmap;
        } catch (Exception e) {
            Timber.e(e);
            return null;
        }
    }

    private File getAttachmentFileFromBitmap(Bitmap bitmap) {
        File file = new File(context.getExternalCacheDir(), UUID.randomUUID() + ".jpg");
        tasteWrapper.saveBitmapToFile(bitmap, file, Bitmap.CompressFormat.JPEG, 70);
        bitmap.recycle();
        return file;
    }

    private Bitmap correctOrientation(Bitmap bmp, Uri uri) {
        try {
            InputStream is = context.getContentResolver().openInputStream(uri);
            if (is == null) {
                return bmp;
            }

            ExifInterface ei = new ExifInterface(is);
            int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_UNDEFINED);

            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    try {
                        bmp = rotateBitmap(bmp, 90);
                    } catch (OutOfMemoryError e) {
                        Timber.e(e);
                    }
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    try {
                        bmp = rotateBitmap(bmp, 180);
                    } catch (OutOfMemoryError e) {
                        Timber.e(e);
                    }
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    try {
                        bmp = rotateBitmap(bmp, 270);
                    } catch (OutOfMemoryError e) {
                        Timber.e(e);
                    }
                    break;
            }

            is.close();
        } catch (Exception e) {
            Timber.e(e);
            // Never mind
        }
        return bmp;
    }

    public static Bitmap rotateBitmap(Bitmap source, float angle) {
        Bitmap retVal;

        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        retVal = Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);

        source.recycle();
        return retVal;
    }
}
