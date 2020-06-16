package com.esst.ts.dao;

import com.esst.ts.model.Exam;
import com.esst.ts.model.ExamPOJO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface ExamMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Exam record);

    int insertSelective(Exam record);

    Exam selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Exam record);

    int updateByPrimaryKey(Exam record);

    @Update("UPDATE exam SET is_deleted=1 WHERE id=#{id}")
    int deleteWithId(@Param("id") int id);

    @Select("select e.id exam_id,e.exam_name,e.`status`,(case e.`status` when 1 then '发布' else '未发布' end) " +
            "status_name,e.create_user,ifnull(uc.user_count,0) user_count,e.exam_attribute" +
            ",concat((case e.exam_attribute when 1 then '考试' else '联合操作' end),'，','3题','，','60分钟') " +
            "edit_info from exam e LEFT JOIN (select exam_id,count(distinct user_id) user_count " +
            "from exam_user_relation group by exam_id) uc on e.id=uc.exam_id where e.`status`=#{status}")
    @ResultMap("BasePOJOResultMap")
    List<ExamPOJO> GetList(@Param("status") int status);
}