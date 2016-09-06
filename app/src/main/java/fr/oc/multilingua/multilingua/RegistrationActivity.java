package fr.oc.multilingua.multilingua;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import fr.oc.multilingua.multilingua.sqlite.DBHelper;

public class RegistrationActivity extends AppCompatActivity {

    private EditText inputLastname, inputFirstname, inputPassword, inputEmail;
    private TextInputLayout inputLayoutLastname, inputLayoutFirstname, inputLayoutPassword, inputLayoutEmail;
    public Button btnValidate;
    public DBHelper db = new DBHelper(this);

    private final IntentFilter intentFilter = new IntentFilter("close");

    private final BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if ("close".equals(intent.getAction())) {
                finish();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        LocalBroadcastManager.getInstance(this).registerReceiver(broadcastReceiver, intentFilter);

        setTitle("Inscription");

        inputLastname = (EditText) findViewById(R.id.input_lastname);
        inputFirstname = (EditText) findViewById(R.id.input_firstname);
        inputPassword = (EditText) findViewById(R.id.input_password);
        inputEmail = (EditText) findViewById(R.id.input_email);
        inputLayoutLastname = (TextInputLayout) findViewById(R.id.input_layout_lastname);
        inputLayoutFirstname = (TextInputLayout) findViewById(R.id.input_layout_firstname);
        inputLayoutPassword = (TextInputLayout) findViewById(R.id.input_layout_password);
        inputLayoutEmail = (TextInputLayout) findViewById(R.id.input_layout_email);
        btnValidate = (Button) findViewById(R.id.btn_validate);

        inputLastname.addTextChangedListener(new MyTextWatcher(inputLastname));
        inputFirstname.addTextChangedListener(new MyTextWatcher(inputFirstname));
        inputPassword.addTextChangedListener(new MyTextWatcher(inputPassword));
        inputEmail.addTextChangedListener(new MyTextWatcher(inputEmail));

        btnValidate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (submitForm()) {
                    db.insertUser(
                            inputLastname.getText().toString(),
                            inputFirstname.getText().toString(),
                            inputPassword.getText().toString(),
                            inputEmail.getText().toString()
                    );

                    Intent intent = new Intent(RegistrationActivity.this, MainActivity.class);
                    intent.putExtra("successMessage", "Inscription réussie");
                    startActivity(intent);
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        LocalBroadcastManager.getInstance(this).unregisterReceiver(broadcastReceiver);
    }

    /**
     * Validating form
     */
    private boolean submitForm() {
        return validateLastname() && validateFirstname() && validatePassword() && validateEmail();
    }

    /**
     * Validating input lastname
     */
    private boolean validateLastname() {
        if (inputLastname.getText().toString().trim().isEmpty()) {
            inputLayoutLastname.setError(getString(R.string.err_msg_lastname));
            requestFocus(inputLastname);
            return false;
        } else {
            inputLayoutLastname.setErrorEnabled(false);
        }
        return true;
    }

    /**
     * Validating input firstname
     */
    private boolean validateFirstname() {
        if (inputFirstname.getText().toString().trim().isEmpty()) {
            inputLayoutFirstname.setError(getString(R.string.err_msg_firstname));
            requestFocus(inputFirstname);
            return false;
        } else {
            inputLayoutFirstname.setErrorEnabled(false);
        }
        return true;
    }

    /**
     * Validating input password
     */
    private boolean validatePassword() {
        if (inputPassword.getText().toString().trim().isEmpty()) {
            inputLayoutPassword.setError(getString(R.string.err_msg_password));
            requestFocus(inputPassword);
            return false;
        } else {
            inputLayoutPassword.setErrorEnabled(false);
        }
        return true;
    }

    /**
     * Validating input email
     */
    private boolean validateEmail() {
        String email = inputEmail.getText().toString().trim();

        if (email.isEmpty() || !isValidEmail(email)) {
            inputLayoutEmail.setError(getString(R.string.err_msg_email));
            requestFocus(inputEmail);
            return false;
        } else {
            inputLayoutEmail.setErrorEnabled(false);
        }
        return true;
    }

    private static boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    private class MyTextWatcher implements TextWatcher {

        private View view;

        private MyTextWatcher(View view) {
            this.view = view;
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void afterTextChanged(Editable editable) {
            switch (view.getId()) {
                case R.id.input_lastname:
                    validateLastname();
                    break;
                case R.id.input_firstname:
                    validateFirstname();
                    break;
                case R.id.input_password:
                    validatePassword();
                    break;
                case R.id.input_email:
                    validateEmail();
                    break;
            }
        }
    }
}
