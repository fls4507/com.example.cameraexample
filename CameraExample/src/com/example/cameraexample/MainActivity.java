<<<<<<< HEAD
package com.example.cameraexample;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Locale;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

//http://stackoverflow.com/questions/4171352/how-to-use-ocr-web-service-in-android-application-how-can-we-send-request-and-g
public class MainActivity extends Activity {

	private static final String TAG = "CallCamera";
	private static final int CAPTURE_IMAGE_ACTIVITY_REQ = 0;

	Uri fileUri = null;
	ImageView photoImage = null;
	Button btnSubmit;
	SoapServiceHandler soapServ;

	//learning git some more!!
	//MOOOAR
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		soapServ = new SoapServiceHandler(this);		
		photoImage = (ImageView) findViewById(R.id.photo_image);

		Button callCameraButton = (Button) findViewById(R.id.button_callcamera);
		btnSubmit = (Button) findViewById(R.id.btnSubmitPic);

		callCameraButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
				File file = getOutputPhotoFile();
				fileUri = Uri.fromFile(getOutputPhotoFile());
				i.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
				startActivityForResult(i, CAPTURE_IMAGE_ACTIVITY_REQ);

				Log.d(TAG, file.getAbsolutePath());
			}
		});
		btnSubmit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				btnSubmit.setText("sending");
				soapServ.getSoapResponse(fileUri.toString(), getByteArrayOfStoredPhoto());
				btnSubmit.setText("sent");
			}
		});
		soapServ.setOnOCRComplete(new OCREvents() {
			@Override
			public void OCRComplete() {
				Log.i("camera Example", "OCR Complete, From Main");
			}
		});
	}

	private File getOutputPhotoFile() {
		File directory = new File(
				Environment
				.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
				getPackageName());
		if (!directory.exists()) {
			if (!directory.mkdirs()) {
				Log.e(TAG, "Failed to create storage directory.");
				return null;
			}
		}
		String timeStamp = new SimpleDateFormat("yyyMMdd_HHmmss", Locale.US)
		.format(new Date(0));
		return new File(directory.getPath() + File.separator + "IMG_"
				+ timeStamp + ".jpg");
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQ) {
			if (resultCode == RESULT_OK) {
				Uri photoUri = null;
				if (data == null) {
					// A known bug here! The image should have saved in fileUri
					Toast.makeText(this, "Image saved successfully",
							Toast.LENGTH_LONG).show();
					photoUri = fileUri;
				} else {
					photoUri = data.getData();
					Toast.makeText(this,
							"Image saved successfully in: " + data.getData(),
							Toast.LENGTH_LONG).show();
				}

				try {
					showPhoto(photoUri);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else if (resultCode == RESULT_CANCELED) {
				Toast.makeText(this, "Cancelled", Toast.LENGTH_SHORT).show();
			} else {
				Toast.makeText(this, "Callout for image capture failed!",
						Toast.LENGTH_LONG).show();
			}
		}
	}

	private void showPhoto(Uri photoUri) throws IOException {
		String filePath = photoUri.getEncodedPath();
		File imageFile = new File(filePath);
		if (imageFile.exists()){
			byte[] bytes = convertPhotoToByteArray(imageFile.getAbsolutePath());
			Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
			//Bitmap bitmap = BitmapFactory.decodeFile(imageFile.getAbsolutePath());
			BitmapDrawable drawable = new BitmapDrawable(this.getResources(), bitmap);
			photoImage.setScaleType(ImageView.ScaleType.FIT_CENTER);
			photoImage.setImageDrawable(drawable);
		}   	

	}



	public static byte[] convertPhotoToByteArray(String sourcePath) throws IOException {
		File f = new File(sourcePath);
		long l = f.length();
		byte [] buf = new byte[(int) l];
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		try {
			InputStream fis = new FileInputStream(sourcePath);

			for (int readNum; (readNum = fis.read(buf)) != -1;) {
				bos.write(buf, 0, readNum);
				Log.i("","read num bytes: "+readNum);
			}
		} catch (IOException e) {
			System.out.println("IO Ex"+e);
		}
		byte[] bytes = bos.toByteArray();
		return bytes;
	}

	public byte[] getByteArrayOfStoredPhoto(){
		byte[] bytes = null;
		String filePath = fileUri.getEncodedPath();
		File imageFile = new File(filePath);
		if (imageFile.exists()){
			try {
				bytes = convertPhotoToByteArray(imageFile.getAbsolutePath());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}   	
		return bytes;
	}

=======
package com.example.cameraexample;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Locale;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

//http://stackoverflow.com/questions/4171352/how-to-use-ocr-web-service-in-android-application-how-can-we-send-request-and-g
public class MainActivity extends Activity {

	private static final String TAG = "CallCamera";
	private static final int CAPTURE_IMAGE_ACTIVITY_REQ = 0;

	Uri fileUri = null;
	ImageView photoImage = null;
	Button btnSubmit;
	SoapServiceHandler soapServ;

	//learning git some more!!
	//Lets encounter a fast-forward error and do some merging.
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		soapServ = new SoapServiceHandler(this);		
		photoImage = (ImageView) findViewById(R.id.photo_image);

		Button callCameraButton = (Button) findViewById(R.id.button_callcamera);
		btnSubmit = (Button) findViewById(R.id.btnSubmitPic);

		callCameraButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
				File file = getOutputPhotoFile();
				fileUri = Uri.fromFile(getOutputPhotoFile());
				i.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
				startActivityForResult(i, CAPTURE_IMAGE_ACTIVITY_REQ);

				Log.d(TAG, file.getAbsolutePath());
			}
		});
		btnSubmit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				btnSubmit.setText("sending");
				soapServ.getSoapResponse(fileUri.toString(), getByteArrayOfStoredPhoto());
				btnSubmit.setText("sent");
			}
		});
		soapServ.setOnOCRComplete(new OCREvents() {
			@Override
			public void OCRComplete() {
				Log.i("camera Example", "OCR Complete, From Main");
			}
		});
	}

	private File getOutputPhotoFile() {
		File directory = new File(
				Environment
				.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
				getPackageName());
		if (!directory.exists()) {
			if (!directory.mkdirs()) {
				Log.e(TAG, "Failed to create storage directory.");
				return null;
			}
		}
		String timeStamp = new SimpleDateFormat("yyyMMdd_HHmmss", Locale.US)
		.format(new Date(0));
		return new File(directory.getPath() + File.separator + "IMG_"
				+ timeStamp + ".jpg");
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQ) {
			if (resultCode == RESULT_OK) {
				Uri photoUri = null;
				if (data == null) {
					// A known bug here! The image should have saved in fileUri
					Toast.makeText(this, "Image saved successfully",
							Toast.LENGTH_LONG).show();
					photoUri = fileUri;
				} else {
					photoUri = data.getData();
					Toast.makeText(this,
							"Image saved successfully in: " + data.getData(),
							Toast.LENGTH_LONG).show();
				}

				try {
					showPhoto(photoUri);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else if (resultCode == RESULT_CANCELED) {
				Toast.makeText(this, "Cancelled", Toast.LENGTH_SHORT).show();
			} else {
				Toast.makeText(this, "Callout for image capture failed!",
						Toast.LENGTH_LONG).show();
			}
		}
	}

	private void showPhoto(Uri photoUri) throws IOException {
		String filePath = photoUri.getEncodedPath();
		File imageFile = new File(filePath);
		if (imageFile.exists()){
			byte[] bytes = convertPhotoToByteArray(imageFile.getAbsolutePath());
			Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
			//Bitmap bitmap = BitmapFactory.decodeFile(imageFile.getAbsolutePath());
			BitmapDrawable drawable = new BitmapDrawable(this.getResources(), bitmap);
			photoImage.setScaleType(ImageView.ScaleType.FIT_CENTER);
			photoImage.setImageDrawable(drawable);
		}   	

	}



	public static byte[] convertPhotoToByteArray(String sourcePath) throws IOException {
		File f = new File(sourcePath);
		long l = f.length();
		byte [] buf = new byte[(int) l];
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		try {
			InputStream fis = new FileInputStream(sourcePath);

			for (int readNum; (readNum = fis.read(buf)) != -1;) {
				bos.write(buf, 0, readNum);
				Log.i("","read num bytes: "+readNum);
			}
		} catch (IOException e) {
			System.out.println("IO Ex"+e);
		}
		byte[] bytes = bos.toByteArray();
		return bytes;
	}

	public byte[] getByteArrayOfStoredPhoto(){
		byte[] bytes = null;
		String filePath = fileUri.getEncodedPath();
		File imageFile = new File(filePath);
		if (imageFile.exists()){
			try {
				bytes = convertPhotoToByteArray(imageFile.getAbsolutePath());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}   	
		return bytes;
	}

>>>>>>> c6233ca5103647394e54cbb59748a9ef68ad086e
}