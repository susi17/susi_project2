package com.example.orderkuy.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.orderkuy.AppController;
import com.example.orderkuy.R;
import com.example.orderkuy.ServerSide.URL;
import com.example.orderkuy.network.Server;
import com.valdesekamdem.library.mdtoast.MDToast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Register extends AppCompatActivity {

    ProgressDialog pDialog;
    Intent intent;

    @BindView(R.id.btn_login) TextView btn_login;
    @BindView(R.id.btn_register) Button btn_register;
    @BindView(R.id.txt_username) TextView txt_username;
    @BindView(R.id.txt_password) TextView txt_password;
    @BindView(R.id.txt_confirm_password) TextView txt_confirm_password;

    int success;
    ConnectivityManager conMgr;

    private String url = URL.register;
    private static final String TAG = Register.class.getSimpleName();
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";

    String tag_json_obj = "json_obj_req";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        ButterKnife.bind(this);  //Instance ButteKnife

        conMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        {
            if (conMgr.getActiveNetworkInfo() != null
                    && conMgr.getActiveNetworkInfo().isAvailable()
                    && conMgr.getActiveNetworkInfo().isConnected()) {
            } else {
                MDToast mdToast = MDToast.makeText(Register.this ,"No Internet Connection", Toast.LENGTH_LONG, MDToast.TYPE_ERROR);
                mdToast.show();
            }
        }


        btn_login.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                intent = new Intent(Register.this, Login.class);
                finish();
                startActivity(intent);
            }
        });

        btn_register.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                String username = txt_username.getText().toString();
                String password = txt_password.getText().toString();
                String confirm_password = txt_confirm_password.getText().toString();



                    if (conMgr.getActiveNetworkInfo() != null
                            && conMgr.getActiveNetworkInfo().isAvailable()
                            && conMgr.getActiveNetworkInfo().isConnected()) {
                        if (username.trim().length()  == 0 && password.trim().length() == 0 )
                        {
                            MDToast mdToast = MDToast.makeText(Register.this, "Kolom Tidak Boleh Kosong", Toast.LENGTH_LONG, MDToast.TYPE_ERROR);
                            mdToast.show();
                        }else if (txt_password.length() < 6 )
                        {
                            MDToast mdToast = MDToast.makeText(Register.this,  "Masukan Kata Sandi (Minimal 6 Karakter)!", Toast.LENGTH_LONG, MDToast.TYPE_ERROR);
                            mdToast.show();
                        } else if ( txt_confirm_password.length() == 0 || txt_confirm_password.length() < 6)
                        {
                            MDToast mdToast = MDToast.makeText(Register.this,  "Masukan Konfirmasi Kata Sandi!", Toast.LENGTH_LONG, MDToast.TYPE_ERROR);
                            mdToast.show();
                        }
                        else {
                            // Prompt user to enter credentials
                            checkRegister(username, password, confirm_password);
                        }


                    } else {
                        MDToast mdToast = MDToast.makeText(Register.this, "No Internet Connection", Toast.LENGTH_LONG, MDToast.TYPE_ERROR);
                        mdToast.show();
                    }

                }


        });

    }


    private void checkRegister(final String username, final String password, final String confirm_password) {
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
        pDialog.setMessage("Register ...");
        showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.e(TAG, "Register Response: " + response.toString());
                hideDialog();

                try {
                    JSONObject jObj = new JSONObject(response);
                    success = jObj.getInt(TAG_SUCCESS);

                    // Check for error node in json
                    if (success == 1) {


                        Log.e("Successfully Register!", jObj.toString());

                        MDToast mdToast = MDToast.makeText(Register.this,
                                jObj.getString(TAG_MESSAGE), Toast.LENGTH_LONG,MDToast.TYPE_SUCCESS);
                        mdToast.show();
                        intent = new Intent(Register.this, Login.class);
                        finish();
                        startActivity(intent);

                        txt_username.setText("");
                        txt_password.setText("");
                        txt_confirm_password.setText("");

                    } else {
                        Toast.makeText(getApplicationContext(),
                                jObj.getString(TAG_MESSAGE), Toast.LENGTH_LONG).show();

                    }
                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Login Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();

                hideDialog();

            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("username", username);
                params.put("password", password);
                params.put("confirm_password", confirm_password);

                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_json_obj);
    }

    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }

    @Override
    public void onBackPressed() {
        intent = new Intent(Register.this, Login.class);
        finish();
        startActivity(intent);
    }
}
