package com.harambase.pioneer.dao.mapper;

import com.harambase.pioneer.pojo.Relation;

public interface RelationMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Relation record);

    int insertSelective(Relation record);

    Relation selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Relation record);

    int updateByPrimaryKey(Relation record);
}