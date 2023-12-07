import {ChangeDetectorRef, Component, OnInit, ViewChild} from '@angular/core';
import { ExternalNodesService } from 'src/app/core/service/administrator/external-nodes.service';

@Component({
    selector: 'app-external-service-nodes',
    templateUrl: './external-service-nodes.component.html',
    styleUrls: ['./external-service-nodes.component.scss'],
})
export class ExternalServiceNodesComponent implements OnInit {
    tab = 'Air Nodes';
    constructor(private cdr: ChangeDetectorRef , private externalNodesService : ExternalNodesService) {}
    loading = this.externalNodesService.loading;
    ngAfterViewChecked(): void {
        this.cdr.detectChanges();
    }

    ngOnInit(): void {}
    switchTab(tab) {
        this.tab = tab;
    }
}
