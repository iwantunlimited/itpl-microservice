package io.itpl.microservice.mongo;

public class FilterOption {
    /**
     *  i.e. _id of object (i.e. department._id)
     */
    private String id;
    /**
     *  i.e. Home and Furniture, Electronics etc.
     */
    private String caption;
    private String imageUrl;
    /**
     *  i.e. Apply this Filter or Not (based on User Input).
     */
    private boolean selected;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
