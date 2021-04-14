package net.thesis.vglibvol;


public class SearchedItem {
    private String mImageUrl;
    private String mTitle;
    private String mPlatforms;
    private String mGuid;



    public SearchedItem(String imageUrl, String creator, String platforms, String guid) {
        mImageUrl = imageUrl;
        mTitle = creator;
        mPlatforms = platforms;
        mGuid = guid;
    }



    public String getImageUrl() {
        return mImageUrl;
    }

    public String getCreator() {
        return mTitle;
    }

    public String getNameCount() {
        return mPlatforms;
    }

    public String getGuid() {return mGuid;}

}
