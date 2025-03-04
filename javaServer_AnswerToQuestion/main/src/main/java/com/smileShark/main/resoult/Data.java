package com.smileShark.main.resoult;

@lombok.Data
public class Data {
    private Integer pageIndex;
    private Integer dataCount;
    private Object[] data;
    private Integer finishCount;
    private String finishContent;
}
