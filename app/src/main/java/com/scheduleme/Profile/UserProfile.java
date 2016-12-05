package com.scheduleme.Profile;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.app.Fragment;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.scheduleme.Authentication;
import com.scheduleme.Network.Network;
import com.scheduleme.R;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;
import org.w3c.dom.Text;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by mauricio on 11/9/16.
 */

public class UserProfile extends Fragment implements Callback<ResponseBody> {
    private Button changeProfilePic = null;
    private ImageView userProfile = null;
    private TextView name = null;
    private TextView emailAddress = null;
    private JSONObject data = null;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Personal Profile");

        changeProfilePic = (Button) view.findViewById(R.id.takeProfilePic);
        userProfile = (ImageView) view.findViewById(R.id.profileImage);
        name = (TextView) view.findViewById(R.id.name);
        emailAddress = (TextView) view.findViewById(R.id.emailAddress);
        Network.getInstance().getUser(getActivity()).enqueue(this);
        Picasso.with(getActivity()).load(R.drawable.default_profile_pic).into(userProfile);
        changeProfilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
                    startActivityForResult(intent, 1);
                }
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            userProfile.setBackground(new BitmapDrawable(getResources(),imageBitmap));

        }
    }


    @Override
    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
        if (response.code() == 200) {
            try {
                data = new JSONObject(response.body().string());
                Log.d("user_data", data.toString());
                name.setText(data.getString("name"));
                emailAddress.setText(data.getString("email"));
            } catch (Exception e) {

            }
        }
    }

    @Override
    public void onFailure(Call<ResponseBody> call, Throwable t) {

    }
}
