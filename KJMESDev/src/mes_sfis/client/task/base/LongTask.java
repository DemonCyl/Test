package mes_sfis.client.task.base;

import java.util.List;

/**
 * Created by Chris1_Liao on 2018/6/1.
 */
public abstract class LongTask {
    protected int lengthOfTask;
    protected int current = 0;
    protected boolean done = false;
    protected boolean canceled = false;
    protected String statMessage;

    abstract public void setOp(String op);
    abstract public void setDevice(String device);
    abstract public void setIsnList(List isnList);

    public LongTask(int lengthOfTask) {
        //Compute length of task...
        //In a real program, this would figure out
        //the number of bytes to read or whatever.
        this.lengthOfTask = lengthOfTask;
    }

    /**
     * Called from ProgressBarDemo to start the task.
     */
    public void go() {
        final KjSwingWorker worker = new KjSwingWorker() {
            public Object construct() {
                current = 0;
                done = false;
                canceled = false;
                statMessage = null;
                return new ActualTask();
            }
        };
        worker.start();
    }

    /**
     * Called from ProgressBarDemo to find out how much work needs
     * to be done.
     */
    public int getLengthOfTask() {
        return lengthOfTask;
    }

    /**
     * Called from ProgressBarDemo to find out how much has been done.
     */
    public int getCurrent() {
        return current;
    }

    public void stop() {
        canceled = true;
        statMessage = null;
    }

    /**
     * Called from ProgressBarDemo to find out if the task has completed.
     */
    public boolean isDone() {
        return done;
    }

    /**
     * Returns the most recent status message, or null
     * if there is no current status message.
     */
    public String getMessage() {
        return statMessage;
    }



    /**
     * The actual long running task.  This runs in a SwingWorker thread.
     */
    class ActualTask {
        ActualTask() {
            //Fake a long task,
            //making a random amount of progress every second.
            while (!canceled && !done) {
                try {
                    Thread.sleep(1000); //sleep for a second
                    current += Math.random() * 100; //make some progress
                    if (current >= lengthOfTask) {
                        done = true;
                        current = lengthOfTask;
                    }
                    statMessage = "Completed " + current +
                            " out of " + lengthOfTask + ".";
                } catch (InterruptedException e) {
                    System.out.println("ActualTask interrupted");
                }
            }
        }
    }
}