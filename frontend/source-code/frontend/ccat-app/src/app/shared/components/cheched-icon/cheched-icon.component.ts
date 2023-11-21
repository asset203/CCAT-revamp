import { Component, Input, OnInit } from '@angular/core';
import {faCircleCheck} from '@fortawesome/free-solid-svg-icons';
@Component({
  selector: 'app-cheched-icon',
  templateUrl: './cheched-icon.component.html',
  styleUrls: ['./cheched-icon.component.scss']
})
export class ChechedIconComponent implements OnInit {
  faCircleCheck = faCircleCheck;
  @Input() condition;
  constructor() { }

  ngOnInit(): void {
  }

}
