package co.aquario.socialkit.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v4.app.NavUtils;
import android.support.v4.app.TaskStackBuilder;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewTreeObserver;
import android.view.animation.OvershootInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxStatus;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import co.aquario.socialkit.MainApplication;
import co.aquario.socialkit.R;
import co.aquario.socialkit.util.AndroidMultiPartEntity;
import co.aquario.socialkit.util.PrefManager;

public class PostPhotoActivity extends Activity implements OnClickListener {

	Context context;
	Button select_photo;
	Button take_photo;
	Button post_photo;
	ImageView imageView;
	EditText photoText;

	File tempFile;

	AQuery aq;

	Bitmap bmpByteArray;

    public static final String ARG_TAKEN_PHOTO_URI = "arg_taken_photo_uri";
    private Uri photoUri;
    private int photoSize;


    public static void openWithPhotoUri(Activity openingActivity, Uri photoUri) {
        Intent intent = new Intent(openingActivity, PostPhotoActivity.class);
        intent.putExtra(ARG_TAKEN_PHOTO_URI, photoUri);
        openingActivity.startActivity(intent);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(ARG_TAKEN_PHOTO_URI, photoUri);
    }

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_post_photo);


		//String path = getIntent().getExtras().getString("photo");
		//String rotate = getIntent().getExtras().getString("rotate");

        boolean tabletSize = getResources().getBoolean(R.bool.isTablet);
        if (tabletSize) {
            photoSize = getResources().getDimensionPixelSize(R.dimen.publish_photo_thumbnail_size_tablet);;
        } else {
            photoSize = getResources().getDimensionPixelSize(R.dimen.publish_photo_thumbnail_size);;
        }

        if (savedInstanceState == null) {
            photoUri = getIntent().getParcelableExtra(ARG_TAKEN_PHOTO_URI);

        } else {
            photoUri = savedInstanceState.getParcelable(ARG_TAKEN_PHOTO_URI);

        }

        tempFile = new File(getPath(context,photoUri));

		context = this;
		aq = new AQuery(context);

		select_photo = (Button) findViewById(R.id.button_photo);
		take_photo = (Button) findViewById(R.id.button_video);
		post_photo = (Button) findViewById(R.id.button_recent);

		imageView = (ImageView) findViewById(R.id.image);

		// Bitmap b = BitmapFactory.decodeFile(path);

		imageView.setImageBitmap(bmpByteArray);

        imageView.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                imageView.getViewTreeObserver().removeOnPreDrawListener(this);
                loadThumbnailPhoto();
                return true;
            }
        });


		photoText = (EditText) findViewById(R.id.comment_box);

		select_photo.setOnClickListener(this);
		take_photo.setOnClickListener(this);
		post_photo.setOnClickListener(this);

		//getActionBar().setTitle("แบ่งปันรูปภาพ");
		//getActionBar().setDisplayHomeAsUpEnabled(true);

	}

    public static String getPath(final Context context, final Uri uri) {

        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/"
                            + split[1];
                }

                // TODO handle non-primary volumes
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {

                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"),
                        Long.valueOf(id));

                return getDataColumn(context, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[] { split[1] };

                return getDataColumn(context, contentUri, selection,
                        selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {
            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }

    /**
     * Get the value of the data column for this Uri. This is useful for
     * MediaStore Uris, and other file-based ContentProviders.
     *
     * @param context
     *            The context.
     * @param uri
     *            The Uri to query.
     * @param selection
     *            (Optional) Filter used in the query.
     * @param selectionArgs
     *            (Optional) Selection arguments used in the query.
     * @return The value of the _data column, which is typically a file path.
     */
    public static String getDataColumn(Context context, Uri uri,
                                       String selection, String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = { column };

        try {
            cursor = context.getContentResolver().query(uri, projection,
                    selection, selectionArgs, null);
            if (cursor != null && cursor.moveToFirst()) {
                final int column_index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(column_index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }

    /**
     * @param uri
     *            The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri
                .getAuthority());
    }

    /**
     * @param uri
     *            The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri
                .getAuthority());
    }

    /**
     * @param uri
     *            The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri
                .getAuthority());
    }

    private void loadThumbnailPhoto() {
        imageView.setScaleX(0);
        imageView.setScaleY(0);
        Picasso.with(this)
                .load(photoUri)
                .centerCrop()
                .resize(photoSize, photoSize)
                .into(imageView, new Callback() {
                    @Override
                    public void onSuccess() {
                        imageView.animate()
                                .scaleX(1.f).scaleY(1.f)
                                .setInterpolator(new OvershootInterpolator())
                                .setDuration(400)
                                .setStartDelay(200)
                                .start();
                    }

                    @Override
                    public void onError() {
                    }
                });
    }

	public Bitmap decodeFile(String path) {// you can provide file path here
		int orientation;
		try {
			if (path == null) {
				return null;
			}
			// decode image size
			BitmapFactory.Options o = new BitmapFactory.Options();
			o.inJustDecodeBounds = true;
			// Find the correct scale value. It should be the power of 2.
			final int REQUIRED_SIZE = 70;
			int width_tmp = o.outWidth, height_tmp = o.outHeight;
			int scale = 0;
			while (true) {
				if (width_tmp / 2 < REQUIRED_SIZE
						|| height_tmp / 2 < REQUIRED_SIZE)
					break;
				width_tmp /= 2;
				height_tmp /= 2;
				scale++;
			}
			// decode with inSampleSize
			BitmapFactory.Options o2 = new BitmapFactory.Options();
			o2.inSampleSize = scale;
			Bitmap bm = BitmapFactory.decodeFile(path, o2);
			Bitmap bitmap = bm;

			ExifInterface exif = new ExifInterface(path);

			orientation = exif
					.getAttributeInt(ExifInterface.TAG_ORIENTATION, 1);

			Log.e("ExifInteface .........", "rotation =" + orientation);

			// exif.setAttribute(ExifInterface.ORIENTATION_ROTATE_90, 90);

			Log.e("orientation", "" + orientation);
			Matrix m = new Matrix();

			if ((orientation == ExifInterface.ORIENTATION_ROTATE_180)) {
				m.postRotate(180);
				// m.postScale((float) bm.getWidth(), (float) bm.getHeight());
				// if(m.preRotate(90)){
				Log.e("in orientation", "" + orientation);
				bitmap = Bitmap.createBitmap(bm, 0, 0, bm.getWidth(),
                        bm.getHeight(), m, true);
				return bitmap;
			} else if (orientation == ExifInterface.ORIENTATION_ROTATE_90) {
				m.postRotate(90);
				Log.e("in orientation", "" + orientation);
				bitmap = Bitmap.createBitmap(bm, 0, 0, bm.getWidth(),
                        bm.getHeight(), m, true);
				return bitmap;
			} else if (orientation == ExifInterface.ORIENTATION_ROTATE_270) {
				m.postRotate(270);
				Log.e("in orientation", "" + orientation);
				bitmap = Bitmap.createBitmap(bm, 0, 0, bm.getWidth(),
                        bm.getHeight(), m, true);
				return bitmap;
			}
			return bitmap;
		} catch (Exception e) {
			return null;
		}
	}

	public static Bitmap RotateBitmap(Bitmap source, float angle) {
		Matrix matrix = new Matrix();
		matrix.postRotate(angle);
		return Bitmap.createBitmap(source, 0, 0, source.getWidth(),
                source.getHeight(), matrix, true);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		return true;
	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {

		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			Intent upIntent = new Intent(this, MainActivity.class);
			if (NavUtils.shouldUpRecreateTask(this, upIntent)) {
				TaskStackBuilder.from(this).addNextIntent(upIntent)
						.startActivities();
				finish();
			} else {
				NavUtils.navigateUpTo(this, upIntent);
			}
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

    public String url = "https://www.vdomax.com/ajax.php?t=post&a=new&user_id=6&token=123456&user_pass=039a726ac0aeec3dde33e45387a7d4ac";
    public long totalSize;
    public ProgressDialog dialog;
    public String statusText;

	@Override
	public void onClick(View v) {


		if (v.getId() == R.id.button_recent) {


            statusText = photoText.getText().toString()
                    .replace("\n", "%0A");
            //statusText.toString().trim().replaceAll("\\s+", " ");

            Pattern pattern = Pattern.compile("\\s");
            Matcher matcher = pattern.matcher(statusText);
            boolean found = matcher.find();
            boolean isWhitespace = statusText.matches("^\\s*$");

            if (statusText.length() == 0 || statusText.trim().equals("") || found || isWhitespace) {
                photoText.setError("กรุณาพิมพ์ข้อความก่อนส่ง");
                Log.e("YEAH", statusText.length() + " " + statusText.trim() + " " + found + " " + isWhitespace);
            }

            statusText = photoText.getText().toString();

			dialog = new ProgressDialog(context);
			dialog.setIndeterminate(true);
			dialog.setCancelable(false);
			dialog.setInverseBackgroundForced(false);
			dialog.setCanceledOnTouchOutside(false);
			dialog.setTitle("Uploading");
			dialog.setMessage("กำลังอัพโหลดรูปภาพ..");
			dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
			dialog.setIndeterminate(false);
			dialog.setMax(100);

            new UploadFileToServer().execute();




			//aq.progress(dialog).ajax(url, params, JSONObject.class, this,
			//		"postPhotoCb");

		}

	}



    private class UploadFileToServer extends AsyncTask<Void, Integer, String> {
        @Override
        protected void onPreExecute() {
            // setting progress bar to zero
            dialog.show();
            dialog.setProgress(0);
            super.onPreExecute();
        }

        @Override
        protected void onProgressUpdate(Integer... progress) {
            // Making progress bar visible
            //progressBar.setVisibility(View.VISIBLE);

            // updating progress bar value
            dialog.setProgress(progress[0]);

            // updating percentage value
            dialog.setTitle(String.valueOf(progress[0]) + "%");
            //dialog.setText();
        }

        @Override
        protected String doInBackground(Void... params) {
            return uploadFile();
        }

        @SuppressWarnings("deprecation")
        private String uploadFile() {
            String responseString = null;

            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(url);

            try {
                AndroidMultiPartEntity entity = new AndroidMultiPartEntity(
                        new AndroidMultiPartEntity.ProgressListener() {

                            @Override
                            public void transferred(long num) {
                                publishProgress((int) ((num / (float) totalSize) * 100));
                                dialog.setProgress((int) ((num / (float) totalSize) * 100));
                            }
                        });

                File sourceFile = tempFile;

                PrefManager pref = MainApplication.get(getApplicationContext()).getPrefManager();
                String userId = pref.userId().getOr("1301");


                Charset chars = Charset.forName("UTF-8");

                entity.addPart("timeline_id", new StringBody(userId));
                entity.addPart("recipient_id", new StringBody(""));
                entity.addPart("text",
                        new StringBody(statusText,chars));
                entity.addPart("photos[]", new FileBody(sourceFile));

                totalSize = entity.getContentLength();
                httppost.setEntity(entity);

                // Making server call
                HttpResponse response = httpclient.execute(httppost);
                HttpEntity r_entity = response.getEntity();

                int statusCode = response.getStatusLine().getStatusCode();
                if (statusCode == 200) {
                    // Server response
                    responseString = EntityUtils.toString(r_entity);
                } else {
                    responseString = "Error occurred! Http Status Code: "
                            + statusCode;
                }

            } catch (ClientProtocolException e) {
                responseString = e.toString();
            } catch (IOException e) {
                responseString = e.toString();
            }

            return responseString;

        }

        @Override
        protected void onPostExecute(String result) {
            Log.e("HEYHEY", "Response from server: " + result);

            // showing the server response in an alert dialog
            dialog.dismiss();
            Intent i = new Intent(PostPhotoActivity.this,MainActivity.class);
            startActivity(i);
            //showAlert(result);
            super.onPostExecute(result);
        }

    }

    /**
     * Method to show alert dialog
     * */
    private void showAlert(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(message).setTitle("Response from Servers")
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }



	public void postPhotoCb(String url, JSONObject jo, AjaxStatus status)
			throws JSONException {
		if (jo != null) {
			Log.e("check", jo.toString());
            Intent intent = new Intent(PostPhotoActivity.this,
                    MainActivity.class);
            startActivity(intent);

		}
	}

}
