import { Component } from '@angular/core';
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
    private tablesService: TablesService,
  ) {}

  dropped(files: NgxFileDropEntry[], fileToOverwrite: string): void {
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

        /**
        // You could upload it like this:
        const formData = new FormData()
        formData.append('logo', file, relativePath)

        // Headers
        const headers = new HttpHeaders({
          'security-token': 'mytoken'
        })

        this.http.post('https://mybackend.com/api/upload/sanitize-and-save-logo', formData, { headers: headers, responseType: 'blob' })
        .subscribe(data => {
          // Sanitized logo returned from backend
        })
        **/

      });
    } else {
      // It was a directory (empty directories are added, otherwise only files)
      const fileEntry = firstDroppedFile.fileEntry as FileSystemDirectoryEntry;
      console.log(firstDroppedFile.relativePath, fileEntry);
    }
  }

  async uploadFiles(fileOne: NgxFileDropEntry, fileTwo: NgxFileDropEntry): Promise<void> {
    await this.tablesService.uploadFiles(fileOne, fileTwo);
  }

  fileOver(event: any) {
    console.log(event);
  }

  fileLeave(event: any) {
    console.log(event);
  }
}
