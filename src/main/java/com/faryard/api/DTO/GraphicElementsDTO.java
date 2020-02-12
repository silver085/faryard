package com.faryard.api.DTO;

import java.util.List;

public class GraphicElementsDTO {
    private String status;
    private String message;

    private List<GraphDataDTO> data;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<GraphDataDTO> getData() {
        return data;
    }

    public void setData(List<GraphDataDTO> data) {
        this.data = data;
    }
}
