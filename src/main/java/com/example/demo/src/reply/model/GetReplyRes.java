package com.example.demo.src.reply.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
@AllArgsConstructor
public class GetReplyRes {
    private int Idx;
    private int userIdx;
    private int commentIdx;
    private String reply;
}
