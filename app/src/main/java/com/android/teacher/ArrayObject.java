package com.android.teacher;

public class ArrayObject {

    private String[] kgList;
    private String[] ruList;
    private String[] trList;
    private int[] ids;

    public ArrayObject(String[] kgList, String[] ruList, String[] trList, int[] ids) {
        this.kgList = kgList;
        this.ruList = ruList;
        this.trList = trList;
        this.ids = ids;
    }

    public String[] getKgList() {return kgList;}

    public String[] getRuList() {return ruList;}

    public String[] getTrList() {return trList;}

    public int[] getIds(){return ids;}

}
