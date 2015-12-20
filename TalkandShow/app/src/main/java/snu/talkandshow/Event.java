package snu.talkandshow;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Seungyong on 2015-11-06.
 */
public class Event implements Parcelable {
    private int id;
    private int type;
    private String title;
    private int date;
    private String place;
    private String host;
    private int fee;
    private String contact;
    private String info;
    private int updateTime;

    public int describeContents(){
        return 0;
    }
    public Event() {
        id = -1;
        type = -1;
        title = "";
        date = 0;
        place = "";
        host= "";
        fee= -1;
        contact="";
        info="";
        updateTime = -1;
    }
    public Event(Parcel in) {
        readFromParcel(in);
    }
    public Event(int id, int type, String name, String place, int date, String host, int fee, String contact, String info, int updateTime){
        this.id=id;
        this.type=type;
        this.title=name;
        this.date=date;
        this.place=place;
        this.host=host;
        this.fee=fee;
        this.contact=contact;
        this.info=info;
        this.updateTime=updateTime;
    }
    public void writeToParcel(Parcel dest, int flags){
        dest.writeInt(id);
        dest.writeInt(type);
        dest.writeString(title);
        dest.writeInt(date);
        dest.writeString(place);
        dest.writeString(host);
        dest.writeInt(fee);
        dest.writeString(contact);
        dest.writeString(info);
        dest.writeInt(updateTime);
    }
    private void readFromParcel(Parcel in) {
        id=in.readInt();
        type=in.readInt();
        title=in.readString();
        place=in.readString();
        date=in.readInt();
        host=in.readString();
        fee=in.readInt();
        contact=in.readString();
        info=in.readString();
        updateTime=in.readInt();
    }
    public static final Creator CREATOR = new Creator() {
        @Override
        public Event createFromParcel(Parcel in) {
            return new Event(in);
        }
        @Override
        public Event[] newArray(int size) {
            return new Event[size];
        }
    };

    public int getId() {
        return id;
    }
    public int getType(){ return type;}
    public String getTitle(){
        return title;
    }
    public int getDate() {
        return date;
    }
    public String getPlace(){
        return place;
    }
    public String getHost(){
        return host;
    }
    public int getFee(){
        return fee;
    }
    public String getContact(){
        return contact;
    }
    public String getInfo(){
        return info;
    }
    public int getUpdateTime() {return updateTime;}
    public void setId(int id) {
        this.id=id;
    }
    public void setType(int type) {this.type=type;}
    public void setTitle(String title){
        this.title=title;
    }
    public void setDate(int date) {
        this.date=date;
    }
    public void setPlace(String place){
        this.place=place;
    }
    public void setFee(int fee) {
        this.fee=fee;
    }
    public void setHost(String host){
        this.host=host;
    }
    public void setContact(String contact){
        this.contact=contact;
    }
    public void setInfo(String info){
        this.info=info;
    }
    public void setUpdateTime(int updateTime) {this.updateTime=updateTime;}
}
