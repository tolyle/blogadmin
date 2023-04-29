package org.lyle.utils.file;

import org.apache.commons.io.FilenameUtils;
import org.lyle.exception.BusinessException;
import org.lyle.utils.date.DateUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

/**
 * 文件上传帮助类
 */
public class FileUploadUtils {

	public static String uploadFile(MultipartFile file, String filePath) throws IOException {
		int fileNameLength = file.getOriginalFilename().length();
		if (fileNameLength > 100) {
			throw new BusinessException("文件名称过长");
		}
		String fileName = file.getOriginalFilename();
		String extension = FilenameUtils.getExtension(fileName);
		if (StringUtils.isEmpty(extension)) {
			extension = MimeTypeUtils.getExtension(file.getContentType());
		}
		String encodingFilename = FileUtils.encodingFilename(fileName);
		fileName = DateUtils.datePath() + "/" + encodingFilename + "." + extension;
		File desc = new File(filePath, fileName);
		if (!desc.getParentFile().exists()) {
			desc.getParentFile().mkdirs();
		}
		if (!desc.exists()) {
			desc.createNewFile();
		}
		file.transferTo(desc);
		return fileName;
	}
}