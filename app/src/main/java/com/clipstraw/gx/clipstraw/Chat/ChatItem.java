package com.clipstraw.gx.clipstraw.Chat;

/**
 * Created by Faizzy on 22-01-2016.
 */
public class ChatItem {
    private  String name, time, imageUri;





    public ChatItem(String Name,String Time, String ImageUri) {
        this.name = Name;
        this.time =Time;
        this.imageUri =ImageUri;

    }

    protected String getImageUri() {
        return imageUri;
    }

    protected String getTime() {
        return time;
    }

    protected String getName() {
        return name;
    }

    protected void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }

    protected void setTime(String time) {
        this.time = time;
    }

    protected void setName(String name) {
        this.name = name;
    }


}

