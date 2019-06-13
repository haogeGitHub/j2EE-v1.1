package tmall.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

public class FileUtil {
	public static InputStream parseUpload(HttpServletRequest request, Map<String, String> params) {
		InputStream is =null;
		try {
            DiskFileItemFactory factory = new DiskFileItemFactory();
            ServletFileUpload upload = new ServletFileUpload(factory);
            //设置上传文件的大小限制为10M
            factory.setSizeThreshold(1024 * 10240);
             
            List items = upload.parseRequest(request);
            Iterator iter = items.iterator();
            while (iter.hasNext()) {
                FileItem item = (FileItem) iter.next();
                if (!item.isFormField()) {
                    // item.getInputStream() 获取上传文件的输入流
                    is = item.getInputStream();
                } else {
                	String paramName = item.getFieldName();
                	String paramValue = item.getString();
                	paramValue = new String(paramValue.getBytes("ISO-8859-1"), "UTF-8");
                	params.put(paramName, paramValue);
                }
            }
             

             
        } catch (Exception e) {
            e.printStackTrace();
        }
		return is;
	}
	
	public static void wirte(File file, InputStream is){
		//将文件流写入到图片文件中
		try {
			//如果通过parseUpload 获取到的输入流是空的，或者其中的可取字节数为0，那么就不进行上传处理
			if(is!=null&&is.available()!=0){
				//输入输出流相对于内存而言
				FileOutputStream fos=new FileOutputStream(file);
				byte[] b=new byte[1024*1024];//一次向文件写入1M数据
				int length=0;
				while((length=is.read(b))!=-1){
					fos.write(b,0,length);
				}
				fos.flush();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
