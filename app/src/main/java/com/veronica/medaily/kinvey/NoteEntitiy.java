package com.veronica.medaily.kinvey;

import com.google.api.client.json.GenericJson;
import com.google.api.client.util.Key;
import com.kinvey.java.model.KinveyMetaData;
import com.kinvey.java.model.KinveyReference;

/**
 * Created by Veronica on 10/12/2016.
 */
public class NoteEntitiy extends GenericJson {

    @Key("_id")
    String id;

    @Key("title")
    String title;

    @Key("content")
    String content;

    @Key("createdOnDate")
    String createdOnDate;

    @Key("photoUri")
    String photoUri;

    @Key("authorEmail")
    private String authorEmail;

    @Key("categoryName")
    private String categoryName;

    public String getCategoryName() {
        return categoryName;
    }
    @Key("reminderDate")
    String reminderDate;

    @Key(KinveyMetaData.JSON_FIELD_NAME)
    private KinveyMetaData meta; // Kinvey metadata, OPTIONAL
    @Key("_acl")
    private KinveyMetaData.AccessControlList acl;

    public NoteEntitiy(){

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreatedOnDate() {
        return createdOnDate;
    }

    public void setCreatedOnDate(String createdOnDate) {
        this.createdOnDate = createdOnDate;
    }

    public String getPhotoUri() {
        return photoUri;
    }

    public void setPhotoUri(String photoUri) {
        this.photoUri = photoUri;
    }

    public String getAuthorEmail() {
        return authorEmail;
    }

    public void setAuthorEmail(String authorEmail) {
        this.authorEmail = authorEmail;
    }

    public String setCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryId) {
        this.categoryName = categoryId;
    }

    public String getReminderDate() {
        return reminderDate;
    }

    public void setReminderDate(String reminderDate) {
        this.reminderDate = reminderDate;
    }

    public KinveyMetaData getMeta() {
        return meta;
    }

    public void setMeta(KinveyMetaData meta) {
        this.meta = meta;
    }

    public KinveyMetaData.AccessControlList getAcl() {
        return acl;
    }

    public void setAcl(KinveyMetaData.AccessControlList acl) {
        this.acl = acl;
    }
}
