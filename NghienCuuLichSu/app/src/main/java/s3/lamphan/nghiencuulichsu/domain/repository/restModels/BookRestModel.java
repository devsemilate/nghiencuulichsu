package s3.lamphan.nghiencuulichsu.domain.repository.restModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by lam.phan on 11/7/2016.
 */
public class BookRestModel {
    @SerializedName("data")
    @Expose
    ArrayList<BookData> data = new ArrayList<>();
    @SerializedName("totalObjects")
    @Expose
    Integer totalObjects;

    public ArrayList<BookData> getData() {
        return data;
    }

    public void setData(ArrayList<BookData> data) {
        this.data = data;
    }

    public Integer getTotalObjects() {
        return totalObjects;
    }

    public void setTotalObjects(Integer totalObjects) {
        this.totalObjects = totalObjects;
    }
}
