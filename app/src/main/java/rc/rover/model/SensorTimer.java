package rc.rover.model;

import android.os.Handler;
import android.util.Log;

import java.util.Timer;
import java.util.TimerTask;

public class SensorTimer {
    private static final String TAG = "TIMER";

    private Timer timer;
    private final Handler handler;

    private TimerTask timerTask;
    private final String mAction;
    private final Integer mInterval;
    private final Runnable task;

    private boolean isScheduled = false;

    public SensorTimer(String action, Integer interval, final Runnable task) {
        mAction = action;
        mInterval = interval;
        this.task = task;
        handler = new Handler();
        createTimerTask();
        start();
    }

    private void createTimerTask() {
        handler.removeCallbacks(task);
        timer = new Timer(mAction,true);
        timerTask = new TimerTask() {
            @Override
            public void run() {
                handler.post(task);
            }
        };
    }

    public synchronized void start() {
        if (timer == null) {
            createTimerTask();
        }
        if(!isScheduled) {
            Log.d(TAG, "Timer started. Action started: " + mAction);
            timer.schedule(timerTask, 0, mInterval);
        }
        isScheduled = true;
    }

    public synchronized void stop() {
        Log.d(TAG, "Timer stopped. Action disabled: " + mAction);
        if (timer != null) {
            timer.cancel();
        }
        timer = null;
        isScheduled = false;
    }
}
