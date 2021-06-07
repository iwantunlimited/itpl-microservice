package io.itpl.microservice.api;

import io.itpl.microservice.LoggedInUser;

import java.util.Arrays;
import java.util.List;


/**
 *  This class used to Hold the File Attachment uploaded by the App User.
 *  Since we aims to run the service as Docker Container, we have to ensure we do not
 *  end up using any local volumes or network volumes for temp or permanent storage of file.
 *
 *  MediaDeliveryGateway will build UserFile from the Multipart file property received in HTTP Request.
 *  In Case the File needs to be stored permanently, respective service will use Cloud Storage service (i.e. AWS S3, Google Drive etc.)
 *  instead of any local or network storage.
 *
 *  In-Reverse, when App User want to access existing File (i.e. Download or view) in that case Service wil download the Content from
 *  the Cloud Storage and Again Build the UserFile Object to hold the Content of the File.
 *
 */
public class UserFile {


	private String domain;
	private LoggedInUser owner;
	private String fileName;
	private String mimeType;
	private long size;
	private byte[] content;
	private String contentBase64;
	private boolean isDirectory;
	private String directoryId;
	private List<String> tags;
	private ResourceFilter requiredSize;
	private boolean cacheHit;
	private int age;

	public boolean isCacheHit() {
		return cacheHit;
	}

	public void setCacheHit(boolean cacheHit) {
		this.cacheHit = cacheHit;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getMimeType() {
		return mimeType;
	}
	public void setMimeType(String mimeType) {
		this.mimeType = mimeType;
	}
	public long getSize() {
		return size;
	}
	public void setSize(long size) {
		this.size = size;
	}
	public byte[] getContent() {
		return content;
	}
	public void setContent(byte[] content) {
		this.content = content;
	}
	

	public String getDomain() {
		return domain;
	}
	public void setDomain(String domain) {
		this.domain = domain;
	}
	@Override
	public String toString() {
		return "UserFile [domain=" + domain + ", owner=" + owner + ", fileName=" + fileName + ", mimeType=" + mimeType
				+ ", size=" + size + "]";
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(content);
		result = prime * result + ((domain == null) ? 0 : domain.hashCode());
		result = prime * result + ((fileName == null) ? 0 : fileName.hashCode());
		result = prime * result + ((mimeType == null) ? 0 : mimeType.hashCode());
		result = prime * result + (int) (size ^ (size >>> 32));
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserFile other = (UserFile) obj;
		if (!Arrays.equals(content, other.content))
			return false;
		
		if (domain == null) {
			if (other.domain != null)
				return false;
		} else if (!domain.equals(other.domain))
			return false;
		if (fileName == null) {
			if (other.fileName != null)
				return false;
		} else if (!fileName.equals(other.fileName))
			return false;
		if (mimeType == null) {
			if (other.mimeType != null)
				return false;
		} else if (!mimeType.equals(other.mimeType))
			return false;
		if (owner == null) {
			if (other.owner != null)
				return false;
		} else if (!owner.equals(other.owner))
			return false;
		if (size != other.size)
			return false;
		return true;
	}
	public boolean isDirectory() {
		return isDirectory;
	}
	public void setDirectory(boolean isDirectory) {
		this.isDirectory = isDirectory;
	}
	public String getDirectoryId() {
		return directoryId;
	}
	public void setDirectoryId(String directoryId) {
		this.directoryId = directoryId;
	}
	public List<String> getTags() {
		return tags;
	}
	public void setTags(List<String> tags) {
		this.tags = tags;
	}
	public ResourceFilter getRequiredSize() {
		return requiredSize;
	}
	public void setRequiredSize(ResourceFilter requiredSize) {
		this.requiredSize = requiredSize;
	}

	public void setOwner(LoggedInUser owner) {
		this.owner = owner;
	}

	public String getContentBase64() {
		return contentBase64;
	}

	public void setContentBase64(String contentBase64) {
		this.contentBase64 = contentBase64;
	}

	public LoggedInUser getOwner() {
		return owner;
	}
}

