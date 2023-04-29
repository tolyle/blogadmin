package org.lyle.enumerate


public enum  TagType  {
    PHOTO(1, "照片"),
    BLOG(2, "博客"),

    private int value;
    private String desc;


    public static TagType getTagType(int tagType) {
        for (TagType tt : TagType.values()) {
            if (tagType.getValue() == tagType) {
                return tagType;
            }
        }
        return PHOTO;
    }


    public Integer getValue() {
        return this.value;
    }

    String getDesc() {
        return desc
    }

    void setDesc(String desc) {
        this.desc = desc
    }
}
