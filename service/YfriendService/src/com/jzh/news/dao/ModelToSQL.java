package com.jzh.news.dao; 
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Logger;

import com.jzh.news.util.jdkLog;

public class ModelToSQL {
    public static final int INSERTSQL = 1;
    public static final int UPDATESQL = 2;
 
    /**
     * ��ȡInsertSQL
     * 
     * @param model
     * @return
     */
    public static String getInsertSQL(Object model) {
        if (model == null) {
    		jdkLog.log.info("�������Ϊnull����..."); 
            throw new NullPointerException();
        }
        return getInsertOrUpdateSQL(INSERTSQL, model);
    }

    /**
     * ��ȡUpdateSQL
     * 
     * @param model
     * @return
     */
    public static String getUpdateSQL(Object model) {
        if (model == null) {
            jdkLog.log.info("�������Ϊnull����");
            throw new NullPointerException();
        }
        return getInsertOrUpdateSQL(UPDATESQL, model);
    }

    /**
     * ѡ��Insert����Update���,�����UPDATE�����ǿɴ��εĸ���ID����
     * 
     * @param type
     * @param model
     * @return
     */
    private static String getInsertOrUpdateSQL(int type, Object model) {
        String sql = null;
        StringBuilder sb1 = new StringBuilder();
        StringBuilder sb2 = new StringBuilder();
        Class<?> cl = model.getClass();
        Field[] fields = cl.getDeclaredFields();
        String table = cl.getSimpleName();
        try {
            for (Field field : fields) {
                field.setAccessible(true);
                //       INSERTSQL = 1;
                //	     UPDATESQL = 2;
                if (type == 1) {
                	// Ϊ���򲻲���
                	if( field.get(model) != null) {
	                    sb1.append(",");
	                    sb1.append(field.getName());
	                    if (field.getType().equals(String.class)) {
	                        String value = (String) field.get(model);
	                        sb2.append(",");
	                        sb2.append("'");
	                        sb2.append(value);
	                        sb2.append("'");
	                    } else if (field.getType().equals(int.class)) {
	                        int value = field.getInt(model);
	                        sb2.append(",");
	                        sb2.append(value);
	                    } else if(field.getType().equals(Date.class)) {
	                    	SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
	                    	String value =df2.format(field.get(model));
	                    	  sb2.append(",");
		                      sb2.append("'");
		                      sb2.append(value);
		                      sb2.append("'");
	                    }else if(field.getType().equals(Boolean.class)){
	                    	  Boolean value = (Boolean) field.get(model);
		                        sb2.append(",");
		                        sb2.append(value);
	                    }
                	}
                } else if (type == 2) {
                	// Ϊ���򲻲���
                	if( field.get(model) != null) {
                    if (field.getType().equals(String.class)) {
                        String value = (String) field.get(model);
                        sb1.append(",");
                        sb1.append(field.getName());
                        sb1.append("='");
                        sb1.append(value);
                        sb1.append("'");
                    } else if (field.getType().equals(int.class)) {
                        int value = field.getInt(model);
                        sb1.append(",");
                        sb1.append(field.getName());
                        sb1.append("=");
                        sb1.append(value);
                    }else if(field.getType().equals(Date.class)) {
                    	SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
                    	String value =df2.format(field.get(model));
                    	  sb2.append(",");
	                      sb2.append("'");
	                      sb2.append(value);
	                      sb2.append("'");
                    }else if(field.getType().equals(Boolean.class)){
                  	  Boolean value = (Boolean) field.get(model);
                      sb2.append(",");
                      sb2.append(value);
                    }
                  }
                }
            }
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        if (type == 1) {
            sql = String.format("insert into %s (%s) values(%s)", table,
                    sb1.substring(1), sb2.substring(1));
        } else if (type == 2) {
            sql = String.format("update %s set %s where %s=?", table,
                    sb1.substring(1), fields[0].getName());
        }
        jdkLog.log.info("ִ����䣺" + sql);
        System.out.println("sql =" + sql);
        return sql;
    }

    /**
     * ��ȡDeleteSQL,��Ҫ��IDֵ
     * 
     * @param model
     * @return
     */
    public static String getDeleteSQL(Object model) {
        String sql = null;
        Class<?> cl = model.getClass();
        Field[] fields = cl.getDeclaredFields();
        String table = cl.getSimpleName();
        sql = String.format("delete from %s where %s=?", table,
                fields[0].getName());
        jdkLog.log.info("ִ����䣺" + sql);
        return sql;
    }

    /**
     * ��ȡDeleteSQL
     * 
     * @param table
     * @param primkey
     * @return
     */
    public static String getDeleteSQL(String table, String primkey) {
        String sql = String.format("delete from %s where %s=?", table, primkey);
        jdkLog.log.info("ִ����䣺" + sql);
        return sql;
    }

    /**
     * ����ID��ѯ
     * 
     * @param table
     * @param primkey
     * @param id
     * @return
     */
    public static String getQueryByIdSQL(String table, String primkey) {
        String sql = null;
        sql = String.format("select * from %s where %s = ?", table, primkey);
        jdkLog.log.info("ִ����䣺" + sql);
        return sql;
    }

    /**
     * ��ȡ��ѯ���м�¼��SQL
     * 
     * @param model
     * @param OrderBy
     * @return
     */
    public static String getQueryAllSQL(Object model, String orderBy) {
        if (model == null) {
            jdkLog.log.info("�������Ϊnull����");
            throw new NullPointerException();
        }
        String sql = null;
        Class<?> cl = model.getClass();
        String table = cl.getSimpleName();
        sql = String.format("select * from %s order by %s", table, orderBy);
        jdkLog.log.info("ִ����䣺" + sql);
        return sql;
    }

    /**
     * ��ҳ��������ѯ
     * 
     * @param model
     * @param selections
     *            Ϊnull��Ĭ��Ϊȫ��
     * @param where
     *            Ϊnull��Ϊ��������ҳ��ѯ
     * @param limit
     * @param page
     * @param orderBy
     *            nullΪ������
     * @return
     */
    public static String getQuery(Object model, String selections,
            String where, int limit, int page, String orderBy) {
        if (model == null) {
            jdkLog.log.info("�������Ϊnull����");
            throw new NullPointerException();
        }
        String sql = null;
        Class<?> cl = model.getClass();
        String table = cl.getSimpleName();
        int start = limit * page;
        if (selections == null) {
            selections = "*";
        }
        if (where == null) {
            if (orderBy == null) {
                sql = String.format("select %s from %s limit %s,%s",
                        selections, table, start, limit);
            } else {
                sql = String.format(
                        "select %s from %s order by %s limit %s,%s",
                        selections, table, orderBy, start, limit);
            }

        } else {
            if (orderBy == null) {
                sql = String
                        .format("select *  from (select %s from %s where %s) %s limit %s,%s",
                                selections, table, where, table, start, limit);
            } else {
                sql = String
                        .format("select *  from (select %s from %s where %s) %s order by %s limit %s,%s",
                                selections, table, where, table, start, limit);
            }
        }
        jdkLog.log.info("ִ�����:" + sql);
        return sql;
    }

}