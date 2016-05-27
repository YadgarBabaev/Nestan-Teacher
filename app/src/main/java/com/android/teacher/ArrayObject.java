package com.android.teacher;

public class ArrayObject {

    private String[] list;
    private int[] ids;

    public ArrayObject(String[] list, int[] ids) {
        this.list = list;
        this.ids = ids;
    }

    public String[] getWord() {return list;}

    public int[] getIds(){return ids;}

}
