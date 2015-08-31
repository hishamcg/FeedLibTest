package com.strata.firstmyle_lib.create_post.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.IconTextView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.andreabaccega.widget.FormEditText;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
import com.strata.firstmyle_lib.R;
import com.strata.firstmyle_lib.create_post.model.Biztag;
import com.strata.firstmyle_lib.feed.model.FeedPost;
import com.strata.firstmyle_lib.utils.LibShowToast;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by nagashree on 31/8/15.
 */
public class CreatePostFragment  extends Fragment implements View.OnClickListener{

    private static int RESULT_LOAD_IMG = 1,RESULT_CAPTURE_IMAGE = 2;
    private Uri fileUri;
    private String imgPath;
    private ImageView image;
    private IconTextView btn_link,btn_quote,btn_photo,btn_tag,btn_calender;
    private EditText edit_content,edit_link,edit_quote,edit_tag;
    private FormEditText edit_title;
    private LinearLayout edit_date;
    private TextView btn_txt,date_from,date_to;
    private BizSelectFragment fragment;
    private FrameLayout frame_container;
    private int year, month, day,hour,minute;
    private String date_type = "from",feed_type = "Ask";
    private Biztag bizTag;
    private ProgressDialog progress;
    private Context context;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        context = getActivity().getApplicationContext();
        return inflater.inflate(R.layout.create_feed_layout, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        View view = getView();
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setBackgroundColor(Color.parseColor("#4D4D4D"));
//        view.setSupportActionBar(toolbar);

        frame_container = (FrameLayout)view.findViewById(R.id.frame_container);
        image = (ImageView)view.findViewById(R.id.image);
        btn_txt = (TextView)view.findViewById(R.id.btn_txt);
        date_from = (TextView)view.findViewById(R.id.date_from);
        date_to = (TextView)view.findViewById(R.id.date_to);
        btn_link = (IconTextView)view.findViewById(R.id.btn_link);
        btn_quote = (IconTextView)view.findViewById(R.id.btn_quote);
        btn_photo = (IconTextView)view.findViewById(R.id.btn_photo);
        btn_tag = (IconTextView)view.findViewById(R.id.btn_tag);
        btn_calender = (IconTextView)view.findViewById(R.id.btn_calender);

        date_from.setOnClickListener(this);
        date_to.setOnClickListener(this);

        Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        showDate(year, month + 1, day);
        date_type = "to";
        showDate(year, month + 1, day);

        edit_title = (FormEditText)view.findViewById(R.id.edit_title);
        edit_content = (EditText)view.findViewById(R.id.edit_content);
        edit_link = (EditText)view.findViewById(R.id.edit_link);
        edit_quote = (EditText)view.findViewById(R.id.edit_quote);
        edit_tag = (EditText)view.findViewById(R.id.edit_tag);
        edit_date = (LinearLayout)view.findViewById(R.id.edit_date);

        edit_tag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
//                if (fragment == null) {
//                    Bundle bund = new Bundle();
//                    fragment = new BizSelectFragment();
//                    fragment.setArguments(bund);
//                    FragmentManager fragmentManager = getSupportFragmentManager();
//                    fragmentManager.beginTransaction()
//                            .replace(R.id.frame_container, fragment).commit();
//                }
//                frame_container.setVisibility(View.VISIBLE);
            }

        });

        btn_txt.setOnClickListener(this);
        btn_link.setOnClickListener(this);
        btn_quote.setOnClickListener(this);
        btn_photo.setOnClickListener(this);
        btn_tag.setOnClickListener(this);
        btn_calender.setOnClickListener(this);
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFilterPopup(v);
            }
        });
    }

    private String encodeImagetoString(String imgPath) {
        if(imgPath!=null) {
            Bitmap bit = BitmapFactory.decodeFile(imgPath);
            Bitmap bitmap = getResizedBitmap(bit, 200);
            if(bitmap!=null) {
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 5, stream);
                byte[] byte_arr = stream.toByteArray();
                return Base64.encodeToString(byte_arr, 0);
            }
        }
        return null;
    }

    public static Bitmap getResizedBitmap(Bitmap image, int maxSize) {
        if(image!=null) {
            int width = image.getWidth();
            int height = image.getHeight();

            float bitmapRatio = (float) width / (float) height;
            if (bitmapRatio > 1) {
                width = maxSize;
                height = (int) (width / bitmapRatio);
            } else {
                height = maxSize;
                width = (int) (height * bitmapRatio);
            }
            return Bitmap.createScaledBitmap(image, width, height, true);
        }
        return null;
    }

    private void showFilterPopup(View v) {
        PopupMenu popup = new PopupMenu(getActivity(), v);
        // Inflate the menu from xml
        popup.getMenuInflater().inflate(R.menu.popup_gallery_camera, popup.getMenu());
        // Setup menu item selection
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
                int i = item.getItemId();
                if (i == R.id.gallery) {
                    Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(galleryIntent, RESULT_LOAD_IMG);
                    return true;
                } else if (i == R.id.camera) {
                    String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.ENGLISH).format(new Date());
                    ContentValues values = new ContentValues();
                    values.put(MediaStore.Images.Media.TITLE, "IMG_" + timeStamp + ".png");
                    Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    fileUri = context.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values); // store content values
                    cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
                    startActivityForResult(cameraIntent, RESULT_CAPTURE_IMAGE);
                    return true;
                } else {
                    return false;
                }
            }
        });
        // Handle dismissal with: popup.setOnDismissListener(...);
        // Show the menu
        popup.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        String img_path = "";
        if ((requestCode == RESULT_LOAD_IMG || requestCode == RESULT_CAPTURE_IMAGE)
                && resultCode == Activity.RESULT_OK) {
            try {
                String[] filePathColumn = {MediaStore.Images.Media.DATA};
                if (requestCode == RESULT_CAPTURE_IMAGE) {
                    if (fileUri != null) {

                        Cursor cursor1 = context.getContentResolver().query(fileUri,
                                filePathColumn, null, null, null);
                        cursor1.moveToFirst();
                        int columnIndex = cursor1.getColumnIndex(filePathColumn[0]);
                        img_path = cursor1.getString(columnIndex);
                        cursor1.close();
                    }
                } else if (data != null) {
                    Uri selectedImage = data.getData();
                    Cursor cursor = context.getContentResolver().query(selectedImage,
                            filePathColumn, null, null, null);
                    if (cursor == null) {
                        img_path = selectedImage.getPath();
                    } else {
                        cursor.moveToFirst();
                        int idx = cursor.getColumnIndex(filePathColumn[0]);
                        img_path = cursor.getString(idx);
                        cursor.close();
                    }
                }
                SetImage(img_path);
            } catch (Exception e) {
                LibShowToast.setText("Failed to load your image");
                e.printStackTrace();
            }

        } else {
            LibShowToast.setText("You haven't picked Image");
        }
    }

    private void SetImage(String image_url){
        imgPath = image_url;
        Picasso.with(context)
                .load("file://"+image_url)
                .fit().centerCrop()
                .noFade()
                .placeholder(R.drawable.book_cover)
                .into(image);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if(id == R.id.btn_txt){
            ToggleView(edit_content);
        }else if(id == R.id.btn_link){
            ToggleView(edit_link);
        }else if(id == R.id.btn_quote){
            ToggleView(edit_quote);
        }else if(id == R.id.btn_photo){
            ToggleView(image);
        }else if(id == R.id.btn_tag){
//            if(edit_tag.getVisibility() == View.VISIBLE){
//                edit_tag.setVisibility(View.GONE);
//                feed_type = "Ask";
//            }else{
//                if (fragment == null) {
//                    Bundle bund = new Bundle();
//                    fragment = new BizSelectFragment();
//                    fragment.setArguments(bund);
//                    FragmentManager fragmentManager = getChildFragmentManager();
//                    fragmentManager.beginTransaction()
//                            .replace(R.id.frame_container, fragment).commit();
//                }
//                frame_container.setVisibility(View.VISIBLE);
//            }
//            edit_date.setVisibility(View.GONE);
        }else if(id == R.id.btn_calender){
            if(edit_date.getVisibility() == View.VISIBLE){
                edit_date.setVisibility(View.GONE);
                feed_type = "Ask";
            }else{
                edit_date.setVisibility(View.VISIBLE);
                feed_type = "Event";
            }
            edit_tag.setVisibility(View.GONE);
        }else if(id == R.id.date_from || id == R.id.date_to){
            setDate(v);
        }
    }

    private void ToggleView(View btn){
        if(btn.getVisibility() == View.VISIBLE){
            btn.setVisibility(View.GONE);
        }else{
            btn.setVisibility(View.VISIBLE);
        }
    }


    @SuppressLint("ValidFragment")
    public  class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        public void onDateSet(DatePicker view, int changed_year, int changed_month, int changed_day) {
            year = changed_year;
            month = changed_month;
            day = changed_day;
            showDate(year, month+1, day);
        }
    }

    private  void showDate(int year, int month, int day) {
        if(date_type.equals("to")) {
            date_to.setText(new StringBuilder().append(day).append("/")
                    .append(month).append("/").append(year));
        }else{
            date_from.setText(new StringBuilder().append(day).append("/")
                    .append(month).append("/").append(year));
        }
    }

    public void setDate(View view){
        if(view.getId() == R.id.date_from) {
            date_type = "from";
        }else{
            date_type = "to";
        }
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getFragmentManager(), "datePicker");
    }
}
