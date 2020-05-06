package pasindu.dev.classie.srilanka_covid_19;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import cn.pedant.SweetAlert.SweetAlertDialog;
import pasindu.dev.classie.srilanka_covid_19.model.HealthModel;
import pasindu.dev.classie.srilanka_covid_19.network.ApiUtils;
import pasindu.dev.classie.srilanka_covid_19.network.HealthAPIs;
import pasindu.dev.classie.srilanka_covid_19.utills.Constant;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.util.Log.d;

public class MainActivity extends AppCompatActivity {

    private HealthAPIs mAPIService;

    private TextView txtActiveCases;
    private TextView txtRecoveredCases;
    private TextView txtDeathCases;
    private TextView txtConfirmedCases;
    private TextView txtLastUpdateData;
    private TextView txtDate;
    private TextView txtBtnMoreDetails;
    private CardView cvBtnDailyPCRTestings;
    private CardView cvBanner;
    private CardView cvActiveCases;
    private CardView cvRecoveredCases;
    private CardView cvDeathCases;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAPIService = ApiUtils.getAPIService();
        checkNetworkConnectivity();
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {

            }
        });

        AdView mAdview = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdview.loadAd(adRequest);
        mAdview.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                d(Constant.ERROR_HANDLE, "AD Loaded Successfully");
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                // Code to be executed when an ad request fails.
                d(Constant.ERROR_HANDLE, "AD Loading Fails");
            }

            @Override
            public void onAdOpened() {
                // Code to be executed when an ad opens an overlay that
                // covers the screen.
                d(Constant.ERROR_HANDLE, "User Opens the AD");
            }

            @Override
            public void onAdClicked() {
                // Code to be executed when the user clicks on an ad.
                d(Constant.ERROR_HANDLE, "User Clicked On the AD");
            }

            @Override
            public void onAdLeftApplication() {
                // Code to be executed when the user has left the app.
                d(Constant.ERROR_HANDLE, "User Left the Application");
            }

            @Override
            public void onAdClosed() {
                // Code to be executed when the user is about to return
                // to the app after tapping on an ad.
                d(Constant.ERROR_HANDLE, "User Closed The Ad and Return");
            }
        });

        txtActiveCases = (TextView) findViewById(R.id.txt_active_cases);
        txtRecoveredCases = (TextView) findViewById(R.id.txt_recovered_cases);
        txtDeathCases = (TextView) findViewById(R.id.txt_death_cases);
        txtConfirmedCases = (TextView) findViewById(R.id.txt_confirmed_cases);
        txtLastUpdateData = (TextView) findViewById(R.id.txt_last_update_data);
        txtDate = (TextView) findViewById(R.id.txt_date);
        txtBtnMoreDetails = (TextView) findViewById(R.id.txt_btn_more_details);
        cvBtnDailyPCRTestings = (CardView) findViewById(R.id.cv_btn_daily_pcr_testings);
        cvBanner = (CardView) findViewById(R.id.cv_banner);
        cvActiveCases = (CardView) findViewById(R.id.cv_active_cases);
        cvRecoveredCases = (CardView) findViewById(R.id.cv_recovered_cases);
        cvDeathCases = (CardView) findViewById(R.id.cv_death_cases);
    }

    public void getData() {

        Call<HealthModel> call = mAPIService.getHealthDatils();

        call.enqueue(new Callback<HealthModel>() {
            @Override
            public void onResponse(Call<HealthModel> call, Response<HealthModel> response) {
                setData(response);
            }

            @Override
            public void onFailure(Call<HealthModel> call, Throwable t) {
                Toast.makeText(MainActivity.this, "[Error] :" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void checkNetworkConnectivity() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getApplicationContext()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        final NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        Button btnTryAgain;
        if (networkInfo == null || !networkInfo.isConnected() || !networkInfo.isAvailable()) {
            // when internet is inactive
            // Initialize dialog
            final Dialog dialog = new Dialog(this);
            dialog.setContentView(R.layout.network_alert_dialog);
            dialog.setCanceledOnTouchOutside(false);
            dialog.getWindow().setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.getWindow().getAttributes().windowAnimations = android.R.style.Animation_Dialog;

            btnTryAgain = dialog.findViewById(R.id.btn_try_again);

            btnTryAgain.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finish();
                    System.exit(0);
                }
            });
            dialog.show();
        } else {
            getData();
        }
    }

    private void setData(final Response<HealthModel> healthModel) {
        txtActiveCases.setText(healthModel.body().getData().getLocal_active_cases());
        txtRecoveredCases.setText(healthModel.body().getData().getLocal_recovered());
        txtDeathCases.setText(healthModel.body().getData().getLocal_deaths());
        txtConfirmedCases.setText("Confirmed Cases : " + healthModel.body().getData().getLocal_total_cases());
        txtLastUpdateData.setText("Last Update " + healthModel.body().getData().getUpdate_date_time());

        txtBtnMoreDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ConnectivityManager connectivityManager = (ConnectivityManager) getApplicationContext()
                        .getSystemService(Context.CONNECTIVITY_SERVICE);
                final NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
                if (networkInfo == null || !networkInfo.isConnected() || !networkInfo.isAvailable()) {
                    Toast.makeText(MainActivity.this, "Please, Check your network connection", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(MainActivity.this, Main2Activity.class);
                    startActivity(intent);
                }
            }
        });

        cvBtnDailyPCRTestings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ConnectivityManager connectivityManager = (ConnectivityManager) getApplicationContext()
                        .getSystemService(Context.CONNECTIVITY_SERVICE);
                final NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
                if (networkInfo == null || !networkInfo.isConnected() || !networkInfo.isAvailable()) {
                    Toast.makeText(MainActivity.this, "Please, Check your network connection", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(MainActivity.this, PCRActivity.class);
                    startActivity(intent);
                }
            }
        });

        Calendar calendar = Calendar.getInstance();
        DateFormat date = new SimpleDateFormat("EEEE", Locale.getDefault());
        String dayName = date.format(calendar.getTime()); //Monday
        date = new SimpleDateFormat("dd", Locale.getDefault());
        String dayNumber = date.format(calendar.getTime()); //20
        date = new SimpleDateFormat("MMM", Locale.getDefault());
        String monthName = date.format(calendar.getTime()); //Apr

        txtDate.setText(dayName + ", " + dayNumber + " " + monthName);

        cvBanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog("Call - 1390",
                        "Hotline to Receive Advice on Coronavirus \n For Free Medical Advice Call 1390");
            }
        });

        cvActiveCases.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAlertDialog("Total : " + healthModel.body().getData().getLocal_total_cases() + "\n" +
                                "Active Cases : " + healthModel.body().getData().getLocal_active_cases() + "\n" +
                                "New Cases : " + healthModel.body().getData().getLocal_new_cases() + "\n" +
                                "Individuals in Hospitals : " + healthModel.body().getData().getLocal_total_number_of_individuals_in_hospitals());
            }
        });

        cvRecoveredCases.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAlertDialog("Total : " + healthModel.body().getData().getLocal_total_cases() + "\n" +
                                "Active Cases : " + healthModel.body().getData().getLocal_active_cases() + "\n" +
                                "Recovered Cases : " + healthModel.body().getData().getLocal_recovered());
            }
        });

        cvDeathCases.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAlertDialog("Total : " + healthModel.body().getData().getLocal_total_cases() + "\n" +
                                "Active Cases : " + healthModel.body().getData().getLocal_active_cases() + "\n" +
                                "Recovered Cases : " + healthModel.body().getData().getLocal_recovered() + "\n" +
                                "Deaths : " + healthModel.body().getData().getLocal_deaths() + "\n" +
                                "New Death Cases : " + healthModel.body().getData().getLocal_new_deaths());
            }
        });
    }

    public void showDialog(String title, String message) {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.alert_dialog_with_image);
        dialog.setCanceledOnTouchOutside(true);
        dialog.getWindow().setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = android.R.style.Animation_Dialog;
        TextView alertTitle = dialog.findViewById(R.id.txt_title);
        TextView alertMessage = dialog.findViewById(R.id.txt_message);
        ImageView imageIcon = dialog.findViewById(R.id.img_icon);
        alertTitle.setText(title);
        alertMessage.setText(message);
        dialog.show();
    }

    public void showAlertDialog(String message) {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.alert_dialog);
        dialog.setCanceledOnTouchOutside(true);
        dialog.getWindow().setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = android.R.style.Animation_Dialog;
        TextView alertMessage = dialog.findViewById(R.id.txt_message);
        alertMessage.setText(message);
        dialog.show();
    }
}