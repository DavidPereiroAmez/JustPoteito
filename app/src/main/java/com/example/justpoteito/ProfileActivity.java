package com.example.justpoteito;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.FileUtils;
import android.preference.PreferenceManager;
import android.util.Base64;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.justpoteito.models.RequestResponse;
import com.example.justpoteito.models.UserImage;
import com.example.justpoteito.models.UserResponse;
import com.example.justpoteito.network.NetworkUtilities;
import com.example.justpoteito.network.request.ChangePasswordRequest;
import com.example.justpoteito.network.request.DeleteUserRequest;
import com.example.justpoteito.network.request.DishByIdRequest;
import com.example.justpoteito.network.request.LoginRequest;
import com.example.justpoteito.network.request.SignUpRequest;
import com.example.justpoteito.network.request.UserImageRequest;
import com.example.justpoteito.security.RsaEncrypter;
import com.example.justpoteito.security.RsaFileReader;
import com.example.justpoteito.utilities.FormValidator;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

public class ProfileActivity extends AppCompatActivity {

    private int userId = 0;
    Uri selectedImageUri;
    ImageView userImage;
    private static final int PICK_IMAGE_REQUEST = 1;
    private static final int SELECT_FILE = 1;
    NetworkUtilities networkUtilities;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(ProfileActivity.this);
        userId =  preferences.getInt("user_id", 0);

        setContentView(R.layout.activity_profile);

        userImage = findViewById(R.id.user_image);
        String userRealName = preferences.getString("user_realName", "");
        ((TextView) findViewById(R.id.textView_profileName)).setText(userRealName);

        String surnames = preferences.getString("surnames", "");
        ((TextView) findViewById(R.id.textView_profileSurnames)).setText(surnames);

        String userName = preferences.getString("username", "");
        ((TextView) findViewById(R.id.textView_userName)).setText(userName);

        String email = preferences.getString("email", "");
        ((TextView) findViewById(R.id.textView_profileEmail)).setText(email);

        findViewById(R.id.back_button).setOnClickListener(view -> {
            Intent intent = new Intent(ProfileActivity.this, ExplorerActivity.class);
            startActivity(intent);
            finish();
        });

        findViewById(R.id.image_trash).setOnClickListener(view -> {

            String deleteResponse = new NetworkUtilities(this)
                    .makeRequest(new DeleteUserRequest(userId));

            new Toast(this).makeText(this, deleteResponse, Toast.LENGTH_LONG).show();

            if (deleteResponse.equals("User deleted")) {
                Intent intent = new Intent(ProfileActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        findViewById(R.id.profile_change_button).setOnClickListener(view ->{
            setContentView(R.layout.activity_reset_password);


            findViewById(R.id.resetPassword_button).setOnClickListener(v -> {
                if (passwordFormIsValid()) {
                    String requestJson = generateChangePasswordJson();
                    RequestResponse requestResponse = new NetworkUtilities(this).makeRequest(new ChangePasswordRequest(requestJson, this));

                    new Toast(this).makeText(this, requestResponse.getMessage(), Toast.LENGTH_LONG).show();

                    finish();
                    startActivity(getIntent());
                }
            });

        });
        findViewById(R.id.image_pencil).setOnClickListener(v -> {
            abrirGaleria();
            String userImageJson = generateUserImageJson();
            UserImage userImage = new NetworkUtilities(this).makeRequest(new UserImageRequest(userImageJson, this));
        });
    }

    private boolean passwordFormIsValid() {
        FormValidator formValidator = new FormValidator(this);
        boolean isValid = true;

        if (!formValidator.editTextIsValid(findViewById(R.id.editText_oldPassword), 5, false)) isValid = false;
        if (!formValidator.editTextIsValid(findViewById(R.id.editText_newPassword), 5, false)) isValid = false;
        if (!formValidator.editTextIsValid(findViewById(R.id.editText_confirmPassword), 5, false)) isValid = false;

        EditText newPass = ((EditText) findViewById(R.id.editText_newPassword));
        EditText confirmNewPass = ((EditText) findViewById(R.id.editText_confirmPassword));

        if (!newPass.getText().toString()
                .equals(confirmNewPass.getText().toString())) {

            newPass.setError(getString(R.string.passwords_do_not_match));
            confirmNewPass.setError(getString(R.string.passwords_do_not_match));

            isValid = false;
        }

        return isValid;
    }

    private String generateChangePasswordJson() {

        String encryptedNewPass = encryptText(((EditText) findViewById(R.id.editText_newPassword))
               .getText().toString());

        String encryptedOldPass = encryptText(((EditText) findViewById(R.id.editText_oldPassword))
                .getText().toString());


        return  "{" +
                "\"id\": \"" + userId+ "\"," +
                "\"oldPassword\": \"" + encryptedOldPass + "\"," +
                "\"newPassword\": \"" + encryptedNewPass + "\"" +
                "}";

    }
    private String generateUserImageJson() {

        return  "{" +
                "\"id\": \"" + userId + "\"," +
                "\"image\": \"" + userImage + "\"" +
                "}";
    }

    private String encryptText(String password) {
        byte[] key = RsaFileReader.readRsaFile("public.key", ProfileActivity.this);
        return RsaEncrypter.encryptText(password, key);
    }


    public void abrirGaleria(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(
                Intent.createChooser(intent, "Seleccione una imagen"),
                SELECT_FILE);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            selectedImageUri = data.getData();
            userImage.setImageURI(selectedImageUri);
        }
    }

    public String getBase64EncodedImage(String imageURL) {

        if (imageURL != null) {
            try {
                String imagePath = selectedImageUri.getPath();
                Bitmap fileContent = BitmapFactory.decodeFile(imagePath);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                fileContent.compress(Bitmap.CompressFormat.JPEG, 100, baos); // bm is the bitmap object
                byte[] encodedString = baos.toByteArray();
                String encodedImage = Base64.encodeToString(encodedString, Base64.DEFAULT);
                return encodedImage;
            } catch (Exception e) {
                e.printStackTrace();
                return "";
            }
        } else {
            return "";
        }
    }
}