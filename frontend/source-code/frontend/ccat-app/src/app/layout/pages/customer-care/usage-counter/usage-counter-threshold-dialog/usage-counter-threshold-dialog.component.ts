import { Component, Input, OnInit } from '@angular/core';

@Component({
  selector: 'app-usage-counter-threshold-dialog',
  templateUrl: './usage-counter-threshold-dialog.component.html',
  styleUrls: ['./usage-counter-threshold-dialog.component.scss']
})
export class UsageCounterThresholdDialogComponent implements OnInit {

  constructor() { }

  @Input() isModalOpened: boolean;

  ngOnInit(): void {
  }

}
