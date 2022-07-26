import { Component } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { NgxFileDropEntry } from 'ngx-file-drop';
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
      await this.uploadFiles(this.menteeFile, this.mentorFile);
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

  async uploadFiles(fileOne: NgxFileDropEntry, fileTwo: NgxFileDropEntry): Promise<void> {
    try {
      const result = await this.tablesService.uploadFiles(fileOne, fileTwo);

      // Save result to a service that is used by the mentee/mentor table
    } catch(error) {
      this.snackBar.open(
        `${error}`,
        undefined,
        {
          duration: 4000,
          verticalPosition: 'top',
        },
      );
    }

  }
}
