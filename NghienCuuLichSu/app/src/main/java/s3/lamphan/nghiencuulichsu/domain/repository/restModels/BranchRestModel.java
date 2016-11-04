package s3.lamphan.nghiencuulichsu.domain.repository.restModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by lam.phan on 11/3/2016.
 */
public class BranchRestModel {
    @SerializedName("data")
    @Expose
    ArrayList<BranchData> data = new ArrayList<>();

    public ArrayList<BranchData> getData() {
        return data;
    }

    public void setData(ArrayList<BranchData> data) {
        this.data = data;
    }
}
