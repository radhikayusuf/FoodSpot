package com.foodspot.root.myapp;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.foodspot.root.myapp.Adapter.AdapterComment;
import com.foodspot.root.myapp.HttpService.Comment.CommentRequest;
import com.foodspot.root.myapp.HttpService.Comment.CommentRequestArray;
import com.foodspot.root.myapp.HttpService.LikeRequest.LIkeRequestArray;
import com.foodspot.root.myapp.HttpService.LikeRequest.LikeRequest;
import com.foodspot.root.myapp.HttpService.ServiceHandler;
import com.google.gson.GsonBuilder;
import com.squareup.picasso.Picasso;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class CommentActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    TextView tvJudul, tvName,tvIsi,tvNumLike;
    ImageButton btnRating, btnSubmitComment, btnDeleteStatus;
    ImageView ivContent;
    CircleImageView ivProfile;
    RecyclerView rc_comment;
    EditText etComment;
    Intent i;
    static String ids, id_user_orang;
    ProgressDialog progressDialog, progressDialog1;
    List<CommentRequest> commentRequests =new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        InitComponent();
        i = getIntent();
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Wait a second...");
        progressDialog1 = new ProgressDialog(this);
        progressDialog1.setMessage("Uploading ...");
        progressDialog.show();
        ids = i.getStringExtra("ids");
        id_user_orang = i.getStringExtra("id_user_orang");
        tvName.setText(i.getStringExtra("nama"));
        tvIsi.setText(i.getStringExtra("isi"));
        tvNumLike.setText(i.getStringExtra("num_like")+" Likes This");
        tvJudul.setText(i.getStringExtra("judul"));
        sharedPreferences = getApplicationContext().getSharedPreferences("data",0);
        Picasso.with(getApplicationContext()).load("http://morefoods.hol.es/img/"+i.getStringExtra("pic_content")).into(ivContent);
        Picasso.with(getApplicationContext()).load("http://morefoods.hol.es/img/"+i.getStringExtra("pic_dp")).into(ivProfile);
        new backgroundTask().execute();

    }

    @Override
    protected void onStart() {
        super.onStart();
        btnSubmitComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog1.show();
                new uploadComment(etComment.getText().toString()).execute();
                etComment.setText("");

            }
        });

        if(id_user_orang.equals(sharedPreferences.getString("id_user",""))){
            btnDeleteStatus.setVisibility(View.VISIBLE);
        }else{
            btnDeleteStatus.setVisibility(View.GONE);
        }

        btnDeleteStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.show();
                new DeleteStatus().execute();
            }
        });
    }

    void InitComponent(){
        tvJudul = (TextView) findViewById(R.id.comment_judul);
        tvName = (TextView) findViewById(R.id.comment_name_profile);
        tvIsi = (TextView) findViewById(R.id.comment_date);
        tvNumLike = (TextView) findViewById(R.id.comment_numlike);
        btnRating = (ImageButton) findViewById(R.id.comment_btnRating);
        btnSubmitComment = (ImageButton) findViewById(R.id.comment_btnSubmit);
        ivProfile = (CircleImageView) findViewById(R.id.comment_pic_profile);
        ivContent = (ImageView) findViewById(R.id.comment_img_content);
        rc_comment = (RecyclerView) findViewById(R.id.rc_comment);
        etComment = (EditText) findViewById(R.id.etComment);
        btnDeleteStatus = (ImageButton) findViewById(R.id.deleteStatus);
    }


    class ratingTask extends AsyncTask<Void, Void, Void>{

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            new backgroundTask().execute();

            rc_comment.setAdapter(null);
            AdapterComment adapterDataRestaurant = new AdapterComment(commentRequests,getApplicationContext());
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(rc_comment.getContext());
            rc_comment.setLayoutManager(layoutManager);
            rc_comment.setAdapter(adapterDataRestaurant);
            adapterDataRestaurant.notifyDataSetChanged();
            progressDialog1.dismiss();
        }

        @Override
        protected Void doInBackground(Void... params) {
            ServiceHandler serviceHandler = new ServiceHandler();
//            String URL = "http://morefoods.hol.es/LikeRequest.php?api_key=restoran&action=likeStatus" +
//                    "&id_user=" +sharedPreferences.getString("id_user","kosong") +
//                    "&id_status="+ ids;
//

            String URL = "http://morefoods.hol.es/Like.php?api_key=restoran&action=likeStatus" +
                    "&id_user="+sharedPreferences.getString("id_user","kosong") +
                    "&id_status="+ids;


            Log.d("URL like"," : "+ URL);

            String response = serviceHandler.makeServiceCall(URL, ServiceHandler.GET);
            Log.d("hasil response like", " : "+response);
            return null;
        }
    }

    class unRatingTask extends AsyncTask<Void, Void, Void>{

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            new backgroundTask().execute();

            rc_comment.setAdapter(null);
            AdapterComment adapterDataRestaurant = new AdapterComment(commentRequests,getApplicationContext());
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(rc_comment.getContext());
            rc_comment.setLayoutManager(layoutManager);
            rc_comment.setAdapter(adapterDataRestaurant);
            adapterDataRestaurant.notifyDataSetChanged();
            progressDialog1.dismiss();
        }

        @Override
        protected Void doInBackground(Void... params) {
            ServiceHandler serviceHandler = new ServiceHandler();

            String URL = "http://morefoods.hol.es/Like.php?api_key=restoran&action=unlike" +
                    "&id_user=" +sharedPreferences.getString("id_user","kosong")+
                    "&id_status="+ids;


            Log.d("URL like"," : "+ URL);

            String response = serviceHandler.makeServiceCall(URL, ServiceHandler.GET);
            Log.d("hasil response unlike", " : "+response);
            return null;
        }
    }

    class uploadComment extends AsyncTask<Void, Void, Void>{
        String comment="";

        public uploadComment(String comment) {
            this.comment = comment;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            new backgroundTask().execute();

            rc_comment.setAdapter(null);
            AdapterComment adapterDataRestaurant = new AdapterComment(commentRequests,getApplicationContext());
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(rc_comment.getContext());
            rc_comment.setLayoutManager(layoutManager);
            rc_comment.setAdapter(adapterDataRestaurant);
            adapterDataRestaurant.notifyDataSetChanged();
            progressDialog1.dismiss();
        }

        @Override
        protected Void doInBackground(Void... params) {
            ServiceHandler serviceHandler = new ServiceHandler();
            String url = "http://morefoods.hol.es/Comment.php?api_key=restoran&action=addComment" +
                    "&id_user=" + sharedPreferences.getString("id_user","kosong")+
                    "&id_status="+ ids +
                    "&isi_comment="+ URLEncoder.encode(comment);
            String response = serviceHandler.makeServiceCall(url,ServiceHandler.GET);

            Log.d("hasil comment"," "+response);
            return  null;
        }
    }

    class backgroundTask extends AsyncTask<Void, Void, Void>{

        Boolean b = false;


        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            new checkLike(sharedPreferences.getString("id_user","kosong"),ids).execute();
            if(b==true){
                rc_comment.setAdapter(null);
                AdapterComment adapterDataRestaurant = new AdapterComment(commentRequests,getApplicationContext());
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(rc_comment.getContext());
                rc_comment.setLayoutManager(layoutManager);
                rc_comment.setAdapter(adapterDataRestaurant);
                adapterDataRestaurant.notifyDataSetChanged();
            }
            progressDialog.dismiss();
        }

        @Override
        protected Void doInBackground(Void... params) {
            ServiceHandler serviceHandler = new ServiceHandler();
            String url = "http://morefoods.hol.es/Comment.php?api_key=restoran&action=getCommentByIdStatus&id_status="+ids;
            String response = serviceHandler.makeServiceCall(url,ServiceHandler.GET);

            commentRequests.clear();
            Log.d("response comment", " : "+response);
            if(!response.contains("[]")){
                CommentRequestArray commentRequestArray = new GsonBuilder().create().fromJson(response, CommentRequestArray.class);
                for(CommentRequest cq : commentRequestArray.comment_result){
                        commentRequests.add(cq);
                        Log.d("isi comment 1", " : "+cq.getIsi_comment());
                    }
                b = true;
            }else{
                b=false;
               Log.d("No comment in"," this post");
            }

            return null;
        }
    }

    class checkLike extends AsyncTask<Void, Void, Void>{
        String id_user, id_status, hasil;
        public checkLike(String id_user, String id_status) {
            this.id_user = id_user;
            this.id_status = id_status;
        }

        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Log.d("Hasil likebaru ",hasil);

            Drawable d1= getResources().getDrawable(R.drawable.ic_star_border_white_24dp);
            Drawable d2= getResources().getDrawable(R.drawable.ic_star_white_24dp);


            if(hasil.equals("0")){
                btnRating.setBackground(d1);
                btnRating.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        progressDialog1.show();
                        new ratingTask().execute();
                    }
                });
            }else {
                btnRating.setBackground(d2);
                btnRating.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        progressDialog1.show();
                        new unRatingTask().execute();
                    }
                });
            }
        }

        @Override
        protected Void doInBackground(Void... params) {
            ServiceHandler serviceHandler = new ServiceHandler();
            String url = "http://morefoods.hol.es/Like.php?api_key=restoran&action=isLike" +
                    "&id_user="+id_user+
                    "&id_status="+id_status;

            //http://morefoods.hol.es/Like.php?api_key=restoran&action=isLike&id_user=2&id_status=1

            Log.d("URLLike", " "+url);

            String response = serviceHandler.makeServiceCall(url, ServiceHandler.GET);

            Log.d("Hasil cek like"," "+response);

            LIkeRequestArray lIkeRequestArray = new GsonBuilder().create().fromJson(response, LIkeRequestArray.class);
            for(LikeRequest lq : lIkeRequestArray.likeresult){
                hasil = lq.getCode();
                Log.d("Hasil cek like", " id " +lq.getCode());
            }

            return null;
        }
    }

    class DeleteStatus extends AsyncTask<Void, Void, Void>{


        public DeleteStatus() {

        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            progressDialog.dismiss();
            finish();

        }

        @Override
        protected Void doInBackground(Void... params) {
            ServiceHandler serviceHandler = new ServiceHandler();
           String url = "http://morefoods.hol.es/Status.php?api_key=restoran&action=deleteStatus" +
                   "&id_status="+ids;
            String response = serviceHandler.makeServiceCall(url, ServiceHandler.GET);
            Log.d("Hasil cek delete"," "+response);
            return null;
        }
    }
}
