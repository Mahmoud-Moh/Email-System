package com.WebApp.EmailServerClient.AOP;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Aspect
@Component
public class AspectLogging {
    @Before(value = "execution(* com.WebApp.EmailServerClient.Controllers.MainController.*(..))")
    public void BeforeAdvice(JoinPoint TheMethod) {
        String Data = "";
        if (Objects.equals(TheMethod.getSignature().getName(), "SignUp")) {
            Object[] Type = TheMethod.getArgs();
            Data = "--- New User Data --> " + Type[0];
        } else if (Objects.equals(TheMethod.getSignature().getName(), "LogIn")) {
            Object[] Type = TheMethod.getArgs();
            Data = "--- Login Credentials --> ";
            Data = Data.concat("Email Address: " + Type[0] + " Password: " + Type[1]);
        } else if (Objects.equals(TheMethod.getSignature().getName(), "SearchEmail")) {
            Object[] Type = TheMethod.getArgs();
            Data = "--- Searching for the value --> " + Type[0] + " in the attribute --> " + Type[2] +
                    " in folder --> " + Type[1] + " for ID --> " + Type[3];
        } else if (Objects.equals(TheMethod.getSignature().getName(), "ShowEmail")) {
            Object[] Type = TheMethod.getArgs();
            Data = "--- Sorting emails by --> " + Type[1] +
                    " in folder --> " + Type[0] + " for ID --> " + Type[2];
        } else if (Objects.equals(TheMethod.getSignature().getName(), "SendEmail")) {
            Object[] Type = TheMethod.getArgs();
            Data = "--- Email Content --> " + Type[0] + " From User with ID --> " + Type[1];
        } else if (Objects.equals(TheMethod.getSignature().getName(), "PriorityEmail")) {
            Object[] Type = TheMethod.getArgs();
            Data = "--- Sorting emails by priority for User with ID --> " + Type[0];
        } else if (Objects.equals(TheMethod.getSignature().getName(), "DraftEmail")) {
            Object[] Type = TheMethod.getArgs();
            Data = "--- Send not sent-email to draft for User with ID --> " + Type[1] + " Email --> " + Type[0];
        } else if (Objects.equals(TheMethod.getSignature().getName(), "Attachment")) {
            Data = "--- Waiting for a file to attach";
        } else if (Objects.equals(TheMethod.getSignature().getName(), "DeleteEmail")) {
            Object[] Type = TheMethod.getArgs();
            Data = "--- Email with ID --> " + Type[2] + " will be Deleted within User --> " + Type[1] + " Folder with ID --> " + Type[0];
        }

        System.out.println("Before : " + TheMethod.getSignature().getName() + " " + Data);
    }

    @AfterReturning(value = "execution(* com.WebApp.EmailServerClient.Controllers.MainController.*(..))")
    public void AfterAdvice(JoinPoint TheMethod) {
        String Data = "";
        if (Objects.equals(TheMethod.getSignature().getName(), "SignUp")) {
            Data = "--- Signed Up";
        } else if (Objects.equals(TheMethod.getSignature().getName(), "LogIn")) {
            Data = "--- Login Credentials --> Validated";
        } else if (Objects.equals(TheMethod.getSignature().getName(), "SearchEmail")) {
            Data = "--- Searching Ended";
        } else if (Objects.equals(TheMethod.getSignature().getName(), "ShowEmail")) {
            Data = "--- Sorting Ended --> ";
        } else if (Objects.equals(TheMethod.getSignature().getName(), "SendEmail")) {
            Object[] Type = TheMethod.getArgs();
            Data = "--- Email is sent From User with ID " + Type[1];
        } else if (Objects.equals(TheMethod.getSignature().getName(), "PriorityEmail")) {
            Data = "--- Sorting Ended";
        } else if (Objects.equals(TheMethod.getSignature().getName(), "DraftEmail")) {
            Object[] Type = TheMethod.getArgs();
            Data = "--- Email is sent to draft for User with ID --> " + Type[1];
        } else if (Objects.equals(TheMethod.getSignature().getName(), "Attachment")) {
            Data = "--- File is Attached";
        } else if (Objects.equals(TheMethod.getSignature().getName(), "DeleteEmail")) {
            Object[] Type = TheMethod.getArgs();
            Data = "--- Email with ID " + Type[2] + " is Deleted within User " + Type[1] + " Folder with ID " + Type[0];
        }

        System.out.println("Success After : " + TheMethod.getSignature().getName() + " " + Data + "\n");
    }
}
