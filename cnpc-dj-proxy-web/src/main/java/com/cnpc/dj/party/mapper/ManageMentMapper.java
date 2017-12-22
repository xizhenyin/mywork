package com.cnpc.dj.party.mapper;

import java.util.List;

import com.cnpc.dj.party.entity.ManageMent;


public interface ManageMentMapper {
    int deleteByPrimaryKey(String id);

    int insert(ManageMent record);

    int insertSelective(ManageMent record);

    ManageMent selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(ManageMent record);

    int updateByPrimaryKey(ManageMent record);
    
     List<ManageMent> findList(ManageMent record);
     
    int batchUpdate(List<ManageMent> list);
}