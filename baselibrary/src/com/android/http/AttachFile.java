package com.android.http;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 附件
 * @author sunjuncai
 */
public class AttachFile implements Parcelable{
	
	/** 类型 */
	private String type;
	
	/** 路径 */
	private String path;
	
	/**是否加过水印**/
	private  int watermark = 0;

    public AttachFile() {
        
    }
    
	public int isWatermark() {
		return watermark;
	}

	public void setWatermark(int watermark) {
		this.watermark = watermark;
	}

    public AttachFile(String type, String path) {
        this.type = type;
        this.path = path;
    }

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public static final Parcelable.Creator<AttachFile> CREATOR = new Creator<AttachFile>() {
        public AttachFile createFromParcel(Parcel source) {
        	AttachFile dictionary = new AttachFile();
        	dictionary.path = source.readString();
        	dictionary.type = source.readString();
        	dictionary.watermark = source.readInt();
            return dictionary;
        }

        public AttachFile[] newArray(int size) {
            return new AttachFile[size];
        }

    };

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(path);
        dest.writeString(type);
        dest.writeInt(watermark);
    }

}

