package com.ontecxmedia.ontecx.activities;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;

import com.ontecxmedia.ontecx.Constant;
import com.ontecxmedia.ontecx.NetworkConnection;
import com.ontecxmedia.ontecx.R;
import com.ontecxmedia.ontecx.UserPreferences;
import com.ontecxmedia.ontecx.model.Auth.LoginUserPost;
import com.ontecxmedia.ontecx.model.Auth.Tokens;
import com.ontecxmedia.ontecx.model.Errors.APIError;
import com.ontecxmedia.ontecx.model.Errors.ErrorUtils;
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


public class SignIn extends AppCompatActivity implements View.OnClickListener{

    /** ButterKnife Code **/
    @BindView(R.id.layout_signIn)
    FrameLayout mLayoutSignIn;
    @BindView(R.id.inputLayoutEmail)
    TextInputLayout mInputLayoutEmail;
    @BindView(R.id.email_editxt)
    EditText mEmailEditxt;
    @BindView(R.id.inputLayoutPassword)
    TextInputLayout mInputLayoutPassword;
    @BindView(R.id.password_editxt)
    EditText mPasswordEditxt;
    @BindView(R.id.signin_btn)
    Button mSigninBtn;
    @BindView(R.id.avi1)
    AVLoadingIndicatorView mAvi1;
    @BindView(R.id.register)
    TextView mRegister;
    @BindView(R.id.forget_pass)
    TextView mForgetPass;
    /** ButterKnife Code **/

    UserPreferences userPreferences;


    String user_email="";
    NetworkConnection networkConnection=new NetworkConnection();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        ButterKnife.bind(this);
        mRegister.setPaintFlags(mRegister.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        mRegister.setText("Click to Register");

        mForgetPass.setPaintFlags(mForgetPass.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        mForgetPass.setText("Forgot Password ?");

        userPreferences = new UserPreferences(this);

        Intent intent=getIntent();
        user_email=intent.getStringExtra(Constant.EMAIL);
        setUp();

        mSigninBtn.setOnClickListener(this);
        mRegister.setOnClickListener(this);
        mForgetPass.setOnClickListener(this);
    }

    private void setUp(){
        mEmailEditxt.setText(user_email);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.signin_btn:

                ValidateForm();

                break;

            case R.id.register:
                startActivity(new Intent(SignIn.this,SignUp.class));
                finish();
                break;

            case R.id.forget_pass:
               // startActivity(new Intent(SignIn.this,ForgetPassword.class));
                break;

        }

    }

    private void ValidateForm() {

        if (networkConnection.isNetworkConnected(this)) {
            boolean isValid = true;

            if (mEmailEditxt.getText().toString().isEmpty()) {
                mInputLayoutEmail.setError("Email is required!");
                isValid = false;
            } else if (!isValidEmailAddress(mEmailEditxt.getText().toString())) {
                mInputLayoutEmail.setError("Valid Email is required!");
                isValid = false;
            } else {
                mInputLayoutEmail.setErrorEnabled(false);
            }
            if (mPasswordEditxt.getText().toString().isEmpty()) {
                mInputLayoutPassword.setError("Password is required!");
                isValid = false;
            } else if (mPasswordEditxt.getText().toString().trim().length()<6 && mInputLayoutPassword.isClickable()) {
                mInputLayoutPassword.setError("Your Password must not less than 6 character");
                isValid = false;
            } else {
                mInputLayoutPassword.setErrorEnabled(false);
            }

            if (isValid) {

                //Post Request to Api
               sendData();
              }

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
        mSigninBtn.setVisibility(View.GONE);
        mAvi1.setVisibility(View.VISIBLE);

        LoginUserPost userPostData=new LoginUserPost(mEmailEditxt.getText().toString(),
                mPasswordEditxt.getText().toString()

        );
        Log.i("UserObj",userPostData.toString());

        sentNetworkRequest(userPostData);
    }

    private  void sentNetworkRequest(LoginUserPost userPostObj){
        try {

            //get client and call object for request
            ApiInterface client = ServiceGenerator.createService(ApiInterface.class);

            Call<Tokens> call = client.login(userPostObj);

            call.enqueue(new Callback<Tokens>() {
                @Override
                public void onResponse(Call<Tokens> call, Response<Tokens> response) {
                    if(response.code()==400){
                        showMessage("Check your internet connection");
                        return;
                    }else if(response.code()==429){
                        showMessage("Too many requests on database");
                        return;
                    }else if(response.code()==500){
                        showMessage("Server Error");
                        return;
                    }else if(response.code()==401){
                        showMessage("Unauthorized access, please try login again");
                        return;
                    }


                    try {
                        if (!response.isSuccessful()) {

                            if(response.code()==422){

                                showMessage("Invalid email or password");
                                mSigninBtn.setVisibility(View.VISIBLE);
                                mAvi1.setVisibility(View.GONE);
                                return;
                            }

                            try {
                                APIError apiError = ErrorUtils.parseError(response);

                                showMessage("Login Failed: " + apiError.getErrors());
                                Log.i("Invalid EntryK", apiError.getErrors().toString());
                                Log.i("Invalid Entry", response.errorBody().toString());

                            } catch (Exception e) {
                                Log.i("InvalidEntry", e.getMessage());
                                showMessage("Login Failed");

                            }
                            mSigninBtn.setVisibility(View.VISIBLE);
                            mAvi1.setVisibility(View.GONE);
                            return;
                        }

                        String access_token = response.body().getAccess();
                        String refresh_token = response.body().getRefresh();



                        try {
                            userPreferences.setUserAccessToken(access_token);
                            userPreferences.setUserRefreshToken(refresh_token);

                        }catch (Exception e){
                            Log.i("PrefError", e.getMessage());
                            mSigninBtn.setVisibility(View.VISIBLE);
                            mAvi1.setVisibility(View.GONE);
                        }

                        mSigninBtn.setVisibility(View.VISIBLE);
                        mAvi1.setVisibility(View.GONE);
                        if (user_email != null) {
                            Intent intent = new Intent(SignIn.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            showMessage("Error: " + response.body());
                        }
                    } catch (Exception e) {
                        showMessage("Login Failed: " + e.getMessage());
                        mSigninBtn.setVisibility(View.VISIBLE);
                        mAvi1.setVisibility(View.GONE);
                        Log.i("Login Failed", e.getMessage());
                    }
                }

                @Override
                public void onFailure(Call<Tokens> call, Throwable t) {
                    showMessage("Login Failed " + t.getMessage());
                    Log.i("GEtError", t.getMessage());
                    mSigninBtn.setVisibility(View.VISIBLE);
                    mAvi1.setVisibility(View.GONE);
                }
            });
        }catch (Exception e){
            Log.i("GenError", e.getMessage());
        }


    }

    private void showMessage(String s) {
        Snackbar.make(mLayoutSignIn, s, Snackbar.LENGTH_LONG).show();
    }
}
