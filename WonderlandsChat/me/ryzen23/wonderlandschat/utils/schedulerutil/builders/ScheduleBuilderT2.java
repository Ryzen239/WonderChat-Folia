package me.ryzen23.wonderlandschat.utils.schedulerutil.builders;

import me.ryzen23.wonderlandschat.utils.schedulerutil.data.ScheduleBuilderBase;
import me.ryzen23.wonderlandschat.utils.schedulerutil.data.ScheduleData;
import me.ryzen23.wonderlandschat.utils.schedulerutil.data.ScheduleThread;

public class ScheduleBuilderT2
implements ScheduleBuilderBase {
    private final ScheduleData data;

    ScheduleBuilderT2(ScheduleData data) {
        this.data = data;
    }

    public ScheduleThread run(Runnable runnable) {
        this.data.setRunnable(runnable);
        return new ScheduleThread(this.data);
    }

    @Override
    public ScheduleData getData() {
        return this.data;
    }
}
