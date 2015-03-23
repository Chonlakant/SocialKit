package co.aquario.socialkit.activity;

import android.content.Context;
import android.graphics.Bitmap;
import android.hardware.Camera;

import com.commonsware.cwac.camera.PictureTransaction;
import com.commonsware.cwac.camera.SimpleCameraHost;

class MyCameraHost extends SimpleCameraHost {

    private Camera.Size previewSize;

    public MyCameraHost(Context ctxt) {
        super(ctxt);
    }

    @Override
    public boolean useFullBleedPreview() {
        return true;
    }

    @Override
    public Camera.Size getPictureSize(PictureTransaction xact, Camera.Parameters parameters) {
        return previewSize;
    }

    @Override
    public Camera.Parameters adjustPreviewParameters(Camera.Parameters parameters) {
        Camera.Parameters parameters1 = super.adjustPreviewParameters(parameters);
        previewSize = parameters1.getPreviewSize();
        return parameters1;
    }

    @Override
    public void saveImage(PictureTransaction xact, final Bitmap bitmap) {

        /*
        ctxt.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                showTakenPicture(bitmap);
            }
        });
        */
    }

    @Override
    public void saveImage(PictureTransaction xact, byte[] image) {
        super.saveImage(xact, image);
        //photoPath = getPhotoPath();
    }

    @Override
    public boolean useFrontFacingCamera() {
        return false;
    }
}
