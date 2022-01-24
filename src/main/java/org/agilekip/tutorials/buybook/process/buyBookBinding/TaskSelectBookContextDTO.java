package org.agilekip.tutorials.buybook.process.buyBookBinding;

import org.agilekip.tutorials.buybook.service.dto.BuyBookBindingDTO;
import org.akip.service.dto.TaskInstanceDTO;

public class TaskSelectBookContextDTO {

    private BuyBookBindingDTO buyBookBinding;
    private TaskInstanceDTO taskInstance;

    public BuyBookBindingDTO getBuyBookBinding() {
        return buyBookBinding;
    }

    public void setBuyBookBinding(BuyBookBindingDTO buyBookBinding) {
        this.buyBookBinding = buyBookBinding;
    }

    public TaskInstanceDTO getTaskInstance() {
        return taskInstance;
    }

    public void setTaskInstance(TaskInstanceDTO taskInstance) {
        this.taskInstance = taskInstance;
    }
}
