package com.example.jakub.bartnik.merchant.app.core.reward;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ChuckNorrisResponse {

    private String[] categories;
    @JsonProperty("created_at")
    private String createdAt;
    @JsonProperty("icon_url")
    private String iconUrl;
    private String id;
    @JsonProperty("updated_at")
    private String updatedAt;
    private String url;
    private String value;



}
