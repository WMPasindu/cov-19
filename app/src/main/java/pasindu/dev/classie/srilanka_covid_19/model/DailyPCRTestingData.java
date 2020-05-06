package pasindu.dev.classie.srilanka_covid_19.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DailyPCRTestingData {

    @SerializedName("date")
    @Expose
    private String date;

    @SerializedName("count")
    @Expose
    private String count;

    public DailyPCRTestingData() {
        // default constructor
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }
}
