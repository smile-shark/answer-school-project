package com.smileShark.main.resoult;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;

@Getter
public class SubsectionId {
    private String subsectionId;

    @JsonProperty("subsectionId")
    public void setSubsectionId(String subsectionId) {
        this.subsectionId = subsectionId;
    }

    @Override
    public String toString() {
        return "SubsectionId{" +
                "subsectionId='" + subsectionId + '\'' +
                '}';
    }
}
