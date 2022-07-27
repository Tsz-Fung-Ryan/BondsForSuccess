import { Injectable } from "@angular/core";
import { Subject } from "rxjs";
import { Match } from "../libs/api/generated-code/api";

@Injectable({
  providedIn: 'root'
})
export class MatchedResultsService {
  public matchedResultsChanged: Subject<Match[]> = new Subject<Match[]>();

  setMatchedResults(matchedResults: Match[]): void {
    this.matchedResultsChanged.next(matchedResults);
  }
}