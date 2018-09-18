package com.jzh.news.dao; 
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Logger;

public class ModelToSQL {
    public static final int INSERTSQL = 1;
    public static final int UPDATESQL = 2;
    private static Logger log = Logger.getLogger(ModelToSQL.class.getName());

    /**
     * 获取InsertSQL
     * 
     * @param model
     * @return
     */
    public static String getInsertSQL(Object model) {
        if (model == null) {
            log.info("传入参数为null！！");
            throw new NullPointerException();
        }
        return getInsertOrUpdateSQL(INSERTSQL, model);
    }

    /**
     * 获取UpdateSQL
     * 
     * @param model
     * @return
     */
    public static String getUpdateSQL(Object model) {
        if (model == null) {
            log.info("传入参数为null！！");
            throw new NullPointerException();
        }
        return getInsertOrUpdateSQL(UPDATESQL, model);
    }

    /**
     * 选择Insert或者Update语句,如果是UPDATE，则是可传参的根据ID更新
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
                	// 为空则不插入
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
                	// 为空则不插入
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
        log.info("执行语句：" + sql);
        System.out.println("sql =" + sql);
        return sql;
    }

    /**
     * 获取DeleteSQL,需要传ID值
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
        log.info("执行语句：" + sql);
        return sql;
    }

    /**
     * 获取DeleteSQL
     * 
     * @param table
     * @param primkey
     * @return
     */
    public static String getDeleteSQL(String table, String primkey) {
        String sql = String.format("delete from %s where %s=?", table, primkey);
        log.info("执行语句：" + sql);
        return sql;
    }

    /**
     * 根据ID查询
     * 
     * @param table
     * @param primkey
     * @param id
     * @return
     */
    public static String getQueryByIdSQL(String table, String primkey) {
        String sql = null;
        sql = String.format("select * from %s where %s = ?", table, primkey);
        log.info("执行语句：" + sql);
        return sql;
    }

    /**
     * 获取查询所有记录的SQL
     * 
     * @param model
     * @param OrderBy
     * @return
     */
    public static String getQueryAllSQL(Object model, String orderBy) {
        if (model == null) {
            log.info("传入参数为null！！");
            throw new NullPointerException();
        }
        String sql = null;
        Class<?> cl = model.getClass();
        String table = cl.getSimpleName();
        sql = String.format("select * from %s order by %s", table, orderBy);
        log.info("执行语句：" + sql);
        return sql;
    }

    /**
     * 分页按条件查询
     * 
     * @param model
     * @param selections
     *            为null是默认为全部
     * @param where
     *            为null是为无条件分页查询
     * @param limit
     * @param page
     * @param orderBy
     *            null为不排序
     * @return
     */
    public static String getQuery(Object model, String selections,
            String where, int limit, int page, String orderBy) {
        if (model == null) {
            log.info("传入参数为null！！");
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
        log.info("执行语句:" + sql);
        return sql;
    }

}