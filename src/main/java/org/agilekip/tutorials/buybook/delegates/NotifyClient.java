package org.agilekip.tutorials.buybook.delegates;

import java.util.Locale;
import org.agilekip.tutorials.buybook.service.MailService;
import org.agilekip.tutorials.buybook.service.dto.BuyBookBindingDTO;
import org.agilekip.tutorials.buybook.service.dto.BuyBookDTO;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

@Component
public class NotifyClient implements JavaDelegate {

    @Autowired
    MailService mailService;

    @Autowired
    SpringTemplateEngine templateEngine;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++");
        System.out.println(delegateExecution.getVariable("processInstance").toString());
        System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++");
        BuyBookBindingDTO pi = (BuyBookBindingDTO) delegateExecution.getVariable("processInstance");
        BuyBookDTO buyBook = pi.getBuyBook();

        String to = "agilekip@agilekip.com";
        String subject = "[AgileKip] Summary of your purchase " + buyBook.getPurpose();
        Context context = new Context(Locale.getDefault());
        context.setVariable("buyBook", buyBook);
        String content = templateEngine.process("buyBookProcess/notifyClientEmail", context);
        mailService.sendEmail(to, subject, content, false, true);
    }
}
