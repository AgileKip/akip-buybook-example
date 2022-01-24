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
public class TaskSelectDeliveryService {

    private final TaskInstanceService taskInstanceService;

    private final BuyBookService buyBookService;

    private final TaskInstanceRepository taskInstanceRepository;

    private final BuyBookBindingRepository buyBookBindingRepository;

    private final TaskInstanceMapper taskInstanceMapper;

    private final TaskSelectDeliveryMapper taskSelectDeliveryMapper;

    private final BuyBookBindingMapper buyBookBindingMapper;

    public TaskSelectDeliveryService(
        TaskInstanceService taskInstanceService,
        BuyBookService buyBookService,
        TaskInstanceRepository taskInstanceRepository,
        BuyBookBindingRepository buyBookBindingRepository,
        TaskInstanceMapper taskInstanceMapper,
        TaskSelectDeliveryMapper taskSelectDeliveryMapper,
        BuyBookBindingMapper buyBookBindingMapper
    ) {
        this.taskInstanceService = taskInstanceService;
        this.buyBookService = buyBookService;
        this.taskInstanceRepository = taskInstanceRepository;
        this.buyBookBindingRepository = buyBookBindingRepository;
        this.taskInstanceMapper = taskInstanceMapper;
        this.taskSelectDeliveryMapper = taskSelectDeliveryMapper;
        this.buyBookBindingMapper = buyBookBindingMapper;
    }

    public TaskSelectDeliveryContextDTO loadContext(Long taskInstanceId) {
        TaskInstanceDTO taskInstanceDTO = taskInstanceRepository
            .findById(taskInstanceId)
            .map(taskInstanceMapper::toDTOLoadTaskContext)
            .orElseThrow();

        BuyBookBindingDTO buyBookBinding = buyBookBindingRepository
            .findByProcessInstanceId(taskInstanceDTO.getProcessInstance().getId())
            .map(taskSelectDeliveryMapper::toBuyBookBindingDTO)
            .orElseThrow();

        TaskSelectDeliveryContextDTO taskSelectDeliveryContext = new TaskSelectDeliveryContextDTO();
        taskSelectDeliveryContext.setTaskInstance(taskInstanceDTO);
        taskSelectDeliveryContext.setBuyBookBinding(buyBookBinding);

        return taskSelectDeliveryContext;
    }

    public TaskSelectDeliveryContextDTO claim(Long taskInstanceId) {
        taskInstanceService.claim(taskInstanceId);
        return loadContext(taskInstanceId);
    }

    public void save(TaskSelectDeliveryContextDTO taskSelectDeliveryContext) {
        BuyBookDTO buyBookDTO = buyBookService.findOne(taskSelectDeliveryContext.getBuyBookBinding().getBuyBook().getId()).orElseThrow();
        buyBookDTO.setPurpose(taskSelectDeliveryContext.getBuyBookBinding().getBuyBook().getPurpose());
        buyBookDTO.setDeliveryHome(taskSelectDeliveryContext.getBuyBookBinding().getBuyBook().getDeliveryHome());
        buyBookService.save(buyBookDTO);
    }

    public void complete(TaskSelectDeliveryContextDTO taskSelectDeliveryContext) {
        save(taskSelectDeliveryContext);
        BuyBookBindingDTO buyBookBinding = buyBookBindingRepository
            .findByProcessInstanceId(taskSelectDeliveryContext.getBuyBookBinding().getProcessInstance().getId())
            .map(buyBookBindingMapper::toDto)
            .orElseThrow();
        taskInstanceService.complete(taskSelectDeliveryContext.getTaskInstance(), buyBookBinding);
    }
}
