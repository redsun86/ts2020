package com.esst.ts.dao;

import com.esst.ts.model.*;

/**
 * 创建标识：梁建磊 2020/8/4 10:43
 */
public interface AD500uMapper {

    /**
     * 清空数据表
     *
     * @return
     */
    int truncatetable();

    /**
     * 插入产品
     *
     * @param mod
     * @return
     */
    int insertProduct(Product mod);

    Product getInsertProductModel(Product mod);

    /**
     * 插入工艺/单元
     *
     * @param mod
     * @return
     */
    int insertTechnology(Technology mod);

    Technology getInsertTechnologyModel(Technology mod);

    /**
     * 插入产品工艺关系
     *
     * @param productId
     * @param technologyId
     * @return
     */
    int insertProTechRelation(ProTechRelation mod);

    /**
     * 插入默认任务单
     *
     * @param mod
     * @return
     */
    int insertTask(Task mod);

    Task getInsertTaskModel(Task mod);

    /**
     * 插入用户与任务单关系
     *
     * @param mod
     * @return
     */
    int insertUserTaskRelation(UserTaskRelation mod);

    int insertTaskOperRelation(TaskOperRelation mod);

    /**
     * 插入工况
     *
     * @param mod
     * @return
     */
    int insertOperate(Operate mod);

    Operate getInsertOperateModel(Operate mod);

    /**
     * 插入默认配置
     *
     * @return
     */
    int insertDefoultSetting();
}
