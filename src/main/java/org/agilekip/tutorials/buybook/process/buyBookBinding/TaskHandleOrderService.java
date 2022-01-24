package org.agilekip.tutorials.buybook.process.buyBookBinding;

import org.agilekip.tutorials.buybook.repository.BuyBookBindingRepository;
import org.agilekip.tutorials.buybook.service.BuyBookService;
import org.agilekip.tutorials.buybook.service.dto.BuyBookBindingDTO;
import org.agilekip.tutorials.buybook.service.dto.BuyBookDTO;
import org.agilekip.tutorials.buybook.service.mapper.BuyBookBindingMapper;
import org.akip.repository.TaskInstanceRepository;
import org.akip.service.TaskInstanceService;
import org.akip.service.dto.TaskInstanceDTO;
import org.akip.service.mapper.TaskInstanceMapper;
import org.springframework.stereotype.Component;

@Component
public class TaskHandleOrderService {

    private final TaskInstanceService taskInstanceService;

    private final BuyBookService buyBookService;

    private final TaskInstanceRepository taskInstanceRepository;

    private final BuyBookBindingRepository buyBookBindingRepository;

    private final TaskInstanceMapper taskInstanceMapper;

    private final TaskHandleOrderMapper taskHandleOrderMapper;

    private final BuyBookBindingMapper buyBookBindingMapper;

    public TaskHandleOrderService(
        TaskInstanceService taskInstanceService,
        BuyBookService buyBookService,
        TaskInstanceRepository taskInstanceRepository,
        BuyBookBindingRepository buyBookBindingRepository,
        TaskInstanceMapper taskInstanceMapper,
        TaskHandleOrderMapper taskHandleOrderMapper,
        BuyBookBindingMapper buyBookBindingMapper
    ) {
        this.taskInstanceService = taskInstanceService;
        this.buyBookService = buyBookService;
        this.taskInstanceRepository = taskInstanceRepository;
        this.buyBookBindingRepository = buyBookBindingRepository;
        this.taskInstanceMapper = taskInstanceMapper;
        this.taskHandleOrderMapper = taskHandleOrderMapper;
        this.buyBookBindingMapper = buyBookBindingMapper;
    }

    public TaskHandleOrderContextDTO loadContext(Long taskInstanceId) {
        TaskInstanceDTO taskInstanceDTO = taskInstanceRepository
            .findById(taskInstanceId)
            .map(taskInstanceMapper::toDTOLoadTaskContext)
            .orElseThrow();

        BuyBookBindingDTO buyBookBinding = buyBookBindingRepository
            .findByProcessInstanceId(taskInstanceDTO.getProcessInstance().getId())
            .map(taskHandleOrderMapper::toBuyBookBindingDTO)
            .orElseThrow();

        TaskHandleOrderContextDTO taskHandleOrderContext = new TaskHandleOrderContextDTO();
        taskHandleOrderContext.setTaskInstance(taskInstanceDTO);
        taskHandleOrderContext.setBuyBookBinding(buyBookBinding);

        return taskHandleOrderContext;
    }

    public TaskHandleOrderContextDTO claim(Long taskInstanceId) {
        taskInstanceService.claim(taskInstanceId);
        return loadContext(taskInstanceId);
    }

    public void save(TaskHandleOrderContextDTO taskHandleOrderContext) {
        BuyBookDTO buyBookDTO = buyBookService.findOne(taskHandleOrderContext.getBuyBookBinding().getBuyBook().getId()).orElseThrow();
        buyBookDTO.setPurpose(taskHandleOrderContext.getBuyBookBinding().getBuyBook().getPurpose());
        buyBookDTO.setPurchaseReview(taskHandleOrderContext.getBuyBookBinding().getBuyBook().getPurchaseReview());
        buyBookService.save(buyBookDTO);
    }

    public void complete(TaskHandleOrderContextDTO taskHandleOrderContext) {
        save(taskHandleOrderContext);
        BuyBookBindingDTO buyBookBinding = buyBookBindingRepository
            .findByProcessInstanceId(taskHandleOrderContext.getBuyBookBinding().getProcessInstance().getId())
            .map(buyBookBindingMapper::toDto)
            .orElseThrow();
        taskInstanceService.complete(taskHandleOrderContext.getTaskInstance(), buyBookBinding);
    }
}
