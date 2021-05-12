package io.itpl.microservice.api;

public class ResourceFilter {
	private int width;
	private int height;
	public ResourceFilter() {
		// TODO Auto-generated constructor stub
	}
	
	public ResourceFilter(int width, int height) {
		super();
		this.width = width;
		this.height = height;
	}

	public static ResourceFilter from(int width,int height) {
		return new ResourceFilter(width,height);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + height;
		result = prime * result + width;
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
		ResourceFilter other = (ResourceFilter) obj;
		if (height != other.height)
			return false;
		if (width != other.width)
			return false;
		return true;
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

	@Override
	public String toString() {
		return "ResourceFilter [width=" + width + ", height=" + height + "]";
	}

}
