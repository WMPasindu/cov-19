package pasindu.dev.classie.srilanka_covid_19.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Hospital {

    @SerializedName("name_ta")
    @Expose
    private String name_ta;

    @SerializedName("updated_at")
    @Expose
    private String updated_at;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("name_si")
    @Expose
    private String name_si;

    @SerializedName("created_at")
    @Expose
    private String created_at;

    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("deleted_at")
    @Expose
    private String deleted_at;

    public Hospital() {
        // default constructor
    }

    public String getName_ta() {
        return name_ta;
    }

    public void setName_ta(String name_ta) {
        this.name_ta = name_ta;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName_si() {
        return name_si;
    }

    public void setName_si(String name_si) {
        this.name_si = name_si;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDeleted_at() {
        return deleted_at;
    }

    public void setDeleted_at(String deleted_at) {
        this.deleted_at = deleted_at;
    }
}
