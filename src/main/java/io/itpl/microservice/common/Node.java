package io.itpl.microservice.common;

import com.google.common.base.Strings;


import java.util.Date;
import java.util.List;

public class Node implements Comparable<Node>{

    private String id;
    private String name;
    private String description;
    private String imageUrl;
    private String backgroundImageUrl;
    private int childCount;
    private List<Node> childElements;
    private int listingPriority;
    private int level;
    private boolean root;
    private boolean leaf;
    private Date createdOn;
    private boolean inactive;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getBackgroundImageUrl() {
        return backgroundImageUrl;
    }

    public void setBackgroundImageUrl(String backgroundImageUrl) {
        this.backgroundImageUrl = backgroundImageUrl;
    }

    public List<Node> getChildElements() {
        return childElements;
    }

    public void setChildElements(List<Node> childElements) {
        this.childElements = childElements;
    }

    public int getListingPriority() {
        return listingPriority;
    }

    public void setListingPriority(int listingPriority) {
        this.listingPriority = listingPriority;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public boolean isRoot() {
        return root;
    }

    public void setRoot(boolean root) {
        this.root = root;
    }

    public boolean isLeaf() {
        return leaf;
    }

    public void setLeaf(boolean leaf) {
        this.leaf = leaf;
    }

    public int getChildCount() {
        return childCount;
    }

    public void setChildCount(int childCount) {
        this.childCount = childCount;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    public boolean isInactive() {
        return inactive;
    }

    public void setInactive(boolean inactive) {
        this.inactive = inactive;
    }

    @Override
    public int compareTo(Node o) {
        return o.listingPriority - this.listingPriority;
    }

}
