package com.example.demo.src.reply.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PatchReplyReq {
    private int replyIdx;
    private String reply;
}
