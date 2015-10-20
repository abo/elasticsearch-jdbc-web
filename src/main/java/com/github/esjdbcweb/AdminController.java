package com.github.esjdbcweb;

import com.google.common.base.Strings;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.xbib.tools.JDBCImporter;

import javax.validation.Valid;

/**
 * Created by abo on 10/15/15.
 */
@org.springframework.stereotype.Controller
public class AdminController {

    @Autowired
    TaskRepo repo;

    @Autowired
    QueueRunner runner;

    @RequestMapping(value={"/tasks","/",""}, method=RequestMethod.GET)
    public String tasks(@RequestParam(value = "page",required = false, defaultValue = "0") int page,
                        @RequestParam(value = "size", required = false, defaultValue = "10") int size, Model model){
        model.addAttribute("tasks", repo.findAll(new PageRequest(page, size)));
        return "index";
    }

    @RequestMapping(value="/tasks/new")
    public String form(Model model){
        return "new";
    }

    @RequestMapping(value="/tasks", method = RequestMethod.POST)
    public String create(@RequestParam("taskname") String taskName,
                         @RequestParam("mysqlhost")String mysqlHost,
                         @RequestParam("mysqlport") int mysqlPort,
                         @RequestParam("mysqlusername") String mysqlUsername,
                         @RequestParam(value = "mysqlpassword",required = false, defaultValue = "") String mysqlPassword,
                         @RequestParam("mysqltable") String mysqlTable,
                         @RequestParam("sqlwhere") String sqlwhere,
                         @RequestParam("eshost") String elasticsearchHost,
                         @RequestParam("esport") int elasticsearchPort,
                         @RequestParam("escluster") String elasticsearchCluster,
                         @RequestParam("esindex") String elasticsearchIndex){
        String setting = null;
        switch (mysqlTable){
            case "confonlineusers":
                setting = SettingBuilder.settingOfConfonlineusers(mysqlHost,mysqlPort,mysqlUsername,mysqlPassword,sqlwhere,elasticsearchHost,elasticsearchPort,elasticsearchCluster,elasticsearchIndex);
                break;
            case "sitevodusers":
                setting = SettingBuilder.settingOfSitevodusers(mysqlHost,mysqlPort,mysqlUsername,mysqlPassword,sqlwhere,elasticsearchHost,elasticsearchPort,elasticsearchCluster,elasticsearchIndex);
                break;
            case "tn_confstat":
                setting = SettingBuilder.settingOfTNConfstat(mysqlHost,mysqlPort,mysqlUsername,mysqlPassword,sqlwhere,elasticsearchHost,elasticsearchPort,elasticsearchCluster,elasticsearchIndex);
                break;
            case "tn_userstat":
                setting = SettingBuilder.settingOfTNUserstat(mysqlHost,mysqlPort,mysqlUsername,mysqlPassword,sqlwhere,elasticsearchHost,elasticsearchPort,elasticsearchCluster,elasticsearchIndex);
                break;
            case "siteliveusers":
                setting = SettingBuilder.settingOfSiteliveusers(mysqlHost,mysqlPort,mysqlUsername,mysqlPassword,sqlwhere,elasticsearchHost,elasticsearchPort,elasticsearchCluster,elasticsearchIndex);
                break;
            case "tn_confonlineusers":
                setting = SettingBuilder.settingOfTNConfonlineusers(mysqlHost,mysqlPort,mysqlUsername,mysqlPassword,sqlwhere,elasticsearchHost,elasticsearchPort,elasticsearchCluster,elasticsearchIndex);
                break;
            case "tn_siteliveusers":
                setting = SettingBuilder.settingOfTNSiteliveusers(mysqlHost,mysqlPort,mysqlUsername,mysqlPassword,sqlwhere,elasticsearchHost,elasticsearchPort,elasticsearchCluster,elasticsearchIndex);
                break;
            case "userstat":
                setting = SettingBuilder.settingOfUserstat(mysqlHost,mysqlPort,mysqlUsername,mysqlPassword,sqlwhere,elasticsearchHost,elasticsearchPort,elasticsearchCluster,elasticsearchIndex);
                break;
            default:
                setting = "";
        }

        if(!Strings.isNullOrEmpty(setting)){
            Task task = new Task();
            task.setName(taskName);
            task.setSetting(setting);
            task.setState(Task.STATE_WAITING);
            task.setCreatedAt(System.currentTimeMillis());
            repo.save(task);
            runner.submit(task);
        }

        return "redirect:/";
    }

}
