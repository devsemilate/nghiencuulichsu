package s3.lamphan.nghiencuulichsu.domain.persistence.models;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import s3.lamphan.nghiencuulichsu.mvp.models.Topic;

/**
 * Created by lam.phan on 11/7/2016.
 */
public class TopicRealmObject extends RealmObject{
    @PrimaryKey
    String id;
    String name;
    String description;
    String cover;
    String branch;
    String contentUrl;
    String contentTags;

    public TopicRealmObject() {
    }

    public TopicRealmObject(String id, String name, String description,
                            String cover, String branch,
                            String contentUrl, String contentTags) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.cover = cover;
        this.branch = branch;
        this.contentUrl = contentUrl;
        this.contentTags = contentTags;
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

    public String getContentTags() {
        return contentTags;
    }

    public void setContentTags(String contentTags) {
        this.contentTags = contentTags;
    }

    public static TopicRealmObject convertFromTopicModel(Topic topic)
    {
        return new TopicRealmObject(topic.getId(), topic.getName(), topic.getDescription(),
                topic.getCover(), topic.getBranch(),
                topic.getContentUrl(), topic.getContentTags());
    }

    public static Topic convertToTopicModel(TopicRealmObject topicRealmObject)
    {
        return new Topic(topicRealmObject.getId(), topicRealmObject.getName(),
                topicRealmObject.getDescription(), topicRealmObject.getCover(),
                topicRealmObject.getBranch(), topicRealmObject.getContentUrl(),
                topicRealmObject.getContentTags());
    }
}
