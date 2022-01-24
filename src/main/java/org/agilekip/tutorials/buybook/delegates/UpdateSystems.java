package org.agilekip.tutorials.buybook.delegates;

import java.math.BigDecimal;
import org.agilekip.tutorials.buybook.domain.BuyBook;
import org.agilekip.tutorials.buybook.service.dto.BuyBookBindingDTO;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UpdateSystems implements JavaDelegate {

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        BuyBookBindingDTO pi = (BuyBookBindingDTO) delegateExecution.getVariable("pi");

        String purpose = pi.getBuyBook().getPurpose();
        System.out.println("=================================================");
        System.out.println("=============== UPDATING SYSTEMS !!!======================");
        System.out.println("=============== " + purpose + "======================");
        System.out.println("=================================================");
    }
}
