import { Injectable } from '@angular/core';
import { NgxFileDropEntry } from 'ngx-file-drop';

@Injectable({
  providedIn: 'root'
})
export class TablesService {

  async uploadFiles(menteeFile: NgxFileDropEntry, mentorFile: NgxFileDropEntry): Promise<void> {
    Promise.resolve();
  }
}
