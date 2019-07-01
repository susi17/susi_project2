package com.example.orderkuy.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import com.example.orderkuy.MainActivity;
import com.example.orderkuy.R;
import com.example.orderkuy.ServerSide.UserSession;
import com.example.orderkuy.activity.CartActivity;
import com.example.orderkuy.activity.Login;
import butterknife.BindView;
import butterknife.ButterKnife;

public class AccountFragment extends Fragment {


    @BindView(R.id.display_username) TextView txtnama;
    @BindView(R.id.btn_logout) Button button;
    @BindView(R.id.favorit_saya) TextView favorit_saya;
    @BindView(R.id.keranjang_saya) TextView keranjang_saya;
    @BindView(R.id.koin_saya) TextView koin_saya;
    @BindView(R.id.akun_saya) TextView akun_saya;
    @BindView(R.id.tentang_freetime) TextView tentang_freetime;

//    @BindView(R.id.facebook) ImageButton btn_facebook;
//    @BindView(R.id.instagram) ImageButton btn_instagram;
//    @BindView(R.id.twitter) ImageButton btn_twitter;


    String username;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_account_fragment, container, false);

        ButterKnife.bind(this,view);

        UserSession userSession = new UserSession(getActivity().getApplicationContext());
        username = userSession.getUsernameUser();
        txtnama.setText(username);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
                dialog.setTitle("Logout");
                dialog.setIcon(R.drawable.ic_exit_to_app_black_24dp);
                dialog.setMessage("Ingin keluar?");
                dialog.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        new UserSession(getActivity()).clearSession();
                        Intent intent =new Intent(getActivity().getApplicationContext(),Login.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        ((MainActivity)getActivity()).finish();
                        dialogInterface.dismiss();
                    }
                });
                dialog.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                dialog.show();
            }

//            private void logout() {
//
//                final AlertDialog.Builder logout = new AlertDialog.Builder(getActivity());
//                logout.create();
//                logout.setTitle("Keluar");
//                logout.setMessage("Apakah Kamu Yakin ?");
//                logout.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
//
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//
//                        UserSession x = new UserSession(getActivity());
//                        x.setIsLogin(false);
//                        toLogin();
//                    }
//                });
//                logout.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//                        dialogInterface.cancel();
//                    }
//                });
//                AlertDialog alertDialog=logout.show();
//                alertDialog.show();
//
//            }
        });


        keranjang_saya.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), CartActivity.class);
                startActivity(intent);
            }
        });

        favorit_saya.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), CartActivity.class);
            }
        });

        koin_saya.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), CartActivity.class);
            }
        });

        akun_saya.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), CartActivity.class);
            }
        });

        tentang_freetime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), CartActivity.class);
            }
        });

        return view;
    }


}
