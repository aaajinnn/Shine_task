package com.shine.task.repository;

import com.shine.task.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MenuRepository extends JpaRepository<Menu, Long> {
    // 상위 메뉴 목록
    List<Menu> findAllByParentIsNull();

    // 하위 메뉴 목록
    List<Menu> findAllByParentIsNotNull();
}
