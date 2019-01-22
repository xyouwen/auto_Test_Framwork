package pojo;

/**
 *所有Excell 行对象的抽象类
 */
public abstract  class ExcelRowObjects {

    /**
    * 行号
    */
    private int rowNum;

    public int getRowNum() {
        return rowNum;
    }

    public void setRowNum(int rowNum) {
        this.rowNum = rowNum;
    }
}
