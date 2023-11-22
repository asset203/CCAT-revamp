import {Component, EventEmitter, Input, OnChanges, OnInit, Output, SimpleChanges} from '@angular/core';
import {Feature} from 'src/app/shared/models/feature.interface';

@Component({
    selector: 'app-menu-order',
    templateUrl: './menu-order.component.html',
    styleUrls: ['./menu-order.component.scss'],
})
export class MenuOrderComponent implements OnInit, OnChanges {
    @Input() menus: Feature[];
    @Input() header: string;
    @Input() styleClass: string;
    selectedMenu;
    filteredMenu = [];
    @Output() sendOrderdMenu = new EventEmitter<Feature[]>();
    constructor() {}
    ngOnChanges(changes: SimpleChanges): void {
        /*if (changes.menus&&changes.menus.previousValue) {
            if(changes.menus.currentValue.length !== changes.menus.previousValue.length)
            {this.setFilterAndSort();}
        }*/
    }
    ngOnInit(): void {
        
        this.setFilterAndSort();
    }
    setFilterAndSort() {
        this.filteredMenu = this.menus.filter((menu) => menu.pageName === 'MENU');
        this.filteredMenu = this.filteredMenu.sort((a, b) => a.order - b.order);
        console.log(this.filteredMenu)
    }

    reorder() {
        const newMenu = this.filteredMenu.map((item, index) => {
            return {
                ...item,
                order: index + 1,
            };
        });
        this.sendOrderdMenu.emit(newMenu);
    }
}
