package com.github.esjdbcweb;

import com.google.common.util.concurrent.*;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.*;
import org.xbib.tools.JDBCImporter;

import java.util.concurrent.Callable;
import java.util.concurrent.Executors;

/**
 * Created by abo on 10/16/15.
 */
@org.springframework.stereotype.Service
public class QueueRunner {

    @Autowired
    TaskRepo repo;

    ListeningExecutorService service = MoreExecutors.listeningDecorator(Executors.newFixedThreadPool(1));

    public void submit(Task task){
        ListenableFuture<Void> future = service.submit(new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                task.setState(Task.STATE_EXECUTING);
                repo.save(task);
                new JDBCImporter().setSettings(ImmutableSettings.settingsBuilder().loadFromSource(task.getSetting()).build()).run(true);
                return null;
            }
        });

        Futures.addCallback(future, new FutureCallback<Void>() {
            @Override
            public void onSuccess(Void result) {
                task.setState(Task.STATE_FINISHED);
                repo.save(task);
            }

            @Override
            public void onFailure(Throwable t) {
                task.setState(Task.STATE_FAILED);
                repo.save(task);
            }
        });
    }
}
