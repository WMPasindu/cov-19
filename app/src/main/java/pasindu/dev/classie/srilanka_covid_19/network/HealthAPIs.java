package pasindu.dev.classie.srilanka_covid_19.network;

import pasindu.dev.classie.srilanka_covid_19.model.HealthModel;
import retrofit2.Call;
import retrofit2.http.GET;

public interface HealthAPIs {
    /*
   Get request to fetch city weather.Takes in two parameter-city name and API key.
   */
    @GET("/api/get-current-statistical")
    Call<HealthModel> getHealthDatils();
}
