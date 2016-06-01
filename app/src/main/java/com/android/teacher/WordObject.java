package com.android.teacher;

public class WordObject {

    private String KG;
    private String RU;
    private String TR;
    private byte[] img;
    private byte[] snd;
    private int cat;

    public WordObject(String kg, String ru, String tr, byte[] img, byte[] snd, int c_id) {
        this.KG = kg;
        this.RU = ru;
        this.TR = tr;
        this.img = img;
        this.snd = snd;
        this.cat = c_id;
    }

    public String getWord() {return KG;}

/*public void setWord(String kg) {this.KG = kg;}*/

    public String getRU() {return RU;}

/*public void setRU(String ru) {this.RU = ru;}*/

    public String getTR() {return TR;}

/*public void setTR(String tr) {this.TR = tr;}*/

    public byte[] getImg(){return img;}

/*public void setImg(byte[] img){this.img = img;}*/

    public byte[] getSnd(){return snd;}

/*public void setSnd(byte[] snd){this.snd = snd;}*/

    public int getCat() {return cat;}

/*public void setCat(int c_id) {this.cat = c_id;}*/

}
