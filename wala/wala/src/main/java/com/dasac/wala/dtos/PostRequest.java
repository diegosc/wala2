package com.dasac.wala.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class PostRequest {

    private  String content;
    private  String img;
    private  Long pageId;

}
