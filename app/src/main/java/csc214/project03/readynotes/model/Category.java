package csc214.project03.readynotes.model;

import java.util.ArrayList;

/**
 * Created by Charlotte on 4/27/2017.
 *
 */

public class Category {
    private String title;
    private ArrayList<Note> notes;
    private String[] parameters;
    private TimeFrame timeSlot;


    public boolean sort(Note note){ //Will sort the note if it is in the time slot or has key words.
        boolean yes = false;

        for(String para: parameters) {
            if (note.getNotes().toLowerCase().contains(para)) {
                yes = true;
            }
        }

        if(timeSlot != null){
            if(timeSlot.inFrame(note.getCreate())){
                yes = true;
            }
        }

        return yes;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ArrayList<Note> getNotes() {
        return notes;
    }

    public void setNotes(ArrayList<Note> notes) {
        this.notes = notes;
    }

    public String[] getParameters() {
        return parameters;
    }

    public void setParameters(String[] parameters) {
        this.parameters = parameters;
    }

    public TimeFrame getTimeSlot() {
        return timeSlot;
    }

    public void setTimeSlot(TimeFrame timeSlot) {
        this.timeSlot = timeSlot;
    }

    public void setTimeSlot(int[] days, int sHour, int sMin, int eHour, int eMin){
        timeSlot = new TimeFrame(days, sHour, sMin, eHour, eMin);
    }
}
