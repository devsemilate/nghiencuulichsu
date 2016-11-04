package s3.lamphan.nghiencuulichsu.mvp.models;

import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.List;

import io.realm.RealmObject;
import s3.lamphan.nghiencuulichsu.domain.repository.restModels.TopicData;
import s3.lamphan.nghiencuulichsu.domain.repository.restModels.TopicRestModel;

/**
 * Created by lam.phan on 11/1/2016.
 */
@Parcel
public class Topic {
    String id;
    String name;
    String description;
    String cover;
    String branch;
    String contentUrl;

    public Topic() {
    }

    public Topic(String id, String name, String description, String cover,
                 String branch, String contentUrl) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.cover = cover;
        this.branch = branch;
        this.contentUrl = contentUrl;
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

    public static List<Topic> convertTopicFromRestModel(TopicRestModel topicRestModel)
    {
        List<Topic> results = new ArrayList<>();
        if(topicRestModel != null) {
            ArrayList<TopicData> datas = topicRestModel.getData();
            for(TopicData data : datas)
            {
                Topic topic = new Topic(data.getObjectId(), data.getName(), data.getDescription(), data.getCover(),
                        data.getBranch(), data.getContentUrl());
                results.add(topic);
            }
        }
        return results;
    }
}
