import { CdkDragDrop, moveItemInArray } from '@angular/cdk/drag-drop';
import { Component, OnInit, ViewChild } from '@angular/core';
import { MatTable } from '@angular/material/table';
import { Match, Person } from '../libs/api/generated-code/api';
import { MatchedResultsService } from '../services/matched-results.service';

@Component({
  selector: 'mentee-mentor-table',
  templateUrl: './mentee-mentor-table.component.html',
  styleUrls: ['./mentee-mentor-table.component.scss']
})
export class MenteeMentorTableComponent implements OnInit {
  @ViewChild('menteeTable') menteeTable: MatTable<Person> | undefined;
  @ViewChild('mentorTable') mentorTable: MatTable<Person> | undefined;

  public menteeDisplayedColumns = ['menteeName', 'menteeEmail'];
  public menteeDisplayedHeaderRowColumns = ['header-row-mentee'];
  public mentorDisplayedColumns = ['mentorName', 'mentorEmail'];
  public mentorDisplayedHeaderRowColumns = ['header-row-mentor'];

  public menteeMentorData: Match[] = [];
  public menteeData: Person[] = [];
  public mentorData: Person[] = [];

  constructor(
    private matchedResults: MatchedResultsService,
  ) {}

  ngOnInit(): void {
    this.matchedResults.matchedResultsChanged.subscribe(matchedResults => {
      this.menteeMentorData = matchedResults;
      const { menteeData, mentorData } = this.getMenteeAndMentorData(matchedResults);

      this.menteeData = menteeData;
      this.mentorData = mentorData;
    });
  }

  getMenteeAndMentorData(matchedResults: Match[]): { menteeData: Person[], mentorData: Person[] } {
    const menteeData: Person[] = [];
    const mentorData: Person[] = [];

    matchedResults.forEach((matchedResult) => {
      menteeData.push(matchedResult.mentee);
      mentorData.push(matchedResult.mentor);
    });

    return {
      menteeData: menteeData,
      mentorData: mentorData,
    };
  }

  drop(event: CdkDragDrop<string[]>, arrayToRearrange: Person[]) {
    moveItemInArray(arrayToRearrange, event.previousIndex, event.currentIndex);
    this.menteeTable?.renderRows();
    this.mentorTable?.renderRows();
  }

  handleDownloadButtonClicked(): void {
    const combinedMenteeAndMentorData = this.combineMenteeAndMentorData(this.menteeData, this.mentorData);

    console.log(combinedMenteeAndMentorData);
  }

  combineMenteeAndMentorData(menteeData: Person[], mentorData: Person[]): Match[] {
    const combinedMatches: Match[] = [];

    menteeData.forEach((mentee, index) => {
      const newMatch: Match = {
        mentee: mentee,
        mentor: mentorData[index],
      };

      combinedMatches.push(newMatch);
    });

    return combinedMatches;
  }
}
