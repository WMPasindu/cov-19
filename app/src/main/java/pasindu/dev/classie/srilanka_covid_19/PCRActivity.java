package pasindu.dev.classie.srilanka_covid_19;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Cartesian;
import com.anychart.core.cartesian.series.Line;
import com.anychart.data.Mapping;
import com.anychart.data.Set;
import com.anychart.enums.Anchor;
import com.anychart.enums.MarkerType;
import com.anychart.enums.TooltipPositionMode;
import com.anychart.graphics.vector.Stroke;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pasindu.dev.classie.srilanka_covid_19.adapter.HospitalsAdapter;
import pasindu.dev.classie.srilanka_covid_19.model.DailyPCRTestingData;
import pasindu.dev.classie.srilanka_covid_19.model.HealthModel;
import pasindu.dev.classie.srilanka_covid_19.network.ApiUtils;
import pasindu.dev.classie.srilanka_covid_19.network.HealthAPIs;
import pasindu.dev.classie.srilanka_covid_19.utills.Constant;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.util.Log.d;

public class PCRActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    private HealthAPIs mAPIService;
    private AnyChartView anyChartView;
    HospitalsAdapter hospitalsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_p_c_r);

        recyclerView = findViewById(R.id.recyclerview);

        anyChartView = findViewById(R.id.any_chart_view);
        anyChartView.setProgressBar(findViewById(R.id.progress_bar));

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {

            }
        });

        AdView mAdview = mAdview = findViewById(R.id.adView);
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
                setAdapter(response);
                chartOption(response.body().getData().getDaily_pcr_testing_data());
            }

            @Override
            public void onFailure(Call<HealthModel> call, Throwable t) {
                Toast.makeText(PCRActivity.this, "[Error] :" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setAdapter(Response<HealthModel> healthModel) {
        hospitalsAdapter = new HospitalsAdapter(generateData(healthModel.body().getData().getDaily_pcr_testing_data()));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(hospitalsAdapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
    }

    private Map<String, String> generateData(DailyPCRTestingData[] dailyPCRTestingData) {
        Map<String,String> map = new HashMap<>();
        for (DailyPCRTestingData a : dailyPCRTestingData) {
            map.put(a.getDate(),a.getCount());
        }
        return map;
    }

    private void chartOption(DailyPCRTestingData[] dailyPCRTestingData) {
        Cartesian cartesian = AnyChart.line();

        cartesian.animation(true);

        cartesian.padding(10d, 20d, 5d, 20d);

        cartesian.crosshair().enabled(true);
        cartesian.crosshair()
                .yLabel(true)
                .yStroke((Stroke) null, null, null, (String) null, (String) null);

        cartesian.tooltip().positionMode(TooltipPositionMode.POINT);

        cartesian.title("Daily PCR Testings");

        cartesian.yAxis(0).title("count");
        cartesian.xAxis(0).labels().padding(5d, 5d, 5d, 5d);

        List<DataEntry> seriesData = new ArrayList<>();
        for (DailyPCRTestingData a : dailyPCRTestingData) {
            seriesData.add(new CustomDataEntry(a.getDate(), Integer.parseInt(a.getCount())));
        }

        Set set = Set.instantiate();
        set.data(seriesData);
        Mapping series1Mapping = set.mapAs("{ x: 'x', value: 'value' }");

        Line series1 = cartesian.line(series1Mapping);
        series1.name("Sri Lanka");
        series1.hovered().markers().enabled(true);
        series1.hovered().markers()
                .type(MarkerType.CIRCLE)
                .size(4d);
        series1.tooltip()
                .position("right")
                .anchor(Anchor.LEFT_CENTER)
                .offsetX(5d)
                .offsetY(5d);

        cartesian.legend().enabled(true);
        cartesian.legend().fontSize(13d);
        cartesian.legend().padding(0d, 0d, 10d, 0d);

        anyChartView.setChart(cartesian);
    }

    private class CustomDataEntry extends ValueDataEntry {

        CustomDataEntry(String x, Number value) {
            super(x, value);
        }

    }
}
