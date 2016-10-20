package com.foodspot.root.myapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.foodspot.root.myapp.HttpService.Image.ImageRequest;
import com.foodspot.root.myapp.HttpService.Image.ImageRequestArray;
import com.foodspot.root.myapp.HttpService.ServiceHandler;
import com.google.gson.GsonBuilder;
import com.kosalgeek.android.photoutil.CameraPhoto;
import com.kosalgeek.android.photoutil.GalleryPhoto;
import com.kosalgeek.android.photoutil.ImageLoader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class BuffImageActivity extends AppCompatActivity {

    CameraPhoto cameraPhoto;
    GalleryPhoto galleryPhoto;
    final int CAMERA_REQUEST = 1100;
    final int GALLERY_REQUEST = 2200;
    static String selectedImage;
    Bitmap bitmap;
    ImageView ivLook;
    String responsePathImage;
    List<ImageRequest> imageRequestList = new ArrayList<>();
    SharedPreferences sharedPreferences;
    ImageButton btnCamera, btnFolder;
    Button btnUpload;
    ProgressDialog progressDialog;
    String stat = null;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buff_image);
        sharedPreferences = getApplicationContext().getSharedPreferences("data", 0);
        editor = sharedPreferences.edit();
        cameraPhoto = new CameraPhoto(getApplicationContext());
        galleryPhoto = new GalleryPhoto(getApplicationContext());

        btnCamera = (ImageButton) findViewById(R.id.btnCameraSetting);
        btnFolder = (ImageButton) findViewById(R.id.btnFolder);
        ivLook = (ImageView) findViewById(R.id.ivLook);
        btnUpload = (Button) findViewById(R.id.btnUploadSetting);
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(true);
        progressDialog.setMessage("Uploading, Please wait");
        Intent i = getIntent();
        stat = i.getStringExtra("id");
    }

    @Override
    protected void onStart() {
        super.onStart();

        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    startActivityForResult(cameraPhoto.takePhotoIntent(), CAMERA_REQUEST);
                    //cameraPhoto.addToGallery();
                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Error ketika mecoba mengambil gambar "+e.toString(), Toast.LENGTH_LONG).show();
                }
            }
        });

        btnFolder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(galleryPhoto.openGalleryIntent(), GALLERY_REQUEST);
            }
        });

        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(bitmap !=null){
                    progressDialog.show();
                    new uploadImage(bitmap,"testing").execute();
                }else{
                    Toast.makeText(getApplicationContext(),"Choose Image",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("result codenya"," "+String.valueOf(resultCode));
        if (requestCode == GALLERY_REQUEST) {
            if(resultCode!=0){
                Uri uri = data.getData();
                galleryPhoto.setPhotoUri(uri);
                String photoPath = galleryPhoto.getPath();
                try {
                    bitmap = ImageLoader.init().from(photoPath).requestSize(512, 512).getBitmap();
                    ivLook.setImageBitmap(bitmap);
                    selectedImage = photoPath;
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
            //}else(resultCode == CAMERA_REQUEST){
        } else {
            String photoPath = cameraPhoto.getPhotoPath();

            if(!photoPath.equals("")){
                bitmap = null;
                try {
                    bitmap = ImageLoader.init().from(photoPath).getBitmap();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                ivLook.setImageBitmap(bitmap);
                Log.d("Path :", photoPath);
            }
        }
    }

    class uploadImage extends AsyncTask<Void, Void, Void> {

        Bitmap image;
        String name;

        public uploadImage(Bitmap image, String name) {
            this.image = image;
            this.name = name;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            ImageRequestArray imageRequestArray = new GsonBuilder().create().fromJson(responsePathImage, ImageRequestArray.class);
            for(ImageRequest iq : imageRequestArray.file_result){
                imageRequestList.add(iq);
            }


            new backgroundTask(imageRequestList).execute();

            //
        }

        @Override
        protected Void doInBackground(Void... params) {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            image.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);
            String encodeImage = Base64.encodeToString(byteArrayOutputStream.toByteArray(), Base64.DEFAULT);

            ArrayList<NameValuePair> dataToSend = new ArrayList<>();
            dataToSend.add(new BasicNameValuePair("image", encodeImage));
            dataToSend.add(new BasicNameValuePair("name", name));

            HttpParams httpParams = getHttpRequest();

            HttpClient client = new DefaultHttpClient(httpParams);
            HttpPost post = new HttpPost("http://morefoods.hol.es/UploadImagenew.php");


            try {
                post.setEntity(new UrlEncodedFormEntity(dataToSend));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            try {
                client.execute(post);
            } catch (IOException e) {
                e.printStackTrace();
            }

            HttpResponse httpresponse = null;
            try {
                httpresponse = client.execute(post);
            } catch (IOException e) {
                e.printStackTrace();
            }

            HttpEntity httpEntity = httpresponse.getEntity();

            try {
                responsePathImage = EntityUtils.toString(httpEntity);
                Log.d("response upload", ": "+responsePathImage);
            } catch (IOException e) {
                e.printStackTrace();
            }


            return  null;
        }

        private HttpParams getHttpRequest(){
            HttpParams httpParams = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(httpParams, 1000*30);
            HttpConnectionParams.setSoTimeout(httpParams, 1000 * 30);
            return httpParams;
        }

    }

    class backgroundTask extends AsyncTask<Void, Void, Void>{

        List<ImageRequest> imageRequestList;

        public backgroundTask(List<ImageRequest> imageRequest) {
            this.imageRequestList = imageRequest;
        }


        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            progressDialog.dismiss();
            Toast.makeText(getApplicationContext(),"Successfull!!!!!",Toast.LENGTH_LONG).show();

            finish();
        }

        @Override
        protected Void doInBackground(Void... params) {
            ServiceHandler serviceHandler = new ServiceHandler();
            String URL = "http://morefoods.hol.es/Status.php?api_key=restoran" +
                            "&action=update&table=t_user" +
                            "&"+(stat.equals("dp") ? "picture_profile":"picture_background")+"="+imageRequestList.get(0).getFile_name()+
                            "&where=id_user&value="+sharedPreferences.getString("id_user","kosong");

            Log.d("coba :", " "+URL);


            if(stat.equals("dp")) {
                editor.putString("pic_profile", imageRequestList.get(0).getFile_name());
                Log.d("Hasil ganti dp", " "+sharedPreferences.getString("pic_profile","kosong"));
            }else {
                editor.putString("pic_bg", imageRequestList.get(0).getFile_name());
                Log.d("Hasil ganti dp", " "+sharedPreferences.getString("pic_bg","kosong"));
            }
            editor.commit();

            String response = serviceHandler.makeServiceCall(URL, ServiceHandler.GET);

            //Log.d("Hasil :", " "+response);

            return null;
        }
    }
}
