package csc214.project03.readynotes.model;

import java.util.Date;
import java.util.UUID;

/**
 * Created by Charlotte on 4/26/2017.
 *
 */

public class Note {
    private UUID id;
    private String notes;
    private Date create;
    private Date editDate;
    private String picPath;
    private String[] categories;

    public Note(UUID id) {
        this.id = id;
    }

    public Note() { //create new
        id = UUID.randomUUID();
        create = new Date();
        editDate = create;
    }

    public Note(String notes){
        id = UUID.randomUUID();
        this.notes = notes;
        create = new Date();
        editDate = create;
        picPath = null;
        categories = null;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setCreate(Date create) {
        this.create = create;
    }

    public void setEditDate(Date editDate) {
        this.editDate = editDate;
    }

    public String[] getCategories() {
        return categories;
    }

    public String getCatString(){
        if (categories.length == 0)
            return "";
        String all = categories[0];

        for(int i = 1; i < categories.length; i++){
            all = all + "|" + categories[i];
        }

        return all;
    }

    public void setCategories(String[] categories) {
        this.categories = categories;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Date getCreate() {
        return create;
    }

    public Date getEditDate() {
        return editDate;
    }

    public String getPicPath() {
        return picPath;
    }

    public void setPicPath(String picPath) {
        this.picPath = picPath;
    }
}
