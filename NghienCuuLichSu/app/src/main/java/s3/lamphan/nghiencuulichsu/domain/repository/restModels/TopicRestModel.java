package s3.lamphan.nghiencuulichsu.domain.repository.restModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by lam.phan on 11/1/2016.
 */
public class TopicRestModel {
    @SerializedName("data")
    @Expose
    ArrayList<TopicData> data = new ArrayList<>();

    public ArrayList<TopicData> getData() {
        return data;
    }

    public void setData(ArrayList<TopicData> data) {
        this.data = data;
    }
}
