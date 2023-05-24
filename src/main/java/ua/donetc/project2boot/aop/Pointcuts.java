package ua.donetc.project2boot.aop;

import org.aspectj.lang.annotation.Pointcut;

public class Pointcuts {

    @Pointcut("execution(* ua.donetc.project2boot.services.CodeService.get*(..))")
    public void allGetMethods(){

    }

    @Pointcut("execution(* ua.donetc.project2boot.services.CodeService.add*(..))")
    public void allAddMethods(){

    }

}
