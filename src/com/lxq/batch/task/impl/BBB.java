package com.lxq.batch.task.impl;

import com.lxq.batch.status.StatusEnum;
import com.lxq.batch.task.interf.AbstractTaskUnit;

public class BBB extends AbstractTaskUnit {
    public StatusEnum execute() throws Exception {
    	
        System.out.println(this.getName());
        
        return StatusEnum.executeSuccess;
    }

}