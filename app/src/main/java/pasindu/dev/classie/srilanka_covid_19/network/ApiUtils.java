package pasindu.dev.classie.srilanka_covid_19.network;

public class ApiUtils {

    private ApiUtils() { }

    public static final String BASE_URL = "https://www.hpb.health.gov.lk/";

    public static HealthAPIs getAPIService() {

        return RetrofitClient.getClient(BASE_URL).create(HealthAPIs.class);
    }
}
