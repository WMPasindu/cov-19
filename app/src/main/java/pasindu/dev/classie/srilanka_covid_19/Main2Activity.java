package pasindu.dev.classie.srilanka_covid_19;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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

public class Main2Activity extends AppCompatActivity {

    private HealthAPIs mAPIService;
    private TextView txtGlobalTotalCases;
    private TextView txtGlobalRecoveredCases;
    private TextView txtGlobalDeathCases;
    private TextView txtDate;

    private CardView cvActiveCases;
    private CardView cvRecoveredCases;
    private CardView cvDeathCases;
    private LinearLayout LMoreData2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        txtGlobalTotalCases = (TextView) findViewById(R.id.txt_global_total_cases);
        txtGlobalRecoveredCases = (TextView) findViewById(R.id.txt_global_recovered_cases);
        txtGlobalDeathCases = (TextView) findViewById(R.id.txt_global_death_cases);
        txtDate = (TextView) findViewById(R.id.txt_date);
        cvActiveCases = (CardView) findViewById(R.id.cv_active_cases);
        cvRecoveredCases = (CardView) findViewById(R.id.cv_recovered_cases);
        cvDeathCases = (CardView) findViewById(R.id.cv_death_cases);
        LMoreData2 = (LinearLayout) findViewById(R.id.ll_more_data2);

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

        mAPIService = ApiUtils.getAPIService();
        getData();
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
                Toast.makeText(Main2Activity.this, "[Error] :" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setData(final Response<HealthModel> healthModel) {
        txtGlobalTotalCases.setText(healthModel.body().getData().getGlobal_total_cases());
        txtGlobalRecoveredCases.setText(healthModel.body().getData().getGlobal_recovered());
        txtGlobalDeathCases.setText(healthModel.body().getData().getGlobal_deaths());

        Calendar calendar = Calendar.getInstance();
        DateFormat date= new SimpleDateFormat("EEEE", Locale.getDefault());
        String dayName = date.format(calendar.getTime()); //Monday
        date= new SimpleDateFormat("dd", Locale.getDefault());
        String dayNumber = date.format(calendar.getTime()); //20
        date= new SimpleDateFormat("MMM", Locale.getDefault());
        String monthName= date.format(calendar.getTime()); //Apr

        txtDate.setText(dayName +", "+dayNumber +" "+ monthName);

        cvActiveCases.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAlertDialog("Total : " + healthModel.body().getData().getGlobal_total_cases() + "\n" +
                                "New Cases : " + healthModel.body().getData().getGlobal_new_cases());
            }
        });

        cvRecoveredCases.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAlertDialog("Total : "+healthModel.body().getData().getGlobal_total_cases() +"\n"+
                                "Recovered Cases : "+healthModel.body().getData().getGlobal_recovered());
            }
        });

        cvDeathCases.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAlertDialog("Total : " + healthModel.body().getData().getGlobal_total_cases() + "\n" +
                                "Recovered Cases : " + healthModel.body().getData().getGlobal_recovered() + "\n" +
                                "Deaths : " + healthModel.body().getData().getGlobal_deaths() + "\n" +
                                "New Death Cases : " + healthModel.body().getData().getGlobal_new_deaths());
            }
        });

        LMoreData2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog("STAY HOME.SAVE LIVES. \n" +
                                "Help stop coronavirus",
                        "STAY home as much as you can \n " +
                                "KEEP a safe distance \n" +
                                "WASH hands often \n" +
                                "COVER your cough \n" +
                                "SICK? Call ahead",
                        getResources().getDrawable(R.drawable.ic_warning_black_70dp));
            }
        });
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

    public void showDialog(String title, String message, Drawable image) {
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
        imageIcon.setImageDrawable(image);
        dialog.show();
    }
}
