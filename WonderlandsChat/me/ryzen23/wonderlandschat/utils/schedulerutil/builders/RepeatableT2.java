package me.ryzen23.wonderlandschat.utils.schedulerutil.builders;

import me.ryzen23.wonderlandschat.utils.schedulerutil.builders.RepeatableBuilder;
import me.ryzen23.wonderlandschat.utils.schedulerutil.data.ScheduleBuilderBase;
import me.ryzen23.wonderlandschat.utils.schedulerutil.data.ScheduleData;

public class RepeatableT2
implements ScheduleBuilderBase {
    private final ScheduleData data;

    RepeatableT2(ScheduleData data) {
        this.data = data;
    }

    public RepeatableBuilder run(Runnable runnable) {
        this.data.setRunnable(runnable);
        return new RepeatableBuilder(this.data);
    }

    @Override
    public ScheduleData getData() {
        return this.data;
    }
}
