package pasindu.dev.classie.srilanka_covid_19.model;

public class HealthModel {
    private DataModel data;
    private String success;
    private String message;

    public HealthModel() {
        // default constructor
    }

    public DataModel getData() {
        return data;
    }

    public void setData(DataModel data) {
        this.data = data;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
