package com.esst.ts.service.impl;

import com.esst.ts.constants.Constants;
import com.esst.ts.dao.*;
import com.esst.ts.model.*;
import com.esst.ts.service.FZhKTService;
import com.esst.ts.utils.DateUtils;
import org.apache.poi.hpsf.DocumentSummaryInformation;
import org.apache.poi.hpsf.SummaryInformation;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.esst.ts.constants.Constants.StudyType;

@Service
@Transactional(rollbackFor = Exception.class)
public class FZhKTImpl implements FZhKTService {


    @Resource
    private com.esst.ts.dao.FZhKTMapper FZhKTMapper;
    @Resource
    public UserLiveMapper userliveScore;
    @Resource
    private UserLiveDataMapper userlivedata;

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Resource
    private UserScoreRecordMapper userScoreRecord;
    @Resource
    private TaskMapper task;
    @Resource
    private OperateMapper operate;
    @Resource
    private UserMapper user;
    @Resource
    private ExamMapper examMapper;
    @Resource
    private QuestionsMapper questionsMapper;
    @Resource
    private UserLoginLogMapper userLoginLogMapper;
    @Resource
    private UserTokenDao userTokenDao;
    @Resource
    UserScoreRecordMapper userScoreRecordMapper;

    @Override
    public List<Task> getCourseTaskLst(int courseID) {
        return FZhKTMapper.getCourseTaskLstBytechId(courseID);
    }

    @Override
    public boolean inserUserLiveWithBLOBS(UserLiveWithBLOBs score) {
        userliveScore.insert(score);
        return true;
    }

    @Override
    public boolean insertUserLiveDataWithBLOBS(UserLiveDataWithBLOBs score) {
        userlivedata.insert(score);
        return true;
    }

    @Override
    public int insertUserScoreRecore(UserScoreRecord score) {
        return userScoreRecord.insert(score);
    }

    @Override
    public int updateUserScoreRecored(UserScoreRecord usr) {

        return userScoreRecord.updateByPrimaryKey(usr);
    }

    @Override
    public List<UserScoreRecord> getUserScoreRecore(int userid, int taskid, int operateid) {
        return userScoreRecord.getUserScoreRecore(userid, taskid, operateid);
    }

    public FZhKTImpl() {
        super();
    }

    @Override
    public List<UserLiveData> getUserLiveDataList() {
        return userlivedata.getUserLiveDataDistinct();
    }

    @Override
    public List<UserLive> getUserLiveList() {
        return userliveScore.geUserLiveDistinct();
    }

    @Override
    public List<Task> getTaskListAll() {
        return FZhKTMapper.gteTaskListAll();
    }

    @Override
    public List<Operate> getOprateList() {
        return operate.getOperateListAll();
    }

    @Override
    public List<User> getUserListAll() {
        return user.getUserListAll();
    }

    @Override
    public UserLiveWithBLOBs updateUserLive(UserLiveWithBLOBs userlive) {
        UserLiveWithBLOBs ul = new UserLiveWithBLOBs();
        ul = userliveScore.getUserLiveByUserTaskType(userlive.getUserId(), userlive.getTaskId(), userlive.getStudyType());

        if (ul != null) {
            userlive.setId(ul.getId());
            if (userlive.getScoreStatues() == 1 || userlive.getScoreStatues() == 2) {

                double duration = new Date().getTime() - ul.getStartTime();
                userlive.setStudyDuration(duration);
                userlive.setStartTime(ul.getStartTime());
            }
            userliveScore.updateByPrimaryKey(userlive);
        } else {
            userliveScore.insert(userlive);
        }
        return userlive;
    }

    //获取所有试题表
    @Override
    public List<Exam> getExamListAll() {
        return examMapper.getExamListAll();
    }

    @Override
    public List<Questions> getQuestionListAll() {
        return questionsMapper.getQuestionAll();
    }

    @Override
    public List<UserLiveWithBLOBs> getUserLiveAll() {
        return userliveScore.getUserLiveAll();
    }

    @Override
    public List<UserLiveWithBLOBs> getUserLiveByTeacherId(String userId) {
        return userliveScore.getUserLiveByTeacherId(userId);
    }

    @Override
    public List<UserToken> getUserLoginByTeacherID(String userId) {
        //return userLoginLogMapper.getUserLoginByTeacherID(userId);
        return userTokenDao.getUserLoginByTeacherID(userId);
    }

    @Override
    public int getUserLoginLogCountByTeacherID(String userId) {
        return userLoginLogMapper.getUserLoginLogCountByTeacherID(userId);
    }

    @Override
    public void userlivedataTorecord(int userId) {
        List<UserLiveDataWithBLOBs> userLiveDataList = userlivedata.getUserlivaByteacherid(userId);
        //先单条插入，以后改成按list批量的 crq
        if (userLiveDataList != null) {
            for (UserLiveDataWithBLOBs uld : userLiveDataList) {
                UserScoreRecord usr = new UserScoreRecord();
                usr.setUserId(uld.getUserId());
                usr.setTaskId(uld.getTaskId());
                usr.setOperateId(uld.getOperateId());
                usr.setScore(uld.getCurrentScore());
                usr.setTotalScore(uld.getTotalScore());
                usr.setBeginTime(uld.getStartTime());
                usr.setEndTime(uld.getStartTime() + uld.getStudyDuration().longValue());
                usr.setStudyType(uld.getStudyType());
                usr.setMacAddress(uld.getMacAddress());
                usr.setIpAddress(uld.getIdAddress());
                userScoreRecordMapper.insert(usr);
            }
            userlivedata.deletUserliveByteacherid(userId);
            userliveScore.deletUserliveByteacherid(userId);
        }
    }

    @Override
    public int deletelivedataTorecord(int userId) {
        int i=userlivedata.deletUserliveByteacherid(userId);
        int j=userliveScore.deletUserliveByteacherid(userId);
        return i+j;
    }

    @Override
    public double getTaskTotal_score(UserLiveWithBLOBs ul) {
        //如果uld正在做
        double totalscore = 0;
        if (ul.getScoreStatues() != 2) {
            double maxscore = FZhKTMapper.getOperteMaxScore(ul);
            if (maxscore == -1 || ul.getCurrentScore() > maxscore) {
                totalscore = FZhKTMapper.getTotalcoreWhithooutUserLive(ul) + ul.getCurrentScore();
            } else {
                totalscore = FZhKTMapper.getTotalcoreByUserLive(ul);
            }
        }
        //如果已经做完
        else {
            totalscore = FZhKTMapper.getTotalcoreByUserLive(ul);
        }
        return totalscore;
    }

    @Override
    public List<UserLive> checkIsRecordByTeacherId(String beginDate,String endDate,int userID) {
        return userliveScore.checkIsRecordByTeacherId(beginDate,endDate,userID);
    }

    @Override
    public List<ScoreDetailPOJO> getScoreDetailList(UserLiveWithBLOBs ulwb) {
        List<ScoreDetailPOJO> scoreDetailPOJOArrayList = new ArrayList<>();
        List<Operate> operateList = getOprateList();
        List<Questions> questionsList = getQuestionListAll();
        Map<Integer, Operate> operate_map = operateList.stream().collect(Collectors.toMap(Operate::getId, Function.identity(), (key1, key2) -> key2));
        Map<Integer, Questions> questionsMap = questionsList.stream().collect(Collectors.toMap(Questions::getId, Function.identity(), (key1, key2) -> key2));


        scoreDetailPOJOArrayList = FZhKTMapper.getDetailScoreList(ulwb);
        ScoreDetailPOJO scordetail = new ScoreDetailPOJO();
        scordetail.setOperateId(ulwb.getOperateId());
        scordetail.setLearnTime(String.valueOf(ulwb.getStudyDuration() / 1000));
        //DateFormat df=new SimpleDateFormat("yyyy-MM-dd 00:00:00");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String studydate=sdf.format(new Date(ulwb.getStartTime()));
        scordetail.setStudyDate(studydate);
        scordetail.setScore(ulwb.getCurrentScore().toString());

        scordetail.setScoreDetail(ulwb.getScoreDetail());
        if (ulwb.getStudyType() == StudyType.TASK.ordinal()) {
            for (ScoreDetailPOJO sd : scoreDetailPOJOArrayList) {
                int keyi = sd.getOperateId();
                Operate op = operate_map.get(sd.getOperateId());
                sd.setTaskName(op.getOperateName());
            }
            scordetail.setTaskName(operate_map.get(scordetail.getOperateId()).getOperateName());
        } else if (ulwb.getStudyType() == StudyType.EXAM.ordinal()) {
            for (ScoreDetailPOJO sd : scoreDetailPOJOArrayList) {
                sd.setTaskName(questionsMap.get(sd.getOperateId()).getQuestionName());
                //sd.setOperateName("空空空");
            }
            scordetail.setTaskName(questionsMap.get(scordetail.getOperateId()).getQuestionName());
            //scordetail.setOperateName("空空空");
        }
        if (ulwb.getScoreStatues() != Constants.ScoreStatues.END.ordinal()) {
            scoreDetailPOJOArrayList.add(scordetail);
        }
        return scoreDetailPOJOArrayList;
    }

    @Override
    public int updateUserScoreRecoredByTrainID(UserScoreRecord usrScore) {
        return userScoreRecord.updateUserScoreRecoredByTrainID(usrScore);
    }

    @Override
    public List<UserLiveDataWithBLOBs> getOperateMaxScore(int userId, int taskId, int studyType) {
        return userlivedata.getOperateMaxScore(userId,taskId,studyType);
    }

    @Override
    public ResponseEntity<byte[]> exportReatimeScore(List<RealTimeEcxelPOJO> scoreexcelList) {
        //1.创建一个excel文档
        HSSFWorkbook hssfWorkbook = new HSSFWorkbook();
        //2.创建文档摘要
        hssfWorkbook.createInformationProperties();
        //3.获取并配置文档摘要信息
        DocumentSummaryInformation docInfo = hssfWorkbook.getDocumentSummaryInformation();
        //文档类别
        docInfo.setCategory("信息");
        //文档管理员
        docInfo.setManager("esst");
        //文档所属公司
        docInfo.setCompany("esst");
        //文档版本
//        docInfo.setApplicationVersion(1);
        //4.获取文档摘要信息
        SummaryInformation summaryInformation = hssfWorkbook.getSummaryInformation();
        //文档标题
        summaryInformation.setAuthor("hopec");
        //文档创建时间
        summaryInformation.setCreateDateTime(new Date());
        //文档备注
        summaryInformation.setComments("文档备注");
        //5.创建样式
        //创建标题行的样式
        HSSFCellStyle headerStyle = hssfWorkbook.createCellStyle();
        //设置该样式的图案颜色为黄色
        headerStyle.setFillForegroundColor(IndexedColors.YELLOW.index);
        //设置图案填充的样式
//        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        //设置日期相关的样式
        HSSFCellStyle dateCellStyle = hssfWorkbook.createCellStyle();
        //这里的m/d/yy 相当于yyyy-MM-dd
        //dateCellStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("m/d/yy"));

        for (RealTimeEcxelPOJO rte : scoreexcelList) {
            HSSFSheet sheet = hssfWorkbook.createSheet(rte.getTaskName());
            HSSFRow r0 = sheet.createRow(0);
            HSSFRow r1 = sheet.createRow(1);
            HSSFCell c0 = r1.createCell(0);
            c0.setCellValue("学号");

            HSSFCell c1 = r1.createCell(1);
            c1.setCellValue("姓名");

            HSSFCell c2 = r1.createCell(2);
            c2.setCellValue("机器号");

            HSSFCell c3 = r1.createCell(3);
            c3.setCellValue("登录时间(年月日，时分秒)");

            HSSFCell c4 = r1.createCell(4);
            c4.setCellValue("学习时长(min)");
            if (rte.getStudyType() == StudyType.TASK) {
                HSSFCell c5 = r1.createCell(5);
                c5.setCellValue("任务单名称");
            } else {
                HSSFCell c5 = r1.createCell(5);
                c5.setCellValue("试卷名称");
            }
            if (rte.operateList!=null) {
                for (int i = 0; i < rte.operateList.size(); i++) {
                    HSSFCell c6 = r1.createCell(i + 6);
                    c6.setCellValue(rte.operateList.get(i));
                }
            }
            int rownum=2;
            if(rte.realTimeExcelItemPOJOHashMap!=null)
            {
                for (Map.Entry<Integer, RealTimeExcelItemPOJO> entry : rte.realTimeExcelItemPOJOHashMap.entrySet()) {
                    RealTimeExcelItemPOJO itemPOJO=entry.getValue();
                    HSSFRow r = sheet.createRow(rownum);
                    HSSFCell cell0=r.createCell(0);//学号
                    cell0.setCellValue(itemPOJO.getNum());
                    HSSFCell cell1=r.createCell(1);//姓名
                    cell1.setCellValue(itemPOJO.getStudentName());
                    HSSFCell cell2=r.createCell(2);//机器号
                    cell2.setCellValue(itemPOJO.getMachineNum());
                    HSSFCell cell3=r.createCell(3);//登录时间
                    cell3.setCellValue(itemPOJO.getLoginTime());

                    HSSFCell cell4=r.createCell(4);//学习时长
                    cell4.setCellValue(itemPOJO.getLearnTime());
                    HSSFCell cell5=r.createCell(5);//任务单
                    cell5.setCellValue(itemPOJO.getTaskName());

                    for (Map.Entry<Integer, Integer> entryoperate : rte.operateIndexList.entrySet()) {
                        HSSFCell cell6=r.createCell(entryoperate.getValue()+6);
                        if(itemPOJO.operateScoremap.containsKey(entryoperate.getKey())) {
                            cell6.setCellValue(itemPOJO.operateScoremap.get(entryoperate.getKey()));
                        }else {
                            cell6.setCellValue(0);
                        }

                    }
                rownum++;
                }
            }

        }
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        HttpHeaders headers = new HttpHeaders();
        try {
            //将数据表这几个中文的字转码 防止导出后乱码
            Date date = new Date();
            String fileName = DateUtils.parseDate(date) + ".xls";
            headers.setContentDispositionFormData("attachment",
                    new String(fileName.getBytes("UTF-8"), "ISO-8859-1"));
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            hssfWorkbook.write(stream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ResponseEntity<byte[]>(stream.toByteArray(),headers, HttpStatus.CREATED);
    }

    @Override
    public List<scoreModel> getscoreModelList(String user_id, String template_id) {
        return FZhKTMapper.getscoreModelList(user_id, template_id);
    }

}
