package com.onboarid.wanted.board.entity;

import com.onboarid.wanted.user.entity.User;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;

@Entity @Builder @Getter
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long boardId;

    @Column
    private String title;

    @Column
    private String content;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;

    public void updateTitle (String title) {
        this.title = title;
    }
    public void updateContent (String content) {
        this.content = content;
    }
}
