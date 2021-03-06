package com.esst.ts.constants;

import com.esst.ts.model.UserScoreRecord;
import com.esst.ts.model.scoreModel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

@Component
public class Constants {

    public static String CUSTOMER;

    @Value("${customer}")
    public void setCUSTOMER(String CUSTOMER) {
        Constants.CUSTOMER = CUSTOMER;
    }

    //超时时间
    public static final Integer WEB_OUT_TIME = 30000000; // token超时时间

    public static final Integer APP_OUT_TIME = 30000000; // token超时时间

    //系统配置关键字
    public static final String UPLOAD_PIC_URL = "/upload";



    public static AtomicBoolean watchDogLoginFlag =new AtomicBoolean();
    //学习类型Task任务单，Exam试卷
    public enum StudyType {TASK,EXAM };
    //上传成绩状态 start第一次，middle中间上传，end结束
    public enum ScoreStatues{START,MIDDLE,END,};
        //测试服务器
    //public static final String SERVER_URL = "http://";

    public static String getIpAddress(HttpServletRequest request) {
        String serverName = request.getServerName();
        int serverPort = request.getServerPort();
        String url = "http://" + serverName // 服务器地址
                + ":" + serverPort;// 端口号
        String suffix = request.getRequestURI();
        return url;
    }
    public static Map<String, scoreModel> scoredataDic=new HashMap<String, scoreModel>();
    public static HashMap<Integer, UserScoreRecord> userscorerecord_map=new HashMap<Integer, UserScoreRecord>();
}
