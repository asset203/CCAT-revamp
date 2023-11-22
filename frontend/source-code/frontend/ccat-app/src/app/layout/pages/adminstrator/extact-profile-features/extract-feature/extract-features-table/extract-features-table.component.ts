import { Component, Input, OnChanges, OnInit, SimpleChanges } from '@angular/core';
import {saveAs} from 'file-saver';
@Component({
    selector: 'app-extract-features-table',
    templateUrl: './extract-features-table.component.html',
    styleUrls: ['./extract-features-table.component.scss'],
})
export class ExtractFeaturesTableComponent implements OnInit, OnChanges {
    @Input() profileFeaturesData;
    @Input() features;
    @Input() featureColumns;
    selectedFilter;
    dataTableData;
    featuresData;
    dataTableColumns;
    constructor() { }
    ngOnChanges(changes: SimpleChanges): void {
        this.selectedFilter = null;
        this.dataTableData = changes.profileFeaturesData.currentValue;
        this.featuresData = changes.features.currentValue;
        this.dataTableColumns = changes.featureColumns.currentValue;
    }

    ngOnInit(): void {
        this.setInitalDataInfo();
    }
    clearFilter() {
        this.setInitalDataInfo();
        this.selectedFilter = null;
    }
    exportExcel(buffer: any, fileName: string): void {
        let EXCEL_TYPE = 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=UTF-8';
        let EXCEL_EXTENSION = '.xlsx';
        const data: Blob = new Blob([buffer], {
            type: EXCEL_TYPE,
        });
        saveAs(data, fileName + '_export_' + new Date().getTime() + EXCEL_EXTENSION);
    }
    extractFullProfileDataCSV(data) {
        import('xlsx').then((xlsx) => {
            console.log(this.dataTableData)
            const worksheet = xlsx.utils.json_to_sheet(data);
            const workbook = { Sheets: { data: worksheet }, SheetNames: ['data'] };
            const excelBuffer: any = xlsx.write(workbook, { bookType: 'xlsx', type: 'array' });
            this.exportExcel(excelBuffer, 'profiles-features');
        });
    }
    filter() {
        this.dataTableColumns = this.selectedFilter.map((el) => el.name);
        if (this.dataTableColumns.length === 0) {
            this.dataTableColumns = this.featureColumns;
            this.dataTableData = this.profileFeaturesData;
        } else {
            this.dataTableData = this.profileFeaturesData.map((el) => {
                let temp = {};
                this.dataTableColumns.forEach((filter) => {
                    temp[filter] = el[filter];
                });
                return {
                    profile: el.profile,
                    ...temp,
                };
            });
            console.log(this.dataTableData)
        }
    }
    setInitalDataInfo() {
        this.dataTableData = this.profileFeaturesData;
        this.featuresData = this.features;
        this.dataTableColumns = this.featureColumns;
    }
}
