package demo_book.demo.dto;

import java.util.ArrayList;
import java.util.List;

public class ImportResult {
    private int total;
    private int successCount;
    private int failCount;
    private List<FailureDetail> failures = new ArrayList<>();
    public void addFailure(int row, String reason) {
        failures.add(new FailureDetail(row, reason));

    }
    public static class FailureDetail {
        private int row;
        private String reason;

        public FailureDetail(){};
        public FailureDetail(int row, String reason){
            this.row = row;
            this.reason = reason;

        }
        public int getRow() { return row; }
        public void setRow(int row){this.row = row;}
        public String getReason() {return reason;}
        public void setReason(String reason){this.reason = reason;}


    }

    public int getTotal(){return total;}
    public void setTotal(int total){this.total = total;}
    public int getSuccessCount(){return successCount;}
    public void setSuccessCount(int successCount){this.successCount = successCount;}
    public int getFailCount(){return failCount;}
    public void setFailCount( int failCount){this.failCount = failCount;}
    public List<FailureDetail> getFailures(){return failures;}
    public void setFailures(List<FailureDetail> failures){this.failures = failures;}




}
