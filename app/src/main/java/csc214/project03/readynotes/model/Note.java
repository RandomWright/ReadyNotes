package csc214.project03.readynotes.model;

import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by Charlotte on 4/26/2017.
 *
 */

public class Note {
    private static final String TAG = "NOTE";

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
        picPath = "none";
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
        if (categories == null || categories.length == 0)
            return "";
        String all = categories[0];
        Log.d(TAG, all);

        for(int i = 1; i < categories.length; i++){
            all = all + "-" + categories[i];
        }
        Log.d(TAG, all);
        return all;
    }

    public void addCat(String cat){
        Log.d(TAG, cat + " SORT");
        if(categories == null){
            categories = new String[]{cat};
        }
        else {
            List<String> list = new ArrayList<String>(Arrays.asList(categories));
            if(!list.contains(cat)){
                list.add(cat);
            }
            String[] catArr = new String[list.size()];
            categories = list.toArray(catArr);
        }
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

    @Override
    public String toString(){
        return notes;
    }
}
