package com.android.teacher;

public class WordObject {

    private String KG;
    private String RU;
    private String TR;
    private byte[] img;
    private byte[] snd;

    public WordObject(String kg, String ru, String tr, byte[] img, byte[] snd) {
        this.KG = kg;
        this.RU = ru;
        this.TR = tr;
        this.img = img;
        this.snd = snd;
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
}
