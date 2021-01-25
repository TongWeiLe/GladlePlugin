package com.test.gladleplugin;

import android.app.Application;

import com.test.gladleplugin.alpha.AlphaManager;
import com.test.gladleplugin.alpha.ITaskCreator;
import com.test.gladleplugin.alpha.Project;
import com.test.gladleplugin.alpha.Task;
import com.test.gladleplugin.inittask.FirstTask;
import com.test.gladleplugin.inittask.FourthTask;
import com.test.gladleplugin.inittask.SecondTask;
import com.test.gladleplugin.inittask.ThirdTask;

public class GradleApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        FirstTask firstTask = new FirstTask("first", this);
        firstTask.setIsInUiThread(true);
        SecondTask secondTask = new SecondTask("second");

        ThirdTask thirdTask = new ThirdTask("third");

        FourthTask fourthTask = new FourthTask("four", this);
        fourthTask.setIsInUiThread(true);
        firstTask.setExecutePriority(1);
        secondTask.setExecutePriority(2);
        thirdTask.setExecutePriority(3);
        fourthTask.setExecutePriority(4);

        Project.Builder builder = new Project.Builder();

        builder.add(firstTask);
        builder.add(secondTask).after(firstTask);
        builder.add(thirdTask).after(firstTask);
        builder.add(fourthTask).after(secondTask, thirdTask);

        AlphaManager.getInstance(this).addProject(builder.create());
        AlphaManager.getInstance(this).start();
    }

    class TaskCreator implements ITaskCreator {


        @Override
        public Task createTask(String taskName) {
            return null;
        }
    }
}
