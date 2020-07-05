package com.example.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.myapplication.api.ApiHelper;
import com.example.myapplication.model.User;
import com.example.myapplication.service.UserService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends Activity {


    private Button buttonLogin;
    private Button buttonLoginSubscribe;
    private EditText loginText;
    private EditText pwdText;

    /**
     * 1) Création fichier xml pour construire le layout avec les composnats android : attributions des identifiants pour chaque composant pour pouvoir l'utiliser dans l'Activity Java
     * 2) Création d'une class JAVA VIERGE , EXTENDS Activity , Redefinir protected void onCreate(@Nullable Bundle savedInstanceState) pour pouvoir la relier avec le fichier xml via : setContentView(R.layout.login_screen)
     * 3) AndroidManifest.xml si vous voulez changer l'activité principale ou bien pour ajoputer une nouvelle activité
     * 4) Ajout d'une nouvelle librairie externe => fichier build.gradle (Module app) => implementation ... => sync now pour télécharger les dépendances.
     * 5) Création du model => User avec les attributs et les getters and setters
     * 6) Création du service (INTERFACE) => convention Retrofit qui permet de lancer des requetes http vers le serveur
     * 7) Appel du service retrofit crée afin de récuperer les données du serveur (gestion des erreurs, gestion des messages...)
     */

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_screen);

        loginText = findViewById(R.id.login_text);
        pwdText = findViewById(R.id.pwd_text);
        buttonLogin = findViewById(R.id.login_btn);
        buttonLoginSubscribe = findViewById(R.id.login_subscribe_btn);
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Retrofit login process
                if (!"".equals(loginText.getText().toString()) &&
                        !"".equals(pwdText.getText().toString())) {
                    doLogin();
                } else {
                    Toast.makeText(LoginActivity.this, "Merci de saisir le login & password", Toast.LENGTH_SHORT).show();
                }

            }
        });

        buttonLoginSubscribe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentSubscribe = new Intent(LoginActivity.this, SubscriptionActivity.class);
                startActivity(intentSubscribe);
            }
        });
    }

    private void doLogin() {
        final TextView loadingText = findViewById(R.id.login_message_loading);
        loadingText.setVisibility(View.VISIBLE);
        /** partie 7 **/
        UserService userService = ApiHelper.getRetrofit().create(UserService.class);
        /**
         * Préparation de l'objet user pour l'envoyer dans le body la requeste
         */
        User userParameter = new User();
        userParameter.setUser(loginText.getText().toString());
        userParameter.setPw(pwdText.getText().toString());
        /**
         * Appel au service qui fait la communication entre l'appli mobile et le serveur PHP
         */
        Call<User> call = userService.login(userParameter);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                /**
                 * Interception de la réponse du serverur si OK
                 *  reste à vérifier si le user est null ou non pour passer au 2eme écran
                 */
                loadingText.setVisibility(View.GONE);
                User user = response.body();
                if (user.getIdUtilisateur() != null) {
                    Intent intentMainHome = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intentMainHome);
                    Toast.makeText(LoginActivity.this, "LOGIN SUCCESS", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(LoginActivity.this, "LOGIN FAILURE", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                loadingText.setVisibility(View.GONE);
                System.out.println("#############################");
                System.out.println("#############################");
                System.out.println(t.getMessage());
                System.out.println("#############################");
                System.out.println("#############################");
                Toast.makeText(LoginActivity.this, "ERREUR, Merci de contacter l'administrateurF", Toast.LENGTH_SHORT).show();
            }
        });


    }
}
