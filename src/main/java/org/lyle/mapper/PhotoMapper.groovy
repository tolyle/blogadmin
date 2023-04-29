package org.lyle.mapper

import org.lyle.entity.Photo

interface PhotoMapper {
    void addPhoto(Photo photo);

    Photo queryPhotoById(Long id);
}
