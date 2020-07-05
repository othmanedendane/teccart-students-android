package com.example.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.myapplication.api.ApiHelper;
import com.example.myapplication.model.User;
import com.example.myapplication.service.UserService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SubscriptionActivity extends Activity {

    private Button subscribeButton;
    private EditText loginText;
    private EditText pwdText;
    private EditText nomText;
    private EditText prenomText;
    private EditText travailText;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.subscribe_screen);

        loginText = findViewById(R.id.subscribe_login_text);
        pwdText = findViewById(R.id.subscribe_pwd_text);

        nomText = findViewById(R.id.subscribe_nom_text);
        prenomText = findViewById(R.id.subscribe_prenom_text);
        travailText = findViewById(R.id.subscribe_travail_text);

        subscribeButton = findViewById(R.id.subscribe_btn);
        subscribeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Retrofit login process
                doSubscribe();
            }
        });
    }

    private void doSubscribe() {
        /** partie 7 **/
        UserService userService = ApiHelper.getRetrofit().create(UserService.class);
        /**
         * Préparation de l'objet user pour l'envoyer dans le body la requeste
         */
        User userParameter = new User();
        userParameter.setUser(loginText.getText().toString());
        userParameter.setPw(pwdText.getText().toString());
        userParameter.setNom(nomText.getText().toString());
        userParameter.setPrenom(prenomText.getText().toString());
        userParameter.setTravail(travailText.getText().toString());
        /**
         * Appel au service qui fait la communication entre l'appli mobile et le serveur PHP
         */
        Call<User> call = userService.subscribe(userParameter);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                /**
                 * Interception de la réponse du serverur si OK
                 *  reste à vérifier si le user est null ou non pour passer au 2eme écran
                 */
                User user = response.body();
                if (user.getIdUtilisateur() != null) {
                    Intent loginIntent = new Intent(SubscriptionActivity.this,LoginActivity.class);
                    startActivity(loginIntent);
                    Toast.makeText(SubscriptionActivity.this, "SUBSCRICTION DONE", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(SubscriptionActivity.this, "SUBSCRIBTION FAILURE", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                System.out.println("#############################");
                System.out.println("#############################");
                System.out.println(t.getMessage());
                System.out.println("#############################");
                System.out.println("#############################");
                Toast.makeText(SubscriptionActivity.this, "ERREUR, Merci de contacter l'administrateurF", Toast.LENGTH_SHORT).show();
            }
        });


    }
}
