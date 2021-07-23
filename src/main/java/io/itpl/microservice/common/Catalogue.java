package io.itpl.microservice.common;

import com.google.common.base.Strings;

import java.util.List;

public class Catalogue  {

	/**
	 *  The title of the catalogue. User Applications can use this property while displaying listing of the objects
	 *  (i.e. Product, Category etc.) to the end users.
	 */
	private String title;
	/**
	 *  rich description of the object usually in the HTML format.
	 */
	private String htmlDescription;
	/**
	 *  In case there is a dedicated landing page of the object in the user application,
	 *  this property can be used to hold a media object for displaying the banner. 
	 */
	private MediaObject bannerImage;
	/**
	 *  Almost same as a Banner, But here expected to hold reference to the media object which is aligned vertically 
	 *  (i.e. like a Poster) so that can be separated out from the banner.
	 *  
	 *  The one object can have banner as well as Poster both media object reference to support the rendering catalogues 
	 *  at different places within a User Application.
	 */
	private MediaObject posterImage;
	/**
	 *  The User Applications might want to display listing of the Objects (i.e. Product, Brands, Categoroes etc.) at certain listing pages.
	 *  this property can be used to define a link to the media that needs to be displayed with listing as a default.
	 */
	private MediaObject thumbImagePrimary;
	/**
	 *  This is also a link to the media object for using in Listing as thumb image. however, the secondary images can be useful to switch over the 
	 *  media link upon user events i.e. onMouseOver or onClick  etc.  
	 */
	private MediaObject thumbImageSecondary;
	/**
	 *  Additional link of media object kept as a provision to support for future or custom needs of user applications.
	 */
	private MediaObject customImagePrimary;
	/**
	 * Same as thumabImage, secondary image can hold the link to media for switching runtime upon events (i.e. onMouseOver or click etc.)
	 */
	private MediaObject customImageSecondary;
	/**
	 *  Unlike thumb, As the name refer this can hold a link to the icon representation of the media object.
	 */
	private MediaObject iconImage;
	/**
	 *  As reflected in the name itself, this property can hold a link to the image for displaying as a background of the object.
	 */
	private MediaObject backgroundImage;
	/**
	 *  Instead of the single banner image, it might needed to display multiple images as a slides in banner.
	 *  This property holds an ordered collection of the bannerImages which will be played as a slide show by the user application.
	 */
	private List<MediaObject> bannerImageSlides;
	private List<String> bannerImageUrls;
	private List<String> thumbImageUrl;
	/**
	 *  Apart from the Listing and Landing Page, this property holds the media content of the Object (i.e. Product Catalogue). 
	 */
	private List<MediaObject> mediaContent;
	private List<String> mediaContentUrls;
	/**
	 *  Group Wise Attribute Value Map which can be displayed as a Specifications.
	 *  For Example,
	 *  Display :
	 *  	Screen-Width  	: 450
	 *  	Screen-Height 	: 820
	 *  	Display Type 	: AMOLED v3.0
	 *  Storage :
	 *  	Storage-type	: In-Build
	 *  	Storage-Capacity: 256GB 
	 */
	private List<KeyValueGroup> specifications;
	/**
	 * Group wise Ordered List of Strings, which can be displayed as a bullet points by the user application. 
	 */
	private List<GroupedValues> features;
	/**
	 *  Additional info to build search index of this catalogue. These tags will be used in for SEO meta-info section as well.  
	 */
	private List<String> tags;
	/**
	 *  The scale of importance which will be used by User Application to decide the order of display.
	 */
	private int listingPriority;
	/**
	 *  In Case the user rating is enabled on this catalogue, this property will hold the count of total user reviews. 
	 */
	private int totalRatings;
	/**
	 *  As mentioned in the name, its an average rating derived out of all the user ratings that are received for this catalogue. 
	 */
	private int averageRating;
	/**
	 *  A Flag just to badge in case the catalogue is sponsored.
	 */
	private boolean sponsored;
	private GroupedValues highlights;
	private String imageUrl;

	public static String thumbImageUrl(Catalogue catalogue) {
		if(catalogue == null){
			return null;
		}
		if(!Strings.isNullOrEmpty(catalogue.getImageUrl())){
			return catalogue.getImageUrl();
		}

		MediaObject mediaObject = catalogue.getThumbImagePrimary();
		if (mediaObject != null) {
			return mediaObject.getSrc();
		}

		List<String> thumbImages = catalogue.getThumbImageUrl();
		if(thumbImages!=null && !thumbImages.isEmpty()){
			return thumbImages.get(0);
		}

		return null;
	}

	public MediaObject getBannerImage() {
		return bannerImage;
	}
	public void setBannerImage(MediaObject bannerImage) {
		this.bannerImage = bannerImage;
	}
	public MediaObject getPosterImage() {
		return posterImage;
	}
	public void setPosterImage(MediaObject posterImage) {
		this.posterImage = posterImage;
	}
	public MediaObject getThumbImagePrimary() {
		return thumbImagePrimary;
	}
	public void setThumbImagePrimary(MediaObject thumbImagePrimary) {
		this.thumbImagePrimary = thumbImagePrimary;
	}
	public MediaObject getThumbImageSecondary() {
		return thumbImageSecondary;
	}
	public void setThumbImageSecondary(MediaObject thumbImageSecondary) {
		this.thumbImageSecondary = thumbImageSecondary;
	}
	public MediaObject getCustomImagePrimary() {
		return customImagePrimary;
	}
	public void setCustomImagePrimary(MediaObject customImagePrimary) {
		this.customImagePrimary = customImagePrimary;
	}
	public MediaObject getCustomImageSecondary() {
		return customImageSecondary;
	}
	public void setCustomImageSecondary(MediaObject customImageSecondary) {
		this.customImageSecondary = customImageSecondary;
	}
	public MediaObject getIconImage() {
		return iconImage;
	}
	public void setIconImage(MediaObject iconImage) {
		this.iconImage = iconImage;
	}
	public MediaObject getBackgroundImage() {
		return backgroundImage;
	}
	public void setBackgroundImage(MediaObject backgroundImage) {
		this.backgroundImage = backgroundImage;
	}
	public List<MediaObject> getBannerImageSlides() {
		return bannerImageSlides;
	}
	public void setBannerImageSlides(List<MediaObject> bannerImageSlides) {
		this.bannerImageSlides = bannerImageSlides;
	}
	public List<MediaObject> getMediaContent() {
		return mediaContent;
	}
	public void setMediaContent(List<MediaObject> mediaContent) {
		this.mediaContent = mediaContent;
	}

	public List<KeyValueGroup> getSpecifications() {
		return specifications;
	}

	public void setSpecifications(List<KeyValueGroup> specifications) {
		this.specifications = specifications;
	}

	public List<GroupedValues> getFeatures() {
		return features;
	}
	public void setFeatures(List<GroupedValues> features) {
		this.features = features;
	}
	public List<String> getTags() {
		return tags;
	}
	public void setTags(List<String> tags) {
		this.tags = tags;
	}
	public int getListingPriority() {
		return listingPriority;
	}
	public void setListingPriority(int listingPriority) {
		this.listingPriority = listingPriority;
	}
	public int getTotalRatings() {
		return totalRatings;
	}
	public void setTotalRatings(int totalRatings) {
		this.totalRatings = totalRatings;
	}
	public int getAverageRating() {
		return averageRating;
	}
	public void setAverageRating(int averageRating) {
		this.averageRating = averageRating;
	}
	public boolean isSponsored() {
		return sponsored;
	}
	public void setSponsored(boolean sponsored) {
		this.sponsored = sponsored;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getHtmlDescription() {
		return htmlDescription;
	}

	public void setHtmlDescription(String htmlDescription) {
		this.htmlDescription = htmlDescription;
	}

	public List<String> getBannerImageUrls() {
		return bannerImageUrls;
	}

	public void setBannerImageUrls(List<String> bannerImageUrls) {
		this.bannerImageUrls = bannerImageUrls;
	}

	public List<String> getThumbImageUrl() {
		return thumbImageUrl;
	}

	public void setThumbImageUrl(List<String> thumbImageUrl) {
		this.thumbImageUrl = thumbImageUrl;
	}

	public List<String> getMediaContentUrls() {
		return mediaContentUrls;
	}

	public void setMediaContentUrls(List<String> mediaContentUrls) {
		this.mediaContentUrls = mediaContentUrls;
	}

	public GroupedValues getHighlights() {
		return highlights;
	}

	public void setHighlights(GroupedValues highlights) {
		this.highlights = highlights;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
}
