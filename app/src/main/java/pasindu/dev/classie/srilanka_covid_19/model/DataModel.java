package pasindu.dev.classie.srilanka_covid_19.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DataModel {

    @SerializedName("update_date_time")
    @Expose
    private String update_date_time;

    @SerializedName("local_new_cases")
    @Expose
    private String local_new_cases;

    @SerializedName("local_total_cases")
    @Expose
    private String local_total_cases;

    @SerializedName("local_total_number_of_individuals_in_hospitals")
    @Expose
    private String local_total_number_of_individuals_in_hospitals;

    @SerializedName("local_deaths")
    @Expose
    private String local_deaths;

    @SerializedName("local_new_deaths")
    @Expose
    private String local_new_deaths;

    @SerializedName("local_recovered")
    @Expose
    private String local_recovered;

    @SerializedName("local_active_cases")
    @Expose
    private String local_active_cases;

    @SerializedName("global_new_cases")
    @Expose
    private String global_new_cases;

    @SerializedName("global_total_cases")
    @Expose
    private String global_total_cases;

    @SerializedName("global_deaths")
    @Expose
    private String global_deaths;

    @SerializedName("global_new_deaths")
    @Expose
    private String global_new_deaths;

    @SerializedName("global_recovered")
    @Expose
    private String global_recovered;

    @SerializedName("total_pcr_testing_count")
    @Expose
    private String total_pcr_testing_count;

    @SerializedName("daily_pcr_testing_data")
    @Expose
    private DailyPCRTestingData[] daily_pcr_testing_data;

    @SerializedName("hospital_data")
    @Expose
    private HospitalData[] hospital_data;

    public DataModel() {
    }

    public String getUpdate_date_time() {
        return update_date_time;
    }

    public void setUpdate_date_time(String update_date_time) {
        this.update_date_time = update_date_time;
    }

    public String getLocal_new_cases() {
        return local_new_cases;
    }

    public void setLocal_new_cases(String local_new_cases) {
        this.local_new_cases = local_new_cases;
    }

    public String getLocal_total_cases() {
        return local_total_cases;
    }

    public void setLocal_total_cases(String local_total_cases) {
        this.local_total_cases = local_total_cases;
    }

    public String getLocal_total_number_of_individuals_in_hospitals() {
        return local_total_number_of_individuals_in_hospitals;
    }

    public void setLocal_total_number_of_individuals_in_hospitals(String local_total_number_of_individuals_in_hospitals) {
        this.local_total_number_of_individuals_in_hospitals = local_total_number_of_individuals_in_hospitals;
    }

    public String getLocal_deaths() {
        return local_deaths;
    }

    public void setLocal_deaths(String local_deaths) {
        this.local_deaths = local_deaths;
    }

    public String getLocal_new_deaths() {
        return local_new_deaths;
    }

    public void setLocal_new_deaths(String local_new_deaths) {
        this.local_new_deaths = local_new_deaths;
    }

    public String getLocal_recovered() {
        return local_recovered;
    }

    public void setLocal_recovered(String local_recovered) {
        this.local_recovered = local_recovered;
    }

    public String getLocal_active_cases() {
        return local_active_cases;
    }

    public void setLocal_active_cases(String local_active_cases) {
        this.local_active_cases = local_active_cases;
    }

    public String getGlobal_new_cases() {
        return global_new_cases;
    }

    public void setGlobal_new_cases(String global_new_cases) {
        this.global_new_cases = global_new_cases;
    }

    public String getGlobal_total_cases() {
        return global_total_cases;
    }

    public void setGlobal_total_cases(String global_total_cases) {
        this.global_total_cases = global_total_cases;
    }

    public String getGlobal_deaths() {
        return global_deaths;
    }

    public void setGlobal_deaths(String global_deaths) {
        this.global_deaths = global_deaths;
    }

    public String getGlobal_new_deaths() {
        return global_new_deaths;
    }

    public void setGlobal_new_deaths(String global_new_deaths) {
        this.global_new_deaths = global_new_deaths;
    }

    public String getGlobal_recovered() {
        return global_recovered;
    }

    public void setGlobal_recovered(String global_recovered) {
        this.global_recovered = global_recovered;
    }

    public String getTotal_pcr_testing_count() {
        return total_pcr_testing_count;
    }

    public void setTotal_pcr_testing_count(String total_pcr_testing_count) {
        this.total_pcr_testing_count = total_pcr_testing_count;
    }

    public DailyPCRTestingData[] getDaily_pcr_testing_data() {
        return daily_pcr_testing_data;
    }

    public void setDaily_pcr_testing_data(DailyPCRTestingData[] daily_pcr_testing_data) {
        this.daily_pcr_testing_data = daily_pcr_testing_data;
    }

    public HospitalData[] getHospital_data() {
        return hospital_data;
    }

    public void setHospital_data(HospitalData[] hospital_data) {
        this.hospital_data = hospital_data;
    }
}
