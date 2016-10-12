package com.veronica.medaily.kinvey;

import com.google.api.client.json.GenericJson;
import com.google.api.client.util.Key;
import com.kinvey.java.model.KinveyMetaData;
import com.kinvey.java.model.KinveyReference;

import java.util.ArrayList;

/**
 * Created by Veronica on 10/12/2016.
 */
public class CategoryEntity extends GenericJson {

    @Key("_id")
    String id;

    @Key("name")
    private String name;

    @Key("authorEmail")
    private String authorEmail;

    @Key("description")
    private String description;

    @Key("color")
    private int color;


    @Key(KinveyMetaData.JSON_FIELD_NAME)
    private KinveyMetaData meta; // Kinvey metadata, OPTIONAL

    @Key("_acl")
    private KinveyMetaData.AccessControlList acl;

    public CategoryEntity(){

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

    public String getAuthorEmail() {
        return authorEmail;
    }

    public void setAuthorEmail(String authorEmail) {
        this.authorEmail = authorEmail;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
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
