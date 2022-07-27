import { Component } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { NgxFileDropEntry } from 'ngx-file-drop';
import { Match } from '../libs/api/generated-code/api';
import { MatchedResultsService } from '../services/matched-results.service';
import { TablesService } from '../services/tables.service';

@Component({
  selector: 'file-input',
  templateUrl: './file-input.component.html',
  styleUrls: ['./file-input.component.scss']
})
export class FileInputComponent {
  public menteeFileIdentifier = 'menteeFile';
  public mentorFileIdentifier = 'mentorFile';

  public menteeFile: NgxFileDropEntry | undefined;
  public mentorFile: NgxFileDropEntry | undefined;

  constructor(
    private matchedResultsService: MatchedResultsService,
    private snackBar: MatSnackBar,
    private tablesService: TablesService,
  ) {}

  handleFileDropped(files: NgxFileDropEntry[], fileToOverwrite: string): void {
    const firstDroppedFile = files[0];

    // Is it a file?
    if (firstDroppedFile.fileEntry.isFile) {
      const fileEntry = firstDroppedFile.fileEntry as FileSystemFileEntry;
      fileEntry.file((file: File) => {

        // Here you can access the real file
        console.log(firstDroppedFile.relativePath, file);

        if (fileToOverwrite === this.menteeFileIdentifier) {
          this.menteeFile = firstDroppedFile;
        } else if (fileToOverwrite === this.mentorFileIdentifier) {
          this.mentorFile = firstDroppedFile;
        }

      });
    } else {
      // It was a directory (empty directories are added, otherwise only files)
      const fileEntry = firstDroppedFile.fileEntry as FileSystemDirectoryEntry;
      console.log(firstDroppedFile.relativePath, fileEntry);
    }
  }

  async uploadMenteeMentorFiles(): Promise<void> {
    if (this.menteeFile && this.mentorFile) {
      const matchedResults = await this.uploadFiles(this.menteeFile, this.mentorFile);

      if (matchedResults) {
        this.matchedResultsService.setMatchedResults(matchedResults);
        this.snackBar.open(
          'Uploaded mentee and mentor files. Please proceed to next step',
          undefined,
          {
            duration: 4000,
            verticalPosition: 'top',
          },
        );
      }
    } else {
      this.snackBar.open(
        'Please select a mentee and mentor file',
        undefined,
        {
          duration: 4000,
          verticalPosition: 'top',
        },
      );
    }
  }

  async uploadFiles(fileOne: NgxFileDropEntry, fileTwo: NgxFileDropEntry): Promise<Match[] | undefined> {
    try {
      return this.tablesService.uploadFiles(fileOne, fileTwo);
    } catch(error) {
      this.snackBar.open(
        `${error}`,
        undefined,
        {
          duration: 4000,
          verticalPosition: 'top',
        },
      );
      return undefined;
    }

  }
}
