package edu.indiana.d2i.sloan.bean;

import edu.indiana.d2i.sloan.result.ResultState;

/**
 *
 * List info for a given resultid
 * Create a snapshot for individual result (per id)
 *
 **/



public class ResultInfoBean {
    private String vmid, resultId, createtime, notified, notifiedtime, reviewer, status, comment;
    private Boolean expired;
    private ResultState state;

    public ResultInfoBean(String vmid, String resultId,
                          String createtime, String notified, String notifiedtime,
                          String reviewer, String status, String comment, Boolean expired, ResultState state)
    {
        this.vmid = vmid;
        //this.datafield = datafield;
        this.resultId = resultId;
        this.createtime = createtime;
        this.notified = notified;
        this.notifiedtime = notifiedtime;
        this.reviewer = reviewer;
        this.status = status;
        this.comment = comment;
        this.expired = expired;
        this.state = state;
    }

    public String getVmid() {
        return vmid;
    }

    //public String getDatafield() {
    //    return datafield;
    //}

    public String getResultId() {
        return resultId;
    }

    public String getCreatetime(){
        return createtime;
    }

    public String getNotified(){
        return notified;
    }

    public String getNotifiedtime(){
        return notifiedtime;
    }

    public String getReviewer(){return reviewer;}

    public String getStatus(){return status;}

    public String getComment(){return comment;}

    public Boolean isExpired(){return expired;}

    public ResultState getState() {
        return state;
    }
    //public String getUseremail() { return useremail; }
}

