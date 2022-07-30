import { CdkDragDrop, moveItemInArray } from '@angular/cdk/drag-drop';
import { Component, OnInit, ViewChild } from '@angular/core';
import { MatTable } from '@angular/material/table';
import { Match } from '../libs/api/generated-code/api';
import { MatchedResultsService } from '../services/matched-results.service';

@Component({
  selector: 'mentee-mentor-table',
  templateUrl: './mentee-mentor-table.component.html',
  styleUrls: ['./mentee-mentor-table.component.scss']
})
export class MenteeMentorTableComponent implements OnInit {
  @ViewChild('menteeMentorTable') menteeMentorTable: MatTable<Match> | undefined;

  public displayedColumns = ['menteeName', 'menteeEmail', 'mentorName', 'mentorEmail'];
  public displayedHeaderRowColums = ['header-row-mentor', 'header-row-mentee'];
  public menteeMentorData: Match[] = [];

  constructor(
    private matchedResults: MatchedResultsService,
  ) {}

  ngOnInit(): void {
    this.matchedResults.matchedResultsChanged.subscribe(matchedResults => {
      this.menteeMentorData = matchedResults;
    });
  }

  drop(event: CdkDragDrop<string[]>) {
    if (this.menteeMentorData) {
      moveItemInArray(this.menteeMentorData, event.previousIndex, event.currentIndex);
      this.menteeMentorTable?.renderRows();
    }
  }
}
