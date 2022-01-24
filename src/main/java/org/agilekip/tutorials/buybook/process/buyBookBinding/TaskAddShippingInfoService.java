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
public class TaskAddShippingInfoService {

    private final TaskInstanceService taskInstanceService;

    private final BuyBookService buyBookService;

    private final TaskInstanceRepository taskInstanceRepository;

    private final BuyBookBindingRepository buyBookBindingRepository;

    private final TaskInstanceMapper taskInstanceMapper;

    private final TaskAddShippingInfoMapper taskAddShippingInfoMapper;

    private final BuyBookBindingMapper buyBookBindingMapper;

    public TaskAddShippingInfoService(
        TaskInstanceService taskInstanceService,
        BuyBookService buyBookService,
        TaskInstanceRepository taskInstanceRepository,
        BuyBookBindingRepository buyBookBindingRepository,
        TaskInstanceMapper taskInstanceMapper,
        TaskAddShippingInfoMapper taskAddShippingInfoMapper,
        BuyBookBindingMapper buyBookBindingMapper
    ) {
        this.taskInstanceService = taskInstanceService;
        this.buyBookService = buyBookService;
        this.taskInstanceRepository = taskInstanceRepository;
        this.buyBookBindingRepository = buyBookBindingRepository;
        this.taskInstanceMapper = taskInstanceMapper;
        this.taskAddShippingInfoMapper = taskAddShippingInfoMapper;
        this.buyBookBindingMapper = buyBookBindingMapper;
    }

    public TaskAddShippingInfoContextDTO loadContext(Long taskInstanceId) {
        TaskInstanceDTO taskInstanceDTO = taskInstanceRepository
            .findById(taskInstanceId)
            .map(taskInstanceMapper::toDTOLoadTaskContext)
            .orElseThrow();

        BuyBookBindingDTO buyBookBinding = buyBookBindingRepository
            .findByProcessInstanceId(taskInstanceDTO.getProcessInstance().getId())
            .map(taskAddShippingInfoMapper::toBuyBookBindingDTO)
            .orElseThrow();

        TaskAddShippingInfoContextDTO taskAddShippingInfoContext = new TaskAddShippingInfoContextDTO();
        taskAddShippingInfoContext.setTaskInstance(taskInstanceDTO);
        taskAddShippingInfoContext.setBuyBookBinding(buyBookBinding);

        return taskAddShippingInfoContext;
    }

    public TaskAddShippingInfoContextDTO claim(Long taskInstanceId) {
        taskInstanceService.claim(taskInstanceId);
        return loadContext(taskInstanceId);
    }

    public void save(TaskAddShippingInfoContextDTO taskAddShippingInfoContext) {
        BuyBookDTO buyBookDTO = buyBookService.findOne(taskAddShippingInfoContext.getBuyBookBinding().getBuyBook().getId()).orElseThrow();
        buyBookDTO.setPurpose(taskAddShippingInfoContext.getBuyBookBinding().getBuyBook().getPurpose());
        buyBookDTO.setDeliveryAddress(taskAddShippingInfoContext.getBuyBookBinding().getBuyBook().getDeliveryAddress());
        buyBookService.save(buyBookDTO);
    }

    public void complete(TaskAddShippingInfoContextDTO taskAddShippingInfoContext) {
        save(taskAddShippingInfoContext);
        BuyBookBindingDTO buyBookBinding = buyBookBindingRepository
            .findByProcessInstanceId(taskAddShippingInfoContext.getBuyBookBinding().getProcessInstance().getId())
            .map(buyBookBindingMapper::toDto)
            .orElseThrow();
        taskInstanceService.complete(taskAddShippingInfoContext.getTaskInstance(), buyBookBinding);
    }
}
