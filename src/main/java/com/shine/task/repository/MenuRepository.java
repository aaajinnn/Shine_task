package com.shine.task.repository;

import com.shine.task.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MenuRepository extends JpaRepository<Menu, Long> {
    // 상위 메뉴 목록
    @Query("SELECT m FROM Menu m WHERE m.parent.id IS NULL ORDER BY m.listOrder")
    List<Menu> findAllByParentIsNull();

    // 마지막 순서 조회(상위 메뉴)
    @Query("SELECT MAX(m.listOrder) FROM Menu m WHERE m.parent.id IS NULL")
    int findLastOrderForParent();

    // 마지막 순서 조회(하위 메뉴)
    @Query("SELECT MAX(m.listOrder) FROM Menu m WHERE m.parent.id IS NOT NULL AND m.parent.id = :parentId")
    Integer findLastOrderForChild(@Param("parentId") Long parentId);

    // 순서 수정
    @Modifying
    @Query("UPDATE Menu m SET m.listOrder = :listOrder WHERE m.id =:id")
    void updateListOrder(@Param("id") Long id, @Param("listOrder") Integer listOrder);

    // 하위 메뉴 목록
    List<Menu> findAllByParentIsNotNull();

    @Query("SELECT count(m) FROM Menu m WHERE m.parent.id = :parentId ")
    int countByParentIdIsNotNull(Long parentId);

    Menu findByName(String name);
    Boolean existsByName(String name);

    @Query("SELECT m FROM Menu m WHERE m.parent.id =:parentId ORDER BY m.listOrder")
    List<Menu> findByParentId(@Param("parentId") Long parentId);

    Boolean existsByParentId(Long parentId);

}
