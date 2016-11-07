package s3.lamphan.nghiencuulichsu.domain.repository.restModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by lam.phan on 11/1/2016.
 */
public class TopicData {
    @SerializedName("objectId")
    @Expose
    String objectId;
    @SerializedName("name")
    @Expose
    String name;
    @SerializedName("description")
    @Expose
    String description;
    @SerializedName("cover")
    @Expose
    String cover;
    @SerializedName("branch")
    @Expose
    String branch;
    @SerializedName("contentUrl")
    @Expose
    String contentUrl;
    @SerializedName("contentTags")
    @Expose
    String contentTags;

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getContentUrl() {
        return contentUrl;
    }

    public void setContentUrl(String contentUrl) {
        this.contentUrl = contentUrl;
    }

    public String getContentTags() {
        return contentTags;
    }

    public void setContentTags(String contentTags) {
        this.contentTags = contentTags;
    }
}
