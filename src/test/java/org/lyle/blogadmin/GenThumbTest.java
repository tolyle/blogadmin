package org.lyle.blogadmin;

import net.coobird.thumbnailator.Thumbnails;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;

public class GenThumbTest {

	public static void main(String[] args) {
		try {
			for (int i = 0; i < 100; i++) {


				ByteArrayOutputStream os = new ByteArrayOutputStream();


				Thumbnails.of(new File("d:\\Users\\tolyl\\Pictures\\test\\DSC02385.jpg"))
					//设置缩略图大小，按等比缩放
					.size(2304, 1728).toOutputStream(os);
				//将生成的缩略图写入文件
//				.toFile(new File("d:\\Users\\tolyl\\Pictures\\test\\bb.jpg"));

				ByteArrayInputStream fileInputStream = new ByteArrayInputStream(os.toByteArray());
				BufferedImage img = ImageIO.read(fileInputStream);

				System.out.println(img.getWidth());
				System.out.println(img.getHeight());
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
