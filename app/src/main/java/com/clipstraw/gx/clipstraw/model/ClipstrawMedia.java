package com.clipstraw.gx.clipstraw.model;

/**
 * Created by tahir on 1/30/2016.
 */
public class ClipstrawMedia {

    public static final byte MEDIA_IMAGE = 0;

    public static final byte MEDIA_VIDEO = 1;

    private byte mediaType;

    private String mediaUrl;

    private String extension;

    private String cacheFilePath;

    private boolean isCached;

    private String caption;

    public ClipstrawMedia(byte mediaType, String mediaUrl) {
        this.mediaType = mediaType;
        this.mediaUrl = mediaUrl;
    }

}
