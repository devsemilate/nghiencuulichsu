package s3.lamphan.nghiencuulichsu.mvp.models;

import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.List;

import s3.lamphan.nghiencuulichsu.domain.repository.restModels.BranchData;
import s3.lamphan.nghiencuulichsu.domain.repository.restModels.BranchRestModel;
import s3.lamphan.nghiencuulichsu.domain.repository.restModels.TopicData;

/**
 * Created by lam.phan on 11/3/2016.
 */
@Parcel
public class Branch {
    String id;
    String name;
    String image;

    public Branch() {
    }

    public Branch(String id, String name, String image) {
        this.id = id;
        this.name = name;
        this.image = image;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public static List<Branch> convertFromRestModel(BranchRestModel restModel)
    {
        List<Branch> result = new ArrayList<>();
        if(restModel != null) {
            ArrayList<BranchData> datas = restModel.getData();
            for(BranchData data : datas)
            {
                Branch branch = new Branch(data.getId(), data.getName(), data.getImage());
                result.add(branch);
            }
        }
        return result;
    }
}
