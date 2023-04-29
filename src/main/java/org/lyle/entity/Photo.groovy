package org.lyle.entity

class Photo {
    private Long  id;
    private String title;
    private String tags;
    private String  thumbnail_url;

//    private TagType tagType;
//    TagType getTagType() {
//        return tagType
//    }
//
//    void setTagType(TagType tagType) {
//        this.tagType = tagType
//    }

    Long getId() {
        return id
    }

    void setId(Long id) {
        this.id = id
    }

    String getTitle() {
        return title
    }

    void setTitle(String title) {
        this.title = title
    }

    String getTags() {
        return tags
    }

    void setTags(String tags) {
        this.tags = tags
    }

    String getThumbnail_url() {
        return thumbnail_url
    }

    void setThumbnail_url(String thumbnail_url) {
        this.thumbnail_url = thumbnail_url
    }


}
