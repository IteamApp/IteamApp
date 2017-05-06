package iteamapp.iteamapp;

/**
 * Created by Valentin 2017/5/6.
 */

public class Msg {

    public static final int TYPE_RECEIVE = 0;
    public static final int TYPE_SENT = 1;

    private String content;
    private int type;

    public Msg(String content, int type) {
        this.type = type;
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public int getType() {
        return type;
    }



}

