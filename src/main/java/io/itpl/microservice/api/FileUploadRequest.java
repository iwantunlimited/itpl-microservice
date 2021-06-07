package io.itpl.microservice.api;

public class FileUploadRequest extends ApiRequestBody {

    private ResourceFilter imageFilter;
    private UserFile payload;
    private UserFile requestBody;

    public ResourceFilter getImageFilter() {
        return imageFilter;
    }

    public void setImageFilter(ResourceFilter imageFilter) {
        this.imageFilter = imageFilter;
    }

    public UserFile getPayload() {
        return payload;
    }

    public void setPayload(UserFile payload) {
        this.payload = payload;
    }

    public UserFile getRequestBody() {
        return requestBody;
    }

    public void setRequestBody(UserFile requestBody) {
        this.requestBody = requestBody;
    }
}
