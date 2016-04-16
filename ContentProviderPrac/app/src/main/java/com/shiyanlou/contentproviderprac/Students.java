package com.shiyanlou.contentproviderprac;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by AQ-DN-01-076B on 2016/3/12.
 */
public class Students {

    //定义内容提供者的Authority信息
    public static final String AUTHORITY = "com.shiyanlou.provider.Students";

    //定义一个静态内部类，用于存放该内容提供者都包含了哪些数据列
    public static final class Student implements BaseColumns {
        //定义了能被操作的三个数据列的名称

        public final static String _ID = "_id";
        public final static String STUDENT = "student";
        public final static String INFORMATION = "info";

        //定义了该内容提供者面向外界提供服务的两个URI地址
        public final static Uri STUDENTS_CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/students");

        public  final  static  Uri STUDENT_CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/student");
    }
}
