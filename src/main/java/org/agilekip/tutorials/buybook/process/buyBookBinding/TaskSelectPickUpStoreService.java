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
public class TaskSelectPickUpStoreService {

    private final TaskInstanceService taskInstanceService;

    private final BuyBookService buyBookService;

    private final TaskInstanceRepository taskInstanceRepository;

    private final BuyBookBindingRepository buyBookBindingRepository;

    private final TaskInstanceMapper taskInstanceMapper;

    private final TaskSelectPickUpStoreMapper taskSelectPickUpStoreMapper;

    private final BuyBookBindingMapper buyBookBindingMapper;

    public TaskSelectPickUpStoreService(
        TaskInstanceService taskInstanceService,
        BuyBookService buyBookService,
        TaskInstanceRepository taskInstanceRepository,
        BuyBookBindingRepository buyBookBindingRepository,
        TaskInstanceMapper taskInstanceMapper,
        TaskSelectPickUpStoreMapper taskSelectPickUpStoreMapper,
        BuyBookBindingMapper buyBookBindingMapper
    ) {
        this.taskInstanceService = taskInstanceService;
        this.buyBookService = buyBookService;
        this.taskInstanceRepository = taskInstanceRepository;
        this.buyBookBindingRepository = buyBookBindingRepository;
        this.taskInstanceMapper = taskInstanceMapper;
        this.taskSelectPickUpStoreMapper = taskSelectPickUpStoreMapper;
        this.buyBookBindingMapper = buyBookBindingMapper;
    }

    public TaskSelectPickUpStoreContextDTO loadContext(Long taskInstanceId) {
        TaskInstanceDTO taskInstanceDTO = taskInstanceRepository
            .findById(taskInstanceId)
            .map(taskInstanceMapper::toDTOLoadTaskContext)
            .orElseThrow();

        BuyBookBindingDTO buyBookBinding = buyBookBindingRepository
            .findByProcessInstanceId(taskInstanceDTO.getProcessInstance().getId())
            .map(taskSelectPickUpStoreMapper::toBuyBookBindingDTO)
            .orElseThrow();

        TaskSelectPickUpStoreContextDTO taskSelectPickUpStoreContext = new TaskSelectPickUpStoreContextDTO();
        taskSelectPickUpStoreContext.setTaskInstance(taskInstanceDTO);
        taskSelectPickUpStoreContext.setBuyBookBinding(buyBookBinding);

        return taskSelectPickUpStoreContext;
    }

    public TaskSelectPickUpStoreContextDTO claim(Long taskInstanceId) {
        taskInstanceService.claim(taskInstanceId);
        return loadContext(taskInstanceId);
    }

    public void save(TaskSelectPickUpStoreContextDTO taskSelectPickUpStoreContext) {
        BuyBookDTO buyBookDTO = buyBookService.findOne(taskSelectPickUpStoreContext.getBuyBookBinding().getBuyBook().getId()).orElseThrow();
        buyBookDTO.setPurpose(taskSelectPickUpStoreContext.getBuyBookBinding().getBuyBook().getPurpose());
        buyBookDTO.setStore(taskSelectPickUpStoreContext.getBuyBookBinding().getBuyBook().getStore());
        buyBookService.save(buyBookDTO);
    }

    public void complete(TaskSelectPickUpStoreContextDTO taskSelectPickUpStoreContext) {
        save(taskSelectPickUpStoreContext);
        BuyBookBindingDTO buyBookBinding = buyBookBindingRepository
            .findByProcessInstanceId(taskSelectPickUpStoreContext.getBuyBookBinding().getProcessInstance().getId())
            .map(buyBookBindingMapper::toDto)
            .orElseThrow();
        taskInstanceService.complete(taskSelectPickUpStoreContext.getTaskInstance(), buyBookBinding);
    }
}
