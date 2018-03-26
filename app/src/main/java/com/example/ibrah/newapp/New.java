package com.example.ibrah.newapp;

import android.os.Parcel;
import android.os.Parcelable;
public class New implements Parcelable{
    /**
     * A {@link New} object that contains details related to a single
     * New item to be displayed in NewListActivity
     */

    /**
     * New Title
     */
    private String mTitle;

    /**
     * New Author
     */
    private String mAuthor;

    /**
     * Rating
     */
    private String mDate;

    /**
     * Description
     */
    private String mSection;

    /**
     * Thumbnail Link
     */
    private String mLink;


    /**
     * Default Constructor - Constructs a new {@link New} object
     *
     * @param title         - Title of the New
     * @param author        - Author of the New
     * @param date        - Rating of the New (e.g. 3.5)
     * @param section   - Description of the New
     * @param link - Link for the New image
     */
    public New(String title, String author,
               String date, String section, String link) {

        mTitle = title;
        mAuthor = author;
        mDate = date;
        mSection = section;
        mLink = link;
    }

    public New(Parcel input) {

        mTitle = input.readString();
        mAuthor = input.readString();
        mDate = input.readString();
        mSection = input.readString();
        mLink = input.readString();
    }


    /**
     * Getter method - Title
     */
    public String getTitle() {
        return mTitle;
    }

    /**
     * Setter method - Title
     */
    public void setTitle(String title) {
        mTitle = title;
    }

    /**
     * Getter method - Author
     */
    public String getAuthor() {
        return mAuthor;
    }

    /**
     * Setter method - Author
     */
    public void setAuthor(String author) {
        mAuthor = author;
    }


    /**
     * Getter method - Rating
     */
    public String getmDate() {
        return mDate;
    }

    /**
     * Setter method - Rating
     */
    public void setmDate(String rating) {
        mDate = rating;
    }

    /**
     * Getter method - Description
     */
    public String getmSection() {
        return mSection;
    }

    /**
     * Setter method - Description
     */
    public void setmSection(String description) {
        mSection = description;
    }

    /**
     * Getter method - Thumbnail Link
     */
    public String getmLink() {
        return mLink;
    }

    /**
     * Setter method - Thumbnail Link
     */
    public void setmLink(String thumbnailLink) {
        mLink = thumbnailLink;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mTitle);
        dest.writeString(mAuthor);
        dest.writeString(mDate);
        dest.writeString(mSection);
        dest.writeString(mLink);
    }


    public static final Parcelable.Creator<New> CREATOR = new Parcelable.Creator<New>() {
        public New createFromParcel(Parcel in) {
            return new New(in);
        }

        public New[] newArray(int size) {
            return new New[size];
        }
    };
}

/**
 * Created by ibrah on 18/07/2017.
 */

