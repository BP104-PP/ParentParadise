package com.project.pp.lin;

public class Good {
	private String recordType;
    private int recordId;
    private int memberNo;

    public Good(String recordType, int recordId, int memberNo) {
        this.recordType = recordType;
        this.recordId = recordId;
        this.memberNo = memberNo;
    }

    public String getRecordType() {
        return recordType;
    }

    public void setRecordType(String recordType) {
        this.recordType = recordType;
    }

    public int getRecordId() {
        return recordId;
    }

    public void setRecordId(int recordId) {
        this.recordId = recordId;
    }

    public int getMemberNo() {
        return memberNo;
    }

    public void setMemberNo(int memberNo) {
        this.memberNo = memberNo;
    }
}
