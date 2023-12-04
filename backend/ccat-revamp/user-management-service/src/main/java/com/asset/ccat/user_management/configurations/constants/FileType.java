package com.asset.ccat.user_management.configurations.constants;

/**
 *
 * @author marwa.elshawarby
 */
public enum FileType {

    EXCEL_2007(1, ".xlsx"),
    EXCEL_2003(2, ".xls"),
    CSV(3, ".csv");

    public final int id;
    public final String ext;

    private FileType(int id, String ext) {
        this.id = id;
        this.ext = ext;
    }
}
