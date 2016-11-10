package s3.lamphan.nghiencuulichsu.domain.persistence.models;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import s3.lamphan.nghiencuulichsu.mvp.models.Branch;

/**
 * Created by lam.phan on 11/7/2016.
 */
public class BranchRealmObject extends RealmObject{
    @PrimaryKey
    private String id;
    String name;
    String image;
    String type;

    public BranchRealmObject() {
    }

    public BranchRealmObject(String id, String name, String image, String type) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.type = type;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public static BranchRealmObject convertFromBranchModel(Branch branch)
    {
        return new BranchRealmObject(branch.getId(), branch.getName(),
                branch.getImage(), branch.getType());
    }

    public static Branch convertToBranchModel(BranchRealmObject branchRealmObject)
    {
        return new Branch(branchRealmObject.getId(),
                branchRealmObject.getName(), branchRealmObject.getImage(),
                branchRealmObject.getType());
    }
}
