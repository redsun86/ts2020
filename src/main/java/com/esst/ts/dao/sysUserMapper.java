package com.esst.ts.dao;

import com.esst.ts.model.sysUser;

public interface sysUserMapper {
    int deleteByPrimaryKey(Integer userid);

    int insert(sysUser record);

    int insertSelective(sysUser record);

    sysUser selectByPrimaryKey(Integer userid);

    int updateByPrimaryKeySelective(sysUser record);

    int updateByPrimaryKeyWithBLOBs(sysUser record);

    int updateByPrimaryKey(sysUser record);
}