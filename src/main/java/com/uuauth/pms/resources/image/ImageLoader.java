package com.uuauth.pms.resources.image;

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

public abstract class ImageLoader {
	
	public final static void getImage(HttpServletResponse response,
			String imageName) throws IOException {
		InputStream is = ImageLoader.class.getResourceAsStream("/"
				+ ImageLoader.class.getPackage().getName()
						.replaceAll("\\.", "/") + "/" + imageName);
		ServletOutputStream os = response.getOutputStream();
		byte[] bs = new byte[1024];
		while (is.read(bs) > 0) {
			os.write(bs);
		}
		is.close();
		os.flush();
		os.close();
	}
	
}
