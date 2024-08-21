package com.shine.task.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
@Entity
@Table
@Builder
public class Menu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 20, nullable = false)
    private String name; // 메뉴 이름

    @Column(length = 50)
    private String comment; // 메뉴 설명

    private Integer listOrder; // 메뉴의 순서

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id") // 하위메뉴, 부모 id 저장
    private Menu parent;

    @OneToMany(mappedBy = "parent")
    private List<Menu> children = new ArrayList<>();
}
