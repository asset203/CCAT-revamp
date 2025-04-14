import {Component, OnInit, ViewChild} from '@angular/core';
import {Router} from '@angular/router';
import {Table} from 'primeng/table';

@Component({
    selector: 'app-dynamic-pages-list',
    templateUrl: './dynamic-pages-list.component.html',
    styleUrls: ['./dynamic-pages-list.component.scss'],
})
export class DynamicPagesListComponent implements OnInit {
    search = false;
    constructor(private router: Router) {}
    CCdynamicPages = [];
    searchText;
    @ViewChild('dt') dt: Table | undefined;
    ngOnInit(): void {
        this.CCdynamicPages = JSON.parse(sessionStorage.getItem('session')).userProfile.menus[0].items.filter(el=>el.dynamicMenu);
    }
    onSearchInput(inputValue: string): void {
        if (!inputValue) {
            this.dt.clear();
            this.dt.reset();
            this.dt.filterGlobal('', 'contains');
            this.dt.first = 0;
        } else {
            this.dt.filterGlobal(inputValue, 'contains');
        }
    }
    clear(table: Table) {
        if (table.filters) {
            table.filters = {};
        }
        this.searchText = null;
        table.clear();
    }
    navigateToDynamicPage(link) {
      this.router.navigate([link])
    }
}
