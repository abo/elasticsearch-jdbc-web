package com.github.esjdbcweb;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Map;
import java.util.Objects;

/**
 * Created by abo on 10/20/15.
 */
public class SettingBuilder {

    public static String settingOfConfonlineusers(String mysqlHost, int mysqlPort, String username, String password, String sqlWhere, String esHost, int esPort, String esCluster, String esIndex){
        Map<String, Object> setting = ImmutableMap.<String,Object>builder()
                .put("type", "jdbc")
                .put("jdbc", ImmutableMap.builder()
                                .put("url", String.format("jdbc:mysql://%s:%d/webcastingraw", mysqlHost, mysqlPort))
                                .put("user", username)
                                .put("password", password)
                                .put("sql", ImmutableList.of(ImmutableMap.of("statement", "select *,concat(conf_id,unix_timestamp(timestamp)) as _id, date_format(convert_tz(timestamp,'+00:00','+08:00'), '%Y-%m-%dT%T.000+08:00') as timestamp_local from confonlineusers where "+sqlWhere)))
                                .put("timezone", "GMT")
                                .put("resultset_concurrency", "CONCUR_READ_ONLY")
                                .put("elasticsearch", ImmutableMap.of("cluster",esCluster,"host",esHost,"port",esPort))
                                .put("index", esIndex)
                                .put("type", "confonlineusers")
                                .build()
                ).build();
        return new GsonBuilder().disableHtmlEscaping().create().toJson(setting);
    }

   public static String settingOfSitevodusers(String mysqlHost, int mysqlPort, String username, String password, String sqlWhere, String esHost, int esPort, String esCluster, String esIndex){
       Map<String, Object> setting = ImmutableMap.<String,Object>builder()
               .put("type", "jdbc")
               .put("jdbc", ImmutableMap.builder()
                               .put("url", String.format("jdbc:mysql://%s:%d/webcastingraw",mysqlHost, mysqlPort))
                               .put("user", username)
                               .put("password", password)
                               .put("sql", ImmutableList.of(ImmutableMap.of("statement","select *,concat(site_id,unix_timestamp(timestamp)) as _id, date_format(convert_tz(timestamp,'+00:00','+08:00'), '%Y-%m-%dT%T.000+08:00') as timestamp_local from sitevodusers where "+sqlWhere)))
                               .put("timezone", "GMT")
                               .put("resultset_concurrency","CONCUR_READ_ONLY")
                               .put("elasticsearch", ImmutableMap.of("cluster",esCluster,"host",esHost,"port",esPort))
                               .put("index", esIndex)
                               .put("type", "sitevodusers")
                               .build()
               ).build();
       return new GsonBuilder().disableHtmlEscaping().create().toJson(setting);
   }

    public static String settingOfTNConfstat(String mysqlHost, int mysqlPort, String username, String password, String sqlWhere, String esHost, int esPort, String esCluster, String esIndex){
        Map<String, Object> setting = ImmutableMap.<String,Object>builder()
                .put("type", "jdbc")
                .put("jdbc", ImmutableMap.builder()
                                .put("url", String.format("jdbc:mysql://%s:%d/webcastingraw",mysqlHost, mysqlPort))
                                .put("user", username)
                                .put("password", password)
                                .put("sql", ImmutableList.of(ImmutableMap.of("statement","select *,concat(conf_id,unix_timestamp(start_time)) as _id, date_format(convert_tz(start_time,'+00:00','+08:00'), '%Y-%m-%dT%T.000+08:00') as start_time_local from tn_confstat where "+sqlWhere)))
                                .put("timezone", "GMT")
                                .put("resultset_concurrency","CONCUR_READ_ONLY")
                                .put("elasticsearch", ImmutableMap.of("cluster",esCluster,"host",esHost,"port",esPort))
                                .put("index", esIndex)
                                .put("type", "tn_confstat")
                                .build()
                ).build();
        return new GsonBuilder().disableHtmlEscaping().create().toJson(setting);
    }

    public static String settingOfTNUserstat(String mysqlHost, int mysqlPort, String username, String password, String sqlWhere, String esHost, int esPort, String esCluster, String esIndex){
        Map<String, Object> setting = ImmutableMap.<String,Object>builder()
                .put("type", "jdbc")
                .put("jdbc", ImmutableMap.builder()
                                .put("url", String.format("jdbc:mysql://%s:%d/webcastingraw",mysqlHost, mysqlPort))
                                .put("user", username)
                                .put("password", password)
                                .put("sql", ImmutableList.of(ImmutableMap.of("statement", "select *,substring(area FROM 1 FOR 2) as location,concat(conf_id,'.',user_id,'.',unix_timestamp(join_time)) as _id, date_format(convert_tz(join_time,'+00:00','+08:00'), '%Y-%m-%dT%T.000+08:00') as join_time_local from tn_userstat where "+sqlWhere)))
                                .put("timezone", "GMT")
                                .put("row_extend", ImmutableMap.builder()
                                        .put("type", "siteid_extender")
                                        .put("url", String.format("jdbc:mysql://%s:%d/webcast", mysqlHost, mysqlPort))
                                        .put("username", username)
                                        .put("password", password)
                                        .put("sql", "select site_id from gs_tra_room where id=?")
                                        .put("parameter", ImmutableList.of("conf_id"))
                                        .build())
                                .put("resultset_concurrency", "CONCUR_READ_ONLY")
                                .put("elasticsearch", ImmutableMap.of("cluster",esCluster,"host",esHost,"port",esPort))
                                .put("index", esIndex)
                                .put("type", "tn_userstat")
                                .build()
                ).build();
        return new GsonBuilder().disableHtmlEscaping().create().toJson(setting);
    }

    public static String settingOfSiteliveusers(String mysqlHost, int mysqlPort, String username, String password, String sqlWhere, String esHost, int esPort, String esCluster, String esIndex){
        Map<String, Object> setting = ImmutableMap.<String,Object>builder()
                .put("type", "jdbc")
                .put("jdbc", ImmutableMap.builder()
                                .put("url", String.format("jdbc:mysql://%s:%d/webcastingraw",mysqlHost, mysqlPort))
                                .put("user", username)
                                .put("password", password)
                                .put("sql", ImmutableList.of(ImmutableMap.of("statement","select *,concat(site_id,unix_timestamp(timestamp)) as _id, date_format(convert_tz(timestamp,'+00:00','+08:00'), '%Y-%m-%dT%T.000+08:00') as timestamp_local from siteliveusers where "+sqlWhere)))
                                .put("timezone", "GMT")
                                .put("resultset_concurrency","CONCUR_READ_ONLY")
                                .put("elasticsearch", ImmutableMap.of("cluster",esCluster,"host",esHost,"port",esPort))
                                .put("index", esIndex)
                                .put("type", "siteliveusers")
                                .build()
                ).build();
        return new GsonBuilder().disableHtmlEscaping().create().toJson(setting);
    }

    public static String settingOfTNConfonlineusers(String mysqlHost, int mysqlPort, String username, String password, String sqlWhere, String esHost, int esPort, String esCluster, String esIndex){
        Map<String, Object> setting = ImmutableMap.<String,Object>builder()
                .put("type", "jdbc")
                .put("jdbc", ImmutableMap.builder()
                                .put("url", String.format("jdbc:mysql://%s:%d/webcastingraw",mysqlHost, mysqlPort))
                                .put("user", username)
                                .put("password", password)
                                .put("sql", ImmutableList.of(ImmutableMap.of("statement","select *,concat(conf_id,unix_timestamp(timestamp)) as _id, date_format(convert_tz(timestamp,'+00:00','+08:00'), '%Y-%m-%dT%T.000+08:00') as timestamp_local from tn_confonlineusers where "+sqlWhere)))
                                .put("timezone", "GMT")
                                .put("resultset_concurrency","CONCUR_READ_ONLY")
                                .put("elasticsearch", ImmutableMap.of("cluster",esCluster,"host",esHost,"port",esPort))
                                .put("index", esIndex)
                                .put("type", "tn_confonlineusers")
                                .build()
                ).build();
        return new GsonBuilder().disableHtmlEscaping().create().toJson(setting);
    }

    public static String settingOfTNSiteliveusers(String mysqlHost, int mysqlPort, String username, String password, String sqlWhere, String esHost, int esPort, String esCluster, String esIndex){
        Map<String, Object> setting = ImmutableMap.<String,Object>builder()
                .put("type", "jdbc")
                .put("jdbc", ImmutableMap.builder()
                                .put("url", String.format("jdbc:mysql://%s:%d/webcastingraw",mysqlHost, mysqlPort))
                                .put("user", username)
                                .put("password", password)
                                .put("sql", ImmutableList.of(ImmutableMap.of("statement","select *,concat(site_id,unix_timestamp(timestamp)) as _id, date_format(convert_tz(timestamp,'+00:00','+08:00'), '%Y-%m-%dT%T.000+08:00') as timestamp_local from tn_siteliveusers where "+sqlWhere)))
                                .put("timezone", "GMT")
                                .put("resultset_concurrency","CONCUR_READ_ONLY")
                                .put("elasticsearch", ImmutableMap.of("cluster",esCluster,"host",esHost,"port",esPort))
                                .put("index", esIndex)
                                .put("type", "tn_siteliveusers")
                                .build()
                ).build();
        return new GsonBuilder().disableHtmlEscaping().create().toJson(setting);
    }

    public static String settingOfUserstat(String mysqlHost, int mysqlPort, String username, String password, String sqlWhere, String esHost, int esPort, String esCluster, String esIndex){
        Map<String, Object> setting = ImmutableMap.<String,Object>builder()
                .put("type", "jdbc")
                .put("jdbc", ImmutableMap.builder()
                                .put("url", String.format("jdbc:mysql://%s:%d/webcastingraw",mysqlHost, mysqlPort))
                                .put("user", username)
                                .put("password", password)
                                .put("sql", ImmutableList.of(ImmutableMap.of("statement", "select *,substring(area FROM 1 FOR 2) as location,concat(conf_id,'.',user_id,'.',unix_timestamp(join_time)) as _id, date_format(convert_tz(join_time,'+00:00','+08:00'), '%Y-%m-%dT%T.000+08:00') as join_time_local from userstat where "+sqlWhere)))
                                .put("timezone", "GMT")
                                .put("row_extend", ImmutableMap.builder()
                                        .put("type", "siteid_extender")
                                        .put("url", String.format("jdbc:mysql://%s:%d/webcast", mysqlHost, mysqlPort))
                                        .put("username", username)
                                        .put("password", password)
                                        .put("sql", "select site_id from gs_webcast_published where id=?")
                                        .put("parameter", ImmutableList.of("conf_id"))
                                        .build())
                                .put("resultset_concurrency", "CONCUR_READ_ONLY")
                                .put("elasticsearch", ImmutableMap.of("cluster",esCluster,"host",esHost,"port",esPort))
                                .put("index", esIndex)
                                .put("type", "userstat")
                                .build()
                ).build();
        return new GsonBuilder().disableHtmlEscaping().create().toJson(setting);
    }

}
