package com.example.ftcscorecalculatorbeta;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import com.example.ftcscorecalculatorbeta.ui.calculator.CalculatorFragment;
import com.example.ftcscorecalculatorbeta.ui.home.HomeFragment;
import com.example.ftcscorecalculatorbeta.ui.progress.ProgressFragment;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class MainActivity extends AppCompatActivity {

    final static String TAG = "MainActivity";

    private FirebaseAuth mAuth;
    public FirebaseUser currentUser;
    private Fragment gotoFragment;
    public String strFirstTeamId;

    public void login() {
        if (currentUser == null) {
            GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestIdToken(getString(R.string.default_web_client_id))
                    .requestEmail()
                    .build();
            GoogleSignInClient mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
            Intent signInIntent = mGoogleSignInClient.getSignInIntent();
            startActivityForResult(signInIntent, 1);
        }
        updateUI();
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == 1) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                Log.d(TAG, "firebaseAuthWithGoogle:" + account.getId());
                firebaseAuthWithGoogle(account.getIdToken());
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e);
                Toast.makeText(MainActivity.this, "Google sign in failed.\nPlease sign in to use this feature.", Toast.LENGTH_LONG).show();
                updateUI();
            }
        }
    }

    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            currentUser = mAuth.getCurrentUser();
                            updateUI();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            //Snackbar.make(, "Authentication Failed.", Snackbar.LENGTH_SHORT).show();
                            Toast.makeText(MainActivity.this, "Authentication Failed.", Toast.LENGTH_SHORT).show();
                            updateUI();
                        }

                    }
                });
    }

    private void updateUI() {
        //currentUser
        if (currentUser != null)
        {
            Log.d( TAG, "User logged in is " + currentUser.getDisplayName());
            if (gotoFragment != null)
            {
                getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, gotoFragment).commit();
                gotoFragment = null;
            }
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        currentUser = mAuth.getCurrentUser();
        updateUI();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();

        final FragmentManager fragmentManager = getSupportFragmentManager();

        final Fragment cacl_frag = new CalculatorFragment();
        final Fragment home_frag = new HomeFragment();
        final Fragment prog_frag = new ProgressFragment();

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.nav_view);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment = cacl_frag;
                boolean blnRequireLogin = true;
                switch (item.getItemId()) {
                    case R.id.navigation_calculator:
                        fragment = cacl_frag;
                        blnRequireLogin = false;
                        //Toast.makeText(MainActivity.this, "Calc", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.navigation_home:
                        fragment = home_frag;
                        //Toast.makeText(MainActivity.this, "Home", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.navigation_progress:
                        fragment = prog_frag;
                        //Toast.makeText(MainActivity.this, "Progress", Toast.LENGTH_SHORT).show();
                        break;
                }
                if (blnRequireLogin & (currentUser == null))
                {
                    gotoFragment = fragment;
                    login();
                } else {
                    fragmentManager.beginTransaction().replace(R.id.nav_host_fragment, fragment).commit();
                }
                return true;
            }
        });

    }

}