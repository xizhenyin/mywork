package com.cnpc.dj.party.mapper;

import java.util.List;

import com.cnpc.dj.party.entity.ThirdCompany;


public interface ThirdCompanyMapper {
    int deleteByPrimaryKey(String id);

    int insert(ThirdCompany record);

    int insertSelective(ThirdCompany record);

    ThirdCompany selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(ThirdCompany record);

    int updateByPrimaryKey(ThirdCompany record);
    
    List<ThirdCompany> findList(ThirdCompany record);
}