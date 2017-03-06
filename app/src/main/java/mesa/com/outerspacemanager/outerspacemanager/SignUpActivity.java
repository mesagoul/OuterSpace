package mesa.com.outerspacemanager.outerspacemanager;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Lucas on 06/03/2017.
 */

public class SignUpActivity extends Activity {

    private Button addBtn;
    private EditText username;
    private EditText password;
    private User user;
    private Retrofit retrofit;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        username = (EditText) findViewById(R.id.input_identifiant);
        password = (EditText) findViewById(R.id.input_mdp);
        addBtn = (Button) findViewById(R.id.btn_add);

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user = new User(username.getText().toString(),password.getText().toString());
                retrofit = new Retrofit.Builder()
                        .baseUrl("https://outer-space-manager.herokuapp.com/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                Service service = retrofit.create(Service.class);
                Call<List<User>> request = service.createUserAccount(user);

               // request.enqueue(new Callback<List<User>>() {
                //    Log.d("Debug","Debug");
                //});
            }
        });

    }
}
