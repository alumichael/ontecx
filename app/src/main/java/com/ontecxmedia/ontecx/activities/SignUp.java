package com.ontecxmedia.ontecx.activities;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;

import com.ontecxmedia.ontecx.Constant;
import com.ontecxmedia.ontecx.NetworkConnection;
import com.ontecxmedia.ontecx.R;
import com.ontecxmedia.ontecx.UserPreferences;
import com.ontecxmedia.ontecx.model.Auth.RegisterUserGet;
import com.ontecxmedia.ontecx.model.Errors.APIError;
import com.ontecxmedia.ontecx.model.Errors.ErrorUtils;
import com.ontecxmedia.ontecx.model.Auth.RegisterUserPost;
import com.ontecxmedia.ontecx.retrofit_interface.ApiInterface;
import com.ontecxmedia.ontecx.retrofit_interface.ServiceGenerator;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUp extends AppCompatActivity implements View.OnClickListener{

    /** ButterKnife Code **/
    @BindView(R.id.layout_signUp)
    CoordinatorLayout mLayoutSignUp;
    @BindView(R.id.inputLayoutFullName)
    TextInputLayout mInputLayoutFullName;
    @BindView(R.id.fullname_editxt)
    EditText mFullnameEditxt;

    @BindView(R.id.inputLayoutUserName)
    TextInputLayout mInputLayoutUserName;
    @BindView(R.id.username_editxt)
    EditText mUserNameEditxt;


    @BindView(R.id.inputLayoutEmail)
    TextInputLayout mInputLayoutEmail;
    @BindView(R.id.email_editxt)
    EditText mEmailEditxt;
    @BindView(R.id.inputLayoutPassword)
    TextInputLayout mInputLayoutPassword;
    @BindView(R.id.password_editxt)
    EditText mPasswordEditxt;

    @BindView(R.id.inputLayoutConfirmPassword)
    TextInputLayout mInputLayoutConfirmPassword;
    @BindView(R.id.confirm_pass_editxt)
    EditText mConfirmPassEdittxt;

    @BindView(R.id.signup_btn)
    Button mSignupBtn;
    @BindView(R.id.avi1)
    AVLoadingIndicatorView mAvi1;
    @BindView(R.id.login)
    TextView mLogin;
    /** ButterKnife Code **/

    UserPreferences userPreferences;

    NetworkConnection networkConnection=new NetworkConnection();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        ButterKnife.bind(this);

        userPreferences=new UserPreferences(this);

        //Underline CLick to Login
        mLogin.setPaintFlags(mLogin.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        mLogin.setText("Click to Login");

        mSignupBtn.setOnClickListener(this);
        mLogin.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.signup_btn:
                ValidateForm();
                break;
            case R.id.login:
                startActivity(new Intent(getApplicationContext(),SignIn.class));
                finish();
                break;

        }

    }


    private void ValidateForm() {

        if (networkConnection.isNetworkConnected(this)) {
            boolean isValid = true;
            if (mFullnameEditxt.getText().toString().isEmpty()) {
                mInputLayoutFullName.setError("Full Name is required");
                isValid = false;
            } else {
                mInputLayoutFullName.setErrorEnabled(false);
            }

            if (mUserNameEditxt.getText().toString().isEmpty()) {
                mInputLayoutUserName.setError("Username is required");
                isValid = false;
            } else {
                mInputLayoutUserName.setErrorEnabled(false);
            }


            if (mEmailEditxt.getText().toString().isEmpty()) {
                mInputLayoutEmail.setError("Email is required!");
                isValid = false;
            } else if (!isValidEmailAddress(mEmailEditxt.getText().toString())&&mInputLayoutEmail.isClickable()) {
                mInputLayoutEmail.setError("Valid Email is required!");
                isValid = false;
            } else {
                mInputLayoutEmail.setErrorEnabled(false);
            }
            if (mPasswordEditxt.getText().toString().isEmpty()&&mInputLayoutPassword.isClickable()) {
                mInputLayoutPassword.setError("Password is required!");
                isValid = false;
            } else if (mPasswordEditxt.getText().toString().trim().length()<6 && mInputLayoutPassword.isClickable()) {
                mInputLayoutPassword.setError("Your Password must not less than 6 character");
                isValid = false;
            } else {
                mInputLayoutPassword.setErrorEnabled(false);
            }
           if (mConfirmPassEdittxt.getText().toString().trim().length()<6) {
                mInputLayoutPassword.setError("Your Password must not less than 6 character");
                isValid = false;
            } else if (! mConfirmPassEdittxt.getText().toString().trim().equals( mPasswordEditxt.getText().toString().trim())) {
               mInputLayoutPassword.setError("Your Password does not match");
               isValid = false;
           } else {
                mInputLayoutConfirmPassword.setErrorEnabled(false);
            }

           
            if (isValid) {

                //Post Request to Api

                sendData();


            }

            //
            return;
        }
        showMessage("No Internet connection discovered!");
    }

    public static boolean isValidEmailAddress(String email) {
        boolean result = true;
        if (null != email) {
            String regex = "^([_a-zA-Z0-9-]+(\\.[_a-zA-Z0-9-]+)*@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*(\\.[a-zA-Z]{1,6}))?$";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(email);
            if (!matcher.matches()) {
                result = false;
            }
        }

        return result;
    }

    private void sendData(){
        mSignupBtn.setVisibility(View.GONE);
        mAvi1.setVisibility(View.VISIBLE);

        /*String email, String name, String password, String username*/

        RegisterUserPost dataPart = new RegisterUserPost(
                mEmailEditxt.getText().toString(),mFullnameEditxt.getText().toString(),
                mPasswordEditxt.getText().toString(),mUserNameEditxt.getText().toString());



        sentNetworkRequest(dataPart);

    }

    private  void sentNetworkRequest(RegisterUserPost regPostData){
        

        //get client and call object for request
        ApiInterface client = ServiceGenerator.createService(ApiInterface.class);

        Call<RegisterUserGet> call=client.register(regPostData);

        call.enqueue(new Callback<RegisterUserGet>() {
            @Override
            public void onResponse(Call<RegisterUserGet> call, Response<RegisterUserGet> response) {

                if(response.code()==400){
                    showMessage("Check your internet connection");
                    mSignupBtn.setVisibility(View.VISIBLE);
                    mAvi1.setVisibility(View.GONE);
                    return;
                }else if(response.code()==429){
                    showMessage("Too many requests on database");
                    mSignupBtn.setVisibility(View.VISIBLE);
                    mAvi1.setVisibility(View.GONE);
                    return;
                }else if(response.code()==500){
                    showMessage("Server Error");
                    mSignupBtn.setVisibility(View.VISIBLE);
                    mAvi1.setVisibility(View.GONE);
                    return;
                }else if(response.code()==401){
                    showMessage("Unauthorized access, please try login again");
                    mSignupBtn.setVisibility(View.VISIBLE);
                    mAvi1.setVisibility(View.GONE);
                    return;
                }


                try {
                    if (!response.isSuccessful()) {

                        try{
                            APIError apiError= ErrorUtils.parseError(response);

                            showMessage("Invalid Entry: "+apiError.getErrors());
                            Log.i("Invalid EntryK",apiError.getErrors().toString());
                            Log.i("Invalid Entry",response.errorBody().toString());
                            mSignupBtn.setVisibility(View.VISIBLE);
                            mAvi1.setVisibility(View.GONE);

                        }catch (Exception e){
                            Log.i("InvalidEntry",e.getMessage());
                            showMessage("Failed to Register: "+e.getMessage());
                            mSignupBtn.setVisibility(View.VISIBLE);
                            mAvi1.setVisibility(View.GONE);

                        }
                        mSignupBtn.setVisibility(View.VISIBLE);
                        mAvi1.setVisibility(View.GONE);
                        return;
                    }

                    if(response.code()==200){


                        userPreferences.setUserAccessToken(response.body().getTokens().getAccess());
                        userPreferences.setUserAccessToken(response.body().getTokens().getRefresh());
                        userPreferences.setUserName(response.body().getUsername());

                        showMessage("Successfully Registered");



                        Intent intent = new Intent(SignUp.this, SignIn.class);
                        intent.putExtra(Constant.EMAIL, response.body().getEmail());
                        startActivity(intent);
                        SignUp.this.finish();
                    }

                    mSignupBtn.setVisibility(View.VISIBLE);
                    mAvi1.setVisibility(View.GONE);

                }catch (Exception e){
                    showMessage("Registration Error: " + e.getMessage());
                }

            }

            @Override
            public void onFailure(Call<RegisterUserGet> call, Throwable t) {
                showMessage("Registration Failed: "+t.getMessage());
                Log.i("GEtError",t.getMessage());
            }
        });



    }

    
    private void showMessage(String s) {
        Snackbar.make(mLayoutSignUp, s, Snackbar.LENGTH_LONG).show();
    }



}
