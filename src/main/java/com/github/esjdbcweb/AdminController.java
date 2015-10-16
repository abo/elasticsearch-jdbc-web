package com.github.esjdbcweb;

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
        model.addAttribute("task", new Task());
        return "new";
    }

    @RequestMapping(value="/tasks", method = RequestMethod.POST)
    public String create(@Valid Task task, BindingResult result){
        task.setState(Task.STATE_WAITING);
        task.setCreatedAt(System.currentTimeMillis());
        repo.save(task);
        runner.submit(task);
        return "redirect:/";
    }

}
