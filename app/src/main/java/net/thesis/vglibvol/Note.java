package net.thesis.vglibvol;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;


@Entity(tableName = "note_table")
public class Note implements Parcelable{

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String title;

    private String description;

    private String platform;

    private String guid;

    private String genres;

    private String release_date;

    private String developers;


    public static Note instance;
    private Note() {}

    protected Note(Parcel in) {
        id = in.readInt();
        title = in.readString();
        description = in.readString();
        platform = in.readString();
        guid = in.readString();
        genres = in.readString();
        release_date = in.readString();
        developers = in.readString();
    }

    public static final Creator<Note> CREATOR = new Creator<Note>() {
        @Override
        public Note createFromParcel(Parcel in) {
            return new Note(in);
        }

        @Override
        public Note[] newArray(int size) {
            return new Note[size];
        }
    };

    public static Note getInstance() {
        if (instance == null) {
            instance = new Note();
        }
        return instance;
    }


    public Note (String title, String description, String platform, String guid, String genres, String release_date, String developers) {
        this.title = title;
        this.description = description;
        this.platform = platform;
        this.guid = guid;
        this.genres = genres;
        this.release_date = release_date;
        this.developers = developers;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getPlatform() {
        return platform;
    }

    public String getGuid() {return guid;}

    public String getGenres() {return genres;}

    public String getRelease_date() {return release_date;}

    public String getDevelopers() {return developers;}



    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(title);
        dest.writeString(description);
        dest.writeString(platform);
        dest.writeString(guid);
        dest.writeString(genres);
        dest.writeString(release_date);
        dest.writeString(developers);
    }
}