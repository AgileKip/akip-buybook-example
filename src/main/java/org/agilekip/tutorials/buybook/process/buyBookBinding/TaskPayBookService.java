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
public class TaskPayBookService {

    private final TaskInstanceService taskInstanceService;

    private final BuyBookService buyBookService;

    private final TaskInstanceRepository taskInstanceRepository;

    private final BuyBookBindingRepository buyBookBindingRepository;

    private final TaskInstanceMapper taskInstanceMapper;

    private final TaskPayBookMapper taskPayBookMapper;

    private final BuyBookBindingMapper buyBookBindingMapper;

    public TaskPayBookService(
        TaskInstanceService taskInstanceService,
        BuyBookService buyBookService,
        TaskInstanceRepository taskInstanceRepository,
        BuyBookBindingRepository buyBookBindingRepository,
        TaskInstanceMapper taskInstanceMapper,
        TaskPayBookMapper taskPayBookMapper,
        BuyBookBindingMapper buyBookBindingMapper
    ) {
        this.taskInstanceService = taskInstanceService;
        this.buyBookService = buyBookService;
        this.taskInstanceRepository = taskInstanceRepository;
        this.buyBookBindingRepository = buyBookBindingRepository;
        this.taskInstanceMapper = taskInstanceMapper;
        this.taskPayBookMapper = taskPayBookMapper;
        this.buyBookBindingMapper = buyBookBindingMapper;
    }

    public TaskPayBookContextDTO loadContext(Long taskInstanceId) {
        TaskInstanceDTO taskInstanceDTO = taskInstanceRepository
            .findById(taskInstanceId)
            .map(taskInstanceMapper::toDTOLoadTaskContext)
            .orElseThrow();

        BuyBookBindingDTO buyBookBinding = buyBookBindingRepository
            .findByProcessInstanceId(taskInstanceDTO.getProcessInstance().getId())
            .map(taskPayBookMapper::toBuyBookBindingDTO)
            .orElseThrow();

        TaskPayBookContextDTO taskPayBookContext = new TaskPayBookContextDTO();
        taskPayBookContext.setTaskInstance(taskInstanceDTO);
        taskPayBookContext.setBuyBookBinding(buyBookBinding);

        return taskPayBookContext;
    }

    public TaskPayBookContextDTO claim(Long taskInstanceId) {
        taskInstanceService.claim(taskInstanceId);
        return loadContext(taskInstanceId);
    }

    public void save(TaskPayBookContextDTO taskPayBookContext) {
        BuyBookDTO buyBookDTO = buyBookService.findOne(taskPayBookContext.getBuyBookBinding().getBuyBook().getId()).orElseThrow();
        buyBookDTO.setPurpose(taskPayBookContext.getBuyBookBinding().getBuyBook().getPurpose());
        buyBookDTO.setPaymentDetails(taskPayBookContext.getBuyBookBinding().getBuyBook().getPaymentDetails());
        buyBookService.save(buyBookDTO);
    }

    public void complete(TaskPayBookContextDTO taskPayBookContext) {
        save(taskPayBookContext);
        BuyBookBindingDTO buyBookBinding = buyBookBindingRepository
            .findByProcessInstanceId(taskPayBookContext.getBuyBookBinding().getProcessInstance().getId())
            .map(buyBookBindingMapper::toDto)
            .orElseThrow();
        taskInstanceService.complete(taskPayBookContext.getTaskInstance(), buyBookBinding);
    }
}
