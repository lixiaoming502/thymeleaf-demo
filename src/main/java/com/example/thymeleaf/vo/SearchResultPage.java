package com.example.thymeleaf.vo;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lixiaoming on 2018/8/1.
 */
@Setter
@Getter
public class SearchResultPage {

    private List<String> resultItems = new ArrayList<>();
    private String nextPage;
    private int statusCode;

    public void addItem(String item){
        resultItems.add(item);
    }
}
