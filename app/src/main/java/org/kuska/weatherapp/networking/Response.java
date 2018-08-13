package org.kuska.weatherapp.networking;

import com.google.gson.annotations.SerializedName;

/**
 * @author Petr Kuška (kuska.petr@gmail.com)
 * @since 2018/08/12
 */
public class Response {
    @SerializedName("status")
    public String status;

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    @SuppressWarnings({"unused", "used by Retrofit"})
    public Response() {
    }

    public Response(String status) {
        this.status = status;
    }
}
