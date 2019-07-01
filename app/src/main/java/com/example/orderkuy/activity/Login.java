package com.example.orderkuy.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.orderkuy.MainActivity;
import com.example.orderkuy.R;
import com.example.orderkuy.ServerSide.URL;
import com.example.orderkuy.ServerSide.UserSession;
import com.example.orderkuy.ServerSide.VolleyHandler;
import com.valdesekamdem.library.mdtoast.MDToast;

import org.json.JSONException;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Map;
import butterknife.BindView;
import butterknife.ButterKnife;

public class Login extends AppCompatActivity {

    ProgressDialog pDialog;
    @BindView(R.id.btn_login) Button btn_login;
    @BindView(R.id.btn_register) Button btn_register;
    @BindView(R.id.txt_username) TextView txt_username;
    @BindView(R.id.txt_password) TextView txt_password;
    Intent intent;

//    int success;
//    ConnectivityManager conMgr;
//
//    private String url = Server.URL + "login.php";
//
//    private static final String TAG = Login.class.getSimpleName();
//
//    private static final String TAG_SUCCESS = "success";
//    private static final String TAG_MESSAGE = "message";
//
//    public final static String TAG_USERNAME = "username";
//    public final static String TAG_ID = "id_user";
//
//    String tag_json_obj = "json_obj_req";
//
//    SharedPreferences sharedpreferences;
//    Boolean session = false;
//    String id_user, username;
//    public static final String my_shared_preferences = "my_shared_preferences";
//    public static final String session_status = "session_status";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ButterKnife.bind(this);   //Instance ButteKnife

//        conMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
//        {
//            if (conMgr.getActiveNetworkInfo() != null
//                    && conMgr.getActiveNetworkInfo().isAvailable()
//                    && conMgr.getActiveNetworkInfo().isConnected()) {
//            } else {
//                Toast.makeText(getApplicationContext(), "No Internet Connection",
//                        Toast.LENGTH_LONG).show();
//            }
//        }



//        // Cek session login jika TRUE maka langsung buka MainActivity
//        UserSession x = new UserSession(getApplicationContext());
//        sharedpreferences = getBaseContext().getSharedPreferences(my_shared_preferences, Context.MODE_PRIVATE);
//        session = x.getIsLogin();
//        id_user = sharedpreferences.getString(TAG_ID, null);
//
//        if (session) {
//
//            launchMainActivity();
//        }



        btn_login.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                pDialog = new ProgressDialog(Login.this);
                pDialog.setTitle("Logging..");
                pDialog.setMessage("Mohon Tunggu..");
                showDialog();
                login();

//                String username = txt_username.getText().toString();
//                String password = txt_password.getText().toString();
//
//                // mengecek kolom yang kosong
//                if (!Patterns.EMAIL_ADDRESS.matcher(txt_username.getText()).matches() && password.length() < 6) {
//                    if (conMgr.getActiveNetworkInfo() != null
//                            && conMgr.getActiveNetworkInfo().isAvailable()
//                            && conMgr.getActiveNetworkInfo().isConnected()) {
//                        checkLogin(username, password);
//                    } else {
//                        Toast mdToast = Toast.makeText(Login.this ,"No Internet Connection", Toast.LENGTH_LONG);
//                                mdToast.show();
//                    }
//                } else if (!Patterns.EMAIL_ADDRESS.matcher(txt_username.getText()).matches()){
//                    Toast.makeText(Login.this, "Masukan Email yang Benar!", Toast.LENGTH_SHORT).show();
//
//                } else if (password.length() < 6){
//                    Toast.makeText(Login.this, "Masukan Kata Sandi (Minimal 6 Karakter)!", Toast.LENGTH_SHORT).show();
//
//                }
//                else {
//                    // Prompt user to enter credentials
//                    Toast mdToast = Toast.makeText(Login.this ,"Masukan Email dan Kata Sandi yang Benar", Toast.LENGTH_LONG);
//                    mdToast.show();
//                }
          }
        });

        btn_register.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                intent = new Intent(Login.this, Register.class);
                finish();
                startActivity(intent);
            }
        });

    }



//    private void checkLogin(final String username, final String password) {
//        pDialog = new ProgressDialog(this);
//        pDialog.setCancelable(false);
//        pDialog.setMessage("Memuat ...");
//        showDialog();
//
//        StringRequest strReq = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
//
//            @Override
//            public void onResponse(String response) {
//                Log.e(TAG, "Login Response: " + response.toString());
//                hideDialog();
//
//                try {
//                    JSONObject jObj = new JSONObject(response);
//                    success = jObj.getInt(TAG_SUCCESS);
//
//                    // Check for error node in json
//                    if (success == 1) {
//                        String username = jObj.getString(TAG_USERNAME);
//                        String id = jObj.getString(TAG_ID);
//
//                        Log.e("Successfully Login!", jObj.toString());
//
//                        Toast mdToast = Toast.makeText(Login.this, jObj.getString(TAG_MESSAGE), Toast.LENGTH_LONG);
//                        mdToast.show();
//
//                        // menyimpan login ke session
//                        SharedPreferences.Editor editor = sharedpreferences.edit();
//                        editor.putBoolean(session_status, true);
//                        editor.putString(TAG_ID, id);
//                        editor.putString(TAG_USERNAME, username);
//                        editor.apply();
//
//                        // Memanggil main activity
//                        Intent intent = new Intent(Login.this, MainActivity.class);
//                        intent.putExtra(TAG_ID, id);
//                        intent.putExtra(TAG_USERNAME, username);
//                        finish();
//                        startActivity(intent);
//                    } else {
//                        Toast mdToast = Toast.makeText(Login.this,
//                                jObj.getString(TAG_MESSAGE), Toast.LENGTH_LONG);
//                        mdToast.show();
//
//                    }
//                } catch (JSONException e) {
//                    // JSON error
//                    e.printStackTrace();
//                }
//
//            }
//        }, new Response.ErrorListener() {
//
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Log.e(TAG, "Login Error: " + error.getMessage());
//                Toast mdToast = Toast.makeText(Login.this,
//                        error.getMessage(), Toast.LENGTH_LONG);
//                mdToast.show();
//
//                hideDialog();
//
//            }
//        }) {
//
//            @Override
//            protected Map<String, String> getParams() {
//                // Posting parameters to login url
//                Map<String, String> params = new HashMap<String, String>();
//                params.put("username", username);
//                params.put("password", password);
//
//                return params;
//            }
//
//        };
//
//        // Adding request to request queue
//        AppController.getInstance().addToRequestQueue(strReq, tag_json_obj);
//    }



    private void login(){

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL.login, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                        hideDialog();
                    if (jsonObject.getInt("success") == 1){
                        UserSession userSession = new UserSession(getApplicationContext());
                        userSession.setIdUser(jsonObject.getString("id_user"));
                        userSession.setUsernameUser(jsonObject.getString("username"));
                        userSession.setIsLogin(true);
                        Intent intent = new Intent(Login.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                        MDToast.makeText(getApplicationContext(),jsonObject.getString("message"),Toast.LENGTH_LONG,MDToast.TYPE_ERROR).show();
                    } else {
                        MDToast.makeText(getApplicationContext(),jsonObject.getString("message"),Toast.LENGTH_LONG,MDToast.TYPE_WARNING).show();
                        hideDialog();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyHandler.handleVolleyError(getApplicationContext(),error);
                hideDialog();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("username" , txt_username.getText().toString().trim());
                params.put("password", txt_password.getText().toString().trim());

                return params;
            }
        };

        Volley.newRequestQueue(getApplicationContext()).add(stringRequest);
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
//        new AlertDialog.Builder(this)
//                .setTitle("Pengen Metu ?")
//                .setMessage("Yakin Pengen Metu ?")
//                .setNegativeButton(android.R.string.no, null)
//                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
//
//                    public void onClick(DialogInterface arg0, int arg1) {
//                       finishAffinity();
//                       System.exit(0);
//                    }
//                }).show();
        finish();
    }

//    private void launchMainActivity(){
//        Intent main = new Intent(this, MainActivity.class);
//        startActivity(main);
//        finish();
//    }
}
