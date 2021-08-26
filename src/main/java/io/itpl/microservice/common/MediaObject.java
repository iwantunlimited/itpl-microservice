package io.itpl.microservice.common;

import org.springframework.data.annotation.Transient;

import java.util.List;

/**
 * 
 * @author timirpatel
 * @since 12-dec-2020
 * 
 * Model of the Media which can be used with any object (i.e. Category, Brand, Store, Product Catalogue etc.)
 * Any Catalogue can contains Images, Videos or Documents and many other specifications and rich informative text (html).
 * This object defines a global properties that can be supported by any Model which means to be implemented as Catalogue.
 */
public class MediaObject {

	/**
	 *  Declaration of Constants for each Supported Media type
	 */
	public static final String TYPE_IMAGE = "IMAGE";
	public static final String TYPE_VIDEO = "VIDEO";
	public static final String TYPE_DOCUMENT = "DOCUMENT";
	public static final String TYPE_OTHER = "OTHER";
	/**
	 *  The Ordered Collection of the Supported media types which can be used.
	 *  This constant can be used by service or rest controllers to supply the list of values to populate as options in App.
	 */
	public static final String [] LIST_SUPPORTED_MEDIA_TYPES = {
			TYPE_IMAGE,
			TYPE_VIDEO,
			TYPE_DOCUMENT,
			TYPE_OTHER 
	};
	
	/**
	 *  Type of Media, Possible Values : IMAGE, VIDEO, DOCUMENT or OTHER.
	 */
	private String type;
	/**
	 *  URL/URI of the Media. Value can be iWant CDN ResourceId or external URL.
	 */
	private String src;
	/**
	 *  The Caption which can be displayed as overlay text or alt info of meida.
	 */
	private String caption;
	/**
	 *  In case the Media Object is part of sequential images slide show, the sequence number can be used by apps to arrange and run them in user defined sequence number. 
	 */
	private int sequenceNumber;
	/**
	 *  tags will be used for search and indexing. The same tags will be exposed in meta-info of the app for google seo.
	 */
	private List<String> tags;
	/**
	 *  Standard mimeType of the media type so app can use the respective codecs or components for rendering.
	 */
	private String mimeType;
	/**
	 *  file size of the media in bytes.
	 */
	private long sizeInBytes;
	/**
	 *  Duration of Video in seconds, or Number of Pages in case of document
	 */
	private long length;
	/**
	 * Width of Media 
	 */
	private int width;
	/**
	 * Height of Media
	 */
	private int height;
	/**
	 *  Aspect ration will be helpful for apps to set the container dynamically for best possible results.
	 */
	private String aspectRatio;

	private String title;
	private String subTitle;
	private String description;
	@Transient private String base64Content;
	/**
	 * List of Source Object having id and imageUrl field 
	 */
	private List<String>mediaSource;
	
	public List<String> getMediaSource() {
		return mediaSource;
	}

	public void setMediaSource(List<String> mediaSource) {
		this.mediaSource = mediaSource;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSubTitle() {
		return subTitle;
	}

	public void setSubTitle(String subTitle) {
		this.subTitle = subTitle;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getSrc() {
		return src;
	}
	public void setSrc(String src) {
		this.src = src;
	}
	public String getCaption() {
		return caption;
	}
	public void setCaption(String caption) {
		this.caption = caption;
	}
	public int getSequenceNumber() {
		return sequenceNumber;
	}
	public void setSequenceNumber(int sequenceNumber) {
		this.sequenceNumber = sequenceNumber;
	}
	public List<String> getTags() {
		return tags;
	}
	public void setTags(List<String> tags) {
		this.tags = tags;
	}
	public String getMimeType() {
		return mimeType;
	}
	public void setMimeType(String mimeType) {
		this.mimeType = mimeType;
	}
	public long getSizeInBytes() {
		return sizeInBytes;
	}
	public void setSizeInBytes(long sizeInBytes) {
		this.sizeInBytes = sizeInBytes;
	}
	public long getLength() {
		return length;
	}
	public void setLength(long length) {
		this.length = length;
	}
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	public String getAspectRatio() {
		return aspectRatio;
	}
	public void setAspectRatio(String aspectRatio) {
		this.aspectRatio = aspectRatio;
	}

	public String getBase64Content() {
		return base64Content;
	}

	public void setBase64Content(String base64Content) {
		this.base64Content = base64Content;
	}
}
