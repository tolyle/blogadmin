package org.lyle.entity;

public class Photo {
	private Long  id;
	private String title;
	private String tags;
	private String  thumbnail_url;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTags() {
		return tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}

	public String getThumbnail_url() {
		return thumbnail_url;
	}

	public void setThumbnail_url(String thumbnail_url) {
		this.thumbnail_url = thumbnail_url;
	}
//    private TagType tagType;
//    TagType getTagType() {
//        return tagType
//    }
//
//    void setTagType(TagType tagType) {
//        this.tagType = tagType
//    }


}
