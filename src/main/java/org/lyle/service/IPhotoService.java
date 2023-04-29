package org.lyle.service;

import org.lyle.entity.Photo;


interface IPhotoService {
	void addPhoto(Photo photo);

	Photo queryPhotoById(Long id);
}
