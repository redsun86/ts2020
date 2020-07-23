package com.esst.ts.service;

import com.esst.ts.model.TeacherStudentRelation;

import java.util.List;

/**
 * 教师学生关系——业务逻辑层接口定义
 * <p>关系==》Relation</p>
 * <p> 创建标识：梁建磊 2020/7/2 11:21</p>
 */
public interface TeacherStudentRelationService {
    /**
     * 常规方法——根据指定条件获取数据集合
     *
     * @param mod 请求参数
     * @return 返回数据集合
     */
    List<TeacherStudentRelation> GetList(TeacherStudentRelation mod);

    /**
     * 常规方法——获取在线老师的集合
     *
     * @return 返回数据集合
     */
    List<TeacherStudentRelation> GetOnLineTeacherList();
}
