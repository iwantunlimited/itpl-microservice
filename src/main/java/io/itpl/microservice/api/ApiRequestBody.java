package io.itpl.microservice.api;

public class ApiRequestBody {
    private String actionCode;
    private String tid;
    private String sid;
    private String remark;

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getActionCode() {
        return actionCode;
    }

    public void setActionCode(String actionCode) {
        this.actionCode = actionCode;
    }



    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }




    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }
}
