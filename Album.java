
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.servlet.http.Part;

import com.dropbox.core.DbxClient;
import com.dropbox.core.DbxException;
import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.DbxWriteMode;
import com.dropbox.core.DbxEntry;

public class Album
{
	DbxRequestConfig config=new DbxRequestConfig("JavaTutorial/1.0",Locale.getDefault().toString());
	String accessToken="VuiOP1TMTFAAAAAAAAAAkZMV65H9vV9uRYOdtkQwcQeYTeTOMUQGBflaWLU37wRv";
	DbxClient client = new DbxClient(config, accessToken);

	Part file;
	String display;
	String photos;
	String delete;
	String photoURL;
	Map<String,String> all_photos = new HashMap<String,String>();
	public Part getFile()
	{
		return file;
	}
	public void setFile(Part file)
	{
		this.file = file;
	}
	public String getDisplay()
	{
		return display;
	}
	public void setDisplay(String display)
	{
		this.display = display;
	}
	public Map<String,String> getPhotos()
	{
		DbxEntry.WithChildren listing = null;
		try
		{
			listing =client.getMetadataWithChildren("/");
		}
		catch
		(DbxException e)
		{
			e.printStackTrace();
		}
		for(DbxEntry child : listing.children)
		{
			photos=child.name;
			String file_url = null;
			
			try
			{
				file_url = client.createShareableUrl("/"+child.path).replace("://www.", "://dl.");
			}
			catch(DbxException e)
			{

				e.printStackTrace();
			}

			if(file_url!=null)
			{
				all_photos.put(child.name,file_url);
			}
		}
		return all_photos;
	}
	public void setPhotos(String photos)
	{
		this.photos = photos;
	}
	public String getDelete()
	{
		return delete;
	}
	public void setDelete(String delete)
	{
		this.delete = delete;
	}
	public String getPhotoURL()
	{
		return photoURL;
	}
	public void setPhotoURL(String photoURL)
	{
		this.photoURL = photoURL;
	}
	public void displayImage()
	{
		setPhotoURL(getDisplay());
		getPhotos();
		
	}
	public void deleteFiles()
	{
		String delete_file = getDelete();
		String delete_file_name = "";
		for(Map.Entry<String, String> d_file_name : all_photos.entrySet())
		{
			if(delete_file.equals(d_file_name.getValue()))
			{
				delete_file_name=d_file_name.getKey();
			}
		}
		all_photos.remove(delete_file_name);
		try
		{
			client.delete("/"+delete_file_name);
		}
		catch(DbxException e)
		{
			e.printStackTrace();
		}
	}
	
	
	public String getFileName(String content_disposition)
	{
		String file_name = "";
		String temp = this.file.getHeader("content-disposition");
		String[] details = temp.split(";");
		for(int i=0;i<details.length;i++)
		{
			if(details[i].trim().startsWith("filename"))
			{
				int file_index = details[i].indexOf("filename");
				String file_temp_name = details[i].substring(file_index+10);
				String a =file_temp_name.substring(0,file_temp_name.length()-1);
				String[] p = a.split("\\\\");
				file_name=p[p.length-1];
			}
		}
		return file_name;
	}

	public void upload()
	{
		if (this.file == null)
		{
			return;
		}
		String c = this.file.getHeader("content-disposition");
		String file_name = getFileName(c);
		InputStream stream;
		try
		{
			stream = this.file.getInputStream();
			try
			{
				DbxEntry.File uploadedFile	=	null;
				try
				{
					uploadedFile=client.uploadFile("/"+file_name,DbxWriteMode.add(),this.file.getSize(),stream);
				}
				catch
				(DbxException e)
				{
					e.printStackTrace();
				}
			}
			finally
			{
				stream.close();
			}
		}
		catch(IOException e1)	
		{	
			e1.printStackTrace();
		}
		getPhotos();
		
		FacesContext context=FacesContext.getCurrentInstance();
		String viewId=context.getViewRoot().getViewId();
		javax.faces.application.ViewHandler	handler	=context.getApplication().getViewHandler();
		UIViewRoot root	= handler.createView(context,viewId);
		root.setViewId(viewId);
		context.setViewRoot(root);
	}
}		